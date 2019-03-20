package nanodegree.bakingapp.view.overview;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.dannyroa.espresso_samples.recyclerview.RecyclerViewMatcher.withRecyclerView;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import java.text.DecimalFormatSymbols;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import nanodegree.bakingapp.R;
import nanodegree.bakingapp.view.recipes.RecipeListActivity;
import nanodegree.bakingapp.view.recipes.RecipesListActivityIdlingResource;

@RunWith(AndroidJUnit4.class)
public class RecipeOverviewActivityTest extends BaseRecipeOverviewTest {
    private RecipesListActivityIdlingResource mRecipesListActivityIdlingResource;

    @Rule
    public ActivityTestRule<RecipeListActivity> mRecipeListActivityTestRule = new ActivityTestRule<>(
            RecipeListActivity.class, false, false);

    @Test
    public void recipeOverviewActivityPhone_openNutellaPieRecipe_shouldBeSuccess() {
        openRecipeListActivity();

        Context context = mRecipeListActivityTestRule.getActivity();

        assumeFalse(isTablet(context));

        openRecipeOverviewActivity();

        checkRecipeOverviewFragment();
    }

    @Test
    public void recipeOverviewActivityTablet_openNutellaPieRecipe_shouldBeSuccess() {
        openRecipeListActivity();

        Context context = mRecipeListActivityTestRule.getActivity();

        assumeTrue(isTablet(context));

        openRecipeOverviewActivity();

        checkRecipeOverviewFragment();
        checkPreparationStepFragment(false, false, false, true, null);

        openFirstPreparationStep();
        checkPreparationStepFragment(true, false, false, false, "Recipe Introduction");

        openPreparationStep(2);
        checkPreparationStepFragment(false, false, false, false,
                "1. Preheat the oven to 350\u00b0F. Butter a 9\" deep dish pie pan.");

        openPreparationStep(3);
        checkPreparationStepFragment(true, false, false, false,
                "2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.");

        openPreparationStep(4);
        checkPreparationStepFragment(true, false, false, false,
                "3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.");

        openPreparationStep(5);
        checkPreparationStepFragment(true, false, false, false,
                "4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.");

        openPreparationStep(6);
        checkPreparationStepFragment(false, true, false, false,
                "5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.");

        openPreparationStep(7);
        checkPreparationStepFragment(false, false, true, false,
                "6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!");
    }

    @Before
    public void setUp() {
        mRecipesListActivityIdlingResource = new RecipesListActivityIdlingResource();

        IdlingRegistry.getInstance().register(mRecipesListActivityIdlingResource);

        startWebServer();
    }

    @After
    public void tearDown() {
        if (mRecipesListActivityIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mRecipesListActivityIdlingResource);
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

    private void checkRecipeOverviewFragment() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        char decimalSeparator = dfs.getDecimalSeparator();

        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText("1 Nutella Pie"))));
        checkRecipeOverviewItem(0, R.id.recipe_ingredients_header_view_title,
                R.string.recipe_ingredients_header_view_text);
        checkRecipeOverviewItem(1, R.id.recipe_ingredient_view_item_name,
                "- 2 CUP Graham Cracker crumbs");
        checkRecipeOverviewItem(2, R.id.recipe_ingredient_view_item_name,
                "- 6 TBLSP unsalted butter, melted");
        checkRecipeOverviewItem(3, R.id.recipe_ingredient_view_item_name,
                "- 0" + decimalSeparator + "5 CUP granulated sugar");
        checkRecipeOverviewItem(4, R.id.recipe_ingredient_view_item_name,
                "- 1" + decimalSeparator + "5 TSP salt");
        checkRecipeOverviewItem(5, R.id.recipe_ingredient_view_item_name, "- 5 TBLSP vanilla");
        checkRecipeOverviewItem(6, R.id.recipe_ingredient_view_item_name,
                "- 1 K Nutella or other chocolate-hazelnut spread");
        checkRecipeOverviewItem(7, R.id.recipe_ingredient_view_item_name,
                "- 500 G Mascapone Cheese(room temperature)");
        checkRecipeOverviewItem(8, R.id.recipe_ingredient_view_item_name,
                "- 1 CUP heavy cream(cold)");
        checkRecipeOverviewItem(9, R.id.recipe_ingredient_view_item_name,
                "- 4 OZ cream cheese(softened)");

        checkRecipeOverviewItem(10, R.id.recipe_preparation_steps_header_view_title,
                R.string.recipe_preparation_steps_header_view_text);
        checkRecipeOverviewItem(11, R.id.recipe_preparation_step_view_text, "Recipe Introduction");
        checkRecipeOverviewItem(12, R.id.recipe_preparation_step_view_text, "Starting prep");
        checkRecipeOverviewItem(13, R.id.recipe_preparation_step_view_text,
                "Prep the cookie crust.");
        checkRecipeOverviewItem(14, R.id.recipe_preparation_step_view_text,
                "Press the crust into baking form.");
        checkRecipeOverviewItem(15, R.id.recipe_preparation_step_view_text, "Start filling prep");
        checkRecipeOverviewItem(16, R.id.recipe_preparation_step_view_text, "Finish filling prep");
        checkRecipeOverviewItem(17, R.id.recipe_preparation_step_view_text, "Finishing Steps");
    }

    private void checkRecipeOverviewItem(int position, int textViewId, String expectedValue) {
        onView(withId(R.id.recipe_overview_fragment_items))
                .perform(RecyclerViewActions.scrollToPosition(position));

        try {
            // This call must be done in order to avoid a bug (related to recycler view scrolling)
            // when running all tests at once.
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // Do nothing
        }

        onView(withRecyclerView(R.id.recipe_overview_fragment_items).atPositionOnView(position,
                textViewId)).check(matches(withText(expectedValue)));
    }

    private void checkRecipeOverviewItem(int position, int textViewId, int expectedValueResId) {
        onView(withId(R.id.recipe_overview_fragment_items))
                .perform(RecyclerViewActions.scrollToPosition(position));
        onView(withRecyclerView(R.id.recipe_overview_fragment_items).atPositionOnView(position,
                textViewId)).check(matches(withText(expectedValueResId)));
    }
}
