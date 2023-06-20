package br.com.repository;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;

public interface IDaoPessoa {
	
	Pessoa consultarUsuario(String login, String senha);
	List<SelectItem> listaEstados();
	//List<SelectItem> listaCidades(String estado_id);

	List<Pessoa> relatorioPessoa(String nome, String cpf, Date dataIni, Date dataFim);
}
