package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Cliente;
import repository.ClienteDAO;
import util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter {

	private ClienteDAO repository;

	public ClienteConverter() {
		this.repository = CDIServiceLocator.getBean(ClienteDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Cliente retorno = null;

		if (value != null) {
			retorno = repository.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {

			Cliente c = (Cliente) value;
			return c.getIdCliente() == null ? null : c.getIdCliente().toString();
		}
		return "";
	}

}