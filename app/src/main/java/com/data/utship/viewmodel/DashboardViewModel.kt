package com.data.utship.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.data.utship.R
import com.data.utship.model.MenuModel
import java.util.ArrayList

class DashboardViewModel : ViewModel() {
    private lateinit var menuList: MutableLiveData<MenuModel>
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun getCategoryListObserver(): MutableLiveData<MenuModel>? {
        return menuList
    }
    private fun getMenus(): ArrayList<MenuModel> {
        var menuList = ArrayList<MenuModel>()
        menuList.add(MenuModel(0, "Setting", "", R.drawable.sweet_img))
        menuList.add(MenuModel(1, "Setting", "", R.drawable.sweet_img))
        menuList.add(MenuModel(2, "Setting", "", R.drawable.sweet_img))
        menuList.add(MenuModel(3, "Setting", "", R.drawable.sweet_img))

        return menuList
    }
}