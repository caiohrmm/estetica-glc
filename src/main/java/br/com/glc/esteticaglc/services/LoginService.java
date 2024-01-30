package br.com.glc.esteticaglc.services;

import br.com.glc.esteticaglc.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginService {

    @Autowired
    private UsuarioService usuarioService;

    public Usuario autenticar(String login, String senha) {
        return usuarioService.autenticar(login, senha);
    }

    public Usuario obterUsuario(Usuario usuario) {
        Usuario usuarioLogado = usuarioService.buscaPorCodigo(usuario.getCodigo());
        return usuarioLogado;
    }

}