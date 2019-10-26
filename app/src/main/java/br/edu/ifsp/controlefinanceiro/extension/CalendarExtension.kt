package br.edu.ifsp.controlefinanceiro.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formataData(pattern: String): String {
    return SimpleDateFormat(pattern).format(this.time)
}