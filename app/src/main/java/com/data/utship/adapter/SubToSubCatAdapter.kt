package com.data.utship.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.utship.R
import com.data.utship.activity.ProductActivity
import com.data.utship.model.GetAllCategoryBean
import com.data.utship.utills.RvCategoryList
import com.data.utship.utills.RvClickListner


internal class SubToSubCatAdapter(
    private var itemsList: List<GetAllCategoryBean.DataDTO.SubToSubCategoriesDTO>,
    val  context: Context, var rvClickListner: RvCategoryList
) :
    RecyclerView.Adapter<SubToSubCatAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTitlte: TextView = view.findViewById(R.id.tvTitle)
        var ivImage: ImageView = view.findViewById(R.id.ivImage)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subcat_list, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.tvTitlte.text=item.name
        Glide.with(context).load(item.img).into(holder.ivImage)

        holder.itemView.setOnClickListener {
           //  Log.d("zczxcxz",Gson().toJson(itemsList.get(position)))
             rvClickListner.clickPos(item.cid,item.scid,item.id)
           //  itemsList.removeAt(holder.getAdapterPosition())
           //  removeAt(position)



         }
    }

    override fun getItemCount(): Int {
        return itemsList.size

    }

}