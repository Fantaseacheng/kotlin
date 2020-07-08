package com.zengcheng.getyourshittogether.adapter

import android.R.attr.fragment
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.database.entity.HouseEntity
import com.zengcheng.getyourshittogether.ui.home.HomeFragment
import java.net.URI


class HomeListAdapter(var list: ArrayList<HouseEntity>, var ctx: Context) : BaseAdapter() {
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

        var vH: ViewHolder?
        var view:View?
        if(convertView==null){
            view=View.inflate(ctx,R.layout.item_homelist,null);
            vH=ViewHolder();
            vH.textView=view.findViewById(R.id.house_name)
            vH.textView1=view.findViewById(R.id.house_describe)
            vH.imageView=view.findViewById(R.id.house_image)
            view.tag=vH
        }else{
            view = convertView
            vH = view.tag as ViewHolder
        }
        vH.textView?.text=list.get(position).getHouseName()
        vH.textView1?.text=list.get(position).getdes()
        return view!!
    }

    inner class ViewHolder{
        var textView:TextView?=null
        var textView1:TextView?=null
        var imageView:ImageView? = null
    }

}




