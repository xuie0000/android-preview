package com.xuie.androiddemo.presenter;

import com.xuie.androiddemo.model.IModel.UserModel;
import com.xuie.androiddemo.model.UserModelImpl;
import com.xuie.androiddemo.view.IView.IPersonFragment;
import com.xuie.androiddemo.view.fragment.PersonFragment;

public class PersonPresenter extends BasePresenter<PersonFragment> {

    private UserModel mUserModel;
    private IPersonFragment mPersonFragment;

    public PersonPresenter(IPersonFragment mPersonFragment) {
        this.mPersonFragment = mPersonFragment;
        mUserModel = UserModelImpl.getInstance();
    }
}
