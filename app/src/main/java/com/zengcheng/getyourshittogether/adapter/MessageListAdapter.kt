package com.zengcheng.getyourshittogether.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.database.entity.HouseEntity
import com.zengcheng.getyourshittogether.database.entity.MessageEntity

class MessageListAdapter(var list: ArrayList<MessageEntity>, var ctx: Context) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }
    override fun getItemId(position: Int): Long {
        return position.toLong();
    }
    override fun getItem(position: Int): Any {

        return list.get(position)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var vH:ViewHolder?=null
        var view: View?=null
        if(convertView==null){
            view= View.inflate(ctx, R.layout.item_messagelist,null);
            vH=ViewHolder();
            view.tag=vH
        }else{
            view = convertView
            vH = view.tag as ViewHolder
        }
        return view!!
    }
    inner class ViewHolder{
        var textView: TextView?=null
    }

}