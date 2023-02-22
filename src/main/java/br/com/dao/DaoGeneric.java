package br.com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Table;

import br.com.jpautil.JPAUtil;

public class DaoGeneric<E> {
	
	public void salvar(E entidade) {
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		entityManager.persist(entidade);
		entityTransaction.commit();
		entityManager.close();
		
	}
	
	//salva os dados no banco, consulta os dados gerados na base e retorna-os na entidade
	public E merge(E entidade) {
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		E retorno = entityManager.merge(entidade);
		entityTransaction.commit();
		entityManager.close();
		
		return retorno;
		
	}
	
	public void delete(E entidade) {
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		entityManager.remove(entidade);
		entityTransaction.commit();
		entityManager.close();
		
	}
	
	public void deleteById(E entidade) {
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		
		Object id = JPAUtil.getPrimaryKey(entidade); 
		//entityManager.remove(entidade);
		entityManager
		.createNativeQuery(
				"delete from " + entidade.getClass().getAnnotation(Table.class).name().toLowerCase() + " where id = " + id)
		.executeUpdate(); /* Executa "atualização", manipula dados no banco */

		
		entityTransaction.commit();
		entityManager.close();
		
	}
	
	public List<E> getListEntity(Class<E> entidade) {
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		List<E> lista = entityManager
				.createQuery(" from " + entidade.getAnnotation(Table.class).name())
				.getResultList();
		
		entityTransaction.commit();
		entityManager.close();
		return lista;
	}
	
	public E consultar(Class<E> entidade, String id) {
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		E objeto = (E) entityManager.find(entidade, Long.parseLong(id));
		entityTransaction.commit();
		return objeto;
	}
	
}
