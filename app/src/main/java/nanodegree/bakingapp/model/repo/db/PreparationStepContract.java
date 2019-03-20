package nanodegree.bakingapp.model.repo.db;

import android.net.Uri;
import android.provider.BaseColumns;

public final class PreparationStepContract extends BaseContract {
    public static final String PREPARATION_STEP_PATH = "preparation_steps";

    private PreparationStepContract() {
    }

    public static final class PreparationStepEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,
                PREPARATION_STEP_PATH);

        public static final String TABLE_NAME = "preparation_step";

        public static final String RECIPE_ID_COLUMN = "recipe_id";

        public static final String STEP_ID_COLUMN = "step_id";

        public static final String DESCRIPTION_COLUMN = "description";

        public static final String SHORT_DESCRIPTION_COLUMN = "short_description";

        public static final String VIDEO_URL_COLUMN = "video_url";

        public static final String THUMBNAIL_URL_COLUMN = "thumbnail_url";
    }
}
