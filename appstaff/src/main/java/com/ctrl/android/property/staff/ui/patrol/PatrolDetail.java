package com.ctrl.android.property.staff.ui.patrol;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 巡更巡查详情
 * Created by Administrator on 2015/11/10.
 */
public class PatrolDetail extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.tv_patrol_time)
    TextView mPatrolTime;
    @InjectView(R.id.tv_patrol_load)
    TextView mPatrolLoad;
    @InjectView(R.id.tv_patrol_name)
    TextView mPatrolName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_patrol_detail);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {

    }

    @Override
    public String setupToolBarTitle() {
        return "巡视巡查";
    }

    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.white_arrow_left_none);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}
