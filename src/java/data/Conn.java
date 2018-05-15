package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {

    private String path = System.getProperty("user.dir");
    private final String URL_WIN = "jdbc:sqlite:" + path + "\\database\\truco.db";
    private final String URL = "jdbc:sqlite:" + path + "/database/truco.db";
    private Connection conexao = null;

    public Conn() throws SQLException {

        if (this.conexao == null) {
            try {

                Class.forName("org.sqlite.JDBC");

                if (System.getProperty("os.name").contains("Windows")) {
                    this.conexao = DriverManager.getConnection(URL_WIN);
                } else {
                    this.conexao = DriverManager.getConnection(URL);
                }

            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("ERRO ao se conectar ao banco!");
            }

        }

    }

    public Connection getConexao() {

        return this.conexao;

    }

    public void close() throws SQLException {
        if (this.conexao != null) {
            this.conexao.close();
        }
    }

}
