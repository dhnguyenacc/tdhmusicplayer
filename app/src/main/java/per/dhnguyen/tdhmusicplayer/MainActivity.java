package per.dhnguyen.tdhmusicplayer;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.RunnableFuture;

import per.dhnguyen.tdhmusicplayer.data.Song;
import per.dhnguyen.tdhmusicplayer.tablist.AllTabFragment;

/**
 * Created by dHNguyen on 8/7/2016.
 * dhnguyen.acc@gmail.com
 */

/**
 * Giao dien chinh cua chuong trinh
 */
public class MainActivity extends AppCompatActivity {

    public static boolean checkStop = false;
    public static Intent intent;
    public static ArrayList<Song> listSong = new ArrayList<Song>();
    public static ArrayList<String> listArtist = new ArrayList<String>();
    public static ArrayList<String> listAlbum = new ArrayList<String>();
    public static ArrayList<Song> listNowPlaying = new ArrayList<Song>();
    public static int trackIndex = 0;
    private ImageView listIv, goIv, backIv, detailIv, nowPlayingIv, repeatIv, shuffleIv;
    public static ImageView playIv;
    private SeekBar songSb;
    private TextView countTv, timeTv, songTv, singerTv;
    public UpdateInfoThread infoThread;
    private int currentPosition = 0, duration = 0;
    public static MainActivity m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m = this;

        playIv = (ImageView) findViewById(R.id.playIv);
        listIv = (ImageView) findViewById(R.id.listIv);
        songSb = (SeekBar) findViewById(R.id.songSb);
        countTv = (TextView) findViewById(R.id.countTv);
        timeTv = (TextView) findViewById(R.id.timeTv);
        songTv = (TextView) findViewById(R.id.songTv);
        singerTv = (TextView) findViewById(R.id.singerTv);
        goIv = (ImageView) findViewById(R.id.goIv);
        backIv = (ImageView) findViewById(R.id.backIv);
        detailIv = (ImageView) findViewById(R.id.detailIv);
        nowPlayingIv = (ImageView) findViewById(R.id.orderIv);
        repeatIv = (ImageView) findViewById(R.id.repeatIv);
        shuffleIv = (ImageView) findViewById(R.id.shuffleIv);

//        new MyAsyncTask().execute();

        if (intent == null)
            intent = new Intent(getApplicationContext(), MusicService.class);

        if (MusicService.getInstance() == null) {
            listNowPlaying.clear();
            listNowPlaying.addAll(listSong);
        }

        songTv.setText(listNowPlaying.get(trackIndex).getNameSong());
        singerTv.setText(listNowPlaying.get(trackIndex).getArtistSong());

        listIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });
        playIv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                if ( == getRes("playt"))
//                    playIv.setImageResource(getRes("playt"));
//                else
//                    playIv.setImageResource(getRes("pauset"));
                return false;
            }
        });
        playIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MusicService.getInstance() != null) {
                    if (MusicService.getInstance().musicIsPlaying() != false) {
                        MusicService.getInstance().musicPause();
                        playIv.setImageResource(getRes("playt"));
                    } else {
                        MusicService.getInstance().playResume();
                        playIv.setImageResource(getRes("pauset"));
                    }
                } else {

                    MusicService.setMusicUrl(listNowPlaying.get(trackIndex).getPathSong());
                    startService(intent);
                    if (infoThread != null)
                        infoThread.killThread();
                    infoThread = new UpdateInfoThread();

                    new Thread(infoThread).start();
                }


            }
        });

        goIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trackIndex + 1 <= listNowPlaying.size() - 1)
                    trackIndex = trackIndex + 1;
                else
                    trackIndex = 0;

                MusicService.updateNoti();

                MusicService.setMusicUrl(listNowPlaying.get(trackIndex).getPathSong());
                if (MusicService.getInstance() != null) {
                    MusicService.getInstance().resetMediaPlayer();
                    if (infoThread != null)
                        infoThread.killThread();
                    infoThread = new UpdateInfoThread();

                    new Thread(infoThread).start();
                } else {
                    songTv.setText(listNowPlaying.get(trackIndex).getNameSong());
                    singerTv.setText(listNowPlaying.get(trackIndex).getArtistSong());
                }
            }
        });

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trackIndex - 1 >= 0)
                    trackIndex = trackIndex - 1;
                else
                    trackIndex = listNowPlaying.size() - 1;

                MusicService.updateNoti();

                MusicService.setMusicUrl(listNowPlaying.get(trackIndex).getPathSong());
                if (MusicService.getInstance() != null) {
                    MusicService.getInstance().resetMediaPlayer();
                    if (infoThread != null)
                        infoThread.killThread();
                    infoThread = new UpdateInfoThread();

                    new Thread(infoThread).start();
                } else {
                    songTv.setText(listNowPlaying.get(trackIndex).getNameSong());
                    singerTv.setText(listNowPlaying.get(trackIndex).getArtistSong());
                }
            }
        });
        songSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if ((i != currentPosition) && (MusicService.getInstance() != null))
                    MusicService.getInstance().setCurrentPosition(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        detailIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        nowPlayingIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), NowPlayingActivity.class);
                startActivity(intent1);
            }
        });

        repeatIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicService.getInstance() != null) {
                    if (MusicService.getInstance().getLooping() == false) {
                        MusicService.getInstance().setLooping(true);
                        repeatIv.setImageResource(getRes("repeat1"));
                    } else {
                        MusicService.getInstance().setLooping(false);
                        repeatIv.setImageResource(getRes("repeat"));
                    }
                }
            }
        });

        shuffleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicService.getInstance() != null) {
                    if (MusicService.getInstance().isShuffle() == false) {
                        MusicService.getInstance().setShuffle(true);
                        shuffleIv.setImageResource(getRes("shuffle"));
                    } else {
                        MusicService.getInstance().setShuffle(false);
                        shuffleIv.setImageResource(getRes("shuffled"));
                    }
                }
            }
        });
    }

    /**
     * Tra ve id cua anh trong drawable
     * @param initName: ten file trong drawable
     * @return: id cua anh
     */
    public int getRes(String initName) {
        return getResources().getIdentifier("per.dhnguyen.tdhmusicplayer:drawable/" + initName, null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((MusicService.getInstance() != null)) {
            Log.d("testResume", "vaoday");
            if (infoThread != null)
                infoThread.killThread();
            infoThread = new UpdateInfoThread();

            new Thread(infoThread).start();
        }
    }

    /**
     * Chuyen dang mili sang dang timer khi dem gio chay nhac
     * @param milliseconds: mili seconds
     * @return: timer
     */
    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Thread dung de update trang thai khi choi nhac
     */
    class UpdateInfoThread implements Runnable {

        private volatile boolean isRunning = true;

        @Override
        public void run() {
//                    if (MusicService.getInstance().musicIsPlaying()) {
//                        playIv.setImageResource(getRes("pauset"));
//                    }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            int currentPosition = 0, duration = 0;
            duration = MusicService.getInstance().getDuration();
            final int finalDuration = duration;

            songSb.setMax(duration);
            songSb.setProgress(currentPosition);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (MusicService.getInstance().musicIsPlaying() == true) {
                        playIv.setImageResource(getRes("pauset"));
                    } else {
                        playIv.setImageResource(getRes("playt"));
                    }
                    songTv.setText(listNowPlaying.get(trackIndex).getNameSong());
                    singerTv.setText(listNowPlaying.get(trackIndex).getArtistSong());
                    timeTv.setText(milliSecondsToTimer(finalDuration) + "");
                }
            });
            int i = 0;
            while (isRunning) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (MusicService.getInstance() != null) {
                    if (currentPosition == MusicService.getInstance().getPositionMusic())
                        i++;
                    currentPosition = MusicService.getInstance().getPositionMusic();
                    final int finalCurrentPosition = currentPosition;
                    songSb.setProgress(finalCurrentPosition);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            countTv.setText(milliSecondsToTimer(finalCurrentPosition) + "");
                        }
                    });
                    if (MusicService.getInstance().getCheckComplete() == true) {
                        MusicService.getInstance().setCheckComplete(false);
                        if (MusicService.getInstance().isShuffle() == false)
                            doAfterComplete();
                        else {
                            int rd = new Random().nextInt(MainActivity.listNowPlaying.size());
                            MainActivity.trackIndex = rd;
                            MusicService.updateNoti();
                            doShuffleAfterComplete();
                        }

                    }
                }

                //Tu ket thuc thread khi nguoi choi pause qua lau (> 1 minute)
                if (i > 60) {
                    finish();
                    break;
                }
                if (checkStop == true)
                    break;
            }
            if (checkStop == true) {
                stopService(intent);
                finish();
            }
        }

        //Huy thread
        public void killThread() {
            isRunning = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test_des", "vaodayne");
//        if (infoThread != null)
//            infoThread.killThread();
//        stopService(intent);
    }

    /**
     * Thuc hien sau khi mot bai hat da choi xong va choi bai tiep theo
     */
    public void doAfterComplete() {
        Log.d("test_after", "vaoday");
        if (trackIndex + 1 <= listNowPlaying.size() - 1)
            trackIndex = trackIndex + 1;
        else
            trackIndex = 0;
        MusicService.updateNoti();
        MusicService.setMusicUrl(MainActivity.listNowPlaying.get(MainActivity.trackIndex).getPathSong());
        MusicService.getInstance().resetMediaPlayer();
        if (infoThread != null)
            infoThread.killThread();
        infoThread = new UpdateInfoThread();
        new Thread(infoThread).start();
    }

    /**
     * Thuwc hien bai hat da choi xong va choi bai tiep theo o thiet lap "choi ngau nhien"
     */
    public void doShuffleAfterComplete() {
        Log.d("test_after", "vaoday");
        MusicService.setMusicUrl(MainActivity.listNowPlaying.get(MainActivity.trackIndex).getPathSong());
        MusicService.getInstance().resetMediaPlayer();
        if (infoThread != null)
            infoThread.killThread();
        infoThread = new UpdateInfoThread();
        new Thread(infoThread).start();
    }

    public static MainActivity getInstance() {
        return m;
    }

    /**
     * Set icon cho nut play
     */
    public void setPlayIv(){
        if (MusicService.getInstance().musicIsPlaying() == true) {
            playIv.setImageResource(getRes("pauset"));
        } else {
            playIv.setImageResource(getRes("playt"));
        }
    }
}
