package com.sitthichain.nudet.barcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DisplayMessageActivity extends AppCompatActivity {
    private Bitmap Image;

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

        //ImageView imageView = new ImageView(this);
        //imageView.setImageBitmap(returnBitmap);
        //imageView.setImageResource(R.drawable.man);
        //ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        //layout.addView(imageView);
    }   // OnCreate

    private class GetVehicleDocument extends AsyncTask<String, Void, Bitmap> {
        private final static String SERVICE_URI = "http://58.137.117.77:8022/ServiceDocuments.svc/GetDocumentById/000000000001163270/4";
        private String returnString = "";
        private Context context;

        public GetVehicleDocument(Context context) {
            this.context = context;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap retBitmap = null;
            Log.d("Log_V1 > ", "Params : " + params[0]);

            OkHttpClient httpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder().build(); //documentTypeId
            Request request = new Request.Builder()
                    .url(SERVICE_URI)
                    .build();
            Log.d("Log_V1 > ", "requestBody : " + requestBody);
            Log.d("Log_V1 > ", "Uri : " + request.url().toString());
            Log.d("Log_V1 > ", "checking");
            try {
                Response response = httpClient.newCall(request).execute();
                //returnString = response.body().string();

                String jsonString = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("GetDocumentByIdResult");
                JSONObject jsonObjectReturn = jsonArray.getJSONObject(0);

                String docu = jsonObjectReturn.getString("DocumentFile");
                int docLenght = Integer.parseInt(jsonObjectReturn.getString("Lenght")) ;

                byte[] bytes = Base64.decode(docu, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, docLenght);
                retBitmap = bitmap;

                // Do something with the response.
                Log.d("Log_V1 > ","Response : " + docLenght);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Log_V1 > ","Error : " + e.toString());
            }

            return retBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            //super.onPostExecute(result);


            ImageView imageView = (ImageView) findViewById(R.id.imageView3);
            imageView.setImageBitmap(result);
            //imageView.setImageResource(R.drawable.man);
            //ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
            //layout.addView(imageView);
        }
    }


}
