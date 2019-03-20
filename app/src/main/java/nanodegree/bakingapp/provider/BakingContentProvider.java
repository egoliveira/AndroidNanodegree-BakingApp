package nanodegree.bakingapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import nanodegree.bakingapp.model.repo.db.BakingDatabaseHelper;
import nanodegree.bakingapp.model.repo.db.BaseContract;
import nanodegree.bakingapp.model.repo.db.IngredientContract;
import nanodegree.bakingapp.model.repo.db.PreparationStepContract;
import nanodegree.bakingapp.model.repo.db.RecipeContract;

public class BakingContentProvider extends ContentProvider {
    private static final int RECIPES = 100;

    private static final int RECIPE_ID = 101;

    private static final int INGREDIENTS = 200;

    private static final int INGREDIENT_ID = 201;

    private static final int PREPARATION_STEPS = 300;

    private static final int PREPARATION_STEP_ID = 301;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(BaseContract.AUTHORITY, RecipeContract.RECIPES_PATH, RECIPES);
        URI_MATCHER.addURI(BaseContract.AUTHORITY, RecipeContract.RECIPES_PATH + "/#", RECIPE_ID);
        URI_MATCHER.addURI(BaseContract.AUTHORITY, IngredientContract.INGREDIENT_PATH, INGREDIENTS);
        URI_MATCHER.addURI(BaseContract.AUTHORITY, IngredientContract.INGREDIENT_PATH + "/#",
                INGREDIENT_ID);
        URI_MATCHER.addURI(BaseContract.AUTHORITY, PreparationStepContract.PREPARATION_STEP_PATH,
                PREPARATION_STEPS);
        URI_MATCHER.addURI(BaseContract.AUTHORITY,
                PreparationStepContract.PREPARATION_STEP_PATH + "/#", PREPARATION_STEP_ID);

    }

    private BakingDatabaseHelper mHelper;

    @Override
    public boolean onCreate() {
        mHelper = new BakingDatabaseHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mHelper.getReadableDatabase();

        int match = URI_MATCHER.match(uri);
        Cursor cursor;

        switch (match) {
        case RECIPES:
            cursor = db.query(RecipeContract.RecipeEntry.TABLE_NAME, projection, selection,
                    selectionArgs, null, null, sortOrder);
            break;
        case RECIPE_ID:
            // Convenience query
            String recipeId = uri.getPathSegments().get(1);
            cursor = db.query(RecipeContract.RecipeEntry.TABLE_NAME, projection, "_id=?",
                    new String[] { recipeId }, null, null, sortOrder);
            break;
        case INGREDIENTS:
            cursor = db.query(IngredientContract.IngredientEntry.TABLE_NAME, projection, selection,
                    selectionArgs, null, null, sortOrder);
            break;
        case PREPARATION_STEPS:
            cursor = db.query(PreparationStepContract.PreparationStepEntry.TABLE_NAME, projection,
                    selection, selectionArgs, null, null, sortOrder);
            break;
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();

        int match = URI_MATCHER.match(uri);
        Uri returnUri;

        switch (match) {
        case RECIPES:
            long recipeId = db.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, values);

            if (recipeId > 0) {
                returnUri = ContentUris.withAppendedId(RecipeContract.RecipeEntry.CONTENT_URI,
                        recipeId);
            } else {
                throw new SQLiteException("Failed to insert row into " + uri);
            }
            break;
        case INGREDIENTS:
            long ingredientId = db.insert(IngredientContract.IngredientEntry.TABLE_NAME, null,
                    values);

            if (ingredientId > 0) {
                returnUri = ContentUris.withAppendedId(
                        IngredientContract.IngredientEntry.CONTENT_URI, ingredientId);
            } else {
                throw new SQLiteException("Failed to insert row into " + uri);
            }
            break;
        case PREPARATION_STEPS:
            long preparationStepId = db
                    .insert(PreparationStepContract.PreparationStepEntry.TABLE_NAME, null, values);

            if (preparationStepId > 0) {
                returnUri = ContentUris.withAppendedId(
                        PreparationStepContract.PreparationStepEntry.CONTENT_URI,
                        preparationStepId);
            } else {
                throw new SQLiteException("Failed to insert row into " + uri);
            }
            break;
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
        @Nullable String[] selectionArgs) {
        int count;

        final SQLiteDatabase db = mHelper.getWritableDatabase();

        int match = URI_MATCHER.match(uri);

        switch (match) {
        case RECIPE_ID:
            String id = uri.getPathSegments().get(1);

            db.execSQL("PRAGMA foreign_keys = ON");
            count = db.delete(RecipeContract.RecipeEntry.TABLE_NAME, "_id=?", new String[] { id });

            break;
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (count != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
        @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
