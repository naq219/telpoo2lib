package com.lemy.telpoo2lib.net;

import android.util.Base64;
import android.util.Log;


import com.lemy.telpoo2lib.utils.Mlog;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class NetSupport {
    protected static String TAG = NetSupport.class.getSimpleName();
    private String contentType;
    private String userAgent;
    private Integer connectTimeout;
    private Integer soTimeout;
    private String authorization;
    private int numberRetry = 3;
    // private volatile static BaseNetSupportBeta instance;
    private static NetSupport instance;

    public static NetSupport getInstance() {
        if (instance == null) {
            if (instance == null) {
                instance = new NetSupport();
            }
        }
        return instance;
    }

    private NetSupport() {

    }

    public void init(NetConfig netConfig) {
        connectTimeout = netConfig.getConnectTimeout();
        soTimeout = netConfig.getSoTimeout();
        authorization = netConfig.getAuthorization();
        contentType = netConfig.getContentType();
        userAgent = netConfig.getUserAgent();
        numberRetry = netConfig.getNumberRetry();
    }




    private String methodSSL(String method, String url, String parram){
        HttpsURLConnection urlConnection;

        urlConnection= (HttpsURLConnection) getConnection(method,url,parram);
        urlConnection.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        return getResponseFromHttpURLConnection(urlConnection);
    }



    public String methodHttp(String method, String url, String parram){
        HttpURLConnection urlConnection=  getConnection(method,url,parram);
        return getResponseFromHttpURLConnection(urlConnection);
    }

    public String methodHttpCache(String method, String url, String parram,Long cacheMilis){


        HttpURLConnection urlConnection=  getConnection(method,url,parram);
        return getResponseFromHttpURLConnection(urlConnection);
    }

    private String getResponseFromHttpURLConnection(HttpURLConnection urlConnection) {
        if(urlConnection==null) {

            return null;
        }
        if (numberRetry == 0)
            return null;
        int retryCount = 0;
        do {
            try {
                int code = urlConnection.getResponseCode();
                Mlog.D("response code="+code);
                boolean isError = code >= 400;
                InputStream in =null;
                in = isError ? urlConnection.getErrorStream() : urlConnection.getInputStream();
                String res= IOUtils.toString(in,"UTF-8");  //FileSupport.readFromInputStream(in);
                urlConnection.disconnect();
                return res;

            } catch (IOException e) {
                Mlog.E(TAG+" 983793 getResponseFromHttpURLConnection - "+e.getMessage());

            }

        } while (++retryCount < numberRetry);
        urlConnection.disconnect();
        return null;
    }


    private HttpURLConnection getConnection(String method, String url, String parram) {
        Mlog.D(TAG+"method="+method+"  url="+url+" parram="+parram);
        HttpURLConnection conn = null;
        URL request_url = null;
        try {
            request_url = new URL(url);
        } catch (MalformedURLException e) {
            Mlog.E(TAG+" 920183 getConnection - url="+url+"---"+e.getMessage());
            return null;
        }

        try {
            conn = (HttpURLConnection) request_url.openConnection();
        } catch (IOException e) {
            Mlog.E(TAG+" 79192301823 getConnection - "+e.getMessage());
            return null;
        }
        if(connectTimeout!=null)
            conn.setConnectTimeout(connectTimeout);
        if(soTimeout!=null)
            conn.setReadTimeout(soTimeout);
        if (authorization != null)
            conn.setRequestProperty("Authorization", authorization);
        if(contentType!=null)
            conn.setRequestProperty("Content-Type", contentType);
        if(userAgent!=null)
            conn.setRequestProperty("User-Agent", userAgent);


        if (parram != null) { // for POST / PUT
            conn.setDoOutput(true);
            try {
                conn.setRequestMethod(method);
            } catch (ProtocolException e) {
                Mlog.E(TAG+" 23818023 getConnection - "+e.getMessage());
                return null;
            }
            byte[] postDataBytes = new byte[0];
            try {
                postDataBytes = parram.toString().getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                Mlog.E(TAG+" 9193739 getConnection - "+e.getMessage());
                return null;
            }
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

            try {
                conn.getOutputStream().write(postDataBytes);
            } catch (IOException e) {
                Mlog.E(TAG+" 0808129373 getConnection - "+e.getMessage());
                return null;
            }
        }

        return conn;


    }


    private String getB64Auth(String string) {
        String ret = "Basic " + Base64.encodeToString(string.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
        return ret;
    }

    public static String encodeUrl(String value){
        try {
           return URLEncoder.encode(value,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return value;
        }

    }



    /////

    public Dataget simpleGet(String url, String param){
        return request("GET",url,param);
    };

    public Dataget simplePost(String url, String param){
        return request("POST",url,param);
    };

    public Dataget simplePut(String url, String param){
        return request("PUT",url,param);
    };

    public Dataget simpleDelete(String url, String param){
        return request("DELETE",url,param);
    };


    public Dataget request(String method, String url, String param){
        Dataget data = new Dataget();

        String res = NetSupport.getInstance().methodHttp(method,url,param);
        Log.d("telpoo", "url: " + url);
        Log.d("telpoo", "Respone: " + res);

        try {
            JSONObject dataResponse = new JSONObject(""+res);
            if (dataResponse.optInt("status", 2)!=1) {
                data.setCode(Dataget.CODE_FALSE);
                data.setMessage(dataResponse.optString("msg", "Có lỗi api"));
                return data;
            }
            data.setCode(Dataget.CODE_SUCCESS);

            if (!dataResponse.has("data")   ) {
                data.setSuccess(dataResponse);
                return data;
            }

            data.setSuccess(dataResponse.get("data"));
            return data;


        } catch (JSONException e) {
            data.setCode(Dataget.CODE_FALSE);
            data.setMessage("Không đúng định dạng json");
        } catch (Exception e) {
            e.printStackTrace();
            data.setCode(Dataget.CODE_FALSE);
        }
        return data;
    }


}