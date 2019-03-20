package nanodegree.bakingapp.model.repo.db;

import android.net.Uri;
import android.provider.BaseColumns;

public final class IngredientContract extends BaseContract {
    public static final String INGREDIENT_PATH = "ingredients";

    private IngredientContract() {
    }

    public static final class IngredientEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,
                INGREDIENT_PATH);

        public static final String TABLE_NAME = "ingredient";

        public static final String RECIPE_ID_COLUMN = "recipe_id";

        public static final String INGREDIENT_COLUMN = "ingredient";

        public static final String QUANTITY_COLUMN = "quantity";

        public static final String MEASURE_COLUMN = "measure";
    }
}
