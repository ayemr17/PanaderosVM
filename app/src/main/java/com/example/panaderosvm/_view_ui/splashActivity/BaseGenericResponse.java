package com.example.panaderosvm._view_ui.splashActivity;

public class BaseGenericResponse {

    protected int requestType;

    public BaseGenericResponse(int requestType){
        this.requestType = requestType;
    }

    public int getRequestType(){
        return requestType;
    }

}
