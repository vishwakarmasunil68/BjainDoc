package com.emobi.bjaindoc.pojo.urgent;

/**
 * Created by sunil on 20-03-2017.
 */

import com.google.gson.annotations.SerializedName;

public class UrgentChatResultPOJO {
    @SerializedName("u_chat_id")
    String u_chat_id;
    @SerializedName("u_chat_p_id")
    String u_chat_p_id;
    @SerializedName("u_chat_doc_id")
    String u_chat_doc_id;
    @SerializedName("u_date")
    String u_date;
    @SerializedName("u_time")
    String u_time;
    @SerializedName("u_chat_msg")
    String u_chat_msg;
    @SerializedName("u_chat_title")
    String u_chat_title;
    @SerializedName("u_chat_file")
    String u_chat_file;
    @SerializedName("u_direction")
    String u_direction;

    public UrgentChatResultPOJO(String u_chat_id, String u_chat_p_id, String u_chat_doc_id, String u_date, String u_time, String u_chat_msg, String u_chat_title, String u_chat_file, String u_direction) {
        this.u_chat_id = u_chat_id;
        this.u_chat_p_id = u_chat_p_id;
        this.u_chat_doc_id = u_chat_doc_id;
        this.u_date = u_date;
        this.u_time = u_time;
        this.u_chat_msg = u_chat_msg;
        this.u_chat_title = u_chat_title;
        this.u_chat_file = u_chat_file;
        this.u_direction = u_direction;
    }
    public UrgentChatResultPOJO(){

    }

    public String getU_chat_id() {
        return u_chat_id;
    }

    public void setU_chat_id(String u_chat_id) {
        this.u_chat_id = u_chat_id;
    }

    public String getU_chat_p_id() {
        return u_chat_p_id;
    }

    public void setU_chat_p_id(String u_chat_p_id) {
        this.u_chat_p_id = u_chat_p_id;
    }

    public String getU_chat_doc_id() {
        return u_chat_doc_id;
    }

    public void setU_chat_doc_id(String u_chat_doc_id) {
        this.u_chat_doc_id = u_chat_doc_id;
    }

    public String getU_date() {
        return u_date;
    }

    public void setU_date(String u_date) {
        this.u_date = u_date;
    }

    public String getU_time() {
        return u_time;
    }

    public void setU_time(String u_time) {
        this.u_time = u_time;
    }

    public String getU_chat_msg() {
        return u_chat_msg;
    }

    public void setU_chat_msg(String u_chat_msg) {
        this.u_chat_msg = u_chat_msg;
    }

    public String getU_chat_title() {
        return u_chat_title;
    }

    public void setU_chat_title(String u_chat_title) {
        this.u_chat_title = u_chat_title;
    }

    public String getU_chat_file() {
        return u_chat_file;
    }

    public void setU_chat_file(String u_chat_file) {
        this.u_chat_file = u_chat_file;
    }

    public String getU_direction() {
        return u_direction;
    }

    public void setU_direction(String u_direction) {
        this.u_direction = u_direction;
    }

    @Override
    public String toString() {
        return "UrgentChatResultPOJO{" +
                "u_chat_id='" + u_chat_id + '\'' +
                ", u_chat_p_id='" + u_chat_p_id + '\'' +
                ", u_chat_doc_id='" + u_chat_doc_id + '\'' +
                ", u_date='" + u_date + '\'' +
                ", u_time='" + u_time + '\'' +
                ", u_chat_msg='" + u_chat_msg + '\'' +
                ", u_chat_title='" + u_chat_title + '\'' +
                ", u_chat_file='" + u_chat_file + '\'' +
                ", u_direction='" + u_direction + '\'' +
                '}';
    }
}
