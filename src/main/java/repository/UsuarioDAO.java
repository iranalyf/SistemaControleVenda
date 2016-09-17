package repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import model.Usuario;
import util.UtilMensagens;
import util.utilErros;

public class UsuarioDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@Inject
	private UtilMensagens messages;

	@Inject
	private Usuario usuario;

	// metodo cadastrar Usuario
	public boolean cadastrarUsuario(Usuario usuario) {

		try {
			em.getTransaction().begin();
			em.persist(usuario);
			em.getTransaction().commit();
			em.close();

			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive() == false) {
				em.getTransaction().begin();
			}
			em.getTransaction().rollback();
			messages.error("Falha ao Cadastrar o Usuario" + utilErros.getMensagemErro(e));
			return false;
		}
	}

	// metodo consultar vendedores
	public Usuario porId(Long idUsuario) {
		return em.find(Usuario.class, idUsuario);
	}

	public List<Usuario> vendedores() {
		return em.createQuery("from USUARIO", Usuario.class).getResultList();
	}

	// buscar username para logar
	public Usuario porUsername(String username) {
		Usuario usuario = null;
		try {
			usuario = this.em.createQuery("from USUARIO where lower(username) = :username", Usuario.class)
					.setParameter("username", username).getSingleResult();

		} catch (NoResultException e) {

			// nenhum usuario encontrado com o username informado
		}
		return usuario;
	}

	public Usuario getUsuario() {

		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}