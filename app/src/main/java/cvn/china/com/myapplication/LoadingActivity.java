package cvn.china.com.myapplication;


import android.app.ListActivity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class LoadingActivity extends ListActivity {
    Context myContext = null;
    MyListAdapter myListAdapter = null;
    ViewHolder viewHolder = null;

    private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象

    // 用于存储联系人名称
    List<String> myContactName = new ArrayList<String>();
    // 用于存储联系人电话
    List<String> myContactNumber = new ArrayList<String>();
    // 用于存储联系人总数
    int myContactAmount = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asyncQueryHandler = new MyAsyncQueryHandler(getContentResolver());

        getContactList();
    }


    private void getContactList() {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；
        // 查询的字段
        String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY};
        // 按照sort_key升序查詢
        asyncQueryHandler.startQuery(0, null, uri, projection, null, null,
                "sort_key COLLATE LOCALIZED asc");

    }


    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }


        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {

                cursor.moveToFirst(); // 游标移动到第一项
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String name = cursor.getString(1);
                    String number = cursor.getString(2);

                    myContactName.add(name);
                    myContactNumber.add(number);

                }
                myListAdapter = new MyListAdapter(LoadingActivity.this);
                setListAdapter(myListAdapter);

                super.onQueryComplete(token, cookie, cursor);
            }

        }
    }


    class MyListAdapter extends BaseAdapter {

        public MyListAdapter(Context context) {
            myContext = context;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return myContactName.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            if (convertView == null) {
                viewHolder = new ViewHolder();

                convertView = LayoutInflater.from(myContext).inflate(
                        R.layout.list, null);
                viewHolder.name = (TextView) convertView
                        .findViewById(R.id.name);
                viewHolder.number = (TextView) convertView
                        .findViewById(R.id.number);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.name.setText(myContactName.get(position));
            viewHolder.number.setText(myContactNumber.get(position));

            return convertView;
        }
    }

    private static class ViewHolder {
        TextView name;
        TextView number;
    }
}
