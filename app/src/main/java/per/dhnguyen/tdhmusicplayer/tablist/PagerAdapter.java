package per.dhnguyen.tdhmusicplayer.tablist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by dHNguyen on 8/8/2016.
 * dhnguyen.acc@gmail.com
 */

/**
 * PagerView de add fragment khi nhan nut danh sach bai hat
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AllTabFragment alltab = new AllTabFragment();
                return alltab;
            case 1:
                SingerTabFragment singertab = new SingerTabFragment();
                return singertab;
            case 2:
                AlbumTabFragment albumtab = new AlbumTabFragment();
                return albumtab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
