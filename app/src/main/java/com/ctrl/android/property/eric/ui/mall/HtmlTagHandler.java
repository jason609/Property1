package com.ctrl.android.property.eric.ui.mall;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;

import org.xml.sax.XMLReader;

import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * Created by Eric on 2015/9/18.
 */
public class HtmlTagHandler implements Html.TagHandler {

    private Context mContext;

    private ArrayList<String> imageList = new ArrayList<>();
    private int index = -1;

    public HtmlTagHandler(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

        // 处理标签<img>
        if (tag.toLowerCase(Locale.getDefault()).equals("img")) {
            // 获取长度
            int len = output.length();
            // 获取图片地址
            ImageSpan[] images = output.getSpans(len - 1, len, ImageSpan.class);
            String imgURL = images[0].getSource();
            imageList.add(imgURL);
            index = index + 1;
            Log.d("demo","imageList : " + imageList.size() + "## index: " + index);
            // 使图片可点击并监听点击事件
            //output.setSpan(new ClickableImage(mContext, imgURL), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            output.setSpan(new ClickableImage(mContext, imageList, index), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

    }

    private class ClickableImage extends ClickableSpan {
        private String url;
        private Context context;
        private ArrayList<String> imageList;
        private int index;

        public ClickableImage(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        public ClickableImage(Context context,ArrayList<String> imageList, int index) {
            this.context = context;
            this.imageList = imageList;
            this.index = index;
        }

        @Override
        public void onClick(View widget) {
            // 进行图片点击之后的处理
            //MessageUtils.showShortToast(mContext,"图片点击" + imageList.get(index));

//            Intent intent = new Intent(context,ProductImageZoomActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("imageList",imageList);
//            bundle.putInt("position",index);
//            intent.putExtras(bundle);
//            context.startActivity(intent);

        }
    }
}
