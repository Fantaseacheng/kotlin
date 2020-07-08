package com.zengcheng.getyourshittogether.ui

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.borax12.materialdaterangepicker.date.DatePickerDialog
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.database.AppDatabase
import com.zengcheng.getyourshittogether.database.dao.HouseDao
import com.zengcheng.getyourshittogether.database.entity.HouseEntity
import com.zengcheng.getyourshittogether.utils.DateUtils

import kotlinx.android.synthetic.main.activity_add_house.*
import kotlinx.android.synthetic.main.content_add_house.*
import kotlinx.android.synthetic.main.layout_content.*
import java.util.*

class AddHouseActivity : AppCompatActivity() ,DatePickerDialog.OnDateSetListener{
    val TAKE_PHOTO: Int = 1
    val CHOOSE_PHOTO: Int = 2
    val db: AppDatabase? = AppDatabase.instance
    val houseDao: HouseDao = db!!.houseDao()
    private var pic: String by Preference(this, "pic", "")
    private var datein: String by Preference(this, "datein", "")
    private var dateout: String by Preference(this, "dateout", "")
    var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_house)
        setSupportActionBar(add_house_toolbar)
        val db: AppDatabase? = AppDatabase.instance
        val houseDao: HouseDao = db!!.houseDao()
        var newHouse: HouseEntity
        val intent = getIntent()
        val houseNo: Int = intent!!.getIntExtra("houseNo", 0)
        add_house_toolbar.setNavigationOnClickListener {
            val intent1 = Intent(this, MainActivity::class.java)
            startActivity(intent1)
            finish()
        }

        fab.setOnClickListener { view ->
            if (add_house_name.text.toString() == "") {
                add_house_name.setError("房源名称不能为空")

            }
            else if (add_house_des.text.toString() == "") {
                add_house_des.setError("房源描述不能为空")

            }
            else{
                newHouse = HouseEntity(
                    add_house_name.text.toString(), add_house_des.text.toString(),
                    pic, 1, add_house_price.text.toString().toInt(), datein,dateout,houseNo+2
                )
                houseDao.add(newHouse)
                //Toast.makeText(this, houseNo.toString(), Toast.LENGTH_SHORT).show()
                val intent1 = Intent(this, MainActivity::class.java)
                startActivity(intent1)
                finish()
            }

        }

        add_house_pic.setOnClickListener {
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

        add_house_timepick.setOnClickListener {
            showDatePickerDialog()
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
            pic = imagePath
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
            add_house_pic?.setImageBitmap(bitmap)
        } else {
            Toast.makeText(this, "Failed to get image!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TAKE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        val bitmap =
                            BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                        add_house_pic?.setImageBitmap(bitmap)
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
        var houseTime:String = "【" + (monthOfYear + 1) + "月" + dayOfMonth + "日" + "】—可入住"+night+"晚—【" + (monthOfYearEnd + 1) + "月" + dayOfMonthEnd + "日】"

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
        add_house_time.text = houseTime
        datein =  "" + (monthOfYear + 1) + "月" + dayOfMonth + "日" + ""
        dateout = "" + (monthOfYearEnd + 1) + "月" + dayOfMonthEnd +"日" + ""
    }
}