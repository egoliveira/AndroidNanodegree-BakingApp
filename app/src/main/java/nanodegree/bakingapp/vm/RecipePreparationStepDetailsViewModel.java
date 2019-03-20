package nanodegree.bakingapp.vm;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import nanodegree.bakingapp.model.vo.PreparationStep;
import nanodegree.bakingapp.model.vo.Recipe;
import nanodegree.bakingapp.view.detail.RecipePreparationStepDetailsActionListener;

public class RecipePreparationStepDetailsViewModel extends BaseViewModel
        implements RecipePreparationStepDetailsActionListener {
    private final MutableLiveData<Recipe> mRecipe;

    private final MutableLiveData<PreparationStep> mStep;

    private final MutableLiveData<Boolean> mCanGoPrevious;

    private final MutableLiveData<Boolean> mCanGoNext;

    private final MutableLiveData<Boolean> mErrorLoadingVideo;

    private final MutableLiveData<Boolean> mLoadingThumbnail;

    public RecipePreparationStepDetailsViewModel(Recipe recipe, PreparationStep step) {
        this.mRecipe = new MutableLiveData<>();
        this.mRecipe.setValue(recipe);

        this.mStep = new MutableLiveData<>();
        this.mStep.setValue(step);

        this.mCanGoPrevious = new MutableLiveData<>();
        this.mCanGoPrevious.setValue(false);

        this.mCanGoNext = new MutableLiveData<>();
        this.mCanGoNext.setValue(false);

        this.mErrorLoadingVideo = new MutableLiveData<>();
        this.mErrorLoadingVideo.setValue(false);

        this.mLoadingThumbnail = new MutableLiveData<>();
        this.mLoadingThumbnail.setValue(false);

        refreshNavigation();
    }

    public void selectStep(PreparationStep step) {
        Recipe recipe = mRecipe.getValue();

        if ((recipe != null) && (step != null)) {
            int index = recipe.getSteps().indexOf(step);

            if (index != -1) {
                mErrorLoadingVideo.setValue(false);
                mStep.setValue(recipe.getSteps().get(index));
            }
        }
    }

    public LiveData<Recipe> getRecipe() {
        return mRecipe;
    }

    public LiveData<PreparationStep> getStep() {
        return mStep;
    }

    public LiveData<Boolean> getCanGoPrevious() {
        return mCanGoPrevious;
    }

    public LiveData<Boolean> getCanGoNext() {
        return mCanGoNext;
    }

    public LiveData<Boolean> getErrorLoadingVideo() {
        return mErrorLoadingVideo;
    }

    public LiveData<Boolean> getLoadingThumbnail() {
        return mLoadingThumbnail;
    }

    public void setErrorLoadingVideo(boolean error) {
        this.mErrorLoadingVideo.setValue(error);
    }

    public void setLoadingThumbnail(boolean loading) {
        this.mLoadingThumbnail.setValue(loading);
    }

    @Override
    public void onPreviousClick() {
        Recipe recipe = mRecipe.getValue();
        PreparationStep step = mStep.getValue();

        if ((recipe != null) && (step != null)) {
            List<PreparationStep> steps = recipe.getSteps();
            int index = steps.indexOf(step);

            if ((index > 0) && (steps.size() > 0)) {
                mErrorLoadingVideo.setValue(false);
                mStep.setValue(steps.get(index - 1));
                refreshNavigation();
            }
        }
    }

    @Override
    public void onNextClick() {
        Recipe recipe = mRecipe.getValue();
        PreparationStep step = mStep.getValue();

        if ((recipe != null) && (step != null)) {
            List<PreparationStep> steps = recipe.getSteps();
            int index = steps.indexOf(step);

            if ((index > -1) && (index < steps.size() - 1)) {
                mErrorLoadingVideo.setValue(false);
                mStep.setValue(steps.get(index + 1));

                refreshNavigation();
            }
        }
    }

    private void refreshNavigation() {
        Recipe recipe = mRecipe.getValue();

        if (recipe != null) {
            List<PreparationStep> steps = recipe.getSteps();

            if (steps != null) {
                int index = steps.indexOf(mStep.getValue());

                mCanGoPrevious.setValue(index > 0);
                mCanGoNext.setValue((index > -1) && (index < steps.size() - 1));
            } else {
                mCanGoPrevious.setValue(false);
                mCanGoNext.setValue(false);
            }
        } else {
            mCanGoPrevious.setValue(false);
            mCanGoNext.setValue(false);
        }
    }
}
