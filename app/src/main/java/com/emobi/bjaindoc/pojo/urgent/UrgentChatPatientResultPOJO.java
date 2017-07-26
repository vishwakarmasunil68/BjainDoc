package com.emobi.bjaindoc.pojo.urgent;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 20-03-2017.
 */

public class UrgentChatPatientResultPOJO {
    @SerializedName("date")
    String date;
    @SerializedName("result")
    List<UrgentChatResultPOJO> urgentChatResultPOJOList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<UrgentChatResultPOJO> getUrgentChatResultPOJOList() {
        return urgentChatResultPOJOList;
    }

    public void setUrgentChatResultPOJOList(List<UrgentChatResultPOJO> urgentChatResultPOJOList) {
        this.urgentChatResultPOJOList = urgentChatResultPOJOList;
    }

    @Override
    public String toString() {
        return "UrgentChatPatientResultPOJO{" +
                "date='" + date + '\'' +
                ", urgentChatResultPOJOList=" + urgentChatResultPOJOList +
                '}';
    }
}
