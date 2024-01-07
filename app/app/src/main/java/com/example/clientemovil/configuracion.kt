package com.example.clientemovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clientemovil.databinding.ActivityConfiguracionBinding
import com.example.clientemovil.modelo.POKO.Cliente
import com.google.gson.Gson

class configuracion : AppCompatActivity() {
    private lateinit var binding:ActivityConfiguracionBinding
     var cliente: String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)
        binding = ActivityConfiguracionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cerrar.setOnClickListener {
        cerrarSesion()
        }
        binding.irAedicion.setOnClickListener{

            irAEditar(cliente)
        }
    }
    private fun irAEditar(cliente: String?) {
        this.cliente = intent.getStringExtra("cliente")!!
        val intent = Intent(this@configuracion, EdicionActivity::class.java)
        intent.putExtra("cliente", cliente)
        startActivity(intent)
    }
    private fun cerrarSesion(){
        val intent = Intent(this@configuracion, LoginActivity::class.java)
        startActivity(intent)
        this.finish()

    }


}