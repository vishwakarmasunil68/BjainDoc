package com.emobi.bjaindoc.pojo.medication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 17-03-2017.
 */

public class MedicationPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<MedicationResultPOJO> list_medication;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<MedicationResultPOJO> getList_medication() {
        return list_medication;
    }

    public void setList_medication(List<MedicationResultPOJO> list_medication) {
        this.list_medication = list_medication;
    }

    @Override
    public String toString() {
        return "MedicationPOJO{" +
                "success='" + success + '\'' +
                ", list_medication=" + list_medication +
                '}';
    }
}
