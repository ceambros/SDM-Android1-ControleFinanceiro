package br.edu.ifsp.controlefinanceiro.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.controlefinanceiro.R
import br.edu.ifsp.controlefinanceiro.model.Conta
import br.edu.ifsp.controlefinanceiro.model.ControladorDeContas
import kotlinx.android.synthetic.main.adicionar_conta.*
import java.math.BigDecimal

class AdicionarContaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adicionar_conta)
    }

    fun onClickSalvar(v: View) {

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
    }
}