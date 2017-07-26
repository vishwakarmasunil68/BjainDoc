package com.emobi.bjaindoc;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.bjaindoc.pojo.ClinicInfoPOJO;
import com.emobi.bjaindoc.pojo.ClinicInfoPOJOResult;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.emobi.bjaindoc.R.id.d_department;

/**
 * Created by sunil on 04-02-2017.
 */

public class UpdateDoctorFragment extends Fragment implements View.OnClickListener, WebServicesCallBack, CompoundButton.OnCheckedChangeListener {
    public static EditText dpmt, addr, deisgnation, degree, speciality, ed7,
            edtxt_descrption, et_doc_clinic_name, et_doc_clinc_address,
            et_doc_clinic_pincode, et_doc_clinic_city, et_doc_clinic_state,
            et_email, et_telephone, et_mobile;
    AutoCompleteTextView et_doc_clinic_country;
    public static final String REGISTER_URL = "http://www.bjain.com/doctor/registration.php";
    List<EditText> list_address_edittext = new ArrayList<>();
    TextView txt_pname;
    Button btn_Submit;
    RadioGroup rg1, rg2;
    public static String rate1, rate2, reason;
    ImageView more;
    public static String selectedImagePath;
    public static TextView ed2;
    CircleImageView profile_image;
    private static final int FILE_SELECT_CODE = 0;
    LinearLayout parent, ll_scroll_view;
    FloatingActionButton fab_timing;
    TextView fab_clinic;

    //    RecyclerView rv_clinic_info;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.updateinfodocadditional, container, false);

        more = (ImageView) view.findViewById(R.id.more);
        dpmt = (EditText) view.findViewById(d_department);
        addr = (EditText) view.findViewById(R.id.edtxt_mobile);
        deisgnation = (EditText) view.findViewById(R.id.edtxt_ppassword);
//        deisgnation = (EditText) view.findViewById(R.id.edtxt_mobile);

        et_doc_clinic_name = (EditText) view.findViewById(R.id.et_doc_clinic_name);
        et_doc_clinc_address = (EditText) view.findViewById(R.id.et_doc_clinc_address);
        et_doc_clinic_pincode = (EditText) view.findViewById(R.id.et_doc_clinic_pincode);
        et_doc_clinic_city = (EditText) view.findViewById(R.id.et_doc_clinic_city);
        et_doc_clinic_state = (EditText) view.findViewById(R.id.et_doc_clinic_state);
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_mobile = (EditText) view.findViewById(R.id.et_mobile);
        et_telephone = (EditText) view.findViewById(R.id.et_telephone);
        et_doc_clinic_country = (AutoCompleteTextView) view.findViewById(R.id.et_doc_clinic_country);
        ll_scroll_view = (LinearLayout) view.findViewById(R.id.ll_scroll_view);


        fab_clinic = (TextView) view.findViewById(R.id.fab_clinic);
        fab_timing = (FloatingActionButton) view.findViewById(R.id.fab_timing);
//        rv_clinic_info= (RecyclerView) view.findViewById(rv_clinic_info);
        GetALlClinicInfo();
        fab_clinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                clearData();
//                if (isUpdatingclinic) {
//                    CallWebService(true);
//                } else {
//                    CallWebService(false);
//                }
                if (et_doc_clinic_name.getText().toString().isEmpty() || et_mobile.getText().toString().isEmpty() ||
                        et_doc_clinc_address.getText().toString().isEmpty() || et_doc_clinic_pincode.getText().toString().isEmpty()
                        || et_doc_clinic_city.getText().toString().isEmpty() || et_doc_clinic_state.getText().toString().isEmpty() ||
                        et_doc_clinic_country.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please Enter Required Fields", Toast.LENGTH_LONG).show();
                } else {
                    if (et_email.getText().toString().isEmpty()) {
                        showTimings();
                    } else {
                        if (isValidEmailAddress(et_email.getText().toString())) {
                            showTimings();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Please Enter Valid Email", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        fab_timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_doc_clinic_name.getText().toString().isEmpty() || et_mobile.getText().toString().isEmpty() ||
                        et_doc_clinc_address.getText().toString().isEmpty() || et_doc_clinic_pincode.getText().toString().isEmpty()
                        || et_doc_clinic_city.getText().toString().isEmpty() || et_doc_clinic_state.getText().toString().isEmpty() ||
                        et_doc_clinic_country.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please Enter Required Fields", Toast.LENGTH_LONG).show();
                } else {
                    showTimings();
                }
            }
        });

        parent = (LinearLayout) view.findViewById(R.id.parentmain);
        degree = (EditText) view.findViewById(R.id.edtxt_d_degree);
        speciality = (EditText) view.findViewById(R.id.edtxt_d_specialist);
        txt_pname = (TextView) view.findViewById(R.id.txt_pname);

        profile_image = (CircleImageView) view.findViewById(R.id.img_profile);

        reason = PreferenceData.getPatientId(getActivity());
//        dpmt.setText(MainActivityDoctorProfile.reg_department);
//        addr.setText(MainActivityDoctorProfile.reg_clinic_address);
//        deisgnation.setText(MainActivityDoctorProfile.reg_designation);
//        degree.setText(MainActivityDoctorProfile.reg_degree);
//        speciality.setText(MainActivityDoctorProfile.reg_specialist);


        Typeface tf1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        dpmt.setTypeface(tf1);
        addr.setTypeface(tf1);
        deisgnation.setTypeface(tf1);
        speciality.setTypeface(tf1);


//        ed4.setText(Categories_Fragment.p_age);
//        ed5.setText(Categories_Fragment.p_bloodgroup);
//        ed6.setText(Categories_Fragment.p_weight);
//        ed7.setText(Categories_Fragment.p_height);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, FILE_SELECT_CODE);
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout ll = new LinearLayout(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.gravity= Gravity.RIGHT;
                ll.setLayoutParams(params);

                parent.addView(ll);
                EditText edittext = new EditText(getActivity());
                edittext.setLayoutParams(params);
                edittext.setHint("add address");


                list_address_edittext.add(edittext);

                ll.addView(edittext);
            }
        });
        ArrayAdapter<String> adapter = getCountriesAdapter(getActivity().getApplicationContext());
        et_doc_clinic_country.setAdapter(adapter);


        return view;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private ArrayAdapter<String> getCountriesAdapter(Context context) {
//        String[] addresses = new String[accounts.length];
        List<String> list_countries = Arrays.asList(getResources().getStringArray(R.array.countries_array));
        return new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, list_countries);
    }

    public void getPojo(ClinicInfoPOJOResult result) {
        Log.d(TAG, result.toString());
        if (result != null) {
            isUpdatingclinic = true;
            clinicInfoPOJOResult = result;
            ll_scroll_view.setVisibility(View.GONE);
            fab_clinic.setVisibility(View.GONE);
        }
        et_doc_clinic_name.setText(result.getClinic_name());
        et_doc_clinc_address.setText(result.getClinic_address());
        et_doc_clinic_pincode.setText(result.getClinic_pincode());
        et_doc_clinic_city.setText(result.getClinic_city());
        et_doc_clinic_state.setText(result.getClinic_state());
        et_doc_clinic_country.setText(result.getClinic_country());
        et_email.setText(result.getClinic_email());
        et_mobile.setText(result.getClinic_mobile());
        et_telephone.setText(result.getClinic_telephone());
        dpmt.setText(result.getClinic_department());
        deisgnation.setText(result.getClinic_designation());
        degree.setText(result.getClinic_degree());
        speciality.setText(result.getClinic_specialist());
        boolean mon_visibility = checkVisibility(result.getMon_mor_starttime(), result.getMon_mor_endtime(), result.getMon_eve_startime(), result.getMon_eve_endtime());
        boolean Tue_visibility = checkVisibility(result.getTue_mor_starttime(), result.getTue_mor_endtime(), result.getTue_eve_startime(), result.getTue_eve_endtime());
        boolean Wed_visibility = checkVisibility(result.getWed_mor_starttime(), result.getWed_mor_endtime(), result.getWed_eve_startime(), result.getWed_eve_endtime());
        boolean Thus_visibility = checkVisibility(result.getThus_mor_starttime(), result.getThus_mor_endtime(), result.getThus_eve_startime(), result.getThus_eve_endtime());
        boolean Fri_visibility = checkVisibility(result.getFri_mor_starttime(), result.getFri_mor_endtime(), result.getFri_eve_startime(), result.getFri_eve_endtime());
        boolean Sat_visibility = checkVisibility(result.getSat_mor_starttime(), result.getSat_mor_endtime(), result.getSat_eve_startime(), result.getSat_eve_endtime());
        boolean Sun_visibility = checkVisibility(result.getSun_mor_starttime(), result.getSun_mor_endtime(), result.getSun_eve_startime(), result.getSun_eve_endtime());
//        save_linear[0] = mon_visibility;
//        save_linear[1] = Tue_visibility;
//        save_linear[2] = Wed_visibility;
//        save_linear[3] = Thus_visibility;
//        save_linear[4] = Fri_visibility;
//        save_linear[5] = Sat_visibility;
//        save_linear[6] = Sun_visibility;
//
//        first_selected_pos = getFirstSelectedPosition();
//        showTimings();
        is_updating = true;
        is_Start_Editing = true;
        setData(result);
    }

    public void setData(ClinicInfoPOJOResult result) {
        mon_mrng_start_time = result.getMon_mor_starttime();
        mon_mrng_end_time = result.getMon_mor_endtime();
        mon_mrng_time_interval = result.getMon_interval_mor();
        mon_eve_start_time = result.getMon_eve_startime();
        mon_eve_end_time = result.getMon_eve_endtime();
        mon_eve_time_interval = result.getMon_interval_eve();

        tue_mrng_start_time = result.getTue_mor_starttime();
        tue_mrng_end_time = result.getTue_mor_endtime();
        tue_mrng_time_interval = result.getTue_interval_mor();
        tue_eve_start_time = result.getTue_eve_startime();
        tue_eve_end_time = result.getTue_eve_endtime();
        tue_eve_time_interval = result.getTue_interval_eve();


        wed_mrng_start_time = result.getWed_mor_starttime();
        wed_mrng_end_time = result.getWed_mor_endtime();
        wed_mrng_time_interval = result.getWed_interval_mor();
        wed_eve_start_time = result.getWed_eve_startime();
        wed_eve_end_time = result.getWed_eve_endtime();
        wed_eve_time_interval = result.getWed_interval_eve();

        thu_mrng_start_time = result.getThus_mor_starttime();
        thu_mrng_end_time = result.getThus_mor_endtime();
        thu_mrng_time_interval = result.getThus_interval_mor();
        thu_eve_start_time = result.getThus_eve_startime();
        thu_eve_end_time = result.getThus_eve_endtime();
        thu_eve_time_interval = result.getThus_interval_eve();


        fri_mrng_start_time = result.getFri_mor_starttime();
        fri_mrng_end_time = result.getFri_mor_endtime();
        fri_mrng_time_interval = result.getFri_interval_mor();
        fri_eve_start_time = result.getFri_eve_startime();
        fri_eve_end_time = result.getFri_eve_endtime();
        fri_eve_time_interval = result.getFri_interval_eve();


        sat_mrng_start_time = result.getSat_mor_starttime();
        sat_mrng_end_time = result.getSat_mor_endtime();
        sat_mrng_time_interval = result.getSat_interval_mor();
        sat_eve_start_time = result.getSat_eve_startime();
        sat_eve_end_time = result.getSat_eve_endtime();
        sat_eve_time_interval = result.getSat_interval_eve();


        sun_mrng_start_time = result.getSun_mor_starttime();
        sun_mrng_end_time = result.getSun_mor_endtime();
        sun_mrng_time_interval = result.getSun_interval_mor();
        sun_eve_start_time = result.getSun_eve_startime();
        sun_eve_end_time = result.getSun_eve_endtime();
        sun_eve_time_interval = result.getSun_interval_eve();

    }


    ClinicInfoPOJOResult clinicInfoPOJOResult;


    public boolean isUpdatingclinic = false;

    public boolean checkVisibility(String mrng_start_time, String mrng_end_time, String eve_start_time, String eve_end_time) {
        if (!mrng_start_time.equals("")
                || !mrng_end_time.equals("")
                || !eve_start_time.equals("")
                || !eve_end_time.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void CallWebService(boolean isupdating) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (isupdating) {
            nameValuePairs.add(new BasicNameValuePair("doctor_clinic_id", clinicInfoPOJOResult.getDoctor_clinic_id()));
        }
        Log.d(TAG, "doctor id:-" + PreferenceData.getId(getActivity()));
        nameValuePairs.add(new BasicNameValuePair("doctor_id", PreferenceData.getId(getActivity())));
        nameValuePairs.add(new BasicNameValuePair("clinic_name", et_doc_clinic_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_address", et_doc_clinc_address.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_pincode", et_doc_clinic_pincode.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_city", et_doc_clinic_city.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_state", et_doc_clinic_state.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_country", et_doc_clinic_country.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_department", dpmt.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_designation", deisgnation.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_degree", degree.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_specialist", speciality.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("mon_mor_starttime", ValidateTimings(mon_mrng_start_time)));
        nameValuePairs.add(new BasicNameValuePair("mon_mor_endtime", ValidateTimings(mon_mrng_end_time)));
        nameValuePairs.add(new BasicNameValuePair("mon_interval_mor", mon_mrng_time_interval));
        nameValuePairs.add(new BasicNameValuePair("mon_eve_startime", ValidateTimings(mon_eve_start_time)));
        nameValuePairs.add(new BasicNameValuePair("mon_eve_endtime", ValidateTimings(mon_eve_end_time)));
        nameValuePairs.add(new BasicNameValuePair("mon_interval_eve", mon_eve_time_interval));
        nameValuePairs.add(new BasicNameValuePair("tue_mor_starttime", ValidateTimings(tue_mrng_start_time)));
        nameValuePairs.add(new BasicNameValuePair("tue_mor_endtime", ValidateTimings(tue_mrng_end_time)));
        nameValuePairs.add(new BasicNameValuePair("tue_interval_mor", tue_mrng_time_interval));
        nameValuePairs.add(new BasicNameValuePair("tue_eve_startime", ValidateTimings(tue_eve_start_time)));
        nameValuePairs.add(new BasicNameValuePair("tue_eve_endtime", ValidateTimings(tue_eve_end_time)));
        nameValuePairs.add(new BasicNameValuePair("tue_interval_eve", tue_eve_time_interval));
        nameValuePairs.add(new BasicNameValuePair("wed_mor_starttime", ValidateTimings(wed_mrng_start_time)));
        nameValuePairs.add(new BasicNameValuePair("wed_mor_endtime", ValidateTimings(wed_mrng_end_time)));
        nameValuePairs.add(new BasicNameValuePair("wed_interval_mor", wed_mrng_time_interval));
        nameValuePairs.add(new BasicNameValuePair("wed_eve_startime", ValidateTimings(wed_eve_start_time)));
        nameValuePairs.add(new BasicNameValuePair("wed_eve_endtime", ValidateTimings(wed_eve_end_time)));
        nameValuePairs.add(new BasicNameValuePair("wed_interval_eve", wed_eve_time_interval));
        nameValuePairs.add(new BasicNameValuePair("thus_mor_starttime", ValidateTimings(thu_mrng_start_time)));
        nameValuePairs.add(new BasicNameValuePair("thus_mor_endtime", ValidateTimings(thu_mrng_end_time)));
        nameValuePairs.add(new BasicNameValuePair("thus_interval_mor", thu_mrng_time_interval));
        nameValuePairs.add(new BasicNameValuePair("thus_eve_startime", ValidateTimings(thu_eve_start_time)));
        nameValuePairs.add(new BasicNameValuePair("thus_eve_endtime", ValidateTimings(thu_eve_end_time)));
        nameValuePairs.add(new BasicNameValuePair("thus_interval_eve", thu_eve_time_interval));
        nameValuePairs.add(new BasicNameValuePair("fri_mor_starttime", ValidateTimings(fri_mrng_start_time)));
        nameValuePairs.add(new BasicNameValuePair("fri_mor_endtime", ValidateTimings(fri_mrng_end_time)));
        nameValuePairs.add(new BasicNameValuePair("fri_interval_mor", fri_mrng_time_interval));
        nameValuePairs.add(new BasicNameValuePair("fri_eve_startime", ValidateTimings(fri_eve_start_time)));
        nameValuePairs.add(new BasicNameValuePair("fri_eve_endtime", ValidateTimings(fri_eve_end_time)));
        nameValuePairs.add(new BasicNameValuePair("fri_interval_eve", fri_eve_time_interval));
        nameValuePairs.add(new BasicNameValuePair("sat_mor_starttime", ValidateTimings(sat_mrng_start_time)));
        nameValuePairs.add(new BasicNameValuePair("sat_mor_endtime", ValidateTimings(sat_mrng_end_time)));
        nameValuePairs.add(new BasicNameValuePair("sat_interval_mor", sat_mrng_time_interval));
        nameValuePairs.add(new BasicNameValuePair("sat_eve_startime", ValidateTimings(sat_eve_start_time)));
        nameValuePairs.add(new BasicNameValuePair("sat_eve_endtime", ValidateTimings(sat_eve_end_time)));
        nameValuePairs.add(new BasicNameValuePair("sat_interval_eve", sat_eve_time_interval));
        nameValuePairs.add(new BasicNameValuePair("sun_mor_starttime", ValidateTimings(sun_mrng_start_time)));
        nameValuePairs.add(new BasicNameValuePair("sun_mor_endtime", ValidateTimings(sun_mrng_end_time)));
        nameValuePairs.add(new BasicNameValuePair("sun_interval_mor", sun_mrng_time_interval));
        nameValuePairs.add(new BasicNameValuePair("sun_eve_startime", ValidateTimings(sun_eve_start_time)));
        nameValuePairs.add(new BasicNameValuePair("sun_eve_endtime", ValidateTimings(sun_eve_end_time)));
        nameValuePairs.add(new BasicNameValuePair("sun_interval_eve", sun_eve_time_interval));
        nameValuePairs.add(new BasicNameValuePair("clinic_mobile", et_mobile.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_email", et_email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_telephone", et_telephone.getText().toString()));

        if (isupdating) {
            new WebServiceBase(nameValuePairs, getActivity(), this, "updating").execute("http://www.bjain.com/doctor/update_doctor_clinic.php");
        } else {
            new WebServiceBase(nameValuePairs, getActivity(), this, "api1").execute("http://www.bjain.com/doctor/doctor_clinic.php");
        }
    }

    public String ValidateTimings(String timings) {
        try {
            if (timings.length() != 8) {
                String split[] = timings.split(" ");
                String time = split[0];
                String ampm = split[1];
                if (time.length() == 3) {
                    return time + "00 " + ampm;
                } else {
                    if (time.length() == 4) {
                        return time + "0 " + ampm;
                    } else {
                        if (time.length() == 1) {
                            return " " + time + ":00 " + ampm;
                        } else {
                            return timings;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return timings;
        }
        return timings;
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        Log.d(TAG, "response:-" + response);
        switch (apicall) {
            case "api1":
                parseResponse(response, false);
                break;
            case "updating":
                parseResponse(response, true);
                break;
            case "api3":
                parseResponse(response);
                break;
        }
    }

    public void GetALlClinicInfo() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("doctor_id", PreferenceData.getId(getActivity())));
        new WebServiceBase(nameValuePairs, getActivity(), this, "api3").execute("http://www.bjain.com/doctor/doctor_cliniclist.php");

    }

    public void parseResponse(String response) {
        Gson gson = new Gson();
        ClinicInfoPOJO pojo = gson.fromJson(response, ClinicInfoPOJO.class);
        if (pojo != null) {
            try {
                if (pojo.getResult() != null && pojo.getResult().size() > 0) {
//                    rv_clinic_info.setVisibility(View.VISIBLE);
//                    HorizontalAdapter adapter = new HorizontalAdapter(getActivity(), getActivity(), pojo.getResult());
//
//                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//                    rv_clinic_info.setHasFixedSize(true);
//                    rv_clinic_info.setLayoutManager(layoutManager);
//
//                    rv_clinic_info.setAdapter(adapter);
                    Log.d(TAG, "clinic_pojos:-" + pojo.getResult().toString());
                    inflateClinicInfoView(pojo.getResult());
                } else {
//                    rv_clinic_info.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }

    public void UpdateClinicInfo() {
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setContentView(R.layout.dilog_time_edit_confirm);
        dialog1.setTitle("Timings");
        dialog1.show();
        dialog1.setCancelable(true);
        Button btn_ok = (Button) dialog1.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) dialog1.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getAllTimings();
                is_updating = true;
                if (isUpdatingclinic) {
                    CallWebService(true);
                } else {
                    CallWebService(false);
                }

                dialog1.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                showTimings();
            }
        });
        checkPreviousTimings();

    }


    public void inflateClinicInfoView(final List<ClinicInfoPOJOResult> list_pojos) {
        for (int i = 0; i < list_pojos.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_clinic_info, null);
            CardView cv_clinic;
            ImageView imageView;
            final LinearLayout linearLayout;
            TextView tv_clinic_name;
            TextView tv_clinic_address;
            TextView tv_city;
            TextView tv_pin_code;
            TextView tv_degree;
            TextView tv_specialist;
            TextView tv_mon_mrng_time;
            TextView tv_mon_eve_time;
            TextView tv_tue_mrng_time;
            TextView tv_tue_eve_time;
            TextView tv_wed_mrng_time;
            TextView tv_wed_eve_time;
            TextView tv_thu_mrng_time;
            TextView tv_thu_eve_time;
            TextView tv_fri_mrng_time;
            TextView tv_fri_eve_time;
            TextView tv_sat_mrng_time;
            TextView tv_sat_eve_time;
            TextView tv_sun_mrng_time;
            TextView tv_sun_eve_time;
            TextView tv_edit_timings;
            final TextView tv_show_timings;

            LinearLayout ll_mon;
            LinearLayout ll_tue;
            LinearLayout ll_wed;
            LinearLayout ll_thu;
            LinearLayout ll_fri;
            LinearLayout ll_sat;
            LinearLayout ll_sun;

            cv_clinic = (CardView) view.findViewById(R.id.cv_clinic);
            imageView = (ImageView) view.findViewById(R.id.scrollableimg);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout);
            tv_clinic_name = (TextView) view.findViewById(R.id.tv_clinic_name);
            tv_clinic_address = (TextView) view.findViewById(R.id.tv_clinic_address);
            tv_city = (TextView) view.findViewById(R.id.tv_city);
            tv_pin_code = (TextView) view.findViewById(R.id.tv_pin_code);
            tv_degree = (TextView) view.findViewById(R.id.tv_degree);
            tv_specialist = (TextView) view.findViewById(R.id.tv_specialist);
            tv_mon_mrng_time = (TextView) view.findViewById(R.id.tv_mon_mrng_time);
            tv_mon_eve_time = (TextView) view.findViewById(R.id.tv_mon_eve_time);
            tv_tue_mrng_time = (TextView) view.findViewById(R.id.tv_tue_mrng_time);
            tv_tue_eve_time = (TextView) view.findViewById(R.id.tv_tue_eve_time);
            tv_wed_mrng_time = (TextView) view.findViewById(R.id.tv_wed_mrng_time);
            tv_thu_mrng_time = (TextView) view.findViewById(R.id.tv_thu_mrng_time);
            tv_thu_eve_time = (TextView) view.findViewById(R.id.tv_thu_eve_time);
            tv_wed_eve_time = (TextView) view.findViewById(R.id.tv_wed_eve_time);
            tv_fri_mrng_time = (TextView) view.findViewById(R.id.tv_fri_mrng_time);
            tv_fri_eve_time = (TextView) view.findViewById(R.id.tv_fri_eve_time);
            tv_sat_mrng_time = (TextView) view.findViewById(R.id.tv_sat_mrng_time);
            tv_sat_eve_time = (TextView) view.findViewById(R.id.tv_sat_eve_time);
            tv_sun_mrng_time = (TextView) view.findViewById(R.id.tv_sun_mrng_time);
            tv_sun_eve_time = (TextView) view.findViewById(R.id.tv_sun_eve_time);
            tv_edit_timings = (TextView) view.findViewById(R.id.tv_edit_timings);
            tv_show_timings = (TextView) view.findViewById(R.id.tv_show_timings);
            ll_mon = (LinearLayout) view.findViewById(R.id.ll_mon);
            ll_tue = (LinearLayout) view.findViewById(R.id.ll_tue);
            ll_wed = (LinearLayout) view.findViewById(R.id.ll_wed);
            ll_thu = (LinearLayout) view.findViewById(R.id.ll_thu);
            ll_fri = (LinearLayout) view.findViewById(R.id.ll_fri);
            ll_sat = (LinearLayout) view.findViewById(R.id.ll_sat);
            ll_sun = (LinearLayout) view.findViewById(R.id.ll_sun);

            ClinicInfoPOJOResult pojo = list_pojos.get(i);

            tv_clinic_name.setText(pojo.getClinic_name());
            tv_clinic_address.setText(pojo.getClinic_address());
            tv_city.setText(pojo.getClinic_city());
            tv_pin_code.setText(pojo.getClinic_pincode());
            tv_degree.setText(pojo.getClinic_degree());
            tv_specialist.setText(pojo.getClinic_specialist());


            String mon_mrng_time = ValidateString(pojo.getMon_mor_starttime()) + "-" + ValidateString(pojo.getMon_mor_endtime());
            String mon_eve_time = ValidateString(pojo.getMon_eve_startime()) + "-" + ValidateString(pojo.getMon_eve_endtime());
            String Tue_mrng_time = ValidateString(pojo.getTue_mor_starttime()) + "-" + ValidateString(pojo.getTue_mor_endtime());
            String Tue_eve_time = ValidateString(pojo.getTue_eve_startime()) + "-" + ValidateString(pojo.getTue_eve_endtime());
            String Wed_mrng_time = ValidateString(pojo.getWed_mor_starttime()) + "-" + ValidateString(pojo.getWed_mor_endtime());
            String Wed_eve_time = ValidateString(pojo.getWed_eve_startime()) + "-" + ValidateString(pojo.getWed_eve_endtime());
            String Thus_mrng_time = ValidateString(pojo.getThus_mor_starttime()) + "-" + ValidateString(pojo.getThus_mor_endtime());
            String Thus_eve_time = ValidateString(pojo.getThus_eve_startime()) + "-" + ValidateString(pojo.getThus_eve_endtime());
            String Fri_mrng_time = ValidateString(pojo.getFri_mor_starttime()) + "-" + ValidateString(pojo.getFri_mor_endtime());
            String Fri_eve_time = ValidateString(pojo.getFri_eve_startime()) + "-" + ValidateString(pojo.getFri_eve_endtime());
            String Sat_mrng_time = ValidateString(pojo.getSat_mor_starttime()) + "-" + ValidateString(pojo.getSat_mor_endtime());
            String Sat_eve_time = ValidateString(pojo.getSat_eve_startime()) + "-" + ValidateString(pojo.getSat_eve_endtime());
            String Sun_mrng_time = ValidateString(pojo.getSun_mor_starttime()) + "-" + ValidateString(pojo.getSun_mor_endtime());
            String Sun_eve_time = ValidateString(pojo.getSun_eve_startime()) + "-" + ValidateString(pojo.getSun_eve_endtime());


            if (ValidateNewString(pojo.getMon_mor_starttime(), pojo.getMon_mor_endtime(), pojo.getMon_eve_startime(), pojo.getMon_eve_endtime())) {
                ll_mon.setVisibility(View.VISIBLE);
            } else {
                ll_mon.setVisibility(View.GONE);
            }
            if (ValidateNewString(pojo.getTue_mor_starttime(), pojo.getTue_mor_endtime(), pojo.getTue_eve_startime(), pojo.getTue_eve_endtime())) {
                ll_tue.setVisibility(View.VISIBLE);
            } else {
                ll_tue.setVisibility(View.GONE);
            }
            if (ValidateNewString(pojo.getWed_mor_starttime(), pojo.getWed_mor_endtime(), pojo.getWed_eve_startime(), pojo.getWed_eve_endtime())) {
                ll_wed.setVisibility(View.VISIBLE);
            } else {
                ll_wed.setVisibility(View.GONE);
            }
            if (ValidateNewString(pojo.getThus_mor_starttime(), pojo.getThus_mor_endtime(), pojo.getThus_eve_startime(), pojo.getThus_eve_endtime())) {
                ll_thu.setVisibility(View.VISIBLE);
            } else {
                ll_thu.setVisibility(View.GONE);
            }
            if (ValidateNewString(pojo.getFri_mor_starttime(), pojo.getFri_mor_endtime(), pojo.getFri_eve_startime(), pojo.getFri_eve_endtime())) {
                ll_fri.setVisibility(View.VISIBLE);
            } else {
                ll_fri.setVisibility(View.GONE);
            }
            if (ValidateNewString(pojo.getSat_mor_starttime(), pojo.getSat_mor_endtime(), pojo.getSat_eve_startime(), pojo.getSat_eve_endtime())) {
                ll_sat.setVisibility(View.VISIBLE);
            } else {
                ll_sat.setVisibility(View.GONE);
            }
            if (ValidateNewString(pojo.getSun_mor_starttime(), pojo.getSun_mor_endtime(), pojo.getSun_eve_startime(), pojo.getSun_eve_endtime())) {
                ll_sun.setVisibility(View.VISIBLE);
            } else {
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

            tv_show_timings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tv_show_timings.getText().toString().equals("Show")) {
                        tv_show_timings.setText("Hide");
                        linearLayout.setVisibility(View.VISIBLE);
                    } else {
                        if (tv_show_timings.getText().toString().equals("Hide")) {
                            tv_show_timings.setText("Show");
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                }
            });
            final int finalI = i;
            tv_edit_timings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        UpdateByDoc doc = (UpdateByDoc) getActivity();
                        doc.show_save = true;
                    } catch (Exception e) {

                    }
                    UpdateByDoc updateByDoc = (UpdateByDoc) getActivity();
                    updateByDoc.SendToUpdateFragment(list_pojos.get(finalI));
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (linearLayout.getVisibility() == View.VISIBLE) {
                        linearLayout.setVisibility(View.GONE);
                    } else {
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
            });

            ll_scroll_view.addView(view);
        }
    }

    public void parseResponse(String response, boolean updated) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.optString("success");
            if (success.equals("true")) {
                if (updated) {
                    Toast.makeText(getActivity().getApplicationContext(), "Clinic Updated", Toast.LENGTH_LONG).show();
                    JSONObject result = jsonObject.optJSONObject("result");
                    clearData();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Clinic Saved", Toast.LENGTH_LONG).show();
                    JSONObject result = jsonObject.optJSONObject("result");
                    clearData();
                }
                Intent intent = new Intent(getActivity(), UpdateByDoc.class);
                intent.putExtra("position", 1);
                startActivity(intent);
                getActivity().finish();
            } else {
                Intent intent = new Intent(getActivity(), UpdateByDoc.class);
                intent.putExtra("position", 1);
                startActivity(intent);
                getActivity().finish();
                Toast.makeText(getActivity().getApplicationContext(), "No Changes made to clinic.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {

        }
    }

    public String ValidateString(String s) {
        if (s.equals("")) {
            return "00:00";
        } else {
            return s;
        }
    }

    public boolean ValidateNewString(String start_mrng_time, String end_mrng_time, String eve_start_time, String eve_end_time) {
        if (!start_mrng_time.equals("") || !end_mrng_time.equals("") || !eve_start_time.equals("") || !eve_end_time.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void clearData() {
        et_doc_clinic_name.setText("");
        et_doc_clinc_address.setText("");
        et_doc_clinic_pincode.setText("");
        et_doc_clinic_city.setText("");
        et_doc_clinic_state.setText("");
        et_doc_clinic_country.setText("");
        dpmt.setText("");
        deisgnation.setText("");
        degree.setText("");
        speciality.setText("");
        isUpdatingclinic = false;
        clinicInfoPOJOResult = null;
    }


    private List<LinearLayout> list_linear_box = new ArrayList<>();
    private List<View> list_linear_layouts = new ArrayList<>();
    private List<Switch> list_switches = new ArrayList<>();
    private boolean[] selected_layouts = new boolean[7];
    private List<String> list_first_timings = new ArrayList<>();
    private List<String> list_first_update_mon_time = new ArrayList<>();
    private List<String> list_first_update_tue_time = new ArrayList<>();
    private List<String> list_first_update_wed_time = new ArrayList<>();
    private List<String> list_first_update_thu_time = new ArrayList<>();
    private List<String> list_first_update_fri_time = new ArrayList<>();
    private List<String> list_first_update_sat_time = new ArrayList<>();
    private List<String> list_first_update_sun_time = new ArrayList<>();


    EditText et_mon_mrng_start_time, et_mon_mrng_end_time, et_mon_mrng_time_interval,
            et_mon_eve_start_timing, et_mon_eve_end_time, et_mon_eve_time_interval,
            et_tue_mrng_start_time, et_tue_mrng_end_time, et_tue_mrng_time_interval,
            et_tue_eve_start_timing, et_tue_eve_end_time, et_tue_eve_time_interval,
            et_wed_mrng_start_time, et_wed_mrng_end_time, et_wed_mrng_time_interval,
            et_wed_eve_start_timing, et_wed_eve_end_time, et_wed_eve_time_interval,
            et_thu_mrng_start_time, et_thu_mrng_end_time, et_thu_mrng_time_interval,
            et_thu_eve_start_timing, et_thu_eve_end_time, et_thu_eve_time_interval,
            et_fri_mrng_start_time, et_fri_mrng_end_time, et_fri_mrng_time_interval,
            et_fri_eve_start_timing, et_fri_eve_end_time, et_fri_eve_time_interval,
            et_sat_mrng_start_time, et_sat_mrng_end_time, et_sat_mrng_time_interval,
            et_sat_eve_start_timing, et_sat_eve_end_time, et_sat_eve_time_interval,
            et_sun_mrng_start_time, et_sun_mrng_end_time, et_sun_mrng_time_interval,
            et_sun_eve_start_timing, et_sun_eve_end_time, et_sun_eve_time_interval;

    Spinner spinner_sun_mrng_end_ampm, spinner_sun_eve_start_ampm, spinner_sun_eve_end_ampm,
            spinner_sun_mrng_start_ampm, spinner_sat_mrng_end_ampm, spinner_sat_eve_start_ampm, spinner_sat_eve_end_ampm,
            spinner_sat_mrng_start_ampm, spinner_fri_mrng_end_ampm, spinner_fri_eve_start_ampm, spinner_fri_eve_end_ampm,
            spinner_fri_mrng_start_ampm, spinner_thu_mrng_end_ampm, spinner_thu_eve_start_ampm, spinner_thu_eve_end_ampm,
            spinner_thu_mrng_start_ampm, spinner_wed_mrng_end_ampm, spinner_wed_eve_start_ampm, spinner_wed_eve_end_ampm,
            spinner_wed_mrng_start_ampm, spinner_tue_mrng_end_ampm, spinner_tue_eve_start_ampm, spinner_tue_eve_end_ampm,
            spinner_tue_mrng_start_ampm, spinner_mon_mrng_end_ampm, spinner_mon_eve_start_ampm, spinner_mon_eve_end_ampm,
            spinner_mon_mrng_start_ampm;

    Switch switch_mon, switch_tue, switch_wed, switch_thu, switch_fri, switch_sat, switch_sun;

    View layout_mon, layout_tue, layout_wed, layout_thu, layout_fri, layout_sat, layout_sun;
    LinearLayout dialog_mon, dialog_tue, dialog_wed, dialog_thu, dialog_fri, dialog_sat, dialog_sun;


    public void showTimings() {

        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog1.setContentView(R.layout.dialog_time_view);
        dialog1.setTitle("Timings");
        dialog1.show();
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog1.setCancelable(true);

        dialog_mon = (LinearLayout) dialog1.findViewById(R.id.dialog_mon);
        dialog_tue = (LinearLayout) dialog1.findViewById(R.id.dialog_tue);
        dialog_wed = (LinearLayout) dialog1.findViewById(R.id.dialog_wed);
        dialog_thu = (LinearLayout) dialog1.findViewById(R.id.dialog_thu);
        dialog_fri = (LinearLayout) dialog1.findViewById(R.id.dialog_fri);
        dialog_sat = (LinearLayout) dialog1.findViewById(R.id.dialog_sat);
        dialog_sun = (LinearLayout) dialog1.findViewById(R.id.dialog_sun);


        layout_mon = dialog1.findViewById(R.id.include_mon);
        layout_tue = dialog1.findViewById(R.id.include_tue);
        layout_wed = dialog1.findViewById(R.id.include_wed);
        layout_thu = dialog1.findViewById(R.id.include_thu);
        layout_fri = dialog1.findViewById(R.id.include_fri);
        layout_sat = dialog1.findViewById(R.id.include_sat);
        layout_sun = dialog1.findViewById(R.id.include_sun);


        switch_mon = (Switch) dialog1.findViewById(R.id.switch_mon);
        switch_tue = (Switch) dialog1.findViewById(R.id.switch_tue);
        switch_wed = (Switch) dialog1.findViewById(R.id.switch_wed);
        switch_thu = (Switch) dialog1.findViewById(R.id.switch_thu);
        switch_fri = (Switch) dialog1.findViewById(R.id.switch_fri);
        switch_sat = (Switch) dialog1.findViewById(R.id.switch_sat);
        switch_sun = (Switch) dialog1.findViewById(R.id.switch_sun);


        if (list_linear_box.size() > 0) {
            list_linear_box.clear();
        }
        list_linear_box.add(dialog_mon);
        list_linear_box.add(dialog_tue);
        list_linear_box.add(dialog_wed);
        list_linear_box.add(dialog_thu);
        list_linear_box.add(dialog_fri);
        list_linear_box.add(dialog_sat);
        list_linear_box.add(dialog_sun);


        if (list_linear_layouts.size() > 0) {
            list_linear_layouts.clear();
        }
        list_linear_layouts.add(layout_mon);
        list_linear_layouts.add(layout_tue);
        list_linear_layouts.add(layout_wed);
        list_linear_layouts.add(layout_thu);
        list_linear_layouts.add(layout_fri);
        list_linear_layouts.add(layout_sat);
        list_linear_layouts.add(layout_sun);


        if (list_switches.size() > 0) {
            list_switches.clear();
        }
        list_switches.add(switch_mon);
        list_switches.add(switch_tue);
        list_switches.add(switch_wed);
        list_switches.add(switch_thu);
        list_switches.add(switch_fri);
        list_switches.add(switch_sat);
        list_switches.add(switch_sun);


        //declares values

//        is_Start_Editing=false;
//        for(boolean bol:selected_layouts){
//            bol=false;
//        }


        dialog_mon.setOnClickListener(this);
        dialog_tue.setOnClickListener(this);
        dialog_wed.setOnClickListener(this);
        dialog_thu.setOnClickListener(this);
        dialog_fri.setOnClickListener(this);
        dialog_sat.setOnClickListener(this);
        dialog_sun.setOnClickListener(this);

        Button btn_cancel = (Button) dialog1.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) dialog1.findViewById(R.id.btn_ok);


        et_mon_mrng_start_time = (EditText) dialog1.findViewById(R.id.et_mon_mrng_start_time);
        et_mon_mrng_end_time = (EditText) dialog1.findViewById(R.id.et_mon_mrng_end_time);
        et_mon_mrng_time_interval = (EditText) dialog1.findViewById(R.id.et_mon_mrng_time_interval);
        et_mon_eve_start_timing = (EditText) dialog1.findViewById(R.id.et_mon_eve_start_timing);
        et_mon_eve_end_time = (EditText) dialog1.findViewById(R.id.et_mon_eve_end_time);
        et_mon_eve_time_interval = (EditText) dialog1.findViewById(R.id.et_mon_eve_time_interval);
        et_tue_mrng_start_time = (EditText) dialog1.findViewById(R.id.et_tue_mrng_start_time);
        et_tue_mrng_end_time = (EditText) dialog1.findViewById(R.id.et_tue_mrng_end_time);
        et_tue_mrng_time_interval = (EditText) dialog1.findViewById(R.id.et_tue_mrng_time_interval);
        et_tue_eve_start_timing = (EditText) dialog1.findViewById(R.id.et_tue_eve_start_timing);
        et_tue_eve_end_time = (EditText) dialog1.findViewById(R.id.et_tue_eve_end_time);
        et_tue_eve_time_interval = (EditText) dialog1.findViewById(R.id.et_tue_eve_time_interval);
        et_wed_mrng_start_time = (EditText) dialog1.findViewById(R.id.et_wed_mrng_start_time);
        et_wed_mrng_end_time = (EditText) dialog1.findViewById(R.id.et_wed_mrng_end_time);
        et_wed_mrng_time_interval = (EditText) dialog1.findViewById(R.id.et_wed_mrng_time_interval);
        et_wed_eve_start_timing = (EditText) dialog1.findViewById(R.id.et_wed_eve_start_timing);
        et_wed_eve_end_time = (EditText) dialog1.findViewById(R.id.et_wed_eve_end_time);
        et_wed_eve_time_interval = (EditText) dialog1.findViewById(R.id.et_wed_eve_time_interval);
        et_thu_mrng_start_time = (EditText) dialog1.findViewById(R.id.et_thu_mrng_start_time);
        et_thu_mrng_end_time = (EditText) dialog1.findViewById(R.id.et_thu_mrng_end_time);
        et_thu_mrng_time_interval = (EditText) dialog1.findViewById(R.id.et_thu_mrng_time_interval);
        et_thu_eve_start_timing = (EditText) dialog1.findViewById(R.id.et_thu_eve_start_timing);
        et_thu_eve_end_time = (EditText) dialog1.findViewById(R.id.et_thu_eve_end_time);
        et_thu_eve_time_interval = (EditText) dialog1.findViewById(R.id.et_thu_eve_time_interval);
        et_fri_mrng_start_time = (EditText) dialog1.findViewById(R.id.et_fri_mrng_start_time);
        et_fri_mrng_end_time = (EditText) dialog1.findViewById(R.id.et_fri_mrng_end_time);
        et_fri_mrng_time_interval = (EditText) dialog1.findViewById(R.id.et_fri_mrng_time_interval);
        et_fri_eve_start_timing = (EditText) dialog1.findViewById(R.id.et_fri_eve_start_timing);
        et_fri_eve_end_time = (EditText) dialog1.findViewById(R.id.et_fri_eve_end_time);
        et_fri_eve_time_interval = (EditText) dialog1.findViewById(R.id.et_fri_eve_time_interval);
        et_sat_mrng_start_time = (EditText) dialog1.findViewById(R.id.et_sat_mrng_start_time);
        et_sat_mrng_end_time = (EditText) dialog1.findViewById(R.id.et_sat_mrng_end_time);
        et_sat_mrng_time_interval = (EditText) dialog1.findViewById(R.id.et_sat_mrng_time_interval);
        et_sat_eve_start_timing = (EditText) dialog1.findViewById(R.id.et_sat_eve_start_timing);
        et_sat_eve_end_time = (EditText) dialog1.findViewById(R.id.et_sat_eve_end_time);
        et_sat_eve_time_interval = (EditText) dialog1.findViewById(R.id.et_sat_eve_time_interval);
        et_sun_mrng_start_time = (EditText) dialog1.findViewById(R.id.et_sun_mrng_start_time);
        et_sun_mrng_end_time = (EditText) dialog1.findViewById(R.id.et_sun_mrng_end_time);
        et_sun_mrng_time_interval = (EditText) dialog1.findViewById(R.id.et_sun_mrng_time_interval);
        et_sun_eve_start_timing = (EditText) dialog1.findViewById(R.id.et_sun_eve_start_timing);
        et_sun_eve_end_time = (EditText) dialog1.findViewById(R.id.et_sun_eve_end_time);
        et_sun_eve_time_interval = (EditText) dialog1.findViewById(R.id.et_sun_eve_time_interval);


        spinner_sun_mrng_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sun_mrng_end_ampm);
        spinner_sun_eve_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sun_eve_start_ampm);
        spinner_sun_eve_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sun_eve_end_ampm);
        spinner_sun_mrng_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sun_mrng_start_ampm);
        spinner_sat_mrng_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sat_mrng_end_ampm);
        spinner_sat_eve_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sat_eve_start_ampm);
        spinner_sat_eve_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sat_eve_end_ampm);
        spinner_sat_mrng_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sat_mrng_start_ampm);
        spinner_fri_mrng_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_fri_mrng_end_ampm);
        spinner_fri_eve_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_fri_eve_start_ampm);
        spinner_fri_eve_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_fri_eve_end_ampm);
        spinner_fri_mrng_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_fri_mrng_start_ampm);
        spinner_thu_mrng_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_thu_mrng_end_ampm);
        spinner_thu_eve_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_thu_eve_start_ampm);
        spinner_thu_eve_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_thu_eve_end_ampm);
        spinner_thu_mrng_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_thu_mrng_start_ampm);
        spinner_wed_mrng_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_wed_mrng_end_ampm);
        spinner_wed_eve_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_wed_eve_start_ampm);
        spinner_wed_eve_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_wed_eve_end_ampm);
        spinner_wed_mrng_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_wed_mrng_start_ampm);
        spinner_tue_mrng_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_tue_mrng_end_ampm);
        spinner_tue_eve_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_tue_eve_start_ampm);
        spinner_tue_eve_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_tue_eve_end_ampm);
        spinner_tue_mrng_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_tue_mrng_start_ampm);
        spinner_mon_mrng_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_mon_mrng_end_ampm);
        spinner_mon_eve_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_mon_eve_start_ampm);
        spinner_mon_eve_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_mon_eve_end_ampm);
        spinner_mon_mrng_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_mon_mrng_start_ampm);


        et_mon_mrng_start_time.addTextChangedListener(new
                EditTextWatcher(et_mon_mrng_start_time, 0)
        );
        et_mon_mrng_end_time.addTextChangedListener(new
                EditTextWatcher(et_mon_mrng_end_time, 0)
        );
        et_mon_eve_start_timing.addTextChangedListener(new
                EditTextWatcher(et_mon_eve_start_timing, 0)
        );
        et_mon_eve_end_time.addTextChangedListener(new
                EditTextWatcher(et_mon_eve_end_time, 0)
        );


        et_tue_mrng_start_time.addTextChangedListener(new
                EditTextWatcher(et_tue_mrng_start_time, 1)
        );
        et_tue_mrng_end_time.addTextChangedListener(new
                EditTextWatcher(et_tue_mrng_end_time, 1)
        );

        et_tue_eve_start_timing.addTextChangedListener(new
                EditTextWatcher(et_tue_eve_start_timing, 1)
        );
        et_tue_eve_end_time.addTextChangedListener(new
                EditTextWatcher(et_tue_eve_end_time, 1)
        );


        et_wed_mrng_start_time.addTextChangedListener(new
                EditTextWatcher(et_wed_mrng_start_time, 2)
        );
        et_wed_mrng_end_time.addTextChangedListener(new
                EditTextWatcher(et_wed_mrng_end_time, 2)
        );

        et_wed_eve_start_timing.addTextChangedListener(new
                EditTextWatcher(et_wed_eve_start_timing, 2)
        );
        et_wed_eve_end_time.addTextChangedListener(new
                EditTextWatcher(et_wed_eve_end_time, 2)
        );


        et_thu_mrng_start_time.addTextChangedListener(new
                EditTextWatcher(et_thu_mrng_start_time, 3)
        );
        et_thu_mrng_end_time.addTextChangedListener(new
                EditTextWatcher(et_thu_mrng_end_time, 3)
        );

        et_thu_eve_start_timing.addTextChangedListener(new
                EditTextWatcher(et_thu_eve_start_timing, 3)
        );
        et_thu_eve_end_time.addTextChangedListener(new
                EditTextWatcher(et_thu_eve_end_time, 3)
        );


        et_fri_mrng_start_time.addTextChangedListener(new
                EditTextWatcher(et_fri_mrng_start_time, 4)
        );
        et_fri_mrng_end_time.addTextChangedListener(new
                EditTextWatcher(et_fri_mrng_end_time, 4)
        );

        et_fri_eve_start_timing.addTextChangedListener(new
                EditTextWatcher(et_fri_eve_start_timing, 4)
        );
        et_fri_eve_end_time.addTextChangedListener(new
                EditTextWatcher(et_fri_eve_end_time, 4)
        );


        et_sat_mrng_start_time.addTextChangedListener(new
                EditTextWatcher(et_sat_mrng_start_time, 5)
        );
        et_sat_mrng_end_time.addTextChangedListener(new
                EditTextWatcher(et_sat_mrng_end_time, 5)
        );

        et_sat_eve_start_timing.addTextChangedListener(new
                EditTextWatcher(et_sat_eve_start_timing, 5)
        );
        et_sat_eve_end_time.addTextChangedListener(new
                EditTextWatcher(et_sat_eve_end_time, 5)
        );


        et_sun_mrng_start_time.addTextChangedListener(new
                EditTextWatcher(et_sun_mrng_start_time, 6)
        );
        et_sun_mrng_end_time.addTextChangedListener(new
                EditTextWatcher(et_sun_mrng_end_time, 6)
        );

        et_sun_eve_start_timing.addTextChangedListener(new
                EditTextWatcher(et_sun_eve_start_timing, 6)
        );
        et_sun_eve_end_time.addTextChangedListener(new
                EditTextWatcher(et_sun_eve_end_time, 6)
        );

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (starting_index != -1) {
                    Log.d(TAG, "index:-" + starting_index);
                    Log.d(TAG, getFirstLayoutTimings(starting_index).toString());
//                    getAllTimings(getFirstLayoutTimings(starting_index));
                    getAllTimings();
                    is_updating = true;
                    if (isUpdatingclinic) {
//                        CallWebService(true);
                    } else {
                        CallWebService(false);
                    }
                } else {
                    Log.d(TAG, "starting index:-" + starting_index);
                }
                if (isUpdatingclinic) {
                    getAllTimings();
                    CallWebService(true);
                }

                dialog1.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (starting_index != -1) {
                    Log.d(TAG, "index:-" + starting_index);
                    Log.d(TAG, getFirstLayoutTimings(starting_index).toString());
                    getAllTimings();
                    is_updating = true;
                } else {
                    Log.d(TAG, "starting index:-" + starting_index);
                }
                if (is_Start_Editing) {
                    is_updating = true;
                }
                dialog1.dismiss();
            }
        });

        switch_mon.setOnCheckedChangeListener(this);
        switch_tue.setOnCheckedChangeListener(this);
        switch_wed.setOnCheckedChangeListener(this);
        switch_thu.setOnCheckedChangeListener(this);
        switch_fri.setOnCheckedChangeListener(this);
        switch_sat.setOnCheckedChangeListener(this);
        switch_sun.setOnCheckedChangeListener(this);


        if (!is_Start_Editing) {
            SetVisibility(0);
            switch_mon.setVisibility(View.GONE);
            switch_tue.setVisibility(View.GONE);
            switch_wed.setVisibility(View.GONE);
            switch_thu.setVisibility(View.GONE);
            switch_fri.setVisibility(View.GONE);
            switch_sat.setVisibility(View.GONE);
            switch_sun.setVisibility(View.GONE);
        }
//        }else{
//            checkPreviousTimings();
//        }


        if (is_updating) {
            checkPreviousTimings();
            switch_mon.setVisibility(View.VISIBLE);
            switch_tue.setVisibility(View.VISIBLE);
            switch_wed.setVisibility(View.VISIBLE);
            switch_thu.setVisibility(View.VISIBLE);
            switch_fri.setVisibility(View.VISIBLE);
            switch_sat.setVisibility(View.VISIBLE);
            switch_sun.setVisibility(View.VISIBLE);
        }
    }

    private boolean is_updating = false;

    public String ShowTimings() {
        return "clss{" +
                "mon_mrng_start_time='" + mon_mrng_start_time + '\'' +
                ", mon_mrng_end_time='" + mon_mrng_end_time + '\'' +
                ", mon_eve_start_time='" + mon_eve_start_time + '\'' +
                ", mon_eve_end_time='" + mon_eve_end_time + '\'' +
                ", tue_mrng_start_time='" + tue_mrng_start_time + '\'' +
                ", tue_mrng_end_time='" + tue_mrng_end_time + '\'' +
                ", tue_eve_start_time='" + tue_eve_start_time + '\'' +
                ", tue_eve_end_time='" + tue_eve_end_time + '\'' +
                ", wed_mrng_start_time='" + wed_mrng_start_time + '\'' +
                ", wed_mrng_end_time='" + wed_mrng_end_time + '\'' +
                ", wed_eve_start_time='" + wed_eve_start_time + '\'' +
                ", wed_eve_end_time='" + wed_eve_end_time + '\'' +
                ", thu_mrng_start_time='" + thu_mrng_start_time + '\'' +
                ", thu_mrng_end_time='" + thu_mrng_end_time + '\'' +
                ", thu_eve_start_time='" + thu_eve_start_time + '\'' +
                ", thu_eve_end_time='" + thu_eve_end_time + '\'' +
                ", fri_mrng_start_time='" + fri_mrng_start_time + '\'' +
                ", fri_mrng_end_time='" + fri_mrng_end_time + '\'' +
                ", fri_eve_start_time='" + fri_eve_start_time + '\'' +
                ", fri_eve_end_time='" + fri_eve_end_time + '\'' +
                ", sat_mrng_start_time='" + sat_mrng_start_time + '\'' +
                ", sat_mrng_end_time='" + sat_mrng_end_time + '\'' +
                ", sat_eve_start_time='" + sat_eve_start_time + '\'' +
                ", sat_eve_end_time='" + sat_eve_end_time + '\'' +
                ", sun_mrng_start_time='" + sun_mrng_start_time + '\'' +
                ", sun_mrng_end_time='" + sun_mrng_end_time + '\'' +
                ", sun_eve_start_time='" + sun_eve_start_time + '\'' +
                ", sun_eve_end_time='" + sun_eve_end_time + '\'' +
                '}';
    }

    public void setEdittexttext(EditText edittexttext, String text) {
        try {
            String[] split = text.split(" ");
            String[] split2 = split[0].split(":");
            edittexttext.setText(split2[0]);
//            edittexttext.setText(split[0]);
//            edittexttext.append(split2[1]);
            Log.d(TAG, "time1:-" + split2[0]);
            Log.d(TAG, "time2:-" + split2[1]);
        } catch (Exception e) {
            Log.d(TAG, "error:-" + e.toString());
        }
    }

    public void checkPreviousTimings() {
        Log.d(TAG, "previous timings:-" + ShowTimings());
        if (mon_mrng_start_time.length() > 0 || mon_mrng_end_time.length() > 0 || mon_eve_start_time.length() > 0 || mon_eve_end_time.length() > 0) {

            selected_layouts[0] = true;

            list_first_update_mon_time.clear();
            list_first_update_mon_time.add(mon_mrng_start_time);
            list_first_update_mon_time.add(mon_mrng_end_time);
            list_first_update_mon_time.add(mon_eve_start_time);
            list_first_update_mon_time.add(mon_eve_end_time);
            list_first_update_mon_time.add(mon_mrng_time_interval);
            list_first_update_mon_time.add(mon_eve_time_interval);

            list_first_timings.clear();
            list_first_timings.add(mon_mrng_start_time);
            list_first_timings.add(mon_mrng_end_time);
            list_first_timings.add(mon_eve_start_time);
            list_first_timings.add(mon_eve_end_time);
            list_first_timings.add(mon_mrng_time_interval);
            list_first_timings.add(mon_eve_time_interval);
        } else {
            selected_layouts[0] = false;
        }
//        setEdittexttext(et_mon_mrng_start_time,mon_mrng_start_time);


        if (tue_mrng_start_time.length() > 0 || tue_mrng_end_time.length() > 0 || tue_eve_start_time.length() > 0 || tue_eve_end_time.length() > 0) {

            selected_layouts[1] = true;
            list_first_update_tue_time.clear();

            list_first_update_tue_time.add(tue_mrng_start_time);
            list_first_update_tue_time.add(tue_mrng_end_time);
            list_first_update_tue_time.add(tue_eve_start_time);
            list_first_update_tue_time.add(tue_eve_end_time);
            list_first_update_tue_time.add(tue_mrng_time_interval);
            list_first_update_tue_time.add(tue_eve_time_interval);


            list_first_timings.clear();

            list_first_timings.add(tue_mrng_start_time);
            list_first_timings.add(tue_mrng_end_time);
            list_first_timings.add(tue_eve_start_time);
            list_first_timings.add(tue_eve_end_time);
            list_first_timings.add(tue_mrng_time_interval);
            list_first_timings.add(tue_eve_time_interval);
        } else {
            selected_layouts[1] = false;
        }

        if (wed_mrng_start_time.length() > 0 || wed_mrng_end_time.length() > 0 || wed_eve_start_time.length() > 0 || wed_eve_end_time.length() > 0) {

            selected_layouts[2] = true;
            list_first_update_wed_time.clear();

            list_first_update_wed_time.add(wed_mrng_start_time);
            list_first_update_wed_time.add(wed_mrng_end_time);
            list_first_update_wed_time.add(wed_eve_start_time);
            list_first_update_wed_time.add(wed_eve_end_time);
            list_first_update_wed_time.add(wed_mrng_time_interval);
            list_first_update_wed_time.add(wed_eve_time_interval);

            list_first_timings.clear();

            list_first_timings.add(wed_mrng_start_time);
            list_first_timings.add(wed_mrng_end_time);
            list_first_timings.add(wed_eve_start_time);
            list_first_timings.add(wed_eve_end_time);
            list_first_timings.add(wed_mrng_time_interval);
            list_first_timings.add(wed_eve_time_interval);
        } else {
            selected_layouts[2] = false;
        }
        if (thu_mrng_start_time.length() > 0 || thu_mrng_end_time.length() > 0 || thu_eve_start_time.length() > 0 || thu_eve_end_time.length() > 0) {

            selected_layouts[3] = true;
            list_first_update_thu_time.clear();

            list_first_update_thu_time.add(thu_mrng_start_time);
            list_first_update_thu_time.add(thu_mrng_end_time);
            list_first_update_thu_time.add(thu_eve_start_time);
            list_first_update_thu_time.add(thu_eve_end_time);
            list_first_update_thu_time.add(thu_mrng_time_interval);
            list_first_update_thu_time.add(thu_eve_time_interval);

            list_first_timings.clear();

            list_first_timings.add(thu_mrng_start_time);
            list_first_timings.add(thu_mrng_end_time);
            list_first_timings.add(thu_eve_start_time);
            list_first_timings.add(thu_eve_end_time);
            list_first_timings.add(thu_mrng_time_interval);
            list_first_timings.add(thu_eve_time_interval);
        } else {
            selected_layouts[3] = false;
        }
        if (fri_mrng_start_time.length() > 0 || fri_mrng_end_time.length() > 0 || fri_eve_start_time.length() > 0 || fri_eve_end_time.length() > 0) {

            selected_layouts[4] = true;
            list_first_update_fri_time.clear();

            list_first_update_fri_time.add(fri_mrng_start_time);
            list_first_update_fri_time.add(fri_mrng_end_time);
            list_first_update_fri_time.add(fri_eve_start_time);
            list_first_update_fri_time.add(fri_eve_end_time);
            list_first_update_fri_time.add(fri_mrng_time_interval);
            list_first_update_fri_time.add(fri_eve_time_interval);


            list_first_timings.clear();

            list_first_timings.add(fri_mrng_start_time);
            list_first_timings.add(fri_mrng_end_time);
            list_first_timings.add(fri_eve_start_time);
            list_first_timings.add(fri_eve_end_time);
            list_first_timings.add(fri_mrng_time_interval);
            list_first_timings.add(fri_eve_time_interval);
        } else {
            selected_layouts[4] = false;
        }
        if (sat_mrng_start_time.length() > 0 || sat_mrng_end_time.length() > 0 || sat_eve_start_time.length() > 0 || sat_eve_end_time.length() > 0) {

            selected_layouts[5] = true;
            list_first_update_sat_time.clear();

            list_first_update_sat_time.add(sat_mrng_start_time);
            list_first_update_sat_time.add(sat_mrng_end_time);
            list_first_update_sat_time.add(sat_eve_start_time);
            list_first_update_sat_time.add(sat_eve_end_time);
            list_first_update_sat_time.add(sat_mrng_time_interval);
            list_first_update_sat_time.add(sat_eve_time_interval);

            list_first_timings.clear();

            list_first_timings.add(sat_mrng_start_time);
            list_first_timings.add(sat_mrng_end_time);
            list_first_timings.add(sat_eve_start_time);
            list_first_timings.add(sat_eve_end_time);
            list_first_timings.add(sat_mrng_time_interval);
            list_first_timings.add(sat_eve_time_interval);
        } else {
            selected_layouts[5] = false;
        }
        if (sun_mrng_start_time.length() > 0 || sun_mrng_end_time.length() > 0 || sun_eve_start_time.length() > 0 || sun_eve_end_time.length() > 0) {

            selected_layouts[6] = true;
            list_first_update_sun_time.clear();

            list_first_update_sun_time.add(sun_mrng_start_time);
            list_first_update_sun_time.add(sun_mrng_end_time);
            list_first_update_sun_time.add(sun_eve_start_time);
            list_first_update_sun_time.add(sun_eve_end_time);
            list_first_update_sun_time.add(sun_mrng_time_interval);
            list_first_update_sun_time.add(sun_eve_time_interval);


            list_first_timings.clear();

            list_first_timings.add(sun_mrng_start_time);
            list_first_timings.add(sun_mrng_end_time);
            list_first_timings.add(sun_eve_start_time);
            list_first_timings.add(sun_eve_end_time);
            list_first_timings.add(sun_mrng_time_interval);
            list_first_timings.add(sun_eve_time_interval);
        } else {
            selected_layouts[6] = false;
        }

        for (int i = 0; i < selected_layouts.length; i++) {
            if (selected_layouts[i]) {
                Log.d(TAG, "Selected layouts " + i + ":-" + selected_layouts[i]);
                list_linear_box.get(i).setBackgroundResource(R.drawable.linearlayoutborderlight);
                list_switches.get(i).setChecked(true);
                setLayoutTimingsFirstly(i);
            } else {
                Log.d(TAG, "Selected layouts " + i + ":-" + selected_layouts[i]);
                list_linear_box.get(i).setBackgroundResource(R.drawable.linearlayoutborder);
                list_switches.get(i).setChecked(false);
            }

//            et_mon_mrng_start_time.setText("12");
        }
    }

    public void setLayoutTimingsFirstly(int index) {
        switch (index) {
            case 0:
                if (list_first_update_mon_time.size() > 0) {
                    setLayoutTimings(list_first_update_mon_time, index);
                } else {
                    setLayoutTimings(list_first_timings, index);
                }
                break;
            case 1:
                if (list_first_update_tue_time.size() > 0) {
                    setLayoutTimings(list_first_update_tue_time, index);
                } else {
                    setLayoutTimings(list_first_timings, index);
                }
                break;
            case 2:
                if (list_first_update_wed_time.size() > 0) {
                    setLayoutTimings(list_first_update_wed_time, index);
                } else {
                    setLayoutTimings(list_first_timings, index);
                }
                break;
            case 3:
                if (list_first_update_thu_time.size() > 0) {
                    setLayoutTimings(list_first_update_thu_time, index);
                } else {
                    setLayoutTimings(list_first_timings, index);
                }
                break;
            case 4:
                if (list_first_update_fri_time.size() > 0) {
                    setLayoutTimings(list_first_update_fri_time, index);
                } else {
                    setLayoutTimings(list_first_timings, index);
                }
                break;
            case 5:
                if (list_first_update_sat_time.size() > 0) {
                    setLayoutTimings(list_first_update_sat_time, index);
                } else {
                    setLayoutTimings(list_first_timings, index);
                }
                break;
            case 6:
                if (list_first_update_sun_time.size() > 0) {
                    setLayoutTimings(list_first_update_sun_time, index);
                } else {
                    setLayoutTimings(list_first_timings, index);
                }
                break;
        }
    }


    private void getAllTimings() {
        try {
            Log.d(TAG, "getting all timings");
            if (selected_layouts[0]) {
                mon_mrng_start_time = ValidateTimings(et_mon_mrng_start_time, spinner_mon_mrng_start_ampm);
                mon_mrng_end_time = ValidateTimings(et_mon_mrng_end_time, spinner_mon_mrng_end_ampm);
                mon_eve_start_time = ValidateTimings(et_mon_eve_start_timing, spinner_mon_eve_start_ampm);
                mon_eve_end_time = ValidateTimings(et_mon_eve_end_time, spinner_mon_eve_end_ampm);
                mon_mrng_time_interval = et_mon_mrng_time_interval.getText().toString();
                mon_eve_time_interval = et_mon_eve_time_interval.getText().toString();
            } else {
                mon_mrng_start_time = "";
                mon_mrng_end_time = "";
                mon_eve_start_time = "";
                mon_eve_end_time = "";
                mon_mrng_time_interval = "";
                mon_eve_time_interval = "";
            }
            Log.d(TAG, "mon:-" + mon_mrng_start_time + "::" + mon_mrng_end_time + "::" + mon_eve_start_time + "::" + mon_eve_end_time);

            if (selected_layouts[1]) {
                tue_mrng_start_time = ValidateTimings(et_tue_mrng_start_time, spinner_tue_mrng_start_ampm);
                tue_mrng_end_time = ValidateTimings(et_tue_mrng_end_time, spinner_tue_mrng_end_ampm);
                tue_eve_start_time = ValidateTimings(et_tue_eve_start_timing, spinner_tue_eve_start_ampm);
                tue_eve_end_time = ValidateTimings(et_tue_eve_end_time, spinner_tue_eve_end_ampm);
                tue_mrng_time_interval = et_tue_mrng_time_interval.getText().toString();
                tue_eve_time_interval = et_tue_eve_time_interval.getText().toString();
            } else {
                tue_mrng_start_time = "";
                tue_mrng_end_time = "";
                tue_eve_start_time = "";
                tue_eve_end_time = "";
                tue_mrng_time_interval = "";
                tue_eve_time_interval = "";
            }
            Log.d(TAG, "tue:-" + tue_mrng_start_time + "::" + tue_mrng_end_time + "::" + tue_eve_start_time + "::" + tue_eve_end_time);

            if (selected_layouts[2]) {
                wed_mrng_start_time = ValidateTimings(et_wed_mrng_start_time, spinner_wed_mrng_start_ampm);
                wed_mrng_end_time = ValidateTimings(et_wed_mrng_end_time, spinner_wed_mrng_end_ampm);
                wed_eve_start_time = ValidateTimings(et_wed_eve_start_timing, spinner_wed_eve_start_ampm);
                wed_eve_end_time = ValidateTimings(et_wed_eve_end_time, spinner_wed_eve_end_ampm);
                wed_mrng_time_interval = et_wed_mrng_time_interval.getText().toString();
                wed_eve_time_interval = et_wed_eve_time_interval.getText().toString();
            } else {
                wed_mrng_start_time = "";
                wed_mrng_end_time = "";
                wed_eve_start_time = "";
                wed_eve_end_time = "";
                wed_mrng_time_interval = "";
                wed_eve_time_interval = "";
            }
            Log.d(TAG, "wed:-" + wed_mrng_start_time + "::" + wed_mrng_end_time + "::" + wed_eve_start_time + "::" + wed_eve_end_time);

            if (selected_layouts[3]) {
                thu_mrng_start_time = ValidateTimings(et_thu_mrng_start_time, spinner_thu_mrng_start_ampm);
                thu_mrng_end_time = ValidateTimings(et_thu_mrng_end_time, spinner_thu_mrng_end_ampm);
                thu_eve_start_time = ValidateTimings(et_thu_eve_start_timing, spinner_thu_eve_start_ampm);
                thu_eve_end_time = ValidateTimings(et_thu_eve_end_time, spinner_thu_eve_end_ampm);
                thu_mrng_time_interval = et_thu_mrng_time_interval.getText().toString();
                thu_eve_time_interval = et_thu_eve_time_interval.getText().toString();
            } else {
                thu_mrng_start_time = "";
                thu_mrng_end_time = "";
                thu_eve_start_time = "";
                thu_eve_end_time = "";
                thu_mrng_time_interval = "";
                thu_eve_time_interval = "";
            }
            Log.d(TAG, "thu:-" + thu_mrng_start_time + "::" + thu_mrng_end_time + "::" + thu_eve_start_time + "::" + thu_eve_end_time);


            if (selected_layouts[4]) {
                fri_mrng_start_time = ValidateTimings(et_fri_mrng_start_time, spinner_fri_mrng_start_ampm);
                fri_mrng_end_time = ValidateTimings(et_fri_mrng_end_time, spinner_fri_mrng_end_ampm);
                fri_eve_start_time = ValidateTimings(et_fri_eve_start_timing, spinner_fri_eve_start_ampm);
                fri_eve_end_time = ValidateTimings(et_fri_eve_end_time, spinner_fri_eve_end_ampm);
                fri_mrng_time_interval = et_fri_mrng_time_interval.getText().toString();
                fri_eve_time_interval = et_fri_eve_time_interval.getText().toString();
            } else {
                fri_mrng_start_time = "";
                fri_mrng_end_time = "";
                fri_eve_start_time = "";
                fri_eve_end_time = "";
                fri_mrng_time_interval = "";
                fri_eve_time_interval = "";
            }
            Log.d(TAG, "fri:-" + fri_mrng_start_time + "::" + fri_mrng_end_time + "::" + fri_eve_start_time + "::" + fri_eve_end_time);

            if (selected_layouts[5]) {
                sat_mrng_start_time = ValidateTimings(et_sat_mrng_start_time, spinner_sat_mrng_start_ampm);
                sat_mrng_end_time = ValidateTimings(et_sat_mrng_end_time, spinner_sat_mrng_end_ampm);
                sat_eve_start_time = ValidateTimings(et_sat_eve_start_timing, spinner_sat_eve_start_ampm);
                sat_eve_end_time = ValidateTimings(et_sat_eve_end_time, spinner_sat_eve_end_ampm);
                sat_mrng_time_interval = et_sat_mrng_time_interval.getText().toString();
                sat_eve_time_interval = et_sat_eve_time_interval.getText().toString();
            } else {
                sat_mrng_start_time = "";
                sat_mrng_end_time = "";
                sat_eve_start_time = "";
                sat_eve_end_time = "";
                sat_mrng_time_interval = "";
                sat_eve_time_interval = "";
            }
            Log.d(TAG, "sat:-" + sat_mrng_start_time + "::" + sat_mrng_end_time + "::" + sat_eve_start_time + "::" + sat_eve_end_time);

            if (selected_layouts[6]) {
                sun_mrng_start_time = ValidateTimings(et_sun_mrng_start_time, spinner_sun_mrng_start_ampm);
                sun_mrng_end_time = ValidateTimings(et_sun_mrng_end_time, spinner_sun_mrng_end_ampm);
                sun_eve_start_time = ValidateTimings(et_sun_eve_start_timing, spinner_sun_eve_start_ampm);
                sun_eve_end_time = ValidateTimings(et_sun_eve_end_time, spinner_sun_eve_end_ampm);
                sun_mrng_time_interval = et_sun_mrng_time_interval.getText().toString();
                sun_eve_time_interval = et_sun_eve_time_interval.getText().toString();
            } else {
                sun_mrng_start_time = "";
                sun_mrng_end_time = "";
                sun_eve_start_time = "";
                sun_eve_end_time = "";
                sun_mrng_time_interval = "";
                sun_eve_time_interval = "";
            }
            Log.d(TAG, "sun:-" + sun_mrng_start_time + "::" + sun_mrng_end_time + "::" + sun_eve_start_time + "::" + sun_eve_end_time);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    private String mon_mrng_start_time = "", mon_mrng_end_time = "", mon_eve_start_time = "", mon_eve_end_time = "", mon_mrng_time_interval = "", mon_eve_time_interval = "",
            tue_mrng_start_time = "", tue_mrng_end_time = "", tue_eve_start_time = "", tue_eve_end_time = "", tue_mrng_time_interval = "", tue_eve_time_interval = "",
            wed_mrng_start_time = "", wed_mrng_end_time = "", wed_eve_start_time = "", wed_eve_end_time = "", wed_mrng_time_interval = "", wed_eve_time_interval = "",
            thu_mrng_start_time = "", thu_mrng_end_time = "", thu_eve_start_time = "", thu_eve_end_time = "", thu_mrng_time_interval = "", thu_eve_time_interval = "",
            fri_mrng_start_time = "", fri_mrng_end_time = "", fri_eve_start_time = "", fri_eve_end_time = "", fri_mrng_time_interval = "", fri_eve_time_interval = "",
            sat_mrng_start_time = "", sat_mrng_end_time = "", sat_eve_start_time = "", sat_eve_end_time = "", sat_mrng_time_interval = "", sat_eve_time_interval = "",
            sun_mrng_start_time = "", sun_mrng_end_time = "", sun_eve_start_time = "", sun_eve_end_time = "", sun_mrng_time_interval = "", sun_eve_time_interval = "";


    private final String TAG = getClass().getSimpleName();
    //variable
    boolean is_Start_Editing = false;
    int starting_index = -1;


    public void SetVisibility(int index) {
        if (is_updating) {
            for (View view : list_linear_layouts) {
                view.setVisibility(View.GONE);
            }
            list_linear_layouts.get(index).setVisibility(View.VISIBLE);
        } else {
            if (!is_Start_Editing) {
                starting_index = index;
                for (LinearLayout linearLayout : list_linear_box) {
                    linearLayout.setBackgroundResource(R.drawable.linearlayoutborder);
                }
                list_linear_box.get(index).setBackgroundResource(R.drawable.linearlayoutsolid);

                for (View view : list_linear_layouts) {
                    view.setVisibility(View.GONE);
                }
                list_linear_layouts.get(index).setVisibility(View.VISIBLE);

                for (boolean bol : selected_layouts) {
                    bol = false;
                }
            } else {
//                if(selected_layouts[index]){
//                    selected_layouts[index]=false;
//                    list_linear_box.get(index).setBackgroundResource(R.drawable.linearlayoutborder);
////                for (View view : list_linear_layouts) {
////                    view.setVisibility(View.GONE);
////                }
////                list_linear_layouts.get(index).setVisibility(View.VISIBLE);
//                    clearLayoutTimings(index);
//                }
//                else{
//                    selected_layouts[index]=true;
//                    list_linear_box.get(index).setBackgroundResource(R.drawable.linearlayoutsolid);
//                    for (View view : list_linear_layouts) {
//                        view.setVisibility(View.GONE);
//                    }
//                    list_linear_layouts.get(index).setVisibility(View.VISIBLE);
//                    setLayoutTimings(getFirstLayoutTimings(starting_index),index);
//                }
                boolean selecte_layout_index = selected_layouts[index];
                Log.d(TAG, "selecte_layout_index:-" + selecte_layout_index);
                if (selecte_layout_index) {
                    selected_layouts[index] = false;
                    Log.d(TAG, "selected layout" + index + ":-" + selected_layouts[index]);
                    list_linear_box.get(index).setBackgroundResource(R.drawable.linearlayoutborder);
                    //                for (View view : list_linear_layouts) {
                    //                    view.setVisibility0(View.GONE);
                    //                }
                    //                list_linear_layouts.get(index).setVisibility(View.VISIBLE);
                    clearLayoutTimings(index);
                } else {
                    selected_layouts[index] = true;
                    selected_layouts[index] = false;
                    Log.d(TAG, "selected layout1:-" + index + ":-" + selected_layouts[index]);
                    list_linear_box.get(index).setBackgroundResource(R.drawable.linearlayoutsolid);
                    for (View view : list_linear_layouts) {
                        view.setVisibility(View.GONE);
                    }
                    list_linear_layouts.get(index).setVisibility(View.VISIBLE);
                    setLayoutTimings(getFirstLayoutTimings(starting_index), index);
                }
                if (selecte_layout_index) {
                    selected_layouts[index] = false;
                } else {
                    selected_layouts[index] = true;
                }
                //problem is here.
                for (int i = 0; i < selected_layouts.length; i++) {
                    boolean bol = selected_layouts[i];
                    Log.d(TAG, "selected layout booleans" + i + ":-" + bol);
                }
//            selected_layouts[index]=true;
            }
        }
    }

    public void setLayoutTimingsFirstly(String time, EditText et, Spinner sp, boolean changeSpinner) {
        try {
            String[] split = time.split(" ");
            et.setText(split[0]);
            if (changeSpinner) {
                if (split[1].equals("am")) {
                    sp.setSelection(0);
                } else {
                    sp.setSelection(1);
                }
            }
        } catch (Exception e) {

        }
    }

    public void setLayoutTimings(List<String> timings, int position) {
        try {
            switch (position) {
                case 0:
                    setLayoutTimingsFirstly(timings.get(0), et_mon_mrng_start_time, spinner_mon_mrng_start_ampm, true);
                    setLayoutTimingsFirstly(timings.get(1), et_mon_mrng_end_time, spinner_mon_mrng_end_ampm, true);
                    setLayoutTimingsFirstly(timings.get(2), et_mon_eve_start_timing, spinner_mon_eve_start_ampm, false);
                    setLayoutTimingsFirstly(timings.get(3), et_mon_eve_end_time, spinner_mon_eve_end_ampm, false);
                    et_mon_mrng_time_interval.setText(timings.get(4));
                    et_mon_eve_time_interval.setText(timings.get(5));
                    break;
                case 1:
                    setLayoutTimingsFirstly(timings.get(0), et_tue_mrng_start_time, spinner_tue_mrng_start_ampm, true);
                    setLayoutTimingsFirstly(timings.get(1), et_tue_mrng_end_time, spinner_tue_mrng_end_ampm, true);
                    setLayoutTimingsFirstly(timings.get(2), et_tue_eve_start_timing, spinner_tue_eve_start_ampm, false);
                    setLayoutTimingsFirstly(timings.get(3), et_tue_eve_end_time, spinner_tue_eve_end_ampm, false);
                    et_tue_mrng_time_interval.setText(timings.get(4));
                    et_tue_eve_time_interval.setText(timings.get(5));
                    break;
                case 2:
                    setLayoutTimingsFirstly(timings.get(0), et_wed_mrng_start_time, spinner_wed_mrng_start_ampm, true);
                    setLayoutTimingsFirstly(timings.get(1), et_wed_mrng_end_time, spinner_wed_mrng_end_ampm, true);
                    setLayoutTimingsFirstly(timings.get(2), et_wed_eve_start_timing, spinner_wed_eve_start_ampm, false);
                    setLayoutTimingsFirstly(timings.get(3), et_wed_eve_end_time, spinner_wed_eve_end_ampm, false);
                    et_wed_mrng_time_interval.setText(timings.get(4));
                    et_wed_eve_time_interval.setText(timings.get(5));
                    break;
                case 3:
                    setLayoutTimingsFirstly(timings.get(0), et_thu_mrng_start_time, spinner_thu_mrng_start_ampm, true);
                    setLayoutTimingsFirstly(timings.get(1), et_thu_mrng_end_time, spinner_thu_mrng_end_ampm, true);
                    setLayoutTimingsFirstly(timings.get(2), et_thu_eve_start_timing, spinner_thu_eve_start_ampm, false);
                    setLayoutTimingsFirstly(timings.get(3), et_thu_eve_end_time, spinner_thu_eve_end_ampm, false);
                    et_thu_mrng_time_interval.setText(timings.get(4));
                    et_thu_eve_time_interval.setText(timings.get(5));
                    break;
                case 4:
                    setLayoutTimingsFirstly(timings.get(0), et_fri_mrng_start_time, spinner_fri_mrng_start_ampm, true);
                    setLayoutTimingsFirstly(timings.get(1), et_fri_mrng_end_time, spinner_fri_mrng_end_ampm, true);
                    setLayoutTimingsFirstly(timings.get(2), et_fri_eve_start_timing, spinner_fri_eve_start_ampm, false);
                    setLayoutTimingsFirstly(timings.get(3), et_fri_eve_end_time, spinner_fri_eve_end_ampm, false);
                    et_fri_mrng_time_interval.setText(timings.get(4));
                    et_fri_eve_time_interval.setText(timings.get(5));
                    break;
                case 5:
                    setLayoutTimingsFirstly(timings.get(0), et_sat_mrng_start_time, spinner_sat_mrng_start_ampm, true);
                    setLayoutTimingsFirstly(timings.get(1), et_sat_mrng_end_time, spinner_sat_mrng_end_ampm, true);
                    setLayoutTimingsFirstly(timings.get(2), et_sat_eve_start_timing, spinner_sat_eve_start_ampm, false);
                    setLayoutTimingsFirstly(timings.get(3), et_sat_eve_end_time, spinner_sat_eve_end_ampm, false);
                    et_sat_mrng_time_interval.setText(timings.get(4));
                    et_sat_eve_time_interval.setText(timings.get(5));
                    break;
                case 6:
                    setLayoutTimingsFirstly(timings.get(0), et_sun_mrng_start_time, spinner_sun_mrng_start_ampm, true);
                    setLayoutTimingsFirstly(timings.get(1), et_sun_mrng_end_time, spinner_sun_mrng_end_ampm, true);
                    setLayoutTimingsFirstly(timings.get(2), et_sun_eve_start_timing, spinner_sun_eve_start_ampm, false);
                    setLayoutTimingsFirstly(timings.get(3), et_sun_eve_end_time, spinner_sun_eve_end_ampm, false);
                    et_sun_mrng_time_interval.setText(timings.get(4));
                    et_sun_eve_time_interval.setText(timings.get(5));
                    break;
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    public void clearLayoutTimings(int position) {
        switch (position) {
            case 0:
                et_mon_mrng_start_time.setText("");
                et_mon_mrng_end_time.setText("");
                et_mon_eve_start_timing.setText("");
                et_mon_eve_end_time.setText("");
                break;
            case 1:
                et_tue_mrng_start_time.setText("");
                et_tue_mrng_end_time.setText("");
                et_tue_eve_start_timing.setText("");
                et_tue_eve_end_time.setText("");
                break;
            case 2:
                et_wed_mrng_start_time.setText("");
                et_wed_mrng_end_time.setText("");
                et_wed_eve_start_timing.setText("");
                et_wed_eve_end_time.setText("");
                break;
            case 3:
                et_thu_mrng_start_time.setText("");
                et_thu_mrng_end_time.setText("");
                et_thu_eve_start_timing.setText("");
                et_thu_eve_end_time.setText("");
                break;
            case 4:
                et_fri_mrng_start_time.setText("");
                et_fri_mrng_end_time.setText("");
                et_fri_eve_start_timing.setText("");
                et_fri_eve_end_time.setText("");
                break;
            case 5:
                et_sat_mrng_start_time.setText("");
                et_sat_mrng_end_time.setText("");
                et_sat_eve_start_timing.setText("");
                et_sat_eve_end_time.setText("");
                break;
            case 6:
                et_sun_mrng_start_time.setText("");
                et_sun_mrng_end_time.setText("");
                et_sun_eve_start_timing.setText("");
                et_sun_eve_end_time.setText("");
                break;
        }
    }

    public List<String> getFirstLayoutTimings(int index) {
        List<String> list_timings = new ArrayList<>();
        String mrng_start_time = "", mrng_end_time = "", eve_start_time = "", eve_end_time = "", mrng_interval_time = "", eve_interval_time = "";

        switch (index) {
            case 0:
                mrng_start_time = ValidateEditText(et_mon_mrng_start_time) + " " + ValidateSpinner(spinner_mon_mrng_start_ampm);
                mrng_end_time = ValidateEditText(et_mon_mrng_end_time) + " " + ValidateSpinner(spinner_mon_mrng_end_ampm);
                eve_start_time = ValidateEditText(et_mon_eve_start_timing) + " " + ValidateSpinner(spinner_mon_eve_start_ampm);
                eve_end_time = ValidateEditText(et_mon_eve_end_time) + " " + ValidateSpinner(spinner_mon_mrng_end_ampm);
                mrng_interval_time = et_mon_mrng_time_interval.getText().toString();
                eve_interval_time = et_mon_eve_time_interval.getText().toString();
                break;
            case 1:
                mrng_start_time = ValidateEditText(et_tue_mrng_start_time) + " " + ValidateSpinner(spinner_tue_mrng_start_ampm);
                mrng_end_time = ValidateEditText(et_tue_mrng_end_time) + " " + ValidateSpinner(spinner_tue_mrng_end_ampm);
                eve_start_time = ValidateEditText(et_tue_eve_start_timing) + " " + ValidateSpinner(spinner_tue_eve_start_ampm);
                eve_end_time = ValidateEditText(et_tue_eve_end_time) + " " + ValidateSpinner(spinner_tue_mrng_end_ampm);
                mrng_interval_time = et_tue_mrng_time_interval.getText().toString();
                eve_interval_time = et_tue_eve_time_interval.getText().toString();
                break;
            case 2:
                mrng_start_time = ValidateEditText(et_wed_mrng_start_time) + " " + ValidateSpinner(spinner_wed_mrng_start_ampm);
                mrng_end_time = ValidateEditText(et_wed_mrng_end_time) + " " + ValidateSpinner(spinner_wed_mrng_end_ampm);
                eve_start_time = ValidateEditText(et_wed_eve_start_timing) + " " + ValidateSpinner(spinner_wed_eve_start_ampm);
                eve_end_time = ValidateEditText(et_wed_eve_end_time) + " " + ValidateSpinner(spinner_wed_mrng_end_ampm);
                mrng_interval_time = et_wed_mrng_time_interval.getText().toString();
                eve_interval_time = et_wed_eve_time_interval.getText().toString();
                break;
            case 3:
                mrng_start_time = ValidateEditText(et_thu_mrng_start_time) + " " + ValidateSpinner(spinner_thu_mrng_start_ampm);
                mrng_end_time = ValidateEditText(et_thu_mrng_end_time) + " " + ValidateSpinner(spinner_thu_mrng_end_ampm);
                eve_start_time = ValidateEditText(et_thu_eve_start_timing) + " " + ValidateSpinner(spinner_thu_eve_start_ampm);
                eve_end_time = ValidateEditText(et_thu_eve_end_time) + " " + ValidateSpinner(spinner_thu_mrng_end_ampm);
                mrng_interval_time = et_thu_mrng_time_interval.getText().toString();
                eve_interval_time = et_thu_eve_time_interval.getText().toString();
                break;
            case 4:
                mrng_start_time = ValidateEditText(et_fri_mrng_start_time) + " " + ValidateSpinner(spinner_fri_mrng_start_ampm);
                mrng_end_time = ValidateEditText(et_fri_mrng_end_time) + " " + ValidateSpinner(spinner_fri_mrng_end_ampm);
                eve_start_time = ValidateEditText(et_fri_eve_start_timing) + " " + ValidateSpinner(spinner_fri_eve_start_ampm);
                eve_end_time = ValidateEditText(et_fri_eve_end_time) + " " + ValidateSpinner(spinner_fri_mrng_end_ampm);
                mrng_interval_time = et_fri_mrng_time_interval.getText().toString();
                eve_interval_time = et_fri_eve_time_interval.getText().toString();
                break;
            case 5:
                mrng_start_time = ValidateEditText(et_sat_mrng_start_time) + " " + ValidateSpinner(spinner_sat_mrng_start_ampm);
                mrng_end_time = ValidateEditText(et_sat_mrng_end_time) + " " + ValidateSpinner(spinner_sat_mrng_end_ampm);
                eve_start_time = ValidateEditText(et_sat_eve_start_timing) + " " + ValidateSpinner(spinner_sat_eve_start_ampm);
                eve_end_time = ValidateEditText(et_sat_eve_end_time) + " " + ValidateSpinner(spinner_sat_mrng_end_ampm);
                mrng_interval_time = et_sat_mrng_time_interval.getText().toString();
                eve_interval_time = et_sat_eve_time_interval.getText().toString();
                break;
            case 6:
                mrng_start_time = ValidateEditText(et_sun_mrng_start_time) + " " + ValidateSpinner(spinner_sun_mrng_start_ampm);
                mrng_end_time = ValidateEditText(et_sun_mrng_end_time) + " " + ValidateSpinner(spinner_sun_mrng_end_ampm);
                eve_start_time = ValidateEditText(et_sun_eve_start_timing) + " " + ValidateSpinner(spinner_sun_eve_start_ampm);
                eve_end_time = ValidateEditText(et_sun_eve_end_time) + " " + ValidateSpinner(spinner_sun_mrng_end_ampm);
                mrng_interval_time = et_sun_mrng_time_interval.getText().toString();
                eve_interval_time = et_sun_eve_time_interval.getText().toString();
                break;

        }
        list_timings.add(mrng_start_time);
        list_timings.add(mrng_end_time);
        list_timings.add(eve_start_time);
        list_timings.add(eve_end_time);
        list_timings.add(mrng_interval_time);
        list_timings.add(eve_interval_time);

        return list_timings;
    }

    public String ValidateTimings(EditText editText, Spinner spinner) {
        if (editText != null && spinner != null) {
            if (editText.getText().toString().length() > 0) {
                if (spinner.getSelectedItem().toString().length() > 0) {
                    return editText.getText().toString() + " " + spinner.getSelectedItem().toString();
                } else {
                    return editText.getText().toString() + " am";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public String ValidateEditText(EditText editText) {
        if (editText != null && editText.getText().toString().length() > 0) {
            return editText.getText().toString();
        } else {
            return "";
        }
    }

    public String ValidateSpinner(Spinner spinner) {
        if (spinner != null && spinner.getSelectedItem().toString().length() > 0) {
            return spinner.getSelectedItem().toString();
        } else {
            return "";
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.switch_mon:
                setUpdateVisibility(b, 0);
                break;
            case R.id.switch_tue:
                setUpdateVisibility(b, 1);

                break;
            case R.id.switch_wed:
                setUpdateVisibility(b, 2);

                break;
            case R.id.switch_thu:
                setUpdateVisibility(b, 3);

                break;
            case R.id.switch_fri:
                setUpdateVisibility(b, 4);

                break;
            case R.id.switch_sat:
                setUpdateVisibility(b, 5);

                break;
            case R.id.switch_sun:
                setUpdateVisibility(b, 6);

                break;
        }
    }

    public void setUpdateVisibility(boolean b, int index) {
//        Log.d(TAG,"first timings:-"+list_first_update_time.toString());
        if (b) {
//            if (!selected_layouts[index]) {
            selected_layouts[index] = true;
            list_linear_box.get(index).setBackgroundResource(R.drawable.linearlayoutborderlight);
//                if(list_first_update_time.size()==6) {
            setLayoutTimingsFirstly(index);
//                }
//            }
        } else {
//            if(selected_layouts[index]){
            selected_layouts[index] = false;
            list_linear_box.get(index).setBackgroundResource(R.drawable.linearlayoutborder);
            clearLayoutTimings(index);
//            }
        }
    }

    class EditTextWatcher implements TextWatcher {
        boolean is_back = false;
        EditText et_time;
        int position;

        EditTextWatcher(EditText et_time, int position) {
            this.et_time = et_time;
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            Log.d("sunil","length:-"+charSequence.toString().length());
            String text = charSequence.toString();
            if (text.length() == 2) {
                if (is_back) {
                    et_time.setText(text.substring(0, text.length() - 1));
                } else {
                    try {
                        int val = Integer.parseInt(text);
                        if (val > 12) {
                            et_time.setText(text.substring(0, text.length() - 1));
                        }
                    } catch (Exception e) {
                        Log.d(TAG, e.toString());
                    }
                }
            } else {
                if (text.length() == 3) {

                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = et_time.getText().toString();
            try {
                if (text.length() == 1) {
                    int val = Integer.parseInt(text);
                    if (val > 1) {
                        et_time.setText("0" + et_time.getText().toString() + ":");
                    }
                } else {
                    if (text.length() == 2) {
                        et_time.setText(et_time.getText().toString() + ":");
                    } else {
                        if (text.length() == 4) {
                            char lastchar = text.charAt(text.length() - 1);
                            try {
                                int val = Integer.parseInt(lastchar + "");
                                if (val > 5) {
                                    String sub = text.substring(0, text.length() - 1);
//                                Log.d("sunil",sub);
                                    char lastchar1 = text.charAt(text.length() - 1);
                                    et_time.setText(sub + "0" + lastchar1);
                                }
                            } catch (Exception e) {
                                Log.d(TAG, e.toString());
                            }
                        }
                    }
                }
                if (text.length() == 3) {
                    is_back = true;
                } else {
                    is_back = false;
                }
            } catch (Exception e) {

            }
            et_time.setSelection(et_time.getText().length());
            if (!is_updating) {
                is_Start_Editing = true;
                selected_layouts[position] = true;
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.dialog_mon:
                SetVisibility(0);
                break;
            case R.id.dialog_tue:
                SetVisibility(1);
                break;
            case R.id.dialog_wed:
                SetVisibility(2);
                break;
            case R.id.dialog_thu:
                SetVisibility(3);
                break;
            case R.id.dialog_fri:
                SetVisibility(4);
                break;
            case R.id.dialog_sat:
                SetVisibility(5);
                break;
            case R.id.dialog_sun:
                SetVisibility(6);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE) {
            Log.d("sun", "on activity result");
            if (resultCode == Activity.RESULT_OK) {
                if (null == data)
                    return;
                Uri selectedImageUri = data.getData();
                System.out.println(selectedImageUri.toString());
                // MEDIA GALLERY
                selectedImagePath = getPath(
                        getActivity(), selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    String image_path_string = selectedImagePath;
                    Log.d("sunil", selectedImagePath);

                    Bitmap bmImg = BitmapFactory.decodeFile(image_path_string);
                    SharedPreferences sp = getActivity().getSharedPreferences("doctor.txt", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("profile_pic", image_path_string);
                    editor.commit();
                    profile_image.setImageBitmap(bmImg);
                } else {
                    Toast.makeText(getActivity(), "File Selected is corrupted", Toast.LENGTH_LONG).show();
                }
                System.out.println("Image Path =" + selectedImagePath);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<ClinicInfoPOJOResult> horizontalList;
        private Context context;
        private Activity activity;


        public class MyViewHolder extends RecyclerView.ViewHolder {

            public CardView cv_clinic;
            public ImageView imageView;
            public LinearLayout linearLayout;
            public TextView tv_clinic_name;
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

            public MyViewHolder(View view) {
                super(view);
                cv_clinic = (CardView) view.findViewById(R.id.cv_clinic);
                imageView = (ImageView) view.findViewById(R.id.scrollableimg);
                linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout);
                tv_clinic_name = (TextView) view.findViewById(R.id.tv_clinic_name);
                tv_clinic_address = (TextView) view.findViewById(R.id.tv_clinic_address);
                tv_city = (TextView) view.findViewById(R.id.tv_city);
                tv_pin_code = (TextView) view.findViewById(R.id.tv_pin_code);
                tv_degree = (TextView) view.findViewById(R.id.tv_degree);
                tv_specialist = (TextView) view.findViewById(R.id.tv_specialist);
                tv_mon_mrng_time = (TextView) view.findViewById(R.id.tv_mon_mrng_time);
                tv_mon_eve_time = (TextView) view.findViewById(R.id.tv_mon_eve_time);
                tv_tue_mrng_time = (TextView) view.findViewById(R.id.tv_tue_mrng_time);
                tv_tue_eve_time = (TextView) view.findViewById(R.id.tv_tue_eve_time);
                tv_wed_mrng_time = (TextView) view.findViewById(R.id.tv_wed_mrng_time);
                tv_thu_mrng_time = (TextView) view.findViewById(R.id.tv_thu_mrng_time);
                tv_thu_eve_time = (TextView) view.findViewById(R.id.tv_thu_eve_time);
                tv_wed_eve_time = (TextView) view.findViewById(R.id.tv_wed_eve_time);
                tv_fri_mrng_time = (TextView) view.findViewById(R.id.tv_fri_mrng_time);
                tv_fri_eve_time = (TextView) view.findViewById(R.id.tv_fri_eve_time);
                tv_sat_mrng_time = (TextView) view.findViewById(R.id.tv_sat_mrng_time);
                tv_sat_eve_time = (TextView) view.findViewById(R.id.tv_sat_eve_time);
                tv_sun_mrng_time = (TextView) view.findViewById(R.id.tv_sun_mrng_time);
                tv_sun_eve_time = (TextView) view.findViewById(R.id.tv_sun_eve_time);
                ll_mon = (LinearLayout) view.findViewById(R.id.ll_mon);
                ll_tue = (LinearLayout) view.findViewById(R.id.ll_tue);
                ll_wed = (LinearLayout) view.findViewById(R.id.ll_wed);
                ll_thu = (LinearLayout) view.findViewById(R.id.ll_thu);
                ll_fri = (LinearLayout) view.findViewById(R.id.ll_fri);
                ll_sat = (LinearLayout) view.findViewById(R.id.ll_sat);
                ll_sun = (LinearLayout) view.findViewById(R.id.ll_sun);
            }
        }


        public HorizontalAdapter(Context context, Activity activity, List<ClinicInfoPOJOResult> horizontalList) {
            this.horizontalList = horizontalList;
            this.context = context;
            this.activity = activity;
        }

        @Override
        public HorizontalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inflate_clinic_info, parent, false);

            return new HorizontalAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final HorizontalAdapter.MyViewHolder holder, final int position) {
//            holder.tv_task_no.setText("Task :- "+(position+1));
            ClinicInfoPOJOResult pojo = horizontalList.get(position);
            holder.tv_clinic_name.setText(pojo.getClinic_name());
            holder.tv_clinic_address.setText(pojo.getClinic_address());
            holder.tv_city.setText(pojo.getClinic_city());
            holder.tv_pin_code.setText(pojo.getClinic_pincode());
            holder.tv_degree.setText(pojo.getClinic_degree());
            holder.tv_specialist.setText(pojo.getClinic_specialist());

            String mon_mrng_time = ValidateString(pojo.getMon_mor_starttime()) + "-" + ValidateString(pojo.getMon_mor_endtime());
            String mon_eve_time = ValidateString(pojo.getMon_eve_startime()) + "-" + ValidateString(pojo.getMon_eve_endtime());
            String Tue_mrng_time = ValidateString(pojo.getTue_mor_starttime()) + "-" + ValidateString(pojo.getTue_mor_endtime());
            String Tue_eve_time = ValidateString(pojo.getTue_eve_startime()) + "-" + ValidateString(pojo.getTue_eve_endtime());
            String Wed_mrng_time = ValidateString(pojo.getWed_mor_starttime()) + "-" + ValidateString(pojo.getWed_mor_endtime());
            String Wed_eve_time = ValidateString(pojo.getWed_eve_startime()) + "-" + ValidateString(pojo.getWed_eve_endtime());
            String Thus_mrng_time = ValidateString(pojo.getThus_mor_starttime()) + "-" + ValidateString(pojo.getThus_mor_endtime());
            String Thus_eve_time = ValidateString(pojo.getThus_eve_startime()) + "-" + ValidateString(pojo.getThus_eve_endtime());
            String Fri_mrng_time = ValidateString(pojo.getFri_mor_starttime()) + "-" + ValidateString(pojo.getFri_mor_endtime());
            String Fri_eve_time = ValidateString(pojo.getFri_eve_startime()) + "-" + ValidateString(pojo.getFri_eve_endtime());
            String Sat_mrng_time = ValidateString(pojo.getSat_mor_starttime()) + "-" + ValidateString(pojo.getSat_mor_endtime());
            String Sat_eve_time = ValidateString(pojo.getSat_eve_startime()) + "-" + ValidateString(pojo.getSat_eve_endtime());
            String Sun_mrng_time = ValidateString(pojo.getSun_mor_starttime()) + "-" + ValidateString(pojo.getSun_mor_endtime());
            String Sun_eve_time = ValidateString(pojo.getSun_eve_startime()) + "-" + ValidateString(pojo.getSun_eve_endtime());

            if (ValidateNewString(pojo.getMon_mor_starttime(), pojo.getMon_mor_endtime(), pojo.getMon_eve_startime(), pojo.getMon_eve_endtime())) {
                holder.ll_mon.setVisibility(View.VISIBLE);
            } else {
                holder.ll_mon.setVisibility(View.GONE);
            }
            if (ValidateNewString(pojo.getTue_mor_starttime(), pojo.getTue_mor_endtime(), pojo.getTue_eve_startime(), pojo.getTue_eve_endtime())) {
                holder.ll_tue.setVisibility(View.VISIBLE);
            } else {
                holder.ll_tue.setVisibility(View.GONE);
            }
            if (ValidateNewString(pojo.getWed_mor_starttime(), pojo.getWed_mor_endtime(), pojo.getWed_eve_startime(), pojo.getWed_eve_endtime())) {
                holder.ll_wed.setVisibility(View.VISIBLE);
            } else {
                holder.ll_wed.setVisibility(View.GONE);
            }
            if (ValidateNewString(pojo.getThus_mor_starttime(), pojo.getThus_mor_endtime(), pojo.getThus_eve_startime(), pojo.getThus_eve_endtime())) {
                holder.ll_thu.setVisibility(View.VISIBLE);
            } else {
                holder.ll_thu.setVisibility(View.GONE);
            }
            if (ValidateNewString(pojo.getFri_mor_starttime(), pojo.getFri_mor_endtime(), pojo.getFri_eve_startime(), pojo.getFri_eve_endtime())) {
                holder.ll_fri.setVisibility(View.VISIBLE);
            } else {
                holder.ll_fri.setVisibility(View.GONE);
            }
            if (ValidateNewString(pojo.getSat_mor_starttime(), pojo.getSat_mor_endtime(), pojo.getSat_eve_startime(), pojo.getSat_eve_endtime())) {
                holder.ll_sat.setVisibility(View.VISIBLE);
            } else {
                holder.ll_sat.setVisibility(View.GONE);
            }
            if (ValidateNewString(pojo.getSun_mor_starttime(), pojo.getSun_mor_endtime(), pojo.getSun_eve_startime(), pojo.getSun_eve_endtime())) {
                holder.ll_sun.setVisibility(View.VISIBLE);
            } else {
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


            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.linearLayout.getVisibility() == View.VISIBLE) {
                        holder.linearLayout.setVisibility(View.GONE);
                    } else {
                        holder.linearLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        public String ValidateString(String s) {
            if (s.equals("")) {
                return "00:00";
            } else {
                return s;
            }
        }

        public boolean ValidateNewString(String start_mrng_time, String end_mrng_time, String eve_start_time, String eve_end_time) {
            if (!start_mrng_time.equals("") || !end_mrng_time.equals("") || !eve_start_time.equals("") || !eve_end_time.equals("")) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }
}






