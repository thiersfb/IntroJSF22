package br.com.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Table;

import br.com.jpautil.JPAUtil;

@Named
public class DaoGeneric<E>  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	@Inject
	private JPAUtil jpaUtil;
		
	public void salvar(E entidade) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		entityManager.persist(entidade);
		entityTransaction.commit();
		
	}
	
	//salva os dados no banco, consulta os dados gerados na base e retorna-os na entidade
	public E merge(E entidade) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		E retorno = entityManager.merge(entidade);
		entityTransaction.commit();
		
		return retorno;
		
	}
	
	public void delete(E entidade) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		entityManager.remove(entidade);
		entityTransaction.commit();
		
	}
	
	public void deleteById(E entidade) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		
		Object id = jpaUtil.getPrimaryKey(entidade); 
		entityManager.createNativeQuery(
				"delete from " + entidade.getClass().getAnnotation(Table.class).name().toLowerCase() + " where id = " + id)
		.executeUpdate(); /* Executa "atualização", manipula dados no banco */

		
		entityTransaction.commit();
		
	}
	
	public void erasePhotoById(E entidade) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		
		Object id = jpaUtil.getPrimaryKey(entidade); 
		entityManager.createNativeQuery(
				"update " + entidade.getClass().getAnnotation(Table.class).name().toLowerCase() + " set extensao = null, fotoIconBase64 = null, fotoIconBase64Original = null, filenameFoto = null where id = " + id)
		.executeUpdate(); /* Executa "atualização", manipula dados no banco */

		
		entityTransaction.commit();
		
	}
	
	public List<E> getListEntity(Class<E> entidade) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		List<E> lista = entityManager
				.createQuery(" from " + entidade.getAnnotation(Table.class).name())
				.getResultList();
		
		entityTransaction.commit();
		return lista;
	}
	

	public List<E> getListEntityLimit(Class<E> entidade, int limit) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		List<E> lista = entityManager
				.createQuery(" from " + entidade.getAnnotation(Table.class).name() + " order by id desc ")
				.setMaxResults(limit)
				.getResultList();
		
		entityTransaction.commit();
		return lista;
	}
	
	public E consultar(Class<E> entidade, String id) {
		
		//EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		E objeto = (E) entityManager.find(entidade, Long.parseLong(id));
		entityTransaction.commit();
		return objeto;
	}
	
}
