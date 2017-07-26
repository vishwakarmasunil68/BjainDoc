package com.emobi.bjaindoc.pojo.chat;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 15-03-2017.
 */

public class ChatPOJO implements Serializable {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<ChatResultPOJO> chatResultPOJOs;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ChatResultPOJO> getChatResultPOJOs() {
        return chatResultPOJOs;
    }

    public void setChatResultPOJOs(List<ChatResultPOJO> chatResultPOJOs) {
        this.chatResultPOJOs = chatResultPOJOs;
    }

    @Override
    public String toString() {
        return "ChatPOJO{" +
                "success='" + success + '\'' +
                ", chatResultPOJOs=" + chatResultPOJOs +
                '}';
    }
}
