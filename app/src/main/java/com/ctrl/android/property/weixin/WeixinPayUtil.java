package com.ctrl.android.property.weixin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;

import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.weixin.util.MD5;
import com.ctrl.android.property.weixin.util.Util;
import com.ctrl.android.property.wxapi.WXPayEntryActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 微信支付  工具类
 * Created by Eric on 2015/7/30.
 */
public class WeixinPayUtil {

    private Activity mActivity;

    PayReq req;
    TextView show;
    Map<String,String> resultunifiedorder = new HashMap<>();
    StringBuffer sb;

    private String orderIds;
    private String url;
    private String proName;
    private int totalFee;
    final IWXAPI msgApi;

    public WeixinPayUtil(Activity activity){
        this.mActivity = activity;
        msgApi = WXAPIFactory.createWXAPI(mActivity, null);
        req = new PayReq();
        sb=new StringBuffer();
    }

    public WeixinPayUtil(Activity activity,WXPayEntryActivity wxPayEntryActivity){
        this.mActivity = activity;
        msgApi = WXAPIFactory.createWXAPI(mActivity, null);
        req = new PayReq();
        sb=new StringBuffer();
    }



    /**
     * 1 将App注册到微信
     * */
    public void registeAppToWeixin(){
        msgApi.registerApp(Constant.APP_ID);
    }

    /**
     * 2 生成预付订单
     * */
    public void buildPrePayOrder(){
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();
    }

    /**
     * 3 生成微信支付参数
     * */
    public void buildWeixinPayArgs(){
        genPayReq();
    }

    /**
     * 4 调起微信支付
     * */
    public void sendPayArgsToWeixin(){
        sendPayReq();
    }


    /**
     * 支付函数
     * @param orderIds 订单id
     * @param proName 商品名称
     * @param totalFee 订单支付总额(单位:分)
     * */
    public void pay(String orderIds, String proName,int totalFee){
        this.orderIds = orderIds;
        this.proName = proName;
        this.totalFee = totalFee;
        registeAppToWeixin();
        buildPrePayOrder();
        //buildWeixinPayArgs();
        //sendPayArgsToWeixin();
    }

    /**
     * 支付函数
     * @param orderIds 订单id
     * @param proName 商品名称
     * @param totalFee 订单支付总额(单位:分)
     * */
    public void pay(String orderIds, String url,String proName,int totalFee){
        this.orderIds = orderIds;
        this.url = url;
        this.proName = proName;
        this.totalFee = totalFee;
        registeAppToWeixin();
        buildPrePayOrder();
        //buildWeixinPayArgs();
        //sendPayArgsToWeixin();
    }


    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String,String>> {

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(mActivity, "提示", "正在获取预支付订单...");
        }

        @Override
        protected void onPostExecute(Map<String,String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            sb.append("prepay_id\n"+result.get("prepay_id")+"\n\n");

            Log.d("demo", "return_code : " + result.get("return_code"));
            Log.d("demo", "result_code: " + result.get("result_code"));

            Log.d("demo", "prepay_id: " + result.get("prepay_id"));
            Log.d("demo","trade_type: " + result.get("trade_type"));

            resultunifiedorder = result;

            buildWeixinPayArgs();
            Log.d("demo","result: " + result);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String,String>  doInBackground(Void... params) {

            //2# 统一下单的URL地址 生成预支付交易单
            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();

            Log.e("demo","请求参数: " + entity);

            byte[] buf = Util.httpPost(url, entity);

            String content = new String(buf);
            Log.e("demo", "返回结果: " + content);
            Map<String,String> xml = decodeXml(content);

            return xml;
        }
    }

    /**统一下单的请求参数*/
    private String genProductArgs() {
        StringBuffer xml = new StringBuffer();

        try {
            String	nonceStr = genNonceStr();

            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", Constant.APP_ID));//公众账号id
            packageParams.add(new BasicNameValuePair("body", proName));//商品描述
            packageParams.add(new BasicNameValuePair("mch_id", Constant.MCH_ID));//商户号
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));//随机字符串
            packageParams.add(new BasicNameValuePair("notify_url", url));//通知地址

            //packageParams.add(new BasicNameValuePair("attach", "订单说明"));//订单说明
            packageParams.add(new BasicNameValuePair("out_trade_no",orderIds));//商户订单号(具体问题具体分析)
            //packageParams.add(new BasicNameValuePair("out_trade_no",genOutTradNo()));//商户订单号(具体问题具体分析)
            packageParams.add(new BasicNameValuePair("spbill_create_ip","8.8.8.8"));//终端IP
            packageParams.add(new BasicNameValuePair("total_fee", String.valueOf(totalFee)));//总金额  只能为整数
            Log.d("demo","总价: " + String.valueOf(totalFee));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));//交易类型

            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));//签名

            String xmlstring = toXml(packageParams);

            Log.d("demo","XML: " + new String(xmlstring.toString().getBytes(), "ISO8859-1"));

            // 这一步最关键 我们把字符转为 字节后,再使用“ISO8859-1”进行编码，得到“ISO8859-1”的字符串
            return new String(xmlstring.toString().getBytes(), "ISO8859-1");
            //return xmlstring;

        } catch (Exception e) {
            //Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
            Log.d("demo","genProductArgs fail, ex = " + e.getMessage());
            return null;
        }


    }

    /**
     生成签名
     */
    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constant.API_KEY);
        Log.d("demo","SB: " + sb.toString());
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        //String packageSign = (MD5Util.MD5Encode(sb.toString(),"UTF-8")).toUpperCase();
        Log.e("demo","packageSign: " + packageSign);
        return packageSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<"+params.get(i).getName()+">");
            sb.append(params.get(i).getValue());
            sb.append("</"+params.get(i).getName()+">");
        }
        sb.append("</xml>");

        Log.e("orion",sb.toString());
        return sb.toString();
    }

    /**
     * 转成 XML 格式
     * */
    public Map<String,String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName=parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if("xml".equals(nodeName)==false){
                            //实例化student对象
                            xml.put(nodeName,parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion",e.toString());
        }
        return null;

    }

    /**
     * 获取一个随机 字符串
     * */
    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 获取商户自己的 订单号
     * (具体问题具体分析, 此处仅杜撰了一个方法)
     * */
    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private void genPayReq() {

        req.appId = Constant.APP_ID;
        req.partnerId = Constant.MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());


        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n"+req.sign+"\n\n");

        //show.setText(sb.toString());

        sendPayArgsToWeixin();

        Log.e("demo", "XXXX: " + sb.toString());

    }

    private void sendPayReq() {
        msgApi.registerApp(Constant.APP_ID);//此行代码是为了避免 忘记把app注册到微信
        Log.d("demo","APP_ID : " + Constant.APP_ID);
        msgApi.sendReq(req);
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constant.API_KEY);

        this.sb.append("sign str\n"+sb.toString()+"\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        //String appSign = (MD5Util.MD5Encode(sb.toString(),"UTF-8")).toUpperCase();
        Log.e("orion",appSign);
        return appSign;
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }
}










