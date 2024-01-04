package com.example.clientemovil.modelo.POKO

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.clientemovil.ListaPromocionesActivity
import com.example.clientemovil.R
import com.example.clientemovil.interfaz.Notificador

class PromocionAdapter(var listaPromocion: ArrayList<Promocion>, val observador : Notificador) : RecyclerView.Adapter<PromocionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_promocion, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder (itemView : View)  : RecyclerView.ViewHolder(itemView) {
    val textViewNombre: TextView = itemView.findViewById(R.id.nombre_textView)
    val textViewEmpresa: TextView = itemView.findViewById(R.id.empresa_texView)
    val textViewTipo: TextView = itemView.findViewById(R.id.tipo_textview)
    val textViewCupones: TextView = itemView.findViewById(R.id.cupones_textview)
    val textViewFecha: TextView = itemView.findViewById(R.id.vigenci_textview)
    val cardView: CardView = itemView.findViewById(R.id.card_item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val promocion = listaPromocion[position]
        holder.textViewNombre.text = "Promocion: ${promocion.nombre}"
        holder.textViewEmpresa.text = "Empresa: ${promocion.empresa}"
        holder.textViewTipo.text = "${promocion.tipo}"
        holder.textViewCupones.text ="numero de cupones Disponibles : ${promocion.cuponesMax} cupones"
        holder.textViewFecha.text = "fecha de vencimiento: ${promocion.fechaFin}"
        holder.cardView.setOnClickListener{
            observador.seleccionarItem(position,promocion)
        }
    }

    override fun getItemCount(): Int {
   return listaPromocion.size
    }

    fun updateList(newList: ArrayList<Promocion>) {
        listaPromocion = newList
        notifyDataSetChanged()
    }
}
