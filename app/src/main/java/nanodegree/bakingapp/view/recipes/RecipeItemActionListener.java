package nanodegree.bakingapp.view.recipes;

import android.content.Context;

import nanodegree.bakingapp.model.vo.Recipe;

public interface RecipeItemActionListener {
    void onRecipeClicked(Recipe recipe, Context context);
}
