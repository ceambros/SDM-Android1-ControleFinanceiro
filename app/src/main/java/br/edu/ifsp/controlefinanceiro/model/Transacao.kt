package br.edu.ifsp.controlefinanceiro.model

import br.edu.ifsp.controlefinanceiro.enums.TipoTransacao
import java.util.Calendar

class Transacao(val valor: Double, val descricao: String, val tipoTransacao: TipoTransacao, val data: Calendar = Calendar.getInstance())