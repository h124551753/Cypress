package com.molmc.core.http;

import android.content.Context;

import com.molmc.core.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * features: 设置okhttp cache
 * Author：  hhe on 16-9-1 11:50
 * Email：   hui@molmc.com
 */

public class CacheInterceptor {

    private static final long CACHE_SIZE = 1024 * 1024 * 50;   //缓存可用大小为50M

    /**
     * 构建缓存
     *
     * @return
     */
    public static Cache buildCache(Context context, String cacheName) {
        final File baseDir = context.getCacheDir();
        final File cacheDir = new File(baseDir, cacheName);
        return new Cache(cacheDir, CACHE_SIZE);
    }

    /**
     * 构建缓存中断
     */
    public static ZCacheInterceptor buildInterceptor(Context context) {
        return new ZCacheInterceptor(context);
    }

    public static class ZCacheInterceptor implements Interceptor {

        private Context mContext;

        public ZCacheInterceptor(Context context) {
            mContext = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtil.isNetworkAvailable(mContext)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response originalResponse = chain.proceed(request);
            if (NetworkUtil.isNetworkAvailable(mContext)) {
                String cacheControl = request.cacheControl().toString();

                // Add cache control header for response same as request's while network is available.
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 7 * 4;     //离线缓存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    }


}
