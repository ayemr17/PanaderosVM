package com.example.panaderosvm._view_ui.panaderias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.panaderosvm.R
import com.example.panaderosvm._view_ui.pueblos.PueblosRecyclerAdapter
import com.example.panaderosvm.model.local.panaderias.PanaderiasEntity
import kotlinx.android.synthetic.main.item_recyclerview_panaderias.view.*

class PanaderiasRecyclerAdapter : RecyclerView.Adapter<PanaderiasRecyclerAdapter.ViewHolderPanaderia>() {

    lateinit var listPanaderias : List<PanaderiasEntity>
    lateinit var mClick : ClickPanaderia

    fun PanaderiasRecyclerAdapter(list: ArrayList<PanaderiasEntity>, click : ClickPanaderia) {
        listPanaderias = list
        mClick = click
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPanaderia {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_panaderias, parent, false)
        return ViewHolderPanaderia(view, mClick)
    }

    override fun getItemCount(): Int {
        return if (listPanaderias.isNullOrEmpty()) 0 else listPanaderias.size
    }

    override fun onBindViewHolder(holder: ViewHolderPanaderia, position: Int) {
        val currentItem = listPanaderias[position]
        holder.setIsRecyclable(false)
        holder.bind(currentItem)
    }

    fun getLocation(position: Int) : String {
        return listPanaderias[position].latlong
    }

    fun refreshItems() {
        notifyDataSetChanged()
    }

    class ViewHolderPanaderia(view: View, clickPanaderia: ClickPanaderia) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val imageView: ImageView = view.fotoPanaderia_imageView_itemPanaderia
        val nombrePanaderia: TextView = view.nombrePanaderia_textView_itemPanaderia
        val direccionPanaderia: TextView = view.direccionPanaderia_editText_itemPanaderia
        //val nombrePueblo: TextView = view.itemODS_imageView_borderODS
        var click = clickPanaderia

        fun bind(item : PanaderiasEntity) {
            nombrePanaderia.text = item.nombre
            direccionPanaderia.text = item.direccion

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            click.onClickPanaderia(adapterPosition)
        }
    }

    interface ClickPanaderia {
        fun onClickPanaderia(position: Int)
    }
}