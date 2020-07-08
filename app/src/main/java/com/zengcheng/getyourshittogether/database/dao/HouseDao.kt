package com.zengcheng.getyourshittogether.database.dao

import androidx.room.*
import com.zengcheng.getyourshittogether.database.entity.HouseEntity

@Dao
interface HouseDao {

    @Insert
    fun add(houseEntity: HouseEntity?)

    @Delete
    fun remove(houseEntity: HouseEntity?)

    @Query("select * from house where houseno = :no ")
    fun get(no:Int?):HouseEntity?

    @Query("select * from house where userno = :no ")
    fun getAll(no: Int?): List<HouseEntity?>?

    @Update
    fun update(entity: HouseEntity)
}