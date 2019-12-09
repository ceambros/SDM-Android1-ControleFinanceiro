package br.edu.ifsp.controlefinanceiro.model

import br.edu.ifsp.controlefinanceiro.enums.TipoTransacaoEnum
import java.math.BigDecimal
import java.util.Calendar

class Transacao(
    val valor: BigDecimal
    , val descricao: String
    , val categoria: String
    , val tipoTransacao: TipoTransacaoEnum
    , val data: Calendar = Calendar.getInstance()
)