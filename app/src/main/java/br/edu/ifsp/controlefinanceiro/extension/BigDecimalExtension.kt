package br.edu.ifsp.controlefinanceiro.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun BigDecimal.formatoBrasileiro(): String {

    return DecimalFormat
        .getCurrencyInstance(Locale("pt", "br"))
        .format(this)
}