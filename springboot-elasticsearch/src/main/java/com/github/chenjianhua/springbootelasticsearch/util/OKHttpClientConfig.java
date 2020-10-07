package com.github.chenjianhua.springbootelasticsearch.util;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;


@Configuration
public class OKHttpClientConfig {
    private final static int CONNECT_TIMEOUT_MILLISECOND = 500;
    private final static int READ_TIMEOUT_SECOND = 5;
    private final static int WRITE_TIMEOUT_SECOND = 1;

    private final static int MAX_IDLE_CONNECTIONS = 100;

    private final static int KEEP_ALIVE_DURATION_SECOND = 5;

    @Bean
    public OkHttpClient okHttpClient() {
        try {
            // 忽略https证书
            TrustManager[] trustAllCerts = buildTrustManagers();
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true)
                    .connectionPool(okHttpPool())
                    .connectTimeout(CONNECT_TIMEOUT_MILLISECOND, TimeUnit.MILLISECONDS)
                    .readTimeout(READ_TIMEOUT_SECOND, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT_SECOND, TimeUnit.SECONDS);
            OkHttpClient client = builder.build();
            OkHttpUtil.setOkHttpClient(client);
            return client;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new OkHttpClient.Builder().build();
        }
    }

    @Bean
    public ConnectionPool okHttpPool() {
        return new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION_SECOND, TimeUnit.SECONDS);
    }

    /**
     * 忽略https证书
     */
    public static TrustManager[] buildTrustManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
    }
}
