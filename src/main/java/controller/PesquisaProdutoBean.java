package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import filter.ProdutoFilter;
import model.Produto;
import repository.ProdutoDAO;
import util.UtilMensagens;

@Named
@ApplicationScoped
public class PesquisaProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Produto> produtosFiltrados;

	@Inject
	private ProdutoDAO repository;

	@Inject
	private ProdutoFilter filtro;

	@Inject
	private UtilMensagens messages;

	@Inject
	private Produto produtoSelecionado;

	public void excluirProduto() {
		repository.removerProduto(produtoSelecionado);
		produtosFiltrados.remove(produtoSelecionado);
		messages.info("Produto  " + produtoSelecionado.getCodigoProduto() + "  Excluido com Sucesso.");
	}

	public void pesquisarProduto() {
		produtosFiltrados = repository.produtosFiltrados(filtro);
	}

	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public ProdutoFilter getFiltro() {
		return filtro;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}
}