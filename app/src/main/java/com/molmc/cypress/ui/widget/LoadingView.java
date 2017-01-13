package com.molmc.cypress.ui.widget;

import android.app.AlertDialog;
import android.content.Context;

import dmax.dialog.SpotsDialog;

/**
 * features: loading dialog
 * Author：  hhe on 16-9-2 00:23
 * Email：   hui@molmc.com
 */
public class LoadingView {

    private AlertDialog mLoadingDialog;

    public LoadingView(Context context, String message) {
        mLoadingDialog = new SpotsDialog(context, message);
    }

    public void show() {
        mLoadingDialog.show();
    }

    public void dismiss() {
        mLoadingDialog.dismiss();
    }
}
