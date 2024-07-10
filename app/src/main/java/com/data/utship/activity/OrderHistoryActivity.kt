package com.data.utship.activity


import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.data.utship.R
import com.data.utship.adapter.BaseAdapter
import com.data.utship.databinding.ActivityOrderHistoryBinding
import com.data.utship.model.MenuModel
import com.data.utship.utills.extensions.rvItemAnimation
import com.data.utship.utills.extensions.setVerticalLayout
import java.util.ArrayList


class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_login)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        clickOnMyView()
        bindViews()
    }

    private fun bindViews() {

        binding.toolbarTitle.imgClose.setOnClickListener { finish() }
        binding.toolbarTitle.tvTitleProName.text = "My Order History"
        binding.toolbarTitle.imgCart.visibility= View.GONE
        binding.toolbarTitle.imgFav.visibility= View.GONE

        /*   getMenus().forEachIndexed { i, details ->
            if (i > 0) {
                mAdapter.addItem(details)
            }
        }*/
        binding.recylerList.rcFav.rvItemAnimation()
        mAdapter.addMoreItems(getMenus())
        binding.recylerList.rcFav.setVerticalLayout()
        binding.recylerList.rcFav.adapter = mAdapter
    }

    private val mAdapter =
        BaseAdapter<MenuModel>(R.layout.item_order_his_list, onBind = { view, model, position ->
            view.findViewById<TextView>(R.id.tvTitle).text = model.title
          //  view.findViewById<TextView>(R.id.tvAmount).text = model.subTitle
            view.findViewById<ImageView>(R.id.ivImage)
        })

    private fun clickOnMyView() {
       // appPreferences = PreferenceManager(this)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
    }
    private fun getMenus(): ArrayList<MenuModel> {
        var menuList = ArrayList<MenuModel>()
        menuList.add(MenuModel(0, "Setting", "",R.drawable.sweet_img))
        menuList.add(MenuModel(1, "Setting", "",R.drawable.sweet_img))
        menuList.add(MenuModel(2, "Setting", "",R.drawable.sweet_img))
        menuList.add(MenuModel(3, "Setting", "",R.drawable.sweet_img))

        return menuList
    }

}