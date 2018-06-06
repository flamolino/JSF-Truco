package data.qry;

import bean.ChaveTorneio;
import bean.Dupla;
import bean.Torneio;
import bean.Usuario;
import bean.tabelas.ListaDeInscritosEmUmTorneio;
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
    private static final String PEGA_DUPLA_POR_LIDER = "select * from dupla where jogadorLider = ?;";
    private static final String PEGA_DUPLA_POR_ID = "select * from dupla where id = ?;";

    private static final String VERIFICAR_TORNEIO_EXISTENTE = "select * from torneio where nome = ?;";
    private static final String PEGA_TORNEIO_POR_ID = "select * from torneio where id = ?;";
    private static final String PEGA_LISTA_DE_TORNEIOS_EM_ATIVIDADE = "select T.nome as nome, count(I.id) as qtd, T.lim"
            + "iteDuplas as limite, T.descricao as descr, T.dataEncerramentoInsc as dataEnc from  torneio T left join inscritos_torneio I on T.id = I.idTorn"
            + "eio where T.finalizado <= 0 group by T.id order by qtd desc;";
    private static final String PEGA_LISTA_DE_INSCRITOS_EM_UM_TORNEIO = "select dupla.nome as duplas, dupla.id as iddupla, "
            + "inscritos_torneio.ordem as ordem, dupla.logo as logo from dupla, torneio, inscritos_torneio where torneio.id = ? and dupla.id = ins"
            + "critos_torneio.idDupla and torneio.id = inscritos_torneio.idTorneio group by inscritos_torneio.ordem;";
    private static final String VERIFICA_SE_JA_ESTA_INSCRITO_NO_TORNEIO = "select * from inscritos_torneio where idDupla = ? and idTorneio = ?;";

    private static final String PEGA_CHAVE_TORNEIO_POR_ID = "select * from chave where idTorneio = ?;";
    private static final String PEGA_ID_CHAVE_TORNEIO_POR_IDDUPLA1_E_FASE = "select id from chave where idDupla1 = ? and fase = ?;";
    private static final String PEGA_CHAVE_TORNEIO_POR_ID_E_FASE = "select * from chave where idTorneio = ? and fase = ?;";
    private static final String PEGA_CHAVE_TORNEIO_POR_ID_E_FASE_E_IDS = "select * from chave where idTorneio = ? and fase = ? and idDupla1 = ? and idDupla2 = ?;";

    private Usuario user = null;
    private Dupla dupla = null;
    private Torneio torneio = null;
    private PreparedStatement pstmt = null;
    private Conn conexao = null;
    private ResultSet rs = null;
    private ListaDeTorneios lstDTor = null;
    private ArrayList<ListaDeTorneios> listaDeTorneios = null;
    private ListaDeInscritosEmUmTorneio lstInscrTor = null;
    private ArrayList<ListaDeInscritosEmUmTorneio> listaDeInscritosEmUmTorneio = null;
    private ChaveTorneio chaveTorneio = null;
    private ArrayList<ChaveTorneio> listaChaveTor = null;

    public int getIDChaveTorneio(int idDupla1, int fase) throws SQLException {
        int idChave = -1;
        try {

            this.conexao = new Conn();

            this.pstmt = conexao.getConexao().prepareStatement(PEGA_ID_CHAVE_TORNEIO_POR_IDDUPLA1_E_FASE);

            this.pstmt.setInt(1, idDupla1);
            this.pstmt.setInt(1, fase);

            this.rs = pstmt.executeQuery();

            if (this.rs != null) {

                if (this.rs.next()) {

                    idChave = this.rs.getInt("id");
                }
            }

        } catch (SQLException e) {

        }
        closeConns();
        return idChave;
    }

    public ArrayList<ChaveTorneio> getChaveTorneioAtualizada(int idTorneio) throws SQLException {

        this.conexao = new Conn();
        this.pstmt = conexao.getConexao().prepareStatement(PEGA_CHAVE_TORNEIO_POR_ID);

        this.pstmt.setInt(1, idTorneio);

        this.rs = pstmt.executeQuery();
        if (this.rs != null) {
            this.listaChaveTor = new ArrayList();
            while (this.rs.next()) {
                this.chaveTorneio = new ChaveTorneio();
                this.chaveTorneio.setFase(this.rs.getInt("fase"));
                this.chaveTorneio.setId(this.rs.getInt("id"));
                this.chaveTorneio.setIdDupla1(this.rs.getInt("idDupla1"));
                this.chaveTorneio.setIdDupla2(this.rs.getInt("idDupla2"));
                this.chaveTorneio.setIdTorneio(this.rs.getInt("idTorneio"));
                this.chaveTorneio.setScoreDp1(this.rs.getInt("scoreDp1"));
                this.chaveTorneio.setScoreDp2(this.rs.getInt("scoreDp2"));
                this.chaveTorneio.setVerificado(this.rs.getInt("verificado"));
                this.listaChaveTor.add(chaveTorneio);
            }
        }
        this.closeConns();
        return listaChaveTor;
    }

    public ArrayList<ChaveTorneio> getChaveTorneioAtualizadaPorFase(int idTorneio, int fase) throws SQLException {

        this.conexao = new Conn();
        this.pstmt = conexao.getConexao().prepareStatement(PEGA_CHAVE_TORNEIO_POR_ID_E_FASE);

        this.pstmt.setInt(1, idTorneio);
        this.pstmt.setInt(2, fase);

        ArrayList<ChaveTorneio> lsChv = null;
        ChaveTorneio chv = null;

        this.rs = pstmt.executeQuery();
        if (this.rs != null) {
            lsChv = new ArrayList();
            while (this.rs.next()) {
                chv = new ChaveTorneio();
                chv.setFase(this.rs.getInt("fase"));
                chv.setId(this.rs.getInt("id"));
                chv.setIdDupla1(this.rs.getInt("idDupla1"));
                chv.setIdDupla2(this.rs.getInt("idDupla2"));
                chv.setIdTorneio(this.rs.getInt("idTorneio"));
                chv.setScoreDp1(this.rs.getInt("scoreDp1"));
                chv.setScoreDp2(this.rs.getInt("scoreDp2"));
                lsChv.add(chv);
            }
        }
        this.closeConns();
        return lsChv;
    }

    public ChaveTorneio getObjChaveTorneioAtualizadaPorFaseEIds(int idTorneio, int fase, int idDp1, int idDp2) throws SQLException {

        this.conexao = new Conn();
        this.pstmt = conexao.getConexao().prepareStatement(PEGA_CHAVE_TORNEIO_POR_ID_E_FASE_E_IDS);

        this.pstmt.setInt(1, idTorneio);
        this.pstmt.setInt(2, fase);
        this.pstmt.setInt(3, idDp1);
        this.pstmt.setInt(4, idDp2);

        ChaveTorneio chv = null;

        this.rs = pstmt.executeQuery();
        if (this.rs != null) {
            if (this.rs.next()) {
                chv = new ChaveTorneio();
                chv.setFase(this.rs.getInt("fase"));
                chv.setId(this.rs.getInt("id"));
                chv.setIdDupla1(this.rs.getInt("idDupla1"));
                chv.setIdDupla2(this.rs.getInt("idDupla2"));
                chv.setIdTorneio(this.rs.getInt("idTorneio"));
                chv.setScoreDp1(this.rs.getInt("scoreDp1"));
                chv.setScoreDp2(this.rs.getInt("scoreDp2"));

            }
        }
        this.closeConns();
        return chv;
    }

    public ArrayList<ListaDeInscritosEmUmTorneio> getListaDeInscritosAtualizada(int idTorneio) throws SQLException {

        this.conexao = new Conn();
        this.pstmt = conexao.getConexao().prepareStatement(PEGA_LISTA_DE_INSCRITOS_EM_UM_TORNEIO);

        this.pstmt.setInt(1, idTorneio);

        this.rs = pstmt.executeQuery();
        int i = 1;
        if (this.rs != null) {
            this.listaDeInscritosEmUmTorneio = new ArrayList();
            while (this.rs.next()) {
                this.lstInscrTor = new ListaDeInscritosEmUmTorneio();
                this.lstInscrTor.setNome(this.rs.getString("duplas"));
                this.lstInscrTor.setLogo(this.rs.getString("logo"));
                this.lstInscrTor.setIdInscrito(this.rs.getInt("iddupla"));
                this.lstInscrTor.setOrdem(this.rs.getInt("ordem"));
                this.listaDeInscritosEmUmTorneio.add(lstInscrTor);
                i++;
            }
        }
        this.closeConns();
        return listaDeInscritosEmUmTorneio;
    }

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
        this.closeConns();
        return listaDeTorneios;
    }

    public Torneio getTorneioPorNome(String nomeTorneio) throws SQLException {

        this.conexao = new Conn();
        this.pstmt = conexao.getConexao().prepareStatement(VERIFICAR_TORNEIO_EXISTENTE);

        this.pstmt.setString(1, nomeTorneio);

        this.rs = pstmt.executeQuery();

        if (this.rs != null) {

            if (this.rs.next()) {
                this.torneio = new Torneio();

                this.torneio.setCriador(this.rs.getInt("criador"));
                this.torneio.setData(Utilities.DateToString(Utilities.StringToDate(this.rs.getString("dataCriacao"))));
                this.torneio.setDataEncerraInsc(Utilities.DateToString(Utilities.StringToDate(this.rs.getString("dataEncerramentoInsc"))));
                this.torneio.setDescricao(this.rs.getString("descricao"));
                this.torneio.setFinalizado(this.rs.getInt("finalizado"));
                this.torneio.setId(this.rs.getInt("id"));
                this.torneio.setLimiteDuplas(this.rs.getInt("limiteDuplas"));
                this.torneio.setNome(this.rs.getString("nome"));

            }
        }

        this.closeConns();
        return this.torneio;
    }

    public Torneio getTorneioPorID(int idTorneio) throws SQLException {

        this.conexao = new Conn();
        this.pstmt = conexao.getConexao().prepareStatement(PEGA_TORNEIO_POR_ID);

        this.pstmt.setInt(1, idTorneio);

        this.rs = pstmt.executeQuery();

        if (this.rs != null) {

            if (this.rs.next()) {
                this.torneio = new Torneio();

                this.torneio.setCriador(this.rs.getInt("criador"));
                this.torneio.setData(Utilities.DateToString(Utilities.StringToDate(this.rs.getString("dataCriacao"))));
                this.torneio.setDataEncerraInsc(Utilities.DateToString(Utilities.StringToDate(this.rs.getString("dataEncerramentoInsc"))));
                this.torneio.setDescricao(this.rs.getString("descricao"));
                this.torneio.setFinalizado(this.rs.getInt("finalizado"));
                this.torneio.setId(this.rs.getInt("id"));
                this.torneio.setLimiteDuplas(this.rs.getInt("limiteDuplas"));
                this.torneio.setNome(this.rs.getString("nome"));

            }
        }

        this.closeConns();
        return this.torneio;
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

    public Dupla AutenticarDupla(int idDupla) throws SQLException {

        this.dupla = new Dupla();
        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(PEGA_DUPLA_POR_ID);

        this.pstmt.setInt(1, idDupla);

        this.rs = pstmt.executeQuery();

        if (this.rs != null) {

            if (this.rs.next()) {

                this.dupla = getConsultaDupla(this.rs);

            }
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

            this.pstmt = conexao.getConexao().prepareStatement(PEGA_DUPLA_POR_LIDER);

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

    public boolean verificaEstaInscritoTorneio(int idDupla, int idTorneio) throws SQLException {

        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(VERIFICA_SE_JA_ESTA_INSCRITO_NO_TORNEIO);

        this.pstmt.setInt(1, idDupla);
        this.pstmt.setInt(2, idTorneio);

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

    public void closeConns() throws SQLException {

        if (this.pstmt != null) {
            this.pstmt.close();
        }
        if (this.rs != null) {
            this.rs.close();
        }
        this.conexao.close();

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

    public int getCountQry(String qry) throws SQLException {
        this.conexao = new Conn();

        this.pstmt = conexao.getConexao().prepareStatement(qry);

        this.rs = pstmt.executeQuery();

        if (this.rs != null) {
            if (this.rs.next()) {
                int count = this.rs.getInt("c");
                closeConns();
                return count;
            }

        }
        closeConns();
        return 0;
    }

}
