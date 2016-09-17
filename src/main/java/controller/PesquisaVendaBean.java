package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import filter.VendaFilter;
import model.Venda;
import repository.VendaDAO;
import util.UtilMensagens;

@Named
@RequestScoped
public class PesquisaVendaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private VendaDAO repository;

	@Inject
	private VendaFilter filtro;

	@Inject
	private Venda venda;

	@Inject
	private UtilMensagens messages;

	private List<Venda> vendasFiltradas;

	public void excluirVenda() {
		
		this.repository.excluirVenda(venda);
		
		messages.info("Venda excluida com Sucesso.");
	}

	public List<Venda> getVendasFiltradas() {
		return vendasFiltradas;
	}

	public void pesquisarVenda() {
		vendasFiltradas = repository.vendasFiltradas(filtro);
	}

	public VendaFilter getFiltro() {

		return filtro;
	}

	public boolean isEditando() {
		return this.venda.getIdVenda() != null;
	}
}
