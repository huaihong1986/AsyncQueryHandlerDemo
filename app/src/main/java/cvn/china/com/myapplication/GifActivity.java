package cvn.china.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by huaihong on 2016/8/26.
 */
public class GifActivity  extends Activity {
    private GifView gif1, gif2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        gif1 = (GifView) findViewById(R.id.gif1);
        // 设置背景gif图片资源
        gif1.setMovieResource(R.raw.gif1);
        gif2 = (GifView) findViewById(R.id.gif2);
        gif2.setMovieResource(R.raw.gif2);
        // 设置暂停
        // gif2.setPaused(true);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gif1.setPaused(true);
                gif2.setPaused(true);
            }
        }, 2000);
    }

}
