package filter;

import java.io.Serializable;

public class ProdutoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigoProduto;
	private String nome;

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		// se for nulo etribua null, caso contrario passe para maiusculo
		this.codigoProduto = codigoProduto == null ? null : codigoProduto.toUpperCase();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
