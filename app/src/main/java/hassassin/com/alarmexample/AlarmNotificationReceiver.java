package hassassin.com.alarmexample;

import android.app.PendingIntent;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by hassassin1 on 02/05/15.
 */
public class AlarmNotificationReceiver extends BroadcastReceiver {

    // Notificacion al ID para permitir actualizacion futuras

    private static final int MY_NOTIFICATION_ID = 1;
    private static final String TAG = "AlarmNotificationReceiver";

    //Notificacion de los elementos de Texto
    private final CharSequence tickerText = "Are you playing Angry Birds again!!!";
    private final CharSequence contentTitle = "A kind remember";
    private final CharSequence contentText = "Get back to Studying!!!";

    //Elementos de Notificacion
    private Intent mNotificationIntent;
    private PendingIntent mContentIntent;

    private final Uri soundUri = Uri.parse("raw/alarm_rooster.mp3");
    private final long[] mVibratePatern = {0,200,200,300};

    @Override
    public void onReceive(Context context, Intent intent) {
        //The intent que sere utilizado cuando el usuario de click en la vista de notificacion
        mNotificationIntent = new Intent(context, MainActivity.class);
        mNotificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //EL pending Intent que envuelve el intent
        mContentIntent = PendingIntent.getActivity(context, 0, mNotificationIntent, 0);


        //Construye la notificacion
        Notification.Builder notificationBuilder = new Notification.Builder(context).setTicker(tickerText)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setAutoCancel(true).setContentTitle(contentTitle)
                .setContentText(contentText).setContentIntent(mContentIntent)
                .setSound(soundUri).setVibrate(mVibratePatern);

        //Obtenermos el NotificactionManager
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());

       //Log.i(TAG,"Sending notification:" + DateFormat.getDateTimeInstance().format(new Date()));
    }
}
