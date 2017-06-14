package com.example.karanc.admission;

/**
 * Created by karanc on 27-05-2016.
 */
public class DataModel {
    String name;
    String city;

    public DataModel(String name, String city){
        this.city=city;
        this.name=name;
    }
    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }


}
