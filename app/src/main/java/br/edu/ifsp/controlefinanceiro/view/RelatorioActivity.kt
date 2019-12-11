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
import br.edu.ifsp.controlefinanceiro.model.Transacao
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.R.attr.x
import java.nio.file.Files.size
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import br.edu.ifsp.controlefinanceiro.adapter.RelatorioAdapter
import kotlinx.android.synthetic.main.activity_extrato_financeiro.*
import kotlinx.android.synthetic.main.form_relatorio.*
import kotlinx.android.synthetic.main.form_relatorio.extrato_financeiro_listview
import kotlinx.android.synthetic.main.form_transacao.form_transacao_categoria
import kotlinx.android.synthetic.main.form_transacao.form_transacao_tipo
import kotlinx.android.synthetic.main.form_transacao.transacao_nome_conta
import javax.xml.transform.TransformerConfigurationException


class RelatorioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(br.edu.ifsp.controlefinanceiro.R.layout.form_relatorio)

        if (getIntent().hasExtra("nomeContaBancaria")) {
            val nomeConta = getIntent().getStringExtra("nomeContaBancaria")
            transacao_nome_conta.text = nomeConta
        }

        val listaBanco =
            findViewById(br.edu.ifsp.controlefinanceiro.R.id.form_banco) as Spinner

        val listaTipo =
            findViewById(br.edu.ifsp.controlefinanceiro.R.id.form_transacao_tipo) as Spinner

        val listaCategoria =
            findViewById(br.edu.ifsp.controlefinanceiro.R.id.form_transacao_categoria) as Spinner

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

        listaBanco.adapter = ArrayAdapter<Conta>(
            this,
            android.R.layout.simple_spinner_item,
            ControladorDeContas.listaDeContas
        )

    }

    fun onClickPesquisar(v: View) {

        val conta = form_banco.selectedItem as Conta
        val tipo = form_transacao_tipo.selectedItem as TipoTransacaoEnum
        val categoria = form_transacao_categoria.selectedItem.toString()
        val dataInicio = form_transacao_data_inicio.text.toString()
        val dataFim = form_transacao_data_fim.text.toString()

        val dataInicioFormatada = null
        val dataFimFormatada = null;

        if (!dataInicio.isEmpty() && !dataFim.isEmpty()) {
            val calendario = Calendar.getInstance()
            try {
                val sdf = SimpleDateFormat("dd/MM/yyyy");
                val dataInicioFormatada = sdf.parse(dataInicio) as Date
                val dataFimFormatada = sdf.parse(dataInicio) as Date

                calendario.time = dataInicioFormatada
            } catch (ex: Exception) {
                Toast.makeText(this, "Data inválida!", Toast.LENGTH_SHORT).show()
                return
            }
        }


        var listaConta: ArrayList<Conta> = ArrayList()
        var listaTransacao: ArrayList<Transacao> = ArrayList()

        for (conta in ControladorDeContas.listaDeContas) {

            // Do something with the value
            println(conta.descricao);
            listaTransacao.clear();

            for (transacao in conta.listaTransacoes) {
                println(transacao.valor);

                if (transacao.categoria.equals(categoria)) {
                    listaTransacao.add(transacao);
                    continue
                }

                if (transacao.tipoTransacao.equals(tipo)) {
                    listaTransacao.add(transacao);
                    continue
                }

                if (transacao.data.before(dataFimFormatada) && transacao.data.after(
                        dataInicioFormatada
                    )
                ) {
                    listaTransacao.add(transacao);
                    continue
                }
            }

            //if(!listaTransacao.isEmpty()){
            conta.listaTransacoes.clear();
            conta.listaTransacoes.addAll(listaTransacao);
            listaConta.add(conta);
            //}

            extrato_financeiro_listview.adapter = RelatorioAdapter(this, conta)
        }

    }
}