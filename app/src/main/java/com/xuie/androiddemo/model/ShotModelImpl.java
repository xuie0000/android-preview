package com.xuie.androiddemo.model;

import com.orhanobut.logger.Logger;
import com.xuie.androiddemo.bean.Shot;
import com.xuie.androiddemo.bean.Team;
import com.xuie.androiddemo.model.IModel.ShotModel;
import com.xuie.androiddemo.model.service.DribbbleAPI;
import com.xuie.androiddemo.model.service.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

public class ShotModelImpl implements ShotModel {
    private Realm mRealm;
    private static ShotModelImpl instance;
    private static DribbbleAPI mServiceAPI;

    public final static int PAGE = 1;
    public final static int PER_PAGE = 10;

    public static ShotModelImpl getInstance() {
        if (instance == null) {
            synchronized (ShotModelImpl.class) {
                if (instance == null)
                    instance = new ShotModelImpl();
            }
        }
        return instance;
    }

    private ShotModelImpl() {
        mServiceAPI = ServiceGenerator.createService(DribbbleAPI.class);
    }


    @Override public Observable<List<Shot>> getShotsFromServer(int page, int per_page) {
        return mServiceAPI.getShots(page, per_page)
                .map(shots -> {
                    List<Shot> shotList = new ArrayList<>();
                    for (Shot val : shots) {
                        if (val.getTeam() == null) {
                            val.setTeam(new Team(12345));
                        }
                        shotList.add(val);
                    }
                    return shotList;
                });
    }

    @Override public void saveShotsToRealm(List<Shot> shots) {
        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(shots);
        mRealm.commitTransaction();
        mRealm.close();
        Logger.d("保存了新来的Shot");
    }

    @Override public void clearShotsToRealm() {
        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        mRealm.delete(Shot.class);
        mRealm.commitTransaction();
        mRealm.close();
        Logger.d("清除了所有Shot");
    }

    @Override public void closeSomeThing() {
        if (mRealm != null) {
            mRealm.close();
        }
    }


    @Override public Observable<List<Shot>> loadShots2Realm() {
        return Realm.getDefaultInstance().where(Shot.class)
                .findAllAsync()
                .asObservable()
                .filter(RealmResults::isLoaded)
                .map((Func1<RealmResults<Shot>, List<Shot>>) shots -> {
                    ArrayList<Shot> list = new ArrayList<>();
                    for (Shot shot : shots) list.add(shot);
                    return list;
                });
    }


}
