package com.easylife.aunweshaacademy.models;

import android.util.Log;

import org.json.JSONObject;

public class CourseModel {
    int id;
    float prise;
    String name,imageUrl;
    Boolean isEnrolled;

    public CourseModel(int id, float prise, String name, String imageUrl, Boolean isEnrolled) {
        this.id = id;
        this.prise = prise;
        this.name = name;
        this.imageUrl = imageUrl;
        this.isEnrolled = isEnrolled;
    }

    public CourseModel(JSONObject jsonObject) {
        try {
            Log.i("price",jsonObject.getString("price"));
            this.id = Integer.parseInt(jsonObject.getString("id").toString());
            if(!jsonObject.getString("price").equals("null")) {
                this.prise = Float.parseFloat(jsonObject.getString("price"));
            }else {
                this.prise = 0;
            }
            this.name = jsonObject.getString("name").toString();
            this.imageUrl = jsonObject.getString("image").toString();

            if(jsonObject.getString("isEnrolled").equals("true")){
                this.isEnrolled = true;
            }else {
                this.isEnrolled=false;
            }

            Log.i("item",jsonObject.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrise() {
        return prise;
    }

    public void setPrise(float prise) {
        this.prise = prise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getEnrolled() {
        return isEnrolled;
    }

    public void setEnrolled(Boolean enrolled) {
        isEnrolled = enrolled;
    }
}
