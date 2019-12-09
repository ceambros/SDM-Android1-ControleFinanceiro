package br.edu.ifsp.controlefinanceiro.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.edu.ifsp.controlefinanceiro.R
import br.edu.ifsp.controlefinanceiro.adapter.ExtratoAdapter
import br.edu.ifsp.controlefinanceiro.enums.CategoriaEnum
import br.edu.ifsp.controlefinanceiro.enums.TipoTransacaoEnum
import br.edu.ifsp.controlefinanceiro.extension.formatoBrasileiro
import br.edu.ifsp.controlefinanceiro.model.Conta
import br.edu.ifsp.controlefinanceiro.model.Transacao
import kotlinx.android.synthetic.main.activity_extrato_financeiro.*
import kotlinx.android.synthetic.main.saldo_financeiro.view.*
import java.math.BigDecimal

class ExtratoFinanceiro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extrato_financeiro)

        val contaBancaria = criarContaFicticia()

        var viewPrincipal = window.decorView

        with(viewPrincipal.resumo_card_receita) {
            text = contaBancaria.getTotalReceita().toString();
            setTextColor(ContextCompat.getColor(context, R.color.receita))
        }
        with(viewPrincipal.resumo_card_despesa) {
            text = contaBancaria.getTotalDespesa().toString();
            setTextColor(ContextCompat.getColor(context, R.color.despesa))
        }
        with(viewPrincipal.resumo_card_total) {
            text = contaBancaria.getSaldoFinal().formatoBrasileiro();
            if (contaBancaria.getSaldoFinal() <= BigDecimal.ZERO) {
                setTextColor(ContextCompat.getColor(context, R.color.despesa))
            }
            setTextColor(ContextCompat.getColor(context, R.color.receita))
        }
        viewPrincipal.nome_conta.text = contaBancaria.descricao;

        //Utilizando a extensao kotlin-android-extensions podemos utilizar os componentes do layout sem utilizer findViewByID
        extrato_financeiro_listview.setAdapter(ExtratoAdapter(this, criarContaFicticia()))
    }

    private fun criarContaFicticia(): Conta {

        val listaDeTransacoes: List<Transacao> = listOf(
            Transacao(
                BigDecimal(1000.25),
                "Conta de Agua",
                CategoriaEnum.AGUA.name,
                TipoTransacaoEnum.DESPESA
            ),
            Transacao(
                BigDecimal(100.85),
                "Salário Mensal",
                CategoriaEnum.SALARIO.name,
                TipoTransacaoEnum.RECEITA
            )
        )
        return Conta("Bradesco", BigDecimal(1000), listaDeTransacoes)
    }
}