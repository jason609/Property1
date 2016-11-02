package com.ctrl.android.property.eric.ui.hotline;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.entity.Hotline;
import com.ctrl.android.property.eric.ui.adapter.FamilyHotlineAdapter;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 家人热线(通讯录) activity
 * Created by Eric on 2015/10/13.
 */
public class FamilyHotlineActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.listView)//通讯列表
    ListView listView;

    private FamilyHotlineAdapter familyHotineAdapter;

    private String TITLE = StrConstant.CONTACTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.family_hotline_activity);
        /**首次进入该页不让弹出软键盘*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){
        familyHotineAdapter = new FamilyHotlineAdapter(this);
        familyHotineAdapter.setList(getList());
        listView.setAdapter(familyHotineAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageUtils.showShortToast(FamilyHotlineActivity.this, getList().get(position).getCategory());
            }
        });
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
        return TITLE;
    }


    /**
     * header 右侧按钮
     * */
//    @Override
//    public boolean setupToolBarRightButton(ImageView rightButton) {
//        rightButton.setImageResource(R.drawable.white_cross_icon);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MessageUtils.showShortToast(EasyShopAroundActivity.this, "MORE");
//                //showProStyleListPop();
//            }
//        });
//        return true;
//    }

    private List<Hotline> getList(){
        List<Hotline> list = new ArrayList<>();

        for(int i = 0 ; i < 6 ; i ++){
            Hotline line = new Hotline();
            line.setCategory(i + "部门名称");

            List<Map<String,String>> listmap = new ArrayList<>();
            for(int j = 0 ; j < (((int)(Math.random() * 10)) == 0 ? 1 : ((int)(Math.random() * 10))); j ++){
                Map<String,String> map = new HashMap<>();
                map.put("img",j + "aa");
                map.put("name",i + j + "联系人");
                map.put("position",i + j + "职位");
                map.put("duty",i + j + "职责范围");
                map.put("tel",i + j + "0000000000");
                listmap.add(map);
            }

            line.setListMap(listmap);
            list.add(line);

        }

        return list;
    }

}
