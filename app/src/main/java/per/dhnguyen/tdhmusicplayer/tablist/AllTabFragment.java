package per.dhnguyen.tdhmusicplayer.tablist;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import per.dhnguyen.tdhmusicplayer.MainActivity;
import per.dhnguyen.tdhmusicplayer.MusicService;
import per.dhnguyen.tdhmusicplayer.MyAsyncTask;
import per.dhnguyen.tdhmusicplayer.R;
import per.dhnguyen.tdhmusicplayer.data.Song;
import per.dhnguyen.tdhmusicplayer.listadapter.ListViewAdapter;

/**
 * Created by dHNguyen on 8/8/2016.
 * dhnguyen.acc@gmail.com
 */

/**
 * Fragment khi mo danh sach tat ca bai hat
 */
public class AllTabFragment extends Fragment {


    private ListView listView;
    private ListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.all_tab_fragment, container, false);
        listView = (ListView) layout.findViewById(R.id.allTabLv);
        adapter = new ListViewAdapter(this.getActivity(), MainActivity.listSong);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.listNowPlaying.clear();
                MainActivity.listNowPlaying.addAll(MainActivity.listSong);
                MainActivity.trackIndex = i;
                MusicService.setMusicUrl(MainActivity.listNowPlaying.get(i).getPathSong());
                if (MusicService.getInstance() == null) {
                    getActivity().startService(MainActivity.intent);
                } else {
                    MusicService.getInstance().resetMediaPlayer();
                }
            }
        });

        return layout;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delMenu) {
            Toast.makeText(getActivity(), "Đã xóa!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Đã đổi tên!", Toast.LENGTH_LONG).show();

        }
        return super.onContextItemSelected(item);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        new MyAsyncTask(adapter, listSong).execute();
    }
}
