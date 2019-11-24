package com.mobiquityinc.model;

public class Thing {
    private int id;
    private float size;
    private Float price;
    private Float packageWeight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(Float packageWeight) {
        this.packageWeight = packageWeight;
    }

    public Thing(int id, float size, float price, Float packageWeight) {
        this.id = id;
        this.size = size;
        this.price = price;
        this.packageWeight = packageWeight;
    }



}
