package nanodegree.bakingapp.view.widget;

import java.util.List;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.RemoteViews;

import nanodegree.bakingapp.R;
import nanodegree.bakingapp.model.repo.RecipeRepository;
import nanodegree.bakingapp.model.vo.Recipe;
import nanodegree.bakingapp.service.BakingWidgetService;
import nanodegree.bakingapp.view.recipes.RecipeListActivity;

public class BakingWidgetProvider extends AppWidgetProvider {
    private static final String PREVIOUS_RECIPE_EXTRA = "nanodegree.bakingapp.widget.EXTRA.PREVIOUS_RECIPE";

    private static final String NEXT_RECIPE_EXTRA = "nanodegree.bakingapp.widget.EXTRA.NEXT_RECIPE";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        List<Recipe> recipes = getRecipes(context);

        for (int appWidgetId : appWidgetIds) {
            displayNextRecipe(appWidgetId, recipes, appWidgetManager, context);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean updateWidget = AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction());
        boolean nextRecipe = intent.getBooleanExtra(NEXT_RECIPE_EXTRA, false);
        boolean previousRecipe = intent.getBooleanExtra(PREVIOUS_RECIPE_EXTRA, false);

        if (updateWidget && (nextRecipe || previousRecipe)) {
            int widgetId = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)[0];
            List<Recipe> recipes = getRecipes(context);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            if (nextRecipe) {
                displayNextRecipe(widgetId, recipes, appWidgetManager, context);
            } else {
                displayPreviousRecipe(widgetId, recipes, appWidgetManager, context);
            }
        } else {
            super.onReceive(context, intent);
        }
    }

    private static void displayNextRecipe(int appWidgetId, List<Recipe> recipes,
        AppWidgetManager appWidgetManager, Context context) {
        Recipe recipeToDisplay = getNextRecipeToDisplay(appWidgetId, recipes, context);

        updateAppWidget(appWidgetId, recipes, recipeToDisplay, appWidgetManager, context);
    }

    private static void displayPreviousRecipe(int appWidgetId, List<Recipe> recipes,
        AppWidgetManager appWidgetManager, Context context) {
        Recipe recipeToDisplay = getPreviousRecipeToDisplay(appWidgetId, recipes, context);

        updateAppWidget(appWidgetId, recipes, recipeToDisplay, appWidgetManager, context);
    }

    private static void updateAppWidget(int appWidgetId, List<Recipe> recipes, Recipe recipe,
        AppWidgetManager appWidgetManager, Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

        Intent launchIntent = new Intent(context, RecipeListActivity.class);

        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (recipes.isEmpty()) {
            views.setViewVisibility(R.id.baking_widget_no_recipes_message, View.VISIBLE);
            views.setViewVisibility(R.id.baking_widget_recipe_title, View.GONE);
            views.setViewVisibility(R.id.baking_widget_recipe_ingredients, View.GONE);
            views.setViewVisibility(R.id.baking_widget_next_recipe, View.INVISIBLE);
            views.setViewVisibility(R.id.baking_widget_previous_recipe, View.INVISIBLE);
        } else {
            views.setViewVisibility(R.id.baking_widget_no_recipes_message, View.GONE);
            views.setTextViewText(R.id.baking_widget_recipe_title, recipe.getName());

            if (recipes.size() > 1) {
                views.setViewVisibility(R.id.baking_widget_next_recipe, View.VISIBLE);
                views.setViewVisibility(R.id.baking_widget_previous_recipe, View.VISIBLE);

                int[] widgetId = new int[] { appWidgetId };

                Intent nextRecipeIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                nextRecipeIntent.putExtra(NEXT_RECIPE_EXTRA, true);
                nextRecipeIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetId);
                PendingIntent nextRecipePendingIntent = PendingIntent.getBroadcast(context, 1,
                        nextRecipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.baking_widget_next_recipe,
                        nextRecipePendingIntent);

                Intent previousRecipeIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                previousRecipeIntent.putExtra(PREVIOUS_RECIPE_EXTRA, true);
                previousRecipeIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetId);
                PendingIntent previousRecipePendingIntent = PendingIntent.getBroadcast(context, 2,
                        previousRecipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.baking_widget_previous_recipe,
                        previousRecipePendingIntent);
            } else {
                views.setViewVisibility(R.id.baking_widget_next_recipe, View.INVISIBLE);
                views.setViewVisibility(R.id.baking_widget_previous_recipe, View.INVISIBLE);
            }

            Intent intent = new Intent(context, BakingWidgetService.class);
            intent.putExtra("recipe_id", recipe.getId());
            // https://stackoverflow.com/questions/13199904/android-home-screen-widget-remoteviews-setremoteadapter-method-not-working
            intent.putExtra("random", Math.random());
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            views.setRemoteAdapter(R.id.baking_widget_recipe_ingredients, intent);

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,
                    R.id.baking_widget_recipe_ingredients);

            launchIntent.putExtra("recipe_id", recipe.getId());

            getWidgetSharedPreferences(context).edit()
                    .putInt(Integer.toString(appWidgetId), recipe.getId()).apply();
        }

        PendingIntent launchPendingIntent = PendingIntent.getActivity(context, 3, launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.baking_widget_container, launchPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static List<Recipe> getRecipes(Context context) {
        RecipeRepository repository = new RecipeRepository();

        return repository.getAllRecipes(context);
    }

    private static int getRecipeIndex(int recipeId, List<Recipe> recipes) {
        Recipe r = new Recipe();
        r.setId(recipeId);

        return recipes.indexOf(r);
    }

    private static Recipe getNextRecipeToDisplay(int appWidgetId, List<Recipe> recipes,
        Context context) {
        Recipe recipe = null;

        if ((recipes != null) && (!recipes.isEmpty())) {
            SharedPreferences sp = getWidgetSharedPreferences(context);

            int recipeId = sp.getInt(Integer.toString(appWidgetId), -1);

            if (recipeId == -1) {
                recipe = recipes.get(0);
            } else {
                int index = getRecipeIndex(recipeId, recipes);

                if (index != -1) {
                    index = (index + 1) % recipes.size();
                } else {
                    index = 0;
                }

                recipe = recipes.get(index);
            }
        }

        return recipe;
    }

    private static Recipe getPreviousRecipeToDisplay(int appWidgetId, List<Recipe> recipes,
        Context context) {
        Recipe recipe = null;

        if ((recipes != null) && (!recipes.isEmpty())) {
            SharedPreferences sp = getWidgetSharedPreferences(context);

            int recipeId = sp.getInt(Integer.toString(appWidgetId), -1);

            if (recipeId == -1) {
                recipe = recipes.get(0);
            } else {
                int index = getRecipeIndex(recipeId, recipes);

                if (index != -1) {
                    index = (index - 1) % recipes.size();

                    if (index < 0) {
                        index = recipes.size() + index;
                    }
                } else {
                    index = 0;
                }

                recipe = recipes.get(index);
            }
        }

        return recipe;
    }

    private static SharedPreferences getWidgetSharedPreferences(Context context) {
        return context.getSharedPreferences("BakingWidget", Context.MODE_PRIVATE);
    }
}
