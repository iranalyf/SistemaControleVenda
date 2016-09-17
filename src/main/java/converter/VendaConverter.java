package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Venda;
import repository.VendaDAO;
import util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Venda.class)
public class VendaConverter implements Converter {

	private VendaDAO repository;

	public VendaConverter() {
		this.repository = CDIServiceLocator.getBean(VendaDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Venda retorno = null;

		if (value != null) {
			Long id = new Long(value);
			retorno = repository.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {

			Venda v = (Venda) value;
			return v.getIdVenda() == null ? null : v.getIdVenda().toString();
		}

		return "";
	}
}
