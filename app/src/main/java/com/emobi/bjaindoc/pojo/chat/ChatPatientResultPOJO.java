package com.emobi.bjaindoc.pojo.chat;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 15-03-2017.
 */

public class ChatPatientResultPOJO implements Serializable {
    @SerializedName("date")
    String date;
    @SerializedName("result")
    List<ChatResultPOJO> list_chat;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ChatResultPOJO> getList_chat() {
        return list_chat;
    }

    public void setList_chat(List<ChatResultPOJO> list_chat) {
        this.list_chat = list_chat;
    }

    @Override
    public String toString() {
        return "ChatPatientResultPOJO{" +
                "date='" + date + '\'' +
                ", list_chat=" + list_chat +
                '}';
    }
}
