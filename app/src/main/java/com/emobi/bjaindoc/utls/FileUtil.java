package com.emobi.bjaindoc.utls;

import android.os.Environment;

import java.io.File;

/**
 * Created by sunil on 29-03-2017.
 */

public class FileUtil {

    public static final String BASE_FILE= Environment.getExternalStorageDirectory()+ File.separator+"bjain";
    public static final String getChatDir(){
        File f=new File(BASE_FILE+File.separator+"chat");
        if(!f.exists()){
            f.mkdirs();
        }
        return f.toString();
    }
    public static final String getvideoChatDir(){
        File f=new File(BASE_FILE+File.separator+"video");
        if(!f.exists()){
            f.mkdirs();
        }
        return f.toString();
    }
    public static final String getAudioChatDir(){
        File f=new File(BASE_FILE+File.separator+"audio");
        if(!f.exists()){
            f.mkdirs();
        }
        return f.toString();
    }

}
