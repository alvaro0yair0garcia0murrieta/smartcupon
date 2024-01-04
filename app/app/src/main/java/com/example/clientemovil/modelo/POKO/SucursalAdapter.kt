package com.example.clientemovil.modelo.POKO

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import com.example.clientemovil.R

class SucursalAdapter( val listaSucursal: ArrayList<Sucursal>) : RecyclerView.Adapter<SucursalAdapter.ViewHolder>() {
    class ViewHolder(itemView : View)  : RecyclerView.ViewHolder(itemView) {
    val textViewNombre: TextView = itemView.findViewById(R.id.nombreSucursal_textView)
    val textViewDireccion: TextView = itemView.findViewById(R.id.direccion_texView)
    val textViewTelefono:TextView = itemView.findViewById(R.id.telefono_textview)
    val textViewCodigoP:TextView =itemView.findViewById(R.id.codigoP_textview)
        val cardView: CardView = itemView.findViewById(R.id.card_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_sucursal, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listaSucursal.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sucursal = listaSucursal[position]
        holder.textViewNombre.text= "sucursal: ${sucursal.nombre}"
        holder.textViewDireccion.text = "direccion: ${sucursal.calle}  ${sucursal.numero}"
        holder.textViewTelefono.text ="telefono : ${sucursal.telefono}"
        holder.textViewCodigoP.text ="codigo postal: ${sucursal.codigoP}"
    }

}
