package com.xuie.androiddemo.bean.dribbble;

/**
 * Created by Jack Wang on 2016/6/2.
 */
public class DribbbleToken {


    /**
     * access_token : 29ed478ab86c07f1c069b1af76088f7431396b7c4a2523d06911345da82224a0
     * token_type : bearer
     * scope : public write
     */

    public String access_token;
    public String token_type;
    public String scope;

    @Override public String toString() {
        return "DribbbleToken{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
