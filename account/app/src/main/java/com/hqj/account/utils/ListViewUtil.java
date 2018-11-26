package com.hqj.account.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class ListViewUtil {

    public static void setListViewBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        //int maxWidth = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            //int width = listItem.getMeasuredWidth();
            //if(width>maxWidth)maxWidth = width;
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //params.width = maxWidth;
        listView.setLayoutParams(params);
    }

    public static void setListViewHeight(ExpandableListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        int count = listAdapter.getCount();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    /*
* ExpandListView自适应高度 根据子项数量
* @param listView
* @param listAdapter listView 的适配器
* */
    public static void setListHeight(ExpandableListView listView, BaseExpandableListAdapter listAdapter) {
        int[] isExpand = new int[] { 0, 0};
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int total = 0;

        View listItem;

        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            listItem = listAdapter.getGroupView(i, false, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            total += (listAdapter.getChildrenCount(0) - 1);
        }

        for(int i = 0; i < isExpand.length ; i++) {

            if (isExpand[i] == 1)

                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    listItem = listAdapter.getChildView(i, j, false, null, listView);
                    listItem.measure(0, 0);
                    totalHeight += listItem.getMeasuredHeight();
                    total += (listAdapter.getChildrenCount(i) - 1);
                }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * total);

        listView.setLayoutParams(params);
    }

}
