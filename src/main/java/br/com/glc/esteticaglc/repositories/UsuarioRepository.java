package br.com.glc.esteticaglc.repositories;

import br.com.glc.esteticaglc.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager em;

    public Usuario autenticar(String username, String senha) {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
            Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);

            Md5Hash md5Hash = new Md5Hash(senha);
            criteriaQuery.where(
                    criteriaBuilder.equal(usuarioRoot.get("username"), username),
                    criteriaBuilder.equal(usuarioRoot.get("senha"), md5Hash.toHex())
            );

            TypedQuery<Usuario> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Usuario buscaPorCodigo(Long codigo) {
        Usuario usuario = em.find(Usuario.class, codigo);
        return usuario;
    }

    @Transactional
    public void salvar(Usuario usuario) {
        Md5Hash md5Hash = new Md5Hash(usuario.getSenha());
        usuario.setSenha(md5Hash.toHex());

        em.merge(usuario);
    }

    @Transactional
    public void editar(Usuario usuario) {
        Md5Hash md5Hash = new Md5Hash(usuario.getSenha());
        usuario.setSenha(md5Hash.toHex());

        em.merge(usuario);
    }

    @Transactional
    public void delete(Usuario usuario) {
        em.remove(em.contains(usuario) ? usuario : em.merge(usuario));
    }

}
