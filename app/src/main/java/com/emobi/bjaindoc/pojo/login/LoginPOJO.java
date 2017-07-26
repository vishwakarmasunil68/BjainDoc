package com.emobi.bjaindoc.pojo.login;

/**
 * Created by sunil on 20-03-2017.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class LoginPOJO implements Serializable{
    @SerializedName("reg_id")
    String reg_id;
    @SerializedName("reg_p_id")
    String reg_p_id;
    @SerializedName("reg_name")
    String reg_name;
    @SerializedName("reg_email")
    String reg_email;
    @SerializedName("reg_mob")
    String reg_mob;
    @SerializedName("reg_pass")
    String reg_pass;
    @SerializedName("reg_cpass")
    String reg_cpass;
    @SerializedName("reg_dob")
    String reg_dob;
    @SerializedName("department")
    String department;
    @SerializedName("clinic_address")
    String clinic_address;
    @SerializedName("availability")
    String availability;
    @SerializedName("designation")
    String designation;
    @SerializedName("degree")
    String degree;
    @SerializedName("specialist")
    String specialist;
    @SerializedName("photo")
    String photo;
    @SerializedName("gcm_id")
    String gcm_id;
    @SerializedName("device_token")
    String device_token;
    @SerializedName("un_success_msg")
    String un_success_msg;
    @SerializedName("d_message")
    String d_message;
    @SerializedName("status")
    String status;

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public String getReg_p_id() {
        return reg_p_id;
    }

    public void setReg_p_id(String reg_p_id) {
        this.reg_p_id = reg_p_id;
    }

    public String getReg_name() {
        return reg_name;
    }

    public void setReg_name(String reg_name) {
        this.reg_name = reg_name;
    }

    public String getReg_email() {
        return reg_email;
    }

    public void setReg_email(String reg_email) {
        this.reg_email = reg_email;
    }

    public String getReg_mob() {
        return reg_mob;
    }

    public void setReg_mob(String reg_mob) {
        this.reg_mob = reg_mob;
    }

    public String getReg_pass() {
        return reg_pass;
    }

    public void setReg_pass(String reg_pass) {
        this.reg_pass = reg_pass;
    }

    public String getReg_cpass() {
        return reg_cpass;
    }

    public void setReg_cpass(String reg_cpass) {
        this.reg_cpass = reg_cpass;
    }

    public String getReg_dob() {
        return reg_dob;
    }

    public void setReg_dob(String reg_dob) {
        this.reg_dob = reg_dob;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getClinic_address() {
        return clinic_address;
    }

    public void setClinic_address(String clinic_address) {
        this.clinic_address = clinic_address;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGcm_id() {
        return gcm_id;
    }

    public void setGcm_id(String gcm_id) {
        this.gcm_id = gcm_id;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getUn_success_msg() {
        return un_success_msg;
    }

    public void setUn_success_msg(String un_success_msg) {
        this.un_success_msg = un_success_msg;
    }

    public String getD_message() {
        return d_message;
    }

    public void setD_message(String d_message) {
        this.d_message = d_message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginPOJO{" +
                "reg_id='" + reg_id + '\'' +
                ", reg_p_id='" + reg_p_id + '\'' +
                ", reg_name='" + reg_name + '\'' +
                ", reg_email='" + reg_email + '\'' +
                ", reg_mob='" + reg_mob + '\'' +
                ", reg_pass='" + reg_pass + '\'' +
                ", reg_cpass='" + reg_cpass + '\'' +
                ", reg_dob='" + reg_dob + '\'' +
                ", department='" + department + '\'' +
                ", clinic_address='" + clinic_address + '\'' +
                ", availability='" + availability + '\'' +
                ", designation='" + designation + '\'' +
                ", degree='" + degree + '\'' +
                ", specialist='" + specialist + '\'' +
                ", photo='" + photo + '\'' +
                ", gcm_id='" + gcm_id + '\'' +
                ", device_token='" + device_token + '\'' +
                ", un_success_msg='" + un_success_msg + '\'' +
                ", d_message='" + d_message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
