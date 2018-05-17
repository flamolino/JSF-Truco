package data.qry;

import bean.Dupla;
import bean.Torneio;
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

    private static final String NOVO_TORNEIO = "INSERT INTO torneio "
            + "(id, limiteDuplas, nome, finalizado, descricao, dataCriacao, criador, dataEncerramentoInsc)"
            + " VALUES (null, ?, ?, ?, ?, ?, ?, ?);";

    private static final String INSCREVER_TORNEIO = "INSERT INTO inscritos_torneio "
            + "(id, idDupla, idTorneio, ordem) VALUES (null, ?, ?, ?);";

    private static final String INSERIR_CHAVE = "INSERT INTO chave "
            + "(id, idDupla1, idDupla2, fase, idTorneio, scoreDp1, scoreDp2)"
            + " VALUES (null, ?, ?, ?, ?, ?, ?);";

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

    public boolean inserirNaChave(int idDp1, int idDp2, int fase, int idTorneio) throws SQLException {

        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(INSERIR_CHAVE);

        this.pstmt.setInt(1, idDp1);
        this.pstmt.setInt(2, idDp2);
        this.pstmt.setInt(3, fase);
        this.pstmt.setInt(4, idTorneio);
        this.pstmt.setInt(5, 0);
        this.pstmt.setInt(6, 0);

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

    public boolean novoTorneio(Torneio torneio) throws SQLException {
        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(NOVO_TORNEIO);

        this.pstmt.setInt(1, torneio.getLimiteDuplas());
        this.pstmt.setString(2, torneio.getNome());
        this.pstmt.setInt(3, torneio.getFinalizado());
        this.pstmt.setString(4, torneio.getDescricao());
        this.pstmt.setString(5, torneio.getData());
        this.pstmt.setInt(6, torneio.getCriador());
        this.pstmt.setString(7, torneio.getDataEncerraInsc());

        this.registros = this.pstmt.executeUpdate();
        closeConns();

        return this.registros == 1;

    }

    public boolean inscreverNoTorneio(int idDupla, int idTorneio) throws SQLException {

        Select sel = new Select();
        int ordem = sel.getCountQry("select count(*) as c from inscritos_torneio where idTorneio = " + idTorneio + ";") + 1;

        this.conexao = new Conn();
        this.pstmt = this.conexao.getConexao().prepareStatement(INSCREVER_TORNEIO);

        this.pstmt.setInt(1, idDupla);
        this.pstmt.setInt(2, idTorneio);
        this.pstmt.setInt(3, ordem);

        this.registros = this.pstmt.executeUpdate();
        closeConns();
        return this.registros == 1;
    }

    private void closeConns() throws SQLException {
        if (this.pstmt != null) {
            this.pstmt.close();
        }
        this.conexao.close();
    }

}
