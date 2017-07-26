package com.emobi.bjaindoc.pojo.member;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 16-03-2017.
 */

public class MemberUpdatePOJO {
    @SerializedName("success")
    String success;
    @SerializedName("message")
    String message;
    @SerializedName("result")
    MemberResultPOJO memberResultPOJO;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MemberResultPOJO getMemberResultPOJO() {
        return memberResultPOJO;
    }

    public void setMemberResultPOJO(MemberResultPOJO memberResultPOJO) {
        this.memberResultPOJO = memberResultPOJO;
    }

    @Override
    public String toString() {
        return "MemberUpdatePOJO{" +
                "success='" + success + '\'' +
                ", message='" + message + '\'' +
                ", memberResultPOJO=" + memberResultPOJO +
                '}';
    }
}
