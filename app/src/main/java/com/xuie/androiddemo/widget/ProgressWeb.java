package com.xuie.androiddemo.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ProgressWeb extends SwipeRefreshLayout {
    WebView mWebView;

    public ProgressWeb(Context context, AttributeSet attrs) {
        super(context, attrs);
        mWebView = new WebView(context);
        addView(mWebView);
        initWebView();
    }

    public void initWebView() {
        setWebChromeClient(new ProgressWebChromeClient(this));
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            mWebView.loadUrl(mWebView.getUrl());
            }
        });

    }

    public WebView getWebView() {
        return mWebView;
    }

    public void setWebChromeClient(WebChromeClient wenChromeClient) {
        mWebView.setWebChromeClient(wenChromeClient);
    }

    public void setWebClient(WebViewClient webClient) {
        mWebView.setWebViewClient(webClient);
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public class ProgressWebChromeClient extends WebChromeClient {
        ProgressWeb mProgressWeb;

        public ProgressWebChromeClient(ProgressWeb mProgressWeb) {
            this.mProgressWeb = mProgressWeb;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressWeb.setRefreshing(false);
            } else if (!mProgressWeb.isRefreshing()) mProgressWeb.setRefreshing(true);

            super.onProgressChanged(view, newProgress);
        }
    }
}
