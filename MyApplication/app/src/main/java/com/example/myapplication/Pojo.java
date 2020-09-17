package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class Pojo {
    @SerializedName("prediction")
    private String prediction;

    @SerializedName("CHECK")
    private String CHECK;

    public String getCHECK()
    {
        return CHECK;
    }

    public String getPrediction() {
        return prediction;
    }
}
