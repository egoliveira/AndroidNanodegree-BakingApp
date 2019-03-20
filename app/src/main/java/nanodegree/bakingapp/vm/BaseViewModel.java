package nanodegree.bakingapp.vm;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import nanodegree.bakingapp.R;

public abstract class BaseViewModel extends ViewModel {
    private final MutableLiveData<Boolean> mLoading;

    private final MutableLiveData<Boolean> mError;

    private final MutableLiveData<Boolean> mEmpty;

    private final CompositeDisposable mCompositeDisposable;

    public BaseViewModel() {
        mLoading = new MutableLiveData<>();
        mError = new MutableLiveData<>();
        mEmpty = new MutableLiveData<>();

        mLoading.setValue(false);
        mError.setValue(false);
        mEmpty.setValue(false);

        mCompositeDisposable = new CompositeDisposable();
    }

    @NonNull
    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    @NonNull
    public LiveData<Boolean> isError() {
        return mError;
    }

    @NonNull
    public LiveData<Boolean> isEmpty() {
        return mEmpty;
    }

    protected void setLoading(boolean loading) {
        mLoading.setValue(loading);
    }

    protected void setError(boolean error) {
        mError.setValue(error);
    }

    protected void setEmpty(boolean empty) {
        mEmpty.setValue(empty);
    }

    protected CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    protected boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.is_tablet);
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();

        super.onCleared();
    }
}
