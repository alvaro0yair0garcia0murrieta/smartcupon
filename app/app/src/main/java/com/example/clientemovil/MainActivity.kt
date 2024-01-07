package com.example.clientemovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clientemovil.databinding.ActivityMainBinding
import com.example.clientemovil.modelo.POKO.Cliente
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cliente: Cliente
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        jsonACliente()
        title = "Bienvenido(a) ${cliente.nombre}"
        binding.editarB.setOnClickListener {
            irAConf(cliente)
        }
        binding.irCupones.setOnClickListener {
            irACange()
        }
        binding.ComercialB.setOnClickListener{
            irALista("comercial")
        }
        binding.cafeteriasB.setOnClickListener {
            irALista("cafeteria")
        }
        binding.farmaciasB.setOnClickListener {
            irALista("farmacia")
        }
    }

    private fun irAConf(cliente: Cliente) {
        val gson = Gson()
        val stringCliente = gson.toJson(cliente)
        val intent = Intent(this@MainActivity, configuracion::class.java)
        intent.putExtra("cliente", stringCliente)
        startActivity(intent)
    }

    private fun jsonACliente(){
        val json = intent.getStringExtra("cliente")
        val gson = Gson()
        cliente = gson.fromJson(json, Cliente::class.java)
    }
    private  fun irALista(categoria:String){
        val intent= Intent(this@MainActivity, ListaPromocionesActivity::class.java)
        intent.putExtra("categoria", categoria)
        startActivity(intent )
    }
    private fun irACange(){
        val intent= Intent(this@MainActivity, CanjeActivity::class.java)
        startActivity(intent )

    }
}