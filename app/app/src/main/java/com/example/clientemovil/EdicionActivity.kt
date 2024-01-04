package com.example.clientemovil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.clientemovil.databinding.ActivityEdicionBinding
import com.example.clientemovil.databinding.ActivityMainBinding
import com.example.clientemovil.modelo.POKO.Cliente
import com.example.clientemovil.modelo.POKO.Mensaje
import com.example.clientemovil.utiles.Constantes
import com.google.gson.Gson
import com.koushikdutta.ion.Ion

class EdicionActivity : AppCompatActivity() {
    lateinit var binding: ActivityEdicionBinding
    lateinit var cliente:Cliente
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEdicionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        jsonACliente()
        if(cliente != null){
            cargarInformacion()

        }

    }
    override fun onResume() {
        super.onResume()
        binding.editar.setOnClickListener {
            val cliente= this.sacardatos()
            enviarInformacionActualizacion(cliente)
        }


    }
    private fun jsonACliente(){
        val jsoncliente = intent.getStringExtra("cliente")
        val gson = Gson()
        cliente = gson.fromJson(jsoncliente, Cliente::class.java)
    }
    fun cargarInformacion(){
        binding.nombreText.setText(cliente.nombre)
        binding.apellidoPText.setText(cliente.apellidoP)
        binding.apellidoMText.setText(cliente.apellidoM)
        binding.emailText.setText(cliente.correo)
        binding.telefonoText.setText(cliente.telefono)
        binding.fechaText.setText(cliente.nacimiento)
        binding.calleText.setText(cliente.calle)
        binding.numeroText.setText(cliente.numero)
        binding.emailText.isEnabled=false
        binding.contrasenaText.setText(cliente.contrasena)
    }

    private fun enviarInformacionActualizacion(cliente: Cliente){
        val gson = Gson()
        val json = gson.toJson(cliente)
        Ion.with(this@EdicionActivity)
            .load("PUT", "${Constantes.URL_WS}cliente/actualizar")

            .setHeader("Content-Type","application/json")
            .setStringBody(json)
            .asString()
            .setCallback { e, result ->
                if(e == null && result != null){
                    verificarResultadoEdicon(result)
                }else{
                    Toast.makeText(this@EdicionActivity, "Error el petición para registrarse la información.",
                        Toast.LENGTH_LONG).show()
                }
            }

    }
    private fun verificarResultadoEdicon(resultado : String) {
        val gson = Gson()
        val msj: Mensaje = gson.fromJson(resultado, Mensaje::class.java)
        Toast.makeText(this@EdicionActivity, msj.contenido, Toast.LENGTH_LONG).show()
    }
    private fun sacardatos(): Cliente {
        val cliente = Cliente()
        cliente.nombre= binding.nombreText.text.toString()
        cliente.apellidoM=binding.apellidoMText.text.toString()
        cliente.apellidoP =binding.apellidoPText.text.toString()
        cliente.calle=binding.calleText.text.toString()
        cliente.numero=binding.numeroText.text.toString()
        cliente.correo=binding.emailText.text.toString()
        cliente.telefono= binding.telefonoText.text.toString()
        cliente.nacimiento=binding.fechaText.text.toString()
        cliente.contrasena=binding.contrasenaText.text.toString()
        cliente.idCliente= this.cliente.idCliente
        return cliente
    }
}