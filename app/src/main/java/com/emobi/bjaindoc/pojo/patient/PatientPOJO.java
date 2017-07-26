package com.emobi.bjaindoc.pojo.patient;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 16-03-2017.
 */

public class PatientPOJO implements Serializable{
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<PatientResultPOJO> list_patients;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<PatientResultPOJO> getList_patients() {
        return list_patients;
    }

    public void setList_patients(List<PatientResultPOJO> list_patients) {
        this.list_patients = list_patients;
    }

    @Override
    public String toString() {
        return "PatientPOJO{" +
                "success='" + success + '\'' +
                ", list_patients=" + list_patients +
                '}';
    }
}
