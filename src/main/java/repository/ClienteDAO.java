package repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import model.Cliente;
import util.UtilMensagens;
import util.utilErros;

public class ClienteDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UtilMensagens messages;

	@Inject
	private EntityManager em;

	// metodo cadastrar Cliente
	public boolean cadastrarCliente(Cliente cliente) {

		try {
			em.getTransaction().begin();
			em.persist(cliente);
			em.getTransaction().commit();
			em.close();

			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive() == false) {
				em.getTransaction().begin();
			}
			em.getTransaction().rollback();
			messages.error("Falha ao Cadastrar Cliente " + utilErros.getMensagemErro(e));
			return false;
		}
	}

	// metodo buscarClientes
	public Cliente porId(Long idCliente) {
		return em.find(Cliente.class, idCliente);
	}

	public List<Cliente> clientes() {
		return em.createQuery("from CLIENTE", Cliente.class).getResultList();
	}

	public List<Cliente> porNome(String nome) {
		return em.createQuery("from CLIENTE where upper(nome) like :nome", Cliente.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}
}