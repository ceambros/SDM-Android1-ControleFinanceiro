package br.edu.ifsp.controlefinanceiro.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.edu.ifsp.controlefinanceiro.R
import br.edu.ifsp.controlefinanceiro.enums.TipoTransacaoEnum
import br.edu.ifsp.controlefinanceiro.extension.formataData
import br.edu.ifsp.controlefinanceiro.extension.formatoBrasileiro
import br.edu.ifsp.controlefinanceiro.model.Conta
import br.edu.ifsp.controlefinanceiro.model.Transacao
import kotlinx.android.synthetic.main.extrato_item.view.*
import kotlinx.android.synthetic.main.saldo_financeiro.view.*

class RelatorioAdapter(val contexto: Context, val conta: Conta) : BaseAdapter() {


    override fun getView(posicaoItem: Int, view: View?, parent: ViewGroup?): View {
        var viewExtrato =
            LayoutInflater.from(contexto).inflate(R.layout.extrato_item, parent, false)

        val transacao = conta.listaTransacoes[posicaoItem]

        //Utilizando synthetic para manipular
        if (transacao.tipoTransacao == TipoTransacaoEnum.DESPESA) {
            viewExtrato.transacao_icone.setBackgroundResource(R.drawable.icone_transacao_item_despesa)
        } else {
            viewExtrato.transacao_icone.setBackgroundResource(R.drawable.icone_transacao_item_receita)
        }
        viewExtrato.valor_transacao.text = transacao.valor.formatoBrasileiro()
        viewExtrato.tipo_transacao.text = transacao.categoria
        //Utilizando extension para o metodo formataData
        viewExtrato.data_transacao.text = transacao.data.formataData("dd/MM/yyyy")

        return viewExtrato
    }

    override fun getItem(posicaoItem: Int): Transacao {
        return conta.listaTransacoes[posicaoItem]
    }

    override fun getItemId(posicaoItem: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return conta.listaTransacoes.size
    }

}