package com.emobi.bjaindoc.pojo.urgent;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 20-03-2017.
 */

public class UrgentChatPatientPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<UrgentChatPatientResultPOJO> urgentChatPatientResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<UrgentChatPatientResultPOJO> getUrgentChatPatientResultPOJOList() {
        return urgentChatPatientResultPOJOList;
    }

    public void setUrgentChatPatientResultPOJOList(List<UrgentChatPatientResultPOJO> urgentChatPatientResultPOJOList) {
        this.urgentChatPatientResultPOJOList = urgentChatPatientResultPOJOList;
    }

    @Override
    public String toString() {
        return "UrgentChatPatientPOJO{" +
                "success='" + success + '\'' +
                ", urgentChatPatientResultPOJOList=" + urgentChatPatientResultPOJOList +
                '}';
    }
}
