package cl.mercadonexo.mercadonexo;

import cl.mercadonexo.mercadonexo.dao.CategoriaDAO;
import cl.mercadonexo.mercadonexo.modelo.Categoria;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/categorias")
public class CategoriaServlet extends HttpServlet {

    @EJB
    private CategoriaDAO categoriaDAO;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Preparar el mensaje para la vista
        if ("1".equals(request.getParameter("ok"))) {
            request.setAttribute(
                    "mensaje",
                    "Categoria guardada correctamente!"
            );
        }

        // Enviar la solicitud a la vista JSP
        request.getRequestDispatcher(
                "/WEB-INF/vistas/categorias.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");

        if (nombre == null || nombre.isBlank()
                || nombre.trim().length() > 100
                || (descripcion != null
                && descripcion.trim().length() > 255)) {

            response.sendError(400, "Datos de categoria invalidos");
            return;
        }

        Categoria categoria = new Categoria(
                nombre.trim(),
                descripcion == null ? "" : descripcion.trim()
        );

        categoriaDAO.guardar(categoria);

        response.sendRedirect(
                request.getContextPath() + "/categorias?ok=1"
        );
    }
}
