package per.dhnguyen.tdhmusicplayer.data;

/**
 * Created by dHNguyen on 8/9/2016.
 * dhnguyen.acc@gmail.com
 */

/**
 * Cau truc du lieu thong tin cua 1 bai hat
 */
public class Song {
    private String nameSong;        //ten bai hat
    private String artistSong;      //ten ca si
    private String albumSong;       //ten album
    private String pathSong;        //duong dan toi bai hat

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getArtistSong() {
        return artistSong;
    }

    public void setArtistSong(String artistSong) {
        this.artistSong = artistSong;
    }

    public String getAlbumSong() {
        return albumSong;
    }

    public void setAlbumSong(String albumSong) {
        this.albumSong = albumSong;
    }

    public String getPathSong() {
        return pathSong;
    }

    public void setPathSong(String pathSong) {
        this.pathSong = pathSong;
    }
}
