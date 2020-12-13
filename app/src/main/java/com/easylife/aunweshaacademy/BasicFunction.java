package com.easylife.aunweshaacademy;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class BasicFunction  {
    Context context;

    public BasicFunction(Context context) {
        this.context = context;
    }

    public void write(String key, String data){
        SharedPreferences sharedPreferences=context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(key,data);
        editor.commit();

    }

    public String read(String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"null");
    }

    public String encode(String str){


        byte[] data = new byte[0];
        try {
            data = str.getBytes("UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);

        return base64;



    }






}
