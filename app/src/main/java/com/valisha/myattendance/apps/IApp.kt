package com.valisha.myattendance.apps

import com.valisha.myattendance.contracts.Preferences
import com.valisha.myattendance.model.Db
import com.valisha.myattendance.network.WebService


interface IApp {


    fun preference() : Preferences

    fun db() : Db

    fun webService() : WebService



}