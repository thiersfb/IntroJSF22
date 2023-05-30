package br.com.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Table;

import br.com.entidades.Lancamento;

@Named
public class IDaoLancamentoImplement implements IDaoLancamento, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> consultarLancamentos(Long codUser) {
		List<Lancamento> lista = null;
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		//lista = entityManager.createQuery(" from TBLancamento where usuario.id = " + codUser).getResultList();
		//lista = entityManager.createQuery(" from " + Lancamento.class.getAnnotation(Table.class).name() + " where usuario.id = " + codUser).getResultList();
		lista = entityManager.createQuery(" from " + Lancamento.class.getAnnotation(Table.class).name()).getResultList();
		
		transaction.commit();
		
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> consultarLancamentosLimit(Long codUser, int limit) {
		List<Lancamento> lista = null;
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		lista = entityManager.createQuery(" from " + Lancamento.class.getAnnotation(Table.class).name() + " order by id desc ")
				.setMaxResults(limit)
				.getResultList();
		
		transaction.commit();
		
		return lista;
	}
	
	public List<Lancamento> relatorioLancamento(String numNF, Date dataIni, Date dataFim) {
		//List<Lancamento> lista = null;
		
		System.out.println("NumNF: "+ numNF + " | Data Inicial: " + dataIni + " | Data Final: " + dataFim);
		
		return null;
	}

}
