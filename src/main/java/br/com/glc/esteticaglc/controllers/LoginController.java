package br.com.glc.esteticaglc.controllers;

import br.com.glc.esteticaglc.entities.Usuario;
import br.com.glc.esteticaglc.services.LoginService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import lombok.Data;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@SessionScoped
@Data
public class LoginController implements Serializable {

    @Autowired
    private LoginService loginService;
    private Usuario usuario;
    private Usuario usuarioLogado;

    @PostConstruct
    public void init() {
        usuario = new Usuario();
    }

    public String usuarioFormatado() {
        return usuarioLogado.getNome().toUpperCase();
    }

    public void autenticar() {
        usuarioLogado = loginService.autenticar(usuario.getUsername(), usuario.getSenha());

        if (usuarioLogado == null) {
            Messages.addFlashGlobalError("Usuário ou Senha incorretos.");
            return;
        }

        Faces.redirect("/inicio/home.xhtml");
    }

    public void logout() {
        this.usuarioLogado = null;

        Faces.redirect("/publico/login.xhtml");
    }

    public boolean temPermissao(List<String> permissoes) {
        for (String tipoUsuario : permissoes) {
            if (usuarioLogado.getTipoUsuario().getDescricao().equals(tipoUsuario)) {
                return true;
            }
        }
        return false;
    }

    public static LoginController getInstance() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return (LoginController) facesContext.getExternalContext().getSessionMap().get("loginController");
    }

}