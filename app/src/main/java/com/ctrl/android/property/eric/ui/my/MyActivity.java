package com.ctrl.android.property.eric.ui.my;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.MainActivity;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.MyApplication;
import com.ctrl.android.property.base.Utils;
import com.ctrl.android.property.eric.dao.HouseDao;
import com.ctrl.android.property.eric.dao.MainDao;
import com.ctrl.android.property.eric.dao.MemberDao;
import com.ctrl.android.property.eric.entity.House;
import com.ctrl.android.property.eric.ui.act.ActListActivity;
import com.ctrl.android.property.eric.ui.express.ExpressActivity;
import com.ctrl.android.property.eric.ui.house.HouseListActivity2;
import com.ctrl.android.property.eric.ui.mall.MallMainActivity;
import com.ctrl.android.property.eric.ui.mall.MallShopCartActivity;
import com.ctrl.android.property.eric.ui.mall.OrderListActivity;
import com.ctrl.android.property.eric.ui.pay.HousePayHistoryActivity;
import com.ctrl.android.property.eric.ui.visit.MyVisitActivity;
import com.ctrl.android.property.eric.ui.widget.CustomDialog;
import com.ctrl.android.property.eric.ui.widget.RoundImageView;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;
import com.ctrl.android.property.eric.util.UiUtil;
import com.ctrl.android.property.jason.ui.complaint.MyComplaintActivity;
import com.ctrl.android.property.jason.ui.repairs.MyRepairsActivity;
import com.ctrl.android.property.jason.ui.secondmarket.SecondHandActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 我的信息 主页面
 * Created by Eric on 2015/10/16.
 */
public class MyActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.userIcon)//用户头像
    RoundImageView userIcon;

    @InjectView(R.id.welcome_text)//欢迎语
    TextView welcome_text;
    @InjectView(R.id.my_order_btn_with_num)//我的订单  带个数的
    LinearLayout my_order_btn_with_num;
    @InjectView(R.id.my_setting_btn)//
    ImageView my_setting_btn;
    @InjectView(R.id.my_orders_num)//我的订单数量
    TextView my_orders_num;
    @InjectView(R.id.my_express_btn_wirh_num)//我的快递按钮  带个数的
    LinearLayout my_express_btn_wirh_num;
    @InjectView(R.id.my_express_num)//我的快递数量
    TextView my_express_num;
    @InjectView(R.id.my_score_btn_with_num)//我的积分  带个数的
    LinearLayout my_score_btn_with_num;
    @InjectView(R.id.my_score_num)//我的积分数量
    TextView my_score_num;

    @InjectView(R.id.my_locate_btn)//小区定位
    RelativeLayout my_locate_btn;
    @InjectView(R.id.my_community_name)//小区名称
    TextView my_community_name;
    @InjectView(R.id.my_community_house)//房子地址
    TextView my_community_house;

    @InjectView(R.id.shop_order_btn)//商城订单
    RelativeLayout shop_order_btn;
    @InjectView(R.id.my_pay_history_btn)//我的缴费记录
    RelativeLayout my_pay_history_btn;
    @InjectView(R.id.my_activity_btn)//我的活动
    RelativeLayout my_activity_btn;
    @InjectView(R.id.my_complain_btn)//我的投诉
    RelativeLayout my_complain_btn;
    @InjectView(R.id.my_repair_btn)//我的报修
    RelativeLayout my_repair_btn;
    @InjectView(R.id.my_appointment_btn)//预约及到访
    RelativeLayout my_appointment_btn;
    @InjectView(R.id.my_community_info_btn)//我的分类信息
    RelativeLayout my_community_info_btn;
    @InjectView(R.id.my_express_btn)//我的快递
    RelativeLayout my_express_btn;
    @InjectView(R.id.my_coupon_btn)//我的优惠券
    RelativeLayout my_coupon_btn;
    @InjectView(R.id.my_score_btn)//我的积分
    RelativeLayout my_score_btn;

    @InjectView(R.id.menu_main_page_btn)//footer 首页
    RelativeLayout menu_main_page_btn;
    @InjectView(R.id.menu_service_btn)//footer 服务
    RelativeLayout menu_service_btn;
    @InjectView(R.id.menu_cart_btn)//footer 购物车
    RelativeLayout menu_cart_btn;
    @InjectView(R.id.menu_my_btn)//footer 我的
    RelativeLayout menu_my_btn;
    @InjectView(R.id.menu_my_btn_img)//我的图标
    ImageView menu_my_btn_img;
    @InjectView(R.id.menu_my_btn_text)//我的文本
    TextView menu_my_btn_text;

    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    private String[] items = new String[]{"本地图片", "拍照"};

    private MemberDao memberDao;

    private MainDao mainDao;
    private String TITLE = StrConstant.MAIN_PAGE;

    private HouseDao houseDao;
    private List<House> listHouse;

    //private String welcome = "欢迎你  " + AppHolder.getInstance().getMemberInfo().getNickName();
    //private String orders_num = "" + AppHolder.getInstance().getMemberInfo().getOrderCount();
    //private String express_num = "" + AppHolder.getInstance().getMemberInfo().getExpressCount();
    //private String score_num = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.my_activity);
        ButterKnife.inject(this);
        //init();
    }

    private void init(){

        initBtnLictener();

        menu_my_btn_img.setImageDrawable(getResources().getDrawable(R.drawable.menu_my_icon_press));
        menu_my_btn_text.setTextColor(getResources().getColor(R.color.text_green));

        memberDao = new MemberDao(this);
        //showProgress(true);
        memberDao.requestMemberInfo(AppHolder.getInstance().getMemberInfo().getMemberId(), AppHolder.getInstance().getCommunity().getId());

    }

    @Override
    protected void onResume() {
        super.onResume();

        init();

        if(AppHolder.getInstance().getHouse() != null){
            my_community_name.setText(S.getStr(AppHolder.getInstance().getHouse().getCommunityName()));
            my_community_house.setText(S.getStr(AppHolder.getInstance().getHouse().getBuilding())
                    + "楼" + S.getStr(AppHolder.getInstance().getHouse().getUnit())
                    + "单元" + S.getStr(AppHolder.getInstance().getHouse().getRoom()) + "室");
        }

    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(120 == requestCode){

            Arad.imageLoader.load(S.isNull(AppHolder.getInstance().getMemberInfo().getImgUrl()) ? "aa" : AppHolder.getInstance().getMemberInfo().getImgUrl())
                    .placeholder(R.drawable.touxiang2x)
                    .into(userIcon);

            welcome_text.setText("欢迎你  " + AppHolder.getInstance().getMemberInfo().getNickName());
            my_orders_num.setText("" + AppHolder.getInstance().getMemberInfo().getOrderCount());
            my_express_num.setText("" + AppHolder.getInstance().getMemberInfo().getExpressCount());
            my_score_num.setText("0");
        }

        if(2 == requestCode){
            Arad.imageLoader.load(S.isNull(AppHolder.getInstance().getMemberInfo().getImgUrl()) ? "aa" : AppHolder.getInstance().getMemberInfo().getImgUrl())
                    .placeholder(R.drawable.touxiang2x)
                    .into(userIcon);
        }
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        showProgress(false);
        MessageUtils.showShortToast(this, errorMessage);
    }

    @Override
    public void onClick(View v) {

        if(v == my_setting_btn){
            //MessageUtils.showShortToast(this,"设置");
            Intent intent = new Intent(MyActivity.this, MySettingActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(MyActivity.this);
        }

        if(v == my_locate_btn){

            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MyActivity.this, HouseListActivity2.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MyActivity.this);
                }
            }

//            Intent intent = new Intent(MyActivity.this, HouseListActivity2.class);
//            startActivity(intent);
//            AnimUtil.intentSlidIn(MyActivity.this);
        }

        if(v == my_order_btn_with_num){
            //MessageUtils.showShortToast(this,"我的订单");
            Intent intent = new Intent(MyActivity.this, OrderListActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(MyActivity.this);
        }

        if(v == my_express_btn_wirh_num){
            //MessageUtils.showShortToast(this,"我的快递");

            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MyActivity.this, ExpressActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MyActivity.this);
                }
            }

//            Intent intent = new Intent(MyActivity.this, ExpressActivity.class);
//            startActivity(intent);
//            AnimUtil.intentSlidIn(MyActivity.this);
        }

        if(v == my_score_btn_with_num){
            MessageUtils.showShortToast(this, "尚未开通, 敬请期待");
        }

        if(v == userIcon){
            showDialog();
        }

        //商城订单
        if(v == shop_order_btn){
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                Intent intent = new Intent(MyActivity.this, OrderListActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(MyActivity.this);
            }
        }
        //我的缴费记录
        if(v == my_pay_history_btn){
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MyActivity.this, HousePayHistoryActivity.class);
                    intent.putExtra("communityName",AppHolder.getInstance().getHouse().getCommunityName());
                    intent.putExtra("building_unit_room",(AppHolder.getInstance().getHouse().getBuilding() + "-" + AppHolder.getInstance().getHouse().getUnit() + "-" + AppHolder.getInstance().getHouse().getRoom()));
                    intent.putExtra("proprietorId",AppHolder.getInstance().getHouse().getProprietorId());
                    intent.putExtra("addressId",AppHolder.getInstance().getHouse().getAddressId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MyActivity.this);
                }
            }
        }
        //我的活动
        if(v == my_activity_btn){
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MyActivity.this, ActListActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MyActivity.this);
                }
            }
        }
        //我的投诉
        if(v == my_complain_btn){
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MyActivity.this, MyComplaintActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MyActivity.this);
                }
            }
        }
        //我的报修
        if(v == my_repair_btn){
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MyActivity.this, MyRepairsActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MyActivity.this);
                }
            }
        }
        //预约及到访
        if(v == my_appointment_btn){
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MyActivity.this, MyVisitActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MyActivity.this);
                }
            }
        }
        //我的分类信息
        if(v == my_community_info_btn){
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MyActivity.this, SecondHandActivity.class);
                    intent.addFlags(com.ctrl.android.property.jason.util.StrConstant.GET_OWNER_LIST);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MyActivity.this);
                }
            }
        }
        //我的快递
        if(v == my_express_btn){
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MyActivity.this, ExpressActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MyActivity.this);
                }
            }
        }
        //我的优惠券
        if(v == my_coupon_btn){
            MessageUtils.showShortToast(MyActivity.this, "尚未开通, 敬请期待");
        }
        //我的积分
        if(v == my_score_btn){
            MessageUtils.showShortToast(MyActivity.this, "尚未开通, 敬请期待");
        }


    }


    /**
     * header 左侧按钮
     * */
    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.toolbar_back);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.showShortToast(MyActivity.this, "返回");
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
    @Override
    public boolean setupToolBarRightButton(ImageView rightButton) {
        rightButton.setImageResource(R.drawable.toolbar_home);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHomePage();
            }
        });
        return true;
    }

    /**
     * 初始化中设置 每个text按钮的监听
     * */
    private void initBtnLictener(){

//        UiUtil.clickToActivity(shop_order_btn, MyActivity.this, OrderListActivity.class);
//        UiUtil.clickToActivity(my_pay_history_btn, MyActivity.this, HousePayHistoryActivity.class);
//        UiUtil.clickToActivity(my_activity_btn, MyActivity.this, ActListActivity.class);
//        UiUtil.clickToActivity(my_complain_btn, MyActivity.this, MyComplaintActivity.class);
//        UiUtil.clickToActivity(my_repair_btn, MyActivity.this, MyRepairsActivity.class);
//        UiUtil.clickToActivity(my_appointment_btn, MyActivity.this, MyVisitActivity.class);
//        UiUtil.clickToActivity(my_community_info_btn, MyActivity.this, SecondHandActivity.class);
//        UiUtil.clickToActivity(my_express_btn, MyActivity.this, ExpressActivity.class);

//        my_coupon_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MessageUtils.showShortToast(MyActivity.this, "尚未开通, 敬请期待");
//            }
//        });
//        my_score_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MessageUtils.showShortToast(MyActivity.this,"尚未开通, 敬请期待");
//            }
//        });

        shop_order_btn.setOnClickListener(this);
        my_pay_history_btn.setOnClickListener(this);
        my_activity_btn.setOnClickListener(this);
        my_complain_btn.setOnClickListener(this);
        my_repair_btn.setOnClickListener(this);
        my_appointment_btn.setOnClickListener(this);
        my_community_info_btn.setOnClickListener(this);
        my_express_btn.setOnClickListener(this);
        my_coupon_btn.setOnClickListener(this);
        my_score_btn.setOnClickListener(this);
        my_community_info_btn.setOnClickListener(this);

        //UiUtil.clickToActivity(my_coupon_btn, MyActivity.this, MainActivity.class);
        //UiUtil.clickToActivity(my_score_btn, MyActivity.this, MainActivity.class);

//        my_community_info_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MyActivity.this, SecondHandActivity.class);
//                intent.addFlags(com.ctrl.android.property.jason.util.StrConstant.GET_OWNER_LIST);
//                startActivity(intent);
//                AnimUtil.intentSlidIn(MyActivity.this);
//            }
//        });

        UiUtil.clickToActivity(menu_main_page_btn, MyActivity.this, MainActivity.class);
        UiUtil.clickToActivity(menu_service_btn, MyActivity.this, MallMainActivity.class);
        UiUtil.clickToActivity(menu_cart_btn, MyActivity.this, MallShopCartActivity.class);
        //UiUtil.clickToActivity(menu_my_btn, MyActivity.this, MyActivity.class);

        my_setting_btn.setOnClickListener(this);
        my_locate_btn.setOnClickListener(this);
        my_order_btn_with_num.setOnClickListener(this);
        my_express_btn_wirh_num.setOnClickListener(this);
        my_score_btn_with_num.setOnClickListener(this);

        userIcon.setOnClickListener(this);

    }

    /**
     * 显示选择对话框
     */
    private void showDialog() {

        new AlertDialog.Builder(MyActivity.this)
                .setTitle("设置头像")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case IMAGE_REQUEST_CODE:
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery,
                                        IMAGE_REQUEST_CODE);
                                break;
                            case CAMERA_REQUEST_CODE:

                                Intent intentFromCapture = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);

                                startActivityForResult(intentFromCapture,
                                        CAMERA_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                if(data != null){

                    Uri uri = data.getData();
                    String thePath = Utils.getInstance().getPath(this, uri);
                    Log.d("demo", "thePath: " + thePath);
                    startPhotoZoom(Uri.fromFile(new File(thePath)));
                }
                break;
            case CAMERA_REQUEST_CODE:
                if(data != null){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(MyActivity.this.getContentResolver(), bitmap, null, null));
                    startPhotoZoom(uri);
                }
                break;
            case RESULT_REQUEST_CODE:
                if (data != null) {
                    getImageToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] buffer = out.toByteArray();
            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
            String photo = new String(encode);

            //Drawable drawable = new BitmapDrawable(bitmap);
            //userIcon.setImageDrawable(drawable);
            if (photo != null){
                showProgress(true);
                memberDao.requestModifyMemberIcon(AppHolder.getInstance().getMemberInfo().getMemberId(),photo);
            }
        }
    }

    private void showMemberDialog(){
        final CustomDialog.Builder builder = new CustomDialog.Builder(MyActivity.this);
        builder.setTitle("提示");
        builder.setMessage("游客尚未注册,是否注册");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MyActivity.this, RegesteActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(MyActivity.this);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void showPropertiorDialog(){
        final CustomDialog.Builder builder = new CustomDialog.Builder(MyActivity.this);
        builder.setTitle("提示");
        builder.setMessage("未找到业主信息,请添加房屋");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //MessageUtils.showShortToast(LoginActivity.this, "未找到业主信息,请添加房屋");
                Intent intent = new Intent(MyActivity.this, HouseListActivity2.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(MyActivity.this);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

}
