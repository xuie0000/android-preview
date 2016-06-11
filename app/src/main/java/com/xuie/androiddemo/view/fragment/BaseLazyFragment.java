package com.xuie.androiddemo.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.xuie.androiddemo.bean.dribbble.Shot;


abstract class BaseLazyFragment extends Fragment {
    boolean isPrepared;

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    /**
     * 第一次onResume中的调用onUserVisible避免操作与onFirstUserVisible操作重复
     */
    private boolean isFirstResume = true;

    @Override public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;

    @Override public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    public synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    public void showProgress() {
    }

    public void closeProgress() {

    }

    /**
     * 第一次fragment可见（进行初始化工作）
     */
    abstract void onFirstUserVisible();

    /**
     * fragment可见（切换回来或者onResume）
     */
    abstract void onUserVisible();

    /**
     * 第一次fragment不可见（不建议在此处理事件）
     */
    abstract void onFirstUserInvisible();

    /**
     * fragment不可见（切换掉或者onPause）
     */
    abstract void onUserInvisible();

    abstract void setData();

    abstract void setData(Shot shot);
}