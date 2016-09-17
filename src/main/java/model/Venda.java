package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "VENDA")
public class Venda implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idVenda;
	private Date data;
	private BigDecimal valorTotal = BigDecimal.ZERO;

	private FormaPagamento formaPagamento;
	private Usuario vendedor;
	private Cliente cliente;

	private List<ItemCompra> itens = new ArrayList<ItemCompra>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(Long idVenda) {
		this.idVenda = idVenda;
	}

	@Column(nullable = false)
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	@ManyToOne
	@JoinColumn(name = "vendedor_id", nullable = false)
	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<ItemCompra> getItens() {

		return itens;
	}

	public void setItens(List<ItemCompra> itens) {
		this.itens = itens;
	}

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idVenda == null) ? 0 : idVenda.hashCode());
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
		Venda other = (Venda) obj;
		if (idVenda == null) {
			if (other.idVenda != null)
				return false;
		} else if (!idVenda.equals(other.idVenda))
			return false;
		return true;
	}

	public void recalcularValorTotal() {
		BigDecimal total = BigDecimal.ZERO;

		for (ItemCompra item : this.getItens()) {
			if (item.getProduto() != null && item.getProduto().getIdProduto() != null) {
				total = total.add(item.getvalorTotal());
			}
		}

		this.setValorTotal(total);
	}

	public void adicionarItemVazio() {
		Produto p = new Produto();

		ItemCompra item = new ItemCompra();
		item.setProduto(p);
		item.setVenda(this);

		this.getItens().add(0, item);
	}

	public void removerItemVazio() {
		ItemCompra primeiroItem = this.getItens().get(0);
		if (primeiroItem != null && primeiroItem.getProduto().getIdProduto() == null) {
			getItens().remove(0);
		}
	}
}