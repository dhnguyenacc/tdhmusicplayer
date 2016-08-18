package per.dhnguyen.tdhmusicplayer.listadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import per.dhnguyen.tdhmusicplayer.R;
import per.dhnguyen.tdhmusicplayer.data.Song;

/**
 * Created by dHNguyen on 8/9/2016.
 * dhnguyen.acc@gmail.com
 */

/**
 * ListView Adapter danh sach tat ca bai hat
 */
public class ListViewAdapter extends BaseAdapter {

    private Context con;
    private ArrayList<Song> listSong;
    private LayoutInflater inf;

    public ListViewAdapter(Context initCon, ArrayList<Song> initListSong){
        this.con = initCon;
        this.listSong = initListSong;
        this.inf = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return listSong.size();
    }

    @Override
    public Object getItem(int i) {
        return listSong.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        View row = view;
        if(row == null){
            row = inf.inflate(R.layout.custom_adapter, viewGroup, false);
            holder  = new ViewHolder();
            holder.initComponent(row);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        holder.songCover.setImageResource(R.drawable.songlv);
        holder.songName.setText(listSong.get(i).getNameSong());
        holder.songArtist.setText(listSong.get(i).getArtistSong());

        return row;
    }

    class ViewHolder{
        ImageView songCover;
        TextView songName;
        TextView songArtist;

        void initComponent(View view){
            songCover = (ImageView) view.findViewById(R.id.coverSongIv);
            songName = (TextView) view.findViewById(R.id.nameSongTv);
            songArtist = (TextView) view.findViewById(R.id.artistSongTv);
        }
    }
}
