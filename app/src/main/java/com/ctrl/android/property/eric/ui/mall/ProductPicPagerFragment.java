package com.ctrl.android.property.eric.ui.mall;

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
 * 商品图片
 * Created by Eric on 2015/10/8.
 */
public class ProductPicPagerFragment extends Fragment {

    private String imgUrl;
    private ArrayList<String> imagelist;//传入到图片放大类 用
    private int position;
    public static ProductPicPagerFragment newInstance(String imgUrl,List<String> imageList,int position){
        ProductPicPagerFragment fragment = new ProductPicPagerFragment();
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
        Arad.imageLoader.load(imgUrl == null || imgUrl.equals("") ? "aa" : imgUrl).placeholder(R.drawable.default_image).into(imageView);
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
