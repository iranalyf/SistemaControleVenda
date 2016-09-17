package security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import model.Usuario;
import repository.UsuarioDAO;
import util.cdi.CDIServiceLocator;

public class AppUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UsuarioDAO repository = CDIServiceLocator.getBean(UsuarioDAO.class);

		UsuarioSistema user = null;

		Usuario usuario = repository.porUsername(username);

		if (usuario != null) {

			user = new UsuarioSistema(usuario, getGrupos(usuario));
		}

		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {

		List<SimpleGrantedAuthority> grupo = new ArrayList<>();

		grupo.add(new SimpleGrantedAuthority(usuario.getUsername().toUpperCase()));

		return grupo;
	}
}