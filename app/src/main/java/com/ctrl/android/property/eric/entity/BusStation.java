package com.ctrl.android.property.eric.entity;

/**
 * 便民公交  相关信息
 * Created by Eric on 2015/10/19.
 */
public class BusStation {

    /**车站名称*/
    private String stationName;
    /**当前点 距车站距离*/
    private int stationDistance;
    /**车站所有 车辆线路*/
    private String stationAddress;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getStationDistance() {
        return stationDistance;
    }

    public void setStationDistance(int stationDistance) {
        this.stationDistance = stationDistance;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }
}
