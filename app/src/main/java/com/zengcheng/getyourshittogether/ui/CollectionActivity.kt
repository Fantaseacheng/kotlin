package com.zengcheng.getyourshittogether.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.adapter.CollectionAdapter
import com.zengcheng.getyourshittogether.adapter.MessageListAdapter
import com.zengcheng.getyourshittogether.database.AppDatabase
import com.zengcheng.getyourshittogether.database.dao.MessageDao
import com.zengcheng.getyourshittogether.database.entity.MessageEntity
import kotlinx.android.synthetic.main.activity_colllection.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

class CollectionActivity : AppCompatActivity() {
    var messageList = ArrayList<MessageEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colllection)
        initData()
        initView()
    }
    fun initData() {
        /*var firstHouse = HouseEntity("first house","this is first house","hh","1","1")
        var secondHouse = HouseEntity("second house","this is second house","hh","2","2")
        houseList.add(firstHouse)
        houseList.add(secondHouse)*/
        val db: AppDatabase? = AppDatabase.instance
        val messageDao: MessageDao? = db!!.messageDao()
        //val firstMessage = MessageEntity("first message",1,"undefined","user101",88)
        messageList.addAll(messageDao!!.getByFlag(true) as Collection<MessageEntity>)
        if(messageList.size!=0){
            hint.isInvisible = true
        }
    }

    private fun initView() {
        val myAdapter = CollectionAdapter(this,messageList);
        collection_list.adapter = myAdapter
    }

}
