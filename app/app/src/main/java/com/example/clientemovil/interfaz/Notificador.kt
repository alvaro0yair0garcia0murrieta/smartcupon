package com.example.clientemovil.interfaz

import com.example.clientemovil.modelo.POKO.Promocion

interface Notificador {
    fun seleccionarItem(posicion: Int, promocion: Promocion)
}
