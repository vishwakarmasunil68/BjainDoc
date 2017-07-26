package com.emobi.bjaindoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Emobi-Android-002 on 11/8/2016.
 */
public class AvailabiltyFragment extends Fragment{

    public static EditText mmrng,mafter,mevng,tmrng,tafter,tevng,wmrng,wafter,wevng,thmrng,thafter,thevng,fmrng,fafter,fevng,smrng,safter,sevng,sumrng,suafter,suevng;
    public static Spinner mmrng1,mmrng2,mafter1,mafter2,mevng1,mevng2,tmrng1,tmrng2,tafter1,tafter2,
            tevng1,tevng2,wmrng1,wmrng2,wafter1,wafter2,wevng1,wevng2,thmrng1,thmrng2,
            thafter1,thafter2,thevng1,thevng2,fmrng1,fmrng2,fafter1,fafter2,fevng1,fevng2,smrng1,smrng2,
            safter1,safter2,sevng1,sevng2,sumrng1,sumrng2,suafter1,suafter2,suevng1,suevng2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.availability, container, false);
//            Log.e("Shazia cal","sapan bhaiya");
        mmrng1=(Spinner)view.findViewById(R.id.sp_place_m_mrng1);
        mmrng2=(Spinner)view.findViewById(R.id.sp_place_m_mrng2);
        mafter1=(Spinner)view.findViewById(R.id.sp_place_m_after1);
        mafter2=(Spinner)view.findViewById(R.id.sp_place_m_after2);
        mevng1=(Spinner)view.findViewById(R.id.sp_place_m_evng1);
        mevng2=(Spinner)view.findViewById(R.id.sp_place_m_evng2);
        tmrng1=(Spinner)view.findViewById(R.id.sp_place_t_mrng1);
        tmrng2=(Spinner)view.findViewById(R.id.sp_place_t_mrng2);
        tafter1=(Spinner)view.findViewById(R.id.sp_place_t_after1);
        tafter2=(Spinner)view.findViewById(R.id.sp_place_t_after2);
        tevng1=(Spinner)view.findViewById(R.id.sp_place_t_evng1);
        tevng2=(Spinner)view.findViewById(R.id.sp_place_t_evng2);
        wmrng1=(Spinner)view.findViewById(R.id.sp_place_w_mrng1);
        wmrng2=(Spinner)view.findViewById(R.id.sp_place_w_mrng2);
        wafter1=(Spinner)view.findViewById(R.id.sp_place_w_after1);
        wafter2=(Spinner)view.findViewById(R.id.sp_place_w_after2);
        wevng1=(Spinner)view.findViewById(R.id.sp_place_w_evng1);
        wevng2=(Spinner)view.findViewById(R.id.sp_place_w_evng2);
        thmrng1=(Spinner)view.findViewById(R.id.sp_place_th_mrng1);
        thmrng2=(Spinner)view.findViewById(R.id.sp_place_th_mrng2);
        thafter1=(Spinner)view.findViewById(R.id.sp_place_th_after1);
        thafter2=(Spinner)view.findViewById(R.id.sp_place_th_after2);
        thevng1=(Spinner)view.findViewById(R.id.sp_place_th_evng1);
        thevng2=(Spinner)view.findViewById(R.id.sp_place_th_evng2);
        fmrng1=(Spinner)view.findViewById(R.id.sp_place_f_mrng1);
        fmrng2=(Spinner)view.findViewById(R.id.sp_place_f_mrng2);
        fafter1=(Spinner)view.findViewById(R.id.sp_place_f_after1);
        fafter2=(Spinner)view.findViewById(R.id.sp_place_f_after2);
        fevng1=(Spinner)view.findViewById(R.id.sp_place_f_evng1);
        fevng2=(Spinner)view.findViewById(R.id.sp_place_f_evng2);
        smrng1=(Spinner)view.findViewById(R.id.sp_place_s_mrng1);
        smrng2=(Spinner)view.findViewById(R.id.sp_place_s_mrng2);
        safter1=(Spinner)view.findViewById(R.id.sp_place_s_after1);
        safter2=(Spinner)view.findViewById(R.id.sp_place_s_after2);
        sevng1=(Spinner)view.findViewById(R.id.sp_place_s_evng1);
        sevng2=(Spinner)view.findViewById(R.id.sp_place_s_evng2);
        sumrng1=(Spinner)view.findViewById(R.id.sp_place_su_mrng1);
        sumrng2=(Spinner)view.findViewById(R.id.sp_place_su_mrng2);
        suafter1=(Spinner)view.findViewById(R.id.sp_place_su_after1);
        suafter2=(Spinner)view.findViewById(R.id.sp_place_su_after2);
        suevng1=(Spinner)view.findViewById(R.id.sp_place_su_evng1);
        suevng2=(Spinner)view.findViewById(R.id.sp_place_su_evng2);


        mmrng = (EditText) view.findViewById(R.id.ed_place_m_mrng);
        mafter = (EditText) view.findViewById(R.id.ed_place_m_after);
        mevng = (EditText) view.findViewById(R.id.ed_place_m_evng);
        tmrng = (EditText) view.findViewById(R.id.ed_place_t_mrng);
        tafter = (EditText)view. findViewById(R.id.ed_place_t_after);
        tevng = (EditText) view.findViewById(R.id.ed_place_t_evng);
        wmrng = (EditText) view.findViewById(R.id.ed_place_w_mrng);
        wafter = (EditText) view.findViewById(R.id.ed_place_w_after);
        wevng = (EditText)view. findViewById(R.id.ed_place_w_evng);
        thmrng = (EditText) view.findViewById(R.id.ed_place_th_mrng);
        thafter = (EditText)view. findViewById(R.id.ed_place_th_after);
        thevng = (EditText)view. findViewById(R.id.ed_place_th_evng);
        fmrng = (EditText) view.findViewById(R.id.ed_place_f_mrng);
        fafter = (EditText)view. findViewById(R.id.ed_place_f_after);
        fevng = (EditText) view.findViewById(R.id.ed_place_f_evng);
        smrng = (EditText)view. findViewById(R.id.ed_place_s_mrng);
        safter = (EditText)view. findViewById(R.id.ed_place_s_after);
        sevng = (EditText) view.findViewById(R.id.ed_place_s_evng);
        sumrng = (EditText)view. findViewById(R.id.ed_place_su_mrng);
        suafter = (EditText)view. findViewById(R.id.ed_place_su_after);
        suevng = (EditText) view.findViewById(R.id.ed_place_su_evng);


        // Show a timepicker when the timeButton is clicked


        return view;
    }


}
