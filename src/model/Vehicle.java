package model;

import com.google.gson.annotations.Expose;

import java.util.Collection;

public class Vehicle{

    @Expose
    private String licensePlate;

    @Expose
    private VehicleModel model;

    @Expose
    private String colorHEX;

    @Expose
    private Collection<VehicleOwner> owners;

    public Vehicle(String licensePlate, VehicleModel model, String colorHEX) {
        setColorHEX(colorHEX);
        setLicensePlate(licensePlate);
        setModel(model);
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public VehicleModel getModel() {
        return model;
    }

    public void setModel(VehicleModel model) {
        this.model = model;
    }

    public String getColorHEX() {
        return colorHEX;
    }

    public void setColorHEX(String colorHEX) {
        this.colorHEX = colorHEX;
    }

    public Collection<VehicleOwner> getOwners() {
        return owners;
    }

    public void setOwners(Collection<VehicleOwner> owners) {
        this.owners = owners;
    }

}
