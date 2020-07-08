package com.zengcheng.getyourshittogether.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.database.AppDatabase
import com.zengcheng.getyourshittogether.database.dao.MessageDao
import com.zengcheng.getyourshittogether.database.entity.UserEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var sUserEntity: UserEntity? = null
    var sMainActivity: MainActivity? = null
    fun getInstance(): MainActivity? {
        return sMainActivity
    }
    val db:AppDatabase? = AppDatabase.instance!!
    val messageDao : MessageDao? = db?.messageDao()
    val userdao = db!!.userDao()
    var loggedUserNo: String by Preference(this, "loggedUserNo", "")
    lateinit var menuView:BottomNavigationMenuView
    //获取第2个itemView
    lateinit var itemView:BottomNavigationItemView
    lateinit var badgeView:View
    lateinit var count:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        //supportActionBar?.elevation = 0F
        //Objects.requireNonNull(getSupportActionBar())?.hide();
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_dashboard,
            R.id.navigation_notifications,
            R.id.navigation_profile
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        if(userdao!!.get("dummy")!!.getPassword()=="changed") {
            menuView = nav_view.getChildAt(0) as BottomNavigationMenuView
            //获取第2个itemView
            itemView = menuView.getChildAt(1) as BottomNavigationItemView
            //引入badgeView
            badgeView = LayoutInflater.from(this).inflate(R.layout.layout_badge, menuView, false)
            itemView.addView(badgeView)
            count = badgeView.findViewById(R.id.tv_badge)
        }

    }

    override fun onResume() {
        super.onResume()
        if(userdao!!.get("dummy")!!.getPassword()=="changed") {
            if (messageDao != null) {
                Log.e("pop", messageDao.getByFlag(false).size.toString())
                count.text = messageDao.getByFlag(false).size.toString()
                if (messageDao.getByFlag(false).isEmpty()) {
                    itemView.removeView(badgeView)
                }
            }
        }
    }

}
