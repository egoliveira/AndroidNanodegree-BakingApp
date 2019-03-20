package nanodegree.bakingapp.view.recipes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import nanodegree.bakingapp.R;
import nanodegree.bakingapp.databinding.RecipeListFragmentBinding;
import nanodegree.bakingapp.view.BaseFragment;
import nanodegree.bakingapp.vm.RecipeListViewModel;
import nanodegree.bakingapp.vm.ViewModelProviderFactory;

public class RecipeListFragment
        extends BaseFragment<RecipeListFragmentBinding, RecipeListViewModel> {

    private RecipesAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new RecipesAdapter(getViewModel());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recipesList = getViewDataBinding().recipeListFragmentRecipes;

        recipesList.setAdapter(mAdapter);

        RecyclerView.LayoutManager layoutManager;

        if (isTablet()) {
            int columns = getResources().getInteger(R.integer.recipe_list_column_count);

            layoutManager = new GridLayoutManager(getContext(), columns);
        } else {
            layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        }

        recipesList.setLayoutManager(layoutManager);

        getViewDataBinding().setLifecycleOwner(this);

        getViewModel().getRecipes().observe(this, recipes -> {
            mAdapter.setItems(recipes);
            mAdapter.notifyDataSetChanged();
        });

        getViewDataBinding().setEmpty(getViewModel().isEmpty());
        getViewDataBinding().setError(getViewModel().isError());
        getViewDataBinding().setLoading(getViewModel().isLoading());
        getViewDataBinding().setTryAgainClickListener(v -> getViewModel().load());

        getViewModel().load();
    }

    @Override
    public void onDestroy() {
        getViewModel().getRecipes().removeObservers(this);

        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recipe_list_fragment;
    }

    @Override
    protected RecipeListViewModel createViewModel() {
        RecipeListViewModel recipeListViewModel = null;
        Activity activity = getActivity();

        if (activity != null) {
            recipeListViewModel = ViewModelProviders
                    .of(getActivity(), new ViewModelProviderFactory(new RecipeListViewModel()))
                    .get(RecipeListViewModel.class);
        }

        return recipeListViewModel;
    }
}
