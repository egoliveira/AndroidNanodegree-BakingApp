package nanodegree.bakingapp.model.repo.db;

import android.net.Uri;

public abstract class BaseContract {
    public static final String AUTHORITY = "nanodegree.bakingapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
}
