package controller;

import network.AutoSignIn;

/**
 * Created by Antonio Zaitoun on 15/02/2018.
 */
public class AuthenticatedWebViewController extends WebViewController{

    public AuthenticatedWebViewController() {
        super();
        init();
    }

    public AuthenticatedWebViewController(String path) {
        super(path);
        init();
    }

    private void init(){
        addHeader("id", AutoSignIn.ID);
        addHeader("sessionToken",AutoSignIn.SESSION_TOKEN);
    }
}
