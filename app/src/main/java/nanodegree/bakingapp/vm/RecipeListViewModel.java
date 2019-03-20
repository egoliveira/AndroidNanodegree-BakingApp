package nanodegree.bakingapp.vm;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nanodegree.bakingapp.BakingApp;
import nanodegree.bakingapp.model.repo.RecipeRepository;
import nanodegree.bakingapp.model.vo.Recipe;
import nanodegree.bakingapp.view.overview.RecipeOverviewActivity;
import nanodegree.bakingapp.view.recipes.RecipeItemActionListener;

public class RecipeListViewModel extends BaseViewModel implements RecipeItemActionListener {
    private final MutableLiveData<List<Recipe>> mRecipes;

    private final RecipeRepository mRecipeRepository;

    private int mRecipeIdToOpen;

    public RecipeListViewModel() {
        mRecipeRepository = new RecipeRepository();

        mRecipes = new MutableLiveData<>();
        mRecipes.setValue(new ArrayList<>());
    }

    @NonNull
    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public void load() {
        if (!isLoading().getValue()) {
            setLoading(true);

            getCompositeDisposable()
                    .add(mRecipeRepository.getRemoteRecipes().subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe(recipes -> {
                                Recipe recipeToOpen = null;

                                if ((recipes != null) && (!recipes.isEmpty())) {
                                    mRecipes.setValue(recipes);

                                    for (Recipe recipe : recipes) {
                                        getCompositeDisposable().add(mRecipeRepository
                                                .saveRecipe(recipe, BakingApp.getInstance())
                                                .subscribeOn(Schedulers.io()).subscribe());

                                        if (recipe.getId() == mRecipeIdToOpen) {
                                            recipeToOpen = recipe;
                                        }
                                    }
                                } else {
                                    mRecipes.setValue(new ArrayList<>());
                                }

                                setError(false);
                                setEmpty(mRecipes.getValue().isEmpty());
                                setLoading(false);

                                if (recipeToOpen != null) {
                                    onRecipeClicked(recipeToOpen, BakingApp.getInstance());
                                }

                                mRecipeIdToOpen = -1;
                            }, throwable -> {
                                mRecipes.setValue(new ArrayList<>());
                                setError(true);
                                setEmpty(true);
                                setLoading(false);

                                mRecipeIdToOpen = -1;
                            }));
        }
    }

    @Override
    public void onRecipeClicked(Recipe recipe, Context context) {
        Intent intent = new Intent(context, RecipeOverviewActivity.class);

        intent.putExtra("recipe", recipe);

        context.startActivity(intent);
    }

    public void openRecipe(int recipeId) {
        if (!isError().getValue() && !isEmpty().getValue()) {
            if (!isLoading().getValue()) {
                List<Recipe> recipes = mRecipes.getValue();

                for (Recipe recipe : recipes) {
                    if (recipe.getId() == recipeId) {
                        onRecipeClicked(recipe, BakingApp.getInstance());
                        break;
                    }
                }
            } else {
                mRecipeIdToOpen = recipeId;
            }
        }
    }

    @Override
    protected void onCleared() {
        getCompositeDisposable().dispose();

        super.onCleared();
    }
}
