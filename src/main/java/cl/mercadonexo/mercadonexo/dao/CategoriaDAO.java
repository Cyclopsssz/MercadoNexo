package cl.mercadonexo.mercadonexo.dao;

import cl.mercadonexo.mercadonexo.modelo.Categoria;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CategoriaDAO {

    @PersistenceContext(unitName = "MercadoNexoPU")
    private EntityManager em;

    public void guardar(Categoria categoria) {
        em.persist(categoria);
    }

    public Categoria buscarPorId(Integer id) {
        return em.find(Categoria.class, id);
    }

    public List<Categoria> listar() {
        return em.createQuery(
            "SELECT c FROM Categoria c", Categoria.class
        ).getResultList();
    }

    public Categoria actualizar(Categoria categoria) {
        return em.merge(categoria);
    }

    public void eliminar(Integer id) {
        Categoria categoria = em.find(Categoria.class, id);

        if (categoria != null) {
            em.remove(categoria);
        }
    }
}
