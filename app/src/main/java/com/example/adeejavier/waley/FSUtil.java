package com.example.adeejavier.waley;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ecce-219-pc09-user01 on 3/17/2016.
 */
public class FSUtil {
    public static boolean isStorageReady() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private static String getStorage() {
        return Environment.getExternalStorageDirectory().toString() + "/Waley/";
    }

    public static void write(byte[] data, String filename) {
        File fOutputDir = new File(getStorage());
        File fOutput = new File(getStorage(),  filename + ".json");
        FileOutputStream fileOutputStream = null;

        try {
            fOutputDir.mkdirs();
            fOutput.createNewFile();

            fileOutputStream = new FileOutputStream(fOutput, false);
            fileOutputStream.write(data);
            fileOutputStream.close();
        } catch (IOException e) {
            Log.e("ERROR", "Exception occurred: " + e.getMessage());
        }

        return;
    }

    public static FileInputStream getFileInputStream(String filename) {
        File fInput = new File(getStorage(), filename + ".json");
        FileInputStream fileInputStream = null;

        try {
            Log.i("INFO", "Accessing file: " + fInput.toString());
            fileInputStream = new FileInputStream(fInput);
        } catch (FileNotFoundException e) {
            Log.e("ERROR", "File not found: " + filename + ".json");
        } catch (Exception e) {
            Log.e("ERROR", "Exception occurred: " + e.getMessage());
        }

        return fileInputStream;
    }
}
