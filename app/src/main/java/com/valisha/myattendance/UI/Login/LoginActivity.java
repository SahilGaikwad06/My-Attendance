package com.valisha.myattendance.UI.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.valisha.myattendance.R;
import com.valisha.myattendance.UI.Attendance.Attendance;
import com.valisha.myattendance.UI.Attendance.AttendanceMap;
import com.valisha.myattendance.apps.IApp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText et_api_username,et_ipaddress,et_port,et_enrollId,et_employeeName;

    private TextInputEditText et_api_password;
    private IApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_api_username = findViewById(R.id.et_username);
        et_api_password = findViewById(R.id.et_password);
        et_ipaddress = findViewById(R.id.et_ip);
        et_port = findViewById(R.id.et_port);
        et_enrollId = findViewById(R.id.et_enrollID);
        et_employeeName = findViewById(R.id.et_employee_name);

        app = ((IApp) getApplicationContext());


        if(!app.preference().getIP().isEmpty())
            et_ipaddress.setText(app.preference().getIP());

        if(!app.preference().getPort().isEmpty())
            et_port.setText(app.preference().getPort());

        if(!app.preference().getAPIUsername().isEmpty())
            et_api_username.setText(app.preference().getAPIUsername());

        if(!app.preference().getAPIPassword().isEmpty())
            et_api_password.setText(app.preference().getAPIPassword());


        if(!app.preference().getId().isEmpty())
            et_enrollId.setText(app.preference().getId());

        if(!app.preference().getEmployeeName().isEmpty())
            et_employeeName.setText(app.preference().getEmployeeName());







//        et_port.setText("94");
//        et_api_username.setText("vinay");
//        et_api_password.setText("123456");
//        et_enrollId.setText("1");
//        et_employeeName.setText("Vinay Pujari");


        app = ((IApp) getApplicationContext());

    }

    public void login(View view)
    {
        String username = et_api_username.getText().toString();
        String password = et_api_password.getText().toString();
        String ipAddress = et_ipaddress.getText().toString();
        String port = et_port.getText().toString();
        String enrollID = et_enrollId.getText().toString();
        String employeeName = et_employeeName.getText().toString();

        if(username.isEmpty())
        {
            Toast.makeText(this, "Enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (password.isEmpty())
        {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(ipAddress.isEmpty())
        {
            Toast.makeText(this, "Enter IP", Toast.LENGTH_SHORT).show();
            return;
        }
//        else if (!validateIPAddress(ipAddress))
//        {
//            Toast.makeText(this, "Enter valid IP", Toast.LENGTH_SHORT).show();
//            return;
//        }
        else if (port.isEmpty())
        {
            Toast.makeText(this, "Enter Port", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (enrollID.isEmpty())
        {
            Toast.makeText(this, "Enter enroll id", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (employeeName.isEmpty())
        {
            Toast.makeText(this, "Enter employee name", Toast.LENGTH_SHORT).show();
            return;
        }
        else {


            app.preference().putIP(ipAddress);
            app.preference().putPort(port);
            app.preference().putAPIUsername(username);
            app.preference().putAPIPassword(password);
            app.preference().putId(enrollID);
            app.preference().putEmployeeName(employeeName);

            app.preference().putLoginStatus(true);
            Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
            finish();
        }

        startActivity(new Intent(this, AttendanceMap.class));

    }


    public static boolean isValidIPAddress(String ip)
    {
         String zeroTo255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";
         String regex= zeroTo255 + "\\."+ zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;
         Pattern p = Pattern.compile(regex);
         if (ip == null)
        {
            return false;
        }
         Matcher m = p.matcher(ip);
         return m.matches();
    }

    public static boolean validateIPAddress(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }

}