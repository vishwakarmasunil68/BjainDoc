package com.emobi.bjaindoc.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 10-01-2017.
 */

public class ClinicInfoPOJO implements Serializable{
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<ClinicInfoPOJOResult> result;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ClinicInfoPOJOResult> getResult() {
        return result;
    }

    public void setResult(List<ClinicInfoPOJOResult> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ClinicInfoPOJO{" +
                "success='" + success + '\'' +
                ", result=" + result.toString() +
                '}';
    }
}
