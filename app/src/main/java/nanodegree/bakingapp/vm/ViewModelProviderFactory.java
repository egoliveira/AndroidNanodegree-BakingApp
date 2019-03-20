package nanodegree.bakingapp.vm;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelProviderFactory implements ViewModelProvider.Factory {
    private final ViewModel mViewModel;

    public ViewModelProviderFactory(ViewModel viewModel) {
        this.mViewModel = viewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (!modelClass.isAssignableFrom(mViewModel.getClass())) {
            throw new IllegalArgumentException("Cam't create the view model for the given class");

        }
        return (T) mViewModel;
    }
}
