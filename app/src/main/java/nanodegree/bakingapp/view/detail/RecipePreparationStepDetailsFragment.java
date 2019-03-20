package nanodegree.bakingapp.view.detail;

import org.apache.commons.lang3.StringUtils;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.video.VideoListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import nanodegree.bakingapp.R;
import nanodegree.bakingapp.databinding.RecipePreparationStepDetailsFragmentBinding;
import nanodegree.bakingapp.mechanism.cache.ExoPlayerCache;
import nanodegree.bakingapp.model.vo.PreparationStep;
import nanodegree.bakingapp.model.vo.Recipe;
import nanodegree.bakingapp.view.BaseFragment;
import nanodegree.bakingapp.vm.RecipePreparationStepDetailsViewModel;
import nanodegree.bakingapp.vm.ViewModelProviderFactory;

public class RecipePreparationStepDetailsFragment extends
        BaseFragment<RecipePreparationStepDetailsFragmentBinding, RecipePreparationStepDetailsViewModel> {
    private SimpleExoPlayer mPlayer;

    private long mPosition;

    private boolean mPlaying;

    private VideoListener mVideoListener;

    private Player.EventListener mEventListener;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getViewDataBinding().setLifecycleOwner(this);

        mVideoListener = new PlayerVideoListener();
        mEventListener = new EventListener();

        mPosition = C.TIME_UNSET;

        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getLong("position", C.TIME_UNSET);
            mPlaying = savedInstanceState.getBoolean("playing");
        }

        mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity());
        mPlayer.addVideoListener(mVideoListener);
        mPlayer.addListener(mEventListener);

        if (mPlaying) {
            mPlayer.setPlayWhenReady(true);
            mPlaying = false;
        }

        mPlayer.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

            }
        });

        getViewDataBinding().recipePreparationStepDetailsFragmentPlayer.setPlayer(mPlayer);
        getViewDataBinding().setError(getViewModel().getErrorLoadingVideo());
        getViewDataBinding().setStep(getViewModel().getStep());
        getViewDataBinding().setCanGoPrevious(getViewModel().getCanGoPrevious());
        getViewDataBinding().setCanGoNext(getViewModel().getCanGoNext());
        getViewDataBinding().setLoadingThumbnail(getViewModel().getLoadingThumbnail());
        getViewDataBinding().setListener(getViewModel());

        getViewModel().getStep().observe(this, new StepChangedCallback());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong("position", mPlayer.getCurrentPosition());
        outState.putBoolean("playing", mPlaying);
    }

    @Override
    public void onDestroy() {
        mPlayer.removeVideoListener(mVideoListener);
        mPlayer.removeListener(mEventListener);
        mPlayer.release();

        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recipe_preparation_step_details_fragment;
    }

    @Override
    protected RecipePreparationStepDetailsViewModel createViewModel() {
        Recipe recipe = null;
        PreparationStep step = null;

        Activity activity = getActivity();

        if (activity != null) {
            recipe = activity.getIntent().getParcelableExtra("recipe");
            step = activity.getIntent().getParcelableExtra("step");
        }

        if (recipe == null) {
            throw new IllegalStateException("Recipe not set on activity.");
        }

        if (!isTablet() && (step == null)) {
            throw new IllegalStateException("Preparation step not set on activity.");
        }

        return ViewModelProviders
                .of(getActivity(),
                        new ViewModelProviderFactory(
                                new RecipePreparationStepDetailsViewModel(recipe, step)))
                .get(RecipePreparationStepDetailsViewModel.class);
    }

    private class PlayerVideoListener implements VideoListener {
        @Override
        public void onRenderedFirstFrame() {
            if (mPosition != C.TIME_UNSET) {
                mPlayer.seekTo(mPosition);

                mPosition = C.TIME_UNSET;
            }
        }
    }

    private class EventListener implements Player.EventListener {
        @Override
        public void onPlayerError(ExoPlaybackException error) {
            getViewModel().setErrorLoadingVideo(true);
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            mPlaying = playWhenReady && playbackState == Player.STATE_READY;
        }
    }

    private class StepChangedCallback implements Observer<PreparationStep> {

        @Override
        public void onChanged(PreparationStep step) {
            Picasso.get().cancelRequest(
                    getViewDataBinding().recipePreparationStepDetailsFragmentThumbnail);
            getViewModel().setLoadingThumbnail(false);
            getViewModel().setErrorLoadingVideo(false);

            if ((!getViewModel().getErrorLoadingVideo().getValue()) && (step != null)) {
                Context context = getContext();

                if (context != null) {
                    if (StringUtils.isNotBlank(step.getVideoURL())) {
                        DataSource.Factory upstreamDataSourceFactory = new DefaultDataSourceFactory(
                                context, context.getString(context.getApplicationInfo().labelRes));
                        DataSource.Factory dataSourceFactory = new CacheDataSourceFactory(
                                ExoPlayerCache.getInstance(getContext()),
                                upstreamDataSourceFactory);

                        MediaSource source = new ExtractorMediaSource.Factory(dataSourceFactory)
                                .createMediaSource(Uri.parse(step.getVideoURL()));

                        if (mPosition == C.TIME_UNSET) {
                            mPlayer.setPlayWhenReady(false);
                        }

                        mPlayer.prepare(source, true, true);
                    } else if (StringUtils.isNotBlank(step.getThumbnailURL())) {
                        getViewModel().setLoadingThumbnail(true);

                        Picasso.get().load(step.getThumbnailURL())
                                .placeholder(R.drawable.recipe_card_default_image)
                                .into(getViewDataBinding().recipePreparationStepDetailsFragmentThumbnail,
                                        new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                getViewModel().setLoadingThumbnail(false);
                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                getViewModel().setLoadingThumbnail(false);
                                                getViewModel().setErrorLoadingVideo(true);
                                            }
                                        });
                    }
                }
            }
        }
    }
}
