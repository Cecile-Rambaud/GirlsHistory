package fr.cecilerambaud.ananke;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String CANAL = "MyNotificationCanal";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String myMessage = remoteMessage.getNotification().getBody();
        Log.d("FirebaseMessage", "Vous venez de recevoir une notification" + myMessage);

        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this , CANAL);
        notificationBuilder.setContentTitle("Super!");
        notificationBuilder.setContentText(myMessage);

        notificationBuilder.setContentIntent(pendingIntent);

        long[] vibrationPattern = {500, 1000};
        notificationBuilder.setVibrate(vibrationPattern);

        notificationBuilder.setSmallIcon(R.drawable.icon);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.notification_channel_id);
            String channelTitle = getString(R.string.notification_channel_title);
            String channelDescription = getString(R.string.notification_channel_description);
            NotificationChannel channel = new NotificationChannel(channelId, channelTitle, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDescription);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channelId);
        }
        notificationManager.notify(1, notificationBuilder.build());
    }
}
