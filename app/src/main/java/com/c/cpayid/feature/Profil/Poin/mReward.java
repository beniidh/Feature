package com.c.cpayid.feature.Profil.Poin;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public  class mReward {


    @SerializedName("data")
    public ArrayList<DataEntity> data;
    @SerializedName("message")
    public String message;
    @SerializedName("success")
    public boolean success;
    @SerializedName("error")
    public String error;
    @SerializedName("elapsed")
    public double elapsed;
    @SerializedName("code")
    public int code;
    @SerializedName("response_id")
    public String responseId;

    public static class DataEntity {
        @SerializedName("updated_at")
        public String updatedAt;
        @SerializedName("created_at")
        public String createdAt;
        @SerializedName("created_by")
        public String createdBy;
        @SerializedName("status")
        public int status;
        @SerializedName("image")
        public String image;
        @SerializedName("remaining")
        public int remaining;
        @SerializedName("poin_price")
        public int poinPrice;
        @SerializedName("description")
        public String description;
        @SerializedName("name")
        public String name;
        @SerializedName("server_id")
        public String serverId;
        @SerializedName("id")
        public String id;
    }
}
