package com.valisha.myattendance.UI.Dashboard;


import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.valisha.myattendance.UI.Attendance.Attendance;
import com.valisha.myattendance.UI.Attendance.AttendanceMap;
import com.valisha.myattendance.UI.Logs.AttendanceLogs;
import com.valisha.myattendance.UI.EmployeeLogin.EmployeeLoginActivity;
import com.valisha.myattendance.UI.Login.LoginActivity;
import com.valisha.myattendance.R;
import com.valisha.myattendance.ServiceConfiguration;
import com.valisha.myattendance.apps.CommonClass;
import com.valisha.myattendance.apps.IApp;
import com.valisha.myattendance.model.Db;
import com.valisha.myattendance.model.Logs;

import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity implements DashboardViewModel {

    private IApp app;
    private CardView cardView;
    private TextView tvAddress,tvDate,tvTime,name;
    private final int CODE_DEFAULT_LOCATION = 53;
    private static final int PERMISSION_REQUEST_CODE = 200;

    private Locale locale = new Locale("EN","IN");
    private ProgressDialog progressDialog;

    private Context context;

    private DashBoardPresenter dashBoardPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        app = ((IApp) getApplicationContext());

        dashBoardPresenter = new DashBoardPresenter(app,this);

        /*if(!app.preference().getLoginStatus()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }*/


        CommonClass commonClass = new CommonClass();
        progressDialog = commonClass.progressDialog(this);

        if (!checkPermission()) {
            requestPermission();
        }

        cardView = findViewById(R.id.attendance_card);
        tvAddress = findViewById(R.id.tv_address);
        tvDate = findViewById(R.id.tv_date);
        tvTime = findViewById(R.id.tv_time);
        name = findViewById(R.id.name);
        String address = app.preference().getAddress();


        if(!app.preference().getEmployeeName().isEmpty())
            name.setText(app.preference().getEmployeeName());
        else name.setVisibility(View.GONE);

        if(!address.isEmpty()) {


            tvAddress.setText(app.preference().getAddress());
            tvDate.setText(app.preference().getDate());
            tvTime.setText(app.preference().getTime());

            cardView.setVisibility(View.VISIBLE);

//            final AsycTask task = new AsycTask(this,app);
//            if(isInternetOn())
//            task.execute();




            Db.getInstance(this).logDao().getAllLogs().observe(this, new Observer<List<Logs>>() {
                @Override
                public void onChanged(List<Logs> logs) {



                    for(Logs logs1 : logs)
                    {
                        if(isInternetOn())
                        dashBoardPresenter.postLog(logs1.getId(),logs1.getEnrollID(),logs1.getDate(),logs1.getTime(),String.valueOf(logs1.getLat()),String.valueOf(logs1.getLng()),logs1.getAddress(),logs1.getUserName(),logs1.getPassword());
                    }
                }
            });

        }


    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted) {
                        Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION) && shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION) ) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Dashboard.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                askForLogout();
                return true;
            case R.id.actions_change_configuration:
               startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.actions_logs:
                startActivity(new Intent(this, AttendanceLogs.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void askForLogout()
    {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.baseline_warning_24)
                .setTitle("Logout")
                .setMessage("Do you want to logout from my attendance app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        app.preference().putIP("");
                        app.preference().putPort("");
                        app.preference().putAPIUsername("");
                        app.preference().putAPIPassword("");
                        app.preference().putId("");
                        app.preference().putEmployeeName("");
                        app.preference().putLoginStatus(false);
                        app.preference().putAddress("");
                        app.preference().putTime("");
                        app.preference().putDate("");
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void mark_attendance(View view)
    {
        if(!app.preference().getLoginStatus())
        startActivity(new Intent(this, LoginActivity.class));
        else startActivity(new Intent(this, AttendanceMap.class));
    }

    public void employeeLogin(View view)
    {

        if(!app.preference().getEmployeeLogin().isEmpty())
        startActivity(new Intent(this, EmployeeLoginActivity.class));
        else startActivity(new Intent(this, ServiceConfiguration.class));

    }


    @Override
    public void showProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.show();
            }
        });
    }

    @Override
    public void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void logPostFailed(String status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Dashboard.this, "Log is not published "+status, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void logPOst(String status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Attendance marked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void userNotRegister() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Dashboard.this);
                alertDialog.setIcon(getResources().getDrawable(R.drawable.baseline_warning_24));
                alertDialog.setMessage("Please check username & password.");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.setIcon(R.drawable.baseline_warning_24);
                dialog.show();

            }
        });
    }


    public boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {


            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {


            return false;
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }


}

