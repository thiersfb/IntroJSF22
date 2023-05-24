package br.com.bean;

import java.io.Serializable;
import java.util.ArrayList;
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
	
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	
	@Inject
	private IDaoLancamento daoLancamento;
	@Inject
	private DaoGeneric<Lancamento> daoGeneric;

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	
	public void buscarLancamentos() {
		//System.out.println("Chamou o evento de busca");
		lancamentos = daoGeneric.getListEntity(Lancamento.class);
	}
	

}
