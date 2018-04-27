package data.qry;

import bean.Usuario;
import data.Conn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Select {

    private static final String AUTENTICAR_LOGIN = "select * from usuario where login = ? and senha = ?;";
    private static final String VERIFICAR_USUARIO_EXISTENTE = "select login from usuario where login = ?;";
    private Usuario user = null;
    private PreparedStatement pstmt = null;
    private Conn conexao = null;
    private ResultSet rs = null;

    public Usuario AutenticarLogin(String login, String senha) throws SQLException {

        try {
            this.user = new Usuario();
            this.conexao = new Conn();

            this.pstmt = conexao.getConexao().prepareStatement(AUTENTICAR_LOGIN);

            this.pstmt.setString(1, login);
            this.pstmt.setString(2, senha);

            this.rs = pstmt.executeQuery();

            if (this.rs != null) {

                if (this.rs.next()) {

                    this.user = new Usuario();
                    this.user.setId(this.rs.getInt("id"));
                    this.user.setNome(this.rs.getString("nome"));
                    this.user.setLogin(this.rs.getString("login"));
                    this.user.setSenha(this.rs.getString("senha"));
                    this.user.setEmail(this.rs.getString("email"));
                    this.user.setAvatar(this.rs.getString("avatar"));
                    this.user.setIdade(this.rs.getInt("idade"));
                    this.user.setDuplaAtual(this.rs.getInt("duplaAtual"));
                    this.user.setEndereco(this.rs.getString("endereco"));

                } else {
                    this.user.setId(-1);
                }
            } else {
                this.user.setId(-1);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO ao executar login! " + e.getMessage());
        }
        closeConns();
        return this.user;
    }

    public boolean verificaUsuarioExistente(String login) throws SQLException {

        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(VERIFICAR_USUARIO_EXISTENTE);

        this.pstmt.setString(1, login);

        this.rs = pstmt.executeQuery();

        String loginConsulta;
        if (this.rs != null) {
            if (this.rs.next()) {
                closeConns();
                return true;
            } else {
                return false;
            }

        } else {
            closeConns();
            return false;
        }

    }

    private void closeConns() throws SQLException {
        this.pstmt.close();
        this.rs.close();
    }

}
