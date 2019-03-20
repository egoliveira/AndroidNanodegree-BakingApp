package nanodegree.bakingapp.model.repo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BakingDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "baking.db";

    private static final int DB_VERSION = 1;

    private static final String CREATE_RECIPE_TABLE = "CREATE TABLE "
            + RecipeContract.RecipeEntry.TABLE_NAME + " (" + RecipeContract.RecipeEntry._ID
            + " INTEGER PRIMARY KEY, " + RecipeContract.RecipeEntry.NAME_COLUMN + " TEXT NOT NULL, "
            + RecipeContract.RecipeEntry.SERVINGS_COLUMN + " INTEGER NOT NULL, "
            + RecipeContract.RecipeEntry.IMAGE_COLUMN + " TEXT)";

    private static final String CREATE_INGREDIENT_TABLE = "CREATE TABLE "
            + IngredientContract.IngredientEntry.TABLE_NAME + " ("
            + IngredientContract.IngredientEntry._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + IngredientContract.IngredientEntry.RECIPE_ID_COLUMN + " INTEGER NOT NULL, "
            + IngredientContract.IngredientEntry.INGREDIENT_COLUMN + " TEXT NOT NULL, "
            + IngredientContract.IngredientEntry.QUANTITY_COLUMN + " REAL NOT NULL, "
            + IngredientContract.IngredientEntry.MEASURE_COLUMN + " TEXT NOT NULL, FOREIGN KEY("
            + IngredientContract.IngredientEntry.RECIPE_ID_COLUMN + ") REFERENCES "
            + RecipeContract.RecipeEntry.TABLE_NAME + "(" + RecipeContract.RecipeEntry._ID
            + ") ON UPDATE NO ACTION ON DELETE CASCADE)";

    private static final String CREATE_PREPARATION_STEP_TABLE = "CREATE TABLE "
            + PreparationStepContract.PreparationStepEntry.TABLE_NAME + " ("
            + PreparationStepContract.PreparationStepEntry._ID + " INTEGER PRIMARY KEY, "
            + PreparationStepContract.PreparationStepEntry.RECIPE_ID_COLUMN + " INTEGER NOT NULL, "
            + PreparationStepContract.PreparationStepEntry.STEP_ID_COLUMN + " INTEGER NOT NULL, "
            + PreparationStepContract.PreparationStepEntry.SHORT_DESCRIPTION_COLUMN
            + " TEXT NOT NULL, " + PreparationStepContract.PreparationStepEntry.DESCRIPTION_COLUMN
            + " TEXT NOT NULL, " + PreparationStepContract.PreparationStepEntry.VIDEO_URL_COLUMN
            + " TEXT, " + PreparationStepContract.PreparationStepEntry.THUMBNAIL_URL_COLUMN
            + " TEXT, FOREIGN KEY(" + PreparationStepContract.PreparationStepEntry.RECIPE_ID_COLUMN
            + ") REFERENCES " + RecipeContract.RecipeEntry.TABLE_NAME + "("
            + RecipeContract.RecipeEntry._ID + ") ON UPDATE NO ACTION ON DELETE CASCADE)";

    public BakingDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECIPE_TABLE);
        db.execSQL(CREATE_INGREDIENT_TABLE);
        db.execSQL(CREATE_PREPARATION_STEP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
