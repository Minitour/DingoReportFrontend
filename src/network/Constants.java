package network;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;

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
        public static String getViolationTypes() {return getAddress() + "/getViolationTypes";}
        public static String getVehicleModels() {return getAddress() + "/getVehicleModels";}
        public static String createUser() {
            return getAddress() + "/createUser";
        }
        public static String getTeams() {
            return getAddress() + "/getTeams";
        }
        public static String getAllOfficers() {
            return getAddress() + "/getAllOfficers" ;
        }
        public static String createTeam() {
            return getAddress() + "/createTeam";
        }
        public static String addReportToTeam() {
            return getAddress() + "/addReportToTeam";
        }
        public static String getUnassignedReports(){ return getAddress() + "/getUnassignedReports"; }
        public static String getUnassignedOfficers(){ return getAddress() + "/getUnassignedOfficers"; }
        public static String addOfficerToTeam() {
            return getAddress() + "/addOfficerToTeam";
        }

        public static String resource(String resource){ return getAddress() + "/"+ resource; }


    }
    public static class Codes{
        public static final int SUCCESS=200;
        public static final int MISSING_PARAMETERS=401;

    }

}
