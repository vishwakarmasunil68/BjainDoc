package com.emobi.bjaindoc.pojo.member;

/**
 * Created by sunil on 16-03-2017.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class MemberResultPOJO implements Serializable {

    @SerializedName("m_id")
    String m_id;
    @SerializedName("p_id")
    String p_id;
    @SerializedName("doc_id")
    String doc_id;
    @SerializedName("m_name")
    String m_name;
    @SerializedName("m_email")
    String m_email;
    @SerializedName("m_age")
    String m_age;
    @SerializedName("m_bloodgroup")
    String m_bloodgroup;
    @SerializedName("m_weight")
    String m_weight;
    @SerializedName("m_height")
    String m_height;
    @SerializedName("m_mob")
    String m_mob;
    @SerializedName("m_photo")
    String m_photo;
    @SerializedName("m_medication")
    String m_medication;
    @SerializedName("m_alergi")
    String m_alergi;
    @SerializedName("m_digoxin")
    String m_digoxin;
    @SerializedName("m_condition")
    String m_condition;
    @SerializedName("m_description")
    String m_description;


    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_email() {
        return m_email;
    }

    public void setM_email(String m_email) {
        this.m_email = m_email;
    }

    public String getM_age() {
        return m_age;
    }

    public void setM_age(String m_age) {
        this.m_age = m_age;
    }

    public String getM_bloodgroup() {
        return m_bloodgroup;
    }

    public void setM_bloodgroup(String m_bloodgroup) {
        this.m_bloodgroup = m_bloodgroup;
    }

    public String getM_weight() {
        return m_weight;
    }

    public void setM_weight(String m_weight) {
        this.m_weight = m_weight;
    }

    public String getM_height() {
        return m_height;
    }

    public void setM_height(String m_height) {
        this.m_height = m_height;
    }

    public String getM_mob() {
        return m_mob;
    }

    public void setM_mob(String m_mob) {
        this.m_mob = m_mob;
    }

    public String getM_photo() {
        return m_photo;
    }

    public void setM_photo(String m_photo) {
        this.m_photo = m_photo;
    }

    public String getM_medication() {
        return m_medication;
    }

    public void setM_medication(String m_medication) {
        this.m_medication = m_medication;
    }

    public String getM_alergi() {
        return m_alergi;
    }

    public void setM_alergi(String m_alergi) {
        this.m_alergi = m_alergi;
    }

    public String getM_digoxin() {
        return m_digoxin;
    }

    public void setM_digoxin(String m_digoxin) {
        this.m_digoxin = m_digoxin;
    }

    public String getM_condition() {
        return m_condition;
    }

    public void setM_condition(String m_condition) {
        this.m_condition = m_condition;
    }

    public String getM_description() {
        return m_description;
    }

    public void setM_description(String m_description) {
        this.m_description = m_description;
    }

    @Override
    public String toString() {
        return "MemberResultPOJO{" +
                "m_id='" + m_id + '\'' +
                ", p_id='" + p_id + '\'' +
                ", doc_id='" + doc_id + '\'' +
                ", m_name='" + m_name + '\'' +
                ", m_email='" + m_email + '\'' +
                ", m_age='" + m_age + '\'' +
                ", m_bloodgroup='" + m_bloodgroup + '\'' +
                ", m_weight='" + m_weight + '\'' +
                ", m_height='" + m_height + '\'' +
                ", m_mob='" + m_mob + '\'' +
                ", m_photo='" + m_photo + '\'' +
                ", m_medication='" + m_medication + '\'' +
                ", m_alergi='" + m_alergi + '\'' +
                ", m_digoxin='" + m_digoxin + '\'' +
                ", m_condition='" + m_condition + '\'' +
                ", m_description='" + m_description + '\'' +
                '}';
    }
}
