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
        private String inputText;
    private String inputTextTitle;

    //constructor
    public beanDupla() {
        this.dupla = new Dupla();
        this.dupla.setLogo("https://milvus.com.br/wordpress/wp-content/uploads/2017/05/avatar-default.jpg");
        this.temDupla = false;
        this.mensagem = "";
                this.inputText = "";
        this.inputTextTitle = "";
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
                this.temDupla = sel.verificaDuplaExistente(this.dupla.getNome());
                this.dupla = sel.AutenticarDupla(id, id);
                upd.atualizaDuplaAtual(this.dupla.getId(), id);

            } else {
                this.mensagem = "Erro ao criar dupla!";
            }
        }

    }

    public String getDeletarOuSairDaDupla(int id) throws SQLException {
        Select sel = new Select();
        this.temDupla = sel.verificaDuplaExistente(this.dupla.getNome());
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
        if (this.temDupla) {
            if (dupla.equals("Deletar dupla")) {
                Delete del = new Delete();
                del.deletarDupla(this.dupla.getId(), this.dupla.getJogadorLider());
            } else if (dupla.equals("Deixar dupla")) {
                Update upd = new Update();
                upd.sairDaDupla(this.dupla.getId(), this.dupla.getJogador());
            }
        }
    }

    public String callDupla(int id) throws SQLException {
        Select sel = new Select();
        this.dupla = sel.AutenticarDupla(id, id);
        this.mensagem = "";
        return "dupla";
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

    public String adicionarLogo() {
        this.inputTextTitle = "Coloque a URL da imagem";
        this.inputText = "";
        return "inputText";

    }
    
    public String screenInputTextConfimarLogo() throws SQLException{
        setLogo(this.inputText);
        Update upd = new Update();
        upd.atualizarLogo(this.dupla.getId(), this.dupla.getLogo());
        return "dupla";
    }

    public void convidarParceiro() throws SQLException {

        Select sel = new Select();

        if (sel.verificaUsuarioExistente(parceiro)) {
            if (sel.verificaUsuarioTemConvite(parceiro) != -1) {
                this.mensagem = "Este usuário já têm um convite pendente";
            } else {
                Update upd = new Update();
                upd.convidaParceiro(this.dupla.getJogadorLider(), sel.getIDPorLogin(parceiro));
                this.mensagem = "Um convite foi enviado para o usuário " + parceiro;
            }
        } else {
            this.mensagem = "Não existe um usuário com esse login";
        }

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
    
    public String getParceiro(){
        return this.parceiro;
    }
    
    public void setParceiro(String par){
        this.parceiro = par;
    }
    
            public String getInputText() {
        return this.inputText;
    }

    public void setInputText(String str) {
        this.inputText=str;
    }
    
            public String getInputTextTitle() {
        return this.inputTextTitle;
    }

    public void setInputTextTitle(String str) {
        this.inputTextTitle=str;
    }

}
