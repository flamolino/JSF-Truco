package bean;

import bean.tabelas.ListaDeInscritosEmUmTorneio;
import bean.tabelas.ListaDeTorneios;
import data.qry.Insert;
import data.qry.Select;
import data.qry.Update;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.*;
import javax.faces.component.html.*;
import javax.faces.context.FacesContext;
import outro.Utilities;

@ManagedBean(name = "torneio")
@SessionScoped

public class BeanTorneio {

    private String mensagem;
    private Torneio torneio = null;
    private ListaDeTorneios lstTorneio = null;
    private ListaDeInscritosEmUmTorneio lstTorneioSort = null;
    private ArrayList<ListaDeTorneios> listaDeTorneios = null;
    private ArrayList<ListaDeInscritosEmUmTorneio> listaDeTorneiosSort = null;
    private ChaveTorneio chaveTorneio = null;
    private ArrayList<ChaveTorneio> lstChaveTorneio = null;
    private Date dtCalendario = null;
    private UIComponent found;

    public BeanTorneio() {
        this.mensagem = "";
        this.torneio = new Torneio();
        this.lstTorneio = new ListaDeTorneios();
        this.listaDeTorneios = new ArrayList();
        this.listaDeTorneiosSort = new ArrayList();
        this.dtCalendario = new Date();
        this.lstTorneioSort = new ListaDeInscritosEmUmTorneio();
        this.chaveTorneio = new ChaveTorneio();
        this.lstChaveTorneio = new ArrayList();
    }

    public void criaComp() {

        HtmlPanelGroup div = new HtmlPanelGroup();
        div.setLayout("block");

        HtmlOutputText tile = new HtmlOutputText();
        tile.setValue("asdf");
        tile.setStyle("tile");
        div.getChildren().add(tile);

        doFind(FacesContext.getCurrentInstance(), "tiles");
        found.getChildren().add(div);

    }

    private void doFind(FacesContext context, String clientId) {
        FacesContext.getCurrentInstance().getViewRoot().invokeOnComponent(context, clientId, new ContextCallback() {
            @Override
            public void invokeContextCallback(FacesContext context, UIComponent component) {
                found = component;
            }
        });
    }

    public boolean setDefaultTorneio() {
        this.mensagem = "";
        this.torneio = new Torneio();
        this.lstTorneio = new ListaDeTorneios();
        this.listaDeTorneios = new ArrayList();
        this.dtCalendario = new Date();
        this.lstTorneioSort = new ListaDeInscritosEmUmTorneio();
        this.chaveTorneio = new ChaveTorneio();
        this.lstChaveTorneio = new ArrayList();
        return true;
    }

    public ArrayList<ListaDeTorneios> getListaDeTorneiosAtualizada() throws SQLException {
        Select sel = new Select();
        ArrayList<ListaDeTorneios> lst = sel.getListaDeTorneiosAtualizada();
        return lst;
    }

    public ArrayList<ListaDeInscritosEmUmTorneio> getListaDeInscritosAtualizada() throws SQLException {
        Select sel = new Select();
        return sel.getListaDeInscritosAtualizada(this.torneio.getId());
    }

    public String callTorneio() {
        this.mensagem = "";
        return "go-to-torneio";
    }

    public void iniciaTorneio() throws SQLException {

        this.mensagem = "";

        if (this.torneio.getFinalizado() == -1) {
            int idTorneio = this.torneio.getId();
            Select sel = new Select();
            this.listaDeTorneiosSort = sel.getListaDeInscritosAtualizada(idTorneio);

            if (this.listaDeTorneiosSort.size() > 1) {

                System.out.println(this.listaDeTorneiosSort.size());

                if (this.listaDeTorneiosSort.size() < this.torneio.getLimiteDuplas()) {
                    for (int i = this.listaDeTorneiosSort.size(); i < this.torneio.getLimiteDuplas(); i++) {
                        this.lstTorneioSort = new ListaDeInscritosEmUmTorneio();
                        this.lstTorneioSort.setIdInscrito(-1);
                        this.listaDeTorneiosSort.add(this.lstTorneioSort);
                    }
                }

                this.listaDeTorneiosSort = (ArrayList<ListaDeInscritosEmUmTorneio>) Utilities.embaralhaArrayList(this.listaDeTorneiosSort);

                Insert ins = new Insert();

                int max = this.torneio.getLimiteDuplas() / 2;

                int j = this.listaDeTorneiosSort.size() - 1;
                int id1, id2;

                for (int i = 0; i < max; i++) {

                    id1 = this.listaDeTorneiosSort.get(i).getIdInscrito();
                    id2 = this.listaDeTorneiosSort.get(j).getIdInscrito();

                    ins.inserirNaChave(id1, id2, 1, idTorneio);

                    j--;

                }

                this.torneio.setFinalizado(0);
                Update upd = new Update();
                upd.atualizaStatusTorneio(idTorneio, 0);
                this.mensagem = "O torneio foi iniciado com sucesso!";

            } else {
                this.mensagem = "Não há quantidade de inscritos suficiente para iniciar o torneio!";
            }
        } else {
            this.mensagem = "O torneio já foi iniciado ou já está finalizado!";
        }

    }

    public void avancaFasesTorneio() throws SQLException {

        //     if (this.torneio.getFinalizado() == 0) {
        Select sel = new Select();
        Insert ins = new Insert();
        Update upd = new Update();

        int fasesMax = 0;

        for (int i = 1; i < this.torneio.getLimiteDuplas(); i *= 2) {
            fasesMax++;
        }

        this.lstChaveTorneio = new ArrayList();
        this.lstChaveTorneio = sel.getChaveTorneioAtualizada(this.torneio.getId());
        ChaveTorneio chave = new ChaveTorneio();
        int idDuplaChave = -1;

        this.mensagem = String.valueOf(fasesMax) + " | " + String.valueOf(this.lstChaveTorneio.size());

        for (int i = 0; i < this.lstChaveTorneio.size(); i++) {

            chave = this.lstChaveTorneio.get(i);

            if (fasesMax > chave.getFase()) {
                if (chave.getVerificado() == -1) {

                    if (chave.getScoreDp1() != 0 || chave.getScoreDp2() != 0 && chave.getIdDupla1() != -1 && chave.getIdDupla2() != -1) {

                        int idWin;

                        if (chave.getScoreDp1() > chave.getScoreDp2()) {
                            idWin = chave.getIdDupla1();
                        } else {
                            idWin = chave.getIdDupla2();
                        }

                        atualizaFase(i, chave, idDuplaChave, idWin);

                    } else if (chave.getIdDupla1() == -1 && chave.getIdDupla2() == -1) {

                        atualizaFase(i, chave, idDuplaChave, -1);

                    } else if (chave.getIdDupla1() == -1 && chave.getIdDupla2() != -1) {

                        atualizaFase(i, chave, idDuplaChave, chave.getIdDupla2());

                    } else if (chave.getIdDupla1() != -1 && chave.getIdDupla2() == -1) {

                        atualizaFase(i, chave, idDuplaChave, chave.getIdDupla1());

                    }

                }

            } else {
                // se entrar aqui, está na fase final
            }
            if (chave.getScoreDp1() >= chave.getScoreDp2()) {
                idDuplaChave = chave.getIdDupla1();
            } else {
                idDuplaChave = chave.getIdDupla2();
            }

        }

        //  }
    }

    private void atualizaFase(int i, ChaveTorneio chave, int idDuplaChave, int idWin) throws SQLException {
        Insert ins = new Insert();
        Update upd = new Update();
        Select sel = new Select();
        if (i % 2 == 0 || i == 0) {
            ins.inserirNaChave(idWin, -1, chave.getFase() + 1, this.torneio.getId());
            upd.atualizaVerificadoChave(chave.getId());
        } else {
            int idChaveTorneio = sel.getIDChaveTorneio(idDuplaChave, (chave.getFase() + 1));
            if (idChaveTorneio != -1) {
                upd.atualizaAdversarioChave(idWin, idChaveTorneio);
                upd.atualizaVerificadoChave(chave.getId());
            }
        }
    }

    public void criarNovoTorneio(int idCriador) throws SQLException {
        this.mensagem = "";
        Select sel = new Select();

        if (sel.verificaTorneioExistente(this.torneio.getNome())) {
            this.mensagem = "Já existe um torneio com este nome!";
        } else {

            this.torneio.setData(Utilities.getDataAtualString());
            this.torneio.setDataEncerraInsc(
                    Utilities.DateToString(this.dtCalendario)
                    + " 01:01:01");
            this.torneio.setCriador(idCriador);
            this.torneio.setData(Utilities.getDataAtualString());

            Insert ins = new Insert();

            if (ins.novoTorneio(this.torneio)) {
                this.mensagem = "Torneio criado com sucesso!";

                this.torneio = new Torneio();
            } else {
                this.mensagem = "Falha ao criar torneio!";
            }

        }
    }

    public boolean verificaSeECriadorDoTorneio(int id) {
        return id == this.torneio.getCriador();
    }

    public ArrayList<String> getNumeroDeDuplas() {

        ArrayList<String> a = new ArrayList();

        for (int i = 2; i <= 512; i *= 2) {

            a.add(i + "");

        }

        return a;

    }

    public String callCadastroTorneio() {
        return "go-to-cadastro_torneio";
    }

    public String callTorneioDetalhado(String nomeTorneio, ListaDeTorneios lst) throws SQLException {

        Select sel = new Select();
        this.torneio = sel.getTorneioPorNome(nomeTorneio);
        this.lstTorneio = lst;
        if (this.torneio != null) {
            return "go-to-torneio_detalhado";
        }
        return "";

    }

    public boolean verificaSeDataJaUltrapassouAtual() {

        Date dtEnc = Utilities.StringToDate(this.torneio.getDataEncerraInsc());
        Date dtAtual = Utilities.StringToDate(Utilities.getDataAtualSemHoraString());

        return dtEnc.compareTo(dtAtual) <= 0;
    }

    public String getStatusDoTorneio() {
        if (this.torneio.getFinalizado() > -1) {
            return "Finalizado";
        } else if (this.torneio.getFinalizado() == -1 && !this.getTorneioIsCheio()) {
            return "Aberto";
        } else if (this.torneio.getFinalizado() == -1 && this.getTorneioIsCheio()) {
            return "Ativo";
        }
        return "";
    }

    public String getCorStatusDoTorneio() {
        if (this.torneio.getFinalizado() > -1) {
            return "danger";
        } else if (this.torneio.getFinalizado() == -1 && !this.getTorneioIsCheio()) {
            return "warning";
        } else if (this.torneio.getFinalizado() == -1 && this.getTorneioIsCheio()) {
            return "success";
        }
        return "dark";
    }

    public void inscreverTorneio(int idUsuario, int idDupla, String nomeTorneio) throws SQLException {

        boolean bool;
        this.mensagem = "";
        Select sel = new Select();
        Dupla dupla = sel.AutenticarDupla(idDupla);

        int idTorneio = sel.getTorneioPorNome(nomeTorneio).getId();

        if (dupla.getJogadorLider() > 0) {

            if (dupla.getJogadorLider() != idUsuario) {
                if (dupla.getJogador() > 0) {

                    bool = sel.verificaEstaInscritoTorneio(idDupla, idTorneio);
                    if (!bool) {

                        Insert ins = new Insert();
                        ins.inscreverNoTorneio(idDupla, idTorneio);
                        this.mensagem = "a dupla foi inscrita!";

                    } else {
                        this.mensagem = "Sua dupla já está inscrita neste torneio!";
                    }

                } else {
                    this.mensagem = "Sua dupla não possui um parceiro!";
                }
            } else {
                this.mensagem = "Apenas o líder da dupla pode inscrever-se em um torneio!";
            }

        } else {

            this.mensagem = "Precisa compor uma dupla para participar desde torneio!";

        }
    }

    public boolean verificaSeTemMensagem() {
        return !this.mensagem.equals("");
    }

    public void limpaMensagem() throws SQLException {

        this.mensagem = "";
    }

    public boolean isEstaAtivo() {
        return !(this.torneio.getFinalizado() == -1 && this.getTorneioIsCheio());
    }

    public String getNomeCriador() throws SQLException {
        Select sel = new Select();

        return sel.getLoginPorID(this.torneio.getCriador());
    }

    public String getInscrBarraLimite() {
        return "Lista de Inscritos (" + this.lstTorneio.getQtdInscritos() + "/" + this.lstTorneio.getLimiteDuplas() + ")";
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Torneio getTorneio() {
        return torneio;
    }

    public void setTorneio(Torneio torneio) {
        this.torneio = torneio;
    }

    public int getId() {
        return this.torneio.getId();
    }

    public void setId(int id) {
        this.torneio.setId(id);
    }

    public int getLimiteDuplas() {
        return this.torneio.getLimiteDuplas();
    }

    public void setLimiteDuplas(int limiteDuplas) {
        this.torneio.setLimiteDuplas(limiteDuplas);
    }

    public int getFinalizado() {
        return this.torneio.getFinalizado();
    }

    public void setFinalizado(int finalizado) {
        this.torneio.setFinalizado(finalizado); //= finalizado;
    }

    public int getCriador() {
        return this.torneio.getCriador();
    }

    public void setCriador(int criador) {
        this.torneio.setCriador(criador);/// = criador;
    }

    public String getNome() {
        return this.torneio.getNome();
    }

    public void setNome(String nome) {
        this.torneio.setNome(nome);// = nome;
    }

    public String getDescricao() {
        return this.torneio.getDescricao();
    }

    public void setDescricao(String descricao) {
        this.torneio.setDescricao(descricao);// = descricao;
    }

    public String getData() {
        return this.torneio.getData();
    }

    public void setData(String data) {
        this.torneio.setData(data);// = data;
    }

    public String getDataEncerramento() {
        return this.torneio.getDataEncerraInsc();
    }

    public void setDataEncerramento(String data) {
        this.torneio.setDataEncerraInsc(data);
    }

    public ArrayList<ListaDeTorneios> getListaDeTorneios() {
        return listaDeTorneios;
    }

    public void setListaDeTorneios(ArrayList<ListaDeTorneios> listaDeTorneios) {
        this.listaDeTorneios = listaDeTorneios;
    }

    public Date getDtCalendario() {
        return dtCalendario;
    }

    public void setDtCalendario(Date dt) {
        this.dtCalendario = dt;
    }

    public boolean getTorneioIsCheio() {
        return this.lstTorneio.isJustCheio();
    }

    public boolean getIsInscrever() {
        return this.lstTorneio.isCheio();
    }

}
