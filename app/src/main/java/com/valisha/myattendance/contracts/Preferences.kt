package com.valisha.myattendance.contracts


interface Preferences {

    fun putIP(ip: String)
    fun getIP(): String

    fun putPort(port: String)
    fun getPort(): String

    fun putAPIUsername(api_username: String)
    fun getAPIUsername(): String

    fun putAPIPassword(api_password: String)
    fun getAPIPassword(): String

    fun putId(id: String)
    fun getId(): String

    fun putEmployeeName(employee_name: String)
    fun getEmployeeName(): String

    fun putEmployeeLogin(employeeLogin: String)
    fun getEmployeeLogin(): String

    fun putLoginStatus(loginStatus : Boolean)
    fun getLoginStatus() : Boolean

    fun putAddress(address: String)
    fun getAddress(): String

    fun putDate(date: String)
    fun getDate(): String

    fun putTime(time: String)
    fun getTime(): String










}