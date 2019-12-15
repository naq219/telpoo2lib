package com.lemy.telpoo2lib.net;

public class NetConfig {

    private final String contentType;
    private final String userAgent;
    private final int connectTimeout;
    private final int soTimeout;
    private final String authorization;
    private final int numberRetry;

    private NetConfig(Builder builder) {
        contentType = builder.contentType;
        numberRetry = builder.numberRetry;
        userAgent = builder.userAgent;
        connectTimeout = builder.connectTimeout;
        soTimeout = builder.soTimeout;
        authorization = builder.authorization;
    }

    public String getContentType() {
        return contentType;
    }

    public int getNumberRetry() {
        return numberRetry;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public String getAuthorization() {
        return authorization;
    }

    public NetConfig createDefault() {
        return new NetConfig.Builder().build();
    }

    public static class Builder {
        private int numberRetry = 3;
        private int connectTimeout = 15000;
        private int soTimeout = 10000;
        private String authorization = null;
        private String contentType = null;
        private String userAgent = "Mozilla/5.0 (Linux; Android 4.1.1; Nexus 7 Build/JRO03D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19";

        public Builder() {

        }

        public NetConfig.Builder connectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public NetConfig.Builder soTimeout(int soTimeout) {
            this.soTimeout = soTimeout;
            return this;
        }

        public NetConfig.Builder authorization(String authorization) {
            this.authorization = authorization;
            return this;
        }

        public NetConfig.Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public NetConfig.Builder numberRetry(int numberRetry) {
            this.numberRetry = numberRetry;
            return this;
        }

        public NetConfig.Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public NetConfig.Builder cloneFrom(NetConfig option) {
            connectTimeout = option.getConnectTimeout();
            soTimeout = option.soTimeout;
            authorization = option.authorization;
            contentType = option.contentType;
            numberRetry = option.numberRetry;
            userAgent = option.userAgent;
            return this;

        }

        public NetConfig build() {
            return new NetConfig(this);
        }

    }

}
