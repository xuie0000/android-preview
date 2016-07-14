package com.xuie.androiddemo.ui.activity.login;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mikepenz.iconics.IconicsDrawable;
import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.OAuthUrl;
import com.xuie.androiddemo.R;
import com.xuie.androiddemo.ui.activity.login.presenter.LoginPresenter;
import com.xuie.androiddemo.ui.activity.BaseActivity;
import com.xuie.androiddemo.util.ToastUtil;
import com.xuie.androiddemo.widget.ProgressWeb;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity<LoginActivity, LoginPresenter> implements ILoginActivity {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.webView) ProgressWeb webView;

    private LoginPresenter mLoginPresenter;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter = getPresenter();
        init();
    }

    private void init() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(new IconicsDrawable(this).icon("gmi_arrow_back").sizeDp(16).color(Color.WHITE));
        mToolbar.setNavigationOnClickListener(v -> {
            ToastUtil.Toast(getString(R.string.login_failed));
            setResult(RESULT_CANCELED);
            LoginActivity.this.finish();
        });

        webView.getWebView().setWebViewClient(new WebViewClient() {
            String authCode;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                ToastUtil.Toast("登录失败请稍后重试\n" + error.toString());
            }

            @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Logger.d(url);

                if (url.contains("?code") && url.contains(OAuthUrl.OAUTH_STATE)) {
                    Uri uri = Uri.parse(url);
                    authCode = uri.getQueryParameter("code");
                    Logger.d("code:" + authCode);
                    //选择动画处理还是直接结束
                    ToastUtil.Toast("正在登陆,请稍后...");
                    webView.getWebView().setVisibility(View.GONE);
                    webView.setRefreshing(true);
                    mLoginPresenter.getAccessToken(authCode);
                }
            }
        });
        Logger.d(OAuthUrl.getOAuthLoginUrl(getString(R.string.Client_ID)));
        webView.loadUrl(OAuthUrl.getOAuthLoginUrl(getString(R.string.Client_ID)));
    }

    @Override protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override public void showLoading() {
    }

    @Override public void closeLoading() {
    }
}

