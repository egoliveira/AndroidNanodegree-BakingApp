package nanodegree.bakingapp;

import android.app.Activity;

import androidx.test.espresso.IdlingResource;
import androidx.test.runner.lifecycle.ActivityLifecycleCallback;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

public abstract class AbstractIdlingResource<T extends Activity> implements IdlingResource {
    private final Class<T> mActivityClass;

    private boolean mForeground;

    private boolean mLastIdle;

    private ResourceCallback mCallback;

    private final LifecycleCallback mLifecycleCallback;

    public AbstractIdlingResource(Class<T> activityClass) {
        this.mActivityClass = activityClass;

        this.mLifecycleCallback = new LifecycleCallback();

        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback(mLifecycleCallback);

        mLastIdle = true;
    }

    @Override
    public boolean isIdleNow() {
        return mLastIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.mCallback = callback;
    }

    protected void notifyUpdate() {
        boolean idle = true;

        if (mForeground) {
            idle = isIdle();

            if ((mCallback != null) && (mLastIdle != idle) && idle) {
                mCallback.onTransitionToIdle();
            }
        }

        mLastIdle = idle;
    }

    protected abstract boolean isIdle();

    protected abstract void onActivityCreated(T activity);

    private class LifecycleCallback implements ActivityLifecycleCallback {
        @Override
        public void onActivityLifecycleChanged(Activity activity, Stage stage) {
            if (activity.getClass().isAssignableFrom(mActivityClass)) {
                switch (stage) {
                case CREATED:
                    onActivityCreated((T) activity);
                    break;
                case RESUMED:
                    mForeground = true;
                    break;
                case STOPPED:
                    mForeground = false;
                    break;
                default:
                    break;
                }
            }
        }
    }
}
