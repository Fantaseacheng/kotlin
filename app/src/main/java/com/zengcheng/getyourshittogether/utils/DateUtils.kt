package com.zengcheng.getyourshittogether.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    /**
     * 字符串转时间戳
     * 开始时间
     * @param timeString 年月日的String
     * @return 转化年月日为时间戳String（适配PHP 除以1000）-后台是java的话则不用除以1000
     */
    @SuppressLint("SimpleDateFormat")
    fun getTimeStart(timeString: String): String? {
        var timeStamp: String? = null
        val sdf = SimpleDateFormat("yyyy年MM月dd日")
        val d: Date
        try {
            d = sdf.parse(timeString)
            var l = d.time
            if (l.toString().length > 10) {
                l = l / 1000
            }
            timeStamp = l.toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeStamp
    }

    /**
     * 字符串转时间戳
     * 结束时间- 追加23小时59分钟
     * @param timeString 年月日的String
     * @return 转化年月日为时间戳String（适配PHP 除以1000）-后台是java的话则不用除以1000
     */
    @SuppressLint("SimpleDateFormat")
    fun getTimeEnd(timeString: String): String? {
        var timeStamp: String? = null
        val sdf = SimpleDateFormat("yyyy年MM月dd日HH:mm")
        val d: Date
        try {
            d = sdf.parse((timeString + "23:59"))
            var l = d . time
            if (l.toString().length > 10) {
                l = l / 1000
            }
            timeStamp = l.toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeStamp
    }
}