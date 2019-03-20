package nanodegree.bakingapp.model.repo.db;

import android.net.Uri;
import android.provider.BaseColumns;

public final class RecipeContract extends BaseContract {
    public static final String RECIPES_PATH = "recipes";

    private RecipeContract() {
    }

    public static final class RecipeEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, RECIPES_PATH);

        public static final String TABLE_NAME = "recipe";

        public static final String NAME_COLUMN = "name";

        public static final String SERVINGS_COLUMN = "servings";

        public static final String IMAGE_COLUMN = "image";
    }
}
