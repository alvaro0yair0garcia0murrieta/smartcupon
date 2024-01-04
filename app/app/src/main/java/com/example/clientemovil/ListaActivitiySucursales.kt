package com.example.clientemovil


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientemovil.databinding.ActivityListaActivitiySucursalesBinding
import com.example.clientemovil.modelo.POKO.Sucursal
import com.example.clientemovil.modelo.POKO.SucursalAdapter
import com.example.clientemovil.utiles.Constantes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion

class ListaActivitiySucursales : AppCompatActivity() {

    private lateinit var binding: ActivityListaActivitiySucursalesBinding
    private var idPromocion : Int = 0
    private var listaSucursal: ArrayList<Sucursal> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaActivitiySucursalesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = "sucursales donde se encuentra la promocion"
        idPromocion = intent.getIntExtra("id", 0)
        consultarInformacion(idPromocion)
    }

    fun consultarInformacion(id: Int){
        Ion.with(this@ListaActivitiySucursales)
            .load("GET",
                "${Constantes.URL_WS}sucursal/promocion/${id}")
            .asString()
            .setCallback { e, result ->
                if(e == null && result != null){
                    serializarInformacion(result)
                    cargarInformacionRecycler()
                }else{
                    Toast.makeText(this@ListaActivitiySucursales,
                        "Por el momento no se puede obtener la información ",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    fun serializarInformacion(json : String){
        val gson = Gson()
        val typeLista = object: TypeToken<ArrayList<Sucursal>>() {}.type
        listaSucursal = gson.fromJson(json, typeLista)
    }

    fun cargarInformacionRecycler(){
        binding.recyclerSucursales.layoutManager = LinearLayoutManager(this)
        binding.recyclerSucursales.setHasFixedSize(true)
        if (listaSucursal.size >0){
            binding.tvSinSucursales.visibility = View.GONE
            binding.recyclerSucursales.adapter = SucursalAdapter(listaSucursal)
        }
    }

}