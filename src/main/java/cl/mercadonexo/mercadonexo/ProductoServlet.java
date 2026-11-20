package cl.mercadonexo.mercadonexo;

import cl.mercadonexo.mercadonexo.dao.CategoriaDAO;
import cl.mercadonexo.mercadonexo.dao.ProductoDAO;
import cl.mercadonexo.mercadonexo.modelo.Categoria;
import cl.mercadonexo.mercadonexo.modelo.Producto;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    @EJB
    private ProductoDAO productoDAO;

    @EJB
    private CategoriaDAO categoriaDAO;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Producto editando = null;
        String editarId = request.getParameter("editar");

        // Buscar producto si se solicita editar
        if (editarId != null) {
            try {
                Integer id = Integer.valueOf(editarId);

                if (id <= 0) {
                    response.sendError(400, "ID invalido");
                    return;
                }

                editando = productoDAO.buscarPorId(id);

            } catch (NumberFormatException e) {
                response.sendError(400, "ID invalido");
                return;
            }

            if (editando == null) {
                response.sendError(404, "Producto no encontrado");
                return;
            }
        }

        // Obtener datos desde el modelo
        List<Categoria> categorias = categoriaDAO.listar();
        List<Producto> productos = productoDAO.listar();

        // Entregar los datos a la vista
        request.setAttribute("categorias", categorias);
        request.setAttribute("productos", productos);
        request.setAttribute("editando", editando);

        // Mostrar el JSP
        request.getRequestDispatcher(
                "/WEB-INF/vistas/productos.jsp"
        ).forward(request, response);
    }

    private String escaparHtml(String texto) {
        if (texto == null) {
            return "";
        }

        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        if ("eliminar".equals(request.getParameter("accion"))) {

            try {
                Integer id = Integer.valueOf(
                        request.getParameter("id")
                );

                if (id <= 0) {
                    response.sendError(400, "ID invalido");
                    return;
                }

                Producto producto = productoDAO.buscarPorId(id);

                if (producto == null) {
                    response.sendError(404, "Producto no encontrado");
                    return;
                }

                productoDAO.eliminar(id);

                response.sendRedirect(
                        request.getContextPath() + "/productos?ok=3"
                );

            } catch (NumberFormatException e) {
                response.sendError(400, "ID invalido");
            }

            return;
        }

        try {
            String accion = request.getParameter("accion");
            String nombre = request.getParameter("nombre");

            Integer categoriaId = Integer.valueOf(
                    request.getParameter("categoriaId")
            );

            BigDecimal precio = new BigDecimal(
                    request.getParameter("precio")
            );

            Integer stock = Integer.valueOf(
                    request.getParameter("stock")
            );

            if (nombre == null || nombre.isBlank()
                    || nombre.trim().length() > 150
                    || precio.signum() < 0
                    || precio.scale() > 2
                    || precio.compareTo(
                            new BigDecimal("9999999999.99")) > 0
                    || stock < 0) {

                response.sendError(400, "Datos invalidos");
                return;
            }

            Categoria categoria
                    = categoriaDAO.buscarPorId(categoriaId);

            if (categoria == null) {
                response.sendError(400, "Categoria no encontrada");
                return;
            }

            if ("actualizar".equals(accion)) {

                Integer id = Integer.valueOf(
                        request.getParameter("id")
                );

                Producto producto = productoDAO.buscarPorId(id);

                if (producto == null) {
                    response.sendError(404, "Producto no encontrado");
                    return;
                }

                producto.setNombre(nombre.trim());
                producto.setCategoria(categoria);
                producto.setPrecio(precio);
                producto.setStock(stock);

                productoDAO.actualizar(producto);

                response.sendRedirect(
                        request.getContextPath() + "/productos?ok=2"
                );

            } else if ("crear".equals(accion)) {

                Producto producto = new Producto(
                        nombre.trim(), categoria, precio, stock
                );

                productoDAO.guardar(producto);

                response.sendRedirect(
                        request.getContextPath() + "/productos?ok=1"
                );

            } else {
                response.sendError(400, "Accion invalida");
            }

        } catch (NumberFormatException e) {
            response.sendError(400, "Datos numericos invalidos");
        }
    }
}
