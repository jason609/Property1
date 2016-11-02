package com.ctrl.android.property.staff.ui.repair;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.ClassDao;
import com.ctrl.android.property.staff.dao.RepairDao;
import com.ctrl.android.property.staff.util.S;
import com.ctrl.android.property.staff.util.StrConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/*
* 报修  退单activity
* */

public class ChargeBackCauseActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.spinner)//下拉列表
    Spinner spinner;
    @InjectView(R.id.et_chargeback_content)//原因描述
    EditText et_chargeback_content;

    private ClassDao cdao;
    private List<String>mKindList=new ArrayList<>();
    private RepairDao rdao;
    private String reasonKindId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_charge_back_cause);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        cdao=new ClassDao(this);
        cdao.requestData(StrConstant.FIX_REFUSE_REASON);
        rdao=new RepairDao(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reasonKindId=cdao.getData().get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==999){
            for(int i=0;i<cdao.getData().size();i++){
                mKindList.add(cdao.getData().get(i).getKindName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChargeBackCauseActivity.this,android.R.layout.simple_spinner_item,mKindList);
            spinner.setAdapter(adapter);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }

        if(requestCode==2){
            MessageUtils.showShortToast(this, "拒单提交成功");
            Intent intent=new Intent(ChargeBackCauseActivity.this,RepairsActivity.class);
            startActivity(intent);
            finish();
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
        return "退单原因";
    }

    /**
     *右侧文本
     */
    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText("完成");
        mRightText.setTextColor(Color.WHITE);
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    rdao.requestRepairAssignType(getIntent().getStringExtra("repairDemandId"),
                            AppHolder.getInstance().getStaffInfo().getStaffId(),
                            "2",
                            reasonKindId,
                            et_chargeback_content.getText().toString());

                }
            }
        });
        return true;
    }



    private boolean checkInput(){

        if(S.isNull(reasonKindId)){
            MessageUtils.showShortToast(this, "原因类型不可为空");
            return false;
        }

        if(S.isNull(et_chargeback_content.getText().toString())){
            MessageUtils.showShortToast(this,"原因描述不可为空");
            return false;
        }

        return true;
    }
}
