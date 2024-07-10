package com.data.utship.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.utship.R
import com.data.utship.activity.AddAddressActivity
import com.data.utship.model.AddressListBean
import com.data.utship.model.GetAllCategoryBean
import com.data.utship.utills.RvClickListner
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView


internal class AllCategoryAdapter(
    context: Context, private var itemsList: MutableList<GetAllCategoryBean.DataDTO>,
     var rvClickListner: RvClickListner
) :
    RecyclerView.Adapter<AllCategoryAdapter.MyViewHolder>() {
    val context = context
    private var checkedPosition = 0
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvTitle: TextView = view.findViewById(R.id.tvTitle)
        var ivImage: ImageView = view.findViewById(R.id.ivImage)
        var tvSubTitle: TextView = view.findViewById(R.id.tvSubTitle)


    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subcategory, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        Log.d("zsczcx",Gson().toJson(item))
        holder.ivImage.visibility=View.GONE
        holder.tvSubTitle.visibility=View.GONE
        holder.tvTitle.text=item.name
       /* holder.tvSubTitle.text=item.subToSubCategories[position].name
        Glide.with(context).load(item.subToSubCategories.get(position).img)
            .into(holder.ivImage)*/

      //  holder.tvColor.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.color_green));
      //  holder.tvColor.setTextColor(ContextCompat.getColor(context, R.color.white));

     /*   holder.ivEdit.setOnClickListener {
        //    rvClickListner.clickDeletePos(item.id)
            context.startActivity(Intent(context, AddAddressActivity::class.java).putExtra("way","Edit")
                .putExtra("addressId",item.id))

        }
        holder.ivDelete.setOnClickListener {
            rvClickListner.clickDeletePos(item.id)

        }*/

holder.itemView.setOnClickListener {
    rvClickListner.clickPos(item.id)
   // if (-1==position)
 //   holder.tvColor.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.colorPrimary));
 //   holder.tvColor.setTextColor(ContextCompat.getColor(context, R.color.white));

}
    }

    override fun getItemCount(): Int {
        return itemsList.size

    }
}