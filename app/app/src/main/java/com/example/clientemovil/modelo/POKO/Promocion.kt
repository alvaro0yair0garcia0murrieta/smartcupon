package com.example.clientemovil.modelo.POKO

import java.io.Serializable

data class Promocion (

    val restriccion: String="0",
    val fechaFin: String="",
    val fechaInicio:String="",
    val descripcion:String ="",
    val codigo: String="",
    val imagenBase64:String= "",
    val empresa: String= "",
    val nombre: String= "",
    val tipo: String="",
    val cuponesMax : Int =0,
    val fecha: String ="",
    val idPromocion:Int= 0,
    ): Serializable
