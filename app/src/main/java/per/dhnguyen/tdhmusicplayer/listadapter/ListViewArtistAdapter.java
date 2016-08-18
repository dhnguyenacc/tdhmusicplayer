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
 * ListView Adapter danh sach tat ca ca si
 */
public class ListViewArtistAdapter extends BaseAdapter {

    private Context con;
    private ArrayList<String> listArtist;
    private LayoutInflater inf;

    public ListViewArtistAdapter(Context initCon, ArrayList<String> initListArtist){
        this.con = initCon;
        this.listArtist = initListArtist;
        this.inf = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return listArtist.size();
    }

    @Override
    public Object getItem(int i) {
        return listArtist.get(i);
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
            row = inf.inflate(R.layout.custom_adapter_artist, viewGroup, false);
            holder  = new ViewHolder();
            holder.initComponent(row);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        holder.artistCover.setImageResource(R.drawable.singer);
        holder.nameArtist.setText(listArtist.get(i));

        return row;
    }

    class ViewHolder{
        ImageView artistCover;
        TextView nameArtist;

        void initComponent(View view){
            artistCover = (ImageView) view.findViewById(R.id.coverArtistIv);
            nameArtist = (TextView) view.findViewById(R.id.nameArtistTv);
        }
    }
}
