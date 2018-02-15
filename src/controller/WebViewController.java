package controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import ui.UIViewController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By Tony on 15/02/2018
 */
public class WebViewController extends UIViewController {

    private Map<String,String> headers = new HashMap<>();

    private final String originalAgent;

    @FXML
    private WebView webView;

    public WebViewController() {
        super("/resources/xml/controller_web_view.fxml");
        originalAgent = webView.getEngine().getUserAgent();
    }

    public WebViewController(String url){
        this();
        setURL(url);
    }

    public void setURL(String url) {
        webView.getEngine().setUserAgent(prepareAgent());
        webView.getEngine().load(url);
    }
    
    //TODO: test this method.
    public void addHeader(String header,String value){
        headers.put(header,value);
    }


    private String prepareAgent(){
        StringBuilder builder = new StringBuilder();
        String nL = "\n";
        builder.append(originalAgent).append(nL);

        for(Map.Entry<String,String> entry : headers.entrySet()){
            builder.append(entry.getKey())
                    .append(":")
                    .append(entry.getValue())
                    .append(nL);
        }
        return builder.toString();
    }
}
