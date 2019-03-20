package nanodegree.bakingapp.view.recipes;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import nanodegree.bakingapp.R;
import nanodegree.bakingapp.vm.RecipeListViewModel;

public class RecipeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();

        openRecipeFromIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        openRecipeFromIntent(intent);
    }

    private void openRecipeFromIntent(Intent intent) {
        int recipeId = -1;

        if ((intent.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) == 0) {
            recipeId = intent.getIntExtra("recipe_id", -1);
            intent.removeExtra("recipe_id");
        }

        if (recipeId != -1) {
            RecipeListViewModel recipeListViewModel = ViewModelProviders.of(this)
                    .get(RecipeListViewModel.class);

            recipeListViewModel.openRecipe(recipeId);
        }
    }
}
