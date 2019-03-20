package nanodegree.bakingapp.vm;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import nanodegree.bakingapp.model.vo.PreparationStep;
import nanodegree.bakingapp.model.vo.Recipe;
import nanodegree.bakingapp.model.vo.RecipeIngredientsHeader;
import nanodegree.bakingapp.model.vo.RecipeOverviewItem;
import nanodegree.bakingapp.model.vo.RecipePreparationStepsHeader;
import nanodegree.bakingapp.view.detail.RecipePreparationStepDetailsActivity;
import nanodegree.bakingapp.view.overview.RecipePreparationStepActionListener;

public class RecipeOverviewViewModel extends BaseViewModel
        implements RecipePreparationStepActionListener {
    private final Recipe mRecipe;

    private final ObservableField<PreparationStep> mSelectedStep;

    public RecipeOverviewViewModel(Recipe recipe) {
        this.mRecipe = recipe;
        this.mSelectedStep = new ObservableField<>();
    }

    public List<RecipeOverviewItem> getOverview() {
        List<RecipeOverviewItem> items = new ArrayList<>();

        items.add(new RecipeIngredientsHeader());
        items.addAll(mRecipe.getIngredients());
        items.add(new RecipePreparationStepsHeader());
        items.addAll(mRecipe.getSteps());

        return items;
    }

    @Override
    public void onPreparationStepClicked(PreparationStep step, Context context) {
        if (isTablet(context) && (context instanceof FragmentActivity)) {
            RecipePreparationStepDetailsViewModel vm = ViewModelProviders
                    .of((FragmentActivity) context)
                    .get(RecipePreparationStepDetailsViewModel.class);

            vm.selectStep(step);
            mSelectedStep.set(step);
        } else {
            Intent intent = new Intent(context, RecipePreparationStepDetailsActivity.class);

            intent.putExtra("recipe", mRecipe);
            intent.putExtra("step", step);

            context.startActivity(intent);
        }
    }

    @Override
    public ObservableField<PreparationStep> getSelectedPreparationStep() {
        return mSelectedStep;
    }
}
