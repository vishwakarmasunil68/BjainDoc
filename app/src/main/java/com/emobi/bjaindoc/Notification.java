package com.emobi.bjaindoc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Emobi-Android-002 on 8/20/2016.
 */
public class Notification extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new CallServices().execute(ApiConfig.SEND_MESSAGE);
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
            pd = new ProgressDialog(Notification.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("msg", "HEllO SHUBHAm"));
            namevaluepair.add(new BasicNameValuePair("device_token",""));
            //namevaluepair.add(new BasicNameValuePair("cat", "HAIR"));

            try {

                result = Util.executeHttpPost(params[0], namevaluepair);

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

                try {
                    JSONObject objresponse = new JSONObject(result);

                    Log.e("result",result.toString());

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
