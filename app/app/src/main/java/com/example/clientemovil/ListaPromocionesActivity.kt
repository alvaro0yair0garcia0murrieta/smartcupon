package com.example.clientemovil

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientemovil.databinding.ActivityListaPromocionesBinding
import com.example.clientemovil.interfaz.Notificador
import com.example.clientemovil.modelo.POKO.Promocion
import com.example.clientemovil.modelo.POKO.PromocionAdapter
import com.example.clientemovil.utiles.Constantes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion

class ListaPromocionesActivity : AppCompatActivity(),Notificador {
    private lateinit var binding: ActivityListaPromocionesBinding
    private var listaPromocion: ArrayList<Promocion> = ArrayList()
    private lateinit var adapter: PromocionAdapter

    private fun filterList(string: String): ArrayList<Promocion> {
        val filteredList = listaPromocion.filter { promocion ->
            promocion.nombre.contains(string,ignoreCase = true)||
                    promocion.empresa.contains(string,ignoreCase = true)
            }
        return filteredList as ArrayList<Promocion>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaPromocionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = "Promociones Disponibles"
       val categoria = intent.getStringExtra("categoria")
        consultarInformacion(categoria)
        binding.busquedaEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
adapter.updateList(filterList(s.toString()))
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    fun consultarInformacion(categoria: String?){
        Ion.with(this@ListaPromocionesActivity)
            .load("GET",
                "${Constantes.URL_WS}promocion/promociones/categoria/${categoria}")
            .asString()
            .setCallback { e, result ->
                if(e == null && result != null){
                    serializarInformacion(result)
                    cargarInformacionRecycler()
                }else{
                    Toast.makeText(this@ListaPromocionesActivity,
                        "Por el momento no se puede obtener la información",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun serializarInformacion(json : String){
        val gson = Gson()
        val typeLista = object: TypeToken<ArrayList<Promocion>>() {}.type
        listaPromocion = gson.fromJson(json, typeLista)
    }

    fun cargarInformacionRecycler(){
        binding.recyclerPromociones.layoutManager = LinearLayoutManager(this)
        binding.recyclerPromociones.setHasFixedSize(true)
        if (listaPromocion.size >0){
            binding.tvSinPromociones.visibility = View.GONE
            adapter =  PromocionAdapter(listaPromocion,this)
           binding.recyclerPromociones.adapter = adapter
        }
    }

  override fun seleccionarItem(posicion: Int, promocion: Promocion) {
        Toast.makeText(this@ListaPromocionesActivity, posicion.toString(), Toast.LENGTH_SHORT).show()
        mostrarInformacion(promocion)
    }

    private fun mostrarInformacion(promocion: Promocion){
        val alert = AlertDialog.Builder(this@ListaPromocionesActivity)
        alert.setTitle("Promocion seleccionado")
        alert.setMessage("${promocion.nombre} de la  empresa ${promocion.empresa}")
        alert.setPositiveButton("Ver detalle", DialogInterface.OnClickListener { dialogInterface, i ->
            Toast.makeText(this@ListaPromocionesActivity, "Ir a otra pantalla...", Toast.LENGTH_LONG).show()
            irADetalles(promocion)

        })
        alert.setNegativeButton("Cerrar", null)
        val dialog = alert.create()
        dialog.show()
    }

    private fun irADetalles(promocion: Promocion) {
        val gson = Gson()
        val string= gson.toJson(promocion)
        val intent = Intent(this@ListaPromocionesActivity, DetallesActivity::class.java)
        intent.putExtra("promocion", string)
        startActivity(intent)
    }

}
