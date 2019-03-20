package nanodegree.bakingapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import nanodegree.bakingapp.R;
import nanodegree.bakingapp.vm.BaseViewModel;

public abstract class BaseFragment<B extends ViewDataBinding, VM extends BaseViewModel>
        extends Fragment {

    private B mViewDataBinding;

    private VM mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = createViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);

        return mViewDataBinding.getRoot();
    }

    protected final B getViewDataBinding() {
        return mViewDataBinding;
    }

    protected final VM getViewModel() {
        return mViewModel;
    }

    protected final boolean isTablet() {
        return getResources().getBoolean(R.bool.is_tablet);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract VM createViewModel();
}
