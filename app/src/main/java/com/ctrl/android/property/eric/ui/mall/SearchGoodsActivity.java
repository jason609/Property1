package com.ctrl.android.property.eric.ui.mall;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.jason.dao.ProductDao;
import com.ctrl.android.property.jason.entity.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
* 根据关键字搜索店内商品 activity
* Created by Jason on 2015/11/16
* Modify by Eric on 2015/11/16
* */

public class SearchGoodsActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.tv_search_goods_confirm)
    TextView tv_search_goods_confirm;
    @InjectView(R.id.et_search_goods_search)
    EditText et_search_goods_search;
    private ProductDao dao;
    private List<Product>mProductList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_goods_activity);
        //禁止自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        dao=new ProductDao(this);
        tv_search_goods_confirm.setOnClickListener(this);
        tv_search_goods_confirm.setText("取消");
        et_search_goods_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(et_search_goods_search.getText().toString())){
                    tv_search_goods_confirm.setText("取消");
                }else{
                    tv_search_goods_confirm.setText("确定");
                }



            }
        });


    }


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==0){
            MessageUtils.showShortToast(this, "查询成功");
            if(mProductList.size()>0) {
                mProductList = dao.getProductList();
                Intent intent = new Intent(SearchGoodsActivity.this, MallShopMainActivity.class);
                intent.putExtra("mProductList", (Serializable) mProductList);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v==tv_search_goods_confirm){
            if(!TextUtils.isEmpty(et_search_goods_search.getText().toString())) {
                dao.requestKeyProduct( "6e1eb13ef0fb4bb4a20121817f7e57b9",et_search_goods_search.getText().toString(), "");
            }else{
                finish();
            }
        }
        
    }
}
