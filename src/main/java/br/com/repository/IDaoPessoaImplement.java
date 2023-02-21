package br.com.repository;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Table;

import br.com.entidades.Cidades;
import br.com.entidades.Estados;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;

public class IDaoPessoaImplement implements IDaoPessoa {

	//private EntityManager entityManager;
	
	@Override
	public Pessoa consultarUsuario(String login, String senha) {
		
		Pessoa pessoa = null;
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		//pessoa = (Pessoa) entityManager.createQuery(" select p from TBUsuario p where p.login = '" + login + "' and p.senha = '" + senha + "'").getSingleResult();
		pessoa = (Pessoa) entityManager.createQuery(" select p from " + Pessoa.class.getAnnotation(Table.class).name() + " p where p.login = '" + login + "' and p.senha = '" + senha + "'").getSingleResult();
		
		entityTransaction.commit();
		entityManager.close();
		
		return pessoa;
	}

	@Override
	public List<SelectItem> listaEstados() {
		
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		List<Estados> estados = entityManager.createQuery(" from " + Estados.class.getAnnotation(Table.class).name()).getResultList();
		
		for (Estados estado : estados) {
			selectItems.add(new SelectItem(estado.getId(), estado.getNome())); //id do objeto, label
		}
		
		return selectItems;
	}
	
	/*
	@Override
	public List<SelectItem> listaCidades(String estado_id) {
		
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		List<Estados> estados = entityManager.createQuery(" from " + Cidades.class.getAnnotation(Table.class).name() + " where estados_id = '" + estado_id + "'").getResultList();
		
		for (Estados estado : estados) {
			selectItems.add(new SelectItem(estado.getId(), estado.getNome())); //id do objeto, label
		}
		
		return selectItems;
	}
	*/

}
