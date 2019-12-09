package br.edu.ifsp.controlefinanceiro.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.controlefinanceiro.R
import kotlinx.android.synthetic.main.adicionar_conta.*

class AdicionarContaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adicionar_conta)
    }

    fun onClickSalvar(v: View) {

        val descricao = adicionarContaDescricao.text
        val saldoInicial = adicionarContaSaldoInicial.text

        //Criar um objeto Configuracao
        //val novaConfiguracao: Configuracao = Configuracao(leiautAvancado, separador)

        //Chama o Controller para salvar
        //configuracaoController.salvaConfiguracao(novaConfiguracao)

        Toast.makeText(this, "Conta Salva Com Sucesso!", Toast.LENGTH_SHORT).show()
        finish()
    }
}