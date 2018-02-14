package network;

/**
 * Created By Tony on 14/02/2018
 */
final public class Constants {

    /**
     * Protocol
     */
    public final static String protocol = "http";

    /**
     * Address
     */
    public final static String address = "localhost";

    /**
     * Port
     */
    public final static String port = "8080";

    /**
     * @return full path
     */
    public final static String getAddress(){
        return protocol + "://" + address + ":" + port;
    }

    /**
     * Routes class, contains all API routes.
     */
    public static class Routes {

        public static String login(){
            return getAddress() + "/signin";
        }
        public static String updatePassword(){return getAddress() + "/updatePassword"; }
        public static String getReports() { return getAddress() + "/getReports";}
        public static String createVolunteer() { return getAddress() + "/createVolunteer";}
        public static String makeDecision() { return getAddress() + "/makeDecision";}
        public static String submitReport() { return getAddress() + "/submitReport";}
        public static String uploadFile() { return getAddress() + "/uploadFile";}
        public static String exportReports() { return getAddress() + "/exportReports";}

    }
    public static class Codes{
        public static final int SUCCESS=200;
        public static final int MISSING_PARAMETERS=401;

    }

}
