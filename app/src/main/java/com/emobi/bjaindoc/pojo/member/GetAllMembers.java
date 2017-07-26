package com.emobi.bjaindoc.pojo.member;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 16-03-2017.
 */

public class GetAllMembers implements Serializable{
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<MemberResultPOJO> memberResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<MemberResultPOJO> getMemberResultPOJOList() {
        return memberResultPOJOList;
    }

    public void setMemberResultPOJOList(List<MemberResultPOJO> memberResultPOJOList) {
        this.memberResultPOJOList = memberResultPOJOList;
    }

    @Override
    public String toString() {
        return "GetAllMembers{" +
                "success='" + success + '\'' +
                ", memberResultPOJOList=" + memberResultPOJOList +
                '}';
    }
}
