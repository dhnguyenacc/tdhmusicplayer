package per.dhnguyen.tdhmusicplayer;

import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import per.dhnguyen.tdhmusicplayer.data.Song;

/**
 * Created by dHNguyen on 8/9/2016.
 * dhnguyen.acc@gmail.com
 */

/**
 * Class dung de tim file nhac co trong bo nho
 */
public class FindMusic {
    private String buffer = "";
    private MediaMetadataRetriever mediaMeta;
    final String MEDIA_PATH = Environment.getExternalStorageDirectory()
            .getPath() + "/";
    private ArrayList<Song> songsList;
    private String mp3Pattern = ".flac";

    // Constructor
    public FindMusic() {
        mediaMeta = new MediaMetadataRetriever();
        songsList = new ArrayList<Song>();
    }


    /**
     * Tim tat ca file nhac co trong bo nho
     * @return: mot array list dang du lieu Song
     */
    public ArrayList<Song> getAllSong() {
//        Log.d("test_music", MEDIA_PATH);
        if (MEDIA_PATH != null) {
            File home = new File(MEDIA_PATH);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
//                    Log.d("test_music", file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
        // return songs list array
        return songsList;
    }

    /**
     * Tim tat ca cac thu muc trong sdcard
     * @param directory: duong dan cac thu muc
     */
    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }

                }
            }
        }
    }

    /**
     * Them mot bai ha duoc tim thay vao list
     * @param songP: 1 bai hat dang Song
     */
    private void addSongToList(File songP) {
        if (songP.getName().endsWith(mp3Pattern)) {
            Song song = new Song();
            mediaMeta.setDataSource(songP.getPath());

//            song.setNameSong(songP.getName().substring(0, (songP.getName().length() - 5)));

            buffer = mediaMeta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            if (buffer != null) {
                song.setNameSong(buffer);
            } else {
                song.setNameSong("Unknown");
            }

            song.setPathSong(songP.getPath());

            buffer = mediaMeta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            if (buffer != null) {
                song.setAlbumSong(buffer);
            } else {
                song.setAlbumSong("Unknown");
            }

            buffer = mediaMeta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            if (buffer != null) {
                song.setArtistSong(buffer);
            } else {
                song.setArtistSong("Unknown");
            }
            // Adding each song to SongList
            songsList.add(song);
        }
    }

    /**
     * Tim tat ca ca si
     */
    public void findArtist() {
        MainActivity.listArtist.clear();
        int check = 0;
        for (int i = 0; i < MainActivity.listSong.size(); i++) {
            check = 0;
            if (i == 0)
                MainActivity.listArtist.add(MainActivity.listSong.get(i).getArtistSong());
            for (int j = 0; j < MainActivity.listArtist.size(); j++) {
                if (MainActivity.listSong.get(i).getArtistSong().equals(MainActivity.listArtist.get(j))) {
                    check++;
                }
            }
            if (check == 0) {
                MainActivity.listArtist.add(MainActivity.listSong.get(i).getArtistSong());
            }
        }
    }

    /**
     * Tim tat ca Album
     */
    public void findAlbum(){
        MainActivity.listAlbum.clear();
        int check = 0;
        for (int i = 0; i < MainActivity.listSong.size(); i++) {
            check = 0;
            if (i == 0)
                MainActivity.listAlbum.add(MainActivity.listSong.get(i).getAlbumSong());
            for (int j = 0; j < MainActivity.listAlbum.size(); j++) {
                if (MainActivity.listSong.get(i).getArtistSong().equals(MainActivity.listArtist.get(j))) {
                    check++;
                }
            }
            if (check == 0) {
                MainActivity.listAlbum.add(MainActivity.listSong.get(i).getAlbumSong());
            }
        }
    }

    /**
     * Tim bai hat theo ca si
     * @param initArtist: ten ca si
     */
    public static void findSongfromArtist(String initArtist){
        MainActivity.listNowPlaying.clear();
        for(int i=0; i < MainActivity.listSong.size(); i++){
            if(MainActivity.listSong.get(i).getArtistSong().equals(initArtist)){
                MainActivity.listNowPlaying.add(MainActivity.listSong.get(i));
            }
        }
    }

    /**
     * Tim bai hat theo album
     * @param initAlbum: ten album
     */
    public static void findSongfromAlbum(String initAlbum){
        MainActivity.listNowPlaying.clear();
        for(int i=0; i < MainActivity.listSong.size(); i++){
            if(MainActivity.listSong.get(i).getAlbumSong().equals(initAlbum)){
                MainActivity.listNowPlaying.add(MainActivity.listSong.get(i));
            }
        }
    }
}

