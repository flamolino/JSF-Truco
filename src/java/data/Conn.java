package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conn {

    private String path = System.getProperty("user.dir");
    private final String URL = "jdbc:sqlite:" + path + "\\database\\truco.db";
    private Connection conexao = null;

    public Conn() {
System.out.println(URL);
        if (conexao == null) {
            try {

                Class.forName("org.sqlite.JDBC");
                
                this.conexao = DriverManager.getConnection(URL);

            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, "ERRO ao se conectar ao banco!");
            }
        }

    }

    public Connection getConexao() {
        return this.conexao;

    }

}
