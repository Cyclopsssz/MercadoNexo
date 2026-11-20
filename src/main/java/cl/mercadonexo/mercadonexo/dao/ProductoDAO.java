package cl.mercadonexo.mercadonexo.dao;

import cl.mercadonexo.mercadonexo.modelo.Producto;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductoDAO {

    @PersistenceContext(unitName = "MercadoNexoPU")
    private EntityManager em;

    // CREATE: registrar producto
    public void guardar(Producto producto) {
        em.persist(producto);
    }

    // READ: buscar producto por ID
    public Producto buscarPorId(Integer id) {
        return em.createQuery(
                "SELECT p FROM Producto p JOIN FETCH p.categoria "
                + "WHERE p.id = :id",
                Producto.class
        )
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    // READ: listar todos los productos
    public List<Producto> listar() {
        return em.createQuery(
                "SELECT p FROM Producto p JOIN FETCH p.categoria",
                Producto.class
        ).getResultList();
    }

    // UPDATE: actualizar producto
    public Producto actualizar(Producto producto) {
        return em.merge(producto);
    }

    // DELETE: eliminar producto
    public void eliminar(Integer id) {
        Producto producto = em.find(Producto.class, id);

        if (producto != null) {
            em.remove(producto);
        }
    }

    // JPQL: UPDATE masivo
    public int aumentarStockPruebas() {
        int filas = em.createQuery(
                "UPDATE Producto p "
                + "SET p.stock = p.stock + :cantidad "
                + "WHERE p.nombre IN :nombres"
        )
                .setParameter("cantidad", 5)
                .setParameter("nombres", List.of(
                        "JPQL_PRUEBA_A", "JPQL_PRUEBA_B"
                ))
                .executeUpdate();

        em.clear();
        return filas;
    }

    // JPQL: DELETE masivo
    public int eliminarProductosPrueba() {
        int filas = em.createQuery(
                "DELETE FROM Producto p "
                + "WHERE p.nombre IN :nombres"
        )
                .setParameter("nombres", List.of(
                        "JPQL_PRUEBA_A", "JPQL_PRUEBA_B"
                ))
                .executeUpdate();

        em.clear();
        return filas;
    }

}
