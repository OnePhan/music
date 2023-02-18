package com.example.music_app;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView imgMenu, imgSearch, imgAnh, imgNext, imgPrevious, imgPlay;
    SeekBar skbTime;
    TextView txtStart, txtEnd, txtTenBai, txtTacGia;
    CheckBox ckbShuffle, ckbRepeat;
    public static ArrayList<BaiHat> arrayList;
    MediaPlayer mediaPlayer;
    Handler handler;
    int vitriBaihat, positon = 0, REQUEST_CODE = 123, REQUEST_CODE_SEARCH = 124;
    ArrayList<String> checkBai_daPhat;
    SharedPreferences luuDuLieu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        addMusic();
        vitriBaihat = arrayList.size();
        ganDuLieu();
        khoiTaoMedia(positon);
        chuongTrinh();
    }
    public void chuongTrinh(){
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.play);
                }else {
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.pause);
                }
                if (mediaPlayer.getCurrentPosition() >= mediaPlayer.getDuration())
                {
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                    imgPlay.setImageResource(R.drawable.pause);
                    khoiTaoMedia(positon);
                    mediaPlayer.start();
                }
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBai();
            }
        });
        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreviousBai();
            }
        });
        skbTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skbTime.getProgress());
            }
        });
        ckbShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luuCheck();
            }
        });
        ckbRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luuCheck();
            }
        });
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, DanhSachActivity.class),REQUEST_CODE);
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivityForResult(new Intent(MainActivity.this, SearchActivity.class), REQUEST_CODE_SEARCH);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data !=null){
            positon = data.getIntExtra("l",R.raw.lunglo);
            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            khoiTaoMedia(positon);
            mediaPlayer.start();
            imgPlay.setImageResource(R.drawable.pause);
        }

        if (requestCode == REQUEST_CODE_SEARCH && resultCode == RESULT_OK && data !=null){
              int link = data.getIntExtra("key", R.raw.lunglo);
              for (int i = 0 ; i < arrayList.size(); i++){
                  if(link == arrayList.get(i).getLinkBai()){
                      positon = i;
                  }
              }

            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            khoiTaoMedia(positon);
            mediaPlayer.start();
            imgPlay.setImageResource(R.drawable.pause);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void autoChuyenBai(){
            if (mediaPlayer.getCurrentPosition() >= mediaPlayer.getDuration()){
                positon += 1;
                if (ckbRepeat.isChecked()){
                    if (positon > arrayList.size() - 1){
                        positon = 0;
                    }
                }
                if (ckbShuffle.isChecked()){
                    positon = autoChuyenBaiNgauNhien();
                }
                if (positon <= arrayList.size() - 1){
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                    khoiTaoMedia(positon);
                    vitriBaihat = positon;
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.pause);

                }else {
                    imgPlay.setImageResource(R.drawable.play);
                    positon = vitriBaihat;
                    if(vitriBaihat != arrayList.size()){
                        positon = vitriBaihat;
                    }else {
                        positon = arrayList.size() - 1;
                    }
                    mediaPlayer.pause();
                }
            }
    }
    public Boolean kiemTra_BaiHatDaPhat(int a){
            String b = String.valueOf(a);
            for (int i = 0; i < checkBai_daPhat.size(); i++) {
                if ( b == checkBai_daPhat.get(i)) {
                    return false;
                }
            }
       return true;
    }
    public int autoChuyenBaiNgauNhien(){
            Random rd = new Random();
            int bienDem = 0;
            int a = rd.nextInt(arrayList.size());

            if (checkBai_daPhat.size() == arrayList.size()){
                checkBai_daPhat.clear();
            }

            while (kiemTra_BaiHatDaPhat(a) == false) {
                a = rd.nextInt(arrayList.size());
                bienDem++;
                if (bienDem == arrayList.size()){
                    a = arrayList.size();
                }
            }
            checkBai_daPhat.add(String.valueOf(a));
           return a;
    }
    public void ganDuLieu(){
        luuDuLieu = getSharedPreferences("Check", MODE_PRIVATE);
        ckbShuffle.setChecked(luuDuLieu.getBoolean("shuffle", false));
        ckbRepeat.setChecked(luuDuLieu.getBoolean("lap", false));
        positon = luuDuLieu.getInt("baidanghat", 0);
    }
    public void luuCheck(){
        SharedPreferences.Editor editor = luuDuLieu.edit();
        if (ckbShuffle.isChecked()){
            editor.putBoolean("shuffle", true);
        }else{
            editor.putBoolean("shuffle", false);
        }

        if (ckbRepeat.isChecked()){
            editor.putBoolean("lap", true);
        }else{
            editor.putBoolean("lap", false);
        }
        editor.commit();
    }
    public void luuDuLieu(int a){
        SharedPreferences.Editor editor = luuDuLieu.edit();
        editor.putInt("baidangphat", a);
    }
    public void nextBai(){
        positon += 1;
        if (positon > arrayList.size() - 1){
            positon = arrayList.size() - 1;
        }
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        khoiTaoMedia(positon);
        mediaPlayer.start();
        imgPlay.setImageResource(R.drawable.pause);
    }
    public void PreviousBai(){
        positon -= 1;
        if (positon < 0){
            positon = 0;
        }

        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        khoiTaoMedia(positon);
        mediaPlayer.start();
    }
    public void updateTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                skbTime.setProgress(mediaPlayer.getCurrentPosition());
                txtStart.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                if (mediaPlayer.getCurrentPosition() <= mediaPlayer.getDuration()){
                    handler.postDelayed(this, 200);
                }
                autoChuyenBai();
            }
        },100);
    }
    public void timeTotal(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtEnd.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        skbTime.setMax(mediaPlayer.getDuration());
    }
    public void khoiTaoMedia(int a){
        mediaPlayer = MediaPlayer.create(this, arrayList.get(a).getLinkBai());
        timeTotal();
        txtTenBai.setText(arrayList.get(a).getTenBaiHat());
        txtTacGia.setText(arrayList.get(a).getTenTacGia());
        imgAnh.setImageResource(arrayList.get(a).getAnh());
        updateTime();
        luuDuLieu(a);
    }
    public void anhXa(){
        imgMenu = (ImageView) findViewById(R.id.imageViewMenu);
        imgSearch = (ImageView) findViewById(R.id.imageViewSearch);
        imgAnh = (ImageView) findViewById(R.id.imageViewHinhBaiHat);
        txtStart = (TextView) findViewById(R.id.textViewStartTime);
        txtEnd = (TextView) findViewById(R.id.textViewEndTime);
        txtTenBai = (TextView) findViewById(R.id.textViewTenBaiHat);
        txtTacGia = (TextView) findViewById(R.id.textViewTacGia);
        skbTime = (SeekBar) findViewById(R.id.seekBarTime);
        imgNext = (ImageView) findViewById(R.id.ImageViewNext);
        imgPlay = (ImageView) findViewById(R.id.ImageViewPlayOrPause);
        imgPrevious = (ImageView) findViewById(R.id.imageViewPrevious);
        ckbShuffle = (CheckBox) findViewById(R.id.checkBoxTronBai);
        ckbRepeat = (CheckBox) findViewById(R.id.checkBoxLapBai);
        arrayList = new ArrayList<>();
        checkBai_daPhat = new ArrayList<>();
        checkBai_daPhat.add("0");
    }
    public void addMusic(){
        arrayList.add(new BaiHat(R.drawable.lunglo,"Lửng Lơ","Masew ft. B Ray &amp; RedT &amp; Ý Tiên", R.raw.lunglo));
        arrayList.add(new BaiHat(R.drawable.aino,"Ái Nộ"," Masew x Khoi Vu", R.raw.aino));
        arrayList.add(new BaiHat(R.drawable.danhthoiquenlang,"Đành Thôi Quên Lãng","Khánh Phương", R.raw.danhthoiquenlang));
        arrayList.add(new BaiHat(R.drawable.giuadailodongtay,"Giữa Đại Lộ Đông Tây","Uyên Linh", R.raw.giuadailodongtay));
        arrayList.add(new BaiHat(R.drawable.kiepmahong,"Kiếp Má Hồng","TLong", R.raw.kiepmahong));
        arrayList.add(new BaiHat(R.drawable.nguoitinhmuadong,"Người Tình Mùa Đông","Hà Anh Tuấn", R.raw.nguoitinhmuadong));
        arrayList.add(new BaiHat(R.drawable.nhinmayvenguoi,"Nhìn Mây Vẽ Người","Hương Ly ft. Jombie (G5R)", R.raw.nhinmayvenguoi));
        arrayList.add(new BaiHat(R.drawable.quaphutuong,"QUẢ PHỤ TƯỚNG","DUNGHOANGPHAM ft REYVIN", R.raw.quatuongphu));
        arrayList.add(new BaiHat(R.drawable.noilaitinhxua,"Nối Lại Tình Xưa","Như Quỳnh & Mạnh Quỳnh", R.raw.noilaitinhxua));
        arrayList.add(new BaiHat(R.drawable.songgio,"SÓNG GIÓ","K-ICM x JACK", R.raw.songgio));
        arrayList.add(new BaiHat(R.drawable.tauanhquanui,"Tàu Anh Qua Núi","Anh Thơ", R.raw.tauanhquanui));
        arrayList.add(new BaiHat(R.drawable.tetbinhan,"Tết Bình An","Hana Cẩm Tiên", R.raw.tetbinhan));
        arrayList.add(new BaiHat(R.drawable.votna,"Vỡ Tan","Hiền Hồ x Trịnh Thăng Bình", R.raw.votan));
        arrayList.add(new BaiHat(R.drawable.traingangtinhtan,"Trái Ngang Tình Tan","Tuấn Cảnh x Lạc Khởi", R.raw.traingangtinhta));
        arrayList.add(new BaiHat(R.drawable.tiengvong,"TIẾNG VỌNG NGÀN ĐỜI ","ĐAN TRƯỜNG", R.raw.tiengvongngandoi));
        arrayList.add(new BaiHat(R.drawable.seve,"Seve","Tez Cadey", R.raw.seve));
        arrayList.add(new BaiHat(R.drawable.dangdo,"DANG DỞ","NAL", R.raw.dangdo));
        arrayList.add(new BaiHat(R.drawable.codon,"CÔ ĐƠN DÀNH CHO AI","LEE KEN x NAL", R.raw.codondanhchoai));
        arrayList.add(new BaiHat(R.drawable.thaylong,"THAY LÒNG","NAL x TVK x Truzg", R.raw.thaylong));
        arrayList.add(new BaiHat(R.drawable.bantinhoi,"Bạn Tình Ơi ( Eric T-J REMIX )","Yuni Boo ft. Goctoi Mixer", R.raw.bantinhoi));
        arrayList.add(new BaiHat(R.drawable.cuoihong,"Cưới hong chốt nha","ÚT NHỊ FT ĐỖ THÀNH DUY", R.raw.cuoihongchotnha));

    }
}