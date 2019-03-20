package nanodegree.bakingapp.view.overview;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import nanodegree.bakingapp.databinding.RecipePreparationStepViewBinding;
import nanodegree.bakingapp.model.vo.PreparationStep;

public class RecipePreparationStepViewHolder
        extends BaseRecipeOverviewItemViewHolder<PreparationStep> {
    private final RecipePreparationStepViewBinding mViewBinding;

    private final RecipePreparationStepActionListener mListener;

    public RecipePreparationStepViewHolder(@NonNull RecipePreparationStepViewBinding viewBinding,
            RecipePreparationStepActionListener listener) {
        super(viewBinding.getRoot());

        this.mViewBinding = viewBinding;
        this.mListener = listener;

        if (mListener != null) {
            mListener.getSelectedPreparationStep()
                    .addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                        @Override
                        public void onPropertyChanged(Observable sender, int propertyId) {
                            updateSelection();
                        }
                    });
        }
    }

    @Override
    public void bind(PreparationStep item) {
        mViewBinding.setStep(item);
        mViewBinding.setListener(mListener);

        updateSelection();
    }

    private void updateSelection() {
        boolean selected = (mViewBinding.getStep() != null)
                && (mViewBinding.getStep().equals(mListener.getSelectedPreparationStep().get()));

        mViewBinding.recipePreparationStepViewContainer.setSelected(selected);
    }
}
