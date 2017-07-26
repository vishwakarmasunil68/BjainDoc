package com.emobi.bjaindoc.pojo.urgentchatpatient;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 03-04-2017.
 */

public class NewUrgentChatResultPOJO {
    @SerializedName("u_chat_id")
    String u_chat_id;
    @SerializedName("u_chat_p_id")
    String u_chat_p_id;
    @SerializedName("u_chat_doc_id")
    String u_chat_doc_id;
    @SerializedName("u_chat_date")
    String u_chat_date;
    @SerializedName("u_chat_time")
    String u_chat_time;
    @SerializedName("u_chat_msg")
    String u_chat_msg;
    @SerializedName("u_chat_title")
    String u_chat_title;
    @SerializedName("u_chat_file")
    String u_chat_file;
    @SerializedName("u_thumb_file")
    String u_thumb_file;
    @SerializedName("u_message_type")
    String u_message_type;
    @SerializedName("u_direction")
    String u_direction;

    public NewUrgentChatResultPOJO(String u_chat_id, String u_chat_p_id, String u_chat_doc_id, String u_chat_date, String u_chat_time, String u_chat_msg, String u_chat_title, String u_chat_file, String u_thumb_file, String u_message_type, String u_direction) {
        this.u_chat_id = u_chat_id;
        this.u_chat_p_id = u_chat_p_id;
        this.u_chat_doc_id = u_chat_doc_id;
        this.u_chat_date = u_chat_date;
        this.u_chat_time = u_chat_time;
        this.u_chat_msg = u_chat_msg;
        this.u_chat_title = u_chat_title;
        this.u_chat_file = u_chat_file;
        this.u_thumb_file = u_thumb_file;
        this.u_message_type = u_message_type;
        this.u_direction = u_direction;
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

    public String getU_chat_date() {
        return u_chat_date;
    }

    public void setU_chat_date(String u_chat_date) {
        this.u_chat_date = u_chat_date;
    }

    public String getU_chat_time() {
        return u_chat_time;
    }

    public void setU_chat_time(String u_chat_time) {
        this.u_chat_time = u_chat_time;
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

    public String getU_thumb_file() {
        return u_thumb_file;
    }

    public void setU_thumb_file(String u_thumb_file) {
        this.u_thumb_file = u_thumb_file;
    }

    public String getU_message_type() {
        return u_message_type;
    }

    public void setU_message_type(String u_message_type) {
        this.u_message_type = u_message_type;
    }

    public String getU_direction() {
        return u_direction;
    }

    public void setU_direction(String u_direction) {
        this.u_direction = u_direction;
    }

    @Override
    public String toString() {
        return "NewUrgentChatResultPOJO{" +
                "u_chat_id='" + u_chat_id + '\'' +
                ", u_chat_p_id='" + u_chat_p_id + '\'' +
                ", u_chat_doc_id='" + u_chat_doc_id + '\'' +
                ", u_chat_date='" + u_chat_date + '\'' +
                ", u_chat_time='" + u_chat_time + '\'' +
                ", u_chat_msg='" + u_chat_msg + '\'' +
                ", u_chat_title='" + u_chat_title + '\'' +
                ", u_chat_file='" + u_chat_file + '\'' +
                ", u_thumb_file='" + u_thumb_file + '\'' +
                ", u_message_type='" + u_message_type + '\'' +
                ", u_direction='" + u_direction + '\'' +
                '}';
    }
}
