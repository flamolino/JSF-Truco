package data.qry;

import data.Conn;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {

    public static final String DELETAR_DUPLA = "DELETE FROM dupla where id = ?;";

    private PreparedStatement pstmt = null;
    private Conn conexao = null;

    public void deletarDupla(int id, int idLider) throws SQLException {

        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(DELETAR_DUPLA);
        this.pstmt.setInt(1, id);
        pstmt.executeUpdate();

        this.pstmt = conexao.getConexao().prepareStatement("UPDATE usuario SET duplaAtual = -1 WHERE id = ?;");
        this.pstmt.setInt(1, idLider);
        pstmt.executeUpdate();

        closeConns();
    }

    private void closeConns() throws SQLException {
        this.pstmt.close();
    }

}
