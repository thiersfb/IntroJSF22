package br.com.cursojsf;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;

import br.com.dao.DaoGeneric;
import br.com.entidades.Cidades;
import br.com.entidades.Estados;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;
import br.com.repository.IDaoPessoa;

@ViewScoped
@Named(value = "pessoaBean")
public class PessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa = new Pessoa();
	private List<Pessoa> pessoas  = new ArrayList<Pessoa>();
	
	@Inject
	private DaoGeneric<Pessoa> daoGeneric;
	@Inject
	private IDaoPessoa iDaoPessoa;
	
	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	
	@Inject
	private JPAUtil jpaUtil;
	
	private Part arquivofoto;
	
	public String salvar() throws IOException {
		
		if (arquivofoto != null) {
			
			/* Processar Imagem */
			byte[] imagemByte = getByte(arquivofoto.getInputStream());
			pessoa.setFotoIconBase64Original(imagemByte); /* Salva Imagem em tamanho original */
			
			/* Transformar em um bufferImage */
			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));
			
			/* Captura tipo da imagem*/
			int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
			
			int largura = 200;
			int altura = 200;
			
			/* Criar a miniatura da imagem */
			BufferedImage resizedImagem = new BufferedImage(largura, altura, type);
			Graphics2D g = resizedImagem.createGraphics();
			g.drawImage(bufferedImage, 0, 0, largura, altura, null);
			g.dispose();
			
			/* Escrever novamente a imagem em tamanho menor*/
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			String extensao = arquivofoto.getContentType().split("\\/")[1]; /* retorna image/png */
			String filename = arquivofoto.getSubmittedFileName().split("\\.")[0].toString();
			ImageIO.write(resizedImagem, extensao, baos);
			
			String miniImagem = "data: "+ arquivofoto.getContentType() + ";base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
		
			/* Processar Imagem */
		
			pessoa.setFotoIconBase64(miniImagem);
			pessoa.setExtensao(extensao);
			pessoa.setFilenameFoto(filename);
		
		}
		
		pessoa = daoGeneric.merge(pessoa);
		carregarListaPessoas();
		mostrarMsg("Cadastrado com sucesso");
		return ""; //null ou em branco, fica na mesma página -> outcome
	}
	
	public void registraLog() {
		System.out.println("Metodo registraLog");
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
	
	@SuppressWarnings("unchecked")
	public String editar() {
		
		if(pessoa.getCidades() != null) {
			Estados estado = pessoa.getCidades().getEstados();
			pessoa.setEstados(estado);
			
			List<Cidades> cidades = jpaUtil.getEntityManager().createQuery(" from TBCidades where estados_id = '" + estado.getId() + "'").getResultList();
						
			List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();
			for (Cidades cidade : cidades) {
				selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
			}
			setCidades(selectItemsCidade);
			
		}
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
		
		
		return pessoaUser.getPerfilUser().equals(acesso);
	}
	
	public void mudancaDeValor(ValueChangeEvent event) {
		System.out.println("Valor Antigo: " + event.getOldValue());
		System.out.println("Valor Antigo: " + event.getNewValue());
	}
	
	public List<SelectItem> getEstados() {
		estados = iDaoPessoa.listaEstados();
		return estados;
	}
	
	
	@SuppressWarnings("unchecked")
	public void carregaCidades(AjaxBehaviorEvent event) {
		
		Estados estado = (Estados) ((HtmlSelectOneMenu) event.getSource()).getValue();
		
			
		if(estado != null) {
			pessoa.setEstados(estado);

			List<Cidades> cidades = jpaUtil.getEntityManager().createQuery(" from TBCidades where estados_id = '" + estado.getId() + "'").getResultList();
			//List<Cidades> cidades = JPAUtil.getEntityManager().createQuery(" from " + Cidades.class.getAnnotation(null).toString() + " where estados_id = '" + estado_id + "'").getResultList();
							
			List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();
			for (Cidades cidade : cidades) {
				selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
			}
			setCidades(selectItemsCidade);
		}
			 
	}
	
	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}
	
	public List<SelectItem> getCidades() {
		return cidades;
	}
	
	public void setArquivofoto(Part arquivofoto) {
		this.arquivofoto = arquivofoto;
	}
	
	public Part getArquivofoto() {
		return arquivofoto;
	}
	
	/* Método que converte InputStream para Array de Bytes */
	private byte[] getByte(InputStream is) throws IOException {
		
		int len;
		int size = 1024;
		byte[] buffer = null;
		
		if(is instanceof ByteArrayInputStream) {
			size = is.available() ;
			buffer = new byte [size];
			len = is.read(buffer, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buffer = new byte[size];
			while((len = is.read(buffer, 0, size)) != -1) {
				bos.write(buffer, 0, len);
			}
			buffer = bos.toByteArray();
		}
		
		return buffer;
	}
	
	public void download() throws IOException {
 		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String fileDownloadId = params.get("fileDownloadId");
		
		Pessoa pessoa = daoGeneric.consultar(Pessoa.class, fileDownloadId);
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-Disposition", "attachment; filename=" + pessoa.getFilenameFoto() + "."+ pessoa.getExtensao());
		response.setContentType("application/octet-stream");
		response.setContentLength(pessoa.getFotoIconBase64Original().length);
		response.getOutputStream().write(pessoa.getFotoIconBase64Original());
		response.getOutputStream().flush();
		FacesContext.getCurrentInstance().responseComplete();
	}
}
