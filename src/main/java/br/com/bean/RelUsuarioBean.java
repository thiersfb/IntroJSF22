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
import br.com.entidades.Pessoa;
import br.com.repository.IDaoLancamento;
import br.com.repository.IDaoPessoa;

@ViewScoped
@Named(value = "relUsuarioBean")
public class RelUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date dataIni;
	private Date dataFim;
	private String nome;
	private String cpf;
	
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	@Inject
	private IDaoPessoa daoPessoa;
	@Inject
	private DaoGeneric<Pessoa> daoGeneric;
	
	
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void buscarPessoas() {
		
		
		if(dataIni == null && dataFim == null && (nome == null || nome.isEmpty()) && (cpf == null || cpf.isEmpty())) {
			//System.out.println("tudo vazio");
			pessoas = daoGeneric.getListEntity(Pessoa.class);			
		} else {
			//System.out.println("filtros inseridos");
			pessoas = daoPessoa.relatorioPessoa(nome, cpf, dataIni, dataFim);
		}
	}
	
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	
}
