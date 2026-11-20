package cl.mercadonexo.mercadonexo;

import cl.mercadonexo.mercadonexo.dao.ProductoDAO;
import cl.mercadonexo.mercadonexo.modelo.Producto;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/prueba-jpql")
public class PruebaJPQLServlet extends HttpServlet {

    @EJB
    private ProductoDAO dao;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>MercadoNexo - Pruebas JPQL</h1>");

        String resultado = request.getParameter("resultado");
        String filasTexto = request.getParameter("filas");

        if (resultado != null && filasTexto != null) {
            try {
                int filas = Integer.parseInt(filasTexto);
                out.println("<p>Operacion " +
                    ("actualizar".equals(resultado) ? "UPDATE" :
                     "eliminar".equals(resultado) ? "DELETE" : "desconocida")
                    + ": " + filas + " filas afectadas.</p>");
            } catch (NumberFormatException e) {
                out.println("<p>Resultado no disponible.</p>");
            }
        }

        // Consulta general
        out.println("<h2>1. Consulta general</h2>");
        out.println("<ul>");

        for (Producto p : dao.listar()) {
            out.println("<li>ID " + p.getId()
                + " - " + escapar(p.getNombre())
                + " - Stock: " + p.getStock()
                + "</li>");
        }

        out.println("</ul>");

        // Consulta parametrizada
        out.println("<h2>2. Consulta con parametro</h2>");

        Producto encontrado = dao.buscarPorId(1);

        if (encontrado != null) {
            out.println("<p>ID 1: "
                + escapar(encontrado.getNombre()) + "</p>");
        } else {
            out.println("<p>Producto ID 1 no encontrado.</p>");
        }

        // UPDATE masivo
        out.println("<h2>3. UPDATE masivo</h2>");
        out.println("<form method='post'>");
        out.println("<input type='hidden' name='accion' "
            + "value='actualizar'>");
        out.println("<button type='submit'>"
            + "Aumentar stock de pruebas (+5)</button>");
        out.println("</form>");

        // DELETE masivo
        out.println("<h2>4. DELETE masivo</h2>");
        out.println("<form method='post' "
            + "onsubmit=\"return confirm('Eliminar pruebas?')\">");
        out.println("<input type='hidden' name='accion' "
            + "value='eliminar'>");
        out.println("<button type='submit'>"
            + "Eliminar productos de prueba</button>");
        out.println("</form>");

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws IOException {

        String accion = request.getParameter("accion");
        int filas;

        if ("actualizar".equals(accion)) {
            filas = dao.aumentarStockPruebas();
        } else if ("eliminar".equals(accion)) {
            filas = dao.eliminarProductosPrueba();
        } else {
            response.sendError(400, "Accion invalida");
            return;
        }

        response.sendRedirect(
            request.getContextPath()
            + "/prueba-jpql?resultado=" + accion
            + "&filas=" + filas
        );
    }

    private String escapar(String texto) {
        if (texto == null) return "";
        return texto.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}
