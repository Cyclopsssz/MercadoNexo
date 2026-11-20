package cl.mercadonexo.mercadonexo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/probar-jpa")
public class PruebaConexionServlet extends HttpServlet {

    @PersistenceContext(unitName = "MercadoNexoPU")
    private EntityManager em;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        response.setContentType("text/plain;charset=UTF-8");

        try {
            Object resultado = em.createNativeQuery(
                    "SELECT DB_NAME()"
            ).getSingleResult();

            response.getWriter().println(
                    "Conexion JPA correcta!"
            );

            response.getWriter().println(
                    "Base de datos: " + resultado
            );

            Long categorias = em.createQuery(
                    "SELECT COUNT(c) FROM Categoria c", Long.class
            ).getSingleResult();

            Long productos = em.createQuery(
                    "SELECT COUNT(p) FROM Producto p", Long.class
            ).getSingleResult();

            Long relaciones = em.createQuery(
                    "SELECT COUNT(p) FROM Producto p JOIN p.categoria c",
                    Long.class
            ).getSingleResult();

            response.getWriter().println(
                    "Categorias registradas: " + categorias
            );

            response.getWriter().println(
                    "Productos registrados: " + productos
            );

            response.getWriter().println(
                    "Productos con categoria: " + relaciones
            );

        } catch (Exception e) {
            response.setStatus(500);
            getServletContext().log("Error al probar JPA", e);
            response.getWriter().println(
                    "Error de conexion JPA. Revisa el log de GlassFish."
            );
        }
    }
}
