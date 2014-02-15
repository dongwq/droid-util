package com.teambition.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * User: peng
 * Date: 13-3-13
 * Time: 下午1:27
 * To change this template use File | Settings | File Templates.
 */
public class AndroidUtil {

    private static final Configuration CONFIGURATION_INFINITE = new Configuration.Builder()
            .setDuration(Configuration.DURATION_INFINITE)
            .build();



    // 获取AppKey
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return apiKey;
    }

    /**
     * 显示提示信息
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int msgId) {
        Toast.makeText(context, context.getString(msgId), Toast.LENGTH_SHORT).show();
    }

    public static void showCrouton(Activity context, String msg, Style style) {
        Crouton.makeText(context, msg, style).show();
    }

    public static void showCrouton(Activity context, int msg, Style style) {
        Crouton.makeText(context, context.getResources().getString(msg), style).show();
    }

    public static void showCroutonAlert(Activity context, String msg) {
        Crouton.makeText(context, msg, Style.ALERT).show();
    }

    public static void showCroutonAlertAlways(Activity context, String msg) {
        Crouton.makeText(context, msg, Style.ALERT).setConfiguration(CONFIGURATION_INFINITE).show();
    }

    public static void showCroutonInfo(Activity context, String msg) {
        Crouton.makeText(context, msg, Style.INFO).show();
    }

    public static void showCroutonConfirm(Activity context, String msg) {
        Crouton.makeText(context, msg, Style.CONFIRM).show();
    }

    public static void showCroutonAlert(Activity context, int msg) {
        Crouton.makeText(context, context.getResources().getString(msg), Style.ALERT).show();
    }

    public static void showToast(final Activity activity, final String msg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }



    public final static int TB_NOTIFY_CATION_ID = 201307;

    /**
     * 删除通知提示
     */
    public static void clearNotification(Context context) {
        NotificationManager manager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(TB_NOTIFY_CATION_ID);
    }

    /**
     * 动态设置listview高度
     */
    public static void setListViewHeightBasedOnChildren(ListView listView, int expand) {
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height += expand;//if without this statement,the listview will be a little short
        listView.setLayoutParams(params);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem != null) {
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    /**
     * 隐藏或者显示键盘
     */
    public static void toggleSoftInput(final Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 10);
    }

    /**
     * 文件大小单位转换
     */
    public static String setFileSize(float size) {
        DecimalFormat df = new DecimalFormat("###.##");

        float fSize = size;
        float f = (fSize / (float) (1024 * 1024));
        if (f < 1.0) {
            float f2 = (fSize / (float) (1024));

            return df.format(f2) + "KB";
        } else {
            return df.format(f) + "M";
        }
    }

    /**
     * 获取屏幕宽，高
     */
    public static Point getScreenSize(Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return point;
    }

    /**
     * 获取屏幕宽，高
     */
    public static int getScreenSize(Activity activity, boolean isWidth) {
        int size;
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        if (isWidth) {
            size = point.x;
        } else {
            size = point.y;
        }
        return size;
    }

    /**
     * 获取屏幕宽，高
     */
    public static int getScreenWidth(Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return point.x;
    }

    /**
     * 判断SdCard是否存在
     */
    public static boolean isSdCardNotExisted() {
        return isSDCardExisted();
    }

    public static boolean isSDCardExisted() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 设置view延迟显示
     */
    public static void showView(final View layout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setVisibility(View.GONE);
            }
        }, 5000);

        layout.setVisibility(View.VISIBLE);
    }

    /**
     * 调用手机自带邮件发送功能
     */
    public static void openSendMail(Context context, String email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "title");
        intent.putExtra(Intent.EXTRA_TEXT, "body");
        context.startActivity(Intent.createChooser(intent, ""));
    }


    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException ignore) {
        }
        return packageInfo;
    }


    public static String getAppInfo(Context context) {
        String str = "\r\n \n\n\n -------------------\n Teambition VersionName=%s,\n VersionCode=%d,\n OS Version=%s,\n Model=%s \n------------------";


        return String.format(str, AndroidUtil.getVersionName(context), AndroidUtil.getVersionCode(context), Build.VERSION.RELEASE, Build.MODEL);
    }

}
