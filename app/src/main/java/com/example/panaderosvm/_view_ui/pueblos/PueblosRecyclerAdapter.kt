package com.example.panaderosvm._view_ui.pueblos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.panaderosvm.R
import com.example.panaderosvm.model.local.pueblos.PueblosEntity
import kotlinx.android.synthetic.main.item_recyclerview_pueblos.view.*

class PueblosRecyclerAdapter : RecyclerView.Adapter<PueblosRecyclerAdapter.ViewHolderPueblo>() {

    lateinit var listPueblos : List<PueblosEntity>
    lateinit var mClick : ClickPueblo

    fun PueblosRecyclerAdapter(list: ArrayList<PueblosEntity>, click : ClickPueblo) {
        listPueblos = list
        mClick = click
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPueblo {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_pueblos, parent, false)
        return ViewHolderPueblo(view, mClick)
    }

    override fun getItemCount(): Int {
        return if (listPueblos.isNullOrEmpty()) 0 else listPueblos.size
    }

    override fun onBindViewHolder(holder: ViewHolderPueblo, position: Int) {
        val currentItem = listPueblos[position]
        holder.setIsRecyclable(false)
        holder.bind(currentItem)
    }

    fun getLocation(position: Int) : String {
        return listPueblos[position].latlong
    }

    fun refreshItems() {
        notifyDataSetChanged()
    }

    class ViewHolderPueblo(view: View, clickPueblo: ClickPueblo) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val imageView: ImageView = view.fotoPueblo_imageView_itemPueblo
        val nombrePueblo: TextView = view.nombrePueblo_textView_itemPueblo
        val dptoPueblo: TextView = view.dptoPueblo_editText_itemPueblo
        //val nombrePueblo: TextView = view.itemODS_imageView_borderODS
        var click = clickPueblo

        fun bind(item : PueblosEntity) {

            nombrePueblo.text = item.nombre
            dptoPueblo.text = item.departamento


            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            click.onClickPueblos(adapterPosition)
        }
    }

    interface ClickPueblo {
        fun onClickPueblos(position: Int)
    }

}