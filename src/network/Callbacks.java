package network;

import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import model.*;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.InputStream;
import java.util.List;

/**
 * Created By Tony on 14/02/2018
 */
public final class Callbacks {

    private Callbacks(){}

    @FunctionalInterface
    interface Inner{
        void make(JsonObject json, Exception exception);
    }

    interface ViolationsInner{
        void make(List<Violation> violations);
    }

    /**
     * General callback, used only by the API Manager internally.
     */
    @FunctionalInterface
    public interface General{
        void make(ServerResponse response,Exception exception);
    }

    @FunctionalInterface
    public interface Auth {
        void make(ServerResponse response,String id, String token,int roleId, Exception exception);
    }

    @FunctionalInterface
    public interface Reports {
        void make(ServerResponse response, List<Report> reports,Exception exception);
    }

    @FunctionalInterface
    public interface ViolationTypes {
        void make(ServerResponse response, List<ViolationType> violationTypes, Exception exception);
    }

    @FunctionalInterface
    public interface VehicleModels {
        void make(ServerResponse response, List<VehicleModel> vehicleModels, Exception exception);
    }

    @FunctionalInterface
    public interface Resource {
        void make(InputStream stream,Exception e);
    }

    @FunctionalInterface
    public interface Jasper {
        void make(JasperPrint print, Exception e);
    }

    @FunctionalInterface
    public interface Teams {
        void make(ServerResponse response, List<Team> teams,Exception ex);
    }

    @FunctionalInterface
    public interface Officers {
        void make(ServerResponse response, List<Officer> officers,Exception ex);
    }

    @FunctionalInterface
    public interface Accounts {
        void make(ServerResponse response,List<Account> accounts,Exception ex);
    }

}
