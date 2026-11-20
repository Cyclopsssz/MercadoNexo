<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="cl.mercadonexo.mercadonexo.modelo.Categoria" %>
<%@ page import="cl.mercadonexo.mercadonexo.modelo.Producto" %>

<%!
    // Escapar los textos antes de mostrarlos en HTML
    private String h(Object valor) {
        if (valor == null) {
            return "";
        }
        return String.valueOf(valor)
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
%>

<%
    // Datos entregados por el controlador
    List<Categoria> categorias
            = (List<Categoria>) request.getAttribute("categorias");

    List<Producto> productos
            = (List<Producto>) request.getAttribute("productos");

    Producto editando
            = (Producto) request.getAttribute("editando");

    boolean esEdicion = editando != null;
    String contexto = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>MercadoNexo - Productos</title>

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: white;
                text-align: center;
                margin: 0;
                padding: 40px 20px;
            }

            h1, h2 {
                color: #1565c0;
            }

            h1 {
                margin-bottom: 30px;
            }

            h2 {
                margin-bottom: 25px;
            }

            input, select {
                padding: 8px;
                border: 1px solid #cccccc;
                border-radius: 5px;
                font-size: 14px;
            }

            button {
                background-color: #1976d2;
                color: white;
                border: none;
                border-radius: 5px;
                padding: 10px 16px;
                cursor: pointer;
                font-size: 14px;
            }

            button:hover {
                background-color: #0d47a1;
            }

            table {
                margin: 25px auto;
                border-collapse: collapse;
            }

            th, td {
                padding: 10px;
                border: 1px solid #cccccc;
            }

            th {
                background-color: #f5f5f5;
            }

            hr {
                border: none;
                border-top: 1px solid #dddddd;
                margin: 30px auto;
                max-width: 800px;
            }

            a {
                color: #1976d2;
            }
        </style>

    </head>
    <body>

        <h1>MercadoNexo</h1>

        <% if ("1".equals(request.getParameter("ok"))) { %>
        <p style="color:green">
            Producto guardado correctamente!
        </p>
        <% } %>

        <% if ("2".equals(request.getParameter("ok"))) { %>
        <p style="color:green">
            Producto actualizado correctamente!
        </p>
        <% } %>

        <% if ("3".equals(request.getParameter("ok"))) { %>
        <p style="color:green">
            Producto eliminado correctamente!
        </p>
        <% }%>

        <h2><%= esEdicion
                ? "Editar producto"
                : "Registrar producto"%></h2>

        <form method="post" action="<%= h(contexto)%>/productos">

            <input type="hidden" name="accion"
                   value="<%= esEdicion ? "actualizar" : "crear"%>">

            <% if (esEdicion) {%>
            <input type="hidden" name="id"
                   value="<%= editando.getId()%>">
            <% }%>

            <label>Nombre:</label>
            <input type="text"
                   name="nombre"
                   maxlength="150"
                   required
                   value="<%= esEdicion
                   ? h(editando.getNombre()) : ""%>">

            <br><br>

            <label>Categoría:</label>
            <select name="categoriaId" required>

                <% for (Categoria c : categorias) {%>
                <option value="<%= c.getId()%>"
                        <%= esEdicion
                        && editando.getCategoria().getId()
                                .equals(c.getId())
                        ? "selected" : ""%>>
                    <%= h(c.getNombre())%>
                </option>
                <% }%>

            </select>

            <br><br>

            <label>Precio:</label>
            <input type="number"
                   name="precio"
                   min="0"
                   max="9999999999.99"
                   step="0.01"
                   required
                   value="<%= esEdicion
                           ? h(editando.getPrecio().toPlainString())
                           : ""%>">

            <br><br>

            <label>Stock:</label>
            <input type="number"
                   name="stock"
                   min="0"
                   step="1"
                   required
                   value="<%= esEdicion
                   ? h(editando.getStock()) : ""%>">

            <br><br>

            <button type="submit">
                <%= esEdicion
                ? "Actualizar producto"
                : "Guardar producto"%>
            </button>
        </form>

        <% if (esEdicion) {%>
        <p>
            <a href="<%= h(contexto)%>/productos">
                Cancelar edición
            </a>
        </p>
        <% } %>

        <hr>

        <h2>Productos registrados</h2>

        <table border="1" cellpadding="8">
            <tr>
                <th>ID</th>
                <th>Producto</th>
                <th>Categoría</th>
                <th>Precio</th>
                <th>Stock</th>
                <th>Acciones</th>
            </tr>

            <% for (Producto p : productos) {%>
            <tr>
                <td><%= p.getId()%></td>
                <td><%= h(p.getNombre())%></td>
                <td><%= h(p.getCategoria().getNombre())%></td>
                <td><%= p.getPrecio()%></td>
                <td><%= p.getStock()%></td>
                <td>
                    <a href="<%= h(contexto)%>/productos?editar=<%= p.getId()%>">
                        Editar
                    </a>

                    <form method="post"
                          action="<%= h(contexto)%>/productos"
                          style="display:inline"
                          onsubmit="return confirm('¿Eliminar este producto?');">

                        <input type="hidden"
                               name="accion"
                               value="eliminar">

                        <input type="hidden"
                               name="id"
                               value="<%= p.getId()%>">

                        <button type="submit">Eliminar</button>
                    </form>
                </td>
            </tr>
            <% } %>

        </table>

        <% if (productos.isEmpty()) { %>
        <p>No hay productos registrados.</p>
        <% }%>

    </body>
</html>
