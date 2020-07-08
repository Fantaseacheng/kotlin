package com.zengcheng.getyourshittogether.ui.Splash

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.zengcheng.getyourshittogether.ui.entry.LoginActivity
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.database.AppDatabase
import com.zengcheng.getyourshittogether.database.dao.HouseDao
import com.zengcheng.getyourshittogether.database.dao.MessageDao
import com.zengcheng.getyourshittogether.database.entity.HouseEntity
import com.zengcheng.getyourshittogether.database.entity.MessageEntity
import com.zengcheng.getyourshittogether.database.entity.UserEntity
import com.zengcheng.getyourshittogether.ui.MainActivity
import com.zengcheng.getyourshittogether.ui.Preference

class SplashActivity : AppCompatActivity() {
    private var flag: String by Preference(this, "flag", "")
    private val mHandler = SplashHandle(this)
    private var first: String by Preference(this, "first", "")
    // 将常量放入这里
    companion object {

        // 正常跳转到登录界面 常量 防止以后增加业务逻辑
        val MSG_LAUNCH : Int = 0

        val MSG_MAIN : Int = 1
        // 延时时间
        val SLEEP_TIME = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if(first==""){
            initData()
            first="true"
        }
    }
    private fun initData(){
        val db: AppDatabase? = AppDatabase.instance
        val messageDao: MessageDao? = db!!.messageDao()
        val houseDao: HouseDao? = db.houseDao()
        val userdao = db.userDao()
        val admin = UserEntity()
        admin.setAccount("admin")
        admin.setPassword("123456")
        admin.setUser_no(100)
        val dummy = UserEntity()
        dummy.setAccount("dummy")
        dummy.setPassword("dummy")
        dummy.setUser_no(200)
        val firstMessage = MessageEntity("first message",3,"undefined","user101",false,100)
        val SecondMessage = MessageEntity("second message",4,"undefined","user200",false,99)
        val firstHouse = HouseEntity("整套公寓·成都","【新房特惠】米诺卡·近春熙路太古里/九眼桥/阳光新业奢享复式Loft","https://z1.muscache.cn/im/pictures/d1316854-23e4-4db8-9f13-2a4cc3b835a6.jpg?aki_policy=xx_large",1,178,"6月3日","6月4日",1)
        val secondHouse = HouseEntity("整套公寓·成都","米诺.高空观景双卧套房 已消毒可入住","https://z1.muscache.cn/im/pictures/5e8cc906-575e-4244-95fb-d97212c32087.jpg?aki_policy=x_medium",1,200,"6月3日","6月4日",2)
        val thirdHouse = HouseEntity("整套公寓·成都", "At Sunset/loft/投影仪/宽窄巷子/夜猫子夜市/太古里/春熙路","https://z1.muscache.cn/im/pictures/96e9dbb4-65c3-4710-b0cd-5ff70fdaa6b6.jpg?aki_policy=xx_large",1,198,"6月3日","6月4日",3)
        val forthHouse = HouseEntity("整套公寓·成都","【新房超低价】每客消毒安心住/春熙路太古里/近地铁/洗衣机微波炉/行李寄存","https://z1.muscache.cn/im/pictures/c5487e8e-cea9-412e-9edd-0757cd3b1fb2.jpg?aki_policy=xx_large",1,154,"6月3日","6月4日",4)
        val fifthHouse = HouseEntity("整套公寓·成都","原宿 | 旅居美学 | 窗际浴缸 | HIFI巨幕 | 星级公寓楼 | 春熙路商圈","https://z1.muscache.cn/im/pictures/99dd2e82-f3ee-4356-b5fc-c279f589cc1e.jpg?aki_policy=xx_large",1,167,"6月3日","6月4日",5)
        val sixthHouse = HouseEntity("整套公寓·成都","2m大床/投影/位于春熙路正中/太古里/150m地铁站/床品干洗高温消毒/可寄存","https://z1.muscache.cn/im/pictures/020ff3d9-1456-423f-84a4-e0c0f77099e8.jpg?aki_policy=xx_large",1,154,"6月3日","6月4日",6)
        val seventhHouse = HouseEntity("整套公寓·成都","米诺.高空观景双卧套房 已消毒可入住","https://z1.muscache.cn/im/pictures/667c97f5-36ad-4a3f-b256-db0637515b69.jpg?aki_policy=xx_large",1,167,"6月3日","6月4日",7)
        messageDao!!.add(firstMessage)
        messageDao.add(SecondMessage)
        houseDao!!.add(firstHouse)
        houseDao.add(secondHouse)
        houseDao.add(thirdHouse)
        houseDao.add(forthHouse)
        houseDao.add(fifthHouse)
        houseDao.add(sixthHouse)
        houseDao.add(seventhHouse)
        userdao!!.add(admin)
        userdao.add(dummy)
    }
    override fun onResume() {
        super.onResume()
        val start = System.currentTimeMillis()

        /*
        这里计算了两个时间
        两个时间间可以放入判断条件：是否需要自动登录等
         */

        val costTime = System.currentTimeMillis() - start

        val left = SLEEP_TIME - costTime

        // kotlin中取消了java中的三目运算，换成if...else...
        mHandler.postDelayed(runnable, if(left > 0) left else 0)
    }


    val runnable = Runnable {
        kotlin.run {
            Log.e("flag in Splash",flag)
            if(flag=="logged"){
                val message = mHandler.obtainMessage(MSG_MAIN)
                mHandler.sendMessage(message)
            }
            else{
                val message = mHandler.obtainMessage(MSG_LAUNCH)
                mHandler.sendMessage(message)
            }

        }
    }

    // 弱引用handler内部类
    private class SplashHandle(cls : SplashActivity) : UIHandler<SplashActivity>(cls) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val activity = ref?.get()
            if (null != activity){

                if (activity.isFinishing)
                    return

                when(msg?.what){

                    // 正常跳转到登录界面
                    MSG_LAUNCH -> {
                            activity.startActivity(Intent(activity, LoginActivity::class.java))
                            activity.finish()
                    }

                    else->{
                        activity.startActivity(Intent(activity, MainActivity::class.java))
                        activity.finish()
                    }
                }
            }
        }
    }
}
