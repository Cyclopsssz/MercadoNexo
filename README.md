\# MercadoNexo



Proyecto desarrollado para la evaluación EPE3 de la asignatura Desarrollo Web II, de la carrera Ingeniería en Informática.



\## Descripción



MercadoNexo es una aplicación web para administrar productos tecnológicos y sus categorías, utilizando Java, Jakarta EE, JPA y Microsoft SQL Server.



\## Tecnologías utilizadas



\- Java JDK 21

\- Apache NetBeans

\- Maven

\- Jakarta EE 10

\- Eclipse GlassFish

\- JPA / EclipseLink

\- JPQL

\- JSP

\- Microsoft SQL Server



\## Funcionalidades



\- Registrar productos.

\- Consultar productos registrados.

\- Editar productos existentes.

\- Eliminar productos.

\- Registrar categorías.

\- Relacionar productos con categorías.

\- Ejecutar consultas JPQL generales y parametrizadas.

\- Ejecutar operaciones UPDATE y DELETE masivas mediante JPQL.



\## Arquitectura



La aplicación utiliza el patrón Modelo-Vista-Controlador (MVC).



\- Modelo: entidades JPA y clases DAO.

\- Vista: archivos JSP para productos y categorías.

\- Controlador: Servlets que procesan las solicitudes HTTP.



\## Base de datos



El archivo database/MercadoNexoDB.sql contiene la estructura y los datos de ejemplo de las tablas Categoria y Producto.



Para preparar la base de datos:



1\. Crear una base de datos llamada MercadoNexoDB en SQL Server, si todavía no existe.

2\. Revisar y ejecutar el script SQL en esa base de datos.

3\. Configurar el acceso mediante autenticación SQL Server.

4\. Habilitar TCP/IP y el puerto correspondiente.



\## Configuración y ejecución



1\. Instalar JDK 21, Apache NetBeans, Maven, GlassFish y SQL Server.

2\. Abrir el proyecto Maven en NetBeans.

3\. Preparar la base de datos MercadoNexoDB.

4\. Configurar en GlassFish un pool JDBC llamado MercadoNexoPool con los datos de conexión locales.

5\. Crear el recurso JDBC jdbc/MercadoNexoDS asociado al pool.

6\. Comprobar la conexión desde GlassFish mediante Ping.

7\. Compilar el proyecto para generar el archivo WAR.

8\. Desplegar el WAR en GlassFish con el contexto /MercadoNexo.



Dirección local de la aplicación:



http://localhost:8080/MercadoNexo/



\## Módulos



\- /MercadoNexo/productos

\- /MercadoNexo/categorias

\- /MercadoNexo/prueba-jpql



\## Seguridad



Las credenciales de SQL Server deben configurarse localmente en GlassFish. No se incluyen contraseñas reales en este documento.



\## Evaluación



EPE3 - Desarrollo Web II



Proyecto académico MercadoNexo.

