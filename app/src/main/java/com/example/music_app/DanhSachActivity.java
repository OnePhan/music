package com.example.music_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DanhSachActivity extends AppCompatActivity {
    ListView lstDs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach);
        lstDs = (ListView) findViewById(R.id.listViewDs);

        AdapterDs adapterDs = new AdapterDs(
                this,
                R.layout.dongdanhsach,
                MainActivity.arrayList
        );

        lstDs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("l", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        lstDs.setAdapter(adapterDs);
    }
}