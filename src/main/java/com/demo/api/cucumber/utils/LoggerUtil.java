package com.demo.api.cucumber.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerUtil {

    private static Logger logger;
     public LoggerUtil(){
        try{
            this.logger=Logger.getLogger("Demo");
            PropertyConfigurator.configure(System.getProperty("user.dir")+"/log4j.properties");
        }catch (Exception ae){
            ae.printStackTrace();
        }
    }

    public static Logger getLogger(){
         Logger Temp=null;
         try{
             if(logger==null){
                 LoggerUtil loggerUtil=new LoggerUtil();
                 Temp=logger;
             }else{
                 Temp=logger;
             }
         }catch (Exception ae){
             ae.printStackTrace();
         }return Temp;
    }



}
