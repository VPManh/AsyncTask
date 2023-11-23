package com.example.a_asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private Button btLoadHinh;
    private ImageView imgHinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btLoadHinh = findViewById(R.id.Bt_LoadHinh);
        imgHinh = findViewById(R.id.im_hinh);
        new ReadDataToInternet().execute("https://www.facebook.com/");

        btLoadHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadImgToInternet().execute("https://scontent.fsgn2-7.fna.fbcdn.net/v/t39.30808-6/375668840_1715839848889649_4419814513302659628_n.jpg?_nc_cat=106&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHi4urTyhV2mCz3VdHLLn9qiE9SHfN3HP-IT1Id83cc_3eCn_qoAAf_usBoqZcYqfmTijfF1xM3sHCxju397wF-&_nc_ohc=69qtKeUMJHUAX-eDtOR&_nc_ht=scontent.fsgn2-7.fna&oh=00_AfDCghGgyzkvvsYVLnTgRsQmS7nL4A4UgeY-vZ168PK4cQ&oe=6561FC60  ");
                //khi sử dụng đường dẫn thì nhớ xin quyền Internet bằng
                //< uses-permission android:name="android.permission.INTERNET"/> trong AndroidManifest
            }
        });

    }

    //lấy url từ internet
    private class LoadImgToInternet extends AsyncTask<String, Void, Bitmap>{
        Bitmap bitmapHinh;
        //hàm thực hiện
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                // lấy dữ liệu từ đường dân
                InputStream inputStream = url.openConnection().getInputStream();
                //  chuyển dữ liệu về kiểu bitmap
                bitmapHinh   = BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return bitmapHinh;
        }
        //hàm xuất ra(nhận được)
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgHinh.setImageBitmap(bitmap);
        }
    }

    private class ReadDataToInternet extends AsyncTask<String, Void  ,String>{


        @Override
        protected String doInBackground(String... strings) {

            //chứa dữ liệu đọc được (đổ dữ liệu vào)
            StringBuffer stringBuffer = new StringBuffer();

            try {
//                URL url = new URL(strings[0]);
//
//                URLConnection urlConnection = url.openConnection();
//
//                InputStream inputStream = urlConnection.getInputStream();
//
//                //đọc dữ vào
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//
//                //đoc dữ liệu liên tục
//                BufferedReader bufferedReader =new BufferedReader(inputStreamReader);


                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line="";
                //đọc dữ liệu
                while ((line = bufferedReader.readLine()) != null){
                    stringBuffer.append(line + "\n");
                }

                bufferedReader.close();



            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return stringBuffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
        }
    }


}