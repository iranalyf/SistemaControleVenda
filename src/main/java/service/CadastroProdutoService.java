package service;

import java.io.Serializable;

import javax.inject.Inject;

import model.Produto;
import repository.ProdutoDAO;

public class CadastroProdutoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoDAO repository;

	public Produto salvar(Produto produto) {

		Produto produtoExistente = repository.porCodigo(produto.getCodigoProduto());

		if (produtoExistente != null && !produtoExistente.equals(produto)) {
			throw new NegocioException("Ja existe um produto com esse Código na Lista");
		}

		return null;
	}
}