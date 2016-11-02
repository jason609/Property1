package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ctrl.android.property.R;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 社区调查adapter
 * Created by Administrator on 2015/10/26.
 */
public class SurveyAdapter extends BaseAdapter {

    private Activity mActivity;
    private List<Map<String,String>> list;

    public SurveyAdapter(Activity activity) {
        mActivity = activity;
    }
    public void setList(List<Map<String,String>> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity)
                    .inflate(R.layout.survey_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Map<String,String> map = list.get(position);

        holder.mSurveyTitle.setText(map.get("title"));
        holder.mSurveyName.setText(map.get("name"));
        holder.mSurveyRoom.setText(map.get("room"));

        if(map.get("state").equals("0")){
            holder.mSurveyState.setText("未参与");
            holder.mSurveyState.setBackgroundResource(R.drawable.orange_bg_shap);
        } else {
            holder.mSurveyState.setText("已参与");
            holder.mSurveyState.setBackgroundResource(R.drawable.gray_bg_shap);
        }



        return convertView;
    }
    static class ViewHolder {
        @InjectView(R.id.tv_survey_title)//调查问卷标题
        TextView mSurveyTitle;
        @InjectView(R.id.tv_survey_name)
        TextView mSurveyName;
        @InjectView(R.id.tv_survey_room)
        TextView mSurveyRoom;
        @InjectView(R.id.tv_survey_state)
        TextView mSurveyState;
        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
