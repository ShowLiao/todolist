package com.example.show.todolistv2.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.show.todolistv2.R;

public class MainDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);

        Intent intent = getIntent();

        TextView txtTask = (TextView) findViewById(R.id.txtTask);
        TextView txtDetail = (TextView) findViewById(R.id.txtDetail);
        TextView txtLocation = (TextView) findViewById(R.id.txtLocation);
        TextView txtDudate = (TextView) findViewById(R.id.txtTime);
//        Log.e("date:", String.valueOf(calendar.get(Calendar.DATE)) + "/" + String.valueOf(month) + "/" + String.valueOf(dayOfMonth));
        Log.e("MainDetial Activity", getString(R.string.task));
        txtTask.setText(intent.getStringExtra(getString(R.string.task)));
        txtDudate.setText(intent.getStringExtra(getString(R.string.due_date)));
        txtLocation.setText(intent.getStringExtra(getString(R.string.location)));
        txtDetail.setText(intent.getStringExtra(getString(R.string.detail)));
        txtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenMap(v);
            }
        });

        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    public void onOpenMap(View view) {
        TextView txtLocation = (TextView) view.findViewById(R.id.txtLocation);
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + txtLocation.getText().toString());

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}
