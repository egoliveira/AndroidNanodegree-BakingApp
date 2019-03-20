package nanodegree.bakingapp.viewaction;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

import org.hamcrest.Matcher;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

public class ImageViewDrawableChangeDetector implements ViewAction {
    private final int mDefaultDrawableResId;

    private final long mTimeout;

    public ImageViewDrawableChangeDetector(int defaultDrawableResId, long timeout) {
        this.mDefaultDrawableResId = defaultDrawableResId;
        this.mTimeout = timeout;
    }

    @Override
    public Matcher<View> getConstraints() {
        return allOf(isDisplayed(), isAssignableFrom(ImageView.class));
    }

    @Override
    public String getDescription() {
        return "wait for ImageView drawable changing for (" + mTimeout + ") ms.";
    }

    @Override
    public void perform(UiController uiController, View view) {
        uiController.loopMainThreadUntilIdle();
        long start = System.currentTimeMillis();
        long end = start + mTimeout;

        final ImageView imageView = (ImageView) view;
        boolean changed = false;

        Bitmap lastBitmap = null;
        Drawable lastDrawable;

        if (mDefaultDrawableResId > 0) {
            lastDrawable = ContextCompat.getDrawable(view.getContext(), mDefaultDrawableResId);
        } else {
            lastDrawable = imageView.getDrawable();
        }

        if (lastDrawable instanceof BitmapDrawable) {
            lastBitmap = ((BitmapDrawable) lastDrawable).getBitmap();
        }

        while (!changed && System.currentTimeMillis() < end) {
            if (imageView.getDrawable() instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                changed = bitmap != null && !bitmap.sameAs(lastBitmap);
            }

            uiController.loopMainThreadForAtLeast(50);
        }
    }

    public static ImageViewDrawableChangeDetector waitDrawableChanging(int defaultDrawableResId,
        long timeout) {
        return new ImageViewDrawableChangeDetector(defaultDrawableResId, timeout);
    }
}
