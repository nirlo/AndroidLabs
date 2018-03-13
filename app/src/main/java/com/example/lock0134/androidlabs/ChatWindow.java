package com.example.lock0134.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.lock0134.androidlabs.StartActivity.ACTIVITY_NAME;

public class ChatWindow extends Activity {
    private EditText message;
    private Button send;
    private ListView list;
    private ArrayList<String> messages;
    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);
        list = findViewById(R.id.lView);
        messages = new ArrayList<>();
        dbHelper = new ChatDatabaseHelper(this);

        db = dbHelper.getWritableDatabase();


        Cursor c = db.query(false, dbHelper.TABLE_NAME, new String[] {dbHelper.KEY_ID, dbHelper.KEY_MESSAGE}, null, null, null, null, null, null);
        c.moveToFirst();

        while(!c.isAfterLast()) {
            messages.add(c.getString(c.getColumnIndex(dbHelper.KEY_MESSAGE)));

            Log.i(dbHelper.ACTIVITY_NAME, "SQLMessage: "+ c.getColumnName(c.getColumnIndex(dbHelper.KEY_MESSAGE)));
            Log.i(dbHelper.ACTIVITY_NAME, "Cursor’s  column count =" + c.getColumnCount() );
            c.moveToNext();
        }




        final ChatAdapter messageAdapter = new ChatAdapter( this, messages);
        list.setAdapter(messageAdapter);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = message.getText().toString();
                messages.add(text);
                ContentValues cValues = new ContentValues();
                cValues.put("Message", message.getText().toString());
                db.insert(dbHelper.DATABASE_NAME, "NullColumnName", cValues);
                message.setText("");
                messageAdapter.notifyDataSetChanged();
            }
        });


    }

    private class ChatAdapter extends ArrayAdapter<String> {
        ArrayList<String> list;

        public ChatAdapter(Context ctx, ArrayList<String> list) {
            super(ctx, 0);
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        public String getItem(int position) {
            return list.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position%2==0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView msg = (TextView)result.findViewById(R.id.message_text);
            msg.setText( getItem(position));
            return result;
        }

        public long getId(int position) {
            return position;
        }
    }

    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
