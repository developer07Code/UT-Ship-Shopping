package com.data.utship.activity


import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.data.utship.R
import com.data.utship.adapter.BaseAdapter
import com.data.utship.apihelper.RetrofitService
import com.data.utship.databinding.ActivityCartBinding
import com.data.utship.model.BaseResponseBean
import com.data.utship.model.CartBean
import com.data.utship.utills.SharedPrefUtils
import com.data.utship.utills.extensions.rvItemAnimation
import com.data.utship.utills.extensions.setVerticalLayout
import com.data.utship.utills.utility.AppConstant
import com.data.utship.utills.utility.GeneralUtilities
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyCartActivity : AppCompatActivity() {
    private var totalAmount: Double = 0.00
    private var mrpTotalAmount: Double = 0.00
    private lateinit var binding: ActivityCartBinding
    lateinit var appPreferences: SharedPrefUtils
    lateinit var listCart: kotlin.collections.ArrayList<CartBean.DataDTO.CartsDTO>
    lateinit var CartTotal: CartBean.DataDTO.CartTotalDTO
    lateinit var progressDialog: ProgressDialog
    var count = 0
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_login)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        context = this@MyCartActivity
        bindViews()
        apiMyCart(0)

    }

    private val mAdapter =
        BaseAdapter<CartBean.DataDTO.CartsDTO>(R.layout.item_cart_list, onBind = { view, model, position ->
            view.findViewById<TextView>(R.id.tvTitle).text = model.product
                //    view.findViewById<TextView>(R.id.tvSubTitle).text = model.product
            view.findViewById<TextView>(R.id.tvPriceApp).text = AppConstant.CurrencySymbole+ model.price
            view.findViewById<TextView>(R.id.tvActualPrice).text =AppConstant.CurrencySymbole+ model.mrp
          //  view.findViewById<TextView>(R.id.tvOff).text =AppConstant.CurrencySymbole+ model.dis
          //  view.findViewById<TextView>(R.id.qty).text = "Qty : "+model.qty
         //   view.findViewById<TextView>(R.id.tvColor).text =model.color
            view.findViewById<TextView>(R.id.tvSize).text =model.size
            view.findViewById<TextView>(R.id.tvColor).background.setColorFilter(Color.parseColor(model.color), PorterDuff.Mode.SRC_IN);

            // view.findViewById<TextView>(R.id.tvSize).text = model.size
            Glide.with(this).load(model.img).into(view.findViewById<ImageView>(R.id.ivImage))
            view.findViewById<TextView>(R.id.tvActualPrice).paintFlags =
                view.findViewById<TextView>(R.id.tvActualPrice).paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            view.findViewById<TextView>(R.id.tvValue).text = model.qty
            view.findViewById<ImageView>(R.id.ivMinus).setOnClickListener {
                apiAddToCart("-1", model.pid, model.sizeID, model.colorID)
                view.findViewById<TextView>(R.id.tvValue).text = model.qty
            /*  count--
                apiAddToCart("-1", model.pid, model.sizeID, model.colorID)
              view.findViewById<TextView>(R.id.tvValue).setText(model.qty)
                if (count < 1) {
                    view.findViewById<ImageView>(R.id.ivMinus).setEnabled(false)

                }*/

   /*             if (!model.qty.toString().isNullOrEmpty()){
                    val qty:Int=model.qty.toString().toInt()

                    if (qty==0) {return@setOnClickListener}
                    Toast.makeText(this,"scdfs",Toast.LENGTH_SHORT).show()

                    if (qty>1) {
                        Toast.makeText(this,"yes",Toast.LENGTH_SHORT).show()
                     }else{
                        if (model.size==1){
                            //  PrefManager(context).qtyClear(context)
                            Toast.makeText(this,"No",Toast.LENGTH_SHORT).show()
                        }
                    }
                }*/
            }

            view.findViewById<ImageView>(R.id.ivAdd).setOnClickListener {
                apiAddToCart("1", model.pid,model.sizeID,model.colorID)
                view.findViewById<TextView>(R.id.tvValue).text = model.qty
              /*  count++
                view.findViewById<TextView>(R.id.tvValue).text = model.qty
                if (count > 0) {
                    view.findViewById<ImageView>(R.id.ivMinus).isEnabled = true
                    apiAddToCart("1", model.pid,model.sizeID,model.colorID)
                }
*/


             /*   if (!model.qty.toString().isNullOrEmpty()){
                    val qty:Int=model.qty.toString().toInt()

                    if (qty<10) {
                        Toast.makeText(this,"11110000",Toast.LENGTH_SHORT).show()
                        //  PrefManager(context).putQty(list[position].productID.toString(),list[position].quantity+1)

                    }else{
                        Toast.makeText(this,"You have reached the max limit", Toast.LENGTH_SHORT).show()
                    }
                }*/

            }

            view.findViewById<ImageView>(R.id.ivDelete).setOnClickListener {
              //  Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
                apiMyCart(model.id)
            }
            view.findViewById<ImageView>(R.id.ivImage).setOnClickListener {

            }
        })

    private fun bindViews() {
        appPreferences = SharedPrefUtils()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.apply {
            toolbarTitle.imgClose.setOnClickListener { finish() }
            toolbarTitle.tvTitleProName.text = "My Cart"
            toolbarTitle.imgCart.visibility=View.GONE

            toolbarTitle.imgFav.setOnClickListener {
                startActivity(Intent(this@MyCartActivity, WishlistActivity::class.java))

                finish() }
            tvViewDetail.setOnClickListener {
                if (CartTotal!=null)
                    viewDetailDialog()
            }
            btnCheckout.setOnClickListener {
                startActivity(Intent(this@MyCartActivity, CheckoutActivity::class.java))
              /*  startActivity(Intent(this@MyCartActivity, CheckoutActivity::class.java)
                    .putExtra("cartPayment",CartTotal))*/
            }
            tvShopNow.setOnClickListener {
                startActivity(Intent(this@MyCartActivity, DashboardActivity::class.java))
              /*  startActivity(Intent(this@MyCartActivity, CheckoutActivity::class.java)
                    .putExtra("cartPayment",CartTotal))*/
            }
        }

        /*   getMenus().forEachIndexed { i, details ->
               if (i > 0) {
                   mAdapter.addItem(details)
               }
           }*/

    }

    /*     fun setCalculation(){
           totalAmount=0.00
           mrpTotalAmount=0.00
           for (i in 0 until result.size){
               calculateTotal(result[i].sellingPrice.toString(),result[i].listPrice.toString(),result[i].quantity.toString())
           }
       }
       fun calculateTotal(stringAmount:String,stringMrpAmount:String,qty:String){
           totalAmount+=stringAmount.toDouble()*qty.toDouble();
           mrpTotalAmount+=stringMrpAmount.toDouble()*qty.toDouble();
           tvPriceCart.text=AppConstant.CurrencySymbole+changeDecPoint(totalAmount.toString())
       }*/

    fun apiMyCart(id: Int) {
        GeneralUtilities.showDialog(this)

        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID","ID"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID,0).toString(),id.toString()))
             //   "1",id.toString()))
        val response = RetrofitService.getInstance().getCartApi(builder.build())
        response.enqueue(object : Callback<CartBean> {
            override fun onResponse(call: Call<CartBean>, response: Response<CartBean>) {
                try {
                    GeneralUtilities.hideDialog()
                    if (response.body()?.status == 1) {
                        binding.llShopNowSection.visibility=View.GONE
                        binding.llPaymentSection.visibility=View.VISIBLE
                        binding.recylerList.visibility=View.VISIBLE
                        CartTotal= response.body()!!.data.cartTotal
                        mAdapter.clearItems()
                        binding.recylerList.rvItemAnimation()
                        mAdapter.addItems(response.body()!!.data.carts)
                        binding.recylerList.setVerticalLayout()
                        binding.recylerList.adapter = mAdapter
                        binding.tvPriceCart.text=AppConstant.CurrencySymbole+response.body()!!.data.cartTotal.totalAmount
                        mAdapter.notifyDataSetChanged()

                    }else{
                        binding.recylerList.visibility=View.GONE
                        binding.llShopNowSection.visibility=View.VISIBLE
                        binding.llPaymentSection.visibility=View.GONE
                        Toast.makeText(this@MyCartActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    GeneralUtilities.hideDialog()
                    Toast.makeText(this@MyCartActivity,e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<CartBean>, t: Throwable) {
                GeneralUtilities.hideDialog()
                Toast.makeText(this@MyCartActivity,t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun apiAddToCart(qty: String, pid: Int, sizeID: Int, colorID: Int) {
        GeneralUtilities.showDialog(this)
        val builder: FormBody.Builder = RetrofitService.createBuilder(
            arrayOf("UID", "PID", "SizeID", "ColorID", "Qty"),
            arrayOf(
                appPreferences.getIntValue(AppConstant.UserID,0).toString(), pid.toString(), sizeID.toString(), colorID.toString(), qty
            )
        )
        val response = RetrofitService.getInstance().getAddToCartApi(builder.build())
        response.enqueue(object : Callback<BaseResponseBean> {
            override fun onResponse(
                call: Call<BaseResponseBean>,
                response: Response<BaseResponseBean>
            ) {
                try {
                    GeneralUtilities.hideDialog()

                    if (response.body()?.status == 1) {
                        //handleRecyler(response.body()!!.data)
                        apiMyCart(0)
                        Toast.makeText(
                            this@MyCartActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {

                        Toast.makeText(
                            this@MyCartActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    GeneralUtilities.hideDialog()

                    Toast.makeText(
                        this@MyCartActivity,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //      DialogsProvider.get(this@LoginActivity).setLoading(false)
            }

            override fun onFailure(call: Call<BaseResponseBean>, t: Throwable) {
                // DialogsProvider.get(this@OtpActivity).setLoading(false)
                GeneralUtilities.hideDialog()

                Toast.makeText(this@MyCartActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun viewDetailDialog() {
        val dialog: Dialog = GeneralUtilities.openBootmSheetDailog(
            R.layout.dailog_viewdetail,
            R.style.AppBottomSheetDialogTheme,
            this
        )
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val tvDeliveryValue = dialog.findViewById<TextView>(R.id.tvDeliveryValue)
        val tvBagTotalValue = dialog.findViewById<TextView>(R.id.tvBagTotalValue)
        val tvTotalAmtValue = dialog.findViewById<TextView>(R.id.tvTotalAmtValue)
        val tvDiscountValue = dialog.findViewById<TextView>(R.id.tvDiscountValue)

        tvBagTotalValue.setText(AppConstant.CurrencySymbole+CartTotal.totalAmount)
        tvDiscountValue.setText(AppConstant.CurrencySymbole+CartTotal.useWallet)
        tvDeliveryValue.setText(AppConstant.CurrencySymbole+CartTotal.delivery)
        tvTotalAmtValue.setText(AppConstant.CurrencySymbole+CartTotal.netAmount)
        ivClose.setOnClickListener { dialog.dismiss() }

    }

}