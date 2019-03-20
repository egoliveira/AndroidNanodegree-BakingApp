package nanodegree.bakingapp.view.detail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import nanodegree.bakingapp.R;
import nanodegree.bakingapp.model.vo.Recipe;
import nanodegree.bakingapp.view.BaseActivity;

public class RecipePreparationStepDetailsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        super.onCreate(savedInstanceState);

        Recipe recipe = getIntent().getParcelableExtra("recipe");

        if (getToolbar() != null) {
            getToolbar().setTitle(recipe.getName());
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.recipe_preparation_step_details_activity;
    }
}
