package br.com.cursojsf;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.dao.DaoGeneric;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;

@ManagedBean(name = "lancamentoBean")
@ViewScoped
public class LancamentoBean {
	
	private Lancamento lancamento = new Lancamento();
	private DaoGeneric<Lancamento> daoGeneric = new DaoGeneric<Lancamento>();
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	
	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuariologado");
		lancamento.setUsuario(pessoaUser);
		daoGeneric.salvar(lancamento);
		return ""; //null ou em branco, fica na mesma página -> outcome
	}
	
	public String novo() {
		lancamento = new Lancamento();
		return "";
	}
	
	public String remover() {
		
		return "";
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
