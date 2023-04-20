package br.com.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Table;

import br.com.entidades.Estados;
import br.com.entidades.Pessoa;

@Named
public class IDaoPessoaImplement implements IDaoPessoa, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@Override
	public Pessoa consultarUsuario(String login, String senha) {
		
		Pessoa pessoa = null;
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		try {
			pessoa = (Pessoa) entityManager.createQuery(" select p from " + Pessoa.class.getAnnotation(Table.class).name() + " p where p.login = '" + login + "' and p.senha = '" + senha + "'").getSingleResult();			
		} catch (Exception e) { /* Tratamento se não encontrar o usuario com login e senha */
			System.out.println("Login e senha informados não encontrados na base de dados");
		}
		
		entityTransaction.commit();
		
		return pessoa;
	}

	@Override
	public List<SelectItem> listaEstados() {
		
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		
		List<Estados> estados = entityManager.createQuery(" from " + Estados.class.getAnnotation(Table.class).name()).getResultList();
		
		for (Estados estado : estados) {
			selectItems.add(new SelectItem(estado, estado.getNome()));
		}
		
		return selectItems;
	}
	
}
