package com.data.utship.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.data.utship.R

import com.data.utship.model.MenuModel
import com.data.utship.utills.RvClickListner
import com.data.utship.utills.utility.AppConstant


class CommonFieldAdapter(var context: Activity,var catType: String, var list: ArrayList<MenuModel>, var rvClickListner: RvClickListner) : RecyclerView.Adapter<CommonFieldAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { // infalte the item Layout
    //    val v = LayoutInflater.from(parent.context).inflate(R.layout.item_category_list, parent, false)
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_fav_list, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setIsRecyclable(false)

   /*     holder.tvAdd.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(20f))
        holder.tvQtyAdd.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(100f))
        holder.tvQtyMinus.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(100f))
        holder.tvQty.background = RoundView(Color.TRANSPARENT, RoundView.getRadius(20f), true, R.color.orange)
        holder.tvOff.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(20f))
        holder.tvAdd.visibility = View.VISIBLE*/

        holder.tvTitle.text= list[position].title
        if (catType.equals(AppConstant.Sweet)){
             holder.ivImage.setImageDrawable(context.resources.getDrawable(R.drawable.sweet_img))
        }else{
             holder.ivImage.setImageDrawable(context.resources.getDrawable(R.drawable.sweets_package))
        }
      //  holder.ivImage.setImageDrawable(context.resources.getDrawable(list[position].drawableId))

      /*  if ("Retailer"=="Retailer"){
      //      holder.itemView.visibility=View.GONE
        }*/

        holder.itemView.setOnClickListener {
            rvClickListner.clickPos(list[position].indexId)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val ivImage: ImageView = itemview.findViewById(R.id.ivImage)
       val tvTitle: TextView = itemview.findViewById(R.id.tvTitle)
    }

}