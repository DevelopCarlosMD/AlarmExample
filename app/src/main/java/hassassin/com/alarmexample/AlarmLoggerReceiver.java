package hassassin.com.alarmexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Date;
import android.util.Log;

import java.text.DateFormat;

/**
 * Created by hassassin1 on 02/05/15.
 */
public class AlarmLoggerReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmLoggerReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "Logging alarm at:" + DateFormat.getDateTimeInstance().format(new Date()));
    }
}
