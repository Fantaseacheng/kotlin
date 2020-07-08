package com.zengcheng.getyourshittogether.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "user")
class UserEntity  {

    @ColumnInfo(name = "account")
    private var account: String? = null

    @ColumnInfo(name = "password")
    private var password: String? = null

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "user_no")
    var userNo: Int? = null


    fun userEntity(
        account: String?,
        password: String?,
        userNo: Int
    ) {
        this.account = account
        this.password = password
        this.userNo = userNo
    }

    fun getAccount(): String? {
        return account
    }

    fun setAccount(account: String?) {
        this.account = account
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    fun getUser_no(): Int? {
        return userNo
    }

    fun setUser_no(user_no: Int) {
        this.userNo = user_no
    }

}