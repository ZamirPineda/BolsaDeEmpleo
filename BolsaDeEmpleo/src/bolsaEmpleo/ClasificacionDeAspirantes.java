package bolsaEmpleo;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LogBackendType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.logger.LoggerFactory;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/***
 * Bolsa de Empleo
 * Enunciado
 * Una bolsa de empleo, que ofrece el servicio de selección de personal a una empresa del sector financiero, requiere un programa que le permita administrar las hojas de
 * vida de los aspirantes a diferentes cargos en los que la empresa cliente tiene vacantes. De un aspirante queremos almacenar la cédula (llave primaria), el nombre completo,
 * la edad, la experiencia en años, la profesión y el teléfono.
 *
 * Para la bolsa de empleo es importante poder clasificar los mejores aspirantes de acuerdo a unos criterios previamente definidos (años de experiencia, edad y profesión).
 *
 * La aplicación debe permitir:
 *
 * 1. Agregar nuevas hojas de vida de aspirantes.
 * 2. Mostrar las cédulas de los aspirantes.
 * 3. Mostrar la información detallada de un aspirante, a partir de la cédula.
 * 4. Buscar por nombre del aspirante.
 * 5. Permitir ordenar la lista de aspirantes por los diferentes criterios: años de experiencia, edad y profesión.
 * 6. Permitir consultar el aspirante con mayor experiencia.
 * 7. Permitir consultar el aspirante más joven.
 * 8. Contratar un aspirante (eliminarlo de la lista de aspirantes de la bolsa).
 * 9. Eliminar aquellos aspirantes cuya experiencia sea menor a una cantidad de años especificada.
 * 10. Presentar el promedio de edad de los aspirantes.
 *
 * @author Zamir Pineda
 * @version v0
 * @since 30/09/2023
 */

public class ClasificacionDeAspirantes {

    static Dao<Aspirante, Integer> tablaAspirantes;

    public static void main(String[] args) throws Exception {
        LoggerFactory.setLogBackendFactory(LogBackendType.NULL);
        Scanner teclado = new Scanner(System.in);
        String url = "jdbc:h2:file:./bolsa_empleo";
        // Conectarnos con la base de datos
        ConnectionSource con = new JdbcConnectionSource(url);
        // Configurar la tabla a traves de un DAO (Data Access Object)
        tablaAspirantes = DaoManager.createDao(con, Aspirante.class);

        int opcion;

        do {
            System.out.println("====== MENU =======");
            System.out.println("1. Agregar aspirante");
            System.out.println("2. Mostrar cédulas de aspirantes");
            System.out.println("3. Mostrar informacion detallada de un aspirante");
            System.out.println("4. Buscar por nombre del aspirante");
            System.out.println("5. Ordenar la lista de aspirantes");
            System.out.println("6. Consultar aspirante con mayor experiencia");
            System.out.println("7. Consultar aspirante más joven");
            System.out.println("8. Contratar un aspirante");
            System.out.println("9. Eliminar aspirantes con experiencia menor a ciertos años");
            System.out.println("10. Presentar promedio de edad de los aspirantes");
            System.out.println("0. Salir");

            System.out.print("Digite su opción: ");
            opcion = teclado.nextInt();

            switch (opcion) {
                case 1 -> agregarAspirante();
                case 2 -> mostrarCedulasAspirantes();
                case 3 -> mostrarInformacionAspirante();
                case 4 -> buscarPorNombre();
                case 5 -> ordenarLista();
                case 6 -> consultarMayorExperiencia();
                case 7 -> consultarMasJoven();
                case 8 -> contratarAspirante();
                case 9 -> eliminarPorExperiencia();
                case 10 -> presentarPromedioEdad();
                default -> System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (opcion != 0);
    }

    /***
     *
     */

    private static void agregarAspirante() throws SQLException {
        // 1. Agregar nuevas hojas de vida de aspirantes.
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite la cedula del aspirante: ");
        int cedula = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea pendiente

        System.out.print("Digite el nombre completo del aspirante: ");
        String nombreCompleto = scanner.nextLine();

        System.out.print("Digite la edad del aspirante: ");
        int edad = scanner.nextInt();

        System.out.print("Digite la experiencia en años del aspirante: ");
        int experiencia = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea pendiente

        System.out.print("Digite la profesion del aspirante: ");
        String profesion = scanner.nextLine();

        System.out.print("Digite el teléfono del aspirante: ");
        String telefono = scanner.nextLine();

        Aspirante aspirante = new Aspirante(cedula, nombreCompleto, edad, experiencia, profesion, telefono);

        tablaAspirantes.createOrUpdate(aspirante);

        System.out.println("Aspirante agregado exitosamente!");

        // Pausa la ejecucion durante 2.5 segundos
        pausarEjecucion(2500);
    }

    private static void mostrarCedulasAspirantes() throws SQLException {
        // 2. Mostrar las cédulas de los aspirantes.
        List<Aspirante> aspirantes = tablaAspirantes.queryForAll();

        System.out.println("Cédulas de los aspirantes:");
        for (Aspirante aspirante : aspirantes) {
            System.out.println(aspirante.getCedula());
        }

        // Pausa la ejecucion durante 4.5 segundos
        pausarEjecucion(4500);
    }

    private static void mostrarInformacionAspirante() throws SQLException {
        // 3. Mostrar la información detallada de un aspirante, a partir de la cédula.
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite la cédula del aspirante: ");
        int cedula = scanner.nextInt();

        Aspirante aspirante = tablaAspirantes.queryForId(cedula);

        if (aspirante != null) {
            System.out.println("Informacion del aspirante:");
            System.out.println("Cedula: " + aspirante.getCedula());
            System.out.println("Nombre completo: " + aspirante.getNombreCompleto());
            System.out.println("Edad: " + aspirante.getEdad());
            System.out.println("Experiencia en años: " + aspirante.getExp());
            System.out.println("Profesion: " + aspirante.getProfesion());
            System.out.println("Teléfono: " + aspirante.getTelefono());
        } else {
            System.out.println("No se encontró un aspirante con esa cédula.");
        }

        // Pausa la ejecucion durante 5 segundos
        pausarEjecucion(5000);
    }

    private static void buscarPorNombre() throws SQLException {
        // 4. Buscar por nombre del aspirante.
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite el nombre del aspirante: ");
        String nombre = scanner.nextLine();

        System.out.print("Desea buscar por el primer nombre(S), o cualquier coincidencia(N)? ");
        String busq = scanner.nextLine();

        List<Aspirante> aspirantes;

        if(busq.equalsIgnoreCase("S")){
            aspirantes = tablaAspirantes.queryBuilder()
                .where().like("nombreCompleto", nombre + "%").query();
        }else{
            aspirantes = tablaAspirantes.queryBuilder()
                    .where().like("nombreCompleto", "%" + nombre + "%").query();
        }

        if (!aspirantes.isEmpty()) {
            System.out.println("Aspirantes encontrados:");
            for (Aspirante aspirante : aspirantes) {
                System.out.println(aspirante);
            }
        } else {
            System.out.println("No se encontraron aspirantes con ese nombre.");
        }

        // Pausa la ejecucion durante 3.5 segundos
        pausarEjecucion(3500);
    }

    private static void ordenarLista() throws SQLException {
        // 5. Permitir ordenar la lista de aspirantes por los diferentes criterios: años de experiencia, edad y profesión.
        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleccione el criterio de ordenación:");
        System.out.println("1. Años de experiencia");
        System.out.println("2. Edad");
        System.out.println("3. Profesión");
        System.out.print("Ingrese su opción: ");
        int opcion = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Desea usar paso por referencia?(S/N) ");
        String ref = scanner.nextLine();

        List<Aspirante> aspirantes = tablaAspirantes.queryForAll();

        Comparator<Aspirante> comparador;

        if(ref.equalsIgnoreCase("S")){
            switch (opcion) {
                case 1 -> comparador = Comparator.comparingInt(Aspirante::getExp);
                case 2 -> comparador = Comparator.comparingInt(Aspirante::getEdad);
                case 3 -> comparador = Comparator.comparing(Aspirante::getProfesion);
                default -> {
                    System.out.println("Opción no válida. No se realizará la ordenación.");
                    return;
                }
            }
        }else{
            switch (opcion) {
                case 1 -> comparador = (a1, a2) -> Integer.compare(a1.getExp(), a2.getExp());
                case 2 -> comparador = (a1, a2) -> Integer.compare(a1.getEdad(), a2.getEdad());
                case 3 -> comparador = Comparator.comparing(a -> a.getProfesion());
                default -> {
                    System.out.println("Opción no válida. No se realizará la ordenación.");
                    return;
                }
            }
        }

        // Realizar la ordenacion utilizando el comparador seleccionado
        aspirantes.sort(comparador);

        // Mostrar la lista ordenada
        System.out.println("Lista de aspirantes ordenada:");
        for (Aspirante aspirante : aspirantes) {
            System.out.println(aspirante);
        }

        // Pausa la ejecucion durante 5 segundos
        pausarEjecucion(5000);
    }

    private static void consultarMayorExperiencia() throws SQLException {
        // 6. Permitir consultar el aspirante con mayor experiencia.
        List<Aspirante> aspirantes = tablaAspirantes.queryForAll();

        if (!aspirantes.isEmpty()) {
            // Ordenar la lista por experiencia en orden descendente
            aspirantes.sort(Comparator.comparingInt(Aspirante::getExp).reversed());

            // El aspirante en la posicion 0 despues de ordenar, tiene la mayor experiencia
            Aspirante aspiranteMayorExperiencia = aspirantes.get(0);

            System.out.println("Aspirante con mayor experiencia:");
            System.out.println(aspiranteMayorExperiencia);
        } else {
            System.out.println("No hay aspirantes para consultar.");
        }

        // Pausa la ejecucion durante 5 segundos
        pausarEjecucion(5000);
    }

    private static void consultarMasJoven() throws SQLException {
        // 7. Permitir consultar el aspirante más joven.
        List<Aspirante> aspirantes = tablaAspirantes.queryForAll();

        if (!aspirantes.isEmpty()) {
            // Ordenar la lista por edad en orden ascendente
            aspirantes.sort(Comparator.comparingInt(Aspirante::getEdad));

            // El primer aspirante después de la ordenación tiene la menor edad
            Aspirante aspiranteMasJoven = aspirantes.get(0);

            System.out.println("Aspirante más joven:");
            System.out.println(aspiranteMasJoven);
        } else {
            System.out.println("No hay aspirantes para consultar.");
        }

        // Pausa la ejecucion durante 5 segundos
        pausarEjecucion(5000);
    }

    private static void contratarAspirante() throws SQLException {
        // 8. Contratar un aspirante (eliminarlo de la lista de aspirantes de la bolsa).
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la cédula del aspirante a contratar: ");
        int cedula = scanner.nextInt();

        // Buscar al aspirante por su cédula
        Aspirante aspiranteContratar = tablaAspirantes.queryForId(cedula);

        if (aspiranteContratar != null) {
            // Eliminar al aspirante de la lista (contratar)
            tablaAspirantes.deleteById(cedula);
            System.out.println("Aspirante contratado exitosamente.");
        } else {
            System.out.println("No se encontró un aspirante con esa cédula.");
        }

        // Pausa la ejecucion durante 5 segundos
        pausarEjecucion(5000);
    }

    private static void eliminarPorExperiencia() throws SQLException {
        // 9. Eliminar aquellos aspirantes cuya experiencia sea menor a una cantidad de años especificada.
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la cantidad mínima de años de experiencia: ");
        int experienciaMinima = scanner.nextInt();

        // Obtener la lista de aspirantes con experiencia menor a x años
        List<Aspirante> aspirantesEliminar = tablaAspirantes.queryBuilder()
                .where().le("exp", experienciaMinima).query(); // le.equals( <= )  :)

        if (!aspirantesEliminar.isEmpty()) {
            System.out.println("Aspirantes a eliminar:");
            for (Aspirante aspirante : aspirantesEliminar) {
                System.out.println(aspirante);
            }

            // Pregunta para saber si se elimina
            System.out.print("¿Esta seguro de que desea eliminar estos aspirantes?(S/N): ");
            String confirmacion = scanner.next();

            if (confirmacion.equalsIgnoreCase("S")) {
                // Eliminamos a los aspirantes de la lista
                for (Aspirante aspirante : aspirantesEliminar) {
                    tablaAspirantes.deleteById(aspirante.getCedula());
                }
                System.out.println("Aspirantes eliminados exitosamente.");
            } else {
                System.out.println("Eliminación cancelada por el usuario.");
            }
        } else {
            System.out.println("No hay aspirantes con experiencia menor a la especificada.");
        }

        // Pausa la ejecucion durante 5 segundos
        pausarEjecucion(5000);
    }

    private static void presentarPromedioEdad() throws SQLException {
        // 10. Presentar el promedio de edad de los aspirantes.
        Scanner scanner = new Scanner(System.in);

        List<Aspirante> aspirantes = tablaAspirantes.queryForAll();

        if (!aspirantes.isEmpty()) {
            // Calcular el promedio de edad
            double promedioEdad; //

            System.out.print("¿Desea usar la api de Stream?(S/N): ");
            String tipoSuma = scanner.next();
            if(tipoSuma.equalsIgnoreCase("S")){
                double sumaEdades = aspirantes.stream().mapToInt(Aspirante::getEdad).sum();
                promedioEdad = sumaEdades / aspirantes.size();
            }else{
                double sumaEdades = 0;
                for (Aspirante aspirante : aspirantes) {
                    sumaEdades += aspirante.getEdad();
                }
                promedioEdad = sumaEdades / aspirantes.size();
            }
            // Formatear el resultado a dos decimales
            DecimalFormat df = new DecimalFormat("#.##");
            String promedioFormateado = df.format(promedioEdad);
            System.out.println("Promedio de edad de los aspirantes: " + promedioFormateado);
        } else {
            System.out.println("No hay aspirantes para calcular el promedio de edad.");
        }

        // Pausa la ejecucion durante 3.5 segundos
        pausarEjecucion(3500);
    }

    private static void pausarEjecucion(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
