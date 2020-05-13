package com.chiquita.mcspsa.core.helper.api;

import com.chiquita.mcspsa.BuildConfig;
import com.chiquita.mcspsa.data.model.CoreUserEntity;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoreServiceGeneratorV2 {

    private static CoreServiceGeneratorV2 instance = new CoreServiceGeneratorV2();

    private OkHttpClient.Builder httpClient;

    public static synchronized CoreServiceGeneratorV2 getInstance() {
        return instance;
    }

    public CoreServiceGeneratorV2() {
        httpClient = new OkHttpClient.Builder();
    }

    public <S> S createService(Class<S> serviceClass, CoreUserEntity user) {

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                        .baseUrl(user.getServerAddress())
                        .addConverterFactory(GsonConverterFactory.create());

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(loggingInterceptor);
        }

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder()
                    .header("Accept", "application/json")
                    .method(original.method(), original.body());

            Request request = requestBuilder.build();
            Response response = chain.proceed(request);
            return response;
        });

        httpClient.retryOnConnectionFailure(true);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
