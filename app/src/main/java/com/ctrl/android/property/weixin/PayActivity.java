package com.ctrl.android.property.weixin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.weixin.util.MD5;
import com.ctrl.android.property.weixin.util.Util;
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
 *
 * 可以将此activity作为跳转到  微信支付的跳板
 * */
public class PayActivity extends Activity {

	//private static final String TAG = "MicroMsg.SDKSample.PayActivity";

	PayReq req;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	TextView show;
	Map<String,String> resultunifiedorder = new HashMap<>();
	StringBuffer sb;

	private String prepayId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);
		show =(TextView)findViewById(R.id.editText_prepay_id);
		req = new PayReq();
		sb=new StringBuffer();

		//1# 将App注册到微信
		msgApi.registerApp(Constant.APP_ID);

		GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
		getPrepayId.execute();





//		//生成prepay_id
//		Button payBtn = (Button) findViewById(R.id.unifiedorder_btn);
//		payBtn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
//				getPrepayId.execute();
//			}
//		});
//
		//调起微信支付
		Button appayBtn = (Button) findViewById(R.id.appay_btn);
		appayBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendPayReq();
			}
		});

		//生成签名参数
		Button appay_pre_btn = (Button) findViewById(R.id.appay_pre_btn);
		appay_pre_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				genPayReq();
				sendPayReq();
			}
		});


		String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();

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


		String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion",packageSign);
		return packageSign;
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
		Log.e("orion",appSign);
		return appSign;
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

	private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String,String>> {

		private ProgressDialog dialog;


		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(PayActivity.this, getString(R.string.app_tip), getString(R.string.getting_prepayid));
		}

		@Override
		protected void onPostExecute(Map<String,String> result) {
			if (dialog != null) {
				dialog.dismiss();
			}
			sb.append("prepay_id\n"+result.get("prepay_id")+"\n\n");
			show.setText(sb.toString());

			Log.d("demo","prepay_id: " + result.get("prepay_id"));
			Log.d("demo","trade_type: " + result.get("trade_type"));

			if(result.get("prepay_id") == null || result.get("prepay_id").equals("")){

			} else {
				resultunifiedorder = result;
				genPayReq();
				sendPayReq();
			}

			//prepayId = result.get("prepay_id");
			//Log.d("demo","prepayId: " + prepayId);
			//Log.d("demo","prepayIdXXXX: " + resultunifiedorder.get("prepay_id"));


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

			Log.e("demo","请求参数" + entity);

			byte[] buf = Util.httpPost(url, entity);

			String content = new String(buf);
			Log.e("orion", content);
			Map<String,String> xml=decodeXml(content);

			return xml;
		}
	}



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

	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}



	private String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}


	/**统一下单的请求参数*/
	private String genProductArgs() {
		StringBuffer xml = new StringBuffer();

		try {
			String	nonceStr = genNonceStr();


			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("appid", Constant.APP_ID));//公众账号id
			packageParams.add(new BasicNameValuePair("body", "纯净水"));//商品描述
			packageParams.add(new BasicNameValuePair("mch_id", Constant.MCH_ID));//商户号
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));//随机字符串
			packageParams.add(new BasicNameValuePair("notify_url", Constant.NOTICE_URL));//通知地址
			packageParams.add(new BasicNameValuePair("out_trade_no",genOutTradNo()));//商户订单号
			packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));//终端IP
			packageParams.add(new BasicNameValuePair("total_fee", "10"));//总金额  只能为整数
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));//交易类型


			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));//签名


			String xmlstring =toXml(packageParams);

			// 这一步最关键 我们把字符转为 字节后,再使用“ISO8859-1”进行编码，得到“ISO8859-1”的字符串
			return new String(xmlstring.toString().getBytes(), "ISO8859-1");
			//return xmlstring;

		} catch (Exception e) {
			//Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
			Log.d("demo","genProductArgs fail, ex = " + e.getMessage());
			return null;
		}


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

		show.setText(sb.toString());

		//sendPayReq();

//        Button appayBtn = (Button) findViewById(R.id.appay_btn);
//        appayBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendPayReq();
//            }
//        });

		Log.e("orion", signParams.toString());

	}

	private void sendPayReq() {
		msgApi.registerApp(Constant.APP_ID);
		msgApi.sendReq(req);
		Log.d("demo","APP_ID : " + Constant.APP_ID);
	}


}
