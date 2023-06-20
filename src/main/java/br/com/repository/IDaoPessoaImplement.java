package br.com.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Table;

import br.com.entidades.Estados;
import br.com.entidades.Lancamento;
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

	@Override
	public List<Pessoa> relatorioPessoa(String nome, String cpf, Date dataIni, Date dataFim) {
		
		List<Pessoa> lista = new ArrayList<Pessoa>();
		
		//System.out.println("NumNF: "+ numNF + " | Data Inicial: " + dataIni + " | Data Final: " + dataFim);
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from " + Pessoa.class.getAnnotation(Table.class).name());
		
		if (dataIni != null || dataFim != null || nome != null || !nome.isEmpty() || cpf != null || !cpf.isEmpty()) {
			sql.append(" where ");
		}
		
		if((nome != null && !nome.isEmpty())) {
		   sql.append(" upper(nome) like '%" + nome.trim().toUpperCase() + "%' or upper(sobrenome) like '%" + nome.trim().toUpperCase() + "%'");
		   
		   if (dataIni != null || dataFim != null || (cpf != null && !cpf.isEmpty())) {
			   sql.append(" and ");
		   }
		}
		
		if((cpf != null && !cpf.isEmpty())) {
			   sql.append(" upper(cpf) like '%" + cpf.replace(".", "").replace("-","").trim().toUpperCase() + "%'");
			   
			   if (dataIni != null || dataFim != null) {
				   sql.append(" and ");
			   }
			}
		
		if(dataIni != null) {
			String strDataInicial = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
			sql.append(" dataNascimento >= '").append(strDataInicial).append("'");
			if ( dataFim != null) {
				sql.append(" and ");
			}
		}
		
		if(dataFim != null) {
			String strDataFinal = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			sql.append(" dataNascimento <= '").append(strDataFinal).append("'");
		}
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		lista = entityManager.createQuery(sql.toString()).getResultList();

		transaction.commit();
		
		return lista;
	}
	
}
