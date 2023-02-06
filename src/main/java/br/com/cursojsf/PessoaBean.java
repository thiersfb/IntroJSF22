package br.com.cursojsf;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.dao.DaoGeneric;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoPessoa;
import br.com.repository.IDaoPessoaImplement;

@ManagedBean(name = "pessoaBean")
@ViewScoped
public class PessoaBean {
	
	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
	private List<Pessoa> pessoas  = new ArrayList<Pessoa>();
	
	private IDaoPessoa iDaoPessoa = new IDaoPessoaImplement();
	
	public String salvar() {
		//daoGeneric.salvar(pessoa);
		//pessoa = new Pessoa();
		pessoa = daoGeneric.merge(pessoa);
		carregarListaPessoas();
		return ""; //null ou em branco, fica na mesma página -> outcome
	}
	
	public String novo() {
		pessoa = new Pessoa();
		return "";
	}
	
	public String remove() {
		daoGeneric.deleteById(pessoa);
		novo();
		carregarListaPessoas();
		return "";
	}
	
	@PostConstruct	//método será carregado após instanciado
	public void carregarListaPessoas() {
		pessoas = daoGeneric.getListEntity(Pessoa.class);
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	
	public String logar() {
		
		//carregarPessoas();
		Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());
		
		if(pessoaUser != null) {
			
			//adicionar usuário na sessão usuariologado
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("usuariologado", pessoaUser);
			
			//HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
			//HttpSession session = req.getSession();
			//session.setAttribute("usuariologado", pessoaUser);
			
			return "primeirapagina.jsf";
		}
		return "index.jsf";
	}
	
	public boolean permiteAcesso(String acesso) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuariologado");
		
		//HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
		//HttpSession session = req.getSession();
		//Pessoa pessoaUser = (Pessoa) session.getAttribute("usuariologado");
		
		return pessoaUser.getPerfilUser().equals(acesso);
	}
	
}
