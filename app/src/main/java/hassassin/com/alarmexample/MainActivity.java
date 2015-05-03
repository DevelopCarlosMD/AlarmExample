package hassassin.com.alarmexample;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class MainActivity extends Activity {


    private AlarmManager mAlarmManager;
    private Intent mNotificationReceiverIntent, mLoggerReceiverIntent;
    private PendingIntent mNotificationReceiverPendingIntent,mLoggerReceiverPendingIntent;

    private static final long INITIAL_ALARM_DELAY = 2 * 60 * 1000L;
    protected static final long JITTER = 5000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtener el servicio de Alarma
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //Creamos un Intent para transmitir al notificador de Alarmas AlarmNotificationReceiver
        mNotificationReceiverIntent = new Intent(this, AlarmNotificationReceiver.class);

        mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0 , mNotificationReceiverIntent, 0);

        //Create an Intent to broadcast to the AlarmLoggerReceiver
        mLoggerReceiverIntent = new Intent(MainActivity.this, AlarmLoggerReceiver.class);

        //Create PendingIntent that holds the mLoggerReceiverPendingIntent
        mLoggerReceiverPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, mLoggerReceiverIntent, 0);

        //Setup single alarm Button
        final Button singleAlarmButton = (Button) findViewById(R.id.btn_alarm);
        singleAlarmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //Asignar una sola alarma
                mAlarmManager.set(AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + INITIAL_ALARM_DELAY,
                        mNotificationReceiverPendingIntent);

                mAlarmManager.set(AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + INITIAL_ALARM_DELAY
                                + JITTER, mLoggerReceiverPendingIntent);

                //Mostrar mensaje con el metodo Toast
                Toast.makeText(getApplicationContext(), "Single Alarm Set", Toast.LENGTH_LONG).show();

            }
        });
        //Set up repeating Alarm Button
        final Button repeatingAlarmButton = (Button) findViewById(R.id.btn_repeat_alarm);
        repeatingAlarmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set repeating alarm
                mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                        SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY,
                        AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                        mNotificationReceiverPendingIntent);

                // Set repeating alarm to fire shortly after previous alarm
                mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                        SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY + JITTER,
                        AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                        mLoggerReceiverPendingIntent);

                // Muestra el mensaje Toast
                Toast.makeText(getApplicationContext(), "Repeating Alarm Set", Toast.LENGTH_LONG).show();
            }
        });

        //Set up inexact repeating alarm Button
        final Button inexactRepeatingAlarmButton = (Button) findViewById(R.id.btn_repeat_inexact_alarm);
        inexactRepeatingAlarmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set inexact repeating alarm

                mAlarmManager.setInexactRepeating(
                        AlarmManager.ELAPSED_REALTIME,
                        SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY,
                        AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                        mLoggerReceiverPendingIntent);

                // Set inexact repeating alarm to fire shortly after previous alarm
                mAlarmManager.setInexactRepeating(
                        AlarmManager.ELAPSED_REALTIME,
                        SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY + JITTER,
                        AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                        mLoggerReceiverPendingIntent);

                Toast.makeText(getApplicationContext(),
                        "Intent Repeating Alarm Set", Toast.LENGTH_LONG).show();
            }
        });
        final Button cancelRepeatingAlarmButton = (Button) findViewById(R.id.btn_cancel_alarm);
        cancelRepeatingAlarmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //Cancel all alarms using mNotificationsReceiverPendingIntent
                mAlarmManager.cancel(mNotificationReceiverPendingIntent);

                //Cancel all alarms using mLoggerReceiverPendingIntent
                mAlarmManager.cancel(mLoggerReceiverPendingIntent);

                //Show Toast message
                Toast.makeText(getApplicationContext(),
                        "Repeatig Alarms Cancelled" , Toast.LENGTH_LONG).show();
            }
        });
    }

}
