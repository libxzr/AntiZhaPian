package com.demo.antizha

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.demo.antizha.databinding.MineSettingInfoBinding

class SettingActivity : AppCompatActivity() {

    lateinit var settings: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settingBinding = MineSettingInfoBinding.inflate(layoutInflater)
        setContentView(settingBinding.root)
        settingBinding.setCommit.setOnClickListener {
            when {
                settingBinding.setName.text.toString() == "" -> {
                    Toast.makeText(this@SettingActivity, "名字不能为空。", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                settingBinding.setId.text.toString() == "" -> {
                    Toast.makeText(this@SettingActivity, "证件号不能为空。", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                !settingBinding.setId.text.toString().matches("^[0-6]*X?".toRegex()) -> {
                    Toast.makeText(this@SettingActivity, "证件号格式错误。", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                settingBinding.setRegion.text.toString() == "" -> {
                    Toast.makeText(this@SettingActivity, "地区不能为空。", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                settingBinding.setAddress.text.toString() == "" -> {
                    Toast.makeText(this@SettingActivity, "详细地址不能为空。", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                settingBinding.setWork.text.toString() == "" -> {
                    Toast.makeText(this@SettingActivity, "行业不能为空。", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else -> {
                    settings = getSharedPreferences("setting", 0)

                    editor = settings.edit()
                    editor.putString("name", "*${settingBinding.setName.text} ")

                    editor.putString(
                        "id",
                        settingBinding.setId.text.toString()[0]
                                + "****************"
                                + settingBinding.setId.text.toString()[1]
                    )

                    editor.putString("region", settingBinding.setRegion.text.toString())
                    editor.putString("address", settingBinding.setAddress.text.toString())
                    editor.putString("work", settingBinding.setWork.text.toString())

                    editor.apply()
                    Toast.makeText(this@SettingActivity, "成功", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        settingBinding.setClear.setOnClickListener {
            settingBinding.setName.text = null
            settingBinding.setId.text = null
            settingBinding.setRegion.text = null
            settingBinding.setAddress.text = null
            settingBinding.setWork.text = null
        }

        settingBinding.setReset.setOnClickListener {
            settings = getSharedPreferences("setting", 0)
            editor = settings.edit()
            editor.remove("name")
            editor.remove("id")
            editor.remove("region")
            editor.remove("address")
            editor.remove("work")
            editor.commit()
            Toast.makeText(this@SettingActivity, "成功", Toast.LENGTH_SHORT).show()
            finish()
        }

        settingBinding.setLoadId.setOnClickListener {
            settingBinding.setId.setText("${(10..50).random()}")
        }

        settingBinding.setLoadWork.setOnClickListener {
            val seed = listOf<String>(
                "衣、林、牧、渔业", "金融、保险、投资", "房地产业", "教育、学生",
                "建筑业", "住宿和餐饮业", "采矿业", "旅游、购物、休闲", "工艺品、礼品",
                "党政机关、社会团体", "家具、生活用品、食品", "新闻、出版、科研", "机械设备、通用零部件",
                "广告、会展、商务办公、咨询", "公共管理、社会保障和社会组织", "卫生和社会工作", "制造业"
            )
                .random()
            settingBinding.setWork.setText(seed)
        }

    }

}