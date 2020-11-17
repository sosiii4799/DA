package com.example.da.retrofit2;

public class APIService {
//    private static final String DOMAIN = "192.168.5.171";
//    private static final String HOSTING = "http://"+ DOMAIN;
//    private static final String BASE_URL = "http://" + DOMAIN + "/susnnn/";


    //Hosting

    private static final String DOMAIN = "devnguyen99.com";
    private static final String HOSTING = "http://"+ DOMAIN;
    private static final String BASE_URL = "http://" + DOMAIN + "/Susnnn/";

    public static DataService getService(){
        return RetrofitClient.getClient(BASE_URL).create(DataService.class);
    }
}
