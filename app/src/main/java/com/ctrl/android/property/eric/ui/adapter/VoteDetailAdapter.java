package com.ctrl.android.property.eric.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.SurveyDetail;
import com.ctrl.android.property.eric.ui.survey.VoteDetailActivity;
import com.ctrl.android.property.eric.util.S;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 只有一个textview列表的 adapter
 * Created by Eric on 2015/10/20
 */
public class VoteDetailAdapter extends BaseAdapter{

    private VoteDetailActivity mActivity;
    private List<SurveyDetail> list;

    /**是否参与（0：未参与、1：已参与）*/
    private String partInFlg = "1";

    private List<CheckBox> listCheckBox = new ArrayList<>();

    public VoteDetailAdapter(VoteDetailActivity mActivity){
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.vote_detail_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        listCheckBox.add(holder.checkbox_a);
        listCheckBox.add(holder.checkbox_b);
        listCheckBox.add(holder.checkbox_c);

        final SurveyDetail detail = list.get(position);

        holder.ques_title.setText((position + 1) + ". " + S.getStr(detail.getQuestion()));
        holder.choice_text_a.setText(S.getStr(detail.getA()));
        holder.choice_text_b.setText(S.getStr(detail.getB()));
        holder.choice_text_c.setText(S.getStr(detail.getC()));

        //0：未选中、1：选中
        if(detail.getAnswerA() == 1){
            holder.checkbox_a.setChecked(true);
        } else {
            holder.checkbox_a.setChecked(false);
        }

        //0：未选中、1：选中
        if(detail.getAnswerB() == 1){
            holder.checkbox_b.setChecked(true);
        } else {
            holder.checkbox_b.setChecked(false);
        }

        //0：未选中、1：选中
        if(detail.getAnswerC() == 1){
            holder.checkbox_c.setChecked(true);
        } else {
            holder.checkbox_c.setChecked(false);
        }

        //是否参与（0：未参与、1：已参与）
        if(partInFlg.equals("0")){

            holder.checkbox_a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //0：单选、1：多选
                    if (detail.getChooseType() == 0) {
                        if (holder.checkbox_a.isChecked()) {
                            //holder.checkbox_a.setChecked(true);
                            //holder.checkbox_b.setChecked(false);
                            //holder.checkbox_c.setChecked(false);
                            detail.setAnswerA(1);
                            detail.setAnswerB(0);
                            detail.setAnswerC(0);
                        } else {
                            //holder.checkbox_a.setChecked(true);
                            //holder.checkbox_b.setChecked(false);
                            //holder.checkbox_c.setChecked(false);
                            detail.setAnswerA(0);
                            detail.setAnswerB(1);
                            detail.setAnswerC(1);
                        }
                    } else {
                        if (holder.checkbox_a.isChecked()) {
                            holder.checkbox_a.setChecked(false);
                            detail.setAnswerA(1);
                        } else {
                            holder.checkbox_a.setChecked(true);
                            detail.setAnswerA(0);
                        }
                    }
                    notifyDataSetChanged();

                }
            });

            holder.checkbox_b.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                         //0：单选、1：多选
                         if (detail.getChooseType() == 0) {
                             if (holder.checkbox_b.isChecked()) {
                                 detail.setAnswerA(0);
                                 detail.setAnswerB(1);
                                 detail.setAnswerC(0);
                             } else {
                                 detail.setAnswerA(1);
                                 detail.setAnswerB(0);
                                 detail.setAnswerC(1);
                             }
                         } else {
                             if (holder.checkbox_b.isChecked()) {
                                 holder.checkbox_b.setChecked(false);
                                 detail.setAnswerB(1);
                             } else {
                                 holder.checkbox_b.setChecked(true);
                                 detail.setAnswerB(0);
                             }
                         }

                         notifyDataSetChanged();

                     }
                 }

            );

            holder.checkbox_c.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick(View v) {

                    //0：单选、1：多选
                    if (detail.getChooseType() == 0) {
                        if (holder.checkbox_c.isChecked()) {
                            detail.setAnswerA(0);
                            detail.setAnswerB(0);
                            detail.setAnswerC(1);
                        } else {
                            detail.setAnswerA(1);
                            detail.setAnswerB(0);
                            detail.setAnswerC(0);
                        }
                    } else {
                        if (holder.checkbox_c.isChecked()) {
                            holder.checkbox_c.setChecked(false);
                            detail.setAnswerC(1);
                        } else {
                            holder.checkbox_c.setChecked(true);
                            detail.setAnswerC(0);
                        }
                    }

                        notifyDataSetChanged();
                    }
                }

                );

            }else {
            holder.checkbox_a.setEnabled(false);
            holder.checkbox_b.setEnabled(false);
            holder.checkbox_c.setEnabled(false);
        }



        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.ques_title)//问题
        TextView ques_title;

        @InjectView(R.id.checkbox_a)
        CheckBox checkbox_a;
        @InjectView(R.id.choice_text_a)
        TextView choice_text_a;
        @InjectView(R.id.checkbox_b)
        CheckBox checkbox_b;
        @InjectView(R.id.choice_text_b)
        TextView choice_text_b;
        @InjectView(R.id.checkbox_c)
        CheckBox checkbox_c;
        @InjectView(R.id.choice_text_c)
        TextView choice_text_c;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }



}
