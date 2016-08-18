package per.dhnguyen.tdhmusicplayer.tablist;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import per.dhnguyen.tdhmusicplayer.FindMusic;
import per.dhnguyen.tdhmusicplayer.MainActivity;
import per.dhnguyen.tdhmusicplayer.MusicService;
import per.dhnguyen.tdhmusicplayer.R;
import per.dhnguyen.tdhmusicplayer.listadapter.ListViewAdapter;
import per.dhnguyen.tdhmusicplayer.listadapter.ListViewAlbumAdapter;
import per.dhnguyen.tdhmusicplayer.listadapter.ListViewArtistAdapter;

/**
 * Created by dHNguyen on 8/8/2016.
 * dhnguyen.acc@gmail.com
 */

/**
 * Fragment khi mo danh sach album
 */
public class AlbumTabFragment extends Fragment {

    private ListView listView;
    private ListViewAlbumAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.album_tab_fragment, container, false);
        listView = (ListView) layout.findViewById(R.id.albumTabLv);
        adapter = new ListViewAlbumAdapter(this.getActivity(), MainActivity.listAlbum);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int y = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.trackIndex = 0;
                        FindMusic.findSongfromAlbum(MainActivity.listAlbum.get(y));
                        MusicService.setMusicUrl(MainActivity.listNowPlaying.get(0).getPathSong());
                        if (MusicService.getInstance() == null) {
                            getActivity().startService(MainActivity.intent);
                        } else {
                            MusicService.getInstance().resetMediaPlayer();
                        }
                    }
                }).start();

//                MainActivity.trackIndex = i;
//                MusicService.setMusicUrl(MainActivity.listSong.get(i).getPathSong());
//                if (MusicService.getInstance() == null) {
//                    getActivity().startService(MainActivity.intent);
//                } else {
//                    MusicService.getInstance().resetMediaPlayer();
//                }
            }
        });

        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        new MyAsyncTask(adapter, listSong).execute();
    }
}