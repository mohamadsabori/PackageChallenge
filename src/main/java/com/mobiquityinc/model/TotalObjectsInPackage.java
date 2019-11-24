package com.mobiquityinc.model;

import java.util.List;

public class TotalObjectsInPackage {
    List<Thing> things;
    Float totalCostOfObjectsInPackage;
    Float weightOfPackage;

    public TotalObjectsInPackage(List<Thing> things, Float totalCostOfObjectsInPackage, Float weightOfPackage) {
        this.things = things;
        this.totalCostOfObjectsInPackage = totalCostOfObjectsInPackage;
        this.weightOfPackage = weightOfPackage;
    }

    public List<Thing> getThings() {
        return things;
    }

    public void setThings(List<Thing> things) {
        this.things = things;
    }

    public Float getTotalCostOfObjectsInPackage() {
        return totalCostOfObjectsInPackage;
    }

    public void setTotalCostOfObjectsInPackage(Float totalCostOfObjectsInPackage) {
        this.totalCostOfObjectsInPackage = totalCostOfObjectsInPackage;
    }

    public Float getWeightOfPackage() {
        return weightOfPackage;
    }

    public void setWeightOfPackage(Float weightOfPackage) {
        this.weightOfPackage = weightOfPackage;
    }

    @Override
    public boolean equals(Object obj) {
        return this.totalCostOfObjectsInPackage.equals(((TotalObjectsInPackage)obj).getTotalCostOfObjectsInPackage());
    }
}
