package com.emobi.bjaindoc.pojo.allAppointment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 27-02-2017.
 */

public class AllAppointmentPOJO implements Serializable{
    @SerializedName("Success")
    String success;
    @SerializedName("Result")
    List<AllAppointmentResultPOJO> list_appointments;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<AllAppointmentResultPOJO> getList_appointments() {
        return list_appointments;
    }

    public void setList_appointments(List<AllAppointmentResultPOJO> list_appointments) {
        this.list_appointments = list_appointments;
    }

    @Override
    public String toString() {
        return "AllAppointmentPOJO{" +
                "success='" + success + '\'' +
                ", list_appointments=" + list_appointments.toString() +
                '}';
    }
}
