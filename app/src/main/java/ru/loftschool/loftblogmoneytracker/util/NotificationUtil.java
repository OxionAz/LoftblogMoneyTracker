package ru.loftschool.loftblogmoneytracker.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.ui.activity.MainActivity_;

/**
 * Created by Александр on 31.10.2015.
 */
public class NotificationUtil {
    private static final int NOTIFICATION_ID = 12345;

    public static void updateNotification(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String displayNotificationsKey = context.getString(R.string.pref_enable_notifications_key);
        boolean displayNotifications = pref.getBoolean(displayNotificationsKey, Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));
        String enableSoundKey = context.getString(R.string.pref_enable_sound_key);
        boolean enableSound = pref.getBoolean(enableSoundKey, Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));
        String enableVibrationKey = context.getString(R.string.pref_enable_vibration_key);
        boolean enableVibration = pref.getBoolean(enableVibrationKey, Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));
        String enableIndicatorKey = context.getString(R.string.pref_enable_indicator_key);
        boolean enableIndicator = pref.getBoolean(enableIndicatorKey, Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));
        
        if (displayNotifications){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            Intent intent = new Intent(context, MainActivity_.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            builder.setSmallIcon(R.mipmap.ic_launcher);
            if (enableIndicator) builder.setLights(Color.GREEN, 300, 1500);
            if (enableVibration) builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            if (enableSound) builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            builder.setAutoCancel(true);

            String title = context.getString(R.string.app_name);
            String contentText = context.getString(R.string.notification_message);
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            builder.setLargeIcon(largeIcon);
            builder.setContentTitle(title);
            builder.setContentText(contentText);

            Notification notification = builder.build();

            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(NOTIFICATION_ID, notification);
        }
    }
}
