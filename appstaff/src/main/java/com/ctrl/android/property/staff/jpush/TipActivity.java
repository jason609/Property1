package com.ctrl.android.property.staff.jpush;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.StartActivity;
import com.ctrl.android.property.staff.ui.widget.photoview.CustomDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.jpush.android.api.JPushInterface;

public class TipActivity extends Activity {

    @InjectView(R.id.title)
    TextView title_txt;
    @InjectView(R.id.message)
    TextView message_txt;
    @InjectView(R.id.positiveButton)
    Button positiveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_activity);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        if (null != intent) {
	        Bundle bundle = getIntent().getExtras();
	        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
	        String content = bundle.getString(JPushInterface.EXTRA_ALERT);

            title_txt.setText("提示");
            message_txt.setText(content);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TipActivity.this, StartActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(TipActivity.this);
                    finish();
                }
            });

        }

    }


    private void showPropertiorDialog(String content){
        final CustomDialog.Builder builder = new CustomDialog.Builder(TipActivity.this);
        builder.setTitle("提示");
        builder.setMessage(content);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.hidenNegativeButton();

        builder.create().show();
    }


}
