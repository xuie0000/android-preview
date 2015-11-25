package com.xuie.androiddemo.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Links extends RealmObject {

    @SerializedName("web") @Expose @PrimaryKey private String web = "http://xuie0000.com/";
    @SerializedName("twitter") @Expose private String twitter;

    /**
     * @return The web
     */
    public String getWeb() {
        return web;
    }

    /**
     * @param web The web
     */
    public void setWeb(String web) {
        this.web = web;
    }

    /**
     * @return The twitter
     */
    public String getTwitter() {
        return twitter;
    }

    /**
     * @param twitter The twitter
     */
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

}
