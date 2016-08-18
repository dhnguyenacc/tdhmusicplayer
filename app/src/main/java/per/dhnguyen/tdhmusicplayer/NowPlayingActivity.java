package per.dhnguyen.tdhmusicplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Activity hang doi
 */
public class NowPlayingActivity extends AppCompatActivity {

    private ListView nowPlayingIv;
    private String[] nowPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);
        setTitle("Hàng đợi");


        nowPlaying = new String[MainActivity.listNowPlaying.size()];
        for (int i = 0; i < MainActivity.listNowPlaying.size(); i++) {
            nowPlaying[i] = MainActivity.listNowPlaying.get(i).getNameSong();
        }


        nowPlayingIv = (ListView) findViewById(R.id.nowPlayingLv);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, nowPlaying);
        nowPlayingIv.setAdapter(adapter);
        nowPlayingIv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.trackIndex = i;
                MusicService.setMusicUrl(MainActivity.listNowPlaying.get(i).getPathSong());
                if (MusicService.getInstance() == null) {
                    startService(MainActivity.intent);
                } else {
                    MusicService.getInstance().resetMediaPlayer();
                }
            }
        });
    }
}
