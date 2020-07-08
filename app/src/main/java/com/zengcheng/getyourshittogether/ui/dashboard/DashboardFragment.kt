package com.zengcheng.getyourshittogether.ui.dashboard

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.adapter.MessageListAdapter
import com.zengcheng.getyourshittogether.database.AppDatabase
import com.zengcheng.getyourshittogether.database.dao.MessageDao
import com.zengcheng.getyourshittogether.database.entity.MessageEntity
import com.zengcheng.getyourshittogether.ui.MessageDetailActivity
import com.zengcheng.getyourshittogether.ui.Preference
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.layout_message.*

class DashboardFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener {

    private lateinit var dashboardViewModel: DashboardViewModel
    var messageList = ArrayList<MessageEntity>()
    val db: AppDatabase? = AppDatabase.instance
    val messageDao: MessageDao? = db!!.messageDao()
    val userdao = db!!.userDao()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
           //var loggedUserNo: String by Preference(this.requireActivity(), "loggedUserNo", "")
            if(userdao!!.get("dummy")!!.getPassword()=="unchanged"){
                message_hint.isInvisible = false
                message_hint_pic.isInvisible = false
            }
            if(userdao.get("dummy")!!.getPassword()=="changed"){
                initData()
                initView()
            }

        })
        return root
    }

    override fun onResume() {
        super.onResume()
        if(userdao!!.get("dummy")!!.getPassword()=="changed"){
            initData()
            initView()
            if(messageDao!!.getByStatus("undefined").isEmpty()){
                message_hint.isInvisible = false
                message_hint_pic.isInvisible = false
            }
        }

    }
    fun initData() {
        /*var firstHouse = HouseEntity("first house","this is first house","hh","1","1")
        var secondHouse = HouseEntity("second house","this is second house","hh","2","2")
        houseList.add(firstHouse)
        houseList.add(secondHouse)*/
        messageList.addAll(messageDao!!.getByStatus("undefined") as Collection<MessageEntity>)
        if(messageDao.getByStatus("undefined").isEmpty()){
            message_hint.isInvisible = false
            message_hint_pic.isInvisible = false
        }

    }
    fun initView() {
        val myAdapter = MessageListAdapter(messageDao!!.getByStatus("undefined") as ArrayList<MessageEntity>,this.requireContext());
        listview_message.adapter = myAdapter
        swipe_message.setColorSchemeColors(Color.rgb(0,132,138))
        swipe_message.setOnRefreshListener(this)
        listview_message.setOnItemClickListener { parent, view, position, id ->
            //Toast.makeText(this.requireContext(),position.toString(), Toast.LENGTH_SHORT).show()
            val intent =  Intent(this.requireContext(), MessageDetailActivity::class.java)
            intent.putExtra("message", messageList[position])
            startActivity(intent)
        }
    }

    override fun onRefresh() {
        swipe_message.isRefreshing = false
        initView()
        if(messageDao!!.getByStatus("undefined").isEmpty()){
            message_hint.isInvisible = false
            message_hint_pic.isInvisible = false
        }
        Toast.makeText(this.requireContext(),"hh",Toast.LENGTH_SHORT).show()

    }

}
