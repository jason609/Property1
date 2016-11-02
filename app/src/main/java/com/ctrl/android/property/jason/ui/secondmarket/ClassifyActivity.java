package com.ctrl.android.property.jason.ui.secondmarket;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.jason.dao.ClassDao;
import com.ctrl.android.property.jason.entity.Kind;
import com.ctrl.android.property.jason.util.StrConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ClassifyActivity extends AppToolBarActivity implements View.OnClickListener {
   private List<String>list=new ArrayList<>();
   private List<Kind>mKindList=new ArrayList<>();
    @InjectView(R.id.lv_classify)
    ListView lv_classify;
    private ArrayAdapter adapter;
    private ClassDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        ButterKnife.inject(this);

        init();
    }

    private void init() {
     /*   for(int i=0;i<20;i++) {
            list.add("类型"+i);
        }*/
        dao=new ClassDao(this);
        dao.requestData(StrConstant.SECOND_MARKET_TYPE_APPKEY);



    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==4){
           // MessageUtils.showShortToast(this,"获取分类成功");
            mKindList=dao.getData();
            for(int i=0;i<mKindList.size();i++){
                list.add(mKindList.get(i).getKindName());
            }
            adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
            lv_classify.setAdapter(adapter);
            lv_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(getIntent().getFlags()==StrConstant.WANT_BUY_TYPE){
                        getIntent().putExtra("kind", mKindList.get(position));
                        setResult(RESULT_OK, getIntent());
                        finish();
                    }
                }
            });
        }

    }

    @Override
    public void onClick(View v) {



    }

    /**
     * header 左侧按钮
     * */
    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.white_arrow_left_none);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MessageUtils.showShortToast(MallShopActivity.this, "AA");
                onBackPressed();
            }
        });
        return true;
    }

    /**
     * 页面标题
     * */
    @Override
    public String setupToolBarTitle() {
        return "分类";
    }



}
