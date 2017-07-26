package com.emobi.bjaindoc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import database.PreferenceData;

/**
 * Created by Emobi-Android-002 on 8/13/2016.
 */
public class DoctorDetail extends Activity {
    EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7;
    RadioGroup radioGroup;
    public static final String REGISTER_URL="http://www.bjain.com/doctor/registration.php";
    Button btn_Submit;
    String rate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateinfopatient);
        btn_Submit = (Button) findViewById(R.id.btn_submit);
        ed1=(EditText)findViewById(R.id.edtxt_pname);
        ed2=(EditText)findViewById(R.id.edtxt_pemail);
        ed3=(EditText)findViewById(R.id.edtxt_ppassword);
        ed4=(EditText)findViewById(R.id.edtxt_page);
        ed5=(EditText)findViewById(R.id.edtxt_pbgroup);
        ed6=(EditText)findViewById(R.id.edtxt_pwt);
        ed7=(EditText)findViewById(R.id.edtxt_pheight);
        radioGroup=(RadioGroup)findViewById(R.id.p_rg_Account);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos1 = radioGroup.indexOfChild(findViewById(checkedId));
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
                        rate = "Active";
                        break;
                    case 1:
                        rate = "Deactive";
                        break;

                    default:
                        break;
                }
            }
        });
//        mViewTherapist = (View) findViewById(R.id.layout_therapist);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new CallServices().execute(ApiConfig.ADD_PATIENT_URL);
                    Toast.makeText(getApplicationContext(),"Enter all information first",Toast.LENGTH_LONG).show();

            }
        });
    }
    public class CallServices extends AsyncTask<String, String, String> {
        final String name =ed1.getText().toString().trim();
        final String email =ed2.getText().toString().trim();
        final String password =ed3.getText().toString().trim();
        final String age =ed4.getText().toString().trim();
        final String b_group =ed5.getText().toString().trim();
        final String weight=ed6.getText().toString().trim();
        final String height=ed7.getText().toString().trim();
        final String activation=rate.toString().trim();
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(DoctorDetail.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("p_name", name));
            namevaluepair.add(new BasicNameValuePair("p_login_pass", password));
            namevaluepair.add(new BasicNameValuePair("p_login_id", email));
            namevaluepair.add(new BasicNameValuePair("p_age", age));
            namevaluepair.add(new BasicNameValuePair("p_weight", weight));
            namevaluepair.add(new BasicNameValuePair("p_bloodgroup", b_group));
            namevaluepair.add(new BasicNameValuePair("p_height", height));
            namevaluepair.add(new BasicNameValuePair("p_status", activation));


            try {

                result = Util.executeHttpPost(params[0], namevaluepair);

                Log.e("result",result.toString());
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
            }


            if (result != null) {
Toast.makeText(getApplicationContext(),"Successfully patient added",Toast.LENGTH_LONG).show();
                finish();
                try {

                /*JSONObject jobj = new JSONObject(result);

                    System.out.print("dhvsbnvsbncc" + jobj.toString());
                    // System.out.print("dhvsbnvsbncc" + jobj.length());

                    *//*for(int i=0;i<=jobj.length();i++) {

                        JSONObject detail=jobj.optJSONObject(i+"");


                    }
*//*

                    admin = new ArrayList<String>();

                    System.out.print("mansingh" + jobj.getString("username"));
                    String username = jobj.getString("username");

                    System.out.print("massds" + jobj.getString("messagecode"));
                    System.out.print("nmcczm" + jobj.getString("message"));

                    messagecode = jobj.getString("messagecode");
                    String message = jobj.getString("message");
                    // String t3=jobj.getString("message").toString();

                    admin.add(username);

                    admin.add(messagecode);

                    admin.add(message);


                    Log.e("valuesare", username);

                    Log.e("valuesare", messagecode);
                    Log.e("valuesare", message);
                    if (messagecode.equalsIgnoreCase("200")) {
                        Toast.makeText(getApplicationContext(), "Login Succsess fully", Toast.LENGTH_SHORT).show();
                    *//*if(admin.get(1).equals("200"))

                    {
*//*
                        user=username;

                        Log.e("valuesare", messagecode);
                        Log.e("valuesare", message);


                    } else {

                        Log.e("valuesare", username);

                        Log.e("valuesare", message);
                        Toast.makeText(LoginActivity.this, "Invalid Password And EmailId", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"please enter IFSC Number",Toast.LENGTH_LONG).show();

                    }









               *//*admin.add(username);

               admin.add(messagecode);

               admin.add(message);


               System.out.print("massds" + admin.toString());


               System.out.print(jobj.getString("messagecode"));
*//*

                    //  System.out.println("mmmmmm" + jobj.length());

                    // JSONObject jsonObject=new JSONObject(result);

                    // jsonObject=jsonObject.getJSONObject("detail");
                    //  JSONObject songs= jsonObject.getJSONObject("personal_detail");
                  *//*  Iterator x = songs.keys();
*//*
                   *//* System.out.println("strrrrrrrrrrrrrr"+x.toString());
*//*

                    // JSONArray jsonArray = new JSONArray();

*//*
                    for (int i = 1; i <= songs.length(); i++) {

                        JSONObject jsonObject2 = songs.getJSONObject(i + "");
                        //Log.e("2", jsonObject2.toString());
                         String Browsername = jsonObject2.getString("borrower_name");

                         fnal.add(Browsername);
                         String amount = jsonObject2.getString("recovery_amount");

                         fnal.add(Browsername);
                         String bankname=jsonObject2.getString("bank_name");
                         fnal.add(Browsername);
                        String bank_region=jsonObject2.getString("bank_region");

                        fnal.add(Browsername);
                        String bank_circle=jsonObject2.getString("bank_circle");
                        fnal.add(Browsername);
                        String branch_name=jsonObject2.getString("branch_name");
                        fnal.add(Browsername);
                        String scheme_code=jsonObject2.getString("scheme_code");
                        fnal.add(Browsername);
                        String status=jsonObject2.getString("status");

                        fnal.add(Browsername);
                    }
                       *//*


*//*

                    while (x.hasNext()){
                        String key = (String) x.next();
                        jsonArray.put(songs.get(key));


                        String j=(String) songs.get(key);
                        //System.out.println(j);
                        hading.add(key);
                        valu.add(j);

                        //System.out.println("gcjj"+ key);

                    }
*//*




                   *//* String[] array = valu.toArray(new String[valu.size()]);

                    String[] array1 = hading.toArray(new String[hading.size()]);

                    for (String o : array) {

                        String jk=o;


                        System.out.println(o);


                    }

                    for (String o :valu) {




                        System.out.println(o);


                    }


                    System.out.println(array.length);




                    for (int i = 0; i <jobj.length() ; i++) {
                        ArrayList<String> hList = new ArrayList<String>();
                        for (int j = 0; j <array.length ; j++) {
                            hList.add( array[j]);
                        }
                        hading1.addAll(hList);



                    }
                   *//* //hading1.addAll(li);
                   *//* System.out.println(hading1.toString());

                    System.out.println(hading1.get(1).toString());

                    System.out.println("mansingh"+hading1.get(0).toString());
*//*

                      *//*String[] array = valu.toArray(new String[valu.size()]);

                  String[] array1 = hading.toArray(new String[hading.size()]);

                    for (String o : array) {

               String jk=o;


                       System.out.println(o);


                    }

                    for (String o :valu) {




                       System.out.println(o);


                        }

*//*
                    //TextView l1=(TextView)findViewById(R.id.textView2)  ;


                    //description = json.getString("branch_name");



                     *//*if (description.equalsIgnoreCase("Description is not found.")) {
                        description = "";
                     }
                     *//*//price = json.getString("price");

                      *//*T1.setText(json.getString("id"));
                     T2.setText(json.getString("cat_id"));
                     T3.setText(json.getString("cat_name"));
                     T4.setText(json.getString("sub_sub_cat_id"));
                     T5.setText(json.getString("name"));
                     T6.setText(json.getString("description"));
                     T7.setText(json.getString("price"));*//*




*//*


                    TextView t1=(TextView)findViewById(R.id.textView1);







                    Dbrowser gh= new Dbrowser(getApplicationContext(), hading1);
                    list1.setAdapter(gh);
*/

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }


    }
}
