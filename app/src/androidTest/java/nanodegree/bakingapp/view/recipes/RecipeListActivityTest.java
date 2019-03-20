package nanodegree.bakingapp.view.recipes;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.danielebottillo.blog.config.DrawableMatcher.withDrawable;
import static com.danielebottillo.blog.config.DrawableMatcher.withoutDrawable;
import static com.dannyroa.espresso_samples.recyclerview.RecyclerViewMatcher.withRecyclerView;
import static nanodegree.bakingapp.viewaction.ImageViewDrawableChangeDetector.waitDrawableChanging;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import nanodegree.bakingapp.AbstractBakingTest;
import nanodegree.bakingapp.R;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest extends AbstractBakingTest {
    private RecipesListActivityIdlingResource mRecipesListActivityIdlingResource;

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(
            RecipeListActivity.class, false, false);

    @Test
    public void recipeListActivity_loadBakingRecipesWithoutError_shouldBeSuccess() {
        setDefaultWebServerResponse();
        openRecipeListActivity();

        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(0,
                R.id.recipe_card_view_title)).check(matches(withText("1 Nutella Pie")));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(0,
                R.id.recipe_card_view_image))
                        .check(matches(withDrawable(R.drawable.recipe_card_default_image)));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(0,
                R.id.recipe_card_view_serve)).check(matches(withText("Serves 8 people")));

        onView(withId(R.id.recipe_list_fragment_recipes))
                .perform(RecyclerViewActions.scrollToPosition(1));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(1,
                R.id.recipe_card_view_title)).check(matches(withText("2 Brownies")));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(1,
                R.id.recipe_card_view_image))
                        .check(matches(withDrawable(R.drawable.recipe_card_default_image)));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(1,
                R.id.recipe_card_view_serve)).check(matches(withText("Serves one person")));

        onView(withId(R.id.recipe_list_fragment_recipes))
                .perform(RecyclerViewActions.scrollToPosition(2));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(2,
                R.id.recipe_card_view_title)).check(matches(withText("3 Yellow Cake")));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(2,
                R.id.recipe_card_view_image))
                        .perform(waitDrawableChanging(R.drawable.recipe_card_default_image, 10000));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(2,
                R.id.recipe_card_view_image))
                        .check(matches(withoutDrawable(R.drawable.recipe_card_default_image)));

        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(2,
                R.id.recipe_card_view_serve)).check(matches(withText("Serves 10 people")));

        onView(withId(R.id.recipe_list_fragment_recipes))
                .perform(RecyclerViewActions.scrollToPosition(3));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(3,
                R.id.recipe_card_view_title)).check(matches(withText("4 Cheesecake")));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(3,
                R.id.recipe_card_view_image))
                        .check(matches(withDrawable(R.drawable.recipe_card_default_image)));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(3,
                R.id.recipe_card_view_serve)).check(matches(withText("Serves 11 people")));

        onView(withId(R.id.recipe_list_fragment_empty_message))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.recipe_list_fragment_error_message))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.recipe_list_fragment_try_again))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void recipeListActivity_loadBakingRecipesServerNotWorking_shouldBeSuccess() {
        stopWebServer();

        openRecipeListActivity();

        onView(withId(R.id.recipe_list_fragment_recipes))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        onView(withId(R.id.recipe_list_fragment_empty_message))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.recipe_list_fragment_error_message))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.recipe_list_fragment_try_again))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void recipeListActivity_loadBakingRecipesEmptyResponse_shouldBeSuccess() {
        setEmptyWebServerResponse();
        openRecipeListActivity();

        onView(withId(R.id.recipe_list_fragment_recipes))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        onView(withId(R.id.recipe_list_fragment_empty_message))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.recipe_list_fragment_error_message))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.recipe_list_fragment_try_again))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void recipeListActivity_loadBakingRecipesServerNotWorkingTryAgainClick_shouldBeSuccess() {
        stopWebServer();

        openRecipeListActivity();

        onView(withId(R.id.recipe_list_fragment_recipes))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        onView(withId(R.id.recipe_list_fragment_empty_message))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.recipe_list_fragment_error_message))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.recipe_list_fragment_try_again))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        startWebServer();
        setDefaultWebServerResponse();

        onView(withId(R.id.recipe_list_fragment_try_again)).perform(click());

        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(0,
                R.id.recipe_card_view_title)).check(matches(withText("1 Nutella Pie")));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(0,
                R.id.recipe_card_view_image))
                        .check(matches(withDrawable(R.drawable.recipe_card_default_image)));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(0,
                R.id.recipe_card_view_serve)).check(matches(withText("Serves 8 people")));

        onView(withId(R.id.recipe_list_fragment_recipes))
                .perform(RecyclerViewActions.scrollToPosition(1));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(1,
                R.id.recipe_card_view_title)).check(matches(withText("2 Brownies")));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(1,
                R.id.recipe_card_view_image))
                        .check(matches(withDrawable(R.drawable.recipe_card_default_image)));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(1,
                R.id.recipe_card_view_serve)).check(matches(withText("Serves one person")));

        onView(withId(R.id.recipe_list_fragment_recipes))
                .perform(RecyclerViewActions.scrollToPosition(2));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(2,
                R.id.recipe_card_view_title)).check(matches(withText("3 Yellow Cake")));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(2,
                R.id.recipe_card_view_image))
                        .perform(waitDrawableChanging(R.drawable.recipe_card_default_image, 10000));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(2,
                R.id.recipe_card_view_image))
                        .check(matches(withoutDrawable(R.drawable.recipe_card_default_image)));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(2,
                R.id.recipe_card_view_serve)).check(matches(withText("Serves 10 people")));

        onView(withId(R.id.recipe_list_fragment_recipes))
                .perform(RecyclerViewActions.scrollToPosition(3));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(3,
                R.id.recipe_card_view_title)).check(matches(withText("4 Cheesecake")));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(3,
                R.id.recipe_card_view_image))
                        .check(matches(withDrawable(R.drawable.recipe_card_default_image)));
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(3,
                R.id.recipe_card_view_serve)).check(matches(withText("Serves 11 people")));

        onView(withId(R.id.recipe_list_fragment_empty_message))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.recipe_list_fragment_error_message))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.recipe_list_fragment_try_again))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Before
    public void setUp() {
        startWebServer();

        mRecipesListActivityIdlingResource = new RecipesListActivityIdlingResource();

        IdlingRegistry.getInstance().register(mRecipesListActivityIdlingResource);
    }

    @After
    public void tearDown() {
        if (mRecipesListActivityIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mRecipesListActivityIdlingResource);
        }

        stopWebServer();
    }

    private void openRecipeListActivity() {
        mActivityTestRule.launchActivity(new Intent());
    }
}
