package com.ctrl.android.property.staff.ui.contact;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.ContactDao;
import com.ctrl.android.property.staff.entity.ContactGroup;
import com.ctrl.android.property.staff.entity.Contactor;
import com.ctrl.android.property.staff.entity.Hotline;
import com.ctrl.android.property.staff.ui.adapter.ContactAdapter;
import com.ctrl.android.property.staff.util.S;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 联系人 activity
 * Created by Eric on 2015/11/26.
 */
public class ContactActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.listView)//通讯列表
    ListView listView;

    @InjectView(R.id.keyword_text)
    EditText keyword_text;
    @InjectView(R.id.search_btn)//
    Button search_btn;

    private ContactAdapter contactAdapter;

    private ContactDao contactDao;
    private List<ContactGroup> listContractGroup;
    private List<Contactor> listContactor;

    private String TITLE = "联系人";

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.contact_activity);
        /**首次进入该页不让弹出软键盘*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        search_btn.setOnClickListener(this);

        contactDao = new ContactDao(this);
        showProgress(true);
        contactDao.requestContactGroupList(AppHolder.getInstance().getStaffInfo().getCommunityId());
        //deviceDao.requestDeviceCategory(AppHolder.getInstance().getStaffInfo().getCommunityId(), "", "");

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if(0 == requestCode){
            listContractGroup = contactDao.getListContactGroup();

            contactAdapter = new ContactAdapter(this);
            contactAdapter.setList(listContractGroup);
            listView.setAdapter(contactAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    index = position;

//                    String categoryId = listDeviceCate.get(index).getId();
//                    String communityId = AppHolder.getInstance().getStaffInfo().getCommunityId();
//                    String name = "";
//                    String currentPage = "";
//                    String rowCountPerPage = "";
//                    showProgress(true);
//                    deviceDao.requestDeviceList(categoryId,communityId,name,currentPage,rowCountPerPage);

                    String communityId = AppHolder.getInstance().getStaffInfo().getCommunityId();
                    String groupId = listContractGroup.get(index).getId();
                    String keyword = "";
                    contactDao.requestContactorList(communityId,groupId,keyword);
                }
            });

        }

        if(1 == requestCode){

            //0:展开  1:不展开
            if(listContractGroup.get(index).getFlg() == 0){
                listContractGroup.get(index).setFlg(1);
            } else {
                listContractGroup.get(index).setFlg(0);
            }

            listContractGroup.get(index).setListContactor(contactDao.getListContactor());
            contactAdapter.setList(listContractGroup);
            contactAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
    }

    @Override
    public void onClick(View v) {
        if(v == search_btn){

            if(!S.isNull(keyword_text.getText().toString())){
                Intent intent = new Intent(this, ContactActivity2.class);
                intent.putExtra("keyword",keyword_text.getText().toString());
                startActivity(intent);
                AnimUtil.intentSlidIn(this);
                finish();
            } else {
                MessageUtils.showShortToast(this, "请输入关键字");
            }


        }
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
            line.setCategory(i + "设备大分类");

            List<Map<String,String>> listmap = new ArrayList<>();
            for(int j = 0 ; j < (((int)(Math.random() * 10)) == 0 ? 1 : ((int)(Math.random() * 10))); j ++){
                Map<String,String> map = new HashMap<>();
                map.put("name",i + j + "设备名称");
                listmap.add(map);
            }

            line.setListMap(listmap);
            list.add(line);

        }

        return list;
    }

}
