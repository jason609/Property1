package com.ctrl.android.property.eric.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.SurveyDetail;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 只有一个textview列表的 adapter
 * Created by Eric on 2015/10/20
 */
public class SurveyDetailAdapter extends BaseAdapter{

    private com.ctrl.android.property.eric.ui.survey.SurveyDetailActivity mActivity;
    private List<SurveyDetail> list;

    /**是否参与（0：未参与、1：已参与）*/
    private String partInFlg = "1";

    public SurveyDetailAdapter(com.ctrl.android.property.eric.ui.survey.SurveyDetailActivity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<SurveyDetail> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setPartInFlg(String partInFlg) {
        this.partInFlg = partInFlg;
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.survey_detail_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final SurveyDetail detail = list.get(position);

        final List<RadioButton> listRadio = new ArrayList<>();
        listRadio.add(holder.radio_a);
        listRadio.add(holder.radio_b);
        listRadio.add(holder.radio_c);

        holder.ques_title.setText((position + 1) + ". " + S.getStr(detail.getQuestion()));
        holder.choice_text_a.setText(S.getStr(detail.getA()));
        holder.choice_text_b.setText(S.getStr(detail.getB()));
        holder.choice_text_c.setText(S.getStr(detail.getC()));

        //0：未选中、1：选中
        if(detail.getAnswerA() == 1){
            holder.radio_a.setChecked(true);
        } else {
            holder.radio_a.setChecked(false);
        }

        //0：未选中、1：选中
        if(detail.getAnswerB() == 1){
            holder.radio_b.setChecked(true);
        } else {
            holder.radio_b.setChecked(false);
        }

        //0：未选中、1：选中
        if(detail.getAnswerC() == 1){
            holder.radio_c.setChecked(true);
        } else {
            holder.radio_c.setChecked(false);
        }

        //final Map<String,String> map = new HashMap<>();
        //map.put(Constant.MESSAGEID, detail.getId());


        if(partInFlg.equals("0")){
            holder.radio_a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewUtil.setOneChecked(listRadio, holder.radio_a);
                    detail.setAnswerA(1);
                    detail.setAnswerB(0);
                    detail.setAnswerC(0);
                    notifyDataSetChanged();
                    //map.put(Constant.OPTIONNUM, "100");
                    //mActivity.changeAnsList(position, map);
                }
            });
            holder.choice_text_a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewUtil.setOneChecked(listRadio, holder.radio_a);
                    detail.setAnswerA(1);
                    detail.setAnswerB(0);
                    detail.setAnswerC(0);
                    notifyDataSetChanged();
                    //map.put(Constant.OPTIONNUM, "100");
                    //mActivity.changeAnsList(position, map);
                }
            });

            holder.radio_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewUtil.setOneChecked(listRadio, holder.radio_b);
                    detail.setAnswerA(0);
                    detail.setAnswerB(1);
                    detail.setAnswerC(0);
                    notifyDataSetChanged();
                    //map.put(Constant.OPTIONNUM, "010");
                    //mActivity.changeAnsList(position, map);

                }
            });
            holder.choice_text_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ViewUtil.setOneChecked(listRadio, holder.radio_b);
                    detail.setAnswerA(0);
                    detail.setAnswerB(1);
                    detail.setAnswerC(0);
                    notifyDataSetChanged();
                    //map.put(Constant.OPTIONNUM, "010");
                    //mActivity.changeAnsList(position, map);
                }
            });

            holder.radio_c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewUtil.setOneChecked(listRadio, holder.radio_c);
                    detail.setAnswerA(0);
                    detail.setAnswerB(0);
                    detail.setAnswerC(1);
                    notifyDataSetChanged();
                    //map.put(Constant.OPTIONNUM, "001");
                    //mActivity.changeAnsList(position, map);
                }
            });
            holder.choice_text_c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewUtil.setOneChecked(listRadio, holder.radio_c);
                    detail.setAnswerA(0);
                    detail.setAnswerB(0);
                    detail.setAnswerC(1);
                    notifyDataSetChanged();
                    //map.put(Constant.OPTIONNUM, "001");
                    //mActivity.changeAnsList(position, map);
                }
            });

        } else {
            holder.radio_a.setEnabled(false);
            holder.radio_b.setEnabled(false);
            holder.radio_c.setEnabled(false);
            holder.choice_text_a.setEnabled(false);
            holder.choice_text_b.setEnabled(false);
            holder.choice_text_c.setEnabled(false);
        }



        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.ques_title)//问题
        TextView ques_title;

        @InjectView(R.id.radio_a)
        RadioButton radio_a;
        @InjectView(R.id.choice_text_a)
        TextView choice_text_a;
        @InjectView(R.id.radio_b)
        RadioButton radio_b;
        @InjectView(R.id.choice_text_b)
        TextView choice_text_b;
        @InjectView(R.id.radio_c)
        RadioButton radio_c;
        @InjectView(R.id.choice_text_c)
        TextView choice_text_c;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
