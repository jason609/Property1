package com.ctrl.android.property.jason.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.beanu.arad.Arad;
import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.ui.widget.ImageZoomActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ƷͼƬ
 * Created by Eric on 2015/10/8.
 */
public class HouseDetailPagerFragment extends Fragment {

    private String imgUrl;
    private ArrayList<String> imagelist;//���뵽ͼƬ�Ŵ��� ��
    private int position;
    public static HouseDetailPagerFragment newInstance(String imgUrl,List<String> imageList,int position){
        HouseDetailPagerFragment fragment = new HouseDetailPagerFragment();
        fragment.imgUrl = imgUrl;
        fragment.imagelist = (ArrayList<String>)imageList;
        fragment.position = position;
        //Log.d("demo","imgUrl: "+ position + "||" + imgUrl);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //Log.d("demo", "imgUrl 2: " + position + "||" + imgUrl);
        Arad.imageLoader.load(imgUrl == null || imgUrl.equals("") ? "aa" : imgUrl).placeholder(R.drawable.pro_pic_big_01).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ImageZoomActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("imageList",imagelist);
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return imageView;
    }


}
