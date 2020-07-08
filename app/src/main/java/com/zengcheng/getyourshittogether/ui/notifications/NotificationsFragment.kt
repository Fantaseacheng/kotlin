package com.zengcheng.getyourshittogether.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartConfiger.*
import com.google.android.material.tabs.TabLayout
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.database.AppDatabase
import com.zengcheng.getyourshittogether.ui.Preference
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() ,TabLayout.OnTabSelectedListener{

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
       
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val xAxisData :Array<String> = arrayOf("一月","二月","三月","四月","五月","六月")
        val db = AppDatabase.instance!!
        val messagedao = db.messageDao()!!
        val userdao = db.userDao()
        val june:Array<Int> = arrayOf(33, 36, 20, 48, 44,6+messagedao.getByFlag(true).size)
        val aaChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            .title("销量")
            .backgroundColor("#ffffff")
            .yAxisTitle("销量")
            .categories(arrayOf("一月","二月","三月","四月","五月","六月"))
            .stacking(AAChartStackingType.Normal)
            .series(arrayOf(
                AASeriesElement()
                .name("取消订单数")
                .data(arrayOf(3,2,15,1,5,0+messagedao.getByStatus("denied").size)),
                AASeriesElement()
                    .name("完成订单数")
                    .data(arrayOf(33,36,20,48,44,6+messagedao.getByStatus("confirmed").size))

            )
            )
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            //var loggedUserNo: String by Preference(this.requireContext(), "loggedUserNo", "")
            if(userdao!!.get("dummy")!!.getPassword()=="unchanged"){
                data_hint.isInvisible = false
                data_hint_pic.isInvisible = false
            }
            if(userdao.get("dummy")!!.getPassword()=="changed"){
                val aaChartView = root.findViewById<AAChartView>(R.id.aachart)
                aaChartView.aa_drawChartWithChartModel(aaChartModel)
            }

        })
        return root
    }

    fun initView(){

    }
    override fun onTabReselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }
}
