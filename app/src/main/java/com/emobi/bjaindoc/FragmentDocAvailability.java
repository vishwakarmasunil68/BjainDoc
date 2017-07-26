package com.emobi.bjaindoc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emobi.bjaindoc.pojo.ClinicInfoPOJO;
import com.emobi.bjaindoc.pojo.ClinicInfoPOJOResult;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import database.PreferenceData;

/**
 * Created by sunil on 10-01-2017.
 */

public class FragmentDocAvailability extends Fragment implements WebServicesCallBack{
    RecyclerView rv_clinic_info;
    ImageView scrollableimg;
    LinearLayout ll_scroll;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_doc_availability,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_clinic_info= (RecyclerView) view.findViewById(R.id.rv_clinic_info);
        ll_scroll= (LinearLayout) view.findViewById(R.id.ll_scroll);

        GetALlClinicInfo();
    }

    public void GetALlClinicInfo(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("doctor_id", PreferenceData.getId(getActivity())));
        new WebServiceBase(nameValuePairs,getActivity(), this, "api1").execute("http://www.bjain.com/doctor/doctor_cliniclist.php");

    }

    @Override
    public void onGetMsg(String[] msg) {
        String api=msg[0];
        String response=msg[1];
        switch (api){
            case "api1":parseResponse(response);
                break;
        }
    }
    public void parseResponse(String response){
        Gson gson=new Gson();
        ClinicInfoPOJO pojo = gson.fromJson(response, ClinicInfoPOJO.class);
        if(pojo!=null){
            if(pojo.getResult()!=null&&pojo.getResult().size()>0){
//                HorizontalAdapter adapter = new HorizontalAdapter(getActivity(),getActivity(), pojo.getResult());

//                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//                rv_clinic_info.setHasFixedSize(true);
//                rv_clinic_info.setLayoutManager(layoutManager);
//
//                rv_clinic_info.setAdapter(adapter);
                inflateTimingList(pojo.getResult());
            }
        }

    }


    public void inflateTimingList(final List<ClinicInfoPOJOResult> horizontalList){
        ll_scroll.removeAllViews();
        for (int i = 0; i < horizontalList.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_clinic_info, null);

            CardView cv_clinic= (CardView) view.findViewById(R.id.cv_clinic);
            TextView tv_clinic_name= (TextView) view.findViewById(R.id.tv_clinic_name);
            LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.linearlayout);
            TextView tv_clinic_address= (TextView) view.findViewById(R.id.tv_clinic_address);
            ImageView scrollableimg= (ImageView) view.findViewById(R.id.scrollableimg);
            TextView tv_city= (TextView) view.findViewById(R.id.tv_city);
            TextView tv_pin_code= (TextView) view.findViewById(R.id.tv_pin_code);
            TextView tv_degree= (TextView) view.findViewById(R.id.tv_degree);
            TextView tv_specialist= (TextView) view.findViewById(R.id.tv_specialist);
            TextView tv_mon_mrng_time= (TextView) view.findViewById(R.id.tv_mon_mrng_time);
            TextView tv_mon_eve_time= (TextView) view.findViewById(R.id.tv_mon_eve_time);
            TextView tv_tue_mrng_time= (TextView) view.findViewById(R.id.tv_tue_mrng_time);
            TextView tv_tue_eve_time= (TextView) view.findViewById(R.id.tv_tue_eve_time);
            TextView tv_wed_mrng_time= (TextView) view.findViewById(R.id.tv_wed_mrng_time);
            TextView tv_thu_mrng_time= (TextView) view.findViewById(R.id.tv_thu_mrng_time);
            TextView tv_thu_eve_time= (TextView) view.findViewById(R.id.tv_thu_eve_time);
            TextView tv_wed_eve_time= (TextView) view.findViewById(R.id.tv_wed_eve_time);
            TextView tv_fri_mrng_time= (TextView) view.findViewById(R.id.tv_fri_mrng_time);
            TextView tv_fri_eve_time= (TextView) view.findViewById(R.id.tv_fri_eve_time);
            TextView tv_sat_mrng_time= (TextView) view.findViewById(R.id.tv_sat_mrng_time);
            TextView tv_sat_eve_time= (TextView) view.findViewById(R.id.tv_sat_eve_time);
            TextView tv_sun_mrng_time= (TextView) view.findViewById(R.id.tv_sun_mrng_time);
            TextView tv_sun_eve_time= (TextView) view.findViewById(R.id.tv_sun_eve_time);
            LinearLayout ll_mon= (LinearLayout) view.findViewById(R.id.ll_mon);
            LinearLayout ll_tue= (LinearLayout) view.findViewById(R.id.ll_tue);
            LinearLayout ll_wed= (LinearLayout) view.findViewById(R.id.ll_wed);
            LinearLayout ll_thu= (LinearLayout) view.findViewById(R.id.ll_thu);
            LinearLayout ll_fri= (LinearLayout) view.findViewById(R.id.ll_fri);
            LinearLayout ll_sat= (LinearLayout) view.findViewById(R.id.ll_sat);
            LinearLayout ll_sun= (LinearLayout) view.findViewById(R.id.ll_sun);
            LinearLayout ll_showedit= (LinearLayout) view.findViewById(R.id.ll_showedit);


            ClinicInfoPOJOResult pojo=horizontalList.get(i);
            scrollableimg.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            tv_clinic_name.setText(pojo.getClinic_name());
            tv_clinic_address.setText(pojo.getClinic_address());
            tv_city.setText(pojo.getClinic_city());
            tv_pin_code.setText(pojo.getClinic_pincode());
            tv_degree.setText(pojo.getClinic_degree());
            tv_specialist.setText(pojo.getClinic_specialist());
            ll_showedit.setVisibility(View.GONE);

            String mon_mrng_time=ValidateString(pojo.getMon_mor_starttime())+"-"+ValidateString(pojo.getMon_mor_endtime());
            String mon_eve_time=ValidateString(pojo.getMon_eve_startime())+"-"+ValidateString(pojo.getMon_eve_endtime());
            String Tue_mrng_time=ValidateString(pojo.getTue_mor_starttime())+"-"+ValidateString(pojo.getTue_mor_endtime());
            String Tue_eve_time=ValidateString(pojo.getTue_eve_startime())+"-"+ValidateString(pojo.getTue_eve_endtime());
            String Wed_mrng_time=ValidateString(pojo.getWed_mor_starttime())+"-"+ValidateString(pojo.getWed_mor_endtime());
            String Wed_eve_time=ValidateString(pojo.getWed_eve_startime())+"-"+ValidateString(pojo.getWed_eve_endtime());
            String Thus_mrng_time=ValidateString(pojo.getThus_mor_starttime())+"-"+ValidateString(pojo.getThus_mor_endtime());
            String Thus_eve_time=ValidateString(pojo.getThus_eve_startime())+"-"+ValidateString(pojo.getThus_eve_endtime());
            String Fri_mrng_time=ValidateString(pojo.getFri_mor_starttime())+"-"+ValidateString(pojo.getFri_mor_endtime());
            String Fri_eve_time=ValidateString(pojo.getFri_eve_startime())+"-"+ValidateString(pojo.getFri_eve_endtime());
            String Sat_mrng_time=ValidateString(pojo.getSat_mor_starttime())+"-"+ValidateString(pojo.getSat_mor_endtime());
            String Sat_eve_time=ValidateString(pojo.getSat_eve_startime())+"-"+ValidateString(pojo.getSat_eve_endtime());
            String Sun_mrng_time=ValidateString(pojo.getSun_mor_starttime())+"-"+ValidateString(pojo.getSun_mor_endtime());
            String Sun_eve_time=ValidateString(pojo.getSun_eve_startime())+"-"+ValidateString(pojo.getSun_eve_endtime());

            if(ValidateNewString(pojo.getMon_mor_starttime(),pojo.getMon_mor_endtime(),pojo.getMon_eve_startime(),pojo.getMon_eve_endtime())){
                ll_mon.setVisibility(View.VISIBLE);
            }
            else{
                ll_mon.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getTue_mor_starttime(),pojo.getTue_mor_endtime(),pojo.getTue_eve_startime(),pojo.getTue_eve_endtime())){
                ll_tue.setVisibility(View.VISIBLE);
            }
            else{
                ll_tue.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getWed_mor_starttime(),pojo.getWed_mor_endtime(),pojo.getWed_eve_startime(),pojo.getWed_eve_endtime())){
                ll_wed.setVisibility(View.VISIBLE);
            }
            else{
                ll_wed.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getThus_mor_starttime(),pojo.getThus_mor_endtime(),pojo.getThus_eve_startime(),pojo.getThus_eve_endtime())){
                ll_thu.setVisibility(View.VISIBLE);
            }
            else{
                ll_thu.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getFri_mor_starttime(),pojo.getFri_mor_endtime(),pojo.getFri_eve_startime(),pojo.getFri_eve_endtime())){
                ll_fri.setVisibility(View.VISIBLE);
            }
            else{
                ll_fri.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getSat_mor_starttime(),pojo.getSat_mor_endtime(),pojo.getSat_eve_startime(),pojo.getSat_eve_endtime())){
                ll_sat.setVisibility(View.VISIBLE);
            }
            else{
                ll_sat.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getSun_mor_starttime(),pojo.getSun_mor_endtime(),pojo.getSun_eve_startime(),pojo.getSun_eve_endtime())){
                ll_sun.setVisibility(View.VISIBLE);
            }
            else{
                ll_sun.setVisibility(View.GONE);
            }

            tv_mon_mrng_time.setText(mon_mrng_time);
            tv_mon_eve_time.setText(mon_eve_time);
            tv_tue_mrng_time.setText(Tue_mrng_time);
            tv_tue_eve_time.setText(Tue_eve_time);
            tv_wed_mrng_time.setText(Wed_mrng_time);
            tv_wed_eve_time.setText(Wed_eve_time);
            tv_thu_mrng_time.setText(Thus_mrng_time);
            tv_thu_eve_time.setText(Thus_eve_time);
            tv_fri_mrng_time.setText(Fri_mrng_time);
            tv_fri_eve_time.setText(Fri_eve_time);
            tv_sat_mrng_time.setText(Sat_mrng_time);
            tv_sat_eve_time.setText(Sat_eve_time);
            tv_sun_mrng_time.setText(Sun_mrng_time);
            tv_sun_eve_time.setText(Sun_eve_time);


            final int finalI = i;
            cv_clinic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        UpdateByDoc doc= (UpdateByDoc) getActivity();
                        doc.show_save=true;
                    }
                    catch (Exception e){

                    }
                    UpdateByDoc updateByDoc= (UpdateByDoc) getActivity();
                    updateByDoc.SendToUpdateFragment(horizontalList.get(finalI));

                }
            });

            ll_scroll.addView(view);
        }
    }
    public String ValidateString(String s){
        if(s.equals("")){
            return "00:00";
        }
        else{
            return s;
        }
    }
    public boolean ValidateNewString(String start_mrng_time,String end_mrng_time,String eve_start_time,String eve_end_time){
        if(!start_mrng_time.equals("")||!end_mrng_time.equals("")||!eve_start_time.equals("")||!eve_end_time.equals("")){
            return true;
        }
        else{
            return false;
        }
    }
    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<ClinicInfoPOJOResult> horizontalList;
        private Context context;
        private Activity activity;


        public class MyViewHolder extends RecyclerView.ViewHolder {

            public  CardView cv_clinic;
            public TextView tv_clinic_name;
            public LinearLayout linearLayout;
            public TextView tv_clinic_address;
            public TextView tv_city;
            public TextView tv_pin_code;
            public TextView tv_degree;
            public TextView tv_specialist;
            public TextView tv_mon_mrng_time;
            public TextView tv_mon_eve_time;
            public TextView tv_tue_mrng_time;
            public TextView tv_tue_eve_time;
            public TextView tv_wed_mrng_time;
            public TextView tv_wed_eve_time;
            public TextView tv_thu_mrng_time;
            public TextView tv_thu_eve_time;
            public TextView tv_fri_mrng_time;
            public TextView tv_fri_eve_time;
            public TextView tv_sat_mrng_time;
            public TextView tv_sat_eve_time;
            public TextView tv_sun_mrng_time;
            public TextView tv_sun_eve_time;

            public LinearLayout ll_mon;
            public LinearLayout ll_tue;
            public LinearLayout ll_wed;
            public LinearLayout ll_thu;
            public LinearLayout ll_fri;
            public LinearLayout ll_sat;
            public LinearLayout ll_sun;
            public LinearLayout ll_showedit;
            public MyViewHolder(View view) {
                super(view);
                cv_clinic= (CardView) view.findViewById(R.id.cv_clinic);
                tv_clinic_name= (TextView) view.findViewById(R.id.tv_clinic_name);
                linearLayout= (LinearLayout) view.findViewById(R.id.linearlayout);
                tv_clinic_address= (TextView) view.findViewById(R.id.tv_clinic_address);
                scrollableimg= (ImageView) view.findViewById(R.id.scrollableimg);
                tv_city= (TextView) view.findViewById(R.id.tv_city);
                tv_pin_code= (TextView) view.findViewById(R.id.tv_pin_code);
                tv_degree= (TextView) view.findViewById(R.id.tv_degree);
                tv_specialist= (TextView) view.findViewById(R.id.tv_specialist);
                tv_mon_mrng_time= (TextView) view.findViewById(R.id.tv_mon_mrng_time);
                tv_mon_eve_time= (TextView) view.findViewById(R.id.tv_mon_eve_time);
                tv_tue_mrng_time= (TextView) view.findViewById(R.id.tv_tue_mrng_time);
                tv_tue_eve_time= (TextView) view.findViewById(R.id.tv_tue_eve_time);
                tv_wed_mrng_time= (TextView) view.findViewById(R.id.tv_wed_mrng_time);
                tv_thu_mrng_time= (TextView) view.findViewById(R.id.tv_thu_mrng_time);
                tv_thu_eve_time= (TextView) view.findViewById(R.id.tv_thu_eve_time);
                tv_wed_eve_time= (TextView) view.findViewById(R.id.tv_wed_eve_time);
                tv_fri_mrng_time= (TextView) view.findViewById(R.id.tv_fri_mrng_time);
                tv_fri_eve_time= (TextView) view.findViewById(R.id.tv_fri_eve_time);
                tv_sat_mrng_time= (TextView) view.findViewById(R.id.tv_sat_mrng_time);
                tv_sat_eve_time= (TextView) view.findViewById(R.id.tv_sat_eve_time);
                tv_sun_mrng_time= (TextView) view.findViewById(R.id.tv_sun_mrng_time);
                tv_sun_eve_time= (TextView) view.findViewById(R.id.tv_sun_eve_time);
                ll_mon= (LinearLayout) view.findViewById(R.id.ll_mon);
                ll_tue= (LinearLayout) view.findViewById(R.id.ll_tue);
                ll_wed= (LinearLayout) view.findViewById(R.id.ll_wed);
                ll_thu= (LinearLayout) view.findViewById(R.id.ll_thu);
                ll_fri= (LinearLayout) view.findViewById(R.id.ll_fri);
                ll_sat= (LinearLayout) view.findViewById(R.id.ll_sat);
                ll_sun= (LinearLayout) view.findViewById(R.id.ll_sun);
                ll_showedit= (LinearLayout) view.findViewById(R.id.ll_showedit);
            }
        }


        public HorizontalAdapter(Context context, Activity activity, List<ClinicInfoPOJOResult> horizontalList) {
            this.horizontalList = horizontalList;
            this.context = context;
            this.activity=activity;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inflate_clinic_info, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
//            holder.tv_task_no.setText("Task :- "+(position+1));
            ClinicInfoPOJOResult pojo=horizontalList.get(position);
            scrollableimg.setVisibility(View.GONE);
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.tv_clinic_name.setText(pojo.getClinic_name());
            holder.tv_clinic_address.setText(pojo.getClinic_address());
            holder.tv_city.setText(pojo.getClinic_city());
            holder.tv_pin_code.setText(pojo.getClinic_pincode());
            holder.tv_degree.setText(pojo.getClinic_degree());
            holder.tv_specialist.setText(pojo.getClinic_specialist());
            holder.ll_showedit.setVisibility(View.GONE);

            String mon_mrng_time=ValidateString(pojo.getMon_mor_starttime())+"-"+ValidateString(pojo.getMon_mor_endtime());
            String mon_eve_time=ValidateString(pojo.getMon_eve_startime())+"-"+ValidateString(pojo.getMon_eve_endtime());
            String Tue_mrng_time=ValidateString(pojo.getTue_mor_starttime())+"-"+ValidateString(pojo.getTue_mor_endtime());
            String Tue_eve_time=ValidateString(pojo.getTue_eve_startime())+"-"+ValidateString(pojo.getTue_eve_endtime());
            String Wed_mrng_time=ValidateString(pojo.getWed_mor_starttime())+"-"+ValidateString(pojo.getWed_mor_endtime());
            String Wed_eve_time=ValidateString(pojo.getWed_eve_startime())+"-"+ValidateString(pojo.getWed_eve_endtime());
            String Thus_mrng_time=ValidateString(pojo.getThus_mor_starttime())+"-"+ValidateString(pojo.getThus_mor_endtime());
            String Thus_eve_time=ValidateString(pojo.getThus_eve_startime())+"-"+ValidateString(pojo.getThus_eve_endtime());
            String Fri_mrng_time=ValidateString(pojo.getFri_mor_starttime())+"-"+ValidateString(pojo.getFri_mor_endtime());
            String Fri_eve_time=ValidateString(pojo.getFri_eve_startime())+"-"+ValidateString(pojo.getFri_eve_endtime());
            String Sat_mrng_time=ValidateString(pojo.getSat_mor_starttime())+"-"+ValidateString(pojo.getSat_mor_endtime());
            String Sat_eve_time=ValidateString(pojo.getSat_eve_startime())+"-"+ValidateString(pojo.getSat_eve_endtime());
            String Sun_mrng_time=ValidateString(pojo.getSun_mor_starttime())+"-"+ValidateString(pojo.getSun_mor_endtime());
            String Sun_eve_time=ValidateString(pojo.getSun_eve_startime())+"-"+ValidateString(pojo.getSun_eve_endtime());

            if(ValidateNewString(pojo.getMon_mor_starttime(),pojo.getMon_mor_endtime(),pojo.getMon_eve_startime(),pojo.getMon_eve_endtime())){
                holder.ll_mon.setVisibility(View.VISIBLE);
            }
            else{
                holder.ll_mon.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getTue_mor_starttime(),pojo.getTue_mor_endtime(),pojo.getTue_eve_startime(),pojo.getTue_eve_endtime())){
                holder.ll_tue.setVisibility(View.VISIBLE);
            }
            else{
                holder.ll_tue.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getWed_mor_starttime(),pojo.getWed_mor_endtime(),pojo.getWed_eve_startime(),pojo.getWed_eve_endtime())){
                holder.ll_wed.setVisibility(View.VISIBLE);
            }
            else{
                holder.ll_wed.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getThus_mor_starttime(),pojo.getThus_mor_endtime(),pojo.getThus_eve_startime(),pojo.getThus_eve_endtime())){
                holder.ll_thu.setVisibility(View.VISIBLE);
            }
            else{
                holder.ll_thu.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getFri_mor_starttime(),pojo.getFri_mor_endtime(),pojo.getFri_eve_startime(),pojo.getFri_eve_endtime())){
                holder.ll_fri.setVisibility(View.VISIBLE);
            }
            else{
                holder.ll_fri.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getSat_mor_starttime(),pojo.getSat_mor_endtime(),pojo.getSat_eve_startime(),pojo.getSat_eve_endtime())){
                holder.ll_sat.setVisibility(View.VISIBLE);
            }
            else{
                holder.ll_sat.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getSun_mor_starttime(),pojo.getSun_mor_endtime(),pojo.getSun_eve_startime(),pojo.getSun_eve_endtime())){
                holder.ll_sun.setVisibility(View.VISIBLE);
            }
            else{
                holder.ll_sun.setVisibility(View.GONE);
            }

            holder.tv_mon_mrng_time.setText(mon_mrng_time);
            holder.tv_mon_eve_time.setText(mon_eve_time);
            holder.tv_tue_mrng_time.setText(Tue_mrng_time);
            holder.tv_tue_eve_time.setText(Tue_eve_time);
            holder.tv_wed_mrng_time.setText(Wed_mrng_time);
            holder.tv_wed_eve_time.setText(Wed_eve_time);
            holder.tv_thu_mrng_time.setText(Thus_mrng_time);
            holder.tv_thu_eve_time.setText(Thus_eve_time);
            holder.tv_fri_mrng_time.setText(Fri_mrng_time);
            holder.tv_fri_eve_time.setText(Fri_eve_time);
            holder.tv_sat_mrng_time.setText(Sat_mrng_time);
            holder.tv_sat_eve_time.setText(Sat_eve_time);
            holder.tv_sun_mrng_time.setText(Sun_mrng_time);
            holder.tv_sun_eve_time.setText(Sun_eve_time);


            holder.cv_clinic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        UpdateByDoc doc= (UpdateByDoc) activity;
                        doc.show_save=true;
                    }
                    catch (Exception e){

                    }
                    UpdateByDoc updateByDoc= (UpdateByDoc) activity;
                    updateByDoc.SendToUpdateFragment(horizontalList.get(position));

                }
            });
        }
        public String ValidateString(String s){
            if(s.equals("")){
                return "00:00";
            }
            else{
                return s;
            }
        }
        public boolean ValidateNewString(String start_mrng_time,String end_mrng_time,String eve_start_time,String eve_end_time){
            if(!start_mrng_time.equals("")||!end_mrng_time.equals("")||!eve_start_time.equals("")||!eve_end_time.equals("")){
                return true;
            }
            else{
                return false;
            }
        }
        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }
}
