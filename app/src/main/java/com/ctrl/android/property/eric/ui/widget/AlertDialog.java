package com.ctrl.android.property.eric.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ctrl.android.property.R;

/**
 * 自定义弹出框
 * Created by Eric on 2015/11/6.
 */
public class AlertDialog extends Dialog {

    public AlertDialog(Context context) {
        super(context);
    }

    public AlertDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;

        /**
         * 提示文本
         * */
        private String message;
        /**
         * 按钮文本
         * */
        private String returnText;

        /**
         * 按钮事件
         * */
        private OnClickListener onClickListener;

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

        /**
         * 给弹出的提示内容赋值
         * @param message 提示内容
         * */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
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

        public AlertDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final AlertDialog dialog = new AlertDialog(context,R.style.Dialog);
            View layout = inflater.inflate(R.layout.alert_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            if(message != null){
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            }

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
