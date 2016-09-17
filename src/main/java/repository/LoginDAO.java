package repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Usuario;

public class LoginDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public Usuario Login(String username, String password) {

		em.getTransaction().begin();
		Query q = em.createQuery("select u from USUARIO as u where u.username = :usuario and u.password = :senha");

		q.setParameter("usuario", username);
		q.setParameter("senha", password);

		List<Usuario> usuario = q.getResultList();
		em.getTransaction().commit();

		if (usuario.isEmpty()) {
			return null;
		} else {
			return usuario.get(0);
		}
	}
}