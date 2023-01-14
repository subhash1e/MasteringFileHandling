package com.subhash1e.masteringfilehandling;

import static android.util.Log.println;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "filexyz.txt";
    private static final String IMG_FILE_NAME ="imgFileXyz.png" ;
    Button mBtnSave = null;
    Button mBtnReadFile = null;
    Button mBtnSaveImage = null;
    EditText mEtFileText = null;
    TextView mStatus = null;
    TextView mReadFile = null;
    ImageView mIvSavedFile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        String path = getFilesDir().getAbsolutePath();
        mStatus.setText(path);
        this.mBtnSave.setOnClickListener(this::createFile);
        this.mBtnSaveImage.setOnClickListener(this::createImageFile);
        this.mBtnReadFile.setOnClickListener(this::readFile);
    }

    private void readFile(View view) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = openFileInput(FILE_NAME);
            int read;
            while((read=inputStream.read())!=-1){
                stringBuilder.append((char) read);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        mReadFile.setText(stringBuilder);
       Bitmap bitmap = null;
        try {
            inputStream = openFileInput(IMG_FILE_NAME);
            bitmap = BitmapFactory.decodeStream(inputStream);

        }catch (IOException e){
            e.printStackTrace();
        }
        mIvSavedFile.setImageBitmap(bitmap);

    }

    private void createImageFile(View view) {
        Bitmap data =getImage();
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(IMG_FILE_NAME,MODE_PRIVATE);
            data.compress(Bitmap.CompressFormat.PNG,50,fos);
            mStatus.setText("Image saved");
            fos.flush();
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }


    }

    private void createFile(View view) {
        String data = this.mEtFileText.getText().toString();
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME,MODE_APPEND);
            fos.write(data.getBytes(StandardCharsets.UTF_8));
            fos.flush();
            mStatus.setText("file saved");
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }
    private Bitmap getImage(){
        Bitmap image = null;
        try{
            InputStream fis = getAssets().open("frm.png");
            image = BitmapFactory.decodeStream(fis);

            fis.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
    private void initViews() {
        this.mBtnSave = findViewById(R.id.btnSaveFile);
        this.mBtnReadFile = findViewById(R.id.btnReadFile);
        this.mBtnSaveImage = findViewById(R.id.btnSaveImage);
        this.mEtFileText = findViewById(R.id.etFile);
        this.mStatus= findViewById(R.id.textView);
        this.mReadFile= findViewById(R.id.tvReadFile);
        this.mIvSavedFile= findViewById(R.id.ivReadFile);
    }
}