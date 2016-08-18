package per.dhnguyen.tdhmusicplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Class bat len dau tien moi khi app chay de nap du lieu
 */
public class BlankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        new MyAsyncTask(this).execute();
    }
}
