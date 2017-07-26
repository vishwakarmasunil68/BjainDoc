package com.emobi.bjaindoc.pojo.appointment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 23-03-2017.
 */

public class PastAppointmentPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("data")
    List<PastAppointmentResultPOJO> pastAppointmentResultPOJO;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<PastAppointmentResultPOJO> getPastAppointmentResultPOJO() {
        return pastAppointmentResultPOJO;
    }

    public void setPastAppointmentResultPOJO(List<PastAppointmentResultPOJO> pastAppointmentResultPOJO) {
        this.pastAppointmentResultPOJO = pastAppointmentResultPOJO;
    }

    @Override
    public String toString() {
        return "PastAppointmentPOJO{" +
                "success='" + success + '\'' +
                ", pastAppointmentResultPOJO=" + pastAppointmentResultPOJO +
                '}';
    }
}
