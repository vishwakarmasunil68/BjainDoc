package com.emobi.bjaindoc.utls;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.emobi.bjaindoc.pojo.urgent.UrgentChatResultPOJO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 21-03-2017.
 */

public class BjainDocDBHelper {
    DataBaseHelper helper;
    public BjainDocDBHelper(Context context){
        helper=new DataBaseHelper(context);
    }

    private static final String u_chat_id="u_chat_id";
    private static final String u_chat_p_id = "u_chat_p_id";
    private static final String u_chat_doc_id = "u_chat_doc_id";
    private static final String u_date="u_date";
    private static final String u_time= "u_time";
    private static final String u_chat_msg= "u_chat_msg";
    private static final String u_chat_title="u_chat_title";
    private static final String u_chat_file= "u_chat_file";
    private static final String u_direction = "u_direction";

    public long insertStoredUrgentChat(UrgentChatResultPOJO dep){
//        //Log.d("sunil","insert:-"+dep.toString());
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(DataBaseHelper.u_chat_id, dep.getU_chat_id()+"");
        contentValues.put(DataBaseHelper.u_chat_p_id, dep.getU_chat_p_id()+"");
        contentValues.put(DataBaseHelper.u_chat_doc_id, dep.getU_chat_doc_id()+"");
        contentValues.put(DataBaseHelper.u_date, dep.getU_date()+"");
        contentValues.put(DataBaseHelper.u_time, dep.getU_time()+"");
        contentValues.put(DataBaseHelper.u_chat_msg, dep.getU_chat_msg()+"");
        contentValues.put(DataBaseHelper.u_chat_title, dep.getU_chat_title()+"");
        contentValues.put(DataBaseHelper.u_chat_file, dep.getU_chat_file()+"");
        contentValues.put(DataBaseHelper.u_direction, dep.getU_direction()+"");


        long id=db.insert(DataBaseHelper.STORED_URGENT_CHAT_TABLE, null, contentValues);
        return id;
    }

    public List<UrgentChatResultPOJO> getAllStoredUrgentChat(){
        SQLiteDatabase db=helper.getWritableDatabase();
        List<UrgentChatResultPOJO> lst=new ArrayList<>();
        String[] columns={DataBaseHelper.ID,
                DataBaseHelper.u_chat_id,
                DataBaseHelper.u_chat_p_id,
                DataBaseHelper.u_chat_doc_id,
                DataBaseHelper.u_date,
                DataBaseHelper.u_time,
                DataBaseHelper.u_chat_msg,
                DataBaseHelper.u_chat_title,
                DataBaseHelper.u_chat_file,
                DataBaseHelper.u_direction
        };
        Cursor cursor=db.query(DataBaseHelper.STORED_URGENT_CHAT_TABLE, columns, null, null, null, null, null);

        while(cursor.moveToNext()){
            String u_chat_id=cursor.getString(1);
            String u_chat_p_id=cursor.getString(2);
            String u_chat_doc_id=cursor.getString(3);
            String u_date=cursor.getString(4);
            String u_time=cursor.getString(5);
            String u_chat_msg=cursor.getString(6);
            String u_chat_title=cursor.getString(7);
            String u_chat_file=cursor.getString(8);
            String u_direction=cursor.getString(9);

            UrgentChatResultPOJO obj=new UrgentChatResultPOJO(u_chat_id,u_chat_p_id,u_chat_doc_id
                    ,u_date,u_time,u_chat_msg,u_chat_title,u_chat_file,u_direction);



            lst.add(obj);
        }
        return lst;

    }
    public List<UrgentChatResultPOJO> getStoredUrgentChatByPID(String p_id){
        SQLiteDatabase db=helper.getWritableDatabase();
        List<UrgentChatResultPOJO> lst=new ArrayList<>();
        String[] columns={DataBaseHelper.ID,
                DataBaseHelper.u_chat_id,
                DataBaseHelper.u_chat_p_id,
                DataBaseHelper.u_chat_doc_id,
                DataBaseHelper.u_date,
                DataBaseHelper.u_time,
                DataBaseHelper.u_chat_msg,
                DataBaseHelper.u_chat_title,
                DataBaseHelper.u_chat_file,
                DataBaseHelper.u_direction
        };
//        Cursor cursor=db.query(DataBaseHelper.OBSTACLE_TABLE, columns, null, null, null, null, null);
        Cursor cursor=db.query(DataBaseHelper.STORED_URGENT_CHAT_TABLE, columns, DataBaseHelper.u_chat_p_id+" = "+p_id, null, null, null, null);
        while(cursor.moveToNext()){
            String u_chat_id=cursor.getString(1);
            String u_chat_p_id=cursor.getString(2);
            String u_chat_doc_id=cursor.getString(3);
            String u_date=cursor.getString(4);
            String u_time=cursor.getString(5);
            String u_chat_msg=cursor.getString(6);
            String u_chat_title=cursor.getString(7);
            String u_chat_file=cursor.getString(8);
            String u_direction=cursor.getString(9);

            UrgentChatResultPOJO obj=new UrgentChatResultPOJO(u_chat_id,u_chat_p_id,u_chat_doc_id
                    ,u_date,u_time,u_chat_msg,u_chat_title,u_chat_file,u_direction);
            lst.add(obj);
        }
        return lst;
    }

//    public int UpdateStoreImagePath(String store_id,String store_image_path){
//        //UPDATE TABLE SET Name='vav' where Name=?
//        SQLiteDatabase db=helper.getWritableDatabase();
//        ContentValues contentValues=new ContentValues();
//        contentValues.put(DataBaseHelper.STORE_IMAGE_PATH,store_image_path);
//        String[] whereArgs={store_id};
//        int count=db.update(DataBaseHelper.STORE_TABLE,contentValues,DataBaseHelper.STORE_ID+" =? ",whereArgs);
//        return count;
//    }

    public int deleteStoreChatByPatientID(String p_id){
        SQLiteDatabase db=helper.getWritableDatabase();
        String[] whereArgs={p_id};
        int count=db.delete(DataBaseHelper.STORED_URGENT_CHAT_TABLE, DataBaseHelper.u_chat_p_id+"=?", whereArgs);
        return count;
    }
    public void deleteAllStoreChat(){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.execSQL("delete from "+ DataBaseHelper.STORED_URGENT_CHAT_TABLE);
    }


    /*------------------------------------------------------------------------------------
    --------------------------------------------------------------------------------------
    --------------------------------------------------------------------------------------
     */


    public long insertServerUrgentChat(UrgentChatResultPOJO dep){
//        //Log.d("sunil","insert:-"+dep.toString());
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(DataBaseHelper.u_chat_id, dep.getU_chat_id()+"");
        contentValues.put(DataBaseHelper.u_chat_p_id, dep.getU_chat_p_id()+"");
        contentValues.put(DataBaseHelper.u_chat_doc_id, dep.getU_chat_doc_id()+"");
        contentValues.put(DataBaseHelper.u_date, dep.getU_date()+"");
        contentValues.put(DataBaseHelper.u_time, dep.getU_time()+"");
        contentValues.put(DataBaseHelper.u_chat_msg, dep.getU_chat_msg()+"");
        contentValues.put(DataBaseHelper.u_chat_title, dep.getU_chat_title()+"");
        contentValues.put(DataBaseHelper.u_chat_file, dep.getU_chat_file()+"");
        contentValues.put(DataBaseHelper.u_direction, dep.getU_direction()+"");


        long id=db.insert(DataBaseHelper.SERVER_URGENT_CHAT_TABLE, null, contentValues);
        return id;
    }

    public List<UrgentChatResultPOJO> getAllSERVERUrgentChat(){
        SQLiteDatabase db=helper.getWritableDatabase();
        List<UrgentChatResultPOJO> lst=new ArrayList<>();
        String[] columns={DataBaseHelper.ID,
                DataBaseHelper.u_chat_id,
                DataBaseHelper.u_chat_p_id,
                DataBaseHelper.u_chat_doc_id,
                DataBaseHelper.u_date,
                DataBaseHelper.u_time,
                DataBaseHelper.u_chat_msg,
                DataBaseHelper.u_chat_title,
                DataBaseHelper.u_chat_file,
                DataBaseHelper.u_direction
        };
        Cursor cursor=db.query(DataBaseHelper.SERVER_URGENT_CHAT_TABLE, columns, null, null, null, null, null);

        while(cursor.moveToNext()){
            String u_chat_id=cursor.getString(1);
            String u_chat_p_id=cursor.getString(2);
            String u_chat_doc_id=cursor.getString(3);
            String u_date=cursor.getString(4);
            String u_time=cursor.getString(5);
            String u_chat_msg=cursor.getString(6);
            String u_chat_title=cursor.getString(7);
            String u_chat_file=cursor.getString(8);
            String u_direction=cursor.getString(9);

            UrgentChatResultPOJO obj=new UrgentChatResultPOJO(u_chat_id,u_chat_p_id,u_chat_doc_id
                    ,u_date,u_time,u_chat_msg,u_chat_title,u_chat_file,u_direction);



            lst.add(obj);
        }
        return lst;

    }
    public List<UrgentChatResultPOJO> getSERVERUrgentChatByPID(String p_id){
        SQLiteDatabase db=helper.getWritableDatabase();
        List<UrgentChatResultPOJO> lst=new ArrayList<>();
        String[] columns={DataBaseHelper.ID,
                DataBaseHelper.u_chat_id,
                DataBaseHelper.u_chat_p_id,
                DataBaseHelper.u_chat_doc_id,
                DataBaseHelper.u_date,
                DataBaseHelper.u_time,
                DataBaseHelper.u_chat_msg,
                DataBaseHelper.u_chat_title,
                DataBaseHelper.u_chat_file,
                DataBaseHelper.u_direction
        };
//        Cursor cursor=db.query(DataBaseHelper.OBSTACLE_TABLE, columns, null, null, null, null, null);
        Cursor cursor=db.query(DataBaseHelper.SERVER_URGENT_CHAT_TABLE, columns, DataBaseHelper.u_chat_p_id+" = "+p_id, null, null, null, null);
        while(cursor.moveToNext()){
            String u_chat_id=cursor.getString(1);
            String u_chat_p_id=cursor.getString(2);
            String u_chat_doc_id=cursor.getString(3);
            String u_date=cursor.getString(4);
            String u_time=cursor.getString(5);
            String u_chat_msg=cursor.getString(6);
            String u_chat_title=cursor.getString(7);
            String u_chat_file=cursor.getString(8);
            String u_direction=cursor.getString(9);

            UrgentChatResultPOJO obj=new UrgentChatResultPOJO(u_chat_id,u_chat_p_id,u_chat_doc_id
                    ,u_date,u_time,u_chat_msg,u_chat_title,u_chat_file,u_direction);
            lst.add(obj);
        }
        return lst;
    }

//    public int UpdateStoreImagePath(String store_id,String store_image_path){
//        //UPDATE TABLE SET Name='vav' where Name=?
//        SQLiteDatabase db=helper.getWritableDatabase();
//        ContentValues contentValues=new ContentValues();
//        contentValues.put(DataBaseHelper.STORE_IMAGE_PATH,store_image_path);
//        String[] whereArgs={store_id};
//        int count=db.update(DataBaseHelper.STORE_TABLE,contentValues,DataBaseHelper.STORE_ID+" =? ",whereArgs);
//        return count;
//    }

    public int deleteServerChatByPatientID(String p_id){
        SQLiteDatabase db=helper.getWritableDatabase();
        String[] whereArgs={p_id};
        int count=db.delete(DataBaseHelper.SERVER_URGENT_CHAT_TABLE, DataBaseHelper.u_chat_p_id+"=?", whereArgs);
        return count;
    }
    public void deleteAllServerChat(){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.execSQL("delete from "+ DataBaseHelper.SERVER_URGENT_CHAT_TABLE);
    }





    static class DataBaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME="bjaindoc";

        //table names
        private static final String STORED_URGENT_CHAT_TABLE="stored_urgent_chat_table";
        private static final String SERVER_URGENT_CHAT_TABLE="server_urgent_chat_table";


        private static final int DATABASE_VERSION=2;

        //columns for the ItemData
        private static final String ID="_id";
        private static final String u_chat_id="u_chat_id";
        private static final String u_chat_p_id = "u_chat_p_id";
        private static final String u_chat_doc_id = "u_chat_doc_id";
        private static final String u_date="u_date";
        private static final String u_time= "u_time";
        private static final String u_chat_msg= "u_chat_msg";
        private static final String u_chat_title="u_chat_title";
        private static final String u_chat_file= "u_chat_file";
        private static final String u_direction = "u_direction";






        private static final String CREATE_STORE_TABLE="CREATE TABLE "+STORED_URGENT_CHAT_TABLE+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +u_chat_id+" VARCHAR(255), "
                +u_chat_p_id +" VARCHAR(255), "
                +u_chat_doc_id+" varchar(255), "
                +u_date +" VARCHAR(255), "
                +u_time+" VARCHAR(255), "
                +u_chat_msg+" VARCHAR(255), "
                +u_chat_title+" VARCHAR(255), "
                +u_chat_file+" VARCHAR(255),  "
                + u_direction +" VARCHAR(255) "
                +");";

        private static final String CREATE_SERVER_URGENT_CHAT_TABLE="CREATE TABLE "+SERVER_URGENT_CHAT_TABLE+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +u_chat_id+" VARCHAR(255), "
                +u_chat_p_id +" VARCHAR(255), "
                +u_chat_doc_id+" varchar(255), "
                +u_date +" VARCHAR(255), "
                +u_time+" VARCHAR(255), "
                +u_chat_msg+" VARCHAR(255), "
                +u_chat_title+" VARCHAR(255), "
                +u_chat_file+" VARCHAR(255),  "
                + u_direction +" VARCHAR(255) "
                +");";



        private static final String DROP_URGENT_CHAT_TABLE="DROP TABLE IF EXISTS "+STORED_URGENT_CHAT_TABLE;
        private static final String DROP_SERVER_URGENT_CHAT_TABLE="DROP TABLE IF EXISTS "+CREATE_SERVER_URGENT_CHAT_TABLE;


        private Context context;



        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            try{

                db.execSQL(CREATE_STORE_TABLE);
                db.execSQL(CREATE_SERVER_URGENT_CHAT_TABLE);

//                Toast.makeText(context, "database called", Toast.LENGTH_SHORT).show();
                Log.d("sunil","database called");
            }
            catch(Exception e){
//                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("sunil",e.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            try{
                db.execSQL(DROP_URGENT_CHAT_TABLE);
                db.execSQL(DROP_SERVER_URGENT_CHAT_TABLE);

//                Toast.makeText(context, "database droped", Toast.LENGTH_SHORT).show();
                Log.d("sunil","database droped");
                onCreate(db);
            }
            catch(Exception e){
//                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("sunil",e.toString());
            }
        }

    }

}
