package nanodegree.bakingapp.model.repo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import io.reactivex.Completable;
import io.reactivex.Single;
import nanodegree.bakingapp.model.repo.db.IngredientContract;
import nanodegree.bakingapp.model.repo.db.PreparationStepContract;
import nanodegree.bakingapp.model.repo.db.RecipeContract;
import nanodegree.bakingapp.model.repo.ws.BakingRemoteServicesFactory;
import nanodegree.bakingapp.model.vo.Ingredient;
import nanodegree.bakingapp.model.vo.PreparationStep;
import nanodegree.bakingapp.model.vo.Recipe;

public class RecipeRepository {
    public Single<List<Recipe>> getRemoteRecipes() {
        return BakingRemoteServicesFactory.create().getRecipes();
    }

    public Completable saveRecipe(Recipe recipe, Context context) {
        return Completable.create(emitter -> {
            ContentResolver resolver = context.getContentResolver();

            resolver.delete(RecipeContract.RecipeEntry.CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(recipe.getId())).build(), null, null);

            ContentValues values = new ContentValues();

            values.put(RecipeContract.RecipeEntry._ID, recipe.getId());
            values.put(RecipeContract.RecipeEntry.NAME_COLUMN, recipe.getName());
            values.put(RecipeContract.RecipeEntry.SERVINGS_COLUMN, recipe.getServings());
            values.put(RecipeContract.RecipeEntry.IMAGE_COLUMN, recipe.getImage());

            resolver.insert(RecipeContract.RecipeEntry.CONTENT_URI, values);

            List<Ingredient> ingredientsList = recipe.getIngredients();

            if ((ingredientsList != null) && (!ingredientsList.isEmpty())) {
                ContentValues[] ingredientsValues = new ContentValues[ingredientsList.size()];

                for (int i = 0; i < ingredientsList.size(); i++) {
                    Ingredient ingredient = ingredientsList.get(i);
                    ingredientsValues[i] = new ContentValues();

                    ingredientsValues[i].put(IngredientContract.IngredientEntry.RECIPE_ID_COLUMN,
                            recipe.getId());
                    ingredientsValues[i].put(IngredientContract.IngredientEntry.INGREDIENT_COLUMN,
                            ingredient.getIngredient());
                    ingredientsValues[i].put(IngredientContract.IngredientEntry.MEASURE_COLUMN,
                            ingredient.getMeasure());
                    ingredientsValues[i].put(IngredientContract.IngredientEntry.QUANTITY_COLUMN,
                            ingredient.getQuantity());
                }

                resolver.bulkInsert(IngredientContract.IngredientEntry.CONTENT_URI,
                        ingredientsValues);
            }

            List<PreparationStep> stepsList = recipe.getSteps();

            if ((stepsList != null) && (!stepsList.isEmpty())) {
                ContentValues[] stepsValues = new ContentValues[stepsList.size()];

                for (int i = 0; i < stepsList.size(); i++) {
                    PreparationStep step = stepsList.get(i);
                    stepsValues[i] = new ContentValues();

                    stepsValues[i].put(
                            PreparationStepContract.PreparationStepEntry.RECIPE_ID_COLUMN,
                            recipe.getId());
                    stepsValues[i].put(PreparationStepContract.PreparationStepEntry.STEP_ID_COLUMN,
                            step.getStepId());
                    stepsValues[i].put(
                            PreparationStepContract.PreparationStepEntry.SHORT_DESCRIPTION_COLUMN,
                            step.getShortDescription());
                    stepsValues[i].put(
                            PreparationStepContract.PreparationStepEntry.DESCRIPTION_COLUMN,
                            step.getDescription());
                    stepsValues[i].put(
                            PreparationStepContract.PreparationStepEntry.VIDEO_URL_COLUMN,
                            step.getVideoURL());
                    stepsValues[i].put(
                            PreparationStepContract.PreparationStepEntry.THUMBNAIL_URL_COLUMN,
                            step.getThumbnailURL());
                }

                resolver.bulkInsert(PreparationStepContract.PreparationStepEntry.CONTENT_URI,
                        stepsValues);
            }
        });
    }

    public List<Recipe> getAllRecipes(Context context) {
        List<Recipe> recipes = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = resolver.query(RecipeContract.RecipeEntry.CONTENT_URI, null, null, null,
                RecipeContract.RecipeEntry._ID + " ASC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Recipe recipe = new Recipe();

                recipe.setId(cursor.getInt(cursor.getColumnIndex(RecipeContract.RecipeEntry._ID)));
                recipe.setName(cursor
                        .getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.NAME_COLUMN)));
                recipe.setServings(cursor
                        .getInt(cursor.getColumnIndex(RecipeContract.RecipeEntry.SERVINGS_COLUMN)));
                recipe.setImage(cursor
                        .getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.IMAGE_COLUMN)));

                recipes.add(recipe);
            }

            cursor.close();
        }

        return recipes;
    }
}
