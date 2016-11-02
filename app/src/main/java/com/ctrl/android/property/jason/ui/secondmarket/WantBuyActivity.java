package com.ctrl.android.property.jason.ui.secondmarket;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.jason.dao.UsedGoodsDao;
import com.ctrl.android.property.jason.entity.Kind;
import com.ctrl.android.property.jason.util.StrConstant;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*
* 我要购 activity
* */

public class WantBuyActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.rl_want_buy)
    RelativeLayout rl_want_buy;
    @InjectView(R.id.et_baobei_title)
    EditText et_baobei_title;
    @InjectView(R.id.et_baobei_detail)
    EditText et_baobei_detail;
    @InjectView(R.id.et_baobei_originalprice)
    EditText et_baobei_originalprice;
    @InjectView(R.id.et_contact)
    EditText et_contact;
    @InjectView(R.id.et_contact_telephone)
    EditText et_contact_telephone;
    @InjectView(R.id.tv_secong_hand_buy_type)
    TextView tv_secong_hand_buy_type;
    public static final int WANT_BUY_TYPE_CODE=0;
    private Kind kind;
    private UsedGoodsDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_buy);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        rl_want_buy.setOnClickListener(this);
        dao=new UsedGoodsDao(this);

    }

    @Override
    public void onClick(View v) {
        if(v==rl_want_buy){
            Intent intent=new Intent(WantBuyActivity.this,ClassifyActivity.class);
            intent.addFlags(StrConstant.WANT_BUY_TYPE);
            startActivityForResult(intent,WANT_BUY_TYPE_CODE);
            AnimUtil.intentSlidIn(WantBuyActivity.this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     switch (requestCode){
         case WANT_BUY_TYPE_CODE:
             if (resultCode == RESULT_OK) {
                 kind = (Kind) data.getSerializableExtra("kind");
                 tv_secong_hand_buy_type.setText(kind.getKindName());
                 Log.d("demo", "kindId: " + kind.getId());
             }
             break;
     }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==99){
            MessageUtils.showShortToast(this, "信息发布成功");
            Intent intent=new Intent();
            setResult(RESULT_OK,intent);
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
        return "我要购";
    }


    /**
     * header 右侧按钮
     * */

    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText("完成");
        mRightText.setTextColor(Color.WHITE);
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* startActivity(new Intent(WantBuyActivity.this,DetailToBuyActivity.class));
                AnimUtil.intentSlidIn(WantBuyActivity.this);*/
                if (TextUtils.isEmpty(et_baobei_title.getText().toString())) {
                    MessageUtils.showShortToast(WantBuyActivity.this, "标题为空");
                    return;
                }
                if (TextUtils.isEmpty(et_baobei_detail.getText().toString())) {
                    MessageUtils.showShortToast(WantBuyActivity.this, "描述为空");
                    return;
                }
                if (TextUtils.isEmpty(et_baobei_originalprice.getText().toString())) {
                    MessageUtils.showShortToast(WantBuyActivity.this, "价格为空");
                    return;
                }
                if (kind.getId() == null || kind.getId().equals("")) {
                    MessageUtils.showShortToast(WantBuyActivity.this, "请选择分类");
                    return;
                }
                if (TextUtils.isEmpty(et_contact.getText().toString())) {
                    MessageUtils.showShortToast(WantBuyActivity.this, "联系人为空");
                    return;
                }
                if (TextUtils.isEmpty(et_contact_telephone.getText().toString())) {
                    MessageUtils.showShortToast(WantBuyActivity.this, "联系电话为空");
                    return;
                }
                dao.requestGoodsAdd(AppHolder.getInstance().getCommunity().getId(),
                        AppHolder.getInstance().getMemberInfo().getMemberId(),
                        AppHolder.getInstance().getProprietor().getProprietorId(),
                        et_baobei_title.getText().toString(),
                        et_baobei_detail.getText().toString(),
                        et_contact.getText().toString(),
                        et_contact_telephone.getText().toString(),
                        et_baobei_originalprice.getText().toString(),
                        kind.getId(),
                        "",
                        "","","",
                        StrConstant.WANT_BUY_TRANSACTION_TYPE,
                        StrConstant.VISIBLE_TYPE
                        );
            }
        });
        return true;
    }
}
