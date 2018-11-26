package com.hqj.account.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * ReWebChomeClient
 *
 * @Author KenChung
 */
public class MyWebChromeClient extends WebChromeClient {
    private View myView;
    private CustomViewCallback myCallback;
    private WebView webView;
    private Window window;
    private Activity activity;

    public MyWebChromeClient() {}

    public MyWebChromeClient(WebView webView, Window window, Activity activity) {
        this.window = window;
        this.webView = webView;
        this.activity = activity;
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (myCallback != null) {
            myCallback.onCustomViewHidden();
            myCallback = null ;
            return;
        }
        ViewGroup parent = (ViewGroup) webView.getParent();
        parent.removeView( webView);
        parent.addView(view);
        myView = view;
        myCallback = callback;
        if(activity.getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
    }

    public void onHideCustomView() {
        if (myView != null) {
            if (myCallback != null) {
                myCallback.onCustomViewHidden();
                myCallback = null ;
            }
            ViewGroup parent = (ViewGroup) myView.getParent();
            parent.removeView( myView);
            parent.addView( webView);
            myView = null;
        }
        if(activity.getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //设置当前窗体为非全屏显示
        window.setFlags(0, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 界面加载情况
     * @param view
     * @param title
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        // android 6.0 以下通过title获取
    }

}