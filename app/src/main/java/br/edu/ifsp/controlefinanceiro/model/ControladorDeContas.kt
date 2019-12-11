package br.edu.ifsp.controlefinanceiro.model

import br.edu.ifsp.controlefinanceiro.enums.CategoriaEnum
import br.edu.ifsp.controlefinanceiro.enums.PeriodicidadeEnum
import br.edu.ifsp.controlefinanceiro.enums.TipoTransacaoEnum
import java.math.BigDecimal


class ControladorDeContas {

    companion object {

        var listaDeContas: ArrayList<Conta> = ArrayList()
        var ultimoCodigo = 0
        var contaAtual = Conta(0, "", BigDecimal(0), ArrayList())

        fun criarContaFicticia() {

            if (contaAtual.listaTransacoes.size != 0) {
                return
            }

            val listaDeTransacoes: ArrayList<Transacao> = ArrayList();

            listaDeTransacoes.add(
                Transacao(
                    valor = BigDecimal.ZERO,
                    descricao = "SALDO INICIAL",
                    categoria = CategoriaEnum.SALDO_INICIAL.name,
                    tipoTransacao = TipoTransacaoEnum.RECEITA,
                    periodicidade = PeriodicidadeEnum.UNICO.name
                )
            )

            val contaDefault =
                Conta(getProximoCodigo(), "Bradesco", BigDecimal(1000), listaDeTransacoes)
            listaDeContas.add(contaDefault)
            contaAtual = contaDefault
        }

        fun adicionarConta(conta: Conta) {
            listaDeContas.add(conta)
        }

        fun getSaldoContas(): BigDecimal {
            var saldo = BigDecimal.ZERO;
            for (conta in listaDeContas) {
                println("********* " + conta.getSaldoFinal())
                println("********* " + contaAtual.getSaldoFinal())
                saldo = saldo.plus(conta.getSaldoFinal())
                println("Saldo " + saldo)
            }
            return saldo
        }

        fun getProximoCodigo(): Int {
            ultimoCodigo = ultimoCodigo + 1
            return ultimoCodigo
        }
    }
}