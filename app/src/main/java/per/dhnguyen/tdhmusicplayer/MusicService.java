package per.dhnguyen.tdhmusicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

/**
 * Created by dHNguyen on 8/10/2016.
 * dhnguyen.acc@gmail.com
 */
public class MusicService extends Service {

    public static RemoteViews remote;
    public static MediaPlayer media;
    private static String musicUrl;
    public static MusicService mInstance;
    private static boolean checkComplete = false;
    public static NotificationManager mana;
    private boolean isShuffle;
    private static NotificationCompat.Builder mBuilder;
    public static Notification noti;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        media = new MediaPlayer();
        mana = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        isShuffle = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            media.setDataSource(getApplicationContext(), Uri.parse(musicUrl));
            media.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        media.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                checkComplete = true;
            }
        });

//        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
//        PendingIntent pending = PendingIntent.getBroadcast(this, 136, intent1, 0);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setTicker("My Service is running");
//        builder.setContentTitle("My Service");
//        builder.setContentText("Tap here");
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setContentIntent(pending);
//        Notification noti = builder.build();
//        startForeground(136, noti);


        mBuilder = new NotificationCompat.Builder(getApplicationContext());
        remote = new RemoteViews(getPackageName(), R.layout.custom_noti);
        mBuilder.setSmallIcon(R.drawable.vinyl);
        mBuilder.setTicker("TDH Music Player");
//        mBuilder.setContentTitle(MainActivity.listNowPlaying.get(MainActivity.trackIndex).getNameSong());
//        mBuilder.setContentText(MainActivity.listNowPlaying.get(MainActivity.trackIndex).getArtistSong());
        mBuilder.setContent(remote);

        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);

        TaskStackBuilder task = TaskStackBuilder.create(getApplicationContext());
        task.addParentStack(MainActivity.class);
        task.addNextIntent(intent1);

        PendingIntent pendingIntent = task.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        Intent switchIntent = new Intent(this, SwitchButton.class);
        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this, 0, switchIntent, 0);
        remote.setOnClickPendingIntent(R.id.playNotiIv, pendingSwitchIntent);

        Intent closeIntent = new Intent(this, CloseButton.class);
        PendingIntent pendingCloseIntent = PendingIntent.getBroadcast(this, 0, closeIntent, 0);
        remote.setOnClickPendingIntent(R.id.closeNotiIv, pendingCloseIntent);
        remote.setCharSequence(R.id.nameNotiTv, "setText", MainActivity.listNowPlaying.get(MainActivity.trackIndex).getNameSong());
        remote.setCharSequence(R.id.artistNotiTv, "setText", MainActivity.listNowPlaying.get(MainActivity.trackIndex).getArtistSong());


        noti = mBuilder.build();

        startForeground(136, noti);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
        media.stop();
        media.release();
        mana.cancel(136);
    }

    public static void setMusicUrl(String initMusicUrl) {
        musicUrl = initMusicUrl;
    }

    public static MusicService getInstance() {
        return mInstance;
    }

    public int getPositionMusic() {
        if (media != null)
            return media.getCurrentPosition();
        else
            return 0;
    }

    public int getDuration() {
        if (media != null)
            return media.getDuration();
        else
            return 0;
    }

    public boolean musicIsPlaying() {
        if (media != null)
            return media.isPlaying();
        else
            return false;
    }


    public void setCurrentPosition(int initCurrent) {
        media.seekTo(initCurrent);
    }

    public static void musicPause() {
        media.pause();
        remote.setImageViewResource(R.id.playNotiIv, R.drawable.playnoti);
        mana.notify(136, noti);
    }

    public void resetMediaPlayer() {
        try {
            media.reset();
            media.setDataSource(getApplicationContext(), Uri.parse(musicUrl));
            media.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void playResume() {
        media.start();
        remote.setImageViewResource(R.id.playNotiIv, R.drawable.pausenoti);
        mana.notify(136, noti);
    }

    public boolean getCheckComplete() {
        return checkComplete;
    }

    public void setCheckComplete(boolean initBoolean) {
        checkComplete = initBoolean;
    }

    public void setLooping(boolean initBoolean) {
        media.setLooping(initBoolean);
    }

    public boolean getLooping() {
        return media.isLooping();
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public void setShuffle(boolean shuffle) {
        isShuffle = shuffle;
    }

    public static class SwitchButton extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (media.isPlaying()){
                musicPause();
            }
            else{
                playResume();
            }
            MainActivity.getInstance().setPlayIv();
        }
    }

    public static class CloseButton extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            MainActivity.checkStop = true;
//            getInstance().stopSelf();
        }
    }

    public static void updateNoti(){
        Log.d("vaoday", "testday");
        remote.setCharSequence(R.id.nameNotiTv, "setText", MainActivity.listNowPlaying.get(MainActivity.trackIndex).getNameSong());
        remote.setCharSequence(R.id.artistNotiTv, "setText", MainActivity.listNowPlaying.get(MainActivity.trackIndex).getArtistSong());
        mana.notify(136, MusicService.noti);
    }
}
