package model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity(name = "ITEM_COMPRA")
public class ItemCompra implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idItemCompra;
	private Integer quantidade = 1;
	private BigDecimal valorUnitario = BigDecimal.ZERO;

	private Produto produto;
	private Venda venda;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdItemCompra() {
		return idItemCompra;
	}

	public void setIdItemCompra(Long idItemCompra) {
		this.idItemCompra = idItemCompra;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	@Transient
	@ManyToOne
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@ManyToOne
	@JoinColumn(name = "venda_id")
	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idItemCompra == null) ? 0 : idItemCompra.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemCompra other = (ItemCompra) obj;
		if (idItemCompra == null) {
			if (other.idItemCompra != null)
				return false;
		} else if (!idItemCompra.equals(other.idItemCompra))
			return false;
		return true;
	}

	@Transient
	public boolean isEstoqueInsuficiente() {
		return this.produto.getIdProduto() != null && getProduto().getEstoque() <= getQuantidade();
	}

	@Transient
	public BigDecimal getvalorTotal() {

		return this.getValorUnitario().multiply(new BigDecimal(this.getQuantidade()));
	}

	@Transient
	public boolean isProdutoAssociado() {
		return this.getProduto() != null && this.getProduto().getIdProduto() != null;
	}

}
