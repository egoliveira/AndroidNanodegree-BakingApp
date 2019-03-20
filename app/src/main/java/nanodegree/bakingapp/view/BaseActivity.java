package nanodegree.bakingapp.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import nanodegree.bakingapp.R;

public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());

        mToolbar = findViewById(R.id.toolbar);
    }

    protected final Toolbar getToolbar() {
        return mToolbar;
    }

    protected abstract int getLayoutResId();
}
