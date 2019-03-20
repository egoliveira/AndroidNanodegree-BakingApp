package nanodegree.bakingapp.view.overview;

import androidx.annotation.NonNull;
import nanodegree.bakingapp.databinding.RecipeIngredientViewBinding;
import nanodegree.bakingapp.model.vo.Ingredient;

public class RecipeIngredientViewHolder extends BaseRecipeOverviewItemViewHolder<Ingredient> {
    private final RecipeIngredientViewBinding mViewBinding;

    public RecipeIngredientViewHolder(@NonNull RecipeIngredientViewBinding viewBinding) {
        super(viewBinding.getRoot());

        this.mViewBinding = viewBinding;
    }

    @Override
    public void bind(Ingredient item) {
        mViewBinding.setIngredient(item);
    }
}
