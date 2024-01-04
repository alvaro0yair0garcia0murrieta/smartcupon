package com.example.clientemovil

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clientemovil.databinding.ActivityDetallesBinding
import com.example.clientemovil.modelo.POKO.Promocion
import com.example.clientemovil.utiles.Constantes
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import android.util.Base64
import android.widget.Toast

class DetallesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetallesBinding
    private lateinit var promocion: Promocion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = "detalles de la promocion"
        jsonAPromocion()
        if (promocion!=null){
            cargarInfo()
            cargarImagen(promocion.idPromocion)
        }
        binding.bottonSucursales.setOnClickListener {
       irADetalles(promocion.idPromocion)

        }
    }

    private fun irADetalles(id: Int) {
        intent= Intent(this@DetallesActivity,ListaActivitiySucursales::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun jsonAPromocion(){
        val json = intent.getStringExtra("promocion")
        val gson = Gson()
        promocion = gson.fromJson(json, Promocion::class.java)
    }

    private fun cargarInfo() {
        binding.textViewCodigo.setText(promocion.codigo)
        binding.textViewDescripcion.setText(promocion.descripcion)
        binding.textViewNombre.setText(promocion.nombre)
        binding.textViewInicio.setText(promocion.fechaInicio)
        binding.textViewVigencia.setText(promocion.fechaFin)
        binding.textViewRestriccion.setText(promocion.restriccion)
        binding.textViewTipo.setText(promocion.tipo)
        binding.textViewCupones.setText(promocion.cuponesMax.toString())
    }

    private fun cargarImagen(idPromocion : Int){
        Ion.with(this@DetallesActivity)
            .load("GET",  "${Constantes.URL_WS}promocion/imagen/${idPromocion}")
            .asString()
            .setCallback { e, result ->
                if(e == null && result != null){
                    val gson = Gson()
                    val promocion = gson.fromJson(result, Promocion::class.java)
                    if(promocion.imagenBase64.isNotEmpty()){
                        cargar(promocion.imagenBase64)
                    }
                }else{
                    Toast.makeText(this@DetallesActivity, "Error al descargar foto de perfil",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun cargar(fotoBase64 :  String){
        val byteImg = Base64.decode(fotoBase64, Base64.DEFAULT)
        val bitMapImg = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.size)
        binding.imageView.setImageBitmap(bitMapImg)
    }

}