package com.morimoku.project_popular_movies2;

public class Data {

    private String id;
    private String name;
    private String key;

    public Data(){}

    public Data(String id, String name, String key){
        this.id = id;
        this.name = name;
        this.key = key;
    }


    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getKey(){
        return key;
    }
    public void setKey(String data_key) {
        this.key = data_key;
    }
    public String getName(){
        return name;
    }

    public void setName(String data_name) {
        this.name = data_name;
    }
}
