package com.emobi.bjaindoc.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.emobi.bjaindoc.ApiConfig;
import com.emobi.bjaindoc.R;
import com.emobi.bjaindoc.pojo.urgent.UrgentChatResultPOJO;
import com.emobi.bjaindoc.pojo.urgentchatpatient.NewUrgentChatResultPOJO;
import com.emobi.bjaindoc.pojo.urgentchatpatient.UrgentPatientPOJO;
import com.emobi.bjaindoc.pojo.urgentchatpatient.UrgentPatientResultPOJO;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.emobi.bjaindoc.utls.Database;
import com.emobi.bjaindoc.utls.StringUtils;
import com.emobi.bjaindoc.utls.ToastClass;
import com.emobi.bjaindoc.widgets.BaseSwipListAdapter;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import database.PreferenceData;

public class UrgentChatPatientListActivity extends AppCompatActivity implements WebServicesCallBack{

    private final String TAG=getClass().getSimpleName();
    private final static String GET_ALL_PATIENT_LIST="get_all_patient_list";

    @BindView(R.id.backarrow)
    ImageView backarrow;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;

    List<TextView> list_text_views=new ArrayList<>();
    Database helper;


    @BindView(R.id.listView)
    SwipeMenuListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent_chat_patient_list);

        ButterKnife.bind(this);
        helper=new Database(this);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        try{
//            String saved_patients=Pref.GetStringPref(getApplicationContext(),StringUtils.URGENT_CHAT_DATA,"");
////                JSONObject jsonObject= new JSONObject(saved_patients);
//            Log.d(TAG,"saved_patients:-"+saved_patients);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Delete");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // set item delete button
                openItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
//                SwipeMenuItem deleteItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
//                        0x3F, 0x25)));
//                // set item width
//                deleteItem.setWidth(dp2px(90));
//                // set a icon
//                deleteItem.setIcon(R.drawable.ic_delete);
//                // add to menu
//                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // delete
                        Log.d(TAG,"patient_id:-"+position);
                        try{
                            deleteUrgentPatient(list_of_patients.get(position).getP_id());
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                        break;

                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        // set SwipeListener
        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        listView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

        // other setting
//		listView.setCloseInterpolator(new BounceInterpolator());

        // test item long click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
    class AppAdapter extends BaseSwipListAdapter {
        List<UrgentPatientResultPOJO> list_of_patients;
        AppAdapter(List<UrgentPatientResultPOJO> list_of_patients){
            this.list_of_patients=list_of_patients;
        }
        @Override
        public int getCount() {
            return list_of_patients.size();
        }

        @Override
        public UrgentPatientResultPOJO getItem(int position) {
            return list_of_patients.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.inflate_patient_list, null);
                new ViewHolder(convertView);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.tv_name.setText(list_of_patients.get(position).getP_name());
            holder.tv_email.setText(list_of_patients.get(position).getP_login_id());
            String image_url="";
            holder.txt_last_seenid.setTag(list_of_patients.get(position).getP_id());
//            txt_last_seenid.setVisibility(View.GONE);



            list_text_views.add(holder.txt_last_seenid);
            if(list_of_patients.get(position).getP_photo().contains("upload/"))
            {
                image_url="http://www.bjain.com/doctor/"+list_of_patients.get(position).getP_photo();
            }
            else{
                image_url="http://www.bjain.com/doctor/upload/"+list_of_patients.get(position).getP_photo();
            }
            Picasso.with(getApplicationContext())
                    .load(image_url)
                    .error(R.drawable.ic_action_person)
                    .placeholder(R.drawable.ic_action_person)
                    .into(holder.iv_profile);
            try{
                List<NewUrgentChatResultPOJO> list_stored_chats=helper.getStoredUrgentChatByPID(list_of_patients.get(position).getU_chat_p_id());
                List<NewUrgentChatResultPOJO> list_server_chats=helper.getSERVERUrgentChatByPID(list_of_patients.get(position).getU_chat_p_id());
                Log.d(TAG,"serverchat:-"+list_server_chats.size());
                Log.d(TAG,"storedchat:-"+list_stored_chats.size());
                if(list_stored_chats.size()>0){
                    holder.txt_last_seenid.setText(String.valueOf(list_server_chats.size()-list_stored_chats.size()));
                    holder.txt_last_seenid.setVisibility(View.VISIBLE);
                }
                else{
                    holder.txt_last_seenid.setText(String.valueOf(list_server_chats.size()));
                    holder.txt_last_seenid.setVisibility(View.VISIBLE);
                }
                if(holder.txt_last_seenid.getText().toString().equals("0")){
                    holder.txt_last_seenid.setVisibility(View.INVISIBLE);
//                    holder.txt_last_seenid.setText(list_of_patients.get(position).getU_time1());
                }
            }
            catch (Exception e){
                Log.d(TAG,e.toString());
            }

            final int finalI = position;
            holder.iv_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(UrgentChatPatientListActivity.this, NewUrgentChatActivity.class);
                    intent.putExtra("patient",list_of_patients.get(finalI));
                    intent.putExtra("position",finalI);
                    startActivityForResult(intent, 101);
                }
            });
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(UrgentChatPatientListActivity.this, NewUrgentChatActivity.class);
                    intent.putExtra("patient",list_of_patients.get(finalI));
                    intent.putExtra("position",finalI);
                    startActivityForResult(intent, 101);
                }
            });
            holder.tv_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(UrgentChatPatientListActivity.this, NewUrgentChatActivity.class);
                    intent.putExtra("patient",list_of_patients.get(finalI));
                    intent.putExtra("position",finalI);
                    startActivityForResult(intent, 101);
                }
            });
//            holder.ll_patient.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent=new Intent(UrgentChatPatientListActivity.this, UrgentChatActivity.class);
//                    intent.putExtra("patient",list_of_patients.get(finalI));
//                    intent.putExtra("position",finalI);
//                    startActivityForResult(intent, 101);
//                }
//            });
            return convertView;
        }

        class ViewHolder {
            LinearLayout ll_patient;
            ImageView iv_profile;
            TextView tv_name;
            TextView tv_email;
            TextView txt_last_seenid;
            public ViewHolder(View view) {
                ll_patient= (LinearLayout) view.findViewById(R.id.ll_patient);
                iv_profile= (ImageView) view.findViewById(R.id.iv_profile);
                tv_name= (TextView) view.findViewById(R.id.tv_name);
                tv_email= (TextView) view.findViewById(R.id.tv_email);
                txt_last_seenid= (TextView) view.findViewById(R.id.txt_last_seenid);
                view.setTag(this);
            }
        }

        @Override
        public boolean getSwipEnableByPosition(int position) {
            if(position % 2 == 0){
                return false;
            }
            return true;
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public void deleteUrgentPatient(String p_id){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("u_chat_p_id", p_id));
        new WebServiceBase(nameValuePairs, this, DELETE_PATIENT, false).execute(ApiConfig.DELETE_URGENT_PATIENT);
    }
    private final String DELETE_PATIENT="delete_patient";
    @Override
    protected void onResume() {
        super.onResume();
        getAllPatients();
        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter(StringUtils.PATIENT_LIST_URGENT_CHAT));
    }

    @Override
    protected void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra("message");
            Log.d(TAG, "chatresult:-" + result);

            try {
                Gson gson = new Gson();
                UrgentChatResultPOJO chatResultPOJO = gson.fromJson(result, UrgentChatResultPOJO.class);
                if(list_of_patients!=null){
                    for(int i=0;i<list_of_patients.size();i++){
                        UrgentPatientResultPOJO urgentPatientResultPOJO=list_of_patients.get(i);
                        if(urgentPatientResultPOJO.getU_chat_p_id().equals(chatResultPOJO.getU_chat_p_id())){
                            try{
                                List<NewUrgentChatResultPOJO> list_stored_chats=helper.getStoredUrgentChatByPID(urgentPatientResultPOJO.getU_chat_p_id());
                                List<NewUrgentChatResultPOJO> list_server_chats=helper.getSERVERUrgentChatByPID(urgentPatientResultPOJO.getU_chat_p_id());
                                Log.d(TAG,"serverchat:-"+list_server_chats.size());
                                Log.d(TAG,"storedchat:-"+list_stored_chats.size());
                                if(list_stored_chats.size()>0){
                                    list_text_views.get(i).setText(String.valueOf(list_server_chats.size()-list_stored_chats.size()));
                                }
                                else{
                                    list_text_views.get(i).setText(String.valueOf(list_server_chats.size()));
                                }
                                if(list_text_views.get(i).getText().toString().equals("0")){
                                    list_text_views.get(i).setText(urgentPatientResultPOJO.getU_time1());
                                }
                            }
                            catch (Exception e){
                                Log.d(TAG,e.toString());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    };

    public void getAllPatients(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));
        new WebServiceBase(nameValuePairs, this, GET_ALL_PATIENT_LIST).execute(ApiConfig.GET_ALL_URGENT_PATIENT);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_ALL_PATIENT_LIST:
                parseAllPatientList(response);
                break;
            case DELETE_PATIENT:
                parseDeleteUrgentPatientResponse(response);
                break;
        }
    }


    public void parseDeleteUrgentPatientResponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            String success=jsonObject.optString("success");
            if(success.equals("true")){
                getAllPatients();
                ToastClass.ShowShortToast(getApplicationContext(),"Patient Deleted");
            }
            else{
                ToastClass.ShowShortToast(getApplicationContext(),"Patient is not deleted");
            }
        }
        catch (Exception e){
            Log.d(TAG,e.toString());
            e.printStackTrace();
        }
    }


    List<UrgentPatientResultPOJO> list_of_patients;
    public void parseAllPatientList(String response){
        Log.d(TAG,"patient list:-"+response);
        try{
            Gson gson=new Gson();
            UrgentPatientPOJO urgentPatientPOJO=gson.fromJson(response,UrgentPatientPOJO.class);
            list_of_patients=urgentPatientPOJO.getUrgentPatientResultPOJOs();
            AppAdapter mAdapter = new AppAdapter(urgentPatientPOJO.getUrgentPatientResultPOJOs());
            listView.setAdapter(mAdapter);
        }
        catch(Exception e){
            e.printStackTrace();
        }
//        try{
//            Gson gson=new Gson();
//            UrgentPatientPOJO urgentPatientPOJO=gson.fromJson(response,UrgentPatientPOJO.class);
//            if(urgentPatientPOJO.getSuccess().equals("true")){
//                Log.d(TAG,"urgent patients:-"+urgentPatientPOJO.toString());
////                list_of_patients.clear();
//                list_of_patients=urgentPatientPOJO.getUrgentPatientResultPOJOs();
//                showPatientList(urgentPatientPOJO.getUrgentPatientResultPOJOs());
////                Pref.SetStringPref(getApplicationContext(),StringUtils.URGENT_CHAT_DATA,response);
//            }
//            else{
//                ToastClass.ShowLongToast(getApplicationContext(),"No Urgent Chat Messages");
//            }
//        }
//        catch (Exception e){
//            Log.d(TAG,e.toString());
//        }
    }

    public void showPatientList(final List<UrgentPatientResultPOJO> list_patients){
        list_text_views.clear();
        ll_scroll.removeAllViews();
        for (int i = 0; i < list_patients.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_patient_list, null);
            LinearLayout ll_patient= (LinearLayout) view.findViewById(R.id.ll_patient);
            ImageView iv_profile= (ImageView) view.findViewById(R.id.iv_profile);
            TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
            TextView tv_email= (TextView) view.findViewById(R.id.tv_email);
            TextView txt_last_seenid= (TextView) view.findViewById(R.id.txt_last_seenid);

            tv_name.setText(list_patients.get(i).getP_name());
            tv_email.setText(list_patients.get(i).getP_login_id());
            String image_url="";
            txt_last_seenid.setTag(list_patients.get(i).getP_id());
//            txt_last_seenid.setVisibility(View.GONE);



            list_text_views.add(txt_last_seenid);
            if(list_patients.get(i).getP_photo().contains("upload/"))
            {
                image_url="http://www.bjain.com/doctor/"+list_patients.get(i).getP_photo();
            }
            else{
                image_url="http://www.bjain.com/doctor/upload/"+list_patients.get(i).getP_photo();
            }
            Picasso.with(getApplicationContext())
                    .load(image_url)
                    .error(R.drawable.ic_action_person)
                    .placeholder(R.drawable.ic_action_person)
                    .into(iv_profile);
            try{
                List<NewUrgentChatResultPOJO> list_stored_chats=helper.getStoredUrgentChatByPID(list_patients.get(i).getU_chat_p_id());
                List<NewUrgentChatResultPOJO> list_server_chats=helper.getSERVERUrgentChatByPID(list_patients.get(i).getU_chat_p_id());
                Log.d(TAG,"serverchat:-"+list_server_chats.size());
                Log.d(TAG,"storedchat:-"+list_stored_chats.size());
                if(list_stored_chats.size()>0){
                    txt_last_seenid.setText(String.valueOf(list_server_chats.size()-list_stored_chats.size()));
                }
                else{
                    txt_last_seenid.setText(String.valueOf(list_server_chats.size()));
                }
                if(txt_last_seenid.getText().toString().equals("0")){
                    txt_last_seenid.setText(list_patients.get(i).getU_time1());
                }
            }
            catch (Exception e){
                Log.d(TAG,e.toString());
            }

            final int finalI = i;
            ll_patient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(UrgentChatPatientListActivity.this, UrgentChatActivity.class);
                    intent.putExtra("patient",list_patients.get(finalI));
                    intent.putExtra("position",finalI);
                    startActivityForResult(intent, 101);
                }
            });

            ll_scroll.addView(view);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                try{
                    int index=Integer.parseInt(result);
//                    list_text_views.get(index).setText(list_of_patients.get(index).getU_time1());
                    list_text_views.get(index).setVisibility(View.INVISIBLE);
                }
                catch (Exception e){
                    Log.d(TAG,e.toString());
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
