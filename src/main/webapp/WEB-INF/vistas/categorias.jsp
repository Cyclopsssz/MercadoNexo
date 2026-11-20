<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>MercadoNexo - Categorías</title>

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
        <h2>Registrar categoría</h2>

        <p style="color: green;">${mensaje}</p>

        <form method="post"
              action="${pageContext.request.contextPath}/categorias">

            <label>Nombre:</label>
            <input type="text"
                   name="nombre"
                   maxlength="100"
                   required>

            <br><br>

            <label>Descripción:</label>
            <input type="text"
                   name="descripcion"
                   maxlength="255">

            <br><br>

            <button type="submit">
                Guardar categoría
            </button>

        </form>

    </body>
</html>
