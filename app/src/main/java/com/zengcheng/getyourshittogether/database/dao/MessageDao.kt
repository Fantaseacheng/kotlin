package com.zengcheng.getyourshittogether.database.dao

import androidx.room.*
import com.zengcheng.getyourshittogether.database.entity.MessageEntity

@Dao
interface MessageDao {
    @Insert
    fun add(messageEntity: MessageEntity?)

    @Delete
    fun remove(messageEntity: MessageEntity?)

    @Query("select * from message where messageno = :no ")
    fun getAll(no: Int?): List<MessageEntity?>?

    @Query("select * from message where messagestatus = :status ")
    fun getByStatus(status: String?): List<MessageEntity?>

    @Update
    fun update(message:MessageEntity)

    @Query("select * from message where flag = :flag ")
    fun getByFlag(flag: Boolean?): List<MessageEntity?>
}