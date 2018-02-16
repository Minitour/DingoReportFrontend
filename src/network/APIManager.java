package network;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import model.*;
import okhttp3.*;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private static Gson gson;

    static {
        GsonBuilder gsonBilder = new GsonBuilder();
        gsonBilder.registerTypeAdapter(Violation.class, new AbstractElementAdapter());
        gson = gsonBilder.create();
    }

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
                AutoSignIn.ID = id;
                AutoSignIn.SESSION_TOKEN = token;
                AutoSignIn.ROLE_ID = roleId;
                AutoSignIn.EMAIL = email;
                callback.make(r,id,token,roleId,null);
            }else
                callback.make(r,null,null,-1,ex);

        });
    }

    public void updatePassword(String id,String token,String currentPassword,String newPassword,Callbacks.General callback){
        JsonObject body = new JsonObject();
        body.addProperty("id",id);
        body.addProperty("sessionToken",token);
        body.addProperty("currentPassword",currentPassword);
        body.addProperty("newPassword",newPassword);

        makeRequest(Constants.Routes.updatePassword(),null,body,(json, ex) ->
                callback.make(new ServerResponse(json),ex));
    }

    public void updatePassword(String currentPassword,String newPassword,Callbacks.General callback){
        updatePassword(AutoSignIn.ID,AutoSignIn.SESSION_TOKEN,currentPassword,newPassword,callback);
    }

    public void getReports(String id,String token,Callbacks.Reports callback){
        JsonObject body = new JsonObject();
        body.addProperty("id",id);
        body.addProperty("sessionToken",token);

        makeRequest(Constants.Routes.getReports(),null,body,(json, exception) -> {
            ServerResponse r = new ServerResponse(json);
            if(exception == null){
                List<Report> reports = new ArrayList<>();
                JsonArray array = gson.fromJson(json.get("data").getAsJsonArray(),JsonArray.class);

                for(JsonElement object : array){
                    try {
                        Report report = gson.fromJson(object, Report.class);
                        reports.add(report);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                }

                callback.make(r,reports,null);
            }else{
                callback.make(r,null,exception);
            }
        });
    }

    public void getReports(Callbacks.Reports callback){
        getReports(AutoSignIn.ID,AutoSignIn.SESSION_TOKEN,callback);
    }

    public void getViolationTypes(String id,String token, Callbacks.ViolationTypes callback){
        JsonObject body = new JsonObject();
        body.addProperty("id",id);
        body.addProperty("sessionToken",token);

        makeRequest(Constants.Routes.getViolationTypes(),null,body,(json, exception) -> {
            ServerResponse r = new ServerResponse(json);
            if(exception == null){
                List<ViolationType> types = new ArrayList<>();
                JsonArray array = gson.fromJson(json.get("data").getAsJsonArray(),JsonArray.class);

                for(JsonElement object : array){
                    try {
                        ViolationType type = gson.fromJson(object, ViolationType.class);
                        types.add(type);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                }

                callback.make(r,types,null);
            }else{
                callback.make(r,null,exception);
            }
        });
    }

    public void getViolationTypes(Callbacks.ViolationTypes callback){
        getViolationTypes(AutoSignIn.ID,AutoSignIn.SESSION_TOKEN,callback);
    }

    public void getVehicleModels(String id,String token, Callbacks.VehicleModels callback){
        JsonObject body = new JsonObject();
        body.addProperty("id",id);
        body.addProperty("sessionToken",token);

        makeRequest(Constants.Routes.getVehicleModels(),null,body,(json, exception) -> {
            ServerResponse r = new ServerResponse(json);
            if(exception == null){
                List<VehicleModel> models = new ArrayList<>();
                JsonArray array = gson.fromJson(json.get("data").getAsJsonArray(),JsonArray.class);

                for(JsonElement object : array){
                    try {
                        VehicleModel model = gson.fromJson(object, VehicleModel.class);
                        models.add(model);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                }

                callback.make(r,models,null);
            }else{
                callback.make(r,null,exception);
            }
        });
    }

    public void getVehicleModels(Callbacks.VehicleModels callback){
        getVehicleModels(AutoSignIn.ID,AutoSignIn.SESSION_TOKEN,callback);
    }

    public void getResource(String id,String token,String resource,Callbacks.Resource callback){
        Map<String,String> headers = new HashMap<>();
        headers.put("id",id);
        headers.put("sessionToken",token);
        requestResource(Constants.Routes.resource(resource), headers, callback);
    }

    public void getResource(String resource,Callbacks.Resource callback){
        getResource(AutoSignIn.ID,AutoSignIn.SESSION_TOKEN,resource,callback);
    }

    public void createVolunteer(String id, String token, Volunteer volunteer, Callbacks.General callback) {
        JsonObject body = new JsonObject();
        body.addProperty("id",id);
        body.addProperty("sessionToken",token);
        body.add("volunteer", toJson(volunteer));

        makeRequest(Constants.Routes.getVehicleModels(), null, body, (json, exception) -> {
            ServerResponse r = new ServerResponse(json);
            if (exception == null) {
                callback.make(r,null);
            }
            else {
                callback.make(r, exception);
            }
        });
    }

    public void makeDecision() {

    }

    /**
     * This function takes in a report object and sends it to the server.
     * Before sending the report object it will iterate over the violations and then upload them synchronously.
     * After all files have been uploaded the callback will be invoked.
     *
     * @param id The user id.
     * @param token The user token
     * @param report The report object (raw)
     * @param callback The callback.
     */
    public void submitReport(String id, String token, Report report, Callbacks.General callback){
        List<Violation> violations = report.getViolations();
        uploadAndModifyViolations(id,token,violations,(violationsModified -> {
            report.setViolations(violationsModified);
            JsonObject body = new JsonObject();
            body.addProperty("id",id);
            body.addProperty("sessionToken",token);
            body.add("report", toJson(report));
            makeRequest(Constants.Routes.submitReport(),null,body,(json,ex)->{
                ServerResponse res = new ServerResponse(json);
                callback.make(res,ex);
            });
        }));
    }

    /**
     * This function takes a list of violations and uploads them into the server, then updates the resource url for each violation.
     *
     * @param id the user id.
     * @param token the user token.
     * @param violations the list of violations.
     * @param callback the callback to be executed upon completion.
     */
    private void uploadAndModifyViolations(String id,String token,List<Violation> violations,Callbacks.ViolationsInner callback){
        new Thread(()->{
            for(Violation v : violations){
                File file = new File(v.getEvidenceLink());
                //upload to server and get file
                String resourceUrl = uploadFile(id,token,file);
                v.setEvidenceLink(resourceUrl);
            }
            callback.make(violations);
        }).start();
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

    private void requestResource(String url,Map<String,String> headers,final Callbacks.Resource callback){
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        //create request body from params
        //create request
        Request request;

        Request.Builder builder = new Request
                .Builder()
                .url(url)
                .get()
                .addHeader("content-type","application/json");

        //add additional headers
        if(headers != null)
            headers.forEach(builder::addHeader);

        request = builder.build();

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
                        InputStream res = responseBody.byteStream();
                        System.out.println("onResponse: " + res);

                        //make thread safe.
                        Platform.runLater(() -> {
                            callback.make(res,null);
                        });
                        responseBody.close();
                    }
                }
            }
        });
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

    private <T> JsonObject toJson(T object){
        JsonElement jsonElement = gson.toJsonTree(object);
        return (JsonObject) jsonElement;
    }

    private String uploadFile(String id,String token,File file){
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),file);
        String ext = getFileExtension(file);
        Request request = new Request.Builder()
                .url(Constants.Routes.uploadFile())
                .put(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("id", id)
                .addHeader("sessiontoken", token)
                .addHeader("filetype", ext)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String res =  response.body().string();
            JsonParser parser = new JsonParser();
            JsonObject o = parser.parse(res).getAsJsonObject();
            return o.get("path").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private String getFileExtension(File file){
        String[] parts = file.getPath().split("\\.");
        return parts[parts.length - 1];
    }

    static class AbstractElementAdapter implements JsonDeserializer<Violation> {
        @Override
        public Violation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            //1 = video, 0 = image
            boolean hasDescription = jsonObject.has("description");

            return context.deserialize(json, hasDescription ? VideoViolation.class : ImageViolation.class);
        }
    }

}
