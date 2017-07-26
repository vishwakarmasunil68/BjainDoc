package patient_side;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.bjaindoc.ApiConfig;
import com.emobi.bjaindoc.InfoApps;
import com.emobi.bjaindoc.R;
import com.emobi.bjaindoc.TypefaceSpan;
import com.emobi.bjaindoc.Util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Emobi-Android-002 on 8/24/2016.
 */
public class Search_Doctor extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    Typeface tf;

    public static String designation, address, reg_id, reg_name, reg_email, reg_pass, reg_cpass, dob, reg_department, reg_clinic_address, reg_designation, reg_specialist, reg_degree, reg_photo;
    public static String emailid, text, profilepicimage, textname, bd;
    EditText et_search;
    public static String patient_id, patient_name, patient_email, patient_status;
    public static ArrayList<InfoApps> contactDetails1;
    UsersAdapter_Serach_Doc mAdapterbroad;
    FloatingActionButton fab;
    Button btn_submit;
    InfoApps Detailapp;
    ImageView imagesearch, imagecancel;
    TextView text_result;
ImageView img;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private int mCurrentSelectedPosition;
    LinearLayout navigation_header_patient;
    CallServices call;
    ActionBar actionBar;

    @TargetApi(Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.searchdoc);
        refresh();

    }


//        View view=mNavigationView.inflateHeaderView(R.layout.drawer_header);

       /* }
        else {
            img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        }*/
        /*if (LoginActivity.textname!=null){
            tv.setText(LoginActivity.textname);
        }
        else {
            tv.setText("Profile UserName");
        }*/













    public void refresh(){
        actionBar=getSupportActionBar();
        SpannableString s = new SpannableString("  Find Doctor");
        s.setSpan(new TypefaceSpan(this, "fonts/Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(R.drawable.bjainicon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setSubtitle(getString(R.string.subtitle));
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("  Find Doctor");
        actionBar.setTitle(s);
        et_search = (EditText)findViewById(R.id.et_search);
        btn_submit=(Button)findViewById(R.id.btn_submit);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        imagesearch= (ImageView) findViewById(R.id.imagesearch);
        imagecancel= (ImageView) findViewById(R.id.imagecancel);
        text_result= (TextView) findViewById(R.id.text_result);
        contactDetails1=new ArrayList<InfoApps>();
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Search_Doctor.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        imagesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagecancel.setVisibility(View.VISIBLE);
                et_search.setVisibility(View.VISIBLE);
                imagesearch.setVisibility(View.INVISIBLE);
            }
        });

        imagecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesearch.setVisibility(View.VISIBLE);
                imagecancel.setVisibility(View.INVISIBLE);
                et_search.setVisibility(View.INVISIBLE);
                et_search.setText("");
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                notify();
//                new CallServices().execute(ApiConfig.FIND_DOCTOR_URL);

                Log.d("sunil",s.toString());
                if (s.length()>0) {
                    contactDetails1.clear();
                    try{
                        call.cancel(true);
                    }
                    catch (Exception e){
                        Log.d("sunil",e.toString());
                    }
                    call=new CallServices();
                    call.execute(ApiConfig.FIND_DOCTOR_URL,s.toString());


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    public class CallServices extends AsyncTask<String, String, String> {
        String text=et_search.getText().toString().trim();
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(Search_Doctor.this);

            pd.setMessage("Searching..");
            pd.setIndeterminate(false);
            pd.setCancelable(true);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(!isCancelled()){
                namevaluepair.add(new BasicNameValuePair("reg_name", params[1]));
                namevaluepair.add(new BasicNameValuePair("clinic_address", params[1]));

                try {

                    result = Util.executeHttpPost(params[0], namevaluepair);

                    Log.e("result", result.toString());

                } catch (Exception e) {

                    e.printStackTrace();

                }
                return result;
            }
            else{
                return null;
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d("sunil","canceled");
            try {
                if (pd != null) {
                    pd.dismiss();
                }
            }
            catch (Exception e){

            }
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
//            if (pd != null) {
//                pd.dismiss();
            if(result!=null){
                try {

                    JSONArray jsonArray = new JSONArray(result);
                    Log.e("Post Method", result.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        Log.e("2", jsonObject2.toString());
                        String message = jsonObject2.getString("d_message");
                        Log.e("message",message);
                        if (message.equalsIgnoreCase("success")) {
                            reg_name = jsonObject2.getString("reg_name");
                            reg_id=jsonObject2.getString("reg_id");
                            address= jsonObject2.getString("clinic_address");
                            designation = jsonObject2.getString("designation");
                            reg_photo = jsonObject2.getString("photo");
                            Detailapp = new InfoApps();
                            Detailapp.setDataAdd(address);
                            Detailapp.setDesignation(designation);
                            Detailapp.setName(reg_name);
                            Detailapp.setId(reg_id);
                            Detailapp.setNumber(reg_photo);
                        /*Detailapp.setNumber(med_date);
                        Detailapp.setName(med_time);*/
						/*Detailapp.setId(patient_id);
						Detailapp.setName(patient_name);
						Detailapp.setEmail_id(patient_email);
						Detailapp.setStatus(patient_status);*/
                            contactDetails1.add(Detailapp);



                        }
                        else {
                            Toast.makeText(Search_Doctor.this,"Sorry no result found", Toast.LENGTH_LONG).show();
                            text_result.setVisibility(View.VISIBLE);
                            text_result.setTypeface(tf);
                        }

                    }
                    if(contactDetails1.size()>0) {
                        text_result.setVisibility(View.GONE);
                        mAdapterbroad = new UsersAdapter_Serach_Doc(contactDetails1, Search_Doctor.this);
                        mRecyclerView.setAdapter(mAdapterbroad);
                    }
                    else{
                        Toast.makeText(Search_Doctor.this,"Sorry no result found", Toast.LENGTH_LONG).show();
                        text_result.setVisibility(View.VISIBLE);
                        text_result.setTypeface(tf);
                    }

                    /*if(contactDetails1.size()>0) {
                        text_result.setVisibility(View.GONE);
                        mAdapterbroad = new UsersAdapter_Serach_Doc(contactDetails1, Search_Doctor.this);
                        mRecyclerView.setAdapter(mAdapterbroad);
                    }
                    else{
                        Toast.makeText(Search_Doctor.this,"Sorry no result found",Toast.LENGTH_LONG).show();
                        text_result.setVisibility(View.VISIBLE);
                        text_result.setTypeface(tf);
                    }*/
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            try {
                if (pd != null) {
                    pd.dismiss();
                }
            }
            catch (Exception e){

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main1, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        int mNotifCount = 0;
        View count = menu.findItem(R.id.action_notification).getActionView();
//        Home_Fragment.find.setText(String.valueOf(mNotifCount));

        if (searchItem != null) {
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

            // use this method for search process
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // use this method when query submitted
                    Toast.makeText(Search_Doctor.this, query, Toast.LENGTH_SHORT).show();
                    Log.d("sunil",query.toString());
                    if (query.length()>0) {
                        contactDetails1.clear();
                        try {
                            call.cancel(true);
                        } catch (Exception e) {
                            Log.d("sunil", e.toString());
                        }
                        call = new CallServices();
                        call.execute(ApiConfig.FIND_DOCTOR_URL, query.toString());
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // use this method for auto complete search process
                        try {
                        contactDetails1.clear();

                        } catch (Exception e) {
                            Log.d("sunil", e.toString());
                        }
                    return false;
                }
            });

        }
        else {

                try {
                    contactDetails1.clear();
                } catch (Exception e) {
                    Log.d("sunil", e.toString());
                }
            }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
