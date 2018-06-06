package bean;

import data.qry.Delete;
import data.qry.Insert;
import data.qry.Select;
import data.qry.Update;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import outro.Utilities;

@ManagedBean(name = "dupla")
@SessionScoped

public class beanDupla {

    private String mensagem;
    private boolean temDupla;
    private Dupla dupla = null;
    private String parceiro;

    //constructor
    public beanDupla() {
        this.dupla = new Dupla();
        this.dupla.setLogo("https://milvus.com.br/wordpress/wp-content/uploads/2017/05/avatar-default.jpg");
        this.temDupla = false;
        this.mensagem = "";
    }

    public boolean setDefaultDupla() {
        this.dupla = new Dupla();
        this.dupla.setLogo("https://milvus.com.br/wordpress/wp-content/uploads/2017/05/avatar-default.jpg");
        this.temDupla = false;
        this.mensagem = "";
        return true;
    }

    public void cadastrarDupla(int id) throws SQLException {

        this.mensagem = "";
        Select sel = new Select();
        if (sel.verificaDuplaExistente(this.dupla.getNome())) {
            this.mensagem = "Já existe uma dupla cadastrada com esse nome!";
        } else {
            this.dupla.setJogadorLider(id);
            this.dupla.setData(Utilities.getDataAtualString());

            Insert ins = new Insert();
            Update upd = new Update();
            if (ins.novaDupla(this.dupla)) {
                this.mensagem = "Dupla criada com sucesso!";
                this.dupla = sel.AutenticarDupla(id, id);
                upd.atualizaDuplaAtual(this.dupla.getId(), id);

            } else {
                this.mensagem = "Erro ao criar dupla!";
            }
        }
        this.temDupla = sel.verificaSeEstaOuSeTemDupla(id);

    }

    public String getDeletarOuSairDaDupla(int id) throws SQLException {
        Select sel = new Select();
        this.temDupla = sel.verificaSeEstaOuSeTemDupla(id);
        this.dupla = sel.AutenticarDupla(id, id);
        if (this.dupla.getJogadorLider() == id) {
            return "Deletar dupla";
        } else if (this.dupla.getJogador() == id) {
            return "Deixar dupla";
        } else {
            return "";
        }

    }

    public void atualizaDupla(int id) throws SQLException {
        Select sel = new Select();
        this.dupla = sel.AutenticarDupla(id, id);
        this.temDupla = sel.verificaSeEstaOuSeTemDupla(id);
    }

    public void excluirOuSairDaDupla(String dupla) throws SQLException {

        int id1 = this.dupla.getJogador();
        int id2 = this.dupla.getJogadorLider();
        if (this.temDupla) {

            if (dupla.equals("Deletar dupla")) {

                if (this.dupla.getJogador() != -1) {
                    Update upd = new Update();
                    upd.sairDaDupla(this.dupla.getId(), this.dupla.getJogador());
                }

                Delete del = new Delete();
                del.deletarDupla(this.dupla.getId(), this.dupla.getJogadorLider());
            } else if (dupla.equals("Deixar dupla")) {
                Update upd = new Update();
                upd.sairDaDupla(this.dupla.getId(), this.dupla.getJogador());
            }
        }
        Select sel = new Select();
        this.temDupla = sel.verificaSeEstaOuSeTemDupla(id1);
        this.temDupla = sel.verificaSeEstaOuSeTemDupla(id2);

    }

    public String callDupla(int id, boolean logado) throws SQLException {
        if (logado) {
            Select sel = new Select();
            this.temDupla = sel.verificaSeEstaOuSeTemDupla(id);
            this.dupla = sel.AutenticarDupla(id, id);
            this.mensagem = "";
            return "go-to-dupla";
        } else {
            return "";
        }
    }

    public boolean verificaSeTemMensagem() {
        return !this.mensagem.equals("");
    }

    public void limpaMensagem() throws SQLException {

        this.mensagem = "";
    }

    public boolean seTemIntegrante() {

        return this.dupla.getJogador() != -1;

    }

    public String getNomeDoLider() throws SQLException {
        Select sel = new Select();
        return sel.getLoginPorID(this.dupla.getJogadorLider());
    }

    public String getNomeDoIntegrante() throws SQLException {
        Select sel = new Select();
        return sel.getLoginPorID(this.dupla.getJogador());
    }

    public int getId() {
        return this.getDupla().getId();
    }

    public void setId(int id) {
        this.getDupla().setId(id);
    }

    public int getJogadorLider() {
        return this.getDupla().getJogadorLider();
    }

    public void setJogadorLider(int jogadorLider) {
        this.getDupla().setJogadorLider(jogadorLider);
    }

    public int getJogador() {
        return this.getDupla().getJogador();
    }

    public void setJogador(int jogador) {
        this.getDupla().setJogador(jogador);
    }

    public String getNome() {
        return this.getDupla().getNome();
    }

    public void setNome(String nome) {
        this.getDupla().setNome(nome);
    }

    public String getLogo() {
        return this.getDupla().getLogo();
    }

    public void setLogo(String logo) {
        this.getDupla().setLogo(logo);
    }

    public String getData() {

        if (this.temDupla) {
            return Utilities.formataData(this.getDupla().getData());
        } else {
            return "";
        }

    }

    public void setData(String data) {
        this.getDupla().setData(data);
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isTemDupla() {
        return temDupla;
    }

    public boolean SeTemDupla(int id) throws SQLException {
        Select sel = new Select();
        this.temDupla = sel.verificaSeEstaOuSeTemDupla(id);
        return this.temDupla;
    }

    public void setTemDupla(boolean temDupla) {
        this.temDupla = temDupla;
    }

    public Dupla getDupla() {
        return dupla;
    }

    public void setDupla(Dupla dupla) {
        this.dupla = dupla;
    }

    public String getParceiro() {
        return this.parceiro;
    }

    public void setParceiro(String par) {
        this.parceiro = par;
    }

    public String pegaTitle(int id) throws SQLException {
        Select sel = new Select();
        this.dupla = sel.AutenticarDupla(id, id);
        return this.dupla.getNome();
    }

    public String pegaLogo(int id) throws SQLException {
        Select sel = new Select();
        this.dupla = sel.AutenticarDupla(id, id);
        return this.dupla.getLogo();
    }

}
