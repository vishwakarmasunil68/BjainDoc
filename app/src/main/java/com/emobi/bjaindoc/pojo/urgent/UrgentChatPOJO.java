package com.emobi.bjaindoc.pojo.urgent;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 20-03-2017.
 */

public class UrgentChatPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<UrgentChatResultPOJO> urgentChatResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<UrgentChatResultPOJO> getUrgentChatResultPOJOList() {
        return urgentChatResultPOJOList;
    }

    public void setUrgentChatResultPOJOList(List<UrgentChatResultPOJO> urgentChatResultPOJOList) {
        this.urgentChatResultPOJOList = urgentChatResultPOJOList;
    }

    @Override
    public String toString() {
        return "UrgentChatPOJO{" +
                "success='" + success + '\'' +
                ", urgentChatResultPOJOList=" + urgentChatResultPOJOList +
                '}';
    }
}
