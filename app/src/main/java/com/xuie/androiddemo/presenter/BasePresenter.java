package com.xuie.androiddemo.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by xuie on 16-4-10.
 * base presenter
 */
public abstract class BasePresenter<T> {

    private Reference<T> mViewRef;

    public void attachView(T view) {
        mViewRef = new WeakReference<>(view);
    }

    protected T getView() {
        if (mViewRef.get() != null)
            return mViewRef.get();
        return null;
    }


    public boolean isAttachedView() {
        return mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef.get() != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public void OnViewStop(){

    }

    public void OnViewResume() {

    }

    public void OnViewCreate() {

    }

    public void onViewDestroy() {

    }

}