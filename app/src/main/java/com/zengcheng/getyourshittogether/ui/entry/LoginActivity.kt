package com.zengcheng.getyourshittogether.ui.entry

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.database.AppDatabase
import com.zengcheng.getyourshittogether.database.entity.UserEntity
import com.zengcheng.getyourshittogether.ui.MainActivity
import com.zengcheng.getyourshittogether.ui.Preference
import com.zengcheng.getyourshittogether.ui.entry.api.ApiRetrofit
import com.zengcheng.getyourshittogether.ui.entry.api.UserApiService
import io.reactivex.FlowableSubscriber
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_login.*
import org.reactivestreams.Subscription

class LoginActivity : AppCompatActivity() {
    private var mUserApiService: UserApiService? = null
    private var mUserEntity: UserEntity? = null
    private var flag: String by Preference(this, "flag", "")
    var loggedUserName: String by Preference(this, "loggedUserName", "")
    var loggedUserNo: String by Preference(this, "loggedUserNo", "")
    val db = AppDatabase.instance!!
    val userdao = db.userDao()!!
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mUserApiService = ApiRetrofit.getInstance()!!.getService(UserApiService::class.java)
        val userName:EditText=findViewById(R.id.login_username)
        val passWord:EditText = findViewById(R.id.login_password)

        val username1:String = userName.text.toString()
        val password1:String = passWord.text.toString()

        button_login.setOnClickListener {
            Toast.makeText(this@LoginActivity,login_username.text.toString(),Toast.LENGTH_LONG).show()
            attemptLogin(login_username.text.toString(),login_password.text.toString())
        }

        button_reg.setOnClickListener {
            val intent1 = Intent(this,RegisterActivity::class.java)
            startActivity(intent1)
        }


    }

    fun attemptLogin(username:String?,password:String?){
        if (username==""){
            login_username.error = "账号不能为空"
        }
        else if (password==""){
            login_password.error = "密码不能为空"
        }
        else if (userdao.get(username!!)?.getAccount()==login_username.text.toString()&&
            userdao.get(username)?.getPassword()==login_password.text.toString()){
            val intent=Intent(this,MainActivity::class.java)
            intent.putExtra("name",login_username.text.toString())
            intent.putExtra("userNoS",userdao.get(username)?.getUser_no())
            if (login_username.text.toString()=="admin"){
                val dummy = userdao.get("dummy")
                dummy!!.setPassword("changed")
                userdao.update(dummy)
            }
            else
            {
                val dummy = userdao.get("dummy")
                dummy!!.setPassword("unchanged")
                userdao.update(dummy)
            }
            startActivity(intent)
            flag = "logged"
            Log.e("flag",flag)
            finish()
        }

        else{
            Toast.makeText(this,"账号或密码错误",Toast.LENGTH_SHORT).show()
        }
        //login(username,password)
    }
    fun login(username: String?, password: String?) {
        val login = mUserApiService!!.login(username, password)
        login!!.observeOn(AndroidSchedulers.mainThread())
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
                    activityGo(this@LoginActivity,mUserEntity)
                }

            })
    }

    fun activityGo(ctx: Context, entity: UserEntity?){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        if (entity != null) {
            intent.putExtra("username",entity.getUser_no())
        }
        startActivity(intent)
        finish()
    }

}
