package com.xuie.androiddemo.bean;

import com.xuie.androiddemo.App;
import com.xuie.androiddemo.R;

public class AuthBody {

    String client_id;
    String client_secret;
    String code;
    String redirect_uri;

    public AuthBody(String code) {
        this.code = code;

        client_id = App.getContext().getString(R.string.Client_ID);
        client_secret = App.getContext().getString(R.string.Client_Secret);
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }
}
