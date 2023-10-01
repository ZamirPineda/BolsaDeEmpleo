package bolsaEmpleo;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class BaseDeDatos {
    static Dao<Aspirante, Integer> tablaAspirantes;
    public static void main(String[] args) throws Exception {
        String url = "jdbc:h2:file:./bolsa_empleo";
        // Conectarnos con la base de datos
        ConnectionSource con = new JdbcConnectionSource(url);
        tablaAspirantes = DaoManager.createDao(con, Aspirante.class);

        // Crear la tabla
        TableUtils.createTable(tablaAspirantes);
        System.out.println("Tabla de aspirantes creada exitosamente!");
        con.close();
    }
}
