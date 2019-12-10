package br.edu.ifsp.controlefinanceiro.model

import br.edu.ifsp.controlefinanceiro.enums.CategoriaEnum
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
                    BigDecimal(1000.25),
                    "Conta de Agua",
                    CategoriaEnum.AGUA.name,
                    TipoTransacaoEnum.DESPESA
                )
            )
            listaDeTransacoes.add(
                Transacao(
                    BigDecimal(1000.85),
                    "Salário Mensal",
                    CategoriaEnum.SALARIO.name,
                    TipoTransacaoEnum.RECEITA
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

        fun mostrarContas() {
            for (conta in listaDeContas) {
                println("*****************" + conta.codigo + " - " + conta.descricao)
            }
        }

        fun getProximoCodigo(): Int {
            ultimoCodigo = ultimoCodigo + 1
            return ultimoCodigo
        }
    }
}