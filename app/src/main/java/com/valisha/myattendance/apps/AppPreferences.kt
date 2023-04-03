package com.valisha.myattendance.apps

import android.content.Context
import android.content.SharedPreferences
import com.valisha.myattendance.R
import com.valisha.myattendance.contracts.Preferences

class AppPreferences(val context:Context) : Preferences {


    private var prefs : SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)

    private val IP = "ip"
    private val PORT = "port"
    private val APIUSERNAME = "api_username"
    private val APIPASSWORD = "api_password"
    private val ENROLLID = "enroll_id"
    private val EMPLOYEENAME = "employee_name"
    private val EMPLOYEELOGIN = "employee_login"
    private val LOGINSTATUS = "login_status"
    private val ADDRESS = "address"
    private val TIME = "time"
    private val DATE = "date"



    override fun putIP(ip: String) {
        prefs.edit().putString(IP,ip).commit()
    }

    override fun getIP(): String {
        return prefs.getString(IP,null)?:""
    }

    override fun putPort(port: String) {
        prefs.edit().putString(PORT,port).commit()
    }

    override fun getPort(): String {
        return prefs.getString(PORT,null)?:""
    }

    override fun putAPIUsername(api_username: String) {
        prefs.edit().putString(APIUSERNAME,api_username).commit()
    }

    override fun getAPIUsername(): String {
        return prefs.getString(APIUSERNAME,null)?:""
    }

    override fun putAPIPassword(api_password: String) {
        prefs.edit().putString(APIPASSWORD,api_password).commit()
    }

    override fun getAPIPassword(): String {
        return prefs.getString(APIPASSWORD,null)?:""
    }

    override fun putId(id: String) {
        prefs.edit().putString(ENROLLID,id).commit()
    }

    override fun getId(): String {
        return prefs.getString(ENROLLID,null)?:""
    }

    override fun putEmployeeName(employee_name: String) {
        prefs.edit().putString(EMPLOYEENAME,employee_name).commit()
    }

    override fun getEmployeeName(): String {
        return prefs.getString(EMPLOYEENAME,null)?:""
    }

    override fun putEmployeeLogin(employeeLogin: String) {
        prefs.edit().putString(EMPLOYEELOGIN,employeeLogin).commit()
    }

    override fun getEmployeeLogin(): String {
        return prefs.getString(EMPLOYEELOGIN,null)?:""
    }

    override fun putLoginStatus(loginStatus: Boolean) {
        prefs.edit().putBoolean(LOGINSTATUS,loginStatus).commit()
    }

    override fun getLoginStatus() : Boolean {
        return prefs.getBoolean(LOGINSTATUS, false)
    }

    override fun putAddress(address: String) {
        prefs.edit().putString(ADDRESS,address).commit()
    }

    override fun getAddress(): String {
        return prefs.getString(ADDRESS,null)?:""
    }

    override fun putDate(date: String) {
        prefs.edit().putString(DATE,date).commit()
    }

    override fun getDate(): String {
        return prefs.getString(DATE,null)?:""

    }

    override fun putTime(time: String) {
        prefs.edit().putString(TIME,time).commit()
    }

    override fun getTime(): String {
        return prefs.getString(TIME,null)?:""

    }


}
