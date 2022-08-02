package edu.neu.madcourse.mad_goer;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotiApplication extends Application {
    public static final String CHANNEL_1_ID = "All events";
    public static final String CHANNEL_2_ID = "My events only";


    @Override
    public void onCreate() {
        super.onCreate();
//      setContentView(R.layout.activity_noti);
        createNotificationChannels();
    }

    private void createNotificationChannels(){
        //check whether sdk is higher than oreo
        CharSequence name ="Stickers from everyone";
        String description = "Display sticker notification from everyone";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    name,
                    importance
            );
            channel1.setDescription(description);
            channel1.enableLights(true);

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "My Interested Events",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("Display sticker notification from friends");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
