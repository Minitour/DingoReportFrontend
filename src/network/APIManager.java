package network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import okhttp3.*;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * Created By Tony on 14/02/2018
 */
public class APIManager {

    public static APIManager getInstance(){
        return manager;
    }
    //singleton
    private final static APIManager manager = new APIManager();
    private final OkHttpClient client;
    private final String TAG = "API-MANAGER";

    /**
     * Private constructor
     */
    private APIManager(){

        client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /**
     *
     * Use this method to login.
     *
     * @param email The user's email
     * @param password The user's password
     * @param callback The callback.
     */
    public void login(String email, String password, Callbacks.Auth callback){
        JsonObject data = new JsonObject();
        data.addProperty("email",email);
        data.addProperty("password",password);

        makeRequest(Constants.Routes.login(),null,data,(json, ex) -> {
            ServerResponse r = new ServerResponse(json);
            if(ex == null && r.isOK()){
                JsonObject d = json.get("data").getAsJsonObject();
                String id = d.get("id").getAsString();
                String token = d.get("sessionToken").getAsString();
                int roleId = d.get("role").getAsInt();
                callback.make(r,id,token,roleId,null);
            }else
                callback.make(r,null,null,-1,ex);

        });
    }

    //TODO: add other methods here

    /**
     * This method makes a post HTTP request to a url using the given params.
     *
     * @param url The route to make http request to.
     * @param jsonBody The parameters to pass in.
     * @param callback The call back function.
     */
    private void makeRequest(String url,
                             Map<String,String> headers,
                             JsonObject jsonBody,
                             final Callbacks.Inner callback){
        //define media type
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        //create request body from params

        RequestBody body = RequestBody.create(mediaType,jsonBody.toString());
        //create request
        Request request;
        Request.Builder builder = new Request
                .Builder()
                .url(url)
                .post(body)
                .addHeader("content-type","application/json");

        //add additional headers
        if(headers != null)
            headers.forEach(builder::addHeader);

        request = builder.build();


        //make request
        makeOkHttpRequest(request,callback);
    }


    private void makeOkHttpRequest(Request request,Callbacks.Inner callback){
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null)
                    callback.make(null,e);
                System.err.println( "onFailure: " + e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (callback!= null){
                    try (ResponseBody responseBody = response.body()) {
                        String res = responseBody.string();
                        System.out.println("onResponse: " + res);

                        //make thread safe.
                        Platform.runLater(() -> {
                            try{
                                JsonParser parser = new JsonParser();
                                JsonObject o = parser.parse(res).getAsJsonObject();
                                callback.make(o,null);
                            }catch (Exception e) {
                                callback.make(null, e);
                            }

                        });
                        responseBody.close();
                    }
                }
            }
        });
    }
}
