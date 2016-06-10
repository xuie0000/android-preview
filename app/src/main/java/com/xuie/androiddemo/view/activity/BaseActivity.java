package com.xuie.androiddemo.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.xuie.androiddemo.presenter.BasePresenter;

/**
 * Created by xuie on 15-12-19.
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {
    protected T mPresenter;
    protected Handler mHandler;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        mPresenter = createPresenter();
        mHandler = new Handler();
        mPresenter.attachView((V) this);
        mPresenter.OnViewCreate();
    }

    @Override protected void onResume() {
        super.onResume();
        mPresenter.OnViewResume();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter.onViewDestroy();
    }

    @Override protected void onStop() {
        super.onStop();
        mPresenter.OnViewStop();
    }

    @Override protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    protected T getPresenter() {
        return mPresenter;
    }

    protected abstract T createPresenter();

    public Handler getHandler() {
        return mHandler;
    }

    protected void initToolbar() {
    }


}
