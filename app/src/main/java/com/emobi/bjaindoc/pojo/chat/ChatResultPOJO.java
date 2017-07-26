package com.emobi.bjaindoc.pojo.chat;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 15-03-2017.
 */

public class ChatResultPOJO implements Serializable{
    @SerializedName("chat_id")
    String chat_id;
    @SerializedName("chat_p_id")
    String chat_p_id;
    @SerializedName("chat_doc_id")
    String chat_doc_id;
    @SerializedName("chat_m_id")
    String chat_m_id;
    @SerializedName("date")
    String date;
    @SerializedName("time")
    String time;
    @SerializedName("chat_msg")
    String chat_msg;
    @SerializedName("chat_title")
    String chat_title;
    @SerializedName("chat_file")
    String chat_file;
    @SerializedName("thumb_file")
    String thumb_file;
    @SerializedName("message_type")
    String message_type;
    @SerializedName("direction")
    String direction;

    public ChatResultPOJO(String chat_id, String chat_p_id, String chat_doc_id, String chat_m_id, String date, String time, String chat_msg, String chat_title, String chat_file, String thumb_file, String message_type, String direction) {
        this.chat_id = chat_id;
        this.chat_p_id = chat_p_id;
        this.chat_doc_id = chat_doc_id;
        this.chat_m_id = chat_m_id;
        this.date = date;
        this.time = time;
        this.chat_msg = chat_msg;
        this.chat_title = chat_title;
        this.chat_file = chat_file;
        this.thumb_file = thumb_file;
        this.message_type = message_type;
        this.direction = direction;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getChat_p_id() {
        return chat_p_id;
    }

    public void setChat_p_id(String chat_p_id) {
        this.chat_p_id = chat_p_id;
    }

    public String getChat_doc_id() {
        return chat_doc_id;
    }

    public void setChat_doc_id(String chat_doc_id) {
        this.chat_doc_id = chat_doc_id;
    }

    public String getChat_m_id() {
        return chat_m_id;
    }

    public void setChat_m_id(String chat_m_id) {
        this.chat_m_id = chat_m_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChat_msg() {
        return chat_msg;
    }

    public void setChat_msg(String chat_msg) {
        this.chat_msg = chat_msg;
    }

    public String getChat_title() {
        return chat_title;
    }

    public void setChat_title(String chat_title) {
        this.chat_title = chat_title;
    }

    public String getChat_file() {
        return chat_file;
    }

    public void setChat_file(String chat_file) {
        this.chat_file = chat_file;
    }

    public String getThumb_file() {
        return thumb_file;
    }

    public void setThumb_file(String thumb_file) {
        this.thumb_file = thumb_file;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "ChatResultPOJO{" +
                "chat_id='" + chat_id + '\'' +
                ", chat_p_id='" + chat_p_id + '\'' +
                ", chat_doc_id='" + chat_doc_id + '\'' +
                ", chat_m_id='" + chat_m_id + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", chat_msg='" + chat_msg + '\'' +
                ", chat_title='" + chat_title + '\'' +
                ", chat_file='" + chat_file + '\'' +
                ", thumb_file='" + thumb_file + '\'' +
                ", message_type='" + message_type + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
