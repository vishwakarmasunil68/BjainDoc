package com.emobi.bjaindoc.pojo.appointment;

import com.emobi.bjaindoc.pojo.allAppointment.AllAppointmentResultPOJO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 23-03-2017.
 */

public class PastAppointmentResultPOJO {
    @SerializedName("date")
    String date;
    @SerializedName("result")
    List<AllAppointmentResultPOJO> list_upcoming_appointments;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<AllAppointmentResultPOJO> getList_upcoming_appointments() {
        return list_upcoming_appointments;
    }

    public void setList_upcoming_appointments(List<AllAppointmentResultPOJO> list_upcoming_appointments) {
        this.list_upcoming_appointments = list_upcoming_appointments;
    }

    @Override
    public String toString() {
        return "PastAppointmentResultPOJO{" +
                "date='" + date + '\'' +
                ", list_upcoming_appointments=" + list_upcoming_appointments +
                '}';
    }
}
