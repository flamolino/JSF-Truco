package bean;

import bean.tabelas.ListaDeTorneios;
import data.qry.Insert;
import data.qry.Select;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import outro.Utilities;

@ManagedBean(name = "torneio")
@SessionScoped

public class BeanTorneio {

    private String mensagem;
    private Torneio torneio = null;
    private ArrayList<ListaDeTorneios> listaDeTorneios = null;
    private Date dtCalendario = null;

    public BeanTorneio() {
        this.mensagem = "";
        this.torneio = new Torneio();
        this.listaDeTorneios = new ArrayList();
        this.dtCalendario = Utilities.StringToDate(Utilities.getDataAtualSemHoraString());
    }

    public ArrayList<ListaDeTorneios> getListaDeTorneiosAtualizada() throws SQLException {
        Select sel = new Select();
        ArrayList<ListaDeTorneios> lst = sel.getListaDeTorneiosAtualizada();
        return lst;
    }

    public String callTorneio() {
        this.mensagem = "";
        return "torneio";
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

    public ArrayList<String> getNumeroDeDuplas() {

        ArrayList<String> a = new ArrayList();

        for (int i = 2; i <= 512; i *= 2) {

            a.add(i + "");

        }

        return a;

    }

    public String callCadastroTorneio() {
        return "cadastro_torneio";
    }

    public String callTorneioDetalhado() {
        return "torneio_detalhado";
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

    /**
     * @return the listaDeTorneios
     */
    public ArrayList<ListaDeTorneios> getListaDeTorneios() {
        return listaDeTorneios;
    }

    /**
     * @param listaDeTorneios the listaDeTorneios to set
     */
    public void setListaDeTorneios(ArrayList<ListaDeTorneios> listaDeTorneios) {
        this.listaDeTorneios = listaDeTorneios;
    }

    /**
     * @return the dtCalendario
     */
    public Date getDtCalendario() {
        return dtCalendario;
    }

}
