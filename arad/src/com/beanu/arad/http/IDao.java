package com.beanu.arad.http;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.utils.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public abstract class IDao {

    protected INetResult mResult;

    public IDao(INetResult activity) {
        this.mResult = activity;
    }

    /**
     * 得到结果后，对结果处理逻辑
     *
     * @param result
     * @param requestCode
     * @throws java.io.IOException
     */
    public abstract void onRequestSuccess(JsonNode result, int requestCode) throws IOException;


    /**
     * get请求网络，本方法提供结果的分发
     *
     * @param params
     */
    protected void getRequest(String url, Map<String, String> params) {
        _getRequest(url, params, 0);
    }

    /**
     * get请求网络，带有请求编号
     *
     * @param params
     */
    protected void getRequestForCode(String url, Map<String, String> params, int requestCode) {
        _getRequest(url, params, requestCode);
    }


    private void _getRequest(String url, Map<String, String> params, final int requestCode) {

        RequestParams ajaxParams = new RequestParams(params);
        Log.d(AsyncHttpClient.getUrlWithQueryString(true, url, ajaxParams));
        android.util.Log.d("demo","请求地址: " + AsyncHttpClient.getUrlWithQueryString(true, url, ajaxParams));
        Arad.http.get(url, ajaxParams, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                android.util.Log.d("demo","返回结果集: " + responseBody);
                try {
                    if (Arad.app.config.httpConfig != null) {
                        JsonNode node = Arad.app.config.httpConfig.handleResult(responseBody);
                        onRequestSuccess(node, requestCode);
                    }
                    mResult.onRequestSuccess(requestCode);
                } catch (AradException e) {
                    mResult.onRequestFaild(e.getError_code(), e.getMessage());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                Log.d("statusCode:" + statusCode + " Body:" + responseBody);
                if (statusCode == 0)
                    mResult.onNoConnect();
                else
                    mResult.onRequestFaild("" + statusCode, responseBody);
            }
        });

    }

    /**
     * POST 请求
     *
     * @param requestCode 自定义这是第几个post请求，用于结果的区分
     */
    public void postRequest(String url, RequestParams params, final int requestCode) {

        android.util.Log.d("demo","请求地址: " + AsyncHttpClient.getUrlWithQueryString(true, url, params));

        Arad.http.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                android.util.Log.d("demo","statusCode: " + statusCode);
                android.util.Log.d("demo","返回结果集: " + responseBody);
                try {
                    if (Arad.app.config.httpConfig != null) {
                        JsonNode node = Arad.app.config.httpConfig.handleResult(responseBody);
                        android.util.Log.d("demo","返回结果: " + node);
                        onRequestSuccess(node, requestCode);
                    }
                    mResult.onRequestSuccess(requestCode);
                } catch (AradException e) {
                    mResult.onRequestFaild(e.getError_code(), e.getMessage());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                android.util.Log.d("demo","statusCode: " + statusCode);
                if (statusCode == 0) {
                    mResult.onNoConnect();
                    android.util.Log.d("demo", "链接失败");
                    android.util.Log.d("demo", "返回结果集: " + responseBody);
                } else {
                    mResult.onRequestFaild("" + statusCode, responseBody);
                }
            }
        });

    }

    public RequestParams mapToRP(Map<String,String> map){
        RequestParams params = new RequestParams();
        Set entrySet = map.entrySet(); // key-value的set集合
        for(String key : map.keySet()){
            params.put(key,map.get(key));
            // android.util.Log.d("demo",key+ "(XXX) : " + map.get(key));
        }
        return params;
    }

}
