package com.hqj.account.utils;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.DOWNLOAD_SERVICE;

public class UpdateUtil {

    public static void doDownload(String url, final String must, final Context context) {
        final DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        final long id = downloadManager.enqueue(request);
        // 创建ProgressDialog类
        final ProgressDialog pg = new ProgressDialog(context);
        // 设置进度对话框为水平进度条风格
        pg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pg.setMessage("正在下载...");
        // 设置在点击Dialog外是否取消Dialog进度条  默认true
        // 设置最大值
        pg.setMax(100);
        String buttonText = "取消";
        if (must.equals("Y")) {
            buttonText = "退出";
            pg.setCancelable(false);
        } else {
            pg.setCancelable(true);
        }
        pg.setButton(DialogInterface.BUTTON_POSITIVE, buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 通过删除消息代码的方式停止定时器
                //progressDialogHandler.removeMessages(1);
                downloadManager.remove(id);
                dialog.dismiss();
                if (must.equals("Y")) {
                    System.exit(0);
                }
            }
        });
        pg.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                int pro = bundle.getInt("pro");
                pg.setProgress(pro);
                if (pro >= 100) {
                    pg.dismiss();
                }
            }
        };

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setAllowedOverRoaming(false);
        request.setMimeType("application/vnd.android.package-archive");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "yingfan.apk");
        request.setDestinationUri(Uri.fromFile(file));
        final String filePath = Uri.fromFile(file).getPath();


        final  DownloadManager.Query query = new DownloadManager.Query();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Cursor cursor = downloadManager.query(query.setFilterById(id));
                if (cursor != null && cursor.moveToFirst()) {
                    if(cursor.getInt(
                            cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        pg.setProgress(100);
                        //install(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/yingfan.apk" );
                        InstallUtil.install(context, filePath);
                        this.cancel();
                        if(must.equals("Y")) {
                            System.exit(0);
                        }
                    }
                    String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                    //String address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    long bytes_downloaded = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    long bytes_total = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    int pro =  (int) ((bytes_downloaded * 100) / bytes_total);
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pro",pro);
                    bundle.putString("name",title);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
                cursor.close();
            }
        };
        timer.schedule(task, 0,1000);
    }
}
