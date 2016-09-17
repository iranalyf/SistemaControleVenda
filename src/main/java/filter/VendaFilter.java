package filter;

import java.io.Serializable;
import java.util.Date;

public class VendaFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataVendaDe;
	private Date dataVendaAte;
	private String nomeVendedor;
	private String nomeCliente;

	public Date getDataVendaDe() {
		return dataVendaDe;
	}

	public void setDataVendaDe(Date dataVendaDe) {
		this.dataVendaDe = dataVendaDe;
	}

	public Date getDataVendaAte() {
		return dataVendaAte;
	}

	public void setDataVendaAte(Date dataVendaAte) {
		this.dataVendaAte = dataVendaAte;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

}
