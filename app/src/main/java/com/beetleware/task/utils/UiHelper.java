package com.beetleware.task.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beetleware.task.R;
import com.beetleware.task.ui.base.BaseActivity;

import java.util.List;

public class UiHelper {

    private BaseActivity activity;
    private ProgressDialog mProgressDialog;

    public UiHelper(BaseActivity activity) {
        this.activity = activity;
    }

    public void hideToolbar() {
        if (activity.getSupportActionBar() != null) activity.getSupportActionBar().hide();
    }

    public void showDialog(String title, String message) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtTitle;
        TextView txtMessage;
        Button btnOK;
        Button btnNO;

        txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        txtMessage = dialog.findViewById(R.id.txtMessage);
        txtMessage.setText(message);

        btnOK = dialog.findViewById(R.id.btnOK);
        btnOK.setText(R.string.label_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnNO = dialog.findViewById(R.id.btnNO);
        btnNO.setVisibility(View.GONE);

        dialog.show();

    }

    public Dialog showDialog(String title, String message, String buttonOK, String buttonNO, int image, View.OnClickListener onOkClickListener) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView imageIcon;
        TextView txtTitle;
        TextView txtMessage;
        Button btnOK;
        Button btnNO;
        ImageButton btnClose;


        txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        txtMessage = dialog.findViewById(R.id.txtMessage);
        txtMessage.setText(message);

        imageIcon = dialog.findViewById(R.id.imageIcon);
        if (image != 0) {
            imageIcon.setImageDrawable(activity.getDrawable(image));
        } else {
            imageIcon.setVisibility(View.GONE);
        }


        btnClose = dialog.findViewById(R.id.btnClose);
        btnClose.setVisibility(View.VISIBLE);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOK = dialog.findViewById(R.id.btnOK);
        if (buttonOK == null) {
            btnOK.setVisibility(View.GONE);
        }
        btnOK.setText(buttonOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (onOkClickListener != null) onOkClickListener.onClick(v);
            }
        });

        btnNO = dialog.findViewById(R.id.btnNO);
        if (buttonNO != null) {
            btnNO.setText(buttonNO);
            btnNO.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        } else {
            btnNO.setVisibility(View.GONE);
        }

        dialog.show();
        return dialog;
    }

    public ProgressDialog showProgressDialog() {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(activity);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.setMessage(activity.getResources().getString(R.string.message_loading));
                mProgressDialog.show();
            }

            return mProgressDialog;
        } catch (Exception e) {
            return null;
        }
    }

    public ProgressDialog showProgressDialog(String message) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(activity);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.setMessage(message);
                mProgressDialog.show();
            }

            return mProgressDialog;
        } catch (Exception e) {
            return null;
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void openActivity(Class<?> targetActivity, Bundle bundle, boolean finish) {
        if (finish) activity.finish();
        Intent intent = new Intent(activity, targetActivity);
        if (bundle != null) intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void openActivityForResult(Class<?> targetActivity, Bundle bundle, boolean finish, int requestCode) {
        if (finish) activity.finish();
        Intent intent = new Intent(activity, targetActivity);
        if (bundle != null) intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    public GridLayoutManager initVerticalRV(@NonNull RecyclerView recyclerView, int spanCount, int space) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(space, spanCount, true));
        recyclerView.setNestedScrollingEnabled(false);
        return gridLayoutManager;
    }

    public GridLayoutManager initHorizontalRV(@NonNull RecyclerView recyclerView, int spanCount, int space) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, spanCount, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(space, spanCount, false));
        recyclerView.setNestedScrollingEnabled(false);
        return gridLayoutManager;
    }

    public void fillSpinner(Spinner spinner, List list) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    public void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public void hideView(@Nullable View view) {
        if (view != null) view.setVisibility(View.GONE);
    }

    public void showView(@Nullable View view) {
        if (view != null) view.setVisibility(View.VISIBLE);
    }

    public void hideView(@NonNull List<View> views) {
        for (View view :
                views) {
            if (view != null) view.setVisibility(View.GONE);
        }
    }

    public void disableView(@NonNull List<View> views) {
        for (View view :
                views) {
            if (view != null) view.setEnabled(false);
        }
    }

    public void enableView(@NonNull List<View> views) {
        for (View view :
                views) {
            if (view != null) view.setEnabled(true);
        }
    }

    public void showView(@NonNull List<View> views) {
        for (View view :
                views) {
            if (view != null) view.setVisibility(View.VISIBLE);
        }
    }

}
