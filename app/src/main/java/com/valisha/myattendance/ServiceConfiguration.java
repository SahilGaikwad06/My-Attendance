package com.valisha.myattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.valisha.myattendance.UI.EmployeeLogin.EmployeeLoginActivity;
import com.valisha.myattendance.apps.IApp;

public class ServiceConfiguration extends AppCompatActivity {

    private EditText et_emp_login_url;
    private IApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_configuration);


        et_emp_login_url = findViewById(R.id.etEmpLoginURL);
        et_emp_login_url.setText("https://myburo.info/employeelogin.aspx");
        app = ((IApp) getApplicationContext());


    }

    public void save(View view)
    {

        String url = et_emp_login_url.getText().toString();


        if (url.isEmpty())
        {
            Toast.makeText(this, "Enter valid login URL", Toast.LENGTH_SHORT).show();
            return;
        }

        else {

            app.preference().putEmployeeLogin(url);
            startActivity(new Intent(this, EmployeeLoginActivity.class)
                    .putExtra("url","https://myburo.info/employeelogin.aspx"));
            Toast.makeText(this, "Server configuration saved.", Toast.LENGTH_SHORT).show();
            finish();

        }


    }




    public boolean isURL(String url) {
        try {
            (new java.net.URL(url)).openStream().close();
            return true;
        } catch (Exception ex) { }
        return false;
    }
}