package nanodegree.bakingapp.mechanism.cache;

import java.io.File;

import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

import android.content.Context;

public final class ExoPlayerCache {
    private static Cache sInstance;

    public static synchronized Cache getInstance(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }

        if (sInstance == null) {
            File cacheFolder = new File(context.getCacheDir(), "exoplayer");

            if (!cacheFolder.exists()) {
                cacheFolder.mkdir();
            }

            if (cacheFolder.exists() && cacheFolder.isDirectory()) {
                sInstance = new SimpleCache(cacheFolder, new NoOpCacheEvictor());
            } else {
                throw new IllegalStateException("Can't create the cache instance.");
            }
        }

        return sInstance;
    }

    private ExoPlayerCache() {
    }
}
