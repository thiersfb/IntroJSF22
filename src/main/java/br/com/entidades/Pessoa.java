package br.com.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
//import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.constraints.br.TituloEleitoral;

@Entity(name = "TBUsuario")
//@Entity()
@Table(name = "TBUsuario")
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotEmpty(message = "Sobrenome deve ser informado")
	@Size(min = 3, max = 20, message = "Mínimo de 3 e máximo 20 caracteres no nome")
	private String nome;
	
	@NotNull(message = "Sobrenome deve ser informado")
	@NotEmpty(message = "Sobrenome deve ser informado")
	private String sobrenome;

	@DecimalMin(value = "10", message = "Idade deve ser maior que 10")
	@DecimalMax(value = "120", message = "Idade deve ser menor que 120")
	private Integer idade;

	@CPF(message="CPF Inválido")
	private String cpf;

	@TituloEleitoral(message="Título Eleitoral Inválido")
	private String tituloEleitoral;
	
	@Temporal(TemporalType.DATE)
	private Date dataNascimento = new Date();

	private String sexo;
	private String nivelExperiencia;
	private String[] frameworks;
	private Integer[] linguagens;
	private boolean status;

	@NotNull(message = "Login deve ser informado")
	@NotEmpty(message = "Login deve ser informado")
	private String login;
	
	@NotNull(message = "Senha deve ser informada")
	@NotEmpty(message = "Senha deve ser informada")
	private String senha;
	
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String localidade; //cidade
	private String uf;
	//private String unidade;
	private String ibge;
	private String gia;
	
	@Transient /* Não persiste dados no banco */
	private Estados estados;
	
	@ManyToOne
	private Cidades cidades;

	private String perfilUser;

	@Column(columnDefinition = "LONGBLOB") /* Tipo LONGBLOB grava arquivos base64 no MySQL */
	private String fotoIconBase64;
	
	@Lob /* Gravar arquivos no banco de dados */
	@Basic(fetch = FetchType.LAZY)
	private byte[] fotoIconBase64Original;
	
	private String extensao;
	private String filenameFoto;

	public Pessoa() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTituloEleitoral() {
		return tituloEleitoral;
	}

	public void setTituloEleitoral(String tituloEleitoral) {
		this.tituloEleitoral = tituloEleitoral;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNivelExperiencia() {
		return nivelExperiencia;
	}

	public void setNivelExperiencia(String nivelExperiencia) {
		this.nivelExperiencia = nivelExperiencia;
	}

	public Integer[] getLinguagens() {
		return linguagens;
	}

	public void setLinguagens(Integer[] linguagens) {
		this.linguagens = linguagens;
	}

	public String[] getFrameworks() {
		return frameworks;
	}

	public void setFrameworks(String[] frameworks) {
		this.frameworks = frameworks;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	/*
	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	*/

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	public String getGia() {
		return gia;
	}

	public void setGia(String gia) {
		this.gia = gia;
	}

	public Estados getEstados() {
		return estados;
	}

	public void setEstados(Estados estados) {
		this.estados = estados;
	}
	
	public Cidades getCidades() {
		return cidades;
	}
	
	public void setCidades(Cidades cidades) {
		this.cidades = cidades;
	}

	public String getPerfilUser() {
		return perfilUser;
	}

	public void setPerfilUser(String perfilUser) {
		this.perfilUser = perfilUser;
	}
	
	

	public String getFotoIconBase64() {
		return fotoIconBase64;
	}

	public void setFotoIconBase64(String fotoIconBase64) {
		this.fotoIconBase64 = fotoIconBase64;
	}

	public byte[] getFotoIconBase64Original() {
		return fotoIconBase64Original;
	}

	public void setFotoIconBase64Original(byte[] fotoIconBase64Original) {
		this.fotoIconBase64Original = fotoIconBase64Original;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}
	
	

	public String getFilenameFoto() {
		return filenameFoto;
	}

	public void setFilenameFoto(String filenameFoto) {
		this.filenameFoto = filenameFoto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
