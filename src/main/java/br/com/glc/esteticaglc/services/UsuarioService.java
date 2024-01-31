package br.com.glc.esteticaglc.services;

import br.com.glc.esteticaglc.controllers.LoginController;
import br.com.glc.esteticaglc.entities.Usuario;
import br.com.glc.esteticaglc.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario autenticar(String username, String senha) {
        return usuarioRepository.autenticar(username, senha);
    }

    public Usuario buscaPorCodigo(Long codigo) {
        return usuarioRepository.buscaPorCodigo(codigo);
    }

    public void salvar(Usuario usuario) {
        usuarioRepository.salvar(usuario);
    }

    public void editar(Usuario usuario) {
        usuarioRepository.editar(usuario);
    }

    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    public Usuario recuperarUsuario() {
        return usuarioRepository.buscaPorCodigo(
                LoginController.getInstance().getUsuario().getCodigo()
        );
    }
}