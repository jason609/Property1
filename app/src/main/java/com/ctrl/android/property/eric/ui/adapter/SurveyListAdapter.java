package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.Survey;
import com.ctrl.android.property.eric.util.D;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 调查列表 adapter
 * Created by Eric on 2015/10/13.
 */
public class SurveyListAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<Survey> list;

    public SurveyListAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Survey> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.survey_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final Survey survey = list.get(position);

        holder.survey_title.setText(S.getStr(survey.getTitle()));
        holder.community_name_and_time.setText(S.getStr(survey.getCommunityName()) + "    " + D.getDateStrFromStamp(Constant.YYYY_MM_DD,survey.getCreateTime()));
        //是否参与（0：未参与、1：已参与）
        if(survey.getHasParticipate() == 1){
            holder.survey_status_flg.setText("已参与");
            holder.survey_status_flg.setBackgroundResource(R.drawable.gray_bg_shap);
        } else {
            holder.survey_status_flg.setText("未参与");
            holder.survey_status_flg.setBackgroundResource(R.drawable.orange_bg_shap);
        }

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.survey_title)//调查主题
        TextView survey_title;
        @InjectView(R.id.community_name_and_time)//小区名称和时间
        TextView community_name_and_time;
        @InjectView(R.id.survey_status_flg)//调查状态
        TextView survey_status_flg;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }




}
