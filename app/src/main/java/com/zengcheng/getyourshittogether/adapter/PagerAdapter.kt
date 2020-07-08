package com.zengcheng.getyourshittogether.adapter

import android.view.View
import android.view.ViewGroup

import androidx.viewpager.widget.PagerAdapter


class PagerAdapter(viewLists: ArrayList<View>) : PagerAdapter() {
    var viewLists: ArrayList<View>
    override fun getCount(): Int {
        return viewLists.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(viewLists[position])
        return viewLists[position]
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(viewLists[position])
    }

    init {
        this.viewLists = viewLists
    }
}