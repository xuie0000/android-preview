package com.xuie.androiddemo.model;

/**
 * Created by Jack Wang on 2016/6/3.
 */
public class Injection {

//    public static ShotsRepository provideTasksRepository(@NonNull Context context) {
//        checkNotNull(context);
//        return ShotsRepository.getInstance(ShotsLocalDataSource.getInstance(context), ShotsRemoteDataSource.getInstance());
//    }

    public static String provideTokenValue(){
//        return "your access_token for test .";
        return "f0eef740b4c44e287e9681c491a3867ecbff02b4552334c0dbb4defccc24bd6b";
    }

}
