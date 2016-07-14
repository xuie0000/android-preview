package com.xuie.androiddemo.ui.fragment.person.presenter;

import com.xuie.androiddemo.model.IModel.UserModel;
import com.xuie.androiddemo.model.UserModelImpl;
import com.xuie.androiddemo.presenter.BasePresenter;
import com.xuie.androiddemo.ui.fragment.person.IPersonFragment;
import com.xuie.androiddemo.ui.fragment.person.PersonFragment;

public class PersonPresenter extends BasePresenter<PersonFragment> {

    private UserModel mUserModel;
    private IPersonFragment mPersonFragment;

    public PersonPresenter(IPersonFragment mPersonFragment) {
        this.mPersonFragment = mPersonFragment;
        mUserModel = UserModelImpl.getInstance();
    }
}
