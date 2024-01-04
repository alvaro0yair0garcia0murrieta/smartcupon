package com.example.clientemovil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.clientemovil.databinding.ActivityMainBinding
import com.example.clientemovil.databinding.ActivityRegistroBinding
import com.example.clientemovil.modelo.POKO.Cliente
import com.example.clientemovil.modelo.POKO.Mensaje
import com.example.clientemovil.utiles.Constantes
import com.example.clientemovil.utiles.Utiles
import com.google.gson.Gson
import com.koushikdutta.ion.Ion

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private var error: Boolean= false
    private lateinit var mensaje:String
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                binding= ActivityRegistroBinding.inflate(layoutInflater)
                val view = binding.root
                setContentView(view)
                title="registrate ahora"
    }

    override fun onResume() {
        super.onResume()
        binding.registrar.setOnClickListener {
        val cliente= this.sacardatos()
            if(!error){
                enviarInformacionActualizacion(cliente)
            }else
            {
                Toast.makeText(this@RegistroActivity, mensaje,
                    Toast.LENGTH_LONG).show()
            }

        }

        
    }


    private fun enviarInformacionActualizacion(cliente: Cliente){
        val gson = Gson()
        val json = gson.toJson(cliente)
        Ion.with(this@RegistroActivity)
            .load("POST", "${Constantes.URL_WS}cliente/registrar")

            .setHeader("Content-Type","application/json")
            .setStringBody(json)
            .asString()
            .setCallback { e, result ->
                if(e == null && result != null){
                    verificarResultadoEdicon(result)
                }else{
                    Toast.makeText(this@RegistroActivity, "Error el petición para registrarse la información.",
                        Toast.LENGTH_LONG).show()
                }
            }

    }
    private fun verificarResultadoEdicon(resultado : String) {
        val gson = Gson()
        val msj: Mensaje = gson.fromJson(resultado, Mensaje::class.java)
        Toast.makeText(this@RegistroActivity, msj.contenido, Toast.LENGTH_LONG).show()
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
        var cumple =binding.fechaText.text.toString()
        if (!Utiles.fechaValidador(cumple)){
           mensaje = "fecha invalida =$cumple"
            error= true
        }
        cliente.nacimiento= cumple
        cliente.contrasena=binding.contrasenaText.text.toString()
        return cliente
    }
}