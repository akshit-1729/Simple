package com.example.simple;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private static final String file_name = "ex.txt";
    private static final int WRITE_EXTERNAL_CODE = 1;
    EditText mEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.a1);
    }

    public void save(View v) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,WRITE_EXTERNAL_CODE);
            }
            else{
                String text = mEditText.getText().toString();
                FileOutputStream fs = null;
                try {

                    fs = openFileOutput(file_name, MODE_PRIVATE);
                    File folder = new File(getApplicationContext().getExternalFilesDir("Directory Name").getAbsolutePath());
                    boolean success = true;
                    if (!folder.exists())
                    {
                        folder.mkdirs();
                        Toast.makeText(getApplicationContext(),"Directory Created",Toast.LENGTH_LONG).show();
                    }
                    String path = getApplicationContext().getExternalFilesDir("Directory Name").getAbsolutePath();
                    if(folder.exists()) {
                        // Do something on success
                        fs.write(text.getBytes());
                        mEditText.getText().clear();
                        Toast.makeText(this, "Saved to " + path + "/" + file_name, Toast.LENGTH_LONG).show();

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(fs != null){
                        try {
                            fs.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        else{
            String text = mEditText.getText().toString();
            FileOutputStream fs = null;
            System.out.println("000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");

            try {
                fs = openFileOutput(file_name, MODE_PRIVATE);
                fs.write(text.getBytes());

                mEditText.getText().clear();
                Toast.makeText(this, "Saved to " + getFilesDir() + "/" + file_name, Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(fs != null){
                    try {
                        fs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void load(View v){
        FileInputStream fis = null;

        try {
            fis = openFileInput(file_name);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb  = new StringBuilder();
            String text;

            while((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            mEditText.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}