package data.qry;

import bean.Dupla;
import bean.Usuario;
import bean.tabelas.ListaDeTorneios;
import data.Conn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import outro.Utilities;

public class Select {

    private static final String AUTENTICAR_LOGIN = "select * from usuario where login = ? and senha = ?;";
    private static final String VERIFICAR_USUARIO_EXISTENTE = "select * from usuario where login = ?;";
    private static final String PEGA_USUARIO_POR_ID = "select * from usuario where id = ?;";
    private static final String PEGA_USUARIO_POR_LOGIN = "select * from usuario where login = ?;";

    private static final String VERIFICA_ESTA_OU_TEM_DUPLA = "select * from dupla where jogadorLider = ? or jogador = ?";
    private static final String VERIFICAR_DUPLA_EXISTENTE = "select * from dupla where nome = ?;";
    private static final String AUTENTICAR_DUPLA = "select * from dupla where jogadorLider = ? or jogador = ?;";
    private static final String PEGA_DUPLA_POR_ID = "select * from dupla where jogadorLider = ?;";

    private static final String VERIFICAR_TORNEIO_EXISTENTE = "select * from torneio where nome = ?;";

    private static final String PEGA_LISTA_DE_TORNEIOS_EM_ATIVIDADE = "select T.nome as nome, count(I.id) as qtd, T.lim"
            + "iteDuplas as limite, T.descricao as descr, T.dataEncerramentoInsc as dataEnc from  torneio T left join inscritos_torneio I on T.id = I.idTorn"
            + "eio where T.finalizado <= 0 group by T.id order by qtd desc;";

    private Usuario user = null;
    private Dupla dupla = null;
    private PreparedStatement pstmt = null;
    private Conn conexao = null;
    private ResultSet rs = null;
    private ListaDeTorneios lstDTor = null;
    private ArrayList<ListaDeTorneios> listaDeTorneios = null;

    public ArrayList<ListaDeTorneios> getListaDeTorneiosAtualizada() throws SQLException {

        this.conexao = new Conn();
        this.pstmt = conexao.getConexao().prepareStatement(PEGA_LISTA_DE_TORNEIOS_EM_ATIVIDADE);

        this.rs = pstmt.executeQuery();

        if (this.rs != null) {
            this.listaDeTorneios = new ArrayList();
            while (this.rs.next()) {
                this.lstDTor = new ListaDeTorneios();
                this.lstDTor.setNome(this.rs.getString("nome"));
                this.lstDTor.setDescricao(this.rs.getString("descr"));
                this.lstDTor.setQtdInscritos(this.rs.getInt("qtd"));
                this.lstDTor.setLimiteDuplas(this.rs.getInt("limite"));
                this.lstDTor.setDataEncerraInsc(Utilities.DateToString(Utilities.StringToDate(this.rs.getString("dataEnc"))));

                if (this.lstDTor.getQtdInscritos() >= this.lstDTor.getLimiteDuplas()) {
                    this.lstDTor.setCheio(true);
                } else {
                    this.lstDTor.setCheio(false);
                }

                this.listaDeTorneios.add(lstDTor);
            }
        }

        return listaDeTorneios;
    }

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

    public String getLoginPorID(int id) throws SQLException {
        String nome = "";
        try {

            this.conexao = new Conn();

            this.pstmt = conexao.getConexao().prepareStatement(PEGA_USUARIO_POR_ID);

            this.pstmt.setInt(1, id);

            this.rs = pstmt.executeQuery();

            if (this.rs != null) {

                if (this.rs.next()) {
                    nome = this.rs.getString("login");
                }
            }

        } catch (SQLException e) {

        }
        closeConns();
        return nome;
    }

    public String getNomeDuplaPorLiderID(int id) throws SQLException {
        String nome = "";
        try {

            this.conexao = new Conn();

            this.pstmt = conexao.getConexao().prepareStatement(PEGA_DUPLA_POR_ID);

            this.pstmt.setInt(1, id);

            this.rs = pstmt.executeQuery();

            if (this.rs != null) {

                if (this.rs.next()) {

                    nome = this.rs.getString("nome");
                }
            }

        } catch (SQLException e) {

        }
        closeConns();
        return nome;
    }

    public int getIDPorLogin(String login) throws SQLException {
        int id = -1;
        try {

            this.conexao = new Conn();

            this.pstmt = conexao.getConexao().prepareStatement(PEGA_USUARIO_POR_LOGIN);

            this.pstmt.setString(1, login);

            this.rs = pstmt.executeQuery();

            if (this.rs != null) {

                if (this.rs.next()) {
                    id = this.rs.getInt("id");
                }
            }

        } catch (SQLException e) {

        }
        closeConns();
        return id;
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
                closeConns();
                return false;
            }

        } else {
            closeConns();
            return false;
        }
    }

    public boolean verificaTorneioExistente(String nome) throws SQLException {

        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(VERIFICAR_TORNEIO_EXISTENTE);

        this.pstmt.setString(1, nome);

        this.rs = pstmt.executeQuery();

        if (this.rs != null) {
            if (this.rs.next()) {
                closeConns();
                return true;
            }

        }
        closeConns();
        return false;
    }

    public int verificaUsuarioTemConvite(String login) throws SQLException {

        int convite = -1;
        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(PEGA_USUARIO_POR_LOGIN);

        this.pstmt.setString(1, login);

        this.rs = pstmt.executeQuery();

        if (this.rs != null) {
            if (this.rs.next()) {

                if (this.rs.getInt("convite") != -1) {
                    convite = this.rs.getInt("convite");
                    closeConns();
                    return convite;
                } else {
                    closeConns();
                    return convite;
                }
            } else {
                closeConns();
                return convite;
            }

        } else {
            closeConns();
            return convite;
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
                closeConns();
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
                closeConns();
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
        u.setConvite(rSet.getInt("convite"));

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
