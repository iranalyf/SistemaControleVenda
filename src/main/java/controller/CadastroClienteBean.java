package controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Cliente;
import repository.ClienteDAO;
import util.UtilMensagens;

@Named
@RequestScoped
public class CadastroClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Cliente cliente;

	@Inject
	private ClienteDAO repository;

	@Inject
	private UtilMensagens messages;

	public void cadastrarCliente() {
		repository.cadastrarCliente(cliente);
		messages.info("Cliente Cadastrado com Sucesso");
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
