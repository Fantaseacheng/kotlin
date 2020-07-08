package com.zengcheng.getyourshittogether.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "message")
class MessageEntity (
    @ColumnInfo(name = "messagename")
    var messagename: String?,
    @ColumnInfo(name = "houseNo")
    var houseNo: Int?,
    @ColumnInfo(name = "messagestatus") var messageStatus: String?,
    @ColumnInfo(name = "tenant") var tenant:String?,
    @ColumnInfo(name = "flag") var flag:Boolean?,
    @ColumnInfo(name = "messageno")
    @PrimaryKey
    @NotNull var messageno: Int):Serializable{
    fun getMessageName(): String? {
        return messagename
    }

    fun setMessageName(name :String?){
        this.messagename = name
    }

    fun getMessageNo():Int{
        return messageno
    }

    fun setMessageNo(no:Int){
        this.messageno = no
    }
}