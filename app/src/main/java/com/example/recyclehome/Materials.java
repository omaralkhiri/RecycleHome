package com.example.recyclehome;

public class Materials {
    private int Id;
    private String imageId;
    private String Meterial_Name;
    private double price;
    private float weight;

    public Materials(int Id,String imageId, String meterial_Name, double price) {
        this.Id=Id;
        this.imageId = imageId;
        Meterial_Name = meterial_Name;
        this.price = price;
    }

    public Materials(int id,String imageId, String meterial_Name, float weight) {
        this.Id=id;
        this.imageId = imageId;
        Meterial_Name = meterial_Name;
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getMeterial_Name() {
        return Meterial_Name;
    }

    public void setMeterial_Name(String meterial_Name) {
        Meterial_Name = meterial_Name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
