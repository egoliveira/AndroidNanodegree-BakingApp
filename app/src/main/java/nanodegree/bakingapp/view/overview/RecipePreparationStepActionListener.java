package nanodegree.bakingapp.view.overview;

import android.content.Context;

import androidx.databinding.ObservableField;
import nanodegree.bakingapp.model.vo.PreparationStep;

public interface RecipePreparationStepActionListener {
    void onPreparationStepClicked(PreparationStep step, Context context);

    ObservableField<PreparationStep> getSelectedPreparationStep();
}
