package br.edu.ifsp.controlefinanceiro.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.controlefinanceiro.R
import br.edu.ifsp.controlefinanceiro.adapter.ExtratoAdapter
import br.edu.ifsp.controlefinanceiro.model.Transacao
import kotlinx.android.synthetic.main.activity_extrato_financeiro.*
import java.util.*

class ExtratoFinanceiro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extrato_financeiro)

        val listaDeTransacoes: List<Transacao> = listOf(
            Transacao(100.25, "Despesa", Calendar.getInstance()),
            Transacao(1000.85, "Salário", Calendar.getInstance())
        )

        //Utilizando a extensao kotlin-android-extensions podemos utilizar os componentes do layout sem utilizer findViewByID
        extrato_financeiro_listview.setAdapter(ExtratoAdapter(this, listaDeTransacoes))
    }
}