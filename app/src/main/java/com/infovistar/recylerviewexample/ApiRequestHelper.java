package com.infovistar.recylerviewexample;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequestHelper {

    public static final String WEB_URL = "https://infovistar.com/";
    public static final String BASE_URL = WEB_URL+"api/v1/";
    public static Retrofit retrofit = null;
    public static Context context;

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetworkDetector.isNetworkAvailable(context)) {
                int maxAge = 60; // read from cache for 1 minute
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    //setup cache
    public static File httpCacheDirectory = new File(context != null ? context.getCacheDir() : null, "responses");
    public static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    public static Cache cache = new Cache(httpCacheDirectory, cacheSize);

    public static final OkHttpClient clientOkHttp = new OkHttpClient().newBuilder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();

    public static Retrofit getApiClient() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(clientOkHttp).build();
        }
        return retrofit;
    }

    public static Retrofit getApiClient(Context ctx) {
        if(retrofit == null) {
            OkHttpClient client;
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                InputStream inputStream = ctx.getResources().openRawResource(R.raw.infovistarcom);
                Certificate certificate = certificateFactory.generateCertificate(inputStream);
                String keyStoreType = KeyStore.getDefaultType();
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(null, null);
                keyStore.setCertificateEntry("ca", certificate);
                String defAlgo = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(defAlgo);
                trustManagerFactory.init(keyStore);
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

                client = new OkHttpClient()
                        .newBuilder()
                        .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagerFactory.getTrustManagers()[0])
                        .readTimeout(120, TimeUnit.SECONDS)
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                        .cache(cache)
                        .build();
                retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build();
            } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
                retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(clientOkHttp).build();
            }
            context = ctx;
        }
        return retrofit;
    }
}
