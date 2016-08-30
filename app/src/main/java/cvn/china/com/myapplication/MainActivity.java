package cvn.china.com.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private PopupWindow popupWindow;
    private TextView textView;
    private Button myButton;
    ArrayList<String> data;
    LinearLayoutManager tasklayoutManager;
    RecyclerView testview;
    BankSpinnerAdapter bankSpinnerAdapter;
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButton = (Button) findViewById(R.id.button);
        //监听按钮
        myButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                initPopWindow();
            }
        });
    }

    private void initPopWindow() {
        //得到PopupWindow的布局
        View contentView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.popupwindow, null);
        contentView.setBackgroundColor(Color.DKGRAY);
        //设置PopWindow的宽高
        popupWindow = new PopupWindow(findViewById(R.id.mainLayout), 400, 500);
        popupWindow.setContentView(contentView);

        textView = (TextView) contentView.findViewById(R.id.text);
        textView.setText("Test");
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        testview = (RecyclerView) contentView.findViewById(R.id.spinner_listbanktype);
        tasklayoutManager = new LinearLayoutManager(this);
        testview.setHasFixedSize(true);
        testview.setLayoutManager(tasklayoutManager);
        testview.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<String>();
        for(int i =0;i<10;i++)
            data.add("test"+i);
        bankSpinnerAdapter = new BankSpinnerAdapter(this,null);
        testview.setAdapter(bankSpinnerAdapter);
        bankSpinnerAdapter.changeData(data);

        //RecyclerView addOnScrollListener在PopUpWindow里面不响应呢，所以选择setOnTouchListener
        testview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            /*   int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (firstVisibleItem == 0) {
                    refresh();
                }*/
                //得到当前显示的最后一个item的view
                View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
                //得到lastChildView的bottom坐标值
                if (lastChildView != null) {
                    int lastChildBottom = lastChildView.getBottom();
                    //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                    int recyclerBottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
                    //通过这个lastChildView得到这个view当前的position值
                    int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);

                    //判断lastChildView的bottom值跟recyclerBottom
                    //判断lastPosition是不是最后一个position
                    //如果两个条件都满足则说明是真正的滑动到了底部
                    if (lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        refreshother();
                    }
                }
            }
        });
        popupWindow.setFocusable(true);
        //显示PopupWindow
        popupWindow.showAsDropDown(contentView);

        testview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = tasklayoutManager.getHeight();

                int y = (int) event.getY();
                Log.e("data","data="+height+"y="+y);
                //继承了Activity的onTouchEvent方法，直接监听点击事件
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    x1 = event.getX();
                    y1 = event.getY();
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    //当手指离开的时候
                    x2 = event.getX();
                    y2 = event.getY();
                    if(y1 - y2 > 50) {
                       refreshother();
                    } else if(y2 - y1 > 50) {
                        Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
                    } else if(x1 - x2 > 50) {
                        Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
                    } else if(x2 - x1 > 50) {
                        Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }

    private void refreshother() {
        for(int i =0;i<10;i++)
            data.add("test"+i);
        bankSpinnerAdapter.changeData(data);
        testview.scrollToPosition(0);
    }
}
