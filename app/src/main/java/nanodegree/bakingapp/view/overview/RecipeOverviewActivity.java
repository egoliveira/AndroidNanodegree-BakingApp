package nanodegree.bakingapp.view.overview;

import android.os.Bundle;

import nanodegree.bakingapp.R;
import nanodegree.bakingapp.model.vo.Recipe;
import nanodegree.bakingapp.view.BaseActivity;

public class RecipeOverviewActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Recipe recipe = getIntent().getParcelableExtra("recipe");

        getToolbar().setTitle(recipe.getName());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.recipe_overview_activity;
    }
}
