package com.emobi.bjaindoc.pojo.urgentchatpatient;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 04-04-2017.
 */

public class NewUrgentPatientPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<NewUrgentPatientResultPOJO> newUrgentPatientResultPOJOs;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<NewUrgentPatientResultPOJO> getNewUrgentPatientResultPOJOs() {
        return newUrgentPatientResultPOJOs;
    }

    public void setNewUrgentPatientResultPOJOs(List<NewUrgentPatientResultPOJO> newUrgentPatientResultPOJOs) {
        this.newUrgentPatientResultPOJOs = newUrgentPatientResultPOJOs;
    }

    @Override
    public String toString() {
        return "NewUrgentPatientPOJO{" +
                "success='" + success + '\'' +
                ", newUrgentPatientResultPOJOs=" + newUrgentPatientResultPOJOs +
                '}';
    }
}
