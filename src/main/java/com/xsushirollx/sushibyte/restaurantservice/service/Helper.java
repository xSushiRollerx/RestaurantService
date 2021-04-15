package com.xsushirollx.sushibyte.restaurantservice.service;

public class Helper {

    public Boolean isDouble(String string){
        Boolean isDouble;
        try {
            Double.parseDouble(string);
            isDouble = true;
        }catch (NumberFormatException e){
            isDouble = false;
        }
        return isDouble;
    }


    public Double roundTwoPlaces(Double dub,Double places){
        Double dec = Math.pow(10,places);
        return  Math.round(dub * dec)/dec;
    }

}
