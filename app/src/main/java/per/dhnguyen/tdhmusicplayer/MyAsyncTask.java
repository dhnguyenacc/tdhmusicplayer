package per.dhnguyen.tdhmusicplayer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import per.dhnguyen.tdhmusicplayer.data.Song;
import per.dhnguyen.tdhmusicplayer.listadapter.ListViewAdapter;
import per.dhnguyen.tdhmusicplayer.tablist.PagerAdapter;

/**
 * Created by dHNguyen on 8/9/2016.
 * dhnguyen.acc@gmail.com
 */

/**
 * Asynctask dung de nap bai hat khi moi bat chuong trinh
 */
public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

    private FindMusic findMusic;
    private Context con;

    public MyAsyncTask(Context con){
        this.con = con;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        findMusic = new FindMusic();
    }

    @Override
    protected Void doInBackground(Void[] objects) {
        MainActivity.listSong = findMusic.getAllSong();
        findMusic.findArtist();
        findMusic.findAlbum();
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void song) {
        super.onPostExecute(song);
        Intent intent = new Intent(con, MainActivity.class);
        Log.d("test_count", MainActivity.listAlbum.size()+"");
        con.startActivity(intent);
        ((AppCompatActivity) con).finish();
    }
}
