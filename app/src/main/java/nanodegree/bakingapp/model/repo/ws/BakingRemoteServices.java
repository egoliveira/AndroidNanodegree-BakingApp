package nanodegree.bakingapp.model.repo.ws;

import java.util.List;

import io.reactivex.Single;
import nanodegree.bakingapp.BuildConfig;
import nanodegree.bakingapp.model.vo.Recipe;
import retrofit2.http.GET;

public interface BakingRemoteServices {
    @GET(BuildConfig.BAKING_SERVICES_RECIPES_PATH)
    Single<List<Recipe>> getRecipes();
}
