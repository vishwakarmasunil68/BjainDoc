package com.emobi.bjaindoc;

/**
 * Created by emobi8 on 9/27/2016.
 */
public class PrecriptionPOJO {
    String p_id;
    String pre_p_id;
    String pre_doc_id;
    String pre_date;
    String pre_message;
    String pre_medicine;
    String pre_file;
    String pre_status;

    public String getP_id() {
        return p_id;
    }

    public String getPre_date() {
        return pre_date;
    }

    public String getPre_doc_id() {
        return pre_doc_id;
    }

    public String getPre_file() {
        return pre_file;
    }

    public String getPre_medicine() {
        return pre_medicine;
    }

    public String getPre_message() {
        return pre_message;
    }

    public String getPre_p_id() {
        return pre_p_id;
    }

    public String getPre_status() {
        return pre_status;
    }

    public void setPre_message(String pre_message) {
        this.pre_message = pre_message;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public void setPre_date(String pre_date) {
        this.pre_date = pre_date;
    }

    public void setPre_doc_id(String pre_doc_id) {
        this.pre_doc_id = pre_doc_id;
    }

    public void setPre_file(String pre_file) {
        this.pre_file = pre_file;
    }

    public void setPre_medicine(String pre_medicine) {
        this.pre_medicine = pre_medicine;
    }

    public void setPre_p_id(String pre_p_id) {
        this.pre_p_id = pre_p_id;
    }

    public void setPre_status(String pre_status) {
        this.pre_status = pre_status;
    }
}
