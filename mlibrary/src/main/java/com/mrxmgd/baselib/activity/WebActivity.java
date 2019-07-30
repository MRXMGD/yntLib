/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.mrxmgd.baselib.R;
import com.mrxmgd.baselib.base.BaseActivity;


/**
 * 网页详情
 * String url  详情地址
 *
 * @author MRXMGD <mrxmgd@gmail.com>
 */
public class WebActivity extends BaseActivity {
    String url = "http://www.baidu.com";
    AgentWeb mAgentWeb;


    @Override
    protected int setLayout() {
        setLeftImage(R.drawable.ic_back);
        setTitle(getString(R.string.loadingText));
        return 0;
    }

    @Override
    protected void Init(Bundle savedInstanceState) {
        url = getIntent().getStringExtra("url");
        Log.e("loge", url);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(baseDataBinding.layoutContainer, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(android.webkit.WebView view, WebResourceRequest request) {
                        return super.shouldOverrideUrlLoading(view, request);
                    }


                }).setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        baseDataBinding.setTitle(title);
                    }
                })
                .createAgentWeb()
                .ready()
                .go(url);
        mAgentWeb.getWebCreator().getWebView().setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                // H5中包含下载链接的话让外部浏览器去处理
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null)
            if (mAgentWeb.handleKeyEvent(keyCode, event)) {
                return true;
            }
        return super.onKeyDown(keyCode, event);
    }

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

}
