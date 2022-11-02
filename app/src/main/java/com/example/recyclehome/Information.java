package com.example.recyclehome;

public class Information {
    private final static String IpAddress="192.168.1.71";
    private final static String pathimage="http://192.168.1.71/materials_image/";
    private static int id;
    private static int phone;
    private static int id_material;
    private static double weight_material;

    public static int getId_material() {
        return id_material;
    }

    public static void setId_material(int id_material) {
        Information.id_material = id_material;
    }

    public static double getWeight_material() {
        return weight_material;
    }

    public static void setWeight_material(double weight_material) {
        Information.weight_material = weight_material;
    }

    public static String getPathimage() {
        return pathimage;
    }

    public static String getIpAddress() {
        return IpAddress;
    }

    public static int getPhone() {
        return phone;
    }

    public static void setPhone(int phone) {
        Information.phone = phone;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Information.id = id;
    }
}
