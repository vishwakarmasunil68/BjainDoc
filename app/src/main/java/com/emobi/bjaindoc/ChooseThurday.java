package com.emobi.bjaindoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Emobi-Android-002 on 11/22/2016.
 */
public class ChooseThurday extends Fragment {
    LinearLayout lineartop,linearmid,linearbottom;
    public static EditText txt_ht,ed1,ed2,ed3,ed4,ed5,ed6;
    ImageView mimageview,aimageview,eimageview;

    public static ListView listview1,listview2,listview3,listview4,listview5,listview6,listview7,listview8;
    public static String text = " ";
    public static String text1 = " ";
    public static String text2 = " ";
    public static String text3 = " ";
    public static String text4 = " ";
    public static String text5 = " ";
    public static String text6 = " ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.chhose_monday, container, false);

        lineartop =(LinearLayout)view.findViewById(R.id.lineartop);
        linearmid =(LinearLayout)view.findViewById(R.id.linearmid);
        linearbottom =(LinearLayout)view.findViewById(R.id.linearbottom);

        mimageview =(ImageView)view.findViewById(R.id.mimg);
        aimageview = (ImageView)view.findViewById(R.id.aimg);
        eimageview = (ImageView)view.findViewById(R.id.eimg);
        txt_ht = (EditText)view.findViewById(R.id.txt_ht);
        ed1 = (EditText)view.findViewById(R.id.ed_sta_mrng);
        ed2 = (EditText)view.findViewById(R.id.ed_end_mrng);
        ed3 = (EditText)view.findViewById(R.id.ed_sta1_mrng);
        ed4 = (EditText)view.findViewById(R.id.ed_end1_mrng);
        ed5 = (EditText)view.findViewById(R.id.ed_sta2_mrng);
        ed6 = (EditText)view.findViewById(R.id.ed_end2_mrng);

        listview1=(ListView)view.findViewById(R.id.listview1);
        listview2=(ListView)view.findViewById(R.id.listview2);
        listview3=(ListView)view.findViewById(R.id.listview3);
        listview4=(ListView)view.findViewById(R.id.listview4);
        listview5=(ListView)view.findViewById(R.id.listview5);
        listview6=(ListView)view.findViewById(R.id.listview6);

        String[] sports1 = getResources().getStringArray(R.array.Time_Pickersheetaftr);
        String[] sports2 = getResources().getStringArray(R.array.Time_Pickersheeteven);
        String[] sports = getResources().getStringArray(R.array.Time_Pickersheet);

        //Row layout defined by Android: android.R.layout.simple_list_item_1
        listview1.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, sports));

        listview2.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, sports));

        listview3.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, sports1));

        listview4.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, sports1));

        listview5.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, sports2));

        listview6.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, sports2));


        mimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lineartop.getVisibility()==View.VISIBLE){
                    lineartop.setVisibility(View.GONE);
                    mimageview.setImageDrawable(getResources().getDrawable(R.drawable.addplus));
                }
                else {
                    lineartop.setVisibility(View.VISIBLE);
                    mimageview.setImageDrawable(getResources().getDrawable(R.drawable.remomins));
                }
            }
        });

        aimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearmid.getVisibility()==View.VISIBLE){
                    linearmid.setVisibility(View.GONE);
                    aimageview.setImageDrawable(getResources().getDrawable(R.drawable.addplus));
                }
                else {
                    linearmid.setVisibility(View.VISIBLE);


                    aimageview.setImageDrawable(getResources().getDrawable(R.drawable.remomins));
                }
            }
        });

        eimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearbottom.getVisibility()==View.VISIBLE){
                    linearbottom.setVisibility(View.GONE);
                    eimageview.setImageDrawable(getResources().getDrawable(R.drawable.addplus));
                }
                else {

                    linearbottom.setVisibility(View.VISIBLE);
                    eimageview.setImageDrawable(getResources().getDrawable(R.drawable.remomins));
                }
            }
        });

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                text = parent.getItemAtPosition(position).toString();
                ed1.setText(text);
//                parent.getChildAt(position).setBackgroundColor(Color.parseColor("#479736"));
                Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
//                for (int i = 0; i < listview1.getChildCount(); i++) {
//                    if(position == i ){
//                        listview1.getSelectedItem(i).setBackgroundColor(Color.parseColor("#479736"));
//                    }else{
//                        listview1.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
//                    }
//                }
                int pos =position;
                Log.e("pos",pos+"");


//                Toast.makeText(getActivity(),text + pos + "",Toast.LENGTH_SHORT).show();

//                Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();



            }
        });
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                text2 = parent.getItemAtPosition(position).toString();
                ed2.setText(text2);
                Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                /*for (int i = 0; i < listview2.getChildCount(); i++) {
                    if(position == i ){
                        listview2.getChildAt(i).setBackgroundColor(Color.parseColor("#479736"));
                    }else{
                        listview2.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }*/
            }
        });
        listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                text3 = parent.getItemAtPosition(position).toString();
                ed3.setText(text3);
                Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                /*for (int i = 0; i < listview3.getChildCount(); i++) {
                    if(position == i ){
                        listview3.getChildAt(i).setBackgroundColor(Color.parseColor("#479736"));
                    }else{
                        listview3.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }*/
            }
        });
        listview4.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                text4 = parent.getItemAtPosition(position).toString();
                ed4.setText(text4);
                Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                /*for (int i = 0; i < listview4.getChildCount(); i++) {
                    if(position == i ){
                        listview4.getChildAt(i).setBackgroundColor(Color.parseColor("#479736"));
                    }else{
                        listview4.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }*/
            }
        });
        listview5.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                text5 = parent.getItemAtPosition(position).toString();
                ed5.setText(text5);
                Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                /*for (int i = 0; i < listview5.getChildCount(); i++) {
                    if(position == i ){
                        listview5.getChildAt(i).setBackgroundColor(Color.parseColor("#479736"));
                    }else{
                        listview5.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }*/
            }
        });
        listview6.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                text6 = parent.getItemAtPosition(position).toString();
                ed6.setText(text6);
                Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                /*for (int i = 0; i < listview6.getChildCount(); i++) {
                    if(position == i ){
                        listview6.getChildAt(i).setBackgroundColor(Color.parseColor("#479736"));
                    }else{
                        listview6.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }*/
            }
        });

        return  view;
    }
}










