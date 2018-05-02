/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.qry;

import data.Conn;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author 04783714118
 */
public class Delete {

    public static final String DELETAR_DUPLA = "DELETE FROM dupla where id = ?";

    private PreparedStatement pstmt = null;
    private Conn conexao = null;

    public void deletarDupla(int id) throws SQLException {

        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(DELETAR_DUPLA);

        this.pstmt.setInt(1, id);

        pstmt.executeUpdate();

        closeConns();
    }

    private void closeConns() throws SQLException {
        this.pstmt.close();
    }

}
