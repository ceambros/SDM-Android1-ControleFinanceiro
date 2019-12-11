package br.edu.ifsp.controlefinanceiro.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.controlefinanceiro.enums.TipoTransacaoEnum
import br.edu.ifsp.controlefinanceiro.model.Conta
import br.edu.ifsp.controlefinanceiro.model.ControladorDeContas
import kotlinx.android.synthetic.main.adicionar_conta.*
import kotlinx.android.synthetic.main.form_transacao.*
import java.math.BigDecimal
import android.widget.ArrayAdapter
import android.widget.Spinner
import br.edu.ifsp.controlefinanceiro.enums.CategoriaEnum
import br.edu.ifsp.controlefinanceiro.enums.PeriodicidadeEnum
import br.edu.ifsp.controlefinanceiro.model.Transacao
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AdicionarTransacaoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(br.edu.ifsp.controlefinanceiro.R.layout.form_transacao)

        if (getIntent().hasExtra("nomeContaBancaria")) {
            val nomeConta = getIntent().getStringExtra("nomeContaBancaria")
            transacao_nome_conta.text = nomeConta
        }

        val listaTipo =
            findViewById(br.edu.ifsp.controlefinanceiro.R.id.form_transacao_tipo) as Spinner

        val listaCategoria =
            findViewById(br.edu.ifsp.controlefinanceiro.R.id.form_transacao_categoria) as Spinner

        val listaPeriodicidade =
            findViewById(br.edu.ifsp.controlefinanceiro.R.id.form_transacao_periodicidade) as Spinner

        listaTipo.adapter =
            ArrayAdapter<TipoTransacaoEnum>(
                this,
                android.R.layout.simple_spinner_item,
                TipoTransacaoEnum.values()
            )
        listaCategoria.adapter =
            ArrayAdapter<CategoriaEnum>(
                this,
                android.R.layout.simple_spinner_item,
                CategoriaEnum.values()
            )

        listaPeriodicidade.adapter =
            ArrayAdapter<PeriodicidadeEnum>(
                this,
                android.R.layout.simple_spinner_item,
                PeriodicidadeEnum.values()
            )
    }

    fun onClickSalvarTransacao(v: View) {

        val tipo = form_transacao_tipo.selectedItem as TipoTransacaoEnum
        val categoria = form_transacao_categoria.selectedItem.toString()
        val dataEmTexto = form_transacao_data.text.toString()
        val periodicidade = form_transacao_periodicidade.selectedItem.toString()

        val valor = try {
            BigDecimal(form_transacao_valor.text.toString())
        } catch (exception: NumberFormatException) {
            BigDecimal.ZERO
            Toast.makeText(this, "Valor inválido!", Toast.LENGTH_SHORT).show()
            return
        }

        val calendario = Calendar.getInstance()
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy");
            val dataFormatata = sdf.parse(dataEmTexto) as Date

            calendario.time = dataFormatata
        } catch (ex: Exception) {
            Toast.makeText(this, "Data inválida!", Toast.LENGTH_SHORT).show()
            return
        }

        val novaTransacao = Transacao(
            valor = valor,
            descricao = "",
            categoria = categoria,
            tipoTransacao = tipo,
            data = calendario,
            periodicidade = periodicidade
        )

        ControladorDeContas.contaAtual.listaTransacoes.add(novaTransacao)

        // Setar resultado para main activithy
        setResult(
            AppCompatActivity.RESULT_OK,
            Intent().putExtra(
                "retornoTransacao",
                "Transacao " + ControladorDeContas.contaAtual.listaTransacoes.size + " criada com sucesso"
            )
        )

        //Toast.makeText(this, "Transação Salva Com Sucesso!", Toast.LENGTH_SHORT).show()
        finish()
    }
}