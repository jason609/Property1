package com.ctrl.android.property.eric.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctrl.android.property.R;

/**
 * 自定义弹出框
 * Created by Eric on 2015/11/6.
 */
public class AlertChartDialog extends Dialog {

    public AlertChartDialog(Context context) {
        super(context);
    }

    public AlertChartDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;

        /**
         * 提示文本
         * */
        private String message;

        /**
         * 选项A 数量
         * */
        private String choice_num_a;

        /**
         * 选项A 名称
         * */
        private String choice_title_a;

        /**
         * 选项B 数量
         * */
        private String choice_num_b;

        /**
         * 选项B 名称
         * */
        private String choice_title_b;

        /**
         * 选项C 数量
         * */
        private String choice_num_c;

        /**
         * 选项C 名称
         * */
        private String choice_title_c;

        /**
         * 按钮文本
         * */
        private String returnText;

        /**
         * 按钮事件
         * */
        private OnClickListener onClickListener;

        /**
         * 屏幕宽度
         * */
        private int width;

        /**
         * 屏幕高度
         * */
        private int height;

        /**
         * 是否点击外框消失
         * */
        private boolean cancelFlg = true;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 给弹出的提示内容赋值
         * @param message 提示内容
         * */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setChoice_num_a(String choice_num_a) {
            this.choice_num_a = choice_num_a;
            return this;
        }

        public Builder setChoice_title_a(String choice_title_a) {
            this.choice_title_a = choice_title_a;
            return this;
        }

        public Builder setChoice_num_b(String choice_num_b) {
            this.choice_num_b = choice_num_b;
            return this;
        }

        public Builder setChoice_title_b(String choice_title_b) {
            this.choice_title_b = choice_title_b;
            return this;
        }

        public Builder setChoice_num_c(String choice_num_c) {
            this.choice_num_c = choice_num_c;
            return this;
        }

        public Builder setChoice_title_c(String choice_title_c) {
            this.choice_title_c = choice_title_c;
            return this;
        }

        /**
         * 给按钮添加文本和事件
         * @param buttonText 文本
         * @param listener 事件
         * */
        public Builder setReturnButton(String buttonText,OnClickListener listener) {
            this.returnText = buttonText;
            this.onClickListener = listener;
            return this;
        }

        /**
         * 给按钮添加文本和事件
         * @param buttonText 文本
         * @param listener 事件
         * */
        public Builder setReturnButton(int buttonText,OnClickListener listener) {
            this.returnText = (String) context.getText(buttonText);
            this.onClickListener = listener;
            return this;
        }

        /**
         * 设置 是否可以点击外框后消失
         * */
        public Builder setCancleFlg(boolean flg) {
            this.cancelFlg = flg;
            return this;
        }

        public AlertChartDialog create() {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final AlertChartDialog dialog = new AlertChartDialog(context,R.style.Dialog);
            View layout = inflater.inflate(R.layout.alert_chart_layout, null);

            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            /**
             * 设置返回信息
             * */
            if(message != null){
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            }

            /**
             * 设置选项A 数量
             * */
            if(choice_num_a != null){
                ((TextView) layout.findViewById(R.id.choice_num_a)).setText(choice_num_a);
            }

            /**
             * 设置选项A 名称
             * */
            if(choice_title_a != null){
                ((TextView) layout.findViewById(R.id.choice_title_a)).setText(choice_title_a);
            }

            /**
             * 设置选项B 数量
             * */
            if(choice_num_b != null){
                ((TextView) layout.findViewById(R.id.choice_num_b)).setText(choice_num_b);
            }

            /**
             * 设置选项B 名称
             * */
            if(choice_title_b != null){
                ((TextView) layout.findViewById(R.id.choice_title_b)).setText(choice_title_b);
            }

            /**
             * 设置选项C 数量
             * */
            if(choice_num_c != null){
                ((TextView) layout.findViewById(R.id.choice_num_c)).setText(choice_num_c);
            }

            /**
             * 设置选项C 名称
             * */
            if(choice_title_c != null){
                ((TextView) layout.findViewById(R.id.choice_title_c)).setText(choice_title_c);
            }

            //LinearLayout chartLayout = ((LinearLayout) layout.findViewById(R.id.chart_layout));
            //chartLayout.setLayoutParams(new LinearLayout.LayoutParams((AndroidUtil.getDeviceWidth((Activity) context)) , AndroidUtil.getDeviceWidth((Activity)context) * 2 / 3));
            //int height = chartLayout.getMeasuredHeight();
            //int width = chartLayout.getMeasuredWidth();

            Log.d("demo","height: " + height);
            Log.d("demo","width: " + width);

            int a = Integer.parseInt(choice_num_a);
            int b = Integer.parseInt(choice_num_b);
            int c = Integer.parseInt(choice_num_c);
            int sum = a + b + c;
            Log.d("demo","a: " + a);
            Log.d("demo","b: " + b);
            Log.d("demo","c: " + c);
            Log.d("demo","sum: " + sum);

            ImageView choice_img_a = ((ImageView) layout.findViewById(R.id.choice_img_a));
            ImageView choice_img_b = ((ImageView) layout.findViewById(R.id.choice_img_b));
            ImageView choice_img_c = ((ImageView) layout.findViewById(R.id.choice_img_c));

            ViewGroup.LayoutParams lpa = choice_img_a.getLayoutParams();
            lpa.height = (height - 50)/3*a/sum;
            lpa.width = (width)/6;
            choice_img_a.setLayoutParams(lpa);
            Log.d("demo", "lpa height: " + lpa.height);
            Log.d("demo", "lpa width: " + lpa.width);
            ViewGroup.LayoutParams lpb = choice_img_b.getLayoutParams();
            lpb.height = (height - 50)/3*b/sum;
            lpb.width = (width)/6;
            choice_img_b.setLayoutParams(lpb);
            ViewGroup.LayoutParams lpc = choice_img_c.getLayoutParams();
            lpc.height = (height - 50)/3*c/sum;
            lpc.width = (width)/6;
            choice_img_c.setLayoutParams(lpc);

            /**
             * 设置返回按钮
             * */
            if (returnText != null) {
                ((TextView) layout.findViewById(R.id.back_btn)).setText(returnText);
                if (onClickListener != null) {
                    ((TextView) layout.findViewById(R.id.back_btn))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    onClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                layout.findViewById(R.id.alert_layout).setVisibility(View.GONE);
            }
            dialog.setCancelable(cancelFlg);
            dialog.setContentView(layout);
            return dialog;
        }

    }



}
