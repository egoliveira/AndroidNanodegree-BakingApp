package nanodegree.bakingapp.view.recipes;

import org.apache.commons.lang3.StringUtils;

import com.squareup.picasso.Picasso;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import nanodegree.bakingapp.R;
import nanodegree.bakingapp.databinding.RecipeCardViewBinding;
import nanodegree.bakingapp.model.vo.Recipe;
import nanodegree.bakingapp.view.BaseAdapter;

public class RecipesAdapter extends BaseAdapter<Recipe, RecipesAdapter.ViewHolder> {
    private RecipeItemActionListener mListener;

    public RecipesAdapter(@Nullable RecipeItemActionListener listener) {
        this.mListener = listener;

        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecipeCardViewBinding binding = RecipeCardViewBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = getItem(position);

        holder.bind(mListener, recipe);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final RecipeCardViewBinding mBinding;

        ViewHolder(RecipeCardViewBinding binding) {
            super(binding.getRoot());

            this.mBinding = binding;
        }

        void bind(RecipeItemActionListener listener, Recipe recipe) {
            mBinding.setActionListener(listener);
            mBinding.setRecipe(recipe);

            if (StringUtils.isNotBlank(recipe.getImage())) {
                Picasso.get().load(recipe.getImage()).error(R.drawable.recipe_card_default_image)
                        .placeholder(R.drawable.recipe_card_default_image)
                        .into(mBinding.recipeCardViewImage);
            } else {
                mBinding.recipeCardViewImage.setImageResource(R.drawable.recipe_card_default_image);
            }
        }
    }
}
