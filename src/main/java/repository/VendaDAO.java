package repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import filter.VendaFilter;
import model.Usuario;
import model.Venda;
import model.vo.DataValor;
import service.NegocioException;
import util.UtilMensagens;
import util.utilErros;

public class VendaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@Inject
	private UtilMensagens messages;

	public boolean cadastrarVenda(Venda venda) {
		try {

			em.getTransaction().begin();
			em.merge(venda);
			em.getTransaction().commit();

			return true;

		} catch (Exception e) {

			if (em.getTransaction().isActive() == false) {
				em.getTransaction().begin();
			}

			em.getTransaction().rollback();
			throw new NegocioException(utilErros.getMensagemErro(e));
		}
	}

	@SuppressWarnings("unchecked")
	public List<Venda> vendasFiltradas(VendaFilter filtro) {

		Session session = em.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Venda.class).createAlias("cliente", "c").createAlias("vendedor",
				"v");

		if (filtro.getDataVendaDe() != null) {

			criteria.add(Restrictions.ge("data", filtro.getDataVendaDe()));
		}

		if (filtro.getDataVendaAte() != null) {

			criteria.add(Restrictions.le("data", filtro.getDataVendaAte()));
		}

		if (StringUtils.isNotBlank(filtro.getNomeVendedor())) {

			criteria.add(Restrictions.ilike("c.nomeVendedor", filtro.getNomeCliente(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getNomeCliente())) {

			criteria.add(Restrictions.ilike("v.nomeCompleto", filtro.getNomeCliente(), MatchMode.ANYWHERE));
		}
		return criteria.addOrder(Order.asc("idVenda")).list();
	}

	public void excluirVenda(Venda venda) {
		try {

			em.getTransaction().begin();
			em.remove(venda);
			em.getTransaction().commit();

		} catch (Exception e) {
			messages.error("Erro ao ecluir a Venda  " + utilErros.getMensagemErro(e));
		}
	}

	public Venda porId(Long idVenda) {
		em.find(Venda.class, idVenda);
		return null;
	}

	// Relatorio grafico todas as vendas
	@SuppressWarnings({ "unchecked" })
	public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDias, Usuario criadoPor) {

		Session session = em.unwrap(Session.class);

		numeroDias -= 1;
		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDias * -1);

		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDias, dataInicial);

		Criteria criteria = session.createCriteria(Venda.class);

		criteria.setProjection(Projections.projectionList()
				.add(Projections.sqlGroupProjection("date(data) as data", "date(data)", new String[] { "data" },
						new Type[] { StandardBasicTypes.DATE }))
				.add(Projections.sum("valorTotal").as("valor"))).add(Restrictions.ge("data", dataInicial.getTime()));

		if (criadoPor != null) {
			criteria.add(Restrictions.eq("vendedor", criadoPor));
		}

		List<DataValor> valoresPorData = criteria.setResultTransformer(Transformers.aliasToBean(DataValor.class))
				.list();

		for (DataValor dataValor : valoresPorData) {
			resultado.put(dataValor.getData(), dataValor.getValor());
		}
		return resultado;
	}

	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDias, Calendar dataInicial) {

		dataInicial = (Calendar) dataInicial.clone();
		Map<Date, BigDecimal> mapaInicial = new TreeMap<>();

		for (int i = 0; i <= numeroDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}
		return mapaInicial;
	}
}