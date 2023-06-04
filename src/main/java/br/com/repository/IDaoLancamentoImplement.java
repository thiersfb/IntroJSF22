package br.com.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
		List<Lancamento> lista = new ArrayList<Lancamento>();
		
		//System.out.println("NumNF: "+ numNF + " | Data Inicial: " + dataIni + " | Data Final: " + dataFim);
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from " + Lancamento.class.getAnnotation(Table.class).name());
		
		if (dataIni != null || dataFim != null || numNF != null || !numNF.isEmpty()) {
			sql.append(" where ");
		}
		
		if((numNF != null && !numNF.isEmpty())) {
		   sql.append(" numeroNF = '" + numNF.trim() + "'");
		   
		   if (dataIni != null || dataFim != null) {
			   sql.append(" and ");
		   }
		}
		
		if(dataIni != null) {
			String strDataInicial = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
			sql.append(" dataInicial >= '").append(strDataInicial).append("'");
			if ( dataFim != null) {
				sql.append(" and ");
			}
		}
		
		if(dataFim != null) {
			String strDataFinal = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			sql.append(" dataFinal <= '").append(strDataFinal).append("'");
		}
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		lista = entityManager.createQuery(sql.toString()).getResultList();

		transaction.commit();
		
		return lista;
	}

}
