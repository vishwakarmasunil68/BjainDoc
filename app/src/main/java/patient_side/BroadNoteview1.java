package patient_side;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.emobi.bjaindoc.ApiConfig;
import com.emobi.bjaindoc.InfoApps;
import com.emobi.bjaindoc.R;
import com.emobi.bjaindoc.Util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import database.PreferenceData;

/**
 * Created by Emobi-Android-002 on 8/24/2016.
 */
public class BroadNoteview1 extends Fragment {
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    public  static String patient_id,patient_name,patient_email,patient_status;
    public static ArrayList<InfoApps> contactDetails1;
    UsersAdapter_Broadnote mAdapterbroad;
    Activity activity;
    FloatingActionButton fab;
    InfoApps Detailapp;


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private int mCurrentSelectedPosition;
    LinearLayout navigation_header_patient;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
//		isInternetOn();
        View rootView = inflater.inflate(R.layout.broadnote, container, false);

        //         mSnackBarLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        contactDetails1=new ArrayList<InfoApps>();
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

//        setUpNavigationDrawer();
        refresh();
//        mNavigationView.setCheckedItem(R.id.navigation_item_4);

        return rootView;
    }
    public void refresh(){
//        mNavigationView.setCheckedItem(R.id.navigation_item_3);


        new CallServices().execute(ApiConfig.VIEW_PATIENT_NOTES_VIEW);
    }
    public class CallServices extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("p_note_id", PreferenceData.getPatientId(getActivity())));
//            namevaluepair.add(new BasicNameValuePair("note_cat", "public"));

            try {

                result = Util.executeHttpPost(params[0], namevaluepair);

                Log.e("result", result.toString());

            } catch (Exception e) {

                e.printStackTrace();

            }

            return result;


        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (pd != null) {
                pd.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    Log.e("Post Method", result.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        Log.e("2", jsonObject2.toString());
                        String message = jsonObject2.getString("message");
                        if (message.equalsIgnoreCase("success")) {
                            String med_mess = jsonObject2.getString("note_msg");
                        /*String med_date = jsonObject2.getString("bro_date");
                        String med_time = jsonObject2.getString("bro_time");*/

                            Detailapp = new InfoApps();
                            Detailapp.setDataAdd(med_mess);
                        /*Detailapp.setNumber(med_date);
                        Detailapp.setName(med_time);*/
						/*Detailapp.setId(patient_id);
						Detailapp.setName(patient_name);
						Detailapp.setEmail_id(patient_email);
						Detailapp.setStatus(patient_status);*/
                            contactDetails1.add(Detailapp);
                            mAdapterbroad = new UsersAdapter_Broadnote(contactDetails1, getActivity());
                            mRecyclerView.setAdapter(mAdapterbroad);
                        }
                        else {
                            Toast.makeText(getActivity()," no notification for you", Toast.LENGTH_LONG).show();
//                            getActivity().finish();
                        }
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
    }

    /*private void setUpNavigationDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        try {
            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setLogo(R.drawable.bjainicon);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setSubtitle(getString(R.string.subtitle));
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("  Notification");
        } catch (Exception ignored) {
        }*/


        /*mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        View headerView = mNavigationView.inflateHeaderView(R.layout.drawer_header);
//        View view=mNavigationView.inflateHeaderView(R.layout.drawer_header);
        CircleImageView img= (CircleImageView) headerView.findViewById(R.id.profilepic);

        navigation_header_patient= (LinearLayout) headerView.findViewById(R.id.navigation_header_patient);
        TextView tv= (TextView) headerView.findViewById(R.id.profilename);
        try {
            tv.setText(PreferenceData.getPatientName(getApplicationContext()));
        }
        catch (Exception e){
        }
//        if (FacebookActivity.prFmofilepicimage!=null){
        try {
            File f = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "profile.png");
            Log.d("sunil", "file:-" + f.exists());
            if(f.exists()) {
                Bitmap bmImg = BitmapFactory.decodeFile(f.toString());
                img.setImageBitmap(bmImg);
            }
            else{
                SharedPreferences sp=getSharedPreferences("doctor.txt", Context.MODE_PRIVATE);
                String imag_path=sp.getString("profile_pic","");
                if(imag_path.equals("")){
                    img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                }
                else{
                    File f1=new File(imag_path);
                    Bitmap bmImg1 = BitmapFactory.decodeFile(f1.toString());
                    img.setImageBitmap(bmImg1);
                }
            }
        }
        catch (Exception e){
            Log.d("sunil",e.toString());
            SharedPreferences sp=getSharedPreferences("doctor.txt", Context.MODE_PRIVATE);
            String imag_path=sp.getString("profile_pic","");
            if(imag_path.equals("")){
                img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
            }
            else{
                File f1=new File(imag_path);
                Bitmap bmImg1 = BitmapFactory.decodeFile(f1.toString());
                img.setImageBitmap(bmImg1);
            }
        }
       *//* }
        else {
            img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        }*//*
        *//*if (LoginActivity.textname!=null){
            tv.setText(LoginActivity.textname);
        }
        else {
            tv.setText("Profile UserName");
        }*//*
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(BroadNote_view.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        navigation_header_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"context",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(BroadNote_view.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_5:
                        mCurrentSelectedPosition = 1;
                        Intent i=new Intent(BroadNote_view.this, Search_Doctor.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.navigation_item_3:
                        mCurrentSelectedPosition = 2;
                        Intent i1=new Intent(BroadNote_view.this, BroadCast_view.class);
                        startActivity(i1);
                        finish();
                        break;
                    case R.id.navigation_item_4:
//                        mCurrentSelectedPosition = 3;
//                        startActivity(new Intent(BroadNote_view.this, BroadNote_view.class));
                        break;
                    case R.id.navigation_item_6:
                        PreferenceData.setPatientLogin(getApplicationContext(),false);
                        Intent intent=new Intent(BroadNote_view.this,FirstPage.class);
                        Toast.makeText(getApplicationContext(), "logout successfully", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finishAffinity();
                        mCurrentSelectedPosition = 5;
                        break;
                }

                mDrawerLayout.closeDrawer(mNavigationView);
                return true;
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle(getString(R.string.drawer_opened));
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }*/



    /*@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
*/

        /*if (item != null && item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
                mDrawerLayout.closeDrawer(mNavigationView);
            } else {
                mDrawerLayout.openDrawer(mNavigationView);
            }
            return true;
        }*/

//        return super.onOptionsItemSelected(item);

//        return super.onOptionsItemSelected(item);
//    }
}
