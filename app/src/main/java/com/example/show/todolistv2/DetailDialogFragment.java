package com.example.show.todolistv2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by show on 8/12/17.
 */

public class DetailDialogFragment extends DialogFragment {

    static Item item;
    public static DialogFragment newInstance(String title, Item it) {

        DetailDialogFragment fragment = new DetailDialogFragment();
        item = it;
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.detail_dialog_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        init widgets
        TextView txtTask = (TextView) view.findViewById(R.id.txtTask);
        TextView txtDetail = (TextView) view.findViewById(R.id.txtDetail);
        TextView txtLocation = (TextView) view.findViewById(R.id.txtLocation);
        TextView txtDudate = (TextView) view.findViewById(R.id.txtTime);
//        Log.e("date:", String.valueOf(calendar.get(Calendar.DATE)) + "/" + String.valueOf(month) + "/" + String.valueOf(dayOfMonth));

        txtTask.setText(item.getTask());
        txtDudate.setText(item.getDate());
        txtLocation.setText(item.getLocation());
        txtDetail.setText(item.getDetail());
        txtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenMap(v);
            }
        });

        Button btnBack = (Button) view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

    }
    public void onOpenMap(View view) {
        TextView txtLocation = (TextView) view.findViewById(R.id.txtLocation);
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + txtLocation.getText().toString());

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}
