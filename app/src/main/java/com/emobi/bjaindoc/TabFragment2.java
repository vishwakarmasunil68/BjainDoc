package com.emobi.bjaindoc;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by Emobi-Android-002 on 10/20/2016.
 */
public class TabFragment2 extends Fragment {
    public static EditText ed1,ed3, ed4, ed5, ed6, ed7,edtxt_descrption;
    public static RadioGroup rg1,rg2;
    public TextView shar;
    public static String three="noo";
    public static String two="noo";
    public static String one="noo";
    public static String rate1,rate2,reason;
    public static ImageView img_no1,img_no2,img_no3;
    public static ImageView img_yes1,img_yes2,img_yes3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.tab_fragment_2, container, false);

        edtxt_descrption = (EditText) view.findViewById(R.id.edtxt_descrption);
        shar = (TextView) view.findViewById(R.id.shar);
        img_no1 = (ImageView) view.findViewById(R.id.img_no1);
        img_no2 = (ImageView) view.findViewById(R.id.img_no2);
        img_no3 = (ImageView) view.findViewById(R.id.img_no3);
        img_yes1 = (ImageView) view.findViewById(R.id.img_yes1);
        img_yes2 = (ImageView) view.findViewById(R.id.img_yes2);
        img_yes3 = (ImageView) view.findViewById(R.id.img_yes3);
        rg2 = (RadioGroup) view.findViewById(R.id.p_rg_Condition);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        shar.setTypeface(tf);


         rate2="Normal";
        edtxt_descrption.setText(Categories_Fragment.description);



        img_no1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                one="no";
                img_no1.setImageDrawable(getResources().getDrawable(R.drawable.close));
                img_yes1.setImageDrawable(getResources().getDrawable(R.drawable.graytick));
            }
        });
        img_no2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                two="no";
                img_no2.setImageDrawable(getResources().getDrawable(R.drawable.close));
                img_yes2.setImageDrawable(getResources().getDrawable(R.drawable.graytick));
            }
        });
        img_no3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_no3.setImageDrawable(getResources().getDrawable(R.drawable.close));
                img_yes3.setImageDrawable(getResources().getDrawable(R.drawable.graytick));
                three="no";
            }
        });

        img_yes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_yes1.setImageDrawable(getResources().getDrawable(R.drawable.green_tick));
                img_no1.setImageDrawable(getResources().getDrawable(R.drawable.greyclose));
                one="yes";
            }
        });
        img_yes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_yes2.setImageDrawable(getResources().getDrawable(R.drawable.green_tick));
                img_no2.setImageDrawable(getResources().getDrawable(R.drawable.greyclose));
                two="yes";
            }
        });
        img_yes3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_yes3.setImageDrawable(getResources().getDrawable(R.drawable.green_tick));
                img_no3.setImageDrawable(getResources().getDrawable(R.drawable.greyclose));
                three="yes";
            }
        });
        if (Categories_Fragment.condition.equals("Normal")) {
            rg2.check(R.id.Normal);
            rate2="Normal";
        }
        else if (Categories_Fragment.condition.equals("Critical")){
            rg2.check(R.id.Critical);
            rate2="Critical";
        }
        else {
        }
if (Categories_Fragment.p_medication.equals(""))
{

}
        else if (Categories_Fragment.p_medication.equals("yes")){
    img_yes1.setImageDrawable(getResources().getDrawable(R.drawable.green_tick));
    img_no1.setImageDrawable(getResources().getDrawable(R.drawable.greyclose));
    img_yes2.setImageDrawable(getResources().getDrawable(R.drawable.green_tick));
    img_no2.setImageDrawable(getResources().getDrawable(R.drawable.greyclose));
    img_yes3.setImageDrawable(getResources().getDrawable(R.drawable.green_tick));
    img_no3.setImageDrawable(getResources().getDrawable(R.drawable.greyclose));
        }
else if (Categories_Fragment.p_medication.equals("no")){
    img_yes1.setImageDrawable(getResources().getDrawable(R.drawable.graytick));
    img_no1.setImageDrawable(getResources().getDrawable(R.drawable.close));
    img_yes2.setImageDrawable(getResources().getDrawable(R.drawable.graytick));
    img_no2.setImageDrawable(getResources().getDrawable(R.drawable.close));
    img_yes3.setImageDrawable(getResources().getDrawable(R.drawable.graytick));
    img_no3.setImageDrawable(getResources().getDrawable(R.drawable.close));
}

        if (Categories_Fragment.p_alergi.equals(""))
        {

        }
        else if (Categories_Fragment.p_alergi.equals("yes")){
            img_yes1.setImageDrawable(getResources().getDrawable(R.drawable.green_tick));
            img_no1.setImageDrawable(getResources().getDrawable(R.drawable.greyclose));
            img_yes2.setImageDrawable(getResources().getDrawable(R.drawable.green_tick));
            img_no2.setImageDrawable(getResources().getDrawable(R.drawable.greyclose));
            img_yes3.setImageDrawable(getResources().getDrawable(R.drawable.green_tick));
            img_no3.setImageDrawable(getResources().getDrawable(R.drawable.greyclose));
        }
        else if (Categories_Fragment.p_alergi.equals("no")){
            img_yes1.setImageDrawable(getResources().getDrawable(R.drawable.graytick));
            img_no1.setImageDrawable(getResources().getDrawable(R.drawable.close));
            img_yes2.setImageDrawable(getResources().getDrawable(R.drawable.graytick));
            img_no2.setImageDrawable(getResources().getDrawable(R.drawable.close));
            img_yes3.setImageDrawable(getResources().getDrawable(R.drawable.graytick));
            img_no3.setImageDrawable(getResources().getDrawable(R.drawable.close));
        }

        if (Categories_Fragment.p_digoxin.equals(""))
        {

        }
        else if (Categories_Fragment.p_digoxin.equals("yes")){
            img_yes1.setImageDrawable(getResources().getDrawable(R.drawable.green_tick));
            img_no1.setImageDrawable(getResources().getDrawable(R.drawable.greyclose));
            img_yes2.setImageDrawable(getResources().getDrawable(R.drawable.green_tick));
            img_no2.setImageDrawable(getResources().getDrawable(R.drawable.greyclose));
            img_yes3.setImageDrawable(getResources().getDrawable(R.drawable.green_tick));
            img_no3.setImageDrawable(getResources().getDrawable(R.drawable.greyclose));
        }
        else if (Categories_Fragment.p_digoxin.equals("no")){
            img_yes1.setImageDrawable(getResources().getDrawable(R.drawable.graytick));
            img_no1.setImageDrawable(getResources().getDrawable(R.drawable.close));
            img_yes2.setImageDrawable(getResources().getDrawable(R.drawable.graytick));
            img_no2.setImageDrawable(getResources().getDrawable(R.drawable.close));
            img_yes3.setImageDrawable(getResources().getDrawable(R.drawable.graytick));
            img_no3.setImageDrawable(getResources().getDrawable(R.drawable.close));
        }
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos1 = rg2.indexOfChild(view.findViewById(checkedId));
                /*Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();
                /*Intent intent1=new Intent(TimeSetActivity.this,DataMovement.class);
                intent1.putExtra("timeindex0",pos);
                startActivity(intent1);*/
                switch (pos1) {

                    case 0:
                        rate2 = "Normal";
                        break;
                    case 1:
                        rate2 = "Critical";
                        break;
                    default:
                        break;
                }
            }
        });

        return view;
    }
}
