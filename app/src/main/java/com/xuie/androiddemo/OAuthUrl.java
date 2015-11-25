package com.xuie.androiddemo;

public class OAuthUrl {
    final static String OAUTH_PATH = "https://dribbble.com/oauth/authorize?";
    public final static String OAUTH_STATE = "0000000";

    public static String getOAuthLoginUrl(String clientId, String redirect_uri, String scope, String state) {
        return OAUTH_PATH
                + "client_id=" + clientId
                + "&redirect_uri" + redirect_uri
                + "&scope=" + scope
                + "&state=" + state;
    }

    public static String getOAuthLoginUrl(String clientId, String state) {
        return getOAuthLoginUrl(clientId, "", "upload+write", state);
    }

    public static String getOAuthLoginUrl(String clientId) {
        return getOAuthLoginUrl(clientId, OAUTH_STATE);
    }

}
