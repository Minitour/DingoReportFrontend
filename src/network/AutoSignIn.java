package network;

/**
 * Created By Tony on 14/02/2018
 */
public final class AutoSignIn {

    private AutoSignIn(){}

    public static String ID = null;

    public static String SESSION_TOKEN = null;

    public static int ROLE_ID = -1;

    public static String EMAIL = null;

    public static void reset() {
        ID = null;
        SESSION_TOKEN = null;
        ROLE_ID = -1;
        EMAIL = null;
    }
}
