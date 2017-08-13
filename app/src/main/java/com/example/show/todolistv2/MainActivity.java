package com.example.show.todolistv2;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> items;
    ListView listItems;
    int itemID = -1;
    int previousPosition;
    AlertDialog.Builder alertDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        IntentFilter filter = new IntentFilter();
        filter.addAction("SaveItem");
        getApplicationContext().registerReceiver(mCmdReceiver, filter);

        listItems = (ListView) findViewById(R.id.lvItems);
        listTodoResult();

        //choose item and change color
        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (-1 != previousPosition) {
                    View view1 = listItems.getChildAt(previousPosition);
                    view1.setBackgroundColor(Color.WHITE);
                }

                Item it1 = (Item) parent.getItemAtPosition(position);
                itemID = it1.getId();
                view.setBackgroundColor(Color.argb(80, 192, 192, 192));
                previousPosition = position;
            }
        });
//        show detail info
        listItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);

                FragmentManager fm = getSupportFragmentManager();
                DetailDialogFragment fragment = (DetailDialogFragment) DetailDialogFragment.newInstance("Todo Detail", item);
                fragment.show(fm, "detail_dialog_fragment");

                return true;
            }
        });
//        del item
        alertDlg = new AlertDialog.Builder(this);
        alertDlg.setTitle("Delete a todo");
        alertDlg.setMessage("Are you sure?");
        alertDlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                TodoItemDB db = TodoItemDB.getsInstance(getApplicationContext());
                db.delItem(itemID);
                listTodoResult();
                dialog.dismiss();
            }
        });

        alertDlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();;
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_add) {
            showEditDialog(-1);
            return true;
        } else if (id == R.id.action_edit){
            showEditDialog(itemID);
            return true;
        } else if (id == R.id.action_del) {
            alertDlg.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showEditDialog(int itemID) {

        FragmentManager fm = getSupportFragmentManager();
        EditDialogFragment fragment = EditDialogFragment.newInstance("Todo Edit", itemID);
        fragment.show(fm, "edit_dialog_fragment");

    }


    BroadcastReceiver mCmdReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            listTodoResult();
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mCmdReceiver);
    }

    public void listTodoResult() {
        TodoItemDB db = TodoItemDB.getsInstance(this);

        items = db.queryAll();

        ItemAdapter adapter = new ItemAdapter(this, items);

        previousPosition = -1;
        listItems.setAdapter(adapter);
    }

}
