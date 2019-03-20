package nanodegree.bakingapp.view.detail;

import android.view.View;
import android.widget.ProgressBar;

import nanodegree.bakingapp.AbstractIdlingResource;
import nanodegree.bakingapp.R;

public class RecipePreparationStepDetailIdlingResource
        extends AbstractIdlingResource<RecipePreparationStepDetailsActivity> {
    private boolean mVideoLoading;

    private boolean mThumbnailLoading;

    public RecipePreparationStepDetailIdlingResource() {
        super(RecipePreparationStepDetailsActivity.class);
    }

    @Override
    public String getName() {
        return "Recipe Preparation Step Detail Idling Resource";
    }

    @Override
    protected boolean isIdle() {
        return !mVideoLoading && !mThumbnailLoading;
    }

    @Override
    protected void onActivityCreated(RecipePreparationStepDetailsActivity activity) {
        ProgressBar videoLoadingProgress = activity.findViewById(R.id.exo_buffering);

        videoLoadingProgress
                .post(new VideoLoadingProgressVisibilityCheckRunnable(videoLoadingProgress));

        ProgressBar thumbnailLoadingProgress = activity
                .findViewById(R.id.recipe_preparation_step_details_fragment_thumbnail_progress);

        thumbnailLoadingProgress.post(
                new ThumbnailLoadingProgressVisibilityCheckRunnable(thumbnailLoadingProgress));
    }

    private class VideoLoadingProgressVisibilityCheckRunnable implements Runnable {
        private final ProgressBar mProgress;

        VideoLoadingProgressVisibilityCheckRunnable(ProgressBar progress) {
            this.mProgress = progress;
        }

        @Override
        public void run() {
            mVideoLoading = mProgress.getVisibility() == View.VISIBLE;
            notifyUpdate();

            mProgress.postDelayed(this, 100);
        }
    }

    private class ThumbnailLoadingProgressVisibilityCheckRunnable implements Runnable {
        private final ProgressBar mProgress;

        ThumbnailLoadingProgressVisibilityCheckRunnable(ProgressBar progress) {
            this.mProgress = progress;
        }

        @Override
        public void run() {
            mThumbnailLoading = mProgress.getVisibility() == View.VISIBLE;
            notifyUpdate();

            mProgress.postDelayed(this, 100);
        }
    }
}
