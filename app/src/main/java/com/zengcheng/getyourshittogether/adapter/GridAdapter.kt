package com.zengcheng.getyourshittogether.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.database.entity.HouseEntity

class GridAdapter(
    private val context: Context,
    private val strList: ArrayList<HouseEntity>
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder:ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_homelist, null)
            //我猜这个函数的作用是指定这个类所对应的小毛驴文件
            holder = ViewHolder()
            holder.textView = view.findViewById(R.id.house_name)
            holder.textView1 = view.findViewById(R.id.house_describe)
            holder.imageView = view.findViewById(R.id.house_image)
            view.tag = holder
        } else {
            holder = (view?.tag) as ViewHolder
        }
        //以上是固定格式
        val myItem = strList[position]
        holder.textView!!.text = myItem.getHouseName()
        holder.textView1!!.text = myItem.getdes()
        val roundedCorners = RoundedCorners(20)
        val options : RequestOptions = RequestOptions.bitmapTransform(roundedCorners)
        if (view != null) {
            Glide.with(view).load(myItem.housepc).apply(options).into(holder.imageView)
        }
        //以上是自定义每个控件的显示内容
        return view!!
    }

    override fun getItem(position: Int): Any = strList[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getCount(): Int = strList.size

    inner class ViewHolder {
        var textView: TextView?=null
        var textView1: TextView?=null
        lateinit var imageView: ImageView
    }
}