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
 * ListView Adapter danh sach tat ca album
 */
public class ListViewAlbumAdapter extends BaseAdapter {

    private Context con;
    private ArrayList<String> listAlbum;
    private LayoutInflater inf;

    public ListViewAlbumAdapter(Context initCon, ArrayList<String> initListAlbum){
        this.con = initCon;
        this.listAlbum = initListAlbum;
        this.inf = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return listAlbum.size();
    }

    @Override
    public Object getItem(int i) {
        return listAlbum.get(i);
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
            row = inf.inflate(R.layout.custom_adapter_album, viewGroup, false);
            holder  = new ViewHolder();
            holder.initComponent(row);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        holder.albumCover.setImageResource(R.drawable.album);
        holder.nameAlbum.setText(listAlbum.get(i));

        return row;
    }

    class ViewHolder{
        ImageView albumCover;
        TextView nameAlbum;

        void initComponent(View view){
            albumCover = (ImageView) view.findViewById(R.id.coverAlbumIv);
            nameAlbum = (TextView) view.findViewById(R.id.nameAlbumTv);
        }
    }
}
