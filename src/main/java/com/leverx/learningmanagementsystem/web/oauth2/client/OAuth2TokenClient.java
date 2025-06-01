package com.leverx.learningmanagementsystem.web.oauth2.client;

public interface OAuth2TokenClient {

    String getToken(String clientId, String clientSecret, String tokenUrl);

}
