package br.edu.ifsp.controlefinanceiro.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.edu.ifsp.controlefinanceiro.R
import br.edu.ifsp.controlefinanceiro.adapter.ExtratoAdapter
import br.edu.ifsp.controlefinanceiro.enums.CategoriaEnum
import br.edu.ifsp.controlefinanceiro.enums.TipoTransacaoEnum
import br.edu.ifsp.controlefinanceiro.extension.formatoBrasileiro
import br.edu.ifsp.controlefinanceiro.model.Conta
import br.edu.ifsp.controlefinanceiro.model.ControladorDeContas
import br.edu.ifsp.controlefinanceiro.model.Transacao
import kotlinx.android.synthetic.main.activity_extrato_financeiro.*
import kotlinx.android.synthetic.main.saldo_financeiro.view.*
import java.math.BigDecimal

class ExtratoFinanceiroActivity : AppCompatActivity() {

    object Constantes {
        val ADICIONAR_CONTA_REQUEST_CODE = 0
        val ADICIONAR_TRANSACAO_REQUEST_CODE = 1
        val ALTERAR_CONTA_REQUEST_CODE = 2
        val VISUALIZAR_EXTRATO_REQUEST_CODE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extrato_financeiro)

        ControladorDeContas.criarContaFicticia()

        //Utilizando a extensao kotlin-android-extensions podemos utilizar os componentes do layout sem utilizer findViewByID
        atualizaExtrato()
        atualizarResumo()
    }

    private fun atualizaExtrato() {
        extrato_financeiro_listview.setAdapter(ExtratoAdapter(this, ControladorDeContas.contaAtual))
    }

    private fun atualizarResumo() {
        var viewPrincipal = window.decorView

        with(viewPrincipal.resumo_card_receita) {
            text = ControladorDeContas.contaAtual.getTotalReceita().toString();
            setTextColor(ContextCompat.getColor(context, R.color.receita))
        }
        with(viewPrincipal.resumo_card_despesa) {
            text = ControladorDeContas.contaAtual.getTotalDespesa().toString();
            setTextColor(ContextCompat.getColor(context, R.color.despesa))
        }
        with(viewPrincipal.resumo_card_total) {
            text = ControladorDeContas.contaAtual.getSaldoFinal().formatoBrasileiro();
            if (ControladorDeContas.contaAtual.getSaldoFinal() <= BigDecimal.ZERO) {
                setTextColor(ContextCompat.getColor(context, R.color.despesa))
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.receita))
            }
        }
        with(viewPrincipal.resumo_saldo_total) {
            text = ControladorDeContas.getSaldoContas().formatoBrasileiro();
            if (ControladorDeContas.getSaldoContas() < BigDecimal.ZERO) {
                setTextColor(ContextCompat.getColor(context, R.color.despesa))
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.receita))
            }
        }
        viewPrincipal.nome_conta.text = ControladorDeContas.contaAtual.descricao
    }

    //Cria o menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var retorno = false

        when (item.itemId) {
            R.id.alterarContaMenuItem -> {
                retorno = true;
                val alterarContaIntent = Intent(this, AlterarContaActivity::class.java)
                startActivityForResult(
                    alterarContaIntent,
                    Constantes.ALTERAR_CONTA_REQUEST_CODE
                )
            }
            R.id.adicionarContaMenuItem -> {
                retorno = true;
                val adicionarContaIntent = Intent(this, AdicionarContaActivity::class.java)
                startActivityForResult(
                    adicionarContaIntent,
                    Constantes.ADICIONAR_CONTA_REQUEST_CODE
                )
            }
            R.id.adicionarTransacao -> {
                retorno = true;
                val adicionarContaIntent = Intent(this, AdicionarTransacaoActivity::class.java)

                //Passagem de parametros para a proxima activity
                adicionarContaIntent.putExtra(
                    "nomeContaBancaria",
                    ControladorDeContas.contaAtual.descricao
                )

                startActivityForResult(
                    adicionarContaIntent,
                    Constantes.ADICIONAR_TRANSACAO_REQUEST_CODE
                )
            }
            R.id.visualizarExtratos -> {
                retorno = true;
                val visualizarExtrato = Intent(this, RelatorioActivity::class.java)

                startActivityForResult(
                    visualizarExtrato,
                    Constantes.VISUALIZAR_EXTRATO_REQUEST_CODE
                )
            }
        }
        return retorno
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constantes.ADICIONAR_TRANSACAO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            atualizarResumo()
            Toast.makeText(this, data?.getStringExtra("retornoTransacao"), Toast.LENGTH_SHORT)
                .show()
        }
        if (requestCode == Constantes.ALTERAR_CONTA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            atualizarResumo()
            atualizaExtrato()
            Toast.makeText(this, data?.getStringExtra("retornoTrocarConta"), Toast.LENGTH_SHORT)
                .show()
        }
        if (requestCode == Constantes.ADICIONAR_CONTA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            atualizarResumo()
            atualizaExtrato()
            Toast.makeText(this, data?.getStringExtra("retornoAdicionarConta"), Toast.LENGTH_SHORT)
                .show()
        }
    }
}