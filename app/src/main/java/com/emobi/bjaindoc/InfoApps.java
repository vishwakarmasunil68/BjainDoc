package com.emobi.bjaindoc;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;

import java.util.ArrayList;

/**
 * Created by emobi5 on 4/15/2016.
 */
public class InfoApps {
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    private String pass;
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    private String email_id;

    public String getP_medication() {
        return p_medication;
    }

    public void setP_medication(String p_medication) {
        this.p_medication = p_medication;
    }

    public String getAlergi() {
        return alergi;
    }

    public void setAlergi(String alergi) {
        this.alergi = alergi;
    }

    public String getP_digoxin() {
        return p_digoxin;
    }

    public void setP_digoxin(String p_digoxin) {
        this.p_digoxin = p_digoxin;
    }

    private String p_medication;
    private String alergi;
    private String p_digoxin;

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    private String Age;

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    private String Height;

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    private String Weight;

    public String getBg() {
        return Bg;
    }

    public void setBg(String bg) {
        Bg = bg;
    }

    private String Bg;
    private String Id;
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap image;
    public String getDataAdd() {
        return DataAdd;
    }

    public void setDataAdd(String dataAdd) {
        DataAdd = dataAdd;
    }

    private String DataAdd;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String number;

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String pro_name;

    public String getInvo_date() {
        return invo_date;
    }

    public void setInvo_date(String invo_date) {
        this.invo_date = invo_date;
    }

    public String invo_date;

    public String getN_time() {
        return n_time;
    }

    public void setN_time(String n_time) {
        this.n_time = n_time;
    }

    public String n_time;
    public String getPro_quantity() {
        return pro_quantity;
    }

    public void setPro_quantity(String pro_quantity) {
        this.pro_quantity = pro_quantity;
    }

    public String pro_quantity;
    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String staff_name;
    public String getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(String bill_amount) {
        this.bill_amount = bill_amount;
    }

    public String getDue_amount() {
        return due_amount;
    }

    public void setDue_amount(String due_amount) {
        this.due_amount = due_amount;
    }

    public String due_amount;
    public String bill_amount;

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String paid_amount;

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String Designation;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String status;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    boolean selected = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int time;

    public String getAppotime() {
        return appotime;
    }

    public void setAppotime(String appotime) {
        this.appotime = appotime;
    }

    public String appotime;
    public String getAppname() {
        return Appname;
    }

    public void setAppname(String appname) {
        Appname = appname;
    }

    private String Appname;

    public Bitmap getAppbmp() {
        return Appbmp;
    }

    public void setAppbmp(Bitmap appbmp) {
        Appbmp = appbmp;
    }

    public Bitmap Appbmp;

    public StringBuilder getData() {
        return data;
    }

    public void setData(StringBuilder data) {
        this.data = data;
    }

    private StringBuilder data;

    public Address getDataadd() {
        return dataadd;
    }

    public void setDataadd(Address dataadd) {
        this.dataadd = dataadd;
    }

    private Address dataadd;

    public ArrayList<Address> getArraylisAddr() {
        return arraylisAddr;
    }

    public void setArraylisAddr(ArrayList<Address> arraylisAddr) {
        this.arraylisAddr = arraylisAddr;
    }

    private ArrayList<Address> arraylisAddr;
    public Drawable getAppicon() {
        return appicon;
    }

    public void setAppicon(Drawable appicon) {
        this.appicon = appicon;
    }

    private Drawable appicon;

    public String getPackagenameApps() {
        return PackagenameApps;
    }

    public void setPackagenameApps(String packagenameApps) {
        PackagenameApps = packagenameApps;
    }

    private String PackagenameApps;
    private String device_token;

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    @Override
    public String toString() {
        return "InfoApps{" +
                "pass='" + pass + '\'' +
                ", email_id='" + email_id + '\'' +
                ", p_medication='" + p_medication + '\'' +
                ", alergi='" + alergi + '\'' +
                ", p_digoxin='" + p_digoxin + '\'' +
                ", Age='" + Age + '\'' +
                ", Height='" + Height + '\'' +
                ", Weight='" + Weight + '\'' +
                ", Bg='" + Bg + '\'' +
                ", Id='" + Id + '\'' +
                ", image=" + image +
                ", DataAdd='" + DataAdd + '\'' +
                ", number='" + number + '\'' +
                ", pro_name='" + pro_name + '\'' +
                ", invo_date='" + invo_date + '\'' +
                ", n_time='" + n_time + '\'' +
                ", pro_quantity='" + pro_quantity + '\'' +
                ", staff_name='" + staff_name + '\'' +
                ", due_amount='" + due_amount + '\'' +
                ", bill_amount='" + bill_amount + '\'' +
                ", paid_amount='" + paid_amount + '\'' +
                ", Designation='" + Designation + '\'' +
                ", status='" + status + '\'' +
                ", selected=" + selected +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", appotime='" + appotime + '\'' +
                ", Appname='" + Appname + '\'' +
                ", Appbmp=" + Appbmp +
                ", data=" + data +
                ", dataadd=" + dataadd +
                ", arraylisAddr=" + arraylisAddr +
                ", appicon=" + appicon +
                ", PackagenameApps='" + PackagenameApps + '\'' +
                ", device_token='" + device_token + '\'' +
                '}';
    }
//
//    @Override
//    public String toString() {
//        return "Name:-"+name+",Number:-"+number;
//    }
}
