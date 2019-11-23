package com.mobiquityinc.model;

public class Thing {
    private int id;
    private float size;
    private float price;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Thing(int id, float size, float price) {
        this.id = id;
        this.size = size;
        this.price = price;
    }

}
