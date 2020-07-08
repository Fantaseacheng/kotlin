package com.zengcheng.getyourshittogether.ui.entry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.database.AppDatabase
import com.zengcheng.getyourshittogether.database.entity.UserEntity
import com.zengcheng.getyourshittogether.ui.Preference
import com.zengcheng.getyourshittogether.ui.entry.api.UserApiService
import io.reactivex.FlowableSubscriber
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_register.*
import org.reactivestreams.Subscription

class RegisterActivity : AppCompatActivity() {
    private var userNo: String by Preference(this, "userNo", "0")
    private var mUserApiService: UserApiService? = null
    private var mUserEntity: UserEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initData()
    }
    fun initData(){
        val db = AppDatabase.instance!!
        val userdao = db.userDao()!!
         button_register.setOnClickListener{
             if(reg_password.text.toString()!=reg_password_confirm.text.toString())
             {
                 reg_password_confirm.error = "密码不一致"
             }
             else if (reg_username.text.toString()==""){
                 reg_username.error = "账号不能为空"
             }
             else if(reg_password.text.toString()==""){
                 reg_password.error = "密码不能为空"
             }
             else{
                 val newUser = UserEntity()
                 newUser.setAccount(reg_username.text.toString())
                 newUser.setPassword(reg_password.text.toString())
                 userNo =(userNo.toInt()+1).toString()
                 newUser.setUser_no(userNo.toInt())
                 userdao.add(newUser)
                 Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show()
                 val intent  = Intent(this,LoginActivity::class.java)
                 startActivity(intent)
                 finish()
             }
         }


    }

    fun register(username: String?, password: String?) {
        val reg = mUserApiService!!.register(username, password)
        reg!!.observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : FlowableSubscriber<UserEntity?> {
                override fun onSubscribe(s: Subscription) {
                    s.request(Long.MAX_VALUE)
                }

                override fun onNext(userInfo: UserEntity?) {
                    Toast.makeText(applicationContext, userInfo.toString(), Toast.LENGTH_SHORT)
                        .show()
                    mUserEntity = userInfo
                }

                override fun onError(t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(applicationContext, "账号或密码错误", Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {
                    val intent1 = Intent(this@RegisterActivity,LoginActivity::class.java)
                    startActivity(intent1)
                }

            })
    }



}

