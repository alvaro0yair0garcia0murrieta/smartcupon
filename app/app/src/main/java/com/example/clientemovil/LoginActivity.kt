package com.example.clientemovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import com.example.clientemovil.databinding.ActivityLoginBinding
import com.example.clientemovil.modelo.POKO.Cliente
import com.example.clientemovil.modelo.POKO.RespuestaLogin
import com.example.clientemovil.utiles.Constantes

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = "Inicio de sesión"
        binding.ingresarB.setOnClickListener {
            if (validarCamposLogin()) {
                verificarCredenciales(
                    binding.correoEditText.text.toString(),
                    binding.passwordText.text.toString()
                )
            }
        }
        binding.registroText.setOnClickListener {
            irARegistro()
        }
    }

    fun validarCamposLogin(): Boolean{
        var esCorrecto = true
        val correo = binding.correoEditText.text.toString()
        val password  = binding.passwordText.text.toString()
        if(correo.isEmpty()){
            esCorrecto = false
            binding.correoEditText.error = "Correo electrónico obligatorio"
        }
        if(password.isEmpty()){
            esCorrecto = false
            binding.passwordText.error = "Contraseña obligatoria"
        }
        return esCorrecto
    }

    fun verificarCredenciales(correo: String, password: String){
        Ion.getDefault(this@LoginActivity).conscryptMiddleware.enable(false)
        Ion.with(this@LoginActivity)
            .load("POST", Constantes.URL_WS+"cliente/login")
            .setHeader("Content-Type","application/x-www-form-urlencoded")
            .setBodyParameter("correo", correo)
            .setBodyParameter("contrasena", password)
            .asString()
            .setCallback { e, result ->
                if(e == null && result != null){
                    serializarRespuestaLogin(result)
                }else{
                    Toast.makeText(this@LoginActivity,
                        "Error de solicitud, por favor inténtelo más tarde",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    fun serializarRespuestaLogin(json : String){
        val gson = Gson()
        val respuestaLogin = gson.fromJson(json, RespuestaLogin::class.java)
        Toast.makeText(this@LoginActivity, respuestaLogin.contenido,
            Toast.LENGTH_LONG).show()
        if(!respuestaLogin.error){
            irPantallaPrincipal(respuestaLogin.cliente)
        }
    }

    fun irPantallaPrincipal(cliente: Cliente){
        val gson = Gson()
        val stringCliente = gson.toJson(cliente)
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.putExtra("cliente", stringCliente)
        startActivity(intent)
        this.finish()
    }
    fun irARegistro(){
        val intent = Intent(this@LoginActivity, RegistroActivity::class.java)
        startActivity(intent)
    }
}