package com.emobi.bjaindoc.pojo.urgentchatpatient;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 20-03-2017.
 */

public class UrgentPatientPOJO implements Serializable {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<UrgentPatientResultPOJO> urgentPatientResultPOJOs;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<UrgentPatientResultPOJO> getUrgentPatientResultPOJOs() {
        return urgentPatientResultPOJOs;
    }

    public void setUrgentPatientResultPOJOs(List<UrgentPatientResultPOJO> urgentPatientResultPOJOs) {
        this.urgentPatientResultPOJOs = urgentPatientResultPOJOs;
    }

    @Override
    public String toString() {
        return "UrgentPatientPOJO{" +
                "success='" + success + '\'' +
                ", urgentPatientResultPOJOs=" + urgentPatientResultPOJOs +
                '}';
    }
}
