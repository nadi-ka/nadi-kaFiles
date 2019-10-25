package com.company;

import java.util.HashMap;

public class DataOfClient {
    private HashMap<Integer, String> mapData = new HashMap<>();

    public void setMapData(HashMap<Integer, String> mapData) {
        this.mapData = mapData;
    }

    public HashMap<Integer, String> getMapData() {
        return mapData;
    }

}
