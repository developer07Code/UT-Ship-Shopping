package com.data.utship.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.data.utship.R
import com.data.utship.model.DashboardBean
import com.data.utship.utills.RvClickListner


class FindStyleAdapter(
    var context: Activity,
    var list: List<DashboardBean.DataDTO.FindStyleDTO>,
    var rvClickListner: RvClickListner
) : RecyclerView.Adapter<FindStyleAdapter.MyViewHolder>() {
    var selectedPosition = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_find_style, parent, false)
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

        holder.tvTitle.text = list[position].name

        //  holder.ivImage.setImageDrawable(context.resources.getDrawable(list[position].drawableId))

        /*  if ("Retailer"=="Retailer"){
        //      holder.itemView.visibility=View.GONE
          }*/
        //   holder.tvTitle.isChecked(position==selectedPosition)

  /*      holder.tvTitle.setChecked(position == selectedPosition)
        holder.tvTitle.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (b){
                selectedPosition == holder.adapterPosition
                holder.llSection.setBackgroundColor(Color.BLACK);
            }else
                holder.llSection.setBackgroundColor(Color.WHITE);

            notifyItemChanged(selectedPosition)
        })*/

      /*  if (selectedPosition == position) {
            holder.llSection.setBackgroundColor(Color.BLACK);
        } else {
            holder.llSection.setBackgroundColor(Color.WHITE);
        }*/

        holder.llSection.setOnClickListener {
            // setSingleSelection(position)
            rvClickListner.clickPos(list[position].id)
        }

    }

    private fun setSingleSelection(position: Int) {
        if (position == RecyclerView.NO_POSITION) return
        notifyItemChanged(selectedPosition)
        selectedPosition = position
        notifyItemChanged(selectedPosition)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvTitle: TextView = itemview.findViewById(R.id.tvTitle)
        val llSection: LinearLayout = itemview.findViewById(R.id.llSection)

/*        itemView.setOnClickListener {
*//*            lastSelectedPosition = selectedPosition;
            selectedPosition = holder.getBindingAdapterPosition();
            notifyItemChanged(lastSelectedPosition);
            notifyItemChanged(selectedPosition);*//*

            //    rvClickListner.clickPos(list[position].indexId)
            setSingleSelection(adapterPosition)
        */
    }

}

