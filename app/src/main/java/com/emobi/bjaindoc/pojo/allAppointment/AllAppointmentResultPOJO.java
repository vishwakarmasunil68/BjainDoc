package com.emobi.bjaindoc.pojo.allAppointment;

/**
 * Created by sunil on 27-02-2017.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AllAppointmentResultPOJO implements Serializable{

    @SerializedName("p_id")
    String p_id;
    @SerializedName("a_id")
    String a_id;
    @SerializedName("a_date")
    String a_date;
    @SerializedName("a_time")
    String a_time;
    @SerializedName("m_id")
    String m_id;
    @SerializedName("m_name")
    String m_name;
    @SerializedName("a_clinic_id")
    String a_clinic_id;
    @SerializedName("a_status")
    String a_status;
    @SerializedName("p_name")
    String p_name;
    @SerializedName("p_mob")
    String p_mob;
    @SerializedName("p_device_token")
    String p_device_token;
    @SerializedName("p_photo")
    String p_photo;
    @SerializedName("clinic_name")
    String clinic_name;

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getA_id() {
        return a_id;
    }

    public void setA_id(String a_id) {
        this.a_id = a_id;
    }

    public String getA_date() {
        return a_date;
    }

    public void setA_date(String a_date) {
        this.a_date = a_date;
    }

    public String getA_time() {
        return a_time;
    }

    public void setA_time(String a_time) {
        this.a_time = a_time;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getA_clinic_id() {
        return a_clinic_id;
    }

    public void setA_clinic_id(String a_clinic_id) {
        this.a_clinic_id = a_clinic_id;
    }

    public String getA_status() {
        return a_status;
    }

    public void setA_status(String a_status) {
        this.a_status = a_status;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_mob() {
        return p_mob;
    }

    public void setP_mob(String p_mob) {
        this.p_mob = p_mob;
    }

    public String getP_device_token() {
        return p_device_token;
    }

    public void setP_device_token(String p_device_token) {
        this.p_device_token = p_device_token;
    }

    public String getP_photo() {
        return p_photo;
    }

    public void setP_photo(String p_photo) {
        this.p_photo = p_photo;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    @Override
    public String toString() {
        return "AllAppointmentResultPOJO{" +
                "p_id='" + p_id + '\'' +
                ", a_id='" + a_id + '\'' +
                ", a_date='" + a_date + '\'' +
                ", a_time='" + a_time + '\'' +
//                ", m_id='" + m_id + '\'' +
//                ", m_name='" + m_name + '\'' +
//                ", a_clinic_id='" + a_clinic_id + '\'' +
//                ", a_status='" + a_status + '\'' +
//                ", p_name='" + p_name + '\'' +
//                ", p_mob='" + p_mob + '\'' +
//                ", p_device_token='" + p_device_token + '\'' +
//                ", p_photo='" + p_photo + '\'' +
//                ", clinic_name='" + clinic_name + '\'' +
                '}';
    }
}
