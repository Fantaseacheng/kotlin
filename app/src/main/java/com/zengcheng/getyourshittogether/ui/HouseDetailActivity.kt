package com.zengcheng.getyourshittogether.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.borax12.materialdaterangepicker.date.DatePickerDialog
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.adapter.PagerAdapter
import com.zengcheng.getyourshittogether.database.AppDatabase
import com.zengcheng.getyourshittogether.database.dao.HouseDao
import com.zengcheng.getyourshittogether.database.entity.HouseEntity
import com.zengcheng.getyourshittogether.utils.DateUtils
import kotlinx.android.synthetic.main.activity_house_detail.*
import kotlinx.android.synthetic.main.content_add_house.*
import kotlinx.android.synthetic.main.layout_content.*
import java.util.*
import kotlin.collections.ArrayList


class HouseDetailActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener {
    val TAKE_PHOTO: Int = 1
    val CHOOSE_PHOTO: Int = 2
    private var aList: ArrayList<View>? = null
    private  var mAdapter: PagerAdapter? = null
    private var houseName:String? = null
    var houseList = ArrayList<HouseEntity>()
    private lateinit var toobarw: Toolbar;
    var imageUri: Uri? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var pNights: String by Preference(this, "nights", "")
    private var newpic: String by Preference(this, "newpic", "")
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_detail)
        intiView()
        setSupportActionBar(toolbarw)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun intiView() {
        val db= AppDatabase.instance
        val housedao:HouseDao = db!!.houseDao()
        val houseNo = intent.getIntExtra("houseNo",0)
        val newHouse = housedao.get(houseNo)
        newpic = newHouse?.getpic().toString()
        Glide.with(this).load(newHouse?.housepc).into(detail_pic)
        detail_describe.setText(newHouse?.housedescrpton)
        detail_price.setText(newHouse?.price.toString())
        detail_date_in.setText(newHouse?.daten)
        detail_date_out.setText(newHouse?.dateout)
        if (pNights!=""){
            detail_date_total.setText(pNights)
        }
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
                if (p1 == 0) {
                    setScanToolColor(false)
                } else {
                    setScanToolColor(true)
                }
            }

        })
        house_delete.setOnClickListener {view->
            Snackbar.make(view, "删除此房源？", Snackbar.LENGTH_LONG)
                .setAction("确定") {
                    housedao.remove(housedao.get(intent.getIntExtra("houseNo",0)))
                    finish()
                    intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }.show()
        }
        detail_pic.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            } else {
                openAlbum()
            }
        }
        house_edit.setOnClickListener {
            detail_price.isFocusable = true
            detail_price.isFocusableInTouchMode = true
            detail_price.requestFocus()
            detail_price.setBackgroundColor(Color.rgb(0,0,0))

            detail_pic.isFocusable = true
            detail_pic.isClickable = true
            detail_pic.isFocusableInTouchMode = true
            detail_pic.requestFocus()

            detail_describe.isFocusable = true
            detail_describe.isFocusableInTouchMode = true
            detail_describe.requestFocus()
            detail_describe.setBackgroundColor(Color.rgb(255,255,0))

            detail_date_in.isClickable = true
            detail_date_in.isFocusable = true
            detail_date_in.isFocusableInTouchMode = true
            detail_date_in.setBackgroundColor(Color.rgb(0,0,0))
            detail_date_in.requestFocus()

            edit_confirm.isVisible = true
            edit_denied.isVisible = true

            house_delete.isGone = true
            house_edit.isGone = true


        }

        edit_confirm.setOnClickListener {view->
            Snackbar.make(view, "是否提交修改？", Snackbar.LENGTH_LONG)
                .setAction("确定") {
                    if(detail_price.text.toString()==""||detail_price.text.toString()=="0"){
                        detail_price.error = "输入正确的价格"
                    }
                    else {
                        detail_pic.isFocusable = false
                        detail_pic.isFocusableInTouchMode = false
                        detail_pic.isClickable = false

                        detail_describe.isFocusable = false
                        detail_describe.isFocusableInTouchMode = false
                        detail_describe.setBackgroundColor(Color.rgb(255,255,255))

                        detail_price.isFocusable = false
                        detail_price.isFocusableInTouchMode = false
                        detail_price.setBackgroundColor(Color.rgb(255, 255, 255))

                        detail_date_in.isClickable = false
                        detail_date_in.isFocusable = false
                        detail_date_in.setBackgroundColor(Color.rgb(255, 255, 255))

                        edit_denied.isGone = true
                        edit_confirm.isGone = true
                        house_delete.isGone = false
                        house_edit.isGone = false
                        newHouse?.housedescrpton = detail_describe.text.toString()
                        newHouse?.setPic(newpic)
                        newHouse?.price = detail_price.text.toString().toInt()
                        newHouse?.daten = detail_date_in.text.toString()
                        newHouse?.dateout = detail_date_out.text.toString()
                        if (newHouse != null) {
                            housedao.update(newHouse)
                        }
                        val newHouse2 = housedao.get(houseNo)
                        detail_price.setText(newHouse2?.price.toString())
                        detail_date_in.setText(newHouse2?.daten)
                        detail_date_out.setText(newHouse2?.dateout)
                        Toast.makeText(this, "房源已修改", Toast.LENGTH_SHORT).show()
                    }
                }.show()

        }
        edit_denied.setOnClickListener {
            detail_pic.isFocusable = false
            detail_pic.isFocusableInTouchMode = false
            detail_pic.isClickable = false

            detail_describe.isFocusable = false
            detail_describe.isFocusableInTouchMode = false
            detail_describe.setBackgroundColor(Color.rgb(255,255,255))

            detail_price.isFocusable = false
            detail_price.isFocusableInTouchMode = false
            detail_price.setBackgroundColor(Color.rgb(255,255,255))

            detail_date_in.isClickable = false
            detail_date_in.isFocusable = false
            detail_date_in.setBackgroundColor(Color.rgb(255,255,255))

            edit_denied.isGone = true
            edit_confirm.isGone = true
            house_delete.isGone = false
            house_edit.isGone = false

            detail_price.setText(newHouse?.price.toString())
            detail_date_in.setText(newHouse?.daten)
            detail_date_out.setText(newHouse?.dateout)
        }
        detail_date_in.setOnClickListener {
            showDatePickerDialog()
        }

    }

    private fun setScanToolColor(isSetColor: Boolean) {
        if (isSetColor) {
            bar_tv.text = "房源详情"
        } else {
            bar_tv.text = ""

        }


    }
    //显示 日期选择器
    private fun showDatePickerDialog() {
        val now = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
            this,
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
        )
        dpd.show(fragmentManager, "Datepickerdialog")
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TAKE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        val bitmap =
                            BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                        detail_pic?.setImageBitmap(bitmap)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            }

            CHOOSE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    handleImageOnKitkat(data)
                }
            }

            else -> {

            }
        }
    }

    fun openAlbum() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.setType("image/*")
        startActivityForResult(intent, CHOOSE_PHOTO)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum()
                } else {
                    Toast.makeText(
                        this,
                        "You denied this permission!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            else -> {

            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun handleImageOnKitkat(data: Intent?) {
        var imagePath: String? = null
        val uri = data?.data
        Log.d("TAG", "handleImageOnKitkat: Uri is: " + uri)
        if (DocumentsContract.isDocumentUri(this, uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents".equals(uri?.authority)) {
                val id = docId.split(":")[1]
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.downloads.documents".equals(uri?.authority)) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    docId.toLong()
                )
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri?.scheme, true)) {
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri?.scheme, true)) {
            imagePath = uri?.path
        }
        if (imagePath != null) {
            newpic = imagePath
        }
        displayImage(imagePath)
    }

    fun handleImageBeforeKitkat(data: Intent?) {
        val uri = data?.data
        var imagePath: String? = getImagePath(uri, null)
        displayImage(imagePath)
    }

    fun getImagePath(uri: Uri?, selection: String?): String? {
        var path: String? = null
        var cursor = contentResolver.query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }


    fun displayImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            detail_pic?.setImageBitmap(bitmap)
        } else {
            Toast.makeText(this, "Failed to get image!!", Toast.LENGTH_SHORT).show()
        }
    }
    /**
     * @param yearn年（始）例：2018
     * @param monthOfYear月（始）例：8
     * @param dayOfMonth日（始）例：15
     * @param yearEnd（结束）例：2018
     * @param monthOfYearEnd（结束）例：8
     * @param dayOfMonthEnd（结束）例：16
     * 由于发现 此控件有个bug，显示的月份少1月，故在取数据的时候，加1.
     */
    override fun onDateSet(
        view: DatePickerDialog?,
        year: Int,
        monthOfYear: Int,
        dayOfMonth: Int,
        yearEnd: Int,
        monthOfYearEnd: Int,
        dayOfMonthEnd: Int
    ) {
        //开始时间的时间戳
        val timeStart =
            DateUtils.getTimeStart("" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日")
        //结束时间的时间戳
        val timeEnd =
            DateUtils.getTimeEnd("" + yearEnd + "年" + (monthOfYearEnd + 1) + "月" + dayOfMonthEnd + "日")

        Log.d(
            "&&&&&&&&&&&",
            "【"+ (monthOfYear + 1) + "月" + dayOfMonth + "日" + "】——到——【"+ (monthOfYearEnd + 1) + "月" + dayOfMonthEnd + "日】"
        )
        var night :Int = dayOfMonthEnd-dayOfMonth
        var date_in:String = "" + (monthOfYear + 1) + "月" + dayOfMonth + "日"
        var date_out:String = "" + (monthOfYearEnd + 1) + "月" + dayOfMonthEnd + "日"
        var date_total:String = "——可入住"+night+"晚——"
        Log.d("&&&&&&&&&&&", "【开始时间戳：" + timeStart + "】——可入住"+night+"晚——【" + "结束时间戳：" + timeEnd + "】")

        //如果需要一些判断操作，可以try-catch 一下 或 if-else 判断
        try {
            if (timeStart?.toLong()!! > timeEnd?.toLong()!!) {
                Toast.makeText(this, "结束时间不能小于开始时间", Toast.LENGTH_SHORT).show()

                return
            }
        } catch (e: Exception) {
            Toast.makeText(this, "选择时间的有误", Toast.LENGTH_SHORT).show()
        }
        pNights = date_total
        detail_date_in.text = date_in
        detail_date_out.text = date_out
        detail_date_total.text = date_total
    }

}
