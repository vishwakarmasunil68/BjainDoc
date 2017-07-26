package com.emobi.bjaindoc.pojo.urgentchatpatient;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 04-04-2017.
 */

public class NewUrgentchatPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<NewUrgentChatResultPOJO> newUrgentChatResultPOJOs;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<NewUrgentChatResultPOJO> getNewUrgentChatResultPOJOs() {
        return newUrgentChatResultPOJOs;
    }

    public void setNewUrgentChatResultPOJOs(List<NewUrgentChatResultPOJO> newUrgentChatResultPOJOs) {
        this.newUrgentChatResultPOJOs = newUrgentChatResultPOJOs;
    }

    @Override
    public String toString() {
        return "NewUrgentchatPOJO{" +
                "success='" + success + '\'' +
                ", newUrgentChatResultPOJOs=" + newUrgentChatResultPOJOs +
                '}';
    }
}
