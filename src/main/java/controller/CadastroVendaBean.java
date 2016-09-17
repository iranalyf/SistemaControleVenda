package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import model.Cliente;
import model.FormaPagamento;
import model.ItemCompra;
import model.Produto;
import model.Usuario;
import model.Venda;
import repository.ClienteDAO;
import repository.ProdutoDAO;
import repository.UsuarioDAO;
import repository.VendaDAO;
import util.UtilMensagens;

@Named
@ApplicationScoped
public class CadastroVendaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDAO daoUsuario;

	@Inject
	private ClienteDAO daoCliente;

	@Inject
	private VendaDAO daoVenda;

	@Inject
	private ProdutoDAO daoProduto;

	@Inject
	private Venda venda;

	@Inject
	private Cliente cliente;

	@Inject
	private Usuario usuario;

	@Inject
	private Produto produto;

	@Inject
	private ItemCompra itens;

	@Inject
	private UtilMensagens messages;

	@Inject
	private Produto produtoLinhaEditavel;

	private List<Usuario> vendedores;
	private List<Cliente> clientes;
	private List<Produto> produtos;

	private String codigoProduto;

	public CadastroVendaBean() {
		limpar();
		getItens();
	}

	public void limpar() {
		this.venda = new Venda();
	}

	// metodo cadastrarVenda
	public void salvarVenda() {

		this.venda.removerItemVazio();

		try {
			this.daoVenda.cadastrarVenda(this.venda);
			messages.info("Venda Cadastrada com Sucesso");
			limpar();

		} finally {
			this.venda.adicionarItemVazio();
		}
	}

	public void carregarProdutoPorCodigo() {
		if (StringUtils.isNotBlank(codigoProduto)) {
			this.produtoLinhaEditavel = this.daoProduto.porCodigo(this.codigoProduto);
			carregarProdutoLinhaEditavel();
		}
	}

	public void atualizarQuantidadeProdutoVenda(ItemCompra item, int linha) {

		if (item.getQuantidade() < 1) {
			if (linha == 0) {
				item.setQuantidade(1);
			} else {
				item.getVenda().getItens().remove(linha);
			}
		}
		this.venda.recalcularValorTotal();
	}

	public void carregarProdutoLinhaEditavel() {
		ItemCompra item = this.venda.getItens().get(0);

		if (produtoLinhaEditavel != null) {
			if (this.exiteItemComProduto(this.produtoLinhaEditavel)) {
				messages.error("Ja exite um item na compra com o produto Informado");
			} else {

				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());

				this.venda.adicionarItemVazio();

				this.produtoLinhaEditavel = null;
				this.codigoProduto = null;

				this.venda.recalcularValorTotal();
			}
		}
	}

	private boolean exiteItemComProduto(Produto produto) {
		boolean existeItem = false;

		for (ItemCompra item : this.venda.getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}
		return existeItem;
	}

	public List<Produto> completarProduto(String nome) {

		return this.daoProduto.porNome(nome);
	}

	public void inicializar() {

		if (FacesContext.getCurrentInstance().isPostback() == false) {
			this.venda.adicionarItemVazio();

			this.venda.recalcularValorTotal();
		}
	}

	public void inicializarVendedores() {
		this.vendedores = daoUsuario.vendedores();
	}

	// carrega combo Cliente
	public void inicializarClientes() {
		this.clientes = daoCliente.clientes();
	}

	public FormaPagamento[] getFormaPagamento() {

		return FormaPagamento.values();
	}

	public List<Cliente> completarClientes(String nome) {
		return daoCliente.porNome(nome);
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Usuario> getVendedores() {
		return vendedores;
	}

	public void setVendedores(List<Usuario> vendedores) {
		this.vendedores = vendedores;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioDAO getDaoUsuario() {
		return daoUsuario;
	}

	public void setDaoUsuario(UsuarioDAO daoUsuario) {
		this.daoUsuario = daoUsuario;
	}

	public ClienteDAO getDaoCliente() {
		return daoCliente;
	}

	public void setDaoCliente(ClienteDAO daoCliente) {
		this.daoCliente = daoCliente;
	}

	public VendaDAO getDaoVenda() {
		return daoVenda;
	}

	public void setDaoVenda(VendaDAO daoVenda) {
		this.daoVenda = daoVenda;
	}

	public ProdutoDAO getDaoProduto() {
		return daoProduto;
	}

	public void setDaoProduto(ProdutoDAO daoProduto) {
		this.daoProduto = daoProduto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public ItemCompra getItens() {
		return itens;
	}

	public void setItens(ItemCompra itens) {
		this.itens = itens;
	}

	public UtilMensagens getMessages() {
		return messages;
	}

	public void setMessages(UtilMensagens messages) {
		this.messages = messages;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

}