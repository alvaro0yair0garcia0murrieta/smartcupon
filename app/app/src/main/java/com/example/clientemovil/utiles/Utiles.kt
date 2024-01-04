package com.example.clientemovil.utiles
import java.text.SimpleDateFormat
class Utiles {

    companion object {
        fun fechaValidador(cumple: String): Boolean {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            dateFormat.isLenient = false
            return try {
                dateFormat.parse(cumple)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}