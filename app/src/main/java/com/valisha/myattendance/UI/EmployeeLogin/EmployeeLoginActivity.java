package com.valisha.myattendance.UI.EmployeeLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.valisha.myattendance.R;

public class EmployeeLoginActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);


        dialog = ProgressDialog.show(EmployeeLoginActivity.this, "",
                "Please wait...", true);
        dialog.show();


        webView = findViewById(R.id.webView);
        webView.loadUrl("https://myburo.info/employeelogin.aspx");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.dismiss();
            }



        });

    }
}