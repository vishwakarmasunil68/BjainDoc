package com.emobi.bjaindoc.pojo.appointment;

import com.emobi.bjaindoc.pojo.allAppointment.AllAppointmentResultPOJO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 23-03-2017.
 */

public class AppointmentResultPOJO {
    @SerializedName("date")
    String date;
    @SerializedName("result")
    List<AllAppointmentResultPOJO> list_appointments;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<AllAppointmentResultPOJO> getList_appointments() {
        return list_appointments;
    }

    public void setList_appointments(List<AllAppointmentResultPOJO> list_appointments) {
        this.list_appointments = list_appointments;
    }

    @Override
    public String toString() {
        return "AppointmentResultPOJO{" +
                "date='" + date + '\'' +
                ", list_appointments=" + list_appointments +
                '}';
    }
}
