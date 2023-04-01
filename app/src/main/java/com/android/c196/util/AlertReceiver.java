package com.android.c196.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlertReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "course_start_end_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Create notification channel
        createNotificationChannel(context);

        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");

        // build otification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // display notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, builder.build());
    }

    private void createNotificationChannel(Context context) {
        // Check if the app targets Android 8.0 (API level 26) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Set channel name and description
            CharSequence name = "Course Channel";
            String description = "Channel for course start and end notifications";

            // Set the importance level of the channel
            int importance = NotificationManager.IMPORTANCE_HIGH;

            // Create a notification channel with the specified ID, name, and importance level
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
