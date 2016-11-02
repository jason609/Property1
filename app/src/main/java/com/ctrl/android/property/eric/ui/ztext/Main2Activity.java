package com.ctrl.android.property.eric.ui.ztext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ctrl.android.property.R;

public class Main2Activity extends Activity {

	private WebView contentWebView = null;
	private TextView msgView = null;

	@JavascriptInterface
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		contentWebView = (WebView) findViewById(R.id.webview);
		msgView = (TextView) findViewById(R.id.msg);
		contentWebView.getSettings().setJavaScriptEnabled(true);
		contentWebView.loadUrl("file:///android_asset/wst.html");

		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(btnClickListener);
		contentWebView.addJavascriptInterface(this, "wst");
	}

	OnClickListener btnClickListener = new OnClickListener() {
		public void onClick(View v) {
			contentWebView.loadUrl("javascript:javacalljs()");
			contentWebView.loadUrl("javascript:javacalljswithargs(" + "'hello world'" + ")");
		}
	};

	public void startFunction() {
		Toast.makeText(this, "js������java����", Toast.LENGTH_SHORT).show();
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				msgView.setText(msgView.getText() + "\njs������java����");

			}
		});
	}

	public void startFunction(final String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				msgView.setText(msgView.getText() + "\njs������java����ݲ���" + str);

			}
		});
	}
}