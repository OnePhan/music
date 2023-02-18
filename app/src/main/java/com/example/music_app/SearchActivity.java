package com.example.music_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SearchActivity extends AppCompatActivity {
    EditText edtBaiHat;
    TextView txtview;
    Button btnConfirm;
    ListView lstViewSearch;
    ArrayList<BaiHat> arraytimKiem;
    AdapterDs adapter;
    int size = MainActivity.arrayList.size();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        anhXa();
        adapter = new AdapterDs(this, R.layout.dongdanhsach, arraytimKiem);
        lstViewSearch.setAdapter(adapter);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = removeAccent(edtBaiHat.getText().toString().trim().toLowerCase());
                arraytimKiem.clear();
                for (int i = 0 ; i < size ; i ++) {
                    String chuoi = removeAccent(MainActivity.arrayList.get(i).getTenBaiHat().toLowerCase()).toString().trim() ;
                    if (search.replace(" ", "").equals(chuoi.replace(" ", ""))){
                            arraytimKiem.add(MainActivity.arrayList.get(i));
                            adapter.notifyDataSetChanged();
                    }else {
                        if (chuoi.contains(search)){
                            arraytimKiem.add(MainActivity.arrayList.get(i));
                            if (arraytimKiem.size() != 0) {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        });
        lstViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("key", arraytimKiem.get(position).getLinkBai());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void anhXa() {
        edtBaiHat = (EditText) findViewById(R.id.editTextBaiHat);
        btnConfirm = (Button) findViewById(R.id.buttonConfirm);
        lstViewSearch =(ListView) findViewById(R.id.listViewSearch);
        arraytimKiem = new ArrayList<>();
    }
    public String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");
        return temp.replaceAll("đ", "d");
    }
}
