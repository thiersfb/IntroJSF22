package br.com.repository;

import java.io.Serializable;
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
	 

}
