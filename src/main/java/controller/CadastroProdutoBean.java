package controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Produto;
import repository.ProdutoDAO;
import util.UtilMensagens;

@Named
@RequestScoped
public class CadastroProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoDAO repository;

	@Inject
	private UtilMensagens messages;

	@Inject
	private Produto produto;

	public void cadastrarProduto() {
		repository.guardar(produto);
		messages.info("Produto Cadastrado com Sucesso");
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public boolean isEditando() {
		return this.produto.getIdProduto() != null;
	}
}