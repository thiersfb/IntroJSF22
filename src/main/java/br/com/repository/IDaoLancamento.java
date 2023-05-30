package br.com.repository;

import java.util.Date;
import java.util.List;

import br.com.entidades.Lancamento;

public interface IDaoLancamento {

	//List<Lancamento> consultarLancamentos();
	List<Lancamento> consultarLancamentos(Long codUser);
	List<Lancamento> consultarLancamentosLimit(Long codUser, int limit);
	
	List<Lancamento> relatorioLancamento(String numNf, Date dataIni, Date dataFim);
	
}
