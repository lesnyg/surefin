package com.jubumam.surefin.ProtectorPackage;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jubumam.surefin.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.net.URISyntaxException;
import java.text.DecimalFormat;

/******************************************************************************
 *
 *	@ SYSTEM NAME		: SM-PAY 결제연동 Sample
 *	@ PROGRAM NAME		: SMPayWebSampleAutoActivity.java
 *	@ MAKER				: jjimbba
 *	@ MAKE DATE			: 2012.09.06
 *	@ PROGRAM CONTENTS	: SM-PAY 결제연동 Sample
 *
 ************************** 변 경 이 력 *****************************************
 * 번호	작업자		작업일				변경내용
 *	1	스마트로	2012.09.06		최초 페이지 작성
 *	2	황예은  	2019.11.21		onActivityResult 메소드 삭제
 *******************************************************************************/


public class SMPayWebSampleAutoActivity extends Activity {

    private WebView mWebView; // 쇼핑몰 WebView
    private String myURL;


    // ////////////////////////////////////////////////
    //
    // 결제수단별 결제 APP을 구분하기 위한 상수값
    // (해당 값은 변경하지 마세요.)
    //
    // ////////////////////////////////////////////////
    final int ISP_CALL = 1;
    final int BANK_CALL = 2;
    final int SUCCESS = 0;
    final int FAIL = 1;

    private String TID = "";

    private SslErrorHandler errorHandler;

    private String resultString = null;
    private String dataURI[] = {
            "http://market.android.com",
            "mvaccine",
            "vguard",
            "droidxantivirus",
            "smhyundaiansimclick://",
            "smshinhanansimclick://",
            "smshinhancardusim://",
            "kebcard",
            "smartwall://",
            "appfree://",
            "market://",
            "v3mobile",
            ".apk",
            "ansimclick",
            "http://m.ahnlab.com/kr/site/download",
            "cloudpay",
            "com.lotte.lottesmartpay",
            "lottesmartpay://",
            "lottecard",
            "com.hanaskcard.paycla",
            "citispayapp",
            "hanaansim",
            "com.ahnlab.v3mobileplus",
            "citicardapp",
            "com.TouchEn.mVaccine.webs",
            "nhallonepayansimclick://",
            "smartpay",
            "mvaccinestart",
            "kb-acp"
    };
    private int totalPrice;
    private String strtotalPrice;
    private String recipiName;
    private String recipiPhone;
    private String param;


    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smpay_web_auto_sample);

        // ////////////////////////////////////////////////
        //
        // WebView 셋팅 - 아래 설정값은 필수로 설정합니다.
        //
        // /////////////////////////////////////////////////

        Intent intent = getIntent();
        totalPrice = intent.getIntExtra("totalPrice", 0);
        recipiName = intent.getStringExtra("recipiName");
        recipiPhone = intent.getStringExtra("recipiPhone");
        strtotalPrice = new DecimalFormat("###,###").format(totalPrice);
//        myURL = "http://192.168.0.12:8080/Example2_Servlet/mainMobilePay.jsp?totalPrice="+totalPrice+"&recipiName="+recipiName+"&recipiPhone="+recipiPhone;
//        myURL = "http://192.168.0.12:8080/Example2_Servlet/mainMobilePay.jsp";
//        myURL = "http://119.194.5.109:80/smartrotest/mainMobilePay.jsp?totalPrice="+totalPrice+"&recipiName="+recipiName+"&recipiPhone="+recipiPhone;
        myURL = "http://surefin1.cafe24.com:8085/smartrotest/mainMobilePay.jsp?totalPrice=" + totalPrice + "&recipiName=" + recipiName + "&recipiPhone=" + recipiPhone;
//        myURL = "http://surefin1.cafe24.com:8085/smartrotest/mainMobilePay.jsp";
//        param = "totalPrice="+totalPrice+"&"+"recipiName="+recipiName+"&"+"recipiPhone="+recipiPhone;

        mWebView = (WebView) findViewById(R.id.webview);

        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JavaScriptInterface(this),"android");
//        mWebView.getSettings().setPluginsEnabled(true);



        getHttpStrData();

        // WebView 설정
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new SMPayViewClient());
        mWebView.loadUrl(myURL);
//        mWebView.postUrl(myURL, EncodingUtils.getBytes(param,"BASE64"));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_smpay_web_sample, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    /**
     * APP 설치체크
     *
     * @param searchApp
     * @return
     */
    private boolean installCheck(String searchApp) {
        try {
            PackageManager pm = getPackageManager();
            if ("ISP".equals(searchApp)) {
                pm.getPackageInfo("kvp.jjy.MispAndroid320", PackageManager.GET_META_DATA);
            } else if ("bankpay".equals(searchApp)) {
                pm.getPackageInfo("com.kftc.bankpay.android", PackageManager.GET_META_DATA);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    private void getHttpStrData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URLSet.TEST_REQUEST_URI_URL, asynResponse);
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case SUCCESS:
                    dataURI = null;
                    dataURI = resultString.split(",");
                    mWebView.loadUrl(myURL);
                    break;
                case FAIL:
                    mWebView.loadUrl(URLSet.Test_StopURL);
                    break;
            }
            super.handleMessage(msg);
        }
    };


    /**
     * response
     */
    private AsyncHttpResponseHandler asynResponse = new AsyncHttpResponseHandler() {
        @Override
        public void onStart() {
            // TODO Auto-generated method stub
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
            resultString = byteToString(responseBody);
            handler.sendEmptyMessage(SUCCESS);
        }

        @Override
        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
            handler.sendEmptyMessage(FAIL);
        }


        @Override
        public void onRetry(int retryNo) {
            // TODO Auto-generated method stub
            super.onRetry(retryNo);
        }

    };

    /**
     * byte 배열을  String 으로 변환
     *
     * @param data
     * @return
     */
    public String byteToString(byte[] data) {
        return new String(data);
    }

    public boolean checkUri(String url) {
        boolean result = false;
        if (url != null) {
            if (dataURI == null) {
                return result;
            }

            for (int i = 0; i < dataURI.length; i++) {
                dataURI[i] = dataURI[i].replace(System.getProperty("line.separator"), "");
                if (url.contains(dataURI[i])) {
                    if (dataURI[i].equals(".apk")) {
                        if (url.endsWith(dataURI[i])) {
                            result = true;
                        } else {
                            result = false;
                        }
                    } else {
                        result = true;
                    }

                    break;
                }
            }
        }
        return result;
    }


    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //진행
                    errorHandler.proceed();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    //중단
                    errorHandler.cancel();
                    break;
            }

        }
    };

    /**
     * 신용카드 결제시 ISP 및 안심클릭 MPI, 금결원 계좌이체 APP 구동을 위한 부분
     *
     * @author jjimbba
     */
    private class SMPayViewClient extends WebViewClient {
        @Override
        public void onReceivedSslError(final WebView view, final SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }


        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent = null;
            if (url.contains("ispmobile")) { // ISP 결제시 ISP 인증 APP 호출

                String[] arrTid = url.split("=");
                TID = arrTid[arrTid.length - 1];

                if (installCheck("ISP")) {
                    Intent isp = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivityForResult(isp, ISP_CALL);

                } else { // ISP APP 이 설치 되지 않았을 경우 Market으로 연결
                    Uri uri = Uri.parse("market://details?id=kvp.jjy.MispAndroid320");
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                return true;

            } else if (url.contains("bankpay")) {
                try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                if (url.startsWith("intent")) { // chrome 버젼 방식 // 앱설치 체크를 합니다.
                    if (getPackageManager().resolveActivity(intent, 0) == null) {
                        String packagename = intent.getPackage();
                        if (packagename != null) {
                            Uri uri = Uri.parse("market://details?id=" + packagename); // 마켓으로 바로 이동
                            intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                            return true;
                        }
                    } else { // 앱이 설치되어 있으면
                        int runType = Integer.parseInt(android.os.Build.VERSION.SDK);
                        if (runType <= 18) {
                            Uri uri = Uri.parse(intent.getDataString());
                            intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } else {
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setComponent(null);
                            try {
                                if (startActivityIfNeeded(intent, -1)) {
                                    return true;
                                }
                            } catch (ActivityNotFoundException ex) {
                                return false;
                            }
                        }

                    }
                } else { // 구 방식
                    Uri uri = Uri.parse(url);
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }

                return true;
            } else if (checkUri(url)) {
                Log.d("url  ", url);
                try {

                    try {
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    } catch (URISyntaxException ex) {
                        return false;
                    }
                    if (url.startsWith("intent")) { // chrome 버젼 방식 // 앱설치 체크를 합니다.

                        if (getPackageManager().resolveActivity(intent, 0) == null) {
                            String packagename = intent.getPackage();
                            if (packagename != null) {
                                Uri uri = Uri.parse("market://search?q=pname:" + packagename);
                                intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                                return true;
                            }
                        }
                        int runType = Integer.parseInt(android.os.Build.VERSION.SDK);
                        if (runType <= 18) {
                            Uri uri = Uri.parse(intent.getDataString());
                            intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } else {
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setComponent(null);
                            try {
                                if (startActivityIfNeeded(intent, -1)) {
                                    return true;
                                }
                            } catch (ActivityNotFoundException ex) {
                                return false;
                            }
                        }
                    } else { // 구 방식
                        Uri uri = Uri.parse(url);
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                    return true;
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    return false;
                }
            } else if (url.startsWith("tel:")) {
                Intent phone = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                // 현재의 activity 에 대해 startActivity 호출
                startActivity(phone);
                return true;
            } else {
                return false;
            }
        }
    }

    private class JavaScriptInterface {
        Context mContext;
        JavaScriptInterface(Context c){
            mContext = c;
        }

        @JavascriptInterface
        public void webview_finish(){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
        }
    }
}
