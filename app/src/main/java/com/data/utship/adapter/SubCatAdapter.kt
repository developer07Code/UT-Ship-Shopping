package com.example


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.data.utship.R
import com.data.utship.activity.ProductActivity
import com.data.utship.adapter.SubToSubCatAdapter
import com.data.utship.model.GetAllCategoryBean
import com.data.utship.utills.RvCategoryList
import com.data.utship.utills.RvClickListner



internal class SubCatAdapter(
    private var itemsList: List<GetAllCategoryBean.DataDTO>,val catID:Int?,
    val  context: Context, var rvClickListner: RvClickListner
) :
    RecyclerView.Adapter<SubCatAdapter.MyViewHolder>() {

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvTitlte: TextView = view.findViewById(R.id.tvTitle)
        var rvSubToTest: RecyclerView = view.findViewById(R.id.rvSubToSubCat)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subcategory, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.tvTitlte.text=item.name

        val layoutManager2 = GridLayoutManager(context,3)
        holder.rvSubToTest.layoutManager = layoutManager2
        holder.rvSubToTest.isNestedScrollingEnabled = true
        holder.rvSubToTest.setHasFixedSize(true)
        val  customAdapter = SubToSubCatAdapter(itemsList[position].subToSubCategories, context, object :
            RvCategoryList {
            override fun clickPos(CatID: Int,subCatID: Int,subTosubCatID: Int) {
                //  addCityApi(cityID)
                Log.d("zczxc",subTosubCatID.toString())
                Log.d("zczxc",CatID.toString())
                Log.d("zczxc", item.id.toString())

                context.startActivity(
                    Intent(context, ProductActivity::class.java)
                        .putExtra("subToSubCatID",subTosubCatID)
                        .putExtra("CID",CatID)
                        .putExtra("subCatID",subCatID)
                )
            }


        })
        holder.rvSubToTest.adapter = customAdapter

 holder.itemView.setOnClickListener {
   //  Log.d("zczxcxz",Gson().toJson(itemsList.get(position)))
     rvClickListner.clickPos(item.id)
   //  itemsList.removeAt(holder.getAdapterPosition())
   //  removeAt(position)
 }
    }

    override fun getItemCount(): Int {
        return itemsList.size

    }
  //  itemsList[position].subToSubCategories
}