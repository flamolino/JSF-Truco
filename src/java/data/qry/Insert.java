package data.qry;

import bean.Usuario;
import data.Conn;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert {

    private PreparedStatement pstmt = null;
    private Conn conexao = null;
    private static final String SQL = "INSERT INTO usuario "
            + "(id, nome, login, senha, email, avatar, idade, duplaAtual, endereco)"
            + " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?);";
    private int registros;

    public boolean novoUsuario(Usuario user) throws SQLException {

        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(SQL);

        this.pstmt.setString(1, user.getNome());
        this.pstmt.setString(2, user.getLogin());
        this.pstmt.setString(3, user.getSenha());
        this.pstmt.setString(4, user.getEmail());
        this.pstmt.setString(5, user.getAvatar());
        this.pstmt.setInt(6, user.getIdade());
        this.pstmt.setInt(7, user.getDuplaAtual());
        this.pstmt.setString(8, user.getEndereco());

        this.registros = this.pstmt.executeUpdate();
        closeConns();

        return this.registros == 1;

    }

    private void closeConns() throws SQLException {
        this.pstmt.close();
    }

}
