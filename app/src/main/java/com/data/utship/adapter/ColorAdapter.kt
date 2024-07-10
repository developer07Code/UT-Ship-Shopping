package com.data.utship.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.data.utship.R
import com.data.utship.model.ProductDetailBean
import com.data.utship.utills.RvClickListner
import com.google.gson.Gson


internal class ColorAdapter(
    private var itemsList: ArrayList<ProductDetailBean.DataDTO.ColorDTO>, context: Context
    ,var rvClickListner: RvClickListner
) :
    RecyclerView.Adapter<ColorAdapter.MyViewHolder>() {
    val context = context
    private var checkedPosition = 0
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var llColor: FrameLayout = view.findViewById(R.id.llColor)
        var tvColor: TextView = view.findViewById(R.id.tvColor)
        var ivColor: ImageView = view.findViewById(R.id.ivColor)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_colors_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
Log.d("zsczcx",Gson().toJson(item))

        holder.tvColor.background.setColorFilter(Color.parseColor(item.value), PorterDuff.Mode.SRC_IN);

      //  holder.tvColor.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.color_green));
      //  holder.tvColor.setTextColor(ContextCompat.getColor(context, R.color.white));

        if (checkedPosition == -1) {
         //   holder.tvColor.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.color_green));
            holder.ivColor.setVisibility(View.GONE)

        } else {
            if (checkedPosition == position) {

                holder.ivColor.setVisibility(View.GONE)
            } else {
              //  holder.tvColor.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.color_secondry));
              //  holder.tvColor.setTextColor(ContextCompat.getColor(context, R.color.black));
                holder.ivColor.setVisibility(View.GONE)
            }
        }
holder.tvColor.setOnClickListener {
    rvClickListner.clickPos(item.id)
   // if (-1==position)
 //   holder.tvColor.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.colorPrimary));
 //   holder.tvColor.setTextColor(ContextCompat.getColor(context, R.color.white));
    holder.ivColor.visibility=View.VISIBLE

    if (checkedPosition != position) {
        notifyItemChanged(checkedPosition);
        checkedPosition = position
    }
    /*else
        holder.ivColor.visibility=View.GONE
*/
}
    }

    override fun getItemCount(): Int {
        return itemsList.size

    }
}