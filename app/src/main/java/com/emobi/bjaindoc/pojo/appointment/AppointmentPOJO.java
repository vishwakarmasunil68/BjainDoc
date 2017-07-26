package com.emobi.bjaindoc.pojo.appointment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 23-03-2017.
 */

public class AppointmentPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("upcoming")
    UpcomingAppointmentPOJO upcomingAppointmentPOJO;
    @SerializedName("past")
    PastAppointmentPOJO pastAppointmentPOJO;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public UpcomingAppointmentPOJO getUpcomingAppointmentPOJO() {
        return upcomingAppointmentPOJO;
    }

    public void setUpcomingAppointmentPOJO(UpcomingAppointmentPOJO upcomingAppointmentPOJO) {
        this.upcomingAppointmentPOJO = upcomingAppointmentPOJO;
    }

    public PastAppointmentPOJO getPastAppointmentPOJO() {
        return pastAppointmentPOJO;
    }

    public void setPastAppointmentPOJO(PastAppointmentPOJO pastAppointmentPOJO) {
        this.pastAppointmentPOJO = pastAppointmentPOJO;
    }

    @Override
    public String toString() {
        return "AppointmentPOJO{" +
                "success='" + success + '\'' +
                ", upcomingAppointmentPOJO=" + upcomingAppointmentPOJO +
                ", pastAppointmentPOJO=" + pastAppointmentPOJO +
                '}';
    }
}
