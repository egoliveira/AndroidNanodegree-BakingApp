package nanodegree.bakingapp.view.recipes;

import android.view.View;
import android.widget.ProgressBar;

import nanodegree.bakingapp.AbstractIdlingResource;
import nanodegree.bakingapp.R;

public class RecipesListActivityIdlingResource extends AbstractIdlingResource<RecipeListActivity> {
    private boolean mIdle;

    public RecipesListActivityIdlingResource() {
        super(RecipeListActivity.class);
    }

    @Override
    protected boolean isIdle() {
        return mIdle;
    }

    @Override
    protected void onActivityCreated(RecipeListActivity activity) {
        final ProgressBar progress = activity.findViewById(R.id.recipe_list_fragment_progress);

        mIdle = progress.getVisibility() != View.VISIBLE;

        progress.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            mIdle = progress.getVisibility() != View.VISIBLE;
            notifyUpdate();
        });

        notifyUpdate();
    }

    @Override
    public String getName() {
        return "Recipe List Idling Resource";
    }
}
