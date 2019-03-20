package nanodegree.bakingapp.view.overview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import nanodegree.bakingapp.R;
import nanodegree.bakingapp.databinding.RecipeOverviewFragmentBinding;
import nanodegree.bakingapp.model.vo.Recipe;
import nanodegree.bakingapp.view.BaseFragment;
import nanodegree.bakingapp.vm.RecipeOverviewViewModel;
import nanodegree.bakingapp.vm.ViewModelProviderFactory;

public class RecipeOverviewFragment
        extends BaseFragment<RecipeOverviewFragmentBinding, RecipeOverviewViewModel> {
    private RecipeOverviewAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new RecipeOverviewAdapter(getViewModel());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recipeOverviewItems = getViewDataBinding().recipeOverviewFragmentItems;

        recipeOverviewItems.setAdapter(mAdapter);
        recipeOverviewItems.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        mAdapter.setItems(getViewModel().getOverview());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recipe_overview_fragment;
    }

    @Override
    protected RecipeOverviewViewModel createViewModel() {
        Recipe recipe = null;

        Activity activity = getActivity();

        if (activity != null) {
            recipe = activity.getIntent().getParcelableExtra("recipe");
        }

        if (recipe == null) {
            throw new IllegalStateException("Recipe not set on activity.");
        }

        return ViewModelProviders
                .of(getActivity(),
                        new ViewModelProviderFactory(new RecipeOverviewViewModel(recipe)))
                .get(RecipeOverviewViewModel.class);
    }
}
