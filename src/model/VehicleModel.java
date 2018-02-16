package model;

import com.google.gson.annotations.Expose;

/**
 * Created By Tony on 12/02/2018
 */
public class VehicleModel {

    @Expose
    private int modelNum;

    @Expose
    private String name;

    public VehicleModel(int modelNum, String name) {
        this.modelNum = modelNum;
        this.name = name;
    }

    public int getModelNum() {
        return modelNum;
    }

    public void setModelNum(int modelNum) {
        this.modelNum = modelNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[#"+modelNum+"] "+name;
    }
}
