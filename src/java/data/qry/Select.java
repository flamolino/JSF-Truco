package data.qry;

import bean.Dupla;
import bean.Usuario;
import data.Conn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import outro.Utilities;

public class Select {

    private static final String AUTENTICAR_LOGIN = "select * from usuario where login = ? and senha = ?;";
    private static final String VERIFICAR_USUARIO_EXISTENTE = "select login from usuario where login = ?;";
    private static final String PEGA_USUARIO_POR_ID = "select * from usuario where id = ?;";

    private static final String VERIFICA_ESTA_OU_TEM_DUPLA = "select jogadorLider, jogador from dupla where jogadorLider = ? or jogador = ?";
    private static final String VERIFICAR_DUPLA_EXISTENTE = "select nome from dupla where nome = ?;";
    private static final String AUTENTICAR_DUPLA = "select * from dupla where jogadorLider = ? or jogador = ?;";

    private Usuario user = null;
    private Dupla dupla = null;
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

                    this.user = getConsultaUsuario(this.rs);

                } else {
                    this.user.setId(-1);
                }
            } else {
                this.user.setId(-1);
            }

        } catch (SQLException e) {

        }
        closeConns();
        return this.user;
    }

    public Dupla AutenticarDupla(int idLider, int idIntegrante) throws SQLException {

        try {
            this.dupla = new Dupla();
            this.conexao = new Conn();

            this.pstmt = conexao.getConexao().prepareStatement(AUTENTICAR_DUPLA);

            this.pstmt.setInt(1, idLider);
            this.pstmt.setInt(2, idIntegrante);

            this.rs = pstmt.executeQuery();

            if (this.rs != null) {

                if (this.rs.next()) {

                    this.dupla = getConsultaDupla(this.rs);

                } else {
                    this.dupla.setId(-1);
                }
            } else {
                this.dupla.setId(-1);
            }

        } catch (SQLException e) {

        }
        closeConns();
        return this.dupla;
    }

    public String getNomeDoLiderOuIntegrante(int id) throws SQLException {
        String nome = "";
        try {

            this.conexao = new Conn();

            this.pstmt = conexao.getConexao().prepareStatement(PEGA_USUARIO_POR_ID);

            this.pstmt.setInt(1, id);

            this.rs = pstmt.executeQuery();

            if (this.rs != null) {

                if (this.rs.next()) {
                    nome = this.rs.getString("nome");
                } else {
                    nome = "";
                }
            } else {
                nome = "Não Possui";
            }

        } catch (SQLException e) {

        }
        closeConns();
        return nome;
    }

    public boolean verificaUsuarioExistente(String login) throws SQLException {

        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(VERIFICAR_USUARIO_EXISTENTE);

        this.pstmt.setString(1, login);

        this.rs = pstmt.executeQuery();

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

    public boolean verificaDuplaExistente(String dupla) throws SQLException {

        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(VERIFICAR_DUPLA_EXISTENTE);

        this.pstmt.setString(1, dupla);

        this.rs = pstmt.executeQuery();

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

    public boolean verificaSeEstaOuSeTemDupla(int id) throws SQLException {
        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(VERIFICA_ESTA_OU_TEM_DUPLA);

        this.pstmt.setInt(1, id);
        this.pstmt.setInt(2, id);

        this.rs = pstmt.executeQuery();

        if (this.rs != null) {
            if (this.rs.next()) {
                closeConns();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    private void closeConns() throws SQLException {
        this.pstmt.close();
        this.rs.close();
    }

    private Usuario getConsultaUsuario(ResultSet rSet) throws SQLException {
        Usuario u = new Usuario();

        u.setId(rSet.getInt("id"));
        u.setNome(rSet.getString("nome"));
        u.setLogin(rSet.getString("login"));
        u.setSenha(rSet.getString("senha"));
        u.setEmail(rSet.getString("email"));
        u.setAvatar(rSet.getString("avatar"));
        u.setIdade(rSet.getInt("idade"));
        u.setDuplaAtual(rSet.getInt("duplaAtual"));
        u.setEndereco(rSet.getString("endereco"));
        u.setData(Utilities.DateToString(Utilities.StringToDate(rSet.getString("dataCriacao"))));

        return u;
    }

    private Dupla getConsultaDupla(ResultSet rSet) throws SQLException {
        Dupla d = new Dupla();

        d.setId(rSet.getInt("id"));
        d.setNome(rSet.getString("nome"));
        d.setLogo(rSet.getString("logo"));
        d.setJogadorLider(rSet.getInt("jogadorLider"));
        d.setJogador(rSet.getInt("jogador"));
        d.setData(Utilities.DateToString(Utilities.StringToDate(rSet.getString("dataCriacao"))));

        return d;
    }

}
