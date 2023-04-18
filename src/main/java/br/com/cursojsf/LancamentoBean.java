package br.com.cursojsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.dao.DaoGeneric;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoLancamento;

@ViewScoped
@Named(value = "lancamentoBean")
public class LancamentoBean  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Lancamento lancamento = new Lancamento();
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	
	@Inject
	private DaoGeneric<Lancamento> daoGeneric;
	@Inject
	private IDaoLancamento daoLancamento;
	
	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuariologado");
		lancamento.setUsuario(pessoaUser);
		//daoGeneric.salvar(lancamento);
		lancamento = daoGeneric.merge(lancamento);
		carregarListaLancamentos();
		return ""; //null ou em branco, fica na mesma página -> outcome
	}
	
	public String novo() {
		lancamento = new Lancamento();
		return "";
	}

	
	public String remove() {
		daoGeneric.deleteById(lancamento);
		novo();
		carregarListaLancamentos();
		return "";
	}
	
	@PostConstruct	//método será carregado após instanciado
	public void carregarListaLancamentos() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuariologado");
		
		lancamentos = daoLancamento.consultarLancamentos(pessoaUser.getId());
		//lancamentos = daoLancamento.consultarLancamentos();
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public DaoGeneric<Lancamento> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Lancamento> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	
	
	
}