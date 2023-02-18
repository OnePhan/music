package com.example.music_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterDs extends BaseAdapter {
    private Context context;
    private int layout;
    private List<BaiHat> baiHatList;
    public AdapterDs(Context context, int layout, List<BaiHat> baiHatList) {
        this.context = context;
        this.layout = layout;
        this.baiHatList = baiHatList;
    }
    @Override
    public int getCount() {
        return baiHatList.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHoder{
        ImageView imgAnh;
        TextView txtTen, txtTG;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder hoder;
        if (convertView == null){
            hoder = new ViewHoder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layout, null);
//            anhXa
            hoder.imgAnh = (ImageView) convertView.findViewById(R.id.imageViewAnh);
            hoder.txtTen = (TextView) convertView.findViewById(R.id.textViewTen);
            hoder.txtTG = (TextView) convertView.findViewById(R.id.textViewTG);
            convertView.setTag(hoder);
        }else {
            hoder = (ViewHoder) convertView.getTag();
        }
        BaiHat baiHat = baiHatList.get(position);
        hoder.imgAnh.setImageResource(baiHat.getAnh());
        hoder.txtTen.setText(baiHat.getTenBaiHat());
        hoder.txtTG.setText(baiHat.getTenTacGia());
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.anim_listds_scale);
        convertView.startAnimation(animation);
        return convertView;
    }
}
