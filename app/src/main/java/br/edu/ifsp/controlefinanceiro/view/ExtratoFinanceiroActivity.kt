package br.edu.ifsp.controlefinanceiro.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extrato_financeiro)

        ControladorDeContas.criarContaFicticia()

        var viewPrincipal = window.decorView

        with(viewPrincipal.resumo_card_receita) {
            text = ControladorDeContas.contaAtual.getTotalReceita().toString();
            setTextColor(ContextCompat.getColor(context, R.color.receita))
        }
        with(viewPrincipal.resumo_card_despesa) {
            text =  ControladorDeContas.contaAtual.getTotalDespesa().toString();
            setTextColor(ContextCompat.getColor(context, R.color.despesa))
        }
        with(viewPrincipal.resumo_card_total) {
            text =  ControladorDeContas.contaAtual.getSaldoFinal().formatoBrasileiro();
            if ( ControladorDeContas.contaAtual.getSaldoFinal() <= BigDecimal.ZERO) {
                setTextColor(ContextCompat.getColor(context, R.color.despesa))
            }
            setTextColor(ContextCompat.getColor(context, R.color.receita))
        }
        viewPrincipal.nome_conta.text =  ControladorDeContas.contaAtual.descricao;

        //Utilizando a extensao kotlin-android-extensions podemos utilizar os componentes do layout sem utilizer findViewByID
        extrato_financeiro_listview.setAdapter(ExtratoAdapter(this,  ControladorDeContas.contaAtual))
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
                finish();
            }
            R.id.adicionarContaMenuItem -> {
                retorno = true;
                val adicionarContaIntent = Intent(this, AdicionarContaActivity::class.java)
                startActivityForResult(
                    adicionarContaIntent,
                    Constantes.ADICIONAR_CONTA_REQUEST_CODE
                )
            }
        }
        return retorno
    }

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constantes.ADICIONAR_CONTA_REQUEST_CODE &&
            resultCode == AppCompatActivity.RESULT_OK
        ) {
            val configuracao = data?.getParcelableExtra<Configuracao>(
                ConfiguracaoActivity.Constantes.CONFIGURACAO
            )
            if (configuracao!!.leiauteAvancado) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.calculadoraFl, CalculadoraAvancadaFragment()).commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.calculadoraFl, CalculadoraBasicaFragment()).commit()
            }
        }
    }
    */


}