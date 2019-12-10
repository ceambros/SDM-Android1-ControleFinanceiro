package br.edu.ifsp.controlefinanceiro.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.controlefinanceiro.R
import br.edu.ifsp.controlefinanceiro.enums.CategoriaEnum
import br.edu.ifsp.controlefinanceiro.enums.TipoTransacaoEnum
import br.edu.ifsp.controlefinanceiro.model.Conta
import br.edu.ifsp.controlefinanceiro.model.ControladorDeContas
import br.edu.ifsp.controlefinanceiro.model.Transacao
import kotlinx.android.synthetic.main.adicionar_conta.*
import java.math.BigDecimal

class AlterarContaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_alterar_conta)

        val listaContas =
            findViewById(br.edu.ifsp.controlefinanceiro.R.id.form_conta) as Spinner

        listaContas.adapter =
            ArrayAdapter<Conta>(
                this,
                android.R.layout.simple_spinner_item,
                ControladorDeContas.listaDeContas
            )
    }


    fun onClickSalvarAlterarConta(v: View) {

        val listaDeContas =
            findViewById(br.edu.ifsp.controlefinanceiro.R.id.form_conta) as Spinner
        val contaSelecionada = listaDeContas.selectedItem as Conta

        ControladorDeContas.contaAtual = contaSelecionada

        // Setar resultado para main activithy
        setResult(
            AppCompatActivity.RESULT_OK,
            Intent().putExtra("retornoTrocarConta", "Conta alterado com sucesso")
        )

        finish()

        /*
        val descricao = adicionarContaDescricao.text.toString()
        val saldoInicial = BigDecimal(adicionarContaSaldoInicial.text.toString())

        //Criar um objeto Configuracao
        //val novaConfiguracao: Configuracao = Configuracao(leiautAvancado, separador)

        //Chama o Controller para salvar
        //configuracaoController.salvaConfiguracao(novaConfiguracao)

        var conta =
            Conta(ControladorDeContas.getProximoCodigo(), descricao, saldoInicial, ArrayList())
        ControladorDeContas.adicionarConta(conta)

        ControladorDeContas.mostrarContas()

        Toast.makeText(this, "Conta Salva Com Sucesso!", Toast.LENGTH_SHORT).show()
        finish()

         */
    }

}