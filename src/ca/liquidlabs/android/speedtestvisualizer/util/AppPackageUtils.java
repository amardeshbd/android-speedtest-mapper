/*
 * Copyright 2013 Liquid Labs Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.liquidlabs.android.speedtestvisualizer.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

import ca.liquidlabs.android.speedtestvisualizer.R;

import java.util.Locale;

/**
 * Collection of utility methods to perform actions.
 */
public class AppPackageUtils {
    private static final String LOG_TAG = AppPackageUtils.class.getSimpleName();

    /**
     * Get proper intent for SpeedTest app
     * 
     * @return Returns either a fully-qualified Intent, or null if the package
     *         does not contain such an activity.
     */
    public static Intent getSpeedTestAppIntent(final Context context) {
        // get intent by KNOWN Package Name
        return context.getPackageManager().getLaunchIntentForPackage(
                AppConstants.PACKAGE_SPEEDTEST_APP);
    }

    /**
     * Helper method to quickly check if SpeedTest app is installed
     * 
     * @param context Application context
     * @return {@code true} if SpeedTest app exist, {@code false} otherwise
     */
    public static boolean isSpeedTestAppInstalled(final Context context) {
        return isAppInstalled(context, AppConstants.PACKAGE_SPEEDTEST_APP);
    }

    /**
     * @param context Application context
     * @param packageName Fully qualified package name of app
     * @return {@code true} when installed, {@code false} otherwise
     */
    public static boolean isAppInstalled(final Context context, final String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            Tracer.debug(LOG_TAG, packageName + " - isAppInstalled : true");

            // app package exists
            return true;
        } catch (NameNotFoundException e) {
            Tracer.debug(LOG_TAG, packageName + " - isAppInstalled : false");

            // unable to get the app - its not installed
            return false;
        }
    }

    /**
     * Get drawable icon for application by package name. To avoid null value
     * you must call {@link #isSpeedTestAppInstalled(Context)} before
     * 
     * @param context Application context
     * @param packageName Fully qualified package name of app
     * @return {@code null} when no app is found, otherwise {@link Drawable}
     *         icon of app
     */
    public static Drawable getAppIcon(final Context context, final String packageName) {
        try {
            return context.getPackageManager().getApplicationIcon(packageName);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    /**
     * Util method to get app name and version.
     * 
     * @param context Application context
     * @param packageName Fully qualified package name of app
     * @return Human readable version info of the application
     */
    public static String getApplicationVersionInfo(final Context context, final String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_ACTIVITIES);
        } catch (NameNotFoundException e) {
            // unable to find application
            return context.getString(R.string.speedtest_app_name) + " app was not found.";
        }

        String appName = "N/A";
        if (packageInfo.applicationInfo != null) {
            appName = (String) context.getPackageManager().getApplicationLabel(
                    packageInfo.applicationInfo);
        }
        return String.format(Locale.US, "\nApp: %s\nVersion: %s\nLanguage: %s\n",
                appName, packageInfo.versionName, Locale.getDefault().getLanguage());
    }

    /**
     * Prepares an intent to report issue via email.
     * 
     * @return Intent to launch to send email.
     */
    public static Intent getReportIssueIntent(final Context context) {
        Intent reportIssueIntent = new Intent(Intent.ACTION_SEND);
        reportIssueIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {
                AppConstants.CONTANT_EMAIL
        });
        reportIssueIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                context.getString(R.string.report_issue_subject,
                        context.getString(R.string.app_name)));
        reportIssueIntent.putExtra(
                Intent.EXTRA_TEXT,
                context.getString(R.string.report_issue_body,
                        AppPackageUtils
                                .getApplicationVersionInfo(context,
                                        AppConstants.PACKAGE_SPEEDTEST_APP)));
        reportIssueIntent.setType(AppConstants.EMAIL_MIME_TYPE);
        return reportIssueIntent;
    }

    /**
     * Prepares this application's share intent for ShareActionProvider
     * 
     * @param context Application context
     * @return An intent to share application via email or txt
     */
    public static Intent getShareAppIntent(final Context context) {
        Intent shareAppIntent = new Intent(Intent.ACTION_SEND);
        shareAppIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                context.getString(R.string.share_app_subject,
                        context.getString(R.string.speedtest_app_name)));
        shareAppIntent.putExtra(
                Intent.EXTRA_TEXT,
                context.getString(R.string.share_app_body, context.getString(R.string.app_name),
                        AppConstants.PLAY_STORE_BASE_WEB_URI + context.getPackageName()));
        shareAppIntent.setType(AppConstants.PLAIN_TEXT_MIME_TYPE);
        return shareAppIntent;
    }
}
