package com.zengcheng.getyourshittogether.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
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
import com.zengcheng.getyourshittogether.database.entity.MessageEntity

class CollectionAdapter(
    private val context: Context,
    private val strList: ArrayList<MessageEntity>
) : BaseAdapter() {
    @SuppressLint("ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder:ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_collection, null)
            holder = ViewHolder()
            holder.textView = view.findViewById(R.id.collection_housename)
            holder.textView1 = view.findViewById(R.id.collection_tenant)
            holder.textView2 = view.findViewById(R.id.collection_status)
            holder.imageView = view.findViewById(R.id.collection_housepic)
            view.tag = holder
        } else {
            holder = (view?.tag) as ViewHolder
        }
        val myItem = strList[position]
        holder.textView!!.text = myItem.getMessageName()
        holder.textView1!!.text = myItem.tenant
        if(myItem.messageStatus == "confirmed"){
            holder.textView2!!.text = "已完成"
            holder.textView2!!.setTextColor(Color.rgb(0, 132, 138))
        }
        val roundedCorners = RoundedCorners(20)
        val options : RequestOptions = RequestOptions.bitmapTransform(roundedCorners)
        if (view != null) {
            Glide.with(view).load("https://z1.muscache.cn/im/pictures/99dd2e82-f3ee-4356-b5fc-c279f589cc1e.jpg?aki_policy=xx_large").apply(options).into(holder.imageView)
        }
        return view!!
    }

    override fun getItem(position: Int): Any = strList[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getCount(): Int = strList.size

    inner class ViewHolder {
        var textView: TextView?=null
        var textView1: TextView?=null
        var textView2 :TextView?=null
        lateinit var imageView: ImageView
    }
}