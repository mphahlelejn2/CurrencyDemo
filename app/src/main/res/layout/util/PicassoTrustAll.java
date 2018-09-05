package com.jeffreymphahlele.jeffdemo.util;


import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/*
 * https://github.com/layerlre/Android-Utility-Class/blob/master/utils/picasso-util/PicassoTrustAll.java
 * */
public class PicassoTrustAll {

        private static Picasso mInstance = null;

        private PicassoTrustAll(Context context) {
            OkHttpClient client = new OkHttpClient();
            client.setHostnameVerifier((s, sslSession) -> true);
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] x509Certificates,
                        String s) {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] x509Certificates,
                        String s) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
                }
            } };
            try {
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                client.setSslSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
                e.printStackTrace();
            }

            mInstance = new Picasso.Builder(context)
                    .downloader(new OkHttpDownloader(client))
                    .listener((picasso, uri, exception) -> Log.e("PICASSO", String.valueOf(exception))).build();

        }

        public static Picasso getInstance(Context context) {
            if (mInstance == null) {
                new com.jeffreymphahlele.jeffdemo.util.PicassoTrustAll(context);
            }
            return mInstance;
        }
    }