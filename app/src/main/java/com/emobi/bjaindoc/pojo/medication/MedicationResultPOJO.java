package com.emobi.bjaindoc.pojo.medication;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 17-03-2017.
 */

public class MedicationResultPOJO {
    @SerializedName("med_id")
    String med_id;
    @SerializedName("med_p_id")
    String med_p_id;
    @SerializedName("med_doc_id")
    String med_doc_id;
    @SerializedName("med_mess")
    String med_mess;
    @SerializedName("med_date")
    String med_date;
    @SerializedName("med_time")
    String med_time;
    @SerializedName("med_datetime")
    String med_datetime;

    public String getMed_id() {
        return med_id;
    }

    public void setMed_id(String med_id) {
        this.med_id = med_id;
    }

    public String getMed_p_id() {
        return med_p_id;
    }

    public void setMed_p_id(String med_p_id) {
        this.med_p_id = med_p_id;
    }

    public String getMed_doc_id() {
        return med_doc_id;
    }

    public void setMed_doc_id(String med_doc_id) {
        this.med_doc_id = med_doc_id;
    }

    public String getMed_mess() {
        return med_mess;
    }

    public void setMed_mess(String med_mess) {
        this.med_mess = med_mess;
    }

    public String getMed_date() {
        return med_date;
    }

    public void setMed_date(String med_date) {
        this.med_date = med_date;
    }

    public String getMed_time() {
        return med_time;
    }

    public void setMed_time(String med_time) {
        this.med_time = med_time;
    }

    public String getMed_datetime() {
        return med_datetime;
    }

    public void setMed_datetime(String med_datetime) {
        this.med_datetime = med_datetime;
    }

    @Override
    public String toString() {
        return "MedicationResultPOJO{" +
                "med_id='" + med_id + '\'' +
                ", med_p_id='" + med_p_id + '\'' +
                ", med_doc_id='" + med_doc_id + '\'' +
                ", med_mess='" + med_mess + '\'' +
                ", med_date='" + med_date + '\'' +
                ", med_time='" + med_time + '\'' +
                ", med_datetime='" + med_datetime + '\'' +
                '}';
    }
}
