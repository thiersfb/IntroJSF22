package br.com.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Table;

import br.com.entidades.Lancamento;
import br.com.jpautil.JPAUtil;

public class IDaoLancamentoImplement implements IDaoLancamento {

	@Override
	public List<Lancamento> consultarLancamentos(Long codUser) {
		List<Lancamento> lista = null;
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		//lista = entityManager.createQuery(" from TBLancamento where usuario.id = " + codUser).getResultList();
		lista = entityManager.createQuery(" from " + Lancamento.class.getAnnotation(Table.class).name() + " where usuario.id = " + codUser).getResultList();
		
		transaction.commit();
		entityManager.close();
		
		return lista;
	}

}
