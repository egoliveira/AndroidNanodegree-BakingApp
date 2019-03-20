package com.danielebottillo.blog.config;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

public class DrawableMatcher extends TypeSafeMatcher<View> {

    private final int drawableResIdId;
    private final boolean expected;
    private String resourceName;
    static final int EMPTY = -1;
    static final int ANY = -2;

    public DrawableMatcher(int drawableResIdId, boolean expected) {
        super(View.class);
        this.drawableResIdId = drawableResIdId;
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(View target) {
        if (!(target instanceof ImageView)) {
            return false;
        }
        ImageView imageView = (ImageView) target;
        if (drawableResIdId == EMPTY) {
            return (imageView.getDrawable() == null && expected);
        }

        if (drawableResIdId == ANY) {
            return imageView.getDrawable() != null && expected;
        }

        Resources resources = target.getContext().getResources();
        Drawable expectedDrawable = resources.getDrawable(drawableResIdId);
        resourceName = resources.getResourceEntryName(drawableResIdId);

        if (expectedDrawable == null) {
            return false;
        }

        Bitmap bitmap = getBitmap(imageView.getDrawable());
        Bitmap otherBitmap = getBitmap(expectedDrawable);
        return bitmap.sameAs(otherBitmap) == expected;
    }

    private Bitmap getBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with drawable from resource id: ");
        description.appendValue(drawableResIdId);
        if (resourceName != null) {
            description.appendText("[");
            description.appendText(resourceName);
            description.appendText("]");
        }
    }

    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId, true);
    }

    public static Matcher<View> withoutDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId, false);
    }

    public static Matcher<View> noDrawable() {
        return new DrawableMatcher(-1, true);
    }
}