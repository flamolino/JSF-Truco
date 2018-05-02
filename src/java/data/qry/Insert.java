package data.qry;

import bean.Dupla;
import bean.Usuario;
import data.Conn;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert {

    private PreparedStatement pstmt = null;
    private Conn conexao = null;
    private static final String NOVO_USUARIO = "INSERT INTO usuario "
            + "(id, nome, login, senha, email, avatar, idade, duplaAtual, endereco, dataCriacao, convite)"
            + " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String NOVA_DUPLA = "INSERT INTO dupla "
            + "(id, nome, logo, jogadorLider, jogador, dataCriacao)"
            + " VALUES (null, ?, ?, ?, ?, ?)";
    private int registros;

    public boolean novoUsuario(Usuario user) throws SQLException {

        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(NOVO_USUARIO);

        this.pstmt.setString(1, user.getNome());
        this.pstmt.setString(2, user.getLogin());
        this.pstmt.setString(3, user.getSenha());
        this.pstmt.setString(4, user.getEmail());
        this.pstmt.setString(5, user.getAvatar());
        this.pstmt.setInt(6, user.getIdade());
        this.pstmt.setInt(7, user.getDuplaAtual());
        this.pstmt.setString(8, user.getEndereco());
        this.pstmt.setString(9, user.getData());
        this.pstmt.setInt(10, user.getConvite());

        this.registros = this.pstmt.executeUpdate();
        closeConns();

        return this.registros == 1;

    }

    public boolean novaDupla(Dupla dupla) throws SQLException {
        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(NOVA_DUPLA);

        this.pstmt.setString(1, dupla.getNome());
        this.pstmt.setString(2, dupla.getLogo());
        this.pstmt.setInt(3, dupla.getJogadorLider());
        this.pstmt.setInt(4, dupla.getJogador());
        this.pstmt.setString(5, dupla.getData());

        this.registros = this.pstmt.executeUpdate();
        closeConns();

        return this.registros == 1;
    }

    private void closeConns() throws SQLException {
        this.pstmt.close();
    }

}
