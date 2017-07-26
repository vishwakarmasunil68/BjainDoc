package com.emobi.bjaindoc.pojo.appointment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 23-03-2017.
 */

public class UpcomingAppointmentPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("data")
    List<UpcomingAppointmentResultPOJO> upcomingAppointmentResultPOJO;


    @Override
    public String toString() {
        return "UpcomingAppointmentPOJO{" +
                "success='" + success + '\'' +
                ", upcomingAppointmentResultPOJO=" + upcomingAppointmentResultPOJO +
                '}';
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<UpcomingAppointmentResultPOJO> getUpcomingAppointmentResultPOJO() {
        return upcomingAppointmentResultPOJO;
    }

    public void setUpcomingAppointmentResultPOJO(List<UpcomingAppointmentResultPOJO> upcomingAppointmentResultPOJO) {
        this.upcomingAppointmentResultPOJO = upcomingAppointmentResultPOJO;
    }
}
