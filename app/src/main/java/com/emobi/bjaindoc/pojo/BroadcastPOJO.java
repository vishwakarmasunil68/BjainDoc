package com.emobi.bjaindoc.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 27-03-2017.
 */

public class BroadcastPOJO {
    @SerializedName("bro_id")
    String bro_id;
    @SerializedName("bro_doc_id")
    String bro_doc_id;
    @SerializedName("bro_mess")
    String bro_mess;
    @SerializedName("bro_date")
    String bro_date;
    @SerializedName("bro_time")
    String bro_time;
    @SerializedName("bro_datetime")
    String bro_datetime;

    public String getBro_id() {
        return bro_id;
    }

    public void setBro_id(String bro_id) {
        this.bro_id = bro_id;
    }

    public String getBro_doc_id() {
        return bro_doc_id;
    }

    public void setBro_doc_id(String bro_doc_id) {
        this.bro_doc_id = bro_doc_id;
    }

    public String getBro_mess() {
        return bro_mess;
    }

    public void setBro_mess(String bro_mess) {
        this.bro_mess = bro_mess;
    }

    public String getBro_date() {
        return bro_date;
    }

    public void setBro_date(String bro_date) {
        this.bro_date = bro_date;
    }

    public String getBro_time() {
        return bro_time;
    }

    public void setBro_time(String bro_time) {
        this.bro_time = bro_time;
    }

    public String getBro_datetime() {
        return bro_datetime;
    }

    public void setBro_datetime(String bro_datetime) {
        this.bro_datetime = bro_datetime;
    }

    @Override
    public String toString() {
        return "BroadcastPOJO{" +
                "bro_id='" + bro_id + '\'' +
                ", bro_doc_id='" + bro_doc_id + '\'' +
                ", bro_mess='" + bro_mess + '\'' +
                ", bro_date='" + bro_date + '\'' +
                ", bro_time='" + bro_time + '\'' +
                ", bro_datetime='" + bro_datetime + '\'' +
                '}';
    }
}
