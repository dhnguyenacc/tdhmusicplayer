package per.dhnguyen.tdhmusicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by sev_user on 8/11/2016.
 */
public class MyReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(Intent.ACTION_HEADSET_PLUG)){
            Toast.makeText(context, "ABC XYZ", Toast.LENGTH_LONG).show();
            context.startActivity(new Intent(context, BlankActivity.class));
        }
    }
}
