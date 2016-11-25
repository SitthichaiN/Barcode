package com.sitthichain.nudet.barcode;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        //Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        //TextView textView = new TextView(this);
        //textView.setTextSize(40);
        //textView.setText(message);
        //ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        //layout.addView(textView);

        Intent intent = getIntent();
        String documentString = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Log.d("Log_V1 > ", "DocumentID : " + documentString);
        // Start call web service...
        GetVehicleDocument getVehicleDocument = new GetVehicleDocument(DisplayMessageActivity.this);
        getVehicleDocument.execute(documentString);
        // End call web service.
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.man);
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(imageView);
    }   // OnCreate

    private class GetVehicleDocument extends AsyncTask<String, Void, String> {
        private final static String SERVICE_URI = "http://58.137.117.77:8022/ServicePerson.svc/data/";
        private String returnString = "";
        private Context context;

        public GetVehicleDocument(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {

            Log.d("Log_V1 > ", "Params : " + params[0]);

            OkHttpClient httpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder().build(); //documentTypeId
            Request request = new Request.Builder()
                    .url(SERVICE_URI + params[0])
                    .build();
            Log.d("Log_V1 > ", "requestBody : " + requestBody);
            Log.d("Log_V1 > ", "Uri : " + request.url().toString());
            Log.d("Log_V1 > ", "checking");
            try {
                Response response = httpClient.newCall(request).execute();
                returnString = response.body().string();
                // Do something with the response.
                Log.d("Log_V1 > ","Response : " + returnString);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


}
