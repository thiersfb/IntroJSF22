package br.com.converter;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.entidades.Cidades;

@FacesConverter(forClass = Cidades.class, value = "cidadeConverter")
public class CidadeConverter implements Converter, Serializable {

	@Override /* Retorna objeto inteiro */
	public Object getAsObject(FacesContext context, UIComponent component, String cidade_id) {

		EntityManager entityManager = CDI.current().select(EntityManager.class).get();

		Cidades cidades = (Cidades) entityManager.find(Cidades.class, Long.parseLong(cidade_id));

		return cidades;
	}

	@Override /* Retorna apenas o ID em String */
	public String getAsString(FacesContext context, UIComponent component, Object cidade) {

		if (cidade == null) {
			return null;
		}
		
		if (cidade instanceof Cidades) {
			return ((Cidades) cidade).getId().toString();
		} else {
			return cidade.toString();
		}
		
	}

}
