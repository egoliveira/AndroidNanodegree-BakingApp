package nanodegree.bakingapp.model.repo.ws;

import nanodegree.bakingapp.model.repo.ws.BakingRemoteServices;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public final class BakingRemoteServicesFactory {
    private BakingRemoteServicesFactory() {
    }

    public static BakingRemoteServices create() {
        return new Retrofit.Builder().baseUrl("http://localhost:8080")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create()).build()
                .create(BakingRemoteServices.class);
    }
}
