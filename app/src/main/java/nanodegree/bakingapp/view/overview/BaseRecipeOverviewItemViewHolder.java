package nanodegree.bakingapp.view.overview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import nanodegree.bakingapp.model.vo.RecipeOverviewItem;

public abstract class BaseRecipeOverviewItemViewHolder<T extends RecipeOverviewItem>
        extends RecyclerView.ViewHolder {
    public BaseRecipeOverviewItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(T item);
}
