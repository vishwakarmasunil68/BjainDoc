package com.emobi.bjaindoc.pojo.urgentchatpatient;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 04-04-2017.
 */

public class NewUrgentPatientResultPOJO {
    @SerializedName("date")
    String date;
    @SerializedName("result")
    List<NewUrgentChatResultPOJO> newUrgentChatResultPOJOs;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<NewUrgentChatResultPOJO> getNewUrgentChatResultPOJOs() {
        return newUrgentChatResultPOJOs;
    }

    public void setNewUrgentChatResultPOJOs(List<NewUrgentChatResultPOJO> newUrgentChatResultPOJOs) {
        this.newUrgentChatResultPOJOs = newUrgentChatResultPOJOs;
    }

    @Override
    public String toString() {
        return "NewUrgentPatientResultPOJO{" +
                "date='" + date + '\'' +
                ", newUrgentChatResultPOJOs=" + newUrgentChatResultPOJOs +
                '}';
    }
}
