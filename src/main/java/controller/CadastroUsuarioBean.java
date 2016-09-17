package controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Usuario;
import repository.UsuarioDAO;
import util.UtilMensagens;

@Named
@RequestScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuario usuario;

	@Inject
	private UsuarioDAO repository;

	@Inject
	private UtilMensagens messages;

	public void cadastrarUsuario() {

		repository.cadastrarUsuario(usuario);
		messages.info("Usuario Cadastrado com Sucesso");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
