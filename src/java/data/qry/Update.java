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
public class Update {

    private static final String SAIR_DA_DUPLA = "UPDATE dupla SET jogador = -1 WHERE id = ?";
    private PreparedStatement pstmt = null;
    private Conn conexao = null;

    public void sairDaDupla(int id) throws SQLException {

        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(SAIR_DA_DUPLA);

        this.pstmt.setInt(1, id);

        pstmt.executeUpdate();

        closeConns();
    }

    private void closeConns() throws SQLException {
        this.pstmt.close();
    }
}
