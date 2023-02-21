package br.com.cursojsf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

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
		mostrarMsg("Cadastrado com sucesso");
		return ""; //null ou em branco, fica na mesma página -> outcome
	}
	
	private void mostrarMsg(String msg) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
		
	}

	public String novo() {
		pessoa = new Pessoa();
		return "";
	}
	
	public String remove() {
		daoGeneric.deleteById(pessoa);
		novo();
		carregarListaPessoas();
		mostrarMsg("Removido com sucesso");
		return "";
	}
	
	@PostConstruct	//método será carregado após instanciado
	public void carregarListaPessoas() {
		pessoas = daoGeneric.getListEntity(Pessoa.class);
	}
	
	public void pesquisaCep(AjaxBehaviorEvent event) {
		
		try {
			URL url = new URL("https://viacep.com.br/ws/" + pessoa.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			String cep = "";
			StringBuilder jsonCep = new StringBuilder();
			
			while ((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}
			
			Pessoa gsonAux = new Gson().fromJson(jsonCep.toString(), Pessoa.class);
			
			pessoa.setCep(gsonAux.getCep());
			pessoa.setLogradouro(gsonAux.getLogradouro());
			pessoa.setComplemento(gsonAux.getComplemento());
			pessoa.setBairro(gsonAux.getBairro());
			pessoa.setLocalidade(gsonAux.getLocalidade()); //localidade
			pessoa.setUf(gsonAux.getUf());
			pessoa.setIbge(gsonAux.getIbge());
			pessoa.setGia(gsonAux.getGia());
			//pessoa.setUnidade(gsonAux.getUnidade());
			
			//System.out.println(jsonCep);
			
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMsg("Erro ao consultar o cep");
		}
		
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
	

	public String logoff() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getSessionMap().remove("usuariologado");
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) context.getCurrentInstance().getExternalContext().getRequest();
		
		httpServletRequest.getSession().invalidate();
		
		return "index.jsf";
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
