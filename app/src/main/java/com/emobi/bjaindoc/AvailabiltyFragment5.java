package com.emobi.bjaindoc;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Emobi-Android-002 on 12/17/2016.
 */
public class AvailabiltyFragment5 extends Fragment{

    CheckBox check_morning;
    CheckBox check_evening;

    RecyclerView rv_days;

    Context ctx;

    CheckBox check_monday,check_tuesday,check_wednesday,check_thursday,check_friday,check_saturday,check_sunday;
    WheelPicker wheel_mon_from_hour,wheel_mon_from_minute,wheel_mon_from_ampm,wheel_tue_from_hour,wheel_tue_from_minute,wheel_tue_from_ampm
            ,wheel_wed_from_hour,wheel_wed_from_minute,wheel_wed_from_ampm,wheel_thu_from_hour,wheel_thu_from_minute,wheel_thu_from_ampm
            ,wheel_fri_from_hour,wheel_fri_from_minute,wheel_fri_from_ampm,wheel_sat_from_hour,wheel_sat_from_minute,wheel_sat_from_ampm
            ,wheel_sun_from_hour,wheel_sun_from_minute,wheel_sun_from_ampm;
    WheelPicker wheel_mon_to_hour,wheel_mon_to_minute,wheel_mon_to_ampm,wheel_tue_to_hour,wheel_tue_to_minute,wheel_tue_to_ampm
            ,wheel_wed_to_hour,wheel_wed_to_minute,wheel_wed_to_ampm,wheel_thu_to_hour,wheel_thu_to_minute,wheel_thu_to_ampm
            ,wheel_fri_to_hour,wheel_fri_to_minute,wheel_fri_to_ampm,wheel_sat_to_hour,wheel_sat_to_minute,wheel_sat_to_ampm
            ,wheel_sun_to_hour,wheel_sun_to_minute,wheel_sun_to_ampm;

    List<String> list_days=new ArrayList<>();

    List<String> lst_hour=new ArrayList<>();
    List<String> lst_minutes=new ArrayList<>();
    List<String> lst_ampm=new ArrayList<>();

    TextView tv_mon_from,tv_mon_to,tv_tue_from,tv_tue_to,tv_wed_from,tv_wed_to,tv_thu_from,tv_thu_to
            ,tv_fri_from,tv_fri_to,tv_sat_from,tv_sat_to,tv_sun_from,tv_sun_to;
    EditText et_mon_place,et_tue_place, et_wed_place,et_thu_place
            ,et_fri_place,et_sat_place,et_sun_place;

    View mon_view,tue_view,wed_view,thu_view,fri_view,sat_view,sun_view;
    List<CheckBox> list_checkboxes=new ArrayList<>();
    List<LinearLayout> list_days_ll=new ArrayList<>();
    List<TextView> list_days_tv=new ArrayList<>();

    final static String morning_scheduled="morning";
    final static String evening_scheduled="evening";
    String string_scheduled=morning_scheduled;

    boolean[] checked_ll=new boolean[7];
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_availability1,container,false);

        ctx=getActivity();

        rv_days= (RecyclerView) view.findViewById(R.id.rv_days);
        initializeViews();
        initializeListData();
        initializeWheels();


//        mon_view.setVisibility(View.GONE);
        tue_view.setVisibility(View.GONE);
        wed_view.setVisibility(View.GONE);
        thu_view.setVisibility(View.GONE);
        fri_view.setVisibility(View.GONE);
        sat_view.setVisibility(View.GONE);
        sun_view.setVisibility(View.GONE);


        HorizontalAdapter adapter=new HorizontalAdapter(getApplicationContext(),list_days);

        LinearLayoutManager horizontalLayoutManagaer1
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_days.setLayoutManager(horizontalLayoutManagaer1);

        rv_days.setAdapter(adapter);


        check_morning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check_evening.setChecked(false);
                    string_scheduled=morning_scheduled;
                }
            }
        });
        check_evening.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check_morning.setChecked(false);
                    string_scheduled=evening_scheduled;
                }
            }
        });

        return view;
    }

    public void SaveTimings(){
        showTimings();
    }

    public boolean[] getCheckedLL(){
        return checked_ll;
    }

    public String getLocation(){
        if(et_mon_place.getText().toString().length()>0){
            return et_mon_place.getText().toString();
        }
        else{
            if(et_tue_place.getText().toString().length()>0){
                return et_tue_place.getText().toString();
            }
            else{
                if(et_wed_place.getText().toString().length()>0){
                    return et_wed_place.getText().toString();
                }
                else{
                    if(et_thu_place.getText().toString().length()>0){
                        return et_thu_place.getText().toString();
                    }
                    else{
                        if(et_fri_place.getText().toString().length()>0){
                            return et_fri_place.getText().toString();
                        }
                        else{
                            if(et_sat_place.getText().toString().length()>0){
                                return et_sat_place.getText().toString();
                            }
                            else{
                                if(et_sun_place.getText().toString().length()>0){
                                    return et_sun_place.getText().toString();
                                }
                                else{
                                    return "";
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public String showTimings(){
        for(int i=0;i<checked_ll.length;i++){
            switch (i){
                case 0:
                    if(checked_ll[i]){
                        monday_selected=false;
                        //Log.d("sunil","monday timings:-"+check_selected_from_time+"hh"+check_selected_to_time);
                        monday_morning_timings =check_selected_from_time+"hh"+check_selected_to_time;
                    }
                    else{
                        monday_morning_timings =" ";
                    }
                    break;
                case 1:
                    if(checked_ll[i]){
                        tuesday_selected=false;
                        tuesday_morning_timings =check_selected_from_time+"hh"+check_selected_to_time;
                        //Log.d("sunil","tuesday timings:-"+check_selected_from_time+"hh"+check_selected_to_time);
                    }
                    else{
                        tuesday_morning_timings =" ";
                    }
                    break;
                case 2:
                    if(checked_ll[i]){
                        wednesday_selected=false;
                        wednesday_morning_timings =check_selected_from_time+"hh"+check_selected_to_time;
                        //Log.d("sunil","wednesday timings:-"+check_selected_from_time+"hh"+check_selected_to_time);
                    }
                    else{
                        wednesday_morning_timings =" ";
                    }
                    break;
                case 3:
                    if(checked_ll[i]){
                        thursday_selected=false;
                        thursday_morning_timings =check_selected_from_time+"hh"+check_selected_to_time;
                        //Log.d("sunil","thursday timings:-"+check_selected_from_time+"hh"+check_selected_to_time);
                    }
                    else{
                        thursday_morning_timings =" ";
                    }
                    break;
                case 4:
                    if(checked_ll[i]){
                        friday_selected=false;
                        friday_morning_timings =check_selected_from_time+"hh"+check_selected_to_time;
                        //Log.d("sunil","friday timings:-"+check_selected_from_time+"hh"+check_selected_to_time);
                    }
                    else{
                        friday_morning_timings =" ";
                    }
                    break;
                case 5:
                    if(checked_ll[i]){
                        saturday_selected=false;
                        saturday_morning_timings =check_selected_from_time+"hh"+check_selected_to_time;
                        //Log.d("sunil","saturday timings:-"+check_selected_from_time+"hh"+check_selected_to_time);
                    }
                    else{
                        saturday_morning_timings =" ";
                    }
                    break;
                case 6:
                    if(checked_ll[i]){
                        sunday_selected=false;
                        sunday_morning_timings =check_selected_from_time+"hh"+check_selected_to_time;
                        //Log.d("sunil","sunday timings:-"+check_selected_from_time+"hh"+check_selected_to_time);
                    }
                    else{
                        sunday_morning_timings =" ";
                    }
                    break;
            }
        }
        String finaltimings="";
        if(check_morning.isChecked()) {
//            Log.d("sunil", "monday:-" + monday_morning_timings+"::");
//            Log.d("sunil", "tuesday:-" + tuesday_morning_timings+"::");
//            Log.d("sunil", "wednesday:-" + wednesday_morning_timings+"::");
//            Log.d("sunil", "thursday:-" + thursday_morning_timings+"::");
//            Log.d("sunil", "friday:-" + friday_morning_timings+"::");
//            Log.d("sunil", "saturday:-" + saturday_morning_timings+"::");
//            Log.d("sunil", "sunday:-" + sunday_morning_timings+"::");
            finaltimings=monday_morning_timings+"::"+tuesday_morning_timings+"::"+
                    wednesday_morning_timings+"::"+thursday_morning_timings+"::"+
                    friday_morning_timings+"::"+saturday_morning_timings+"::"+
                    sunday_morning_timings+"&null";
        }
        else{
            if(check_evening.isChecked()){

//                Log.d("sunil", "monday:-" +"::"+ monday_morning_timings);
//                Log.d("sunil", "tuesday:-" +"::"+ tuesday_morning_timings);
//                Log.d("sunil", "wednesday:-" + "::"+wednesday_morning_timings);
//                Log.d("sunil", "thursday:-" + "::"+thursday_morning_timings);
//                Log.d("sunil", "friday:-" + "::"+friday_morning_timings);
//                Log.d("sunil", "saturday:-" + "::"+saturday_morning_timings);
//                Log.d("sunil", "sunday:-" + "::"+sunday_morning_timings);

                finaltimings="null&"+ monday_morning_timings+"::"+ tuesday_morning_timings
                        +"::"+wednesday_morning_timings+"::"+thursday_morning_timings
                        +"::"+friday_morning_timings+"::"+saturday_morning_timings
                        +"::"+sunday_morning_timings;
            }
        }
        return finaltimings;
    }
    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<String> horizontalList;
        private Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public CheckBox check_days;
            public TextView tv_days;
            public LinearLayout ll_days;

            public MyViewHolder(View view) {
                super(view);
                tv_days = (TextView) view.findViewById(R.id.tv_days);
                check_days= (CheckBox) view.findViewById(R.id.check_days);
                ll_days= (LinearLayout) view.findViewById(R.id.ll_days);
            }
        }


        public HorizontalAdapter(Context context1, List<String> horizontalList) {
            this.horizontalList = horizontalList;
            context=context1;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inflate_horizontal_days, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.tv_days.setText(horizontalList.get(position));
            list_days_ll.add(holder.ll_days);
            list_days_tv.add(holder.tv_days);
            if(position==0){
                holder.ll_days.setBackgroundResource(R.drawable.ll_oval);
                checked_ll[position]=true;
            }
            else{
                holder.ll_days.setBackgroundResource(R.drawable.ll_oval_unchecked);
                checked_ll[position]=false;
            }
            holder.check_days.setVisibility(View.GONE);

            holder.ll_days.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!wheel_changed) {
                        switch (position) {
                            case 0:
                                mon_view.setVisibility(View.VISIBLE);
                                tue_view.setVisibility(View.GONE);
                                wed_view.setVisibility(View.GONE);
                                thu_view.setVisibility(View.GONE);
                                fri_view.setVisibility(View.GONE);
                                sat_view.setVisibility(View.GONE);
                                sun_view.setVisibility(View.GONE);
                                break;
                            case 1:
                                mon_view.setVisibility(View.GONE);
                                tue_view.setVisibility(View.VISIBLE);
                                wed_view.setVisibility(View.GONE);
                                thu_view.setVisibility(View.GONE);
                                fri_view.setVisibility(View.GONE);
                                sat_view.setVisibility(View.GONE);
                                sun_view.setVisibility(View.GONE);
                                break;
                            case 2:
                                mon_view.setVisibility(View.GONE);
                                tue_view.setVisibility(View.GONE);
                                wed_view.setVisibility(View.VISIBLE);
                                thu_view.setVisibility(View.GONE);
                                fri_view.setVisibility(View.GONE);
                                sat_view.setVisibility(View.GONE);
                                sun_view.setVisibility(View.GONE);
                                break;
                            case 3:
                                mon_view.setVisibility(View.GONE);
                                tue_view.setVisibility(View.GONE);
                                wed_view.setVisibility(View.GONE);
                                thu_view.setVisibility(View.VISIBLE);
                                fri_view.setVisibility(View.GONE);
                                sat_view.setVisibility(View.GONE);
                                sun_view.setVisibility(View.GONE);
                                break;
                            case 4:
                                mon_view.setVisibility(View.GONE);
                                tue_view.setVisibility(View.GONE);
                                wed_view.setVisibility(View.GONE);
                                thu_view.setVisibility(View.GONE);
                                fri_view.setVisibility(View.VISIBLE);
                                sat_view.setVisibility(View.GONE);
                                sun_view.setVisibility(View.GONE);
                                break;
                            case 5:
                                mon_view.setVisibility(View.GONE);
                                tue_view.setVisibility(View.GONE);
                                wed_view.setVisibility(View.GONE);
                                thu_view.setVisibility(View.GONE);
                                fri_view.setVisibility(View.GONE);
                                sat_view.setVisibility(View.VISIBLE);
                                sun_view.setVisibility(View.GONE);
                                break;
                            case 6:
                                mon_view.setVisibility(View.GONE);
                                tue_view.setVisibility(View.GONE);
                                wed_view.setVisibility(View.GONE);
                                thu_view.setVisibility(View.GONE);
                                fri_view.setVisibility(View.GONE);
                                sat_view.setVisibility(View.GONE);
                                sun_view.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                    if(wheel_changed){
                        if(checked_ll[position]){
                            holder.ll_days.setBackgroundResource(R.drawable.ll_oval_unchecked);
                            holder.tv_days.setTextColor(Color.parseColor("#000000"));
                            checked_ll[position] = false;
                        }
                        else {
                            holder.ll_days.setBackgroundResource(R.drawable.ll_oval);
                            holder.tv_days.setTextColor(Color.parseColor("#FFFFFF"));
                            checked_ll[position] = true;
                        }
                    }
                    else{
                        for(int i=0;i<list_days_ll.size();i++){
                            LinearLayout linearLayout=list_days_ll.get(i);
                            linearLayout.setBackgroundResource(R.drawable.ll_oval_unchecked);
                            TextView tv=list_days_tv.get(i);
                            tv.setTextColor(Color.parseColor("#000000"));
                            checked_ll[i]=false;
                        }
                        holder.ll_days.setBackgroundResource(R.drawable.ll_oval);
                        holder.tv_days.setTextColor(Color.parseColor("#FFFFFF"));
                        for(boolean b:checked_ll){
                            b=false;
                        }
                        checked_ll[position]=true;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }

    boolean item_unselected=false;


    public void initializeViews(){
        tv_mon_from = (TextView) view.findViewById(R.id.tv_mon_from);
        tv_mon_to = (TextView) view.findViewById(R.id.tv_mon_to);

        et_mon_place= (EditText) view.findViewById(R.id.et_mon_place);

        tv_tue_from = (TextView) view.findViewById(R.id.tv_tue_from);
        tv_tue_to = (TextView) view.findViewById(R.id.tv_tue_to);

        et_tue_place= (EditText) view.findViewById(R.id.et_tue_place);

        tv_wed_from = (TextView) view.findViewById(R.id.tv_wed_from);
        tv_wed_to = (TextView) view.findViewById(R.id.tv_wed_to);

        et_wed_place= (EditText) view.findViewById(R.id.et_wed_place);

        tv_thu_from = (TextView) view.findViewById(R.id.tv_thu_from);
        tv_thu_to = (TextView) view.findViewById(R.id.tv_thu_to);

        et_thu_place= (EditText) view.findViewById(R.id.et_thu_place);

        tv_fri_from = (TextView) view.findViewById(R.id.tv_fri_from);
        tv_fri_to = (TextView) view.findViewById(R.id.tv_fri_to);

        et_fri_place= (EditText) view.findViewById(R.id.et_fri_place);

        tv_sat_from = (TextView) view.findViewById(R.id.tv_sat_from);
        tv_sat_to = (TextView) view.findViewById(R.id.tv_sat_to);

        et_sat_place= (EditText) view.findViewById(R.id.et_sat_place);


        tv_sun_from = (TextView) view.findViewById(R.id.tv_sun_from);
        tv_sun_to = (TextView) view.findViewById(R.id.tv_sun_to);

        et_sun_place= (EditText) view.findViewById(R.id.et_sun_place);


        mon_view = view.findViewById(R.id.include_mon);
        tue_view = view.findViewById(R.id.include_tue);
        wed_view = view.findViewById(R.id.include_wed);
        thu_view = view.findViewById(R.id.include_thu);
        fri_view = view.findViewById(R.id.include_fri);
        sat_view = view.findViewById(R.id.include_sat);
        sun_view = view.findViewById(R.id.include_sun);
        check_morning= (CheckBox) view.findViewById(R.id.check_morning);
        check_evening= (CheckBox) view.findViewById(R.id.check_evening);
    }

    public void initializeListData(){
        list_days.add("MON");
        list_days.add("TUE");
        list_days.add("WED");
        list_days.add("THU");
        list_days.add("FRI");
        list_days.add("SAT");
        list_days.add("SUN");

        for(int i=1;i<=12;i++)
            lst_hour.add(i+"");


        for(int i=1;i<60;i++){
            lst_minutes.add(i+"");
        }

        lst_ampm.add("am");
        lst_ampm.add("pm");
    }

    public void initializeWheels(){

        //monday
        wheel_mon_from_hour= (WheelPicker) view.findViewById(R.id.wheel_mon_from_hour);
        wheel_mon_from_minute= (WheelPicker) view.findViewById(R.id.wheel_mon_from_minute);
        wheel_mon_from_ampm= (WheelPicker) view.findViewById(R.id.wheel_mon_from_ampm);
        wheel_mon_to_hour = (WheelPicker) view.findViewById(R.id.wheel_mon_to_hour);
        wheel_mon_to_minute = (WheelPicker) view.findViewById(R.id.wheel_mon_to_minute);
        wheel_mon_to_ampm = (WheelPicker) view.findViewById(R.id.wheel_mon_to_ampm);

        //tuesday wheel

        wheel_tue_from_hour= (WheelPicker) view.findViewById(R.id.wheel_tue_from_hour);
        wheel_tue_from_minute= (WheelPicker) view.findViewById(R.id.wheel_tue_from_minute);
        wheel_tue_from_ampm= (WheelPicker) view.findViewById(R.id.wheel_tue_from_ampm);
        wheel_tue_to_hour = (WheelPicker) view.findViewById(R.id.wheel_tue_to_hour);
        wheel_tue_to_minute = (WheelPicker) view.findViewById(R.id.wheel_tue_to_minute);
        wheel_tue_to_ampm = (WheelPicker) view.findViewById(R.id.wheel_tue_to_ampm);

        //wednesday wheel

        wheel_wed_from_hour= (WheelPicker) view.findViewById(R.id.wheel_wed_from_hour);
        wheel_wed_from_minute= (WheelPicker) view.findViewById(R.id.wheel_wed_from_minute);
        wheel_wed_from_ampm= (WheelPicker) view.findViewById(R.id.wheel_wed_from_ampm);
        wheel_wed_to_hour = (WheelPicker) view.findViewById(R.id.wheel_wed_to_hour);
        wheel_wed_to_minute = (WheelPicker) view.findViewById(R.id.wheel_wed_to_minute);
        wheel_wed_to_ampm = (WheelPicker) view.findViewById(R.id.wheel_wed_to_ampm);

        //thursday wheel

        wheel_thu_from_hour= (WheelPicker) view.findViewById(R.id.wheel_thu_from_hour);
        wheel_thu_from_minute= (WheelPicker) view.findViewById(R.id.wheel_thu_from_minute);
        wheel_thu_from_ampm= (WheelPicker) view.findViewById(R.id.wheel_thu_from_ampm);
        wheel_thu_to_hour = (WheelPicker) view.findViewById(R.id.wheel_thu_to_hour);
        wheel_thu_to_minute = (WheelPicker) view.findViewById(R.id.wheel_thu_to_minute);
        wheel_thu_to_ampm = (WheelPicker) view.findViewById(R.id.wheel_thu_to_ampm);

        //friday wheel

        wheel_fri_from_hour= (WheelPicker) view.findViewById(R.id.wheel_fri_from_hour);
        wheel_fri_from_minute= (WheelPicker) view.findViewById(R.id.wheel_fri_from_minute);
        wheel_fri_from_ampm= (WheelPicker) view.findViewById(R.id.wheel_fri_from_ampm);
        wheel_fri_to_hour = (WheelPicker) view.findViewById(R.id.wheel_fri_to_hour);
        wheel_fri_to_minute = (WheelPicker) view.findViewById(R.id.wheel_fri_to_minute);
        wheel_fri_to_ampm = (WheelPicker) view.findViewById(R.id.wheel_fri_to_ampm);


        //saturday
        wheel_sat_from_hour= (WheelPicker) view.findViewById(R.id.wheel_sat_from_hour);
        wheel_sat_from_minute= (WheelPicker) view.findViewById(R.id.wheel_sat_from_minute);
        wheel_sat_from_ampm= (WheelPicker) view.findViewById(R.id.wheel_sat_from_ampm);
        wheel_sat_to_hour = (WheelPicker) view.findViewById(R.id.wheel_sat_to_hour);
        wheel_sat_to_minute = (WheelPicker) view.findViewById(R.id.wheel_sat_to_minute);
        wheel_sat_to_ampm = (WheelPicker) view.findViewById(R.id.wheel_sat_to_ampm);


        //sunday
        wheel_sun_from_hour= (WheelPicker) view.findViewById(R.id.wheel_sun_from_hour);
        wheel_sun_from_minute= (WheelPicker) view.findViewById(R.id.wheel_sun_from_minute);
        wheel_sun_from_ampm= (WheelPicker) view.findViewById(R.id.wheel_sun_from_ampm);
        wheel_sun_to_hour = (WheelPicker) view.findViewById(R.id.wheel_sun_to_hour);
        wheel_sun_to_minute = (WheelPicker) view.findViewById(R.id.wheel_sun_to_minute);
        wheel_sun_to_ampm = (WheelPicker) view.findViewById(R.id.wheel_sun_to_ampm);

        setWheelValues();
    }

    public void setWheelValues(){

        //monday
        wheel_mon_from_hour.setOnItemSelectedListener(mon_from_item_selected_hour);
        wheel_mon_from_hour.setData(lst_hour);

        wheel_mon_from_minute.setOnItemSelectedListener(mon_from_item_selected_minute);
        wheel_mon_from_minute.setData(lst_minutes);

        wheel_mon_from_ampm.setOnItemSelectedListener(mon_from_item_selected_sec);
        wheel_mon_from_ampm.setData(lst_ampm);


        wheel_mon_to_hour.setOnItemSelectedListener(mon_to_item_selected_hour);
        wheel_mon_to_hour.setData(lst_hour);

        wheel_mon_to_minute.setOnItemSelectedListener(mon_to_item_selected_minute);
        wheel_mon_to_minute.setData(lst_minutes);

        wheel_mon_to_ampm.setOnItemSelectedListener(mon_to_item_selected_sec);
        wheel_mon_to_ampm.setData(lst_ampm);

        //tuesday
        wheel_tue_from_hour.setOnItemSelectedListener(tue_from_item_selected_hour);
        wheel_tue_from_hour.setData(lst_hour);

        wheel_tue_from_minute.setOnItemSelectedListener(tue_from_item_selected_minute);
        wheel_tue_from_minute.setData(lst_minutes);

        wheel_tue_from_ampm.setOnItemSelectedListener(tue_from_item_selected_sec);
        wheel_tue_from_ampm.setData(lst_ampm);


        wheel_tue_to_hour.setOnItemSelectedListener(tue_to_item_selected_hour);
        wheel_tue_to_hour.setData(lst_hour);

        wheel_tue_to_minute.setOnItemSelectedListener(tue_to_item_selected_minute);
        wheel_tue_to_minute.setData(lst_minutes);

        wheel_tue_to_ampm.setOnItemSelectedListener(tue_to_item_selected_sec);
        wheel_tue_to_ampm.setData(lst_ampm);

        //wednesday

        wheel_wed_from_hour.setOnItemSelectedListener(wed_from_item_selected_hour);
        wheel_wed_from_hour.setData(lst_hour);

        wheel_wed_from_minute.setOnItemSelectedListener(wed_from_item_selected_minute);
        wheel_wed_from_minute.setData(lst_minutes);

        wheel_wed_from_ampm.setOnItemSelectedListener(wed_from_item_selected_sec);
        wheel_wed_from_ampm.setData(lst_ampm);


        wheel_wed_to_hour.setOnItemSelectedListener(wed_to_item_selected_hour);
        wheel_wed_to_hour.setData(lst_hour);

        wheel_wed_to_minute.setOnItemSelectedListener(wed_to_item_selected_minute);
        wheel_wed_to_minute.setData(lst_minutes);

        wheel_wed_to_ampm.setOnItemSelectedListener(wed_to_item_selected_sec);
        wheel_wed_to_ampm.setData(lst_ampm);

        //thursday
        wheel_thu_from_hour.setOnItemSelectedListener(thu_from_item_selected_hour);
        wheel_thu_from_hour.setData(lst_hour);

        wheel_thu_from_minute.setOnItemSelectedListener(thu_from_item_selected_minute);
        wheel_thu_from_minute.setData(lst_minutes);

        wheel_thu_from_ampm.setOnItemSelectedListener(thu_from_item_selected_sec);
        wheel_thu_from_ampm.setData(lst_ampm);


        wheel_thu_to_hour.setOnItemSelectedListener(thu_to_item_selected_hour);
        wheel_thu_to_hour.setData(lst_hour);

        wheel_thu_to_minute.setOnItemSelectedListener(thu_to_item_selected_minute);
        wheel_thu_to_minute.setData(lst_minutes);

        wheel_thu_to_ampm.setOnItemSelectedListener(thu_to_item_selected_sec);
        wheel_thu_to_ampm.setData(lst_ampm);

        //friday
        wheel_fri_from_hour.setOnItemSelectedListener(fri_from_item_selected_hour);
        wheel_fri_from_hour.setData(lst_hour);

        wheel_fri_from_minute.setOnItemSelectedListener(fri_from_item_selected_minute);
        wheel_fri_from_minute.setData(lst_minutes);

        wheel_fri_from_ampm.setOnItemSelectedListener(fri_from_item_selected_sec);
        wheel_fri_from_ampm.setData(lst_ampm);


        wheel_fri_to_hour.setOnItemSelectedListener(fri_to_item_selected_hour);
        wheel_fri_to_hour.setData(lst_hour);

        wheel_fri_to_minute.setOnItemSelectedListener(fri_to_item_selected_minute);
        wheel_fri_to_minute.setData(lst_minutes);

        wheel_fri_to_ampm.setOnItemSelectedListener(fri_to_item_selected_sec);
        wheel_fri_to_ampm.setData(lst_ampm);

        //saturday
        wheel_sat_from_hour.setOnItemSelectedListener(sat_from_item_selected_hour);
        wheel_sat_from_hour.setData(lst_hour);

        wheel_sat_from_minute.setOnItemSelectedListener(sat_from_item_selected_minute);
        wheel_sat_from_minute.setData(lst_minutes);

        wheel_sat_from_ampm.setOnItemSelectedListener(sat_from_item_selected_sec);
        wheel_sat_from_ampm.setData(lst_ampm);


        wheel_sat_to_hour.setOnItemSelectedListener(sat_to_item_selected_hour);
        wheel_sat_to_hour.setData(lst_hour);

        wheel_sat_to_minute.setOnItemSelectedListener(sat_to_item_selected_minute);
        wheel_sat_to_minute.setData(lst_minutes);

        wheel_sat_to_ampm.setOnItemSelectedListener(sat_to_item_selected_sec);
        wheel_sat_to_ampm.setData(lst_ampm);

        //sunday
        wheel_sun_from_hour.setOnItemSelectedListener(sun_from_item_selected_hour);
        wheel_sun_from_hour.setData(lst_hour);

        wheel_sun_from_minute.setOnItemSelectedListener(sun_from_item_selected_minute);
        wheel_sun_from_minute.setData(lst_minutes);

        wheel_sun_from_ampm.setOnItemSelectedListener(sun_from_item_selected_sec);
        wheel_sun_from_ampm.setData(lst_ampm);


        wheel_sun_to_hour.setOnItemSelectedListener(sun_to_item_selected_hour);
        wheel_sun_to_hour.setData(lst_hour);

        wheel_sun_to_minute.setOnItemSelectedListener(sun_to_item_selected_minute);
        wheel_sun_to_minute.setData(lst_minutes);

        wheel_sun_to_ampm.setOnItemSelectedListener(sun_to_item_selected_sec);
        wheel_sun_to_ampm.setData(lst_ampm);
    }


    String monday_morning_timings ="";
    String monday_evening_timings ="";
    String tuesday_morning_timings ="";
    String tuesday_evening_timings ="";
    String wednesday_morning_timings ="";
    String wednesday_evening_timings ="";
    String thursday_morning_timings ="";
    String thursday_evening_timings ="";
    String friday_morning_timings ="";
    String friday_evening_timings ="";
    String saturday_morning_timings ="";
    String saturday_evening_timings ="";
    String sunday_morning_timings ="";
    String sunday_evening_timings ="";

    String mon_from_time="",mon_from_hour="1",mon_from_minute="1",mon_from_ampm="am";
    String monday_to_time="",mon_to_hour="1",mon_to_minute="1",mon_to_ampm="am";
    String tue_from_time="",tue_from_hour="1",tue_from_minute="1",tue_from_ampm="am";
    String tueday_to_time="",tue_to_hour="1",tue_to_minute="1",tue_to_ampm="am";
    String wed_from_time="",wed_from_hour="1",wed_from_minute="1",wed_from_ampm="am";
    String wedday_to_time="",wed_to_hour="1",wed_to_minute="1",wed_to_ampm="am";
    String thu_from_time="",thu_from_hour="1",thu_from_minute="1",thu_from_ampm="am";
    String thuday_to_time="",thu_to_hour="1",thu_to_minute="1",thu_to_ampm="am";
    String fri_from_time="",fri_from_hour="1",fri_from_minute="1",fri_from_ampm="am";
    String friday_to_time="",fri_to_hour="1",fri_to_minute="1",fri_to_ampm="am";
    String sat_from_time="",sat_from_hour="1",sat_from_minute="1",sat_from_ampm="am";
    String satday_to_time="",sat_to_hour="1",sat_to_minute="1",sat_to_ampm="am";
    String sun_from_time="",sun_from_hour="1",sun_from_minute="1",sun_from_ampm="am";
    String sunday_to_time="",sun_to_hour="1",sun_to_minute="1",sun_to_ampm="am";

    WheelPicker.OnItemSelectedListener mon_from_item_selected_hour=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {

            switch (picker.getId()) {
                case R.id.wheel_mon_from_hour:mon_from_hour=lst_hour.get(position);
                    break;
            }
            mon_from_time=mon_from_hour+":"+mon_from_minute+" "+mon_from_ampm;
            tv_mon_from.setText(mon_from_time);
            showcheck(1);
            monday_selected=true;
        }
    };

    WheelPicker.OnItemSelectedListener mon_from_item_selected_minute=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_mon_from_minute:mon_from_minute=lst_minutes.get(position);
                    break;
            }
            mon_from_time=mon_from_hour+":"+mon_from_minute+" "+mon_from_ampm;
            tv_mon_from.setText(mon_from_time);
            monday_selected=true;
            showcheck(1);
        }
    };
    WheelPicker.OnItemSelectedListener mon_from_item_selected_sec=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_mon_from_ampm:mon_from_ampm=lst_ampm.get(position);
                    break;
            }
            mon_from_time=mon_from_hour+":"+mon_from_minute+" "+mon_from_ampm;
            tv_mon_from.setText(mon_from_time);
            monday_selected=true;
            showcheck(1);
        }
    };
    WheelPicker.OnItemSelectedListener mon_to_item_selected_hour=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {

            switch (picker.getId()) {
                case R.id.wheel_mon_to_hour:mon_to_hour=lst_hour.get(position);
                    break;
            }
            monday_to_time=mon_to_hour+":"+mon_to_minute+" "+mon_to_ampm;
            tv_mon_to.setText(monday_to_time);
            monday_selected=true;
            showcheck(1);
        }
    };

    WheelPicker.OnItemSelectedListener mon_to_item_selected_minute=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_mon_to_minute:mon_to_minute=lst_minutes.get(position);
                    break;
            }
            monday_to_time=mon_to_hour+":"+mon_to_minute+" "+mon_to_ampm;
            tv_mon_to.setText(monday_to_time);
            monday_selected=true;
            showcheck(1);
        }
    };
    WheelPicker.OnItemSelectedListener mon_to_item_selected_sec=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_mon_to_ampm:mon_to_ampm=lst_ampm.get(position);
                    break;
            }
            monday_to_time=mon_to_hour+":"+mon_to_minute+" "+mon_to_ampm;
            tv_mon_to.setText(monday_to_time);
            monday_selected=true;
            showcheck(1);
        }
    };

    WheelPicker.OnItemSelectedListener tue_from_item_selected_hour=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {

            switch (picker.getId()) {
                case R.id.wheel_tue_from_hour:tue_from_hour=lst_hour.get(position);
                    break;
            }
            tue_from_time=tue_from_hour+":"+tue_from_minute+" "+tue_from_ampm;
            tv_tue_from.setText(tue_from_time);
            tuesday_selected=true;
            showcheck(2);
        }
    };

    WheelPicker.OnItemSelectedListener tue_from_item_selected_minute=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_tue_from_minute:tue_from_minute=lst_minutes.get(position);
                    break;
            }
            tue_from_time=tue_from_hour+":"+tue_from_minute+" "+tue_from_ampm;
            tv_tue_from.setText(tue_from_time);
            tuesday_selected=true;
            showcheck(2);
        }
    };
    WheelPicker.OnItemSelectedListener tue_from_item_selected_sec=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_tue_from_ampm:tue_from_ampm=lst_ampm.get(position);
                    break;
            }
            tue_from_time=tue_from_hour+":"+tue_from_minute+" "+tue_from_ampm;
            tv_tue_from.setText(tue_from_time);
            tuesday_selected=true;
            showcheck(2);
        }
    };
    WheelPicker.OnItemSelectedListener tue_to_item_selected_hour=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {

            switch (picker.getId()) {
                case R.id.wheel_tue_to_hour:tue_to_hour=lst_hour.get(position);
                    break;
            }
            tueday_to_time=tue_to_hour+":"+tue_to_minute+" "+tue_to_ampm;
            tv_tue_to.setText(tueday_to_time);
            tuesday_selected=true;
            showcheck(2);
        }
    };

    WheelPicker.OnItemSelectedListener tue_to_item_selected_minute=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_tue_to_minute:tue_to_minute=lst_minutes.get(position);
                    break;
            }
            tueday_to_time=tue_to_hour+":"+tue_to_minute+" "+tue_to_ampm;
            tv_tue_to.setText(tueday_to_time);
            tuesday_selected=true;
            showcheck(2);
        }
    };
    WheelPicker.OnItemSelectedListener tue_to_item_selected_sec=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_tue_to_ampm:tue_to_ampm=lst_ampm.get(position);
                    break;
            }
            tueday_to_time=tue_to_hour+":"+tue_to_minute+" "+tue_to_ampm;
            tv_tue_to.setText(tueday_to_time);
            tuesday_selected=true;
            showcheck(2);
        }
    };

    WheelPicker.OnItemSelectedListener wed_from_item_selected_hour=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {

            switch (picker.getId()) {
                case R.id.wheel_wed_from_hour:wed_from_hour=lst_hour.get(position);
                    break;
            }
            wed_from_time=wed_from_hour+":"+wed_from_minute+" "+wed_from_ampm;
            tv_wed_from.setText(wed_from_time);
            wednesday_selected=true;
            showcheck(3);
        }
    };

    WheelPicker.OnItemSelectedListener wed_from_item_selected_minute=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_wed_from_minute:wed_from_minute=lst_minutes.get(position);
                    break;
            }
            wed_from_time=wed_from_hour+":"+wed_from_minute+" "+wed_from_ampm;
            tv_wed_from.setText(wed_from_time);
            wednesday_selected=true;
            showcheck(3);
        }
    };
    WheelPicker.OnItemSelectedListener wed_from_item_selected_sec=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_wed_from_ampm:wed_from_ampm=lst_ampm.get(position);
                    break;
            }
            wed_from_time=wed_from_hour+":"+wed_from_minute+" "+wed_from_ampm;
            tv_wed_from.setText(wed_from_time);
            wednesday_selected=true;
            showcheck(3);
        }
    };
    WheelPicker.OnItemSelectedListener wed_to_item_selected_hour=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {

            switch (picker.getId()) {
                case R.id.wheel_wed_to_hour:wed_to_hour=lst_hour.get(position);
                    break;
            }
            wedday_to_time=wed_to_hour+":"+wed_to_minute+" "+wed_to_ampm;
            tv_wed_to.setText(wedday_to_time);
            wednesday_selected=true;
            showcheck(3);
        }
    };

    WheelPicker.OnItemSelectedListener wed_to_item_selected_minute=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_wed_to_minute:wed_to_minute=lst_minutes.get(position);
                    break;
            }
            wedday_to_time=wed_to_hour+":"+wed_to_minute+" "+wed_to_ampm;
            tv_wed_to.setText(wedday_to_time);
            wednesday_selected=true;
            showcheck(3);
        }
    };
    WheelPicker.OnItemSelectedListener wed_to_item_selected_sec=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_wed_to_ampm:wed_to_ampm=lst_ampm.get(position);
                    break;
            }
            wedday_to_time=wed_to_hour+":"+wed_to_minute+" "+wed_to_ampm;
            tv_wed_to.setText(wedday_to_time);
            wednesday_selected=true;
            showcheck(3);
        }
    };

    WheelPicker.OnItemSelectedListener thu_from_item_selected_hour=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {

            switch (picker.getId()) {
                case R.id.wheel_thu_from_hour:thu_from_hour=lst_hour.get(position);
                    break;
            }
            thu_from_time=thu_from_hour+":"+thu_from_minute+" "+thu_from_ampm;
            tv_thu_from.setText(thu_from_time);
            thursday_selected=true;
            showcheck(4);
        }
    };

    WheelPicker.OnItemSelectedListener thu_from_item_selected_minute=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_thu_from_minute:thu_from_minute=lst_minutes.get(position);
                    break;
            }
            thu_from_time=thu_from_hour+":"+thu_from_minute+" "+thu_from_ampm;
            tv_thu_from.setText(thu_from_time);
            thursday_selected=true;
            showcheck(4);
        }
    };
    WheelPicker.OnItemSelectedListener thu_from_item_selected_sec=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_thu_from_ampm:thu_from_ampm=lst_ampm.get(position);
                    break;
            }
            thu_from_time=thu_from_hour+":"+thu_from_minute+" "+thu_from_ampm;
            tv_thu_from.setText(thu_from_time);
            thursday_selected=true;
            showcheck(4);
        }
    };
    WheelPicker.OnItemSelectedListener thu_to_item_selected_hour=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {

            switch (picker.getId()) {
                case R.id.wheel_thu_to_hour:thu_to_hour=lst_hour.get(position);
                    break;
            }
            thuday_to_time=thu_to_hour+":"+thu_to_minute+" "+thu_to_ampm;
            tv_thu_to.setText(thuday_to_time);
            thursday_selected=true;
            showcheck(4);
        }
    };

    WheelPicker.OnItemSelectedListener thu_to_item_selected_minute=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_thu_to_minute:thu_to_minute=lst_minutes.get(position);
                    break;
            }
            thuday_to_time=thu_to_hour+":"+thu_to_minute+" "+thu_to_ampm;
            tv_thu_to.setText(thuday_to_time);
            thursday_selected=true;
            showcheck(4);
        }
    };
    WheelPicker.OnItemSelectedListener thu_to_item_selected_sec=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_thu_to_ampm:thu_to_ampm=lst_ampm.get(position);
                    break;
            }
            thuday_to_time=thu_to_hour+":"+thu_to_minute+" "+thu_to_ampm;
            tv_thu_to.setText(thuday_to_time);
            thursday_selected=true;
            showcheck(4);
        }
    };

    WheelPicker.OnItemSelectedListener fri_from_item_selected_hour=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {

            switch (picker.getId()) {
                case R.id.wheel_fri_from_hour:fri_from_hour=lst_hour.get(position);
                    break;
            }
            fri_from_time=fri_from_hour+":"+fri_from_minute+" "+fri_from_ampm;
            tv_fri_from.setText(fri_from_time);
            friday_selected=true;
            showcheck(5);
        }
    };

    WheelPicker.OnItemSelectedListener fri_from_item_selected_minute=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_fri_from_minute:fri_from_minute=lst_minutes.get(position);
                    break;
            }
            fri_from_time=fri_from_hour+":"+fri_from_minute+" "+fri_from_ampm;
            tv_fri_from.setText(fri_from_time);
            friday_selected=true;
            showcheck(5);
        }
    };
    WheelPicker.OnItemSelectedListener fri_from_item_selected_sec=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_fri_from_ampm:fri_from_ampm=lst_ampm.get(position);
                    break;
            }
            fri_from_time=fri_from_hour+":"+fri_from_minute+" "+fri_from_ampm;
            tv_fri_from.setText(fri_from_time);
            friday_selected=true;
            showcheck(5);
        }
    };
    WheelPicker.OnItemSelectedListener fri_to_item_selected_hour=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {

            switch (picker.getId()) {
                case R.id.wheel_fri_to_hour:fri_to_hour=lst_hour.get(position);
                    break;
            }
            friday_to_time=fri_to_hour+":"+fri_to_minute+" "+fri_to_ampm;
            tv_fri_to.setText(friday_to_time);
            friday_selected=true;
            showcheck(5);
        }
    };

    WheelPicker.OnItemSelectedListener fri_to_item_selected_minute=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_fri_to_minute:fri_to_minute=lst_minutes.get(position);
                    break;
            }
            friday_to_time=fri_to_hour+":"+fri_to_minute+" "+fri_to_ampm;
            tv_fri_to.setText(friday_to_time);
            friday_selected=true;
            showcheck(5);
        }
    };
    WheelPicker.OnItemSelectedListener fri_to_item_selected_sec=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_fri_to_ampm:fri_to_ampm=lst_ampm.get(position);
                    break;
            }
            friday_to_time=fri_to_hour+":"+fri_to_minute+" "+fri_to_ampm;
            tv_fri_to.setText(friday_to_time);
            friday_selected=true;
            showcheck(5);
        }
    };

    WheelPicker.OnItemSelectedListener sat_from_item_selected_hour=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {

            switch (picker.getId()) {
                case R.id.wheel_sat_from_hour:sat_from_hour=lst_hour.get(position);
                    break;
            }
            sat_from_time=sat_from_hour+":"+sat_from_minute+" "+sat_from_ampm;
            tv_sat_from.setText(sat_from_time);
            saturday_selected=true;
            showcheck(6);
        }
    };

    WheelPicker.OnItemSelectedListener sat_from_item_selected_minute=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_sat_from_minute:sat_from_minute=lst_minutes.get(position);
                    break;
            }
            sat_from_time=sat_from_hour+":"+sat_from_minute+" "+sat_from_ampm;
            tv_sat_from.setText(sat_from_time);
            saturday_selected=true;
            showcheck(6);
        }
    };
    WheelPicker.OnItemSelectedListener sat_from_item_selected_sec=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_sat_from_ampm:sat_from_ampm=lst_ampm.get(position);
                    break;
            }
            sat_from_time=sat_from_hour+":"+sat_from_minute+" "+sat_from_ampm;
            tv_sat_from.setText(sat_from_time);
            saturday_selected=true;
            showcheck(6);
        }
    };
    WheelPicker.OnItemSelectedListener sat_to_item_selected_hour=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {

            switch (picker.getId()) {
                case R.id.wheel_sat_to_hour:sat_to_hour=lst_hour.get(position);
                    break;
            }
            satday_to_time=sat_to_hour+":"+sat_to_minute+" "+sat_to_ampm;
            tv_sat_to.setText(satday_to_time);
            saturday_selected=true;
            showcheck(6);
        }
    };

    WheelPicker.OnItemSelectedListener sat_to_item_selected_minute=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_sat_to_minute:sat_to_minute=lst_minutes.get(position);
                    break;
            }
            satday_to_time=sat_to_hour+":"+sat_to_minute+" "+sat_to_ampm;
            tv_sat_to.setText(satday_to_time);
            saturday_selected=true;
            showcheck(6);
        }
    };
    WheelPicker.OnItemSelectedListener sat_to_item_selected_sec=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_sat_to_ampm:sat_to_ampm=lst_ampm.get(position);
                    break;
            }
            satday_to_time=sat_to_hour+":"+sat_to_minute+" "+sat_to_ampm;
            tv_sat_to.setText(satday_to_time);
            saturday_selected=true;
            showcheck(6);
        }
    };

    WheelPicker.OnItemSelectedListener sun_from_item_selected_hour=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {

            switch (picker.getId()) {
                case R.id.wheel_sun_from_hour:sun_from_hour=lst_hour.get(position);
                    break;
            }
            sun_from_time=sun_from_hour+":"+sun_from_minute+" "+sun_from_ampm;
            tv_sun_from.setText(sun_from_time);
            sunday_selected=true;
            showcheck(7);
        }
    };

    WheelPicker.OnItemSelectedListener sun_from_item_selected_minute=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_sun_from_minute:sun_from_minute=lst_minutes.get(position);
                    break;
            }
            sun_from_time=sun_from_hour+":"+sun_from_minute+" "+sun_from_ampm;
            tv_sun_from.setText(sun_from_time);
            sunday_selected=true;
            showcheck(7);
        }
    };
    WheelPicker.OnItemSelectedListener sun_from_item_selected_sec=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_sun_from_ampm:sun_from_ampm=lst_ampm.get(position);
                    break;
            }
            sun_from_time=sun_from_hour+":"+sun_from_minute+" "+sun_from_ampm;
            tv_sun_from.setText(sun_from_time);
            sunday_selected=true;
            showcheck(7);
        }
    };
    WheelPicker.OnItemSelectedListener sun_to_item_selected_hour=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {

            switch (picker.getId()) {
                case R.id.wheel_sun_to_hour:sun_to_hour=lst_hour.get(position);
                    break;
            }
            sunday_to_time=sun_to_hour+":"+sun_to_minute+" "+sun_to_ampm;
            tv_sun_to.setText(sunday_to_time);
            sunday_selected=true;
            showcheck(7);
        }
    };

    WheelPicker.OnItemSelectedListener sun_to_item_selected_minute=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_sun_to_minute:sun_to_minute=lst_minutes.get(position);
                    break;
            }
            sunday_to_time=sun_to_hour+":"+sun_to_minute+" "+sun_to_ampm;
            tv_sun_to.setText(sunday_to_time);
            sunday_selected=true;
            showcheck(7);
        }
    };
    WheelPicker.OnItemSelectedListener sun_to_item_selected_sec=new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_sun_to_ampm:sun_to_ampm=lst_ampm.get(position);
                    break;
            }
            sunday_to_time=sun_to_hour+":"+sun_to_minute+" "+sun_to_ampm;
            tv_sun_to.setText(sunday_to_time);
            sunday_selected=true;
            showcheck(7);
        }
    };
    String first_checked="";

    boolean monday_selected=false;
    boolean tuesday_selected=false;
    boolean wednesday_selected=false;
    boolean thursday_selected=false;
    boolean friday_selected=false;
    boolean saturday_selected=false;
    boolean sunday_selected=false;

    boolean wheel_changed=false;

    public void showcheck(int item){
        wheel_changed=true;
        switch (item){
            case 1:
//                    list_days_ll.get(0).setBackgroundResource(R.drawable.ll_oval);
//                    list_days_tv.get(0).setTextColor(Color.parseColor("#FFFFFF"));
                check_selected_from_time=mon_from_time;
                check_selected_to_time=monday_to_time;
                first_checked="mon";
                break;
            case 2:
//                list_days_ll.get(1).setBackgroundResource(R.drawable.ll_oval);
//                list_days_tv.get(1).setTextColor(Color.parseColor("#FFFFFF"));
                check_selected_from_time=tue_from_time;
                check_selected_to_time=tueday_to_time;
                first_checked="mon";
                break;
            case 3:
//                list_days_ll.get(2).setBackgroundResource(R.drawable.ll_oval);
//                list_days_tv.get(2).setTextColor(Color.parseColor("#FFFFFF"));
                check_selected_from_time=wed_from_time;
                check_selected_to_time=wedday_to_time;
                first_checked="wed";
                break;
            case 4:
//                list_days_ll.get(3).setBackgroundResource(R.drawable.ll_oval);
//                list_days_tv.get(3).setTextColor(Color.parseColor("#FFFFFF"));
                check_selected_from_time=thu_from_time;
                check_selected_to_time=thuday_to_time;
                first_checked="thu";
                break;
            case 5:
//                list_days_ll.get(4).setBackgroundResource(R.drawable.ll_oval);
//                list_days_tv.get(4).setTextColor(Color.parseColor("#FFFFFF"));
                check_selected_from_time=fri_from_time;
                check_selected_to_time=friday_to_time;
                first_checked="fri";
                break;
            case 6:
//                list_days_ll.get(5).setBackgroundResource(R.drawable.ll_oval);
//                list_days_tv.get(5).setTextColor(Color.parseColor("#FFFFFF"));
                check_selected_from_time=sat_from_time;
                check_selected_to_time=satday_to_time;
                first_checked="sat";
                break;
            case 7:
//                list_days_ll.get(6).setBackgroundResource(R.drawable.ll_oval);
//                list_days_tv.get(6).setTextColor(Color.parseColor("#FFFFFF"));
                check_selected_from_time=sun_from_time;
                check_selected_to_time=sunday_to_time;
                first_checked="sun";
                break;
        }
    }


    String check_selected_from_time="";
    String check_selected_to_time="";

}
