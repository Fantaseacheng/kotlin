package com.zengcheng.getyourshittogether.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.adapter.GridAdapter
import com.zengcheng.getyourshittogether.database.AppDatabase
import com.zengcheng.getyourshittogether.database.dao.HouseDao
import com.zengcheng.getyourshittogether.database.dao.UserDao
import com.zengcheng.getyourshittogether.database.entity.HouseEntity
import com.zengcheng.getyourshittogether.ui.*
import com.zengcheng.getyourshittogether.ui.entry.LoginActivity
import kotlinx.android.synthetic.main.layout_home.*

class HomeFragment : Fragment() , SwipeRefreshLayout.OnRefreshListener{
    var houseList = ArrayList<HouseEntity>()
    private lateinit var homeViewModel: HomeViewModel
    lateinit var myAdapter: GridAdapter
    val db: AppDatabase? = AppDatabase.instance
    val houseDao: HouseDao = db!!.houseDao()
    val userdao = db!!.userDao()
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
           /* var loggedUserNo: String by Preference(this.requireActivity(), "loggedUserNo", "")
            var globalUserNo = activity?.intent?.getIntExtra("userNoS",1)
            var globalUserName = activity!!.intent!!.getStringExtra("name")*/
            if(userdao!!.get("dummy")!!.getPassword()=="unchanged"){
                house_hint.isInvisible = false
                house_hint_pic.isInvisible = false
            }
            if(userdao.get("dummy")!!.getPassword()=="changed"){
                initView()
            }
            //initPager()
        })
        return root
    }

/*    override fun onStart() {
        super.onStart()
        radio_btn1.isChecked = true
    }*/

    override fun onResume() {
        super.onResume()
        if(userdao!!.get("dummy")!!.getPassword()=="changed"){
            initView()
            myAdapter.notifyDataSetChanged()
        }
    }
    fun initView() {
        myAdapter = GridAdapter(this.requireContext(), houseDao.getAll(1) as ArrayList<HouseEntity>)
        houseList = houseDao.getAll(1) as ArrayList<HouseEntity>
        gridview.adapter = myAdapter
        gridview.numColumns = 2
        add_house.setOnClickListener {
            val intent =  Intent(this.requireContext(),AddHouseActivity::class.java)
            intent.putExtra("houseNo",houseList.size)
            startActivity(intent)
        }
        gridview.setOnItemClickListener { parent, view, position, id ->
            //Toast.makeText(this.requireContext(),position.toString(),Toast.LENGTH_SHORT).show()
            val intent1 =  Intent(this.requireContext(),HouseDetailActivity::class.java)
            intent1.putExtra("houseDes",houseList.get(position).housedescrpton)
            intent1.putExtra("picture",houseList.get(position).housepc)
            intent1.putExtra("price",houseList.get(position).price)
            intent1.putExtra("houseNo",houseList.get(position).houseno)
            startActivity(intent1)
        }
    }

    override fun onRefresh() {
        /*swipe_home.isRefreshing = false
        myAdapter.notifyDataSetChanged()*/
    }


/*    fun initPager(){
        val fragmentList = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        //为viewpager设置适配器，kotlin中可以不用findviewbyid ，而是可以直接用设置的id.属性的方式书写
        viewPager.adapter = viewPagerAdapter(parentFragmentManager, fragmentList)
        //为viewpager设置页面滑动监听
        viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                Log.e("ViewPager","position is $position")
                //判断滑动后选择的页面设置相应的RadioButton被选中
                when (position) {
                    //这里的btn_one two the 分别是布局中的三个RadioButton的id，直接调用其方法进行设置
                    0 -> radio_btn1.isChecked = true
                    //当只是写btn_one.isChecked获取的是它的选中状态，如果让它 =true 就会默认调用setChecked()方法进行改变状态
                    1 -> radio_btn2.isChecked = true
                    2 -> radio_btn3.isChecked = true
                }
            }

        })

        radio_group.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                Log.e("ViewPager","checkedId is $checkedId")
                when (checkedId) {
                    //当点击按钮时，调用如下方法选择viewpager要显示的哪个页面
                    R.id.radio_btn1 -> viewPager.setCurrentItem(0, true)
                    R.id.radio_btn2 -> viewPager.setCurrentItem(1, true)
                    R.id.radio_btn3 -> viewPager.setCurrentItem(2, true)
                }
            }
        })*/
    }





