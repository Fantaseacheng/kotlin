package com.zengcheng.getyourshittogether.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "house")
class HouseEntity(
    @ColumnInfo(name = "housename") var housename: String?,
    @ColumnInfo(name = "descrpton") var housedescrpton: String?,
    @ColumnInfo(name = "pcture") var housepc: String?,
    @ColumnInfo(name = "userno") var userno: Int?,
    @ColumnInfo(name = "price") var price: Int,
    @ColumnInfo(name = "daten") var daten:String?,
    @ColumnInfo(name = "dateout") var dateout:String?,
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "houseno") var houseno: Int
) {


    fun getHouseName():String?{
        return housename
    }

    fun setHouseName(name:String){
        this.housename = name
    }

    fun getdes():String?{
        return housedescrpton
    }

    fun setDes(des:String){
        this.housedescrpton = des
    }

    fun getpic():String?{
        return housepc
    }

    fun setPic(pic:String){
        this.housepc = pic
    }
    fun getuserno():Int?{
        return userno
    }

    fun setUserNo(no:Int){
        this.userno = no
    }
    fun getdaten():String?{
        return daten
    }

    fun setdaten(daten:String){
        this.daten = daten
    }
    fun getdateout():String?{
        return dateout
    }

    fun setdateout(dateout:String){
        this.dateout = dateout
    }
}