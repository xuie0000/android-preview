package com.xuie.androiddemo.model.IModel;

import com.xuie.androiddemo.bean.Shot;

import java.util.List;

import rx.Observable;

public interface ShotModel {

    Observable<List<Shot>> loadShots2Realm();

    Observable<List<Shot>> getShotsFromServer(int page, int per_page);

    void saveShotsToRealm(List<Shot> shots);

    void closeSomeThing();

    void clearShotsToRealm();

}
