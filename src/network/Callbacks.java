package network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.Report;
import model.VehicleModel;
import model.ViolationType;
import okhttp3.*;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created By Tony on 14/02/2018
 */
public final class Callbacks {

    private Callbacks(){}

    @FunctionalInterface
    interface Inner{
        void make(JsonObject json, Exception exception);
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

    public interface Resource {
        void make(InputStream stream,Exception e);
    }

    //TODO: add needed interfaces.




}
