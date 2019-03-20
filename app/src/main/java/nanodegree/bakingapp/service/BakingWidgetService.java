package nanodegree.bakingapp.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import nanodegree.bakingapp.view.widget.BakingWidgetDataProvider;

public class BakingWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingWidgetDataProvider(this, intent);
    }
}
