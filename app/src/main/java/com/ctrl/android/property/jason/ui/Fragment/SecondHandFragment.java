package com.ctrl.android.property.jason.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beanu.arad.base.ToolBarFragment;
import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.jason.dao.UsedGoodsDao;
import com.ctrl.android.property.jason.entity.UsedGoods;
import com.ctrl.android.property.jason.ui.adapter.SecondHandBuyAdapter;
import com.ctrl.android.property.jason.ui.adapter.SecondHandTransferAdapter;
import com.ctrl.android.property.jason.ui.secondmarket.BabyDetailActivity;
import com.ctrl.android.property.jason.ui.secondmarket.DetailToBuyActivity;
import com.ctrl.android.property.jason.util.StrConstant;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 二手交易 fragment
 * Created by jason on 2015/10/12.
 */
public class SecondHandFragment extends ToolBarFragment implements View.OnClickListener {
    @InjectView(R.id.pull_to_refresh_listView002)
    PullToRefreshListView pull_to_refresh_listView002;//可刷新的列表
    private ListView mListView;
    private SecondHandTransferAdapter secondHandTransferAdapter;
    private String handType; //交易类型
    private UsedGoodsDao dao;
    private UsedGoods usedGoods;
    private SecondHandTransferAdapter adapter1;
    private SecondHandBuyAdapter adapter2;
    private int currentPage=1;
    private int currentTotalItem=1;
    private int rowCountPerPage=10;
    private String mKindId;
    int bol=1;



    public static SecondHandFragment newInstance(String type,String mKindId){
        SecondHandFragment fragment=new SecondHandFragment();
        fragment.handType=type;
        fragment.mKindId=mKindId;
        return fragment;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
       /* if (isVisibleToUser) {
            //相当于Fragment的onResume

                //
            if(getActivity().getIntent().getFlags()==StrConstant.GET_OWNER_LIST){
                if(handType==StrConstant.SECOND_HAND_TRANSFER){
                    if(mKindId==null ||mKindId.equals("")) {
                        dao.requestGoodsList(handType, "", AppHolder.getInstance().getProprietor().getProprietorId(), String.valueOf(currentPage));
                    }else {
                        currentPage=1;
                        dao.requestGoodsList(handType,mKindId,AppHolder.getInstance().getProprietor().getProprietorId(),String.valueOf(currentPage));
                        currentTotalItem=1;

                    }
                }
                if(handType==StrConstant.SECOND_HAND_BUY){
                    if(mKindId==null ||mKindId.equals("")) {
                        dao.requestGoodsList(handType,"",AppHolder.getInstance().getProprietor().getProprietorId(), String.valueOf(currentPage));
                    }else {
                        currentPage=1;
                        dao.requestGoodsList(handType,mKindId,AppHolder.getInstance().getProprietor().getProprietorId(),String.valueOf(currentPage));
                        currentTotalItem=1;

                    }
                }


            }else{
                if(handType==StrConstant.SECOND_HAND_TRANSFER ) {
                    if(mKindId==null ||mKindId.equals("")) {
                        dao.requestGoodsList(handType,"","" ,String.valueOf(currentPage));
                    }else {
                        currentPage=1;
                        dao.requestGoodsList(handType,mKindId,"",String.valueOf(currentPage));
                        currentTotalItem=1;
                    }

                }
                if(handType==StrConstant.SECOND_HAND_BUY ) {
                    if(mKindId==null ||mKindId.equals("")) {
                        dao.requestGoodsList(handType,"", "",String.valueOf(currentPage));
                    }else {
                        currentPage=1;
                        dao.requestGoodsList(handType,mKindId,"",String.valueOf(currentPage));
                        currentTotalItem=1;
                    }
                }
            }



            //相当于Fragment的onPause
        }  else {

    }*/

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao=new UsedGoodsDao(this);
        bol=1;
    }


    public void setKindId(String kindId){
        this.mKindId=kindId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second_hand,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  Log.i("Tag", "Tag" + "onViewCreated被调用");
        ButterKnife.inject(this,view);
        if(bol==1) {
            currentPage=1;
            currentTotalItem=1;
            if (getActivity().getIntent().getFlags() == StrConstant.GET_OWNER_LIST) {
                if (handType == StrConstant.SECOND_HAND_TRANSFER&&mKindId==null) {
                    if (mKindId == null) {
                        dao.requestGoodsList(handType, "", AppHolder.getInstance().getProprietor().getProprietorId(), String.valueOf(currentPage));
                    } else {
                        currentPage = 1;
                        dao.requestGoodsList(handType, mKindId, AppHolder.getInstance().getProprietor().getProprietorId(), String.valueOf(currentPage));
                        currentTotalItem = 1;
                    }
                }
                if (handType == StrConstant.SECOND_HAND_BUY&&mKindId==null) {
                    if(mKindId==null) {
                        dao.requestGoodsList(handType, "", AppHolder.getInstance().getProprietor().getProprietorId(), String.valueOf(currentPage));
                    }else {
                        currentPage = 1;
                        dao.requestGoodsList(handType, mKindId, AppHolder.getInstance().getProprietor().getProprietorId(), String.valueOf(currentPage));
                        currentTotalItem = 1;
                    }
                }

            } else {
                if (handType == StrConstant.SECOND_HAND_TRANSFER) {
                    if(mKindId==null) {
                        dao.requestGoodsList(handType, "", "", String.valueOf(currentPage));
                    }else {
                        currentPage = 1;
                        dao.requestGoodsList(handType, mKindId, "", String.valueOf(currentPage));
                        currentTotalItem = 1;
                    }
                }
                if (handType == StrConstant.SECOND_HAND_BUY) {
                    if(mKindId==null) {
                        dao.requestGoodsList(handType, "", "", String.valueOf(currentPage));
                    }else {
                        currentPage = 1;
                        dao.requestGoodsList(handType, mKindId, "", String.valueOf(currentPage));
                        currentTotalItem = 1;
                    }

            }

        }
    }
    }

    @Override
    public void onClick(View v) {

    }


/*
* 请求数据成功后调用此方法
* */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);

        if(requestCode==2){
            bol=0;
            if(getActivity().getIntent().getFlags()==StrConstant.GET_OWNER_LIST){

                if(handType==StrConstant.SECOND_HAND_TRANSFER) {
                    mListView=pull_to_refresh_listView002.getRefreshableView();
                    adapter1 =new SecondHandTransferAdapter(getActivity());
                    adapter1.setList(dao.getUsedGoodsList());
                    mListView.setAdapter(adapter1);
                    mListView.setSelection(currentTotalItem);
                    pull_to_refresh_listView002.setMode(PullToRefreshBase.Mode.BOTH);
                    pull_to_refresh_listView002.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                        @Override
                        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                            currentPage = 1;
                            dao.getUsedGoodsList().clear();
                            if(mKindId==null ||mKindId.equals("")) {
                                dao.requestGoodsList(handType, "", AppHolder.getInstance().getProprietor().getProprietorId(), String.valueOf(currentPage));
                            }else {
                                dao.requestGoodsList(handType,mKindId,AppHolder.getInstance().getProprietor().getProprietorId(),String.valueOf(currentPage));
                            }
                            }

                        @Override
                        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                            currentPage = currentPage + 1;
                            if(mKindId==null ||mKindId.equals("")) {
                                dao.requestGoodsList(handType, "", AppHolder.getInstance().getProprietor().getProprietorId(), String.valueOf(currentPage));
                            }else {
                                dao.requestGoodsList(handType,mKindId,AppHolder.getInstance().getProprietor().getProprietorId(),String.valueOf(currentPage));
                            }
                            currentTotalItem+=rowCountPerPage;
                        }
                    });
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            usedGoods = dao.getUsedGoodsList().get(position - 1);
                            Intent intent = new Intent(getActivity().getApplicationContext(), BabyDetailActivity.class);
                            intent.putExtra("usedGoodsId", usedGoods.getId());
                            intent.putExtra("position",position);
                            intent.addFlags(StrConstant.MY_BABY_DETAIL);
                            intent.putExtra("releaseTime", usedGoods.getCreateTime());
                            Log.i("Tag", "releaseTime" + usedGoods.getCreateTime());
                            Log.i("usedGoodsId", "jason" + usedGoods.getId());
                            Log.i("releaseTime", "jason" + usedGoods.getCreateTime());
                            startActivityForResult(intent, 1008);
                            AnimUtil.intentSlidIn(getActivity());
                        }
                    });




                }else if(handType==StrConstant.SECOND_HAND_BUY){
                    mListView=pull_to_refresh_listView002.getRefreshableView();
                    pull_to_refresh_listView002.setMode(PullToRefreshBase.Mode.BOTH);
                    pull_to_refresh_listView002.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                        @Override
                        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                            currentPage = 1;
                            dao.getUsedGoodsList().clear();
                            if(mKindId==null ||mKindId.equals("")) {
                                dao.requestGoodsList(handType, "", AppHolder.getInstance().getProprietor().getProprietorId(), String.valueOf(currentPage));
                            }else {
                                dao.requestGoodsList(handType,mKindId,AppHolder.getInstance().getProprietor().getProprietorId(),String.valueOf(currentPage));
                            }
                        }

                        @Override
                        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                            currentPage += 1;
                            if(mKindId==null ||mKindId.equals("")) {
                                dao.requestGoodsList(handType, "", AppHolder.getInstance().getProprietor().getProprietorId(), String.valueOf(currentPage));
                            }else {
                                dao.requestGoodsList(handType,mKindId,AppHolder.getInstance().getProprietor().getProprietorId(),String.valueOf(currentPage));
                            }
                            currentTotalItem += rowCountPerPage;

                        }
                    });
                   adapter2=new SecondHandBuyAdapter(getActivity());
                    adapter2.setList(dao.getUsedGoodsList());
                    mListView.setAdapter(adapter2);
                    mListView.setSelection(currentTotalItem);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            usedGoods = dao.getUsedGoodsList().get(position - 1);
                            Intent intent = new Intent(getActivity().getApplicationContext(), DetailToBuyActivity.class);
                            intent.putExtra("usedGoodsId", usedGoods.getId());
                            intent.putExtra("position",position);
                            Log.i("Tag", "Tag1000" + position);
                            intent.addFlags(StrConstant.MY_BABY_BUY_DETAIL);
                            intent.putExtra("releaseTime", usedGoods.getCreateTime());
                            startActivityForResult(intent, 1002);
                            AnimUtil.intentSlidIn(getActivity());

                        }
                    });
                }

            }else {

                //MessageUtils.showShortToast(getActivity(), "已连接成功");
                if (handType == StrConstant.SECOND_HAND_TRANSFER) {
                    mListView = pull_to_refresh_listView002.getRefreshableView();
                    adapter1 = new SecondHandTransferAdapter(getActivity());
                    adapter1.setList(dao.getUsedGoodsList());
                    mListView.setAdapter(adapter1);
                    if(currentTotalItem>=rowCountPerPage) {
                        mListView.setSelection(currentTotalItem);
                    }
                    pull_to_refresh_listView002.setMode(PullToRefreshBase.Mode.BOTH);
                    pull_to_refresh_listView002.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                        @Override
                        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                            currentPage = 1;
                            dao.getUsedGoodsList().clear();
                            if(mKindId==null ||mKindId.equals("")) {
                                dao.requestGoodsList(handType, "", "", String.valueOf(currentPage));
                            }else {
                                dao.requestGoodsList(handType,mKindId,"",String.valueOf(currentPage));

                            }

                        }

                        @Override
                        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                            currentPage = currentPage + 1;
                            if(mKindId==null ||mKindId.equals("")) {
                                dao.requestGoodsList(handType, "", "", String.valueOf(currentPage));
                            }else {
                                dao.requestGoodsList(handType,mKindId,"",String.valueOf(currentPage));

                            }
                            currentTotalItem+=rowCountPerPage;



                        }



                    });
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            usedGoods = dao.getUsedGoodsList().get(position - 1);
                            Intent intent = new Intent(getActivity().getApplicationContext(), BabyDetailActivity.class);
                            intent.putExtra("usedGoodsId", usedGoods.getId());
                            intent.putExtra("releaseTime", usedGoods.getCreateTime());
                            Log.i("Tag", "releaseTime" + usedGoods.getCreateTime());
                            Log.i("usedGoodsId", "jason" + usedGoods.getId());
                            Log.i("releaseTime", "jason" + usedGoods.getCreateTime());
                            startActivity(intent);
                            AnimUtil.intentSlidIn(getActivity());
                        }
                    });


                } else if (handType == StrConstant.SECOND_HAND_BUY) {
                    mListView = pull_to_refresh_listView002.getRefreshableView();
                    pull_to_refresh_listView002.setMode(PullToRefreshBase.Mode.BOTH);
                    pull_to_refresh_listView002.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                        @Override
                        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                            currentPage = 1;
                            dao.getUsedGoodsList().clear();
                            if(mKindId==null ||mKindId.equals("")) {
                                dao.requestGoodsList(handType, "", "", String.valueOf(currentPage));
                            }else {
                                dao.requestGoodsList(handType,mKindId,"",String.valueOf(currentPage));
                            }
                        }

                        @Override
                        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                            currentPage += 1;
                            if(mKindId==null ||mKindId.equals("")) {
                                dao.requestGoodsList(handType, "", "",String.valueOf(currentPage));
                            }else {
                                dao.requestGoodsList(handType,mKindId,"",String.valueOf(currentPage));
                            }
                            currentTotalItem += rowCountPerPage;

                        }
                    });
                    adapter2 = new SecondHandBuyAdapter(getActivity());
                    adapter2.setList(dao.getUsedGoodsList());
                    mListView.setAdapter(adapter2);
                    if(currentTotalItem>=rowCountPerPage) {
                        mListView.setSelection(currentTotalItem);
                    }
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            usedGoods = dao.getUsedGoodsList().get(position - 1);
                            Intent intent = new Intent(getActivity().getApplicationContext(), DetailToBuyActivity.class);
                            intent.putExtra("usedGoodsId", usedGoods.getId());
                            intent.putExtra("releaseTime", usedGoods.getCreateTime());
                            startActivity(intent);
                            AnimUtil.intentSlidIn(getActivity());
                        }
                    });
                }
            }
            pull_to_refresh_listView002.onRefreshComplete();
        }
        }


    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
       // super.onRequestFaild(errorNo, errorMessage);
        if(errorNo.equals("002")){
            //
        }
        pull_to_refresh_listView002.onRefreshComplete();
       // MessageUtils.showShortToast(getActivity(),"请检查网络");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
            case 1002:
                if (resultCode == 1003) {
                    // MessageUtils.showShortToast(getActivity(),"213123123");
                    int position = data.getIntExtra("position", 0);
                    dao.getUsedGoodsList().remove(position - 1);
                    adapter2.notifyDataSetChanged();

                }
                break;
            case 1008:
                if (resultCode == 1009) {
                    // MessageUtils.showShortToast(getActivity(), "213123123");
                    int position = data.getIntExtra("position", -1);
                    Log.i("Tag", "Tag121313" + position);
                    dao.getUsedGoodsList().remove(position - 1);
                    adapter1.notifyDataSetChanged();

                }
                break;

        }

    }catch (Exception e){
        e.printStackTrace();
        }
    }


/*    class UpdateListBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
                Log.i("Tag","Tag"+"88888888");
            dao.requestGoodsList(handType, "", "", String.valueOf(currentPage));
        }
    }*/

}



    /**
     * 测试 获取数据的方法
     * */
/*    private List<Map<String,String>> getListMap(String str){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 20 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("name","贾飞龙" +i);
            map.put("time","两分钟前" );
            map.put("location","中润世纪城"+i);
            map.put("price","$25.00"+i);
            list.add(map);
        }
        return list;
    }*/


