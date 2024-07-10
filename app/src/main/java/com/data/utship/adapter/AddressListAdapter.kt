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
import com.data.utship.R
import com.data.utship.activity.AddAddressActivity
import com.data.utship.model.AddressListBean
import com.data.utship.utills.RvClickListner
import com.google.gson.Gson


internal class AddressListAdapter(
    private var itemsList: MutableList<AddressListBean.DataDTO>, context: Context
    , var rvClickListner: RvClickListner
) :
    RecyclerView.Adapter<AddressListAdapter.MyViewHolder>() {
    val context = context
    private var checkedPosition = 0
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var cvAddress: CardView = view.findViewById(R.id.cvAddress)
        var tvUserName: TextView = view.findViewById(R.id.tvUserName)
        var tvAddress: TextView = view.findViewById(R.id.tvAddress)
        var tvUserMobNo: TextView = view.findViewById(R.id.tvUserMobNo)
        var ivChecked: ImageView = view.findViewById(R.id.ivChecked)
        var ivEdit: TextView = view.findViewById(R.id.ivEdit)
        var ivDelete: TextView = view.findViewById(R.id.ivDelete)
        var llDeleteSection: LinearLayout = view.findViewById(R.id.llDeleteSection)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_address, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        Log.d("zsczcx",Gson().toJson(item))
        holder.tvUserName.text=item.name
        holder.tvAddress.text=item.address
        holder.tvUserMobNo.text=item.mobile

      //  holder.tvColor.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.color_green));
      //  holder.tvColor.setTextColor(ContextCompat.getColor(context, R.color.white));

        holder.ivEdit.setOnClickListener {
        //    rvClickListner.clickDeletePos(item.id)
            context.startActivity(Intent(context, AddAddressActivity::class.java).putExtra("way","Edit")
                .putExtra("addressId",item.id))

        }
        holder.ivDelete.setOnClickListener {
            rvClickListner.clickDeletePos(item.id)

        }
        if (checkedPosition == -1) {
         //   holder.tvColor.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.color_green));
        //    holder.ivChecked.setVisibility(View.GONE)
            holder.ivChecked.setImageDrawable(context.getDrawable(R.drawable.ic_unchecked))
            holder.llDeleteSection.visibility=View.GONE


        } else {
            if (checkedPosition == position) {
                holder.ivChecked.setImageDrawable(context.getDrawable(R.drawable.ic_unchecked))
                holder.llDeleteSection.visibility=View.GONE


                //  holder.ivChecked.setVisibility(View.GONE)
            } else {
              //  holder.tvColor.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.color_secondry));
              //  holder.tvColor.setTextColor(ContextCompat.getColor(context, R.color.black));
             //   holder.ivChecked.setVisibility(View.GONE)
                holder.ivChecked.setImageDrawable(context.getDrawable(R.drawable.ic_unchecked))
                holder.llDeleteSection.visibility=View.GONE

            }
        }
holder.cvAddress.setOnClickListener {
    rvClickListner.clickPos(item.id)
   // if (-1==position)
 //   holder.tvColor.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.colorPrimary));
 //   holder.tvColor.setTextColor(ContextCompat.getColor(context, R.color.white));
    holder.ivChecked.setImageDrawable(context.getDrawable(R.drawable.ic_checked))
    holder.llDeleteSection.visibility=View.VISIBLE

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