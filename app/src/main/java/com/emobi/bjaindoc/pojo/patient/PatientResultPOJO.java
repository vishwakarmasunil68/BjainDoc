package com.emobi.bjaindoc.pojo.patient;

/**
 * Created by sunil on 16-03-2017.
 */


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class PatientResultPOJO implements Serializable{

    @SerializedName("p_id")
    String p_id;
    @SerializedName("doc_id")
    String doc_id;
    @SerializedName("p_name")
    String p_name;
    @SerializedName("p_login_id")
    String p_login_id;
    @SerializedName("p_login_pass")
    String p_login_pass;
    @SerializedName("p_age")
    String p_age;
    @SerializedName("p_bloodgroup")
    String p_bloodgroup;
    @SerializedName("p_weight")
    String p_weight;
    @SerializedName("p_height")
    String p_height;
    @SerializedName("p_mob")
    String p_mob;
    @SerializedName("p_status")
    String p_status;
    @SerializedName("p_photo")
    String p_photo;
    @SerializedName("p_gcm_id")
    String p_gcm_id;
    @SerializedName("p_device_token")
    String p_device_token;
    @SerializedName("p_medication")
    String p_medication;
    @SerializedName("p_alergi")
    String p_alergi;
    @SerializedName("p_digoxin")
    String p_digoxin;
    @SerializedName("msg")
    String msg;
    @SerializedName("p_message")
    String p_message;
    @SerializedName("conditions")
    String conditions;
    @SerializedName("description")
    String description;


    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_login_id() {
        return p_login_id;
    }

    public void setP_login_id(String p_login_id) {
        this.p_login_id = p_login_id;
    }

    public String getP_login_pass() {
        return p_login_pass;
    }

    public void setP_login_pass(String p_login_pass) {
        this.p_login_pass = p_login_pass;
    }

    public String getP_age() {
        return p_age;
    }

    public void setP_age(String p_age) {
        this.p_age = p_age;
    }

    public String getP_bloodgroup() {
        return p_bloodgroup;
    }

    public void setP_bloodgroup(String p_bloodgroup) {
        this.p_bloodgroup = p_bloodgroup;
    }

    public String getP_weight() {
        return p_weight;
    }

    public void setP_weight(String p_weight) {
        this.p_weight = p_weight;
    }

    public String getP_height() {
        return p_height;
    }

    public void setP_height(String p_height) {
        this.p_height = p_height;
    }

    public String getP_mob() {
        return p_mob;
    }

    public void setP_mob(String p_mob) {
        this.p_mob = p_mob;
    }

    public String getP_status() {
        return p_status;
    }

    public void setP_status(String p_status) {
        this.p_status = p_status;
    }

    public String getP_photo() {
        return p_photo;
    }

    public void setP_photo(String p_photo) {
        this.p_photo = p_photo;
    }

    public String getP_gcm_id() {
        return p_gcm_id;
    }

    public void setP_gcm_id(String p_gcm_id) {
        this.p_gcm_id = p_gcm_id;
    }

    public String getP_device_token() {
        return p_device_token;
    }

    public void setP_device_token(String p_device_token) {
        this.p_device_token = p_device_token;
    }

    public String getP_medication() {
        return p_medication;
    }

    public void setP_medication(String p_medication) {
        this.p_medication = p_medication;
    }

    public String getP_alergi() {
        return p_alergi;
    }

    public void setP_alergi(String p_alergi) {
        this.p_alergi = p_alergi;
    }

    public String getP_digoxin() {
        return p_digoxin;
    }

    public void setP_digoxin(String p_digoxin) {
        this.p_digoxin = p_digoxin;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getP_message() {
        return p_message;
    }

    public void setP_message(String p_message) {
        this.p_message = p_message;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PatientResultPOJO{" +
                "p_id='" + p_id + '\'' +
                ", doc_id='" + doc_id + '\'' +
                ", p_name='" + p_name + '\'' +
                ", p_login_id='" + p_login_id + '\'' +
                ", p_login_pass='" + p_login_pass + '\'' +
                ", p_age='" + p_age + '\'' +
                ", p_bloodgroup='" + p_bloodgroup + '\'' +
                ", p_weight='" + p_weight + '\'' +
                ", p_height='" + p_height + '\'' +
                ", p_mob='" + p_mob + '\'' +
                ", p_status='" + p_status + '\'' +
                ", p_photo='" + p_photo + '\'' +
                ", p_gcm_id='" + p_gcm_id + '\'' +
                ", p_device_token='" + p_device_token + '\'' +
                ", p_medication='" + p_medication + '\'' +
                ", p_alergi='" + p_alergi + '\'' +
                ", p_digoxin='" + p_digoxin + '\'' +
                ", msg='" + msg + '\'' +
                ", p_message='" + p_message + '\'' +
                ", conditions='" + conditions + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
