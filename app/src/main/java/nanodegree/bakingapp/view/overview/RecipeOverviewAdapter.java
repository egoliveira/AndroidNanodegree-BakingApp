package nanodegree.bakingapp.view.overview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import nanodegree.bakingapp.R;
import nanodegree.bakingapp.databinding.RecipeIngredientViewBinding;
import nanodegree.bakingapp.databinding.RecipePreparationStepViewBinding;
import nanodegree.bakingapp.model.vo.Ingredient;
import nanodegree.bakingapp.model.vo.PreparationStep;
import nanodegree.bakingapp.model.vo.RecipeIngredientsHeader;
import nanodegree.bakingapp.model.vo.RecipeOverviewItem;
import nanodegree.bakingapp.model.vo.RecipePreparationStepsHeader;
import nanodegree.bakingapp.view.BaseAdapter;
import nanodegree.bakingapp.vm.RecipeOverviewViewModel;

public class RecipeOverviewAdapter
        extends BaseAdapter<RecipeOverviewItem, BaseRecipeOverviewItemViewHolder> {
    private static final int RECIPE_INGREDIENTS_HEADER_TYPE = 1;

    private static final int RECIPE_INGREDIENT_TYPE = 2;

    private static final int RECIPE_STEPS_HEADER_TYPE = 3;

    private static final int RECIPE_STEP_TYPE = 4;

    private final RecipeOverviewViewModel mViewModel;

    public RecipeOverviewAdapter(RecipeOverviewViewModel viewModel) {
        this.mViewModel = viewModel;

        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        RecipeOverviewItem item = getItem(position);

        if (item instanceof RecipeIngredientsHeader) {
            type = RECIPE_INGREDIENTS_HEADER_TYPE;
        } else if (item instanceof Ingredient) {
            type = RECIPE_INGREDIENT_TYPE;
        } else if (item instanceof RecipePreparationStepsHeader) {
            type = RECIPE_STEPS_HEADER_TYPE;
        } else if (item instanceof PreparationStep) {
            type = RECIPE_STEP_TYPE;
        }

        return type;
    }

    @NonNull
    @Override
    public BaseRecipeOverviewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
        int viewType) {
        BaseRecipeOverviewItemViewHolder vh;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == RECIPE_INGREDIENTS_HEADER_TYPE) {
            View view = inflater.inflate(R.layout.recipe_ingredients_header_view, parent, false);

            vh = new RecipeIngredientsHeaderViewHolder(view);
        } else if (viewType == RECIPE_INGREDIENT_TYPE) {
            RecipeIngredientViewBinding binding = RecipeIngredientViewBinding.inflate(inflater,
                    parent, false);

            vh = new RecipeIngredientViewHolder(binding);
        } else if (viewType == RECIPE_STEPS_HEADER_TYPE) {
            View view = inflater.inflate(R.layout.recipe_preparation_steps_header_view, parent,
                    false);

            vh = new RecipePreparationStepsHeaderViewHolder(view);
        } else if (viewType == RECIPE_STEP_TYPE) {
            RecipePreparationStepViewBinding binding = RecipePreparationStepViewBinding
                    .inflate(inflater, parent, false);

            vh = new RecipePreparationStepViewHolder(binding, mViewModel);
        } else {
            throw new IllegalArgumentException("Invalid view type: " + viewType);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecipeOverviewItemViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == RECIPE_INGREDIENT_TYPE) {
            RecipeIngredientViewHolder vh = (RecipeIngredientViewHolder) holder;

            vh.bind((Ingredient) getItem(position));
        } else if (viewType == RECIPE_STEP_TYPE) {
            RecipePreparationStepViewHolder vh = (RecipePreparationStepViewHolder) holder;

            vh.bind((PreparationStep) getItem(position));
        }
    }
}
