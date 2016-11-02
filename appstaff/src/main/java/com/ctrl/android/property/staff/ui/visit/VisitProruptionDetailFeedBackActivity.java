package com.ctrl.android.property.staff.ui.visit;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;

public class VisitProruptionDetailFeedBackActivity extends AppToolBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_visit_proruption_detail_feed_back);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public String setupToolBarTitle() {
        return "突发到访";
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
}
