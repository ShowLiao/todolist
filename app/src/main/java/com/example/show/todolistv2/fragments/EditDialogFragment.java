package com.example.show.todolistv2.fragments;

import android.app.DatePickerDialog;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;

import com.example.show.todolistv2.models.Item;
import com.example.show.todolistv2.R;
import com.example.show.todolistv2.models.TodoItemDB;

public class EditDialogFragment extends DialogFragment {

    EditText editTask;
    TextView txtCalendar;
    Calendar calendar;
    EditText editLocation;
    static int itemID = -1;
    EditText editDetail;
    Spinner spinnerPriority;
    String ACTION = "SaveItem";

    public EditDialogFragment() {

    }

    public static EditDialogFragment newInstance(String title, int id) {

        EditDialogFragment fragment = new EditDialogFragment();
        itemID = id;;
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.edit_dialog_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Log.e("EditDialogFragment", "onViewCreated");

        super.onViewCreated(view, savedInstanceState);
        //init widgets
        editTask = (EditText) view.findViewById(R.id.editTask);
        editDetail = (EditText) view.findViewById(R.id.editDetail);
        editLocation = (EditText) view.findViewById(R.id.editLocation);
        txtCalendar = (TextView) view.findViewById(R.id.txtCalendar);
        spinnerPriority = (Spinner) view.findViewById(R.id.spinnerPriority);

        String strTitle = getArguments().getString(getString(R.string.title), getString(R.string.enter_task));
        getDialog().setTitle(strTitle);

        calendar = Calendar.getInstance();
        calendar.get(Calendar.DATE);
        calendar.get(Calendar.MONTH);

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DATE);
        if (-1 != itemID)
        {
            queryItemInfo();
        }


        //for open calender
        txtCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog d = new DatePickerDialog(getContext(), R.style.DatePickDialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        view.setBackgroundColor(Color.BLUE);
                        txtCalendar.setText(String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(dayOfMonth));
                    }
                }, year, month, day);

                d.show();
            }
        });


        //for save button
        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                //save
                if (-1 != itemID) {
                    update();
                } else
                    addNew();

                Intent intent = new Intent();
                intent.setAction(ACTION);

                getActivity().sendBroadcast(intent);
                getDialog().dismiss();
            }
        });

        //for cancel button
        Button btnCancel = (Button)view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Button cancel", "cancel");
                getDialog().dismiss();

            }
        });
    }

    private void addNew() {
        Item item = collectItem();

        TodoItemDB db = TodoItemDB.getsInstance(getDialog().getContext());
        db.addItem(item);
    }

    private void update() {
        Item item = collectItem();
        item.setId(itemID);

        TodoItemDB db = TodoItemDB.getsInstance(getDialog().getContext());
        db.updateInfo(item);
    }

    private void queryItemInfo() {

        TodoItemDB db = TodoItemDB.getsInstance(getDialog().getContext());
        Item item = db.query(itemID);
        editTask.setText(item.getTask());
        txtCalendar.setText(item.getDate());
        editLocation.setText(item.getLocation());
        editDetail.setText(item.getDetail());
        spinnerPriority.setSelection(item.getPriority());

    }

    private Item collectItem() {
        Item item = new Item();
        String date = calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);


        item.setTask(editTask.getText().toString());
        item.setDate(date);
        item.setLocation(editLocation.getText().toString());
        item.setDetail(editDetail.getText().toString());
        Spinner spinner = (Spinner) getDialog().findViewById(R.id.spinnerPriority);

        int priority = 0;
        if (spinner.getSelectedItem().toString().equals(getString(R.string.low)))
            priority = 2;
        else if (spinner.getSelectedItem().toString().equals(getString(R.string.medium)))
            priority = 1;
        item.setPriority(priority);

        return item;
    }


}
