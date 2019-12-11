package br.edu.ifsp.controlefinanceiro.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.controlefinanceiro.R
import br.edu.ifsp.controlefinanceiro.enums.CategoriaEnum
import br.edu.ifsp.controlefinanceiro.enums.PeriodicidadeEnum
import br.edu.ifsp.controlefinanceiro.enums.TipoTransacaoEnum
import br.edu.ifsp.controlefinanceiro.model.Conta
import br.edu.ifsp.controlefinanceiro.model.ControladorDeContas
import br.edu.ifsp.controlefinanceiro.model.Transacao
import kotlinx.android.synthetic.main.adicionar_conta.*
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

class AdicionarContaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adicionar_conta)
    }

    fun onClickSalvar(v: View) {

        val descricao = adicionarContaDescricao.text.toString()
        val saldoInicial = try {
            BigDecimal(adicionarContaSaldoInicial.text.toString())
        } catch (exception: NumberFormatException) {
            BigDecimal.ZERO
        }

        val tipoTransacao = if (saldoInicial < BigDecimal.ZERO) {
            TipoTransacaoEnum.DESPESA
        } else {
            TipoTransacaoEnum.RECEITA
        }

        val dataAtual = Calendar.getInstance()
        dataAtual.time = Date(System.currentTimeMillis())

        val novaTransacao = Transacao(
            valor = saldoInicial.abs(),
            descricao = "SALDO INICIAL",
            categoria = "SALDO INICIAL",
            tipoTransacao = tipoTransacao,
            data = dataAtual,
            periodicidade = PeriodicidadeEnum.UNICO.toString()
        )

        val transacoes = mutableListOf<Transacao>()
        transacoes.add(novaTransacao)

        var conta =
            Conta(ControladorDeContas.getProximoCodigo(), descricao, saldoInicial,
                transacoes as ArrayList<Transacao>
            )

        ControladorDeContas.adicionarConta(conta)
        // Setar resultado para main activithy

        setResult(
            AppCompatActivity.RESULT_OK,
            Intent().putExtra("retornoAdicionarConta", "Conta adicionada com sucesso")
        )

        finish()
    }
}