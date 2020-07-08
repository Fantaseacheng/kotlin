package com.zengcheng.getyourshittogether.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zengcheng.getyourshittogether.database.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    fun add(userEntity: UserEntity?)

    @Query("select * from user where account = :account ")
    fun get(account: String): UserEntity?

    @Update
    fun update(entity: UserEntity)

}