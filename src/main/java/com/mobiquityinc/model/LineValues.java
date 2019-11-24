package com.mobiquityinc.model;

import java.util.List;

public class LineValues {
    private float weightOfPackage;
    private String lineString;

    public float getWeightOfPackage() {
        return weightOfPackage;
    }

    public void setWeightOfPackage(float weightOfPackage) {
        this.weightOfPackage = weightOfPackage;
    }

    public String getLineString() {
        return lineString;
    }

    public void setLineString(String lineString) {
        this.lineString = lineString;
    }

    public LineValues(float weightOfPackage, String lineString) {
        this.weightOfPackage = weightOfPackage;
        this.lineString = lineString;
    }
    public String[] getThingsValue(){
        return this.getLineString().split(" ");
    }
}
