package br.com.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Table;

import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;

public class IDaoPessoaImplement implements IDaoPessoa {

	@Override
	public Pessoa consultarUsuario(String login, String senha) {
		
		Pessoa pessoa = null;
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		//pessoa = (Pessoa) entityManager.createQuery(" select p from TBUsuario p where p.login = '" + login + "' and p.senha = '" + senha + "'").getSingleResult();
		pessoa = (Pessoa) entityManager.createQuery(" select p from " + Lancamento.class.getAnnotation(Table.class).name().toLowerCase() + " p where p.login = '" + login + "' and p.senha = '" + senha + "'").getSingleResult();
		
		entityTransaction.commit();
		entityManager.close();
		
		return pessoa;
	}

}
