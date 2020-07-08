package com.zengcheng.getyourshittogether.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.database.AppDatabase
import com.zengcheng.getyourshittogether.database.dao.HouseDao
import com.zengcheng.getyourshittogether.database.dao.MessageDao
import com.zengcheng.getyourshittogether.database.entity.MessageEntity
import com.zengcheng.getyourshittogether.ui.dashboard.DashboardFragment

import kotlinx.android.synthetic.main.activity_message_detail.*
import kotlinx.android.synthetic.main.content_message_detail.*

class MessageDetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_detail)
        setSupportActionBar(toolbar)

        val db:AppDatabase? = AppDatabase.instance!!
        val houseDao : HouseDao = db!!.houseDao()
        val messageDao : MessageDao? = db.messageDao()
        val entity = intent.getSerializableExtra("message") as MessageEntity
        message_name.text = entity.tenant
        message_detail_describe.text = houseDao.get(entity.houseNo)!!.housedescrpton
        message_detail_price.text ="  ¥ "+houseDao.get(entity.houseNo)!!.price.toString()+"/晚  "
        message_confirm.setOnClickListener { view ->
            Snackbar.make(view, "是否确认订单？", Snackbar.LENGTH_LONG)
                .setAction("确定") {
                    entity.messageStatus = "confirmed"
                    entity.flag = true
                    messageDao!!.update(entity)
                    finish()
                    /*intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)*/
                }.show()
        }
        message_deny.setOnClickListener {view ->
            Snackbar.make(view, "是否取消订单？", Snackbar.LENGTH_LONG)
                .setAction("确定") {
                    entity.messageStatus = "denied"
                    entity.flag = true
                    messageDao!!.update(entity)
                    finish()
                }.show()
        }
    }

}
