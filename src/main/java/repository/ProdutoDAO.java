package repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import filter.ProdutoFilter;
import model.Produto;
import service.NegocioException;
import util.UtilMensagens;

public class ProdutoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@Inject
	private UtilMensagens messages;

	// salvar a atualizar produto
	public boolean guardar(Produto produto) {
		try {
			em.getTransaction().begin();
			em.merge(produto);
			em.getTransaction().commit();

			return true;
		} catch (Exception e) {

			this.messages.error("Erro ao persistir o Objeto" + e.getMessage());
			return false;
		}
	}

	// excluirProduto
	public void removerProduto(Produto produto) {

		try {
			produto = porId(produto.getIdProduto());

			em.getTransaction().begin();
			em.remove(produto);
			em.getTransaction().commit();

		} catch (PersistenceException e) {
			throw new NegocioException("Produto não pode ser excluido..." + e.getMessage());
		}
	}

	// pesquisarProdutoPorCodigo
	public Produto porCodigo(String codigoProduto) {
		return em.createQuery("from PRODUTO where codigoProduto = :codigo", Produto.class)
				.setParameter("codigo", codigoProduto).getSingleResult();
	}

	// pesquisarProdutoecodigo
	@SuppressWarnings("unchecked")
	public List<Produto> produtosFiltrados(ProdutoFilter filtro) {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Produto.class);

		if (StringUtils.isNotBlank(filtro.getCodigoProduto())) {
			criteria.add(Restrictions.eq("codigoProduto", filtro.getCodigoProduto()));
		}

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		return criteria.addOrder(Order.asc("nome")).list();
	}

	public Produto porId(Long idProduto) {

		return em.find(Produto.class, idProduto);
	}

	@SuppressWarnings("unchecked")
	public List<Produto> porNome(String nome) {
		Session session = em.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Produto.class);

		if (StringUtils.isNotBlank(nome)) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.START));
		}
		return criteria.list();
	}
}