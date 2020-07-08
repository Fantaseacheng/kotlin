package com.zengcheng.getyourshittogether.App

import android.app.Application
import com.zengcheng.getyourshittogether.database.AppDatabase
import com.zengcheng.getyourshittogether.database.dao.HouseDao
import com.zengcheng.getyourshittogether.database.entity.HouseEntity

class app : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)

    }
}