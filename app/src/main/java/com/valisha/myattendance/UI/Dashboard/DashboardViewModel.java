package com.valisha.myattendance.UI.Dashboard;

public interface DashboardViewModel {


    void showProgress();

    void hideProgress();


    void logPostFailed(String status);

    void logPOst(String status);

    void userNotRegister();
}
