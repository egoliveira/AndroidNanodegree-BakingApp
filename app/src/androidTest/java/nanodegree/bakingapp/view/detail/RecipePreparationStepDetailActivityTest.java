package nanodegree.bakingapp.view.detail;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.dannyroa.espresso_samples.recyclerview.RecyclerViewMatcher.withRecyclerView;
import static org.junit.Assume.assumeFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import nanodegree.bakingapp.R;
import nanodegree.bakingapp.view.overview.BaseRecipeOverviewTest;
import nanodegree.bakingapp.view.recipes.RecipeListActivity;
import nanodegree.bakingapp.view.recipes.RecipesListActivityIdlingResource;

@RunWith(AndroidJUnit4.class)
public class RecipePreparationStepDetailActivityTest extends BaseRecipeOverviewTest {
    private RecipesListActivityIdlingResource mRecipesListActivityIdlingResource;

    private RecipePreparationStepDetailIdlingResource mRecipePreparationStepDetailIdlingResource;

    @Rule
    public ActivityTestRule<RecipeListActivity> mRecipeListActivityTestRule = new ActivityTestRule<>(
            RecipeListActivity.class, false, false);

    @Test
    public void recipePreparationStepDetailActivityPhone_verifyRecipePreparationStepsNavigationInsideForward_shouldBeSuccess() {
        openRecipeListActivity();

        Context context = mRecipeListActivityTestRule.getActivity();

        assumeFalse(isTablet(context));

        openRecipeOverviewActivity();
        openFirstPreparationStep();

        checkPreparationStepFragment(true, false, false, false, "Recipe Introduction");
        checkPreviousStep(false);
        checkNextStep(true);

        navigateNextStep();

        checkPreparationStepFragment(false, false, false, false,
                "1. Preheat the oven to 350\u00b0F. Butter a 9\" deep dish pie pan.");
        checkPreviousStep(true);
        checkNextStep(true);

        navigateNextStep();

        checkPreparationStepFragment(true, false, false, false,
                "2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.");
        checkPreviousStep(true);
        checkNextStep(true);

        navigateNextStep();

        checkPreparationStepFragment(true, false, false, false,
                "3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.");
        checkPreviousStep(true);
        checkNextStep(true);

        navigateNextStep();

        checkPreparationStepFragment(true, false, false, false,
                "4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.");
        checkPreviousStep(true);
        checkNextStep(true);

        navigateNextStep();

        checkPreparationStepFragment(false, true, false, false,
                "5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.");
        checkPreviousStep(true);
        checkNextStep(true);

        navigateNextStep();

        checkPreparationStepFragment(false, false, true, false,
                "6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!");
        checkPreviousStep(true);
        checkNextStep(false);
    }

    @Test
    public void recipePreparationStepDetailActivityPhone_verifyRecipePreparationStepsNavigationInsideBackward_shouldBeSuccess() {
        openRecipeListActivity();

        Context context = mRecipeListActivityTestRule.getActivity();

        assumeFalse(isTablet(context));

        openRecipeOverviewActivity();
        openLastPreparationStep();

        checkPreparationStepFragment(false, false, true, false,
                "6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!");
        checkPreviousStep(true);
        checkNextStep(false);

        navigatePreviousStep();

        checkPreparationStepFragment(false, true, false, false,
                "5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.");
        checkPreviousStep(true);
        checkNextStep(true);

        navigatePreviousStep();

        checkPreparationStepFragment(true, false, false, false,
                "4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.");
        checkPreviousStep(true);
        checkNextStep(true);

        navigatePreviousStep();

        checkPreparationStepFragment(true, false, false, false,
                "3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.");
        checkPreviousStep(true);
        checkNextStep(true);

        navigatePreviousStep();

        checkPreparationStepFragment(true, false, false, false,
                "2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.");
        checkPreviousStep(true);
        checkNextStep(true);

        navigatePreviousStep();

        checkPreparationStepFragment(false, false, false, false,
                "1. Preheat the oven to 350\u00b0F. Butter a 9\" deep dish pie pan.");
        checkPreviousStep(true);
        checkNextStep(true);

        navigatePreviousStep();

        checkPreparationStepFragment(true, false, false, false, "Recipe Introduction");
        checkPreviousStep(false);
        checkNextStep(true);
    }

    @Test
    public void recipePreparationStepDetailActivityPhone_verifyRecipePreparationStepsNavigationOutside_shouldBeSuccess() {
        openRecipeListActivity();

        Context context = mRecipeListActivityTestRule.getActivity();

        assumeFalse(isTablet(context));

        openRecipeOverviewActivity();
        openFirstPreparationStep();

        checkPreparationStepFragment(true, false, false, false, "Recipe Introduction");
        checkPreviousStep(false);
        checkNextStep(true);

        pressBack();
        openPreparationStep(2);

        checkPreparationStepFragment(false, false, false, false,
                "1. Preheat the oven to 350\u00b0F. Butter a 9\" deep dish pie pan.");
        checkPreviousStep(true);
        checkNextStep(true);

        pressBack();
        openPreparationStep(3);

        checkPreparationStepFragment(true, false, false, false,
                "2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.");
        checkPreviousStep(true);
        checkNextStep(true);

        pressBack();
        openPreparationStep(4);

        checkPreparationStepFragment(true, false, false, false,
                "3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.");
        checkPreviousStep(true);
        checkNextStep(true);

        pressBack();
        openPreparationStep(5);

        checkPreparationStepFragment(true, false, false, false,
                "4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.");
        checkPreviousStep(true);
        checkNextStep(true);

        pressBack();
        openPreparationStep(6);

        checkPreparationStepFragment(false, true, false, false,
                "5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.");
        checkPreviousStep(true);
        checkNextStep(true);

        pressBack();
        openPreparationStep(7);

        checkPreparationStepFragment(false, false, true, false,
                "6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!");
        checkPreviousStep(true);
        checkNextStep(false);
    }

    @Before
    public void setUp() {
        mRecipesListActivityIdlingResource = new RecipesListActivityIdlingResource();
        mRecipePreparationStepDetailIdlingResource = new RecipePreparationStepDetailIdlingResource();

        IdlingRegistry.getInstance().register(mRecipesListActivityIdlingResource);
        IdlingRegistry.getInstance().register(mRecipePreparationStepDetailIdlingResource);

        startWebServer();
    }

    @After
    public void tearDown() {
        if (mRecipesListActivityIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mRecipesListActivityIdlingResource);
        }

        if (mRecipePreparationStepDetailIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mRecipePreparationStepDetailIdlingResource);
        }

        stopWebServer();
    }

    private void openRecipeListActivity() {
        setDefaultWebServerResponse();

        mRecipeListActivityTestRule.launchActivity(new Intent());
    }

    private void openRecipeOverviewActivity() {
        onView(withRecyclerView(R.id.recipe_list_fragment_recipes).atPositionOnView(0,
                R.id.recipe_card_view_image)).perform(click());
    }

    private void checkPreviousStep(boolean expected) {
        if (expected) {
            onView(withId(R.id.recipe_preparation_step_details_fragment_previous_step))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        } else {
            onView(withId(R.id.recipe_preparation_step_details_fragment_previous_step))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        }
    }

    private void checkNextStep(boolean expected) {
        if (expected) {
            onView(withId(R.id.recipe_preparation_step_details_fragment_next_step))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        } else {
            onView(withId(R.id.recipe_preparation_step_details_fragment_next_step))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        }
    }

    private void navigateNextStep() {
        onView(withId(R.id.recipe_preparation_step_details_fragment_next_step)).perform(click());
    }

    private void navigatePreviousStep() {
        onView(withId(R.id.recipe_preparation_step_details_fragment_previous_step))
                .perform(click());
    }
}
