package br.com.repository;

import java.util.List;

import br.com.entidades.Lancamento;

public interface IDaoLancamento {

	List<Lancamento> consultarLancamentos(Long codUser);
}
