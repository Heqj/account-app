package com.hqj.account.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class NetworkUtil {

    public static boolean hasNetwork(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
            if (activeNetInfo != null) {
                return activeNetInfo.isAvailable();
            }
        }
        return false;
    }
}
