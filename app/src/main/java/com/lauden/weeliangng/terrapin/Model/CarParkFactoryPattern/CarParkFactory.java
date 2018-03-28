package com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern;

public class CarParkFactory {
    public CarParkStrategy getCarParkType(String carparkType){



        if(carparkType.equals("HDB")){
            return new HDBCarParkObject();
        }else if(carparkType.equals("MALL")){
            return new MallObject();
        }else{
            return null;
        }

    }
}