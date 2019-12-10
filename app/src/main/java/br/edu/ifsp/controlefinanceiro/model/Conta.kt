package br.edu.ifsp.controlefinanceiro.model

import br.edu.ifsp.controlefinanceiro.enums.TipoTransacaoEnum
import br.edu.ifsp.controlefinanceiro.extension.formatoBrasileiro
import java.math.BigDecimal

class Conta(
    val codigo: Int,
    val descricao: String,
    val saldoInicial: BigDecimal,
    val listaTransacoes: List<Transacao>
) {

    fun receita(): BigDecimal {
        var totalReceita = BigDecimal.ZERO
        for (transacao in listaTransacoes) {
            if (transacao.tipoTransacao == TipoTransacaoEnum.RECEITA) {
                totalReceita = totalReceita.plus(transacao.valor)
            }
        }
        return totalReceita
    }

    fun despesa(): BigDecimal {
        var totalDespesa = BigDecimal.ZERO
        for (transacao in listaTransacoes) {
            if (transacao.tipoTransacao == TipoTransacaoEnum.DESPESA) {
                totalDespesa = totalDespesa.plus(transacao.valor)
            }
        }
        return totalDespesa
    }

    fun getTotalReceita(): String {
        return receita().formatoBrasileiro()
    }

    fun getTotalDespesa(): String {
        return despesa().formatoBrasileiro()
    }

    fun getSaldoFinal(): BigDecimal {
        return receita().subtract(despesa())
    }
}