package nanodegree.bakingapp.view.widget;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import nanodegree.bakingapp.R;
import nanodegree.bakingapp.model.repo.db.IngredientContract;
import nanodegree.bakingapp.view.RecipeFormatUtil;

public class BakingWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {
    private final WeakReference<Context> mContext;

    private final int mRecipeId;

    private Cursor mCursor;

    public BakingWidgetDataProvider(Context context, Intent intent) {
        this.mContext = new WeakReference<>(context);
        this.mRecipeId = intent.getIntExtra("recipe_id", 0);
    }

    @Override
    public void onCreate() {
        createCursor();

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
    }

    @Override
    public void onDataSetChanged() {
        createCursor();
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }

        mContext.clear();
    }

    @Override
    public int getCount() {
        int count = 0;

        if (mCursor != null) {
            count = mCursor.getCount();
        }

        return count;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.get().getPackageName(),
                R.layout.recipe_ingredient_widget_view);

        mCursor.moveToPosition(position);

        float quantity = mCursor.getFloat(
                mCursor.getColumnIndex(IngredientContract.IngredientEntry.QUANTITY_COLUMN));
        String measure = mCursor.getString(
                mCursor.getColumnIndex(IngredientContract.IngredientEntry.MEASURE_COLUMN));
        String name = mCursor.getString(
                mCursor.getColumnIndex(IngredientContract.IngredientEntry.INGREDIENT_COLUMN));

        String text = mContext.get().getString(R.string.recipe_ingredient_view_item_name_template,
                RecipeFormatUtil.formatIngredientQuantity(quantity), measure, name);

        remoteViews.setTextViewText(R.id.recipe_ingredient_widget_view_name, text);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void createCursor() {
        if (mCursor != null) {
            mCursor.close();
        }

        final long token = Binder.clearCallingIdentity();

        mCursor = mContext.get().getContentResolver().query(
                IngredientContract.IngredientEntry.CONTENT_URI,
                new String[] { IngredientContract.IngredientEntry.INGREDIENT_COLUMN,
                        IngredientContract.IngredientEntry.MEASURE_COLUMN,
                        IngredientContract.IngredientEntry.QUANTITY_COLUMN },
                IngredientContract.IngredientEntry.RECIPE_ID_COLUMN + "=?",
                new String[] { Integer.toString(mRecipeId) }, null);

        Binder.restoreCallingIdentity(token);
    }
}
