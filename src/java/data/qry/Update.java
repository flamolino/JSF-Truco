package data.qry;

import data.Conn;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {

    private static final String SAIR_DA_DUPLA = "UPDATE dupla SET jogador = -1 WHERE id = ?;";
    private static final String ATUALIZAR_DUPLA_ATUAL = "UPDATE usuario SET duplaAtual = ? WHERE id = ?;";
    private static final String ATUALIZAR_LOGO_DUPLA = "UPDATE dupla SET logo = ? WHERE id = ?;";
    private static final String CONVIDA_PARCEIRO = "UPDATE usuario SET convite = ? WHERE id = ?;";
    private static final String ENTRAR_PARA_DUPLA = "UPDATE DUPLA SET JOGADOR = ? WHERE id = ?;";
    private static final String RECUSAR_CONVITE = "UPDATE usuario SET convite = -1 WHERE id = ?";
    private PreparedStatement pstmt = null;
    private Conn conexao = null;

    public void sairDaDupla(int idDupla, int idJogador) throws SQLException {

        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(SAIR_DA_DUPLA);
        this.pstmt.setInt(1, idDupla);
        pstmt.executeUpdate();

        this.pstmt = conexao.getConexao().prepareStatement("UPDATE usuario SET duplaAtual = -1 WHERE id =  ?;");
        this.pstmt.setInt(1, idJogador);
        pstmt.executeUpdate();

        closeConns();
    }

    public void recusarConvite(int idJogador) throws SQLException {

        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(RECUSAR_CONVITE);

        this.pstmt.setInt(1, idJogador);

        pstmt.executeUpdate();

        closeConns();
    }

    public void atualizarLogo(int id, String logo) throws SQLException {
        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(ATUALIZAR_LOGO_DUPLA);

        this.pstmt.setString(1, logo);
        this.pstmt.setInt(2, id);

        pstmt.executeUpdate();

        closeConns();
    }

    public void entrarParaDupla(int idUsuario, int idDupla) throws SQLException {
        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(ENTRAR_PARA_DUPLA);

        this.pstmt.setInt(1, idUsuario);
        this.pstmt.setInt(2, idDupla);

        pstmt.executeUpdate();

        closeConns();
    }

    public void convidaParceiro(int idLider, int idParceiro) throws SQLException {
        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(CONVIDA_PARCEIRO);

        this.pstmt.setInt(1, idLider);
        this.pstmt.setInt(2, idParceiro);

        pstmt.executeUpdate();

        closeConns();
    }

    public void atualizaDuplaAtual(int idDupla, int id) throws SQLException {
        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(ATUALIZAR_DUPLA_ATUAL);

        this.pstmt.setInt(1, idDupla);
        this.pstmt.setInt(2, id);

        pstmt.executeUpdate();

        closeConns();
    }

    private void closeConns() throws SQLException {
        if (this.pstmt != null) {
            this.pstmt.close();
        }
        this.conexao.close();
    }
}
