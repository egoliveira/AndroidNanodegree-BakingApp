package nanodegree.bakingapp.view.overview;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.dannyroa.espresso_samples.recyclerview.RecyclerViewMatcher.withRecyclerView;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import nanodegree.bakingapp.AbstractBakingTest;
import nanodegree.bakingapp.R;

public abstract class BaseRecipeOverviewTest extends AbstractBakingTest {
    protected void checkPreparationStepFragment(boolean expectsVideo, boolean expectsThumbnail,
        boolean expectsErrorLoadingResource, boolean expectsStepNotSelected,
        String expectedStepDescription) {
        if (expectsVideo) {
            onView(withId(R.id.recipe_preparation_step_details_fragment_player))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(R.id.exo_play))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        } else {
            onView(withId(R.id.recipe_preparation_step_details_fragment_player))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        }

        if (expectsThumbnail) {
            onView(withId(R.id.recipe_preparation_step_details_fragment_thumbnail))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        } else {
            onView(withId(R.id.recipe_preparation_step_details_fragment_thumbnail))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        }

        if (expectsVideo || expectsThumbnail || expectsStepNotSelected) {
            onView(withId(R.id.recipe_preparation_step_details_fragment_no_video_available_image))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
            onView(withId(R.id.recipe_preparation_step_details_fragment_no_video_available_message))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
            onView(withId(
                    R.id.recipe_preparation_step_details_fragment_error_loading_video_message))
                            .check(matches(
                                    withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        } else if (!expectsErrorLoadingResource) {
            onView(withId(R.id.recipe_preparation_step_details_fragment_no_video_available_image))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(R.id.recipe_preparation_step_details_fragment_no_video_available_message))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(
                    R.id.recipe_preparation_step_details_fragment_error_loading_video_message))
                            .check(matches(
                                    withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        } else {
            onView(withId(R.id.recipe_preparation_step_details_fragment_no_video_available_image))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(
                    R.id.recipe_preparation_step_details_fragment_error_loading_video_message))
                            .check(matches(
                                    withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(R.id.recipe_preparation_step_details_fragment_no_video_available_message))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        }

        if (expectsStepNotSelected) {
            onView(withId(R.id.recipe_preparation_step_details_fragment_no_step_selected_image))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(R.id.recipe_preparation_step_details_fragment_no_step_selected_text))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        } else {
            onView(withId(R.id.recipe_preparation_step_details_fragment_no_step_selected_image))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
            onView(withId(R.id.recipe_preparation_step_details_fragment_no_step_selected_text))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        }

        if (expectedStepDescription != null) {
            onView(withId(R.id.recipe_preparation_step_details_fragment_description))
                    .check(matches(withText(expectedStepDescription)));
        }
    }

    protected void openPreparationStep(int step) {
        onView(withId(R.id.recipe_overview_fragment_items))
                .perform(RecyclerViewActions.scrollToPosition(10 + step));
        onView(withRecyclerView(R.id.recipe_overview_fragment_items).atPositionOnView(10 + step,
                R.id.recipe_preparation_step_view_container)).perform(click());
    }

    protected void openFirstPreparationStep() {
        openPreparationStep(1);
    }

    protected void openLastPreparationStep() {
        openPreparationStep(7);
    }
}
