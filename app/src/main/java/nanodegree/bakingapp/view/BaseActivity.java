package nanodegree.bakingapp.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import nanodegree.bakingapp.R;

public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());

        mToolbar = findViewById(R.id.toolbar);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = false;

        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            handled = true;
            break;
        default:
            super.onOptionsItemSelected(item);
            break;
        }

        return handled;
    }

    protected final Toolbar getToolbar() {
        return mToolbar;
    }

    protected abstract int getLayoutResId();
}
