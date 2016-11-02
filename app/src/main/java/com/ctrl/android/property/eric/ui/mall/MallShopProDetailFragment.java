package com.ctrl.android.property.eric.ui.mall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarFragment;
import com.ctrl.android.property.base.Constant;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 商品详情的 fragment
 * Created by Eric on 2015/9/29.
 */

@SuppressLint("ValidFragment")
public class MallShopProDetailFragment extends AppToolBarFragment implements View.OnClickListener {

    @InjectView(R.id.pro_detail_layout)//图文详细
    LinearLayout pro_detail_layout;
    //@InjectView(R.id.pro_detail_text)//图文内容
    //TextView pro_detail_text;
    @InjectView(R.id.pro_args_layout)//产品参数
    LinearLayout pro_args_layout;

    private int width;
    private int height;

    private int width_draw;
    private int height_draw;

    private static TextView pro_detail_text;//图文内容

    private String proDetailType;//商品详情的类型

    String content = "<h1>t康师傅方便面</h1>" +
            "<img src='http://121.42.199.41:8002/attached/temp/69f475566afa4b1c994b88cfec9e7d1a.jpg' alt='' /> " +
            "</p><h2>这酸爽, 味道好极了</h2><p>" +
            "<img src='http://121.42.199.41:8002/attached/temp/3b9d2e93923d4facb5f006aeb331fad4.jpg' alt='' /> " +
            "</p><h4><span style='color:#E53333;'>大家好才是真的好</span></h4><p>" +
            "<span style='color:#E53333;'>" +
            "<img src='http://121.42.199.41:8002/attached/temp/224f9313dc4646e98fd5c6b31d8b9fb8.jpg' alt='' /><br /></span></p>" +
            "<p><span style='color:#E53333;'><img src='http://121.42.199.41:8002/attached/temp/0bbc911caf3748c1a258507b7f8ec666.jpg' alt='' /><br />" +
            "</span></p>";

    HtmlTagHandler tagHandler;

    public static MallShopProDetailFragment newInstance(String proDetailType){
        MallShopProDetailFragment fragment = new MallShopProDetailFragment();
        fragment.proDetailType = proDetailType;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.product_detail_fragment, container, false);
        pro_detail_text = (TextView) v.findViewById(R.id.pro_detail_text);

        WindowManager wm = (WindowManager) getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

        tagHandler = new HtmlTagHandler(getActivity());


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        /**图文详细*/
        if(proDetailType.equals(Constant.MALL_PRO_DETAIL_TEXT)){
            pro_detail_layout.setVisibility(View.VISIBLE);
            pro_args_layout.setVisibility(View.GONE);
            //pro_detail_text.setText("内容");
            t.start();
        } else if(proDetailType.equals(Constant.MALL_PRO_DETAIL_ARGS)){
            pro_detail_layout.setVisibility(View.GONE);
            pro_args_layout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }

    private static android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0x101) {
                //Log.d("demo","AAAAAAAAAAA" + (CharSequence) msg.obj);
                pro_detail_text.setText((CharSequence) msg.obj);
                pro_detail_text.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
                pro_detail_text.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
            }
            super.handleMessage(msg);
        }
    };

    // 因为从网上下载图片是耗时操作 所以要开启新线程
    Thread t = new Thread(new Runnable() {

        Message msg = Message.obtain();

        @Override
        public void run() {
            // TODO Auto-generated method stub
            //bar.setVisibility(View.VISIBLE);
            /**
             * 要实现图片的显示需要使用Html.fromHtml的一个重构方法：public static Spanned
             * fromHtml (String source, Html.ImageGetterimageGetter,
             * Html.TagHandler
             * tagHandler)其中Html.ImageGetter是一个接口，我们要实现此接口，在它的getDrawable
             * (String source)方法中返回图片的Drawable对象才可以。
             */
            Html.ImageGetter imageGetter = new Html.ImageGetter() {

                @Override
                public Drawable getDrawable(String source) {
                    // TODO Auto-generated method stub
                    URL url;
                    Drawable drawable = null;
                    Log.d("demo","XXXXXXXXXXXXXX");
                    try {
                        url = new URL(source);
                        drawable = Drawable.createFromStream(
                                url.openStream(), null);
                        width_draw = drawable.getIntrinsicWidth();
                        height_draw = drawable.getIntrinsicHeight();
                        drawable.setBounds(0, 0,
                                //width - 100,
                                //(drawable.getIntrinsicHeight()/drawable.getIntrinsicWidth())*(width - 100)
                                width - 100,
                                (((width - 100) * height_draw)/width_draw)
                        );
                        Log.d("demo", "宽: " + width + "高: " + (((width - 100) * height_draw) / width_draw));
                        Log.d("demo", "宽: " + drawable.getIntrinsicWidth() + "高: " + drawable.getIntrinsicHeight());
                    } catch (MalformedURLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return drawable;
                }
            };
            CharSequence test = Html.fromHtml(content,imageGetter, tagHandler);
            Log.d("demo","test: " + test);
            msg.what = 0x101;
            msg.obj = test;
            handler.sendMessage(msg);
        }
    });

    /**
     * 测试 获取数据的方法
     * */
    private List<Map<String,String>> getListMap(String str){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("rate","" + (i*0.5));
            map.put("time",i + "0");
            map.put("tel","1578****078" + i);
            map.put("content",str + " : 味道还不错, 送餐速度也挺快, 还有饮料.味道还不错, 送餐速度也挺快, 还有饮料.送货速度很快, 质量也不错 " + i );
            map.put("date","2015-09-29 10:3" + i);

            list.add(map);
        }
        return list;
    }
}
