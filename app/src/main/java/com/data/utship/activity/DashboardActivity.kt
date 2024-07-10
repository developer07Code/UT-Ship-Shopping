package com.data.utship.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.data.utship.R
import com.data.utship.adapter.CommonFieldDrawerAdapter
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityDashboardBinding
import com.data.utship.fragment.DashboardFragment
import com.data.utship.fragment.HomeFragment
import com.data.utship.fragment.OrderHisFragment
import com.data.utship.fragment.ProfileFragment
import com.data.utship.model.GetBalanceBean
import com.data.utship.model.MenuModel
import com.data.utship.utills.BroadcastReceiverExt
import com.data.utship.utills.Constants.AppBroadcasts.CART_COUNT_CHANGE
import com.data.utship.utills.RvClickListner
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.extensions.getCartCount
import com.data.utship.utills.extensions.sendCartCountChangeBroadcast
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {
    private var count: String = ""
    private lateinit var binding: ActivityDashboardBinding
    lateinit var rcNav: RecyclerView
    lateinit var appPreferences: SharedPrefUtils
    lateinit var navView:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appPreferences = SharedPrefUtils()
   //     Toast.makeText(this,"xvcxcvxcv",Toast.LENGTH_SHORT).show()

        //  val drawerLayout: DrawerLayout = binding.drawerLayout
        /*  binding.toolbarTitle.ivMenu.setOnClickListener {
              binding.drawerLayout.openDrawer(Gravity.LEFT)
          }*/
        Log.d("sffs",getCartCount())
        binding.apply {
            toolbarTitle.ivSearch.setOnClickListener {
                startActivity(Intent(this@DashboardActivity, SearchActivity::class.java))

            }
            toolbarTitle.ivWishlish.setOnClickListener {
                startActivity(Intent(this@DashboardActivity, WishlistActivity::class.java))
            }
            toolbarTitle.ivCart.setOnClickListener {
                if (appPreferences.getIntValue(AppConstant.UserID, 0) == 0) {

                    GeneralUtilities.launchActivity(
                        this@DashboardActivity,
                        LoginNewActivity::class.java
                    )

                }else{
                    startActivity(Intent(this@DashboardActivity, MyCartActivity::class.java))

                }
            }
            toolbarTitle.ivWishlish.setOnClickListener {
                if (appPreferences.getIntValue(AppConstant.UserID, 0) == 0) {

                    GeneralUtilities.launchActivity(
                        this@DashboardActivity,
                        LoginNewActivity::class.java
                    )

                }else{
                    startActivity(Intent(this@DashboardActivity, WishlistActivity::class.java))

                }
            }

            if (appPreferences.getIntValue(AppConstant.UserID,0) == 0){
                toolbarTitle.tvCount.visibility=View.GONE
            }else{
               toolbarTitle.tvCount.visibility=View.VISIBLE
                apiGetTotalCount()
                cartCount()
            }
        }

      //  val navViews: NavigationView = binding.navViews
        navView = binding.navView
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
      //  val headerView: View = binding.navViews.getHeaderView(0)
      //  rcNav = headerView.findViewById<RecyclerView>(R.id.rcNaDrawer)

        // View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main)
        //    ImageView iv = (ImageView)headerview.findViewById(R.id.your_image_view)
        //    val headerBinding: DrawerHeaderBinding = DrawerHeaderBinding.bind(headerView)

        replaceFragment(DashboardFragment(), "Dashboard")
    //    handleRecyclerDrawer()
        // navViews.setNavigationItemSelectedListener(this)

   //     val navController = findNavController(R.id.nav_host_fragment_activity_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_profile
            )
        )

        //  setupActionBarWithNavController(navController, appBarConfiguration)

  //      navView.setupWithNavController(navController)
        BroadcastReceiverExt(this) {
            onAction(CART_COUNT_CHANGE) {

            }
        /*    onAction(PROFILE_UPDATE) {
                setUserInfo()
            }*/
        }
    }

    fun changeTabIndex(id:Int){
        navView.setSelectedItemId(id)
    }

     fun cartCount() {

        sendCartCountChangeBroadcast()
    }

     fun cartCountData(cartCount: String) {
        if (cartCount.equals("0")
        )
            binding.toolbarTitle.tvCount.visibility = View.GONE

        else
            binding.toolbarTitle.tvCount.text =cartCount
    }


  /*  fun handleRecyclerDrawer() {
        rcNav.layoutManager = LinearLayoutManager(this)
        var mAdapter = CommonFieldDrawerAdapter(this, getMenus(), object :
            RvClickListner {
            override fun clickPos(pos: Int) {
                if (pos == 0) {
                    //    startActivity(Intent(this))
                    binding.drawerLayout.closeDrawers()
                } else if (pos == 1) {
                    startActivity(Intent(this@DashboardActivity, MyCartActivity::class.java))
                    //    startActivity(Intent(this@DashboardActivity, CheckoutActivity::class.java))
                    binding.drawerLayout.closeDrawers()
                } else if (pos == 2) {
                    startActivity(Intent(this@DashboardActivity, WishlistActivity::class.java))
                    binding.drawerLayout.closeDrawers()
                } else if (pos == 3) {
                    startActivity(Intent(this@DashboardActivity, OrderHistoryActivity::class.java))
                    binding.drawerLayout.closeDrawers()
                }
            }

            override fun clickDeletePos(pos: Int) {

            }
        })
        rcNav.adapter = mAdapter
        // rvMyAcFiled.isNestedScrollingEnabled = false

    }*/

    private fun getMenus(): ArrayList<MenuModel> {
        var menuList = ArrayList<MenuModel>()
        menuList.add(MenuModel(0, "Home", "", R.drawable.ic_home_black_24dp))
        menuList.add(MenuModel(1, "My Cart", "", R.drawable.ic_outline_shopping_cart_24))
        menuList.add(MenuModel(2, "My Wishlist", "", R.drawable.ic_baseline_favorite_border_24))
        //     menuList.add(MenuModel(3, "My Order", ""))
        menuList.add(MenuModel(4, "Offer & Discount", "", R.drawable.ic_baseline_local_offer_24))
        menuList.add(MenuModel(5, "My Account", "", R.drawable.ic_baseline_local_offer_24))
        menuList.add(MenuModel(6, "Term & Condition", "", R.drawable.icon_empty_cart))
        menuList.add(MenuModel(7, "Privacy Policy", "", R.drawable.icon_empty_cart))
        menuList.add(MenuModel(8, "Refund", "", R.drawable.icon_empty_cart))
        menuList.add(MenuModel(9, "Contact Us", "", R.drawable.icon_empty_cart))
        menuList.add(MenuModel(10, "About Us", "", R.drawable.icon_empty_cart))

        return menuList
    }

    override fun onBackPressed() {

     if (navView.getSelectedItemId()==R.id.navigation_dashboard){
         super.onBackPressed()
         finish()
     }else{
         navView.setSelectedItemId(R.id.navigation_dashboard)
     }
    }

    override fun onResume() {
        super.onResume()
        apiGetTotalCount()
     //   Toast.makeText(this,"x",Toast.LENGTH_SHORT).show()
    }

    fun apiGetTotalCount() {
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID, 0).toString(),
            )
        )
        val response = RetrofitService.getInstance().getGetBalance(builder.build())
        response.enqueue(object : Callback<GetBalanceBean> {
            override fun onResponse(call: Call<GetBalanceBean>, response: Response<GetBalanceBean>) {
                try {

                    if (response.body()?.status == 1) {
                        if (response.body()!!.data.totalCart.toString().equals("0")
                        )
                            binding.toolbarTitle.tvCount.visibility = View.GONE

                        else
                            binding.toolbarTitle.tvCount.text =response.body()!!.data.totalCart.toString()

                    }else{
                        Toast.makeText(this@DashboardActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){

                    Toast.makeText(this@DashboardActivity,e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<GetBalanceBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)

                Toast.makeText(this@DashboardActivity,t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private val mOnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                val frag = supportFragmentManager.findFragmentById(R.id.frame)
                when (item.itemId) {
                    R.id.navigation_dashboard -> {
                        if (frag !is DashboardFragment) {
                            replaceFragment(DashboardFragment(), "Dashboard")
                        }
                        return true
                    }
                    R.id.navigation_home -> {
                        if (frag !is HomeFragment) {
                            replaceFragment(HomeFragment(), "Home")
                        }
                        return true
                    }
                    R.id.navigation_notifications -> {
                        if (appPreferences.getIntValue(AppConstant.UserID,0) == 0)
                            startActivity(Intent(this@DashboardActivity, LoginNewActivity::class.java))
                        else{
                            if (frag !is OrderHisFragment) {
                                replaceFragment(OrderHisFragment(), "OrderHistory")
                            }
                        }
                        return true
                    }
                    R.id.navigation_profile -> {
                        if (appPreferences.getIntValue(AppConstant.UserID,0) == 0)
                            startActivity(Intent(this@DashboardActivity, LoginNewActivity::class.java))
                        else{
                            if (frag !is ProfileFragment) {
                                replaceFragment(ProfileFragment(), "Profile")
                            }
                        }

                        return true
                    }


                }
                return false
            }
        }

    fun replaceFragment(fragment: Fragment, tag: String) {
    //    tvTitle.text=tag
        Log.d("cvxzv",tag)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment, tag)
   //     fragmentTransaction.disallowAddToBackStack()
        fragmentTransaction.commit()
    }

}