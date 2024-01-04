package com.example.clientemovil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.clientemovil.databinding.ActivityCanjeBinding
import com.example.clientemovil.utiles.Constantes
import com.koushikdutta.ion.Ion

class CanjeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCanjeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCanjeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            canjear(binding.editTextText.text.toString())
        }

    }

    private fun canjear(s: String) {
        Ion.with(this@CanjeActivity)
            .load("GET","${Constantes.URL_WS}promocion/canjear/${s}")
            .asString()
            .setCallback{e,result->
                if(e == null && result != null){
                    Toast.makeText(this@CanjeActivity, result,
                        Toast.LENGTH_LONG).show()
                    binding.editTextText.text.clear()
                    binding.textView.setText(result)
                }else{
                    Toast.makeText(this@CanjeActivity, "Error al canjear :$e",
                        Toast.LENGTH_LONG).show()}

            }
    }

}
