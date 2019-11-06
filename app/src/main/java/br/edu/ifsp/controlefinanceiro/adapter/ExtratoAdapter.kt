package br.edu.ifsp.controlefinanceiro.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.edu.ifsp.controlefinanceiro.R
import br.edu.ifsp.controlefinanceiro.extension.formataData
import br.edu.ifsp.controlefinanceiro.model.Transacao
import kotlinx.android.synthetic.main.extrato_item.view.*

class ExtratoAdapter(contexto: Context, listaDeTransacoes: List<Transacao>) : BaseAdapter() {

    private val listaDeTransacoes = listaDeTransacoes
    private val contexto = contexto

    override fun getView(posicaoItem: Int, view: View?, parent: ViewGroup?): View {
        var viewExtrato: View =
            LayoutInflater.from(contexto).inflate(R.layout.extrato_item, parent, false)

        val transacao = listaDeTransacoes[posicaoItem]

        //Utilizando synthetic para manipular
        viewExtrato.valor_transacao.text = transacao.valor.toString()
        viewExtrato.tipo_transacao.text = transacao.descricao
        //Utilizando extension para o metodo formataData
        viewExtrato.data_transacao.text = transacao.data.formataData("dd/MM/yyyy")
        return viewExtrato
    }

    override fun getItem(posicaoItem: Int): Transacao {
        return listaDeTransacoes.get(posicaoItem)
    }

    override fun getItemId(posicaoItem: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return listaDeTransacoes.size
    }

}