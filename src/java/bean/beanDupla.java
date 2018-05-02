package bean;

import data.qry.Delete;
import data.qry.Insert;
import data.qry.Select;
import data.qry.Update;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.swing.JOptionPane;
import outro.Utilities;

@ManagedBean(name = "dupla")
@SessionScoped

public class beanDupla {

    private String mensagem;
    private boolean temDupla;
    private Dupla dupla = null;

    //constructor
    public beanDupla() {
        this.dupla = new Dupla();
        this.dupla.setLogo("https://milvus.com.br/wordpress/wp-content/uploads/2017/05/avatar-default.jpg");
        this.temDupla = false;
        this.mensagem = "TESTE";
    }

    public String cadastrarDupla(int id) throws SQLException {

        this.mensagem = "";
        this.dupla.setJogadorLider(id);
        this.dupla.setData(Utilities.getDataAtualString());
        Select sel = new Select();
        Insert ins = new Insert();

        if (sel.verificaDuplaExistente(this.dupla.getNome())) {
            this.mensagem = "Já existe uma dupla cadastrada com esse nome!";
        } else {
            if (ins.novaDupla(this.dupla)) {
                this.mensagem = "Cadastro realizado com sucesso";
                this.temDupla = sel.verificaDuplaExistente(this.dupla.getNome());
                this.dupla = sel.AutenticarDupla(id, id);
                return "dupla";
            } else {
                this.mensagem = "Erro ao realizar cadastro!";
            }
        }
        return "";

    }

    public String getDeletarOuSairDaDupla(int id) throws SQLException {
        Select sel = new Select();
        this.dupla = sel.AutenticarDupla(id, id);
        if (this.dupla.getJogadorLider() == id) {
            return "Deletar dupla";
        } else if (this.dupla.getJogador() == id) {
            return "Deixar dupla";
        } else {
            return "";
        }

    }

    public void excluirOuSairDaDupla(String dupla) throws SQLException {
        if (dupla.equals("Deletar dupla")) {
            Delete del = new Delete();
            del.deletarDupla(this.dupla.getId());
        } else if (dupla.equals("Deixar dupla")) {
            Update upd = new Update();
            upd.sairDaDupla(this.dupla.getId());
        }
    }

    public String callDupla(int id) throws SQLException {
        Select sel = new Select();
        this.dupla = sel.AutenticarDupla(id, id);
        this.mensagem = "";
        return "dupla";
    }

    public String getNomeDoLider() throws SQLException {
        Select sel = new Select();
        return sel.getNomeDoLiderOuIntegrante(this.dupla.getJogadorLider());
    }

    public String getNomeDoIntegrante() throws SQLException {
        Select sel = new Select();
        return sel.getNomeDoLiderOuIntegrante(this.dupla.getJogador());
    }

    public void adicionarLogo() {
        setLogo(JOptionPane.showInputDialog("Digite a URL da logo da dupla"));
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
        return Utilities.formataData(this.getDupla().getData());
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
        return sel.verificaSeEstaOuSeTemDupla(id);
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

}
