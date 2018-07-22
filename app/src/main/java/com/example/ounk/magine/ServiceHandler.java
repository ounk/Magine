package com.example.ounk.magine;

import android.os.StrictMode;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


final class ServiceHandler {

    public static final String strUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/";
    private static final String strUrlSuffix = "videos-enhanced-c.json";

    static String LoadJson() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            URL url;
            url = new URL(strUrl+strUrlSuffix);
            HttpURLConnection con;
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            con.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();

            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
