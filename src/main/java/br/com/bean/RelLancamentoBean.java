package br.com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.dao.DaoGeneric;
import br.com.entidades.Lancamento;
import br.com.repository.IDaoLancamento;

@ViewScoped
@Named(value = "relLancamentoBean")
public class RelLancamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataIni;
	private Date dataFim;
	
	private String numNF;
	
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	
	@Inject
	private IDaoLancamento daoLancamento;
	@Inject
	private DaoGeneric<Lancamento> daoGeneric;
	
	

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getNumNF() {
		return numNF;
	}

	public void setNumNF(String numNF) {
		this.numNF = numNF;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	
	public void buscarLancamentos() {
		//System.out.println("Chamou o evento de busca");
		System.out.println("NumNF " + numNF);
		System.out.println("Data Ini " + dataIni);
		System.out.println("Data Fim " + dataFim);
		
		if(dataIni == null && dataFim == null && (numNF == null || numNF.isEmpty())) {
			System.out.println("tudo vazio");
			lancamentos = daoGeneric.getListEntity(Lancamento.class);			
		} else {
			System.out.println("filtros inseridos");
			lancamentos = daoLancamento.relatorioLancamento(numNF, dataIni, dataFim);
		}
	}
	

}
