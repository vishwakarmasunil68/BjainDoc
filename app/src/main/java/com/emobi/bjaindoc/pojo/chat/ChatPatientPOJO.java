package com.emobi.bjaindoc.pojo.chat;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 15-03-2017.
 */

public class ChatPatientPOJO  implements Serializable {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<ChatPatientResultPOJO> list_chat_patient_result_pojo;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ChatPatientResultPOJO> getList_chat_patient_result_pojo() {
        return list_chat_patient_result_pojo;
    }

    public void setList_chat_patient_result_pojo(List<ChatPatientResultPOJO> list_chat_patient_result_pojo) {
        this.list_chat_patient_result_pojo = list_chat_patient_result_pojo;
    }

    @Override
    public String toString() {
        return "ChatPatientPOJO{" +
                "success='" + success + '\'' +
                ", list_chat_patient_result_pojo=" + list_chat_patient_result_pojo +
                '}';
    }
}
