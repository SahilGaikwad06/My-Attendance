package com.valisha.myattendance.apps;

import android.app.ProgressDialog;
import android.content.Context;

public class CommonClass {

    public ProgressDialog progressDialog(Context context)
    {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Please wait");
        return pd;
    }

    public Context passcontext(Context context)
    {
        return context;
    }

}
