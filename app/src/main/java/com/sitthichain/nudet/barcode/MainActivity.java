package com.sitthichain.nudet.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.sitthichain.nudet.barcode.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendDocument(View view) {
        //Intent intent = new Intent(this, DisplayMessageActivity.class);
        //Toast.makeText(DisplayMessageActivity.class, "Hello", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextBarcode);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent);

    }
}
