package bean;

import data.qry.Insert;
import data.qry.Select;
import data.qry.Update;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import outro.Utilities;

@ManagedBean(name = "usuario")
@SessionScoped

public class BeanUsuario {

    private String mensagem;
    private boolean logado;
    private Usuario user = null;

    //constructor
    public BeanUsuario() {
        this.user = new Usuario();
        this.logado = false;
    }

    /*
    Methods
     */
    public String logar() throws SQLException {

        Select sel = new Select();

        this.user = sel.AutenticarLogin(this.user.getLogin(), this.user.getSenha());

        if (this.user.getId() != -1) {
            setLogado(true);
            return "main";
        } else {
            return "";
        }

    }

    public void atualizaUsuario() throws SQLException {
        Select sel = new Select();
        this.user = sel.AutenticarLogin(this.user.getLogin(), this.user.getSenha());
    }

    public String cadastrarUsuario() throws ParseException {
        this.mensagem = "";
        try {
            Select sel = new Select();
            Insert ins = new Insert();
            this.user.setData(Utilities.getDataAtualString());
            if (sel.verificaUsuarioExistente(this.user.getLogin())) {
                this.mensagem = "Já existe um usuário com esse login!";
            } else {
                if (ins.novoUsuario(this.user)) {
                    this.mensagem = "Cadastro realizado com sucesso!";
                    return logar();
                }
            }
        } catch (SQLException ex) {
            this.mensagem = "Erro ao realizar cadastro!";
            Logger.getLogger(BeanUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";

    }

    public String minhaDupla() throws SQLException {
        Select sel = new Select();
        if (sel.verificaSeEstaOuSeTemDupla(this.user.getId())) {
            return "";
        } else {
            return "";
        }
    }

    public String desloga() {
        this.mensagem = "";
        this.user = new Usuario();
        return "index";
    }

    public boolean verificaSeTemConvite() throws SQLException {
        Select sel = new Select();
        this.user = sel.AutenticarLogin(this.user.getLogin(), this.user.getSenha());
        return sel.verificaUsuarioTemConvite(this.user.getLogin()) != -1;
    }

    public String getMensagemDeConviteDupla() throws SQLException {
        if (this.verificaSeTemConvite()) {
            Select sel = new Select();

            return "<b>" + sel.getLoginPorID(this.user.getConvite())
                    + "</b><br>está te convidando para ser seu parceiro na dupla<br><b>"
                    + sel.getNomeDuplaPorLiderID(this.user.getConvite()) + "<b>";
        }
        return "";

    }

    public void aceitarConviteDeDupla() throws SQLException {
        Update upd = new Update();
        Select sel = new Select();
        upd.atualizaDuplaAtual(sel.AutenticarDupla(this.user.getConvite(), this.user.getConvite()).getId(), this.user.getId());
        upd.entrarParaDupla(this.user.getId(), sel.AutenticarDupla(this.user.getConvite(), this.user.getConvite()).getId());
        upd.recusarConvite(this.user.getId());
        this.user = sel.AutenticarLogin(this.user.getLogin(), this.user.getSenha());

    }

    public void recusarConviteDeDupla() throws SQLException {
        Update upd = new Update();

        upd.recusarConvite(this.user.getId());
    }

    /*
    Direct Screens Calls
     */
    public String callCadastroUsuario() {
        this.mensagem = "";
        this.user = new Usuario();
        this.user.setIdade(10);
        return "cadastro_usuario";
    }

    public String callTorneio() {
        this.mensagem = "";
        return "torneio";
    }

    public String callMain() {
        this.mensagem = "";
        return "main";
    }

    public String callPerfil() {
        this.mensagem = "";
        return "perfil";
    }

    /*
    Gets and Setters
     */
    public void setMensagem(String str) {
        this.mensagem = str;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public String getLogin() {
        return this.user.getLogin();
    }

    public void setLogin(String str) {
        this.user.setLogin(str);
    }

    public String getSenha() {
        return this.user.getSenha();
    }

    public void setSenha(String str) {
        this.user.setSenha(str);
    }

    public String getNome() {
        return this.user.getNome();
    }

    public void setNome(String str) {
        this.user.setNome(str);
    }

    public String getEmail() {
        return this.user.getEmail();
    }

    public void setEmail(String str) {
        this.user.setEmail(str);
    }

    public String getAvatar() {
        return this.user.getAvatar();
    }

    public void setAvatar(String str) {
        this.user.setAvatar(str);
    }

    public String getEndereco() {
        return this.user.getEndereco();
    }

    public void setEndereco(String str) {
        this.user.setEndereco(str);
    }

    public String getData() {
        return this.user.getData();
    }

    public void setData(String str) {
        this.user.setData(str);
    }

    public int getId() {
        return this.user.getId();
    }

    public void setId(int id) {
        this.user.setId(id);
    }

    public int getConvite() {
        return this.user.getConvite();
    }

    public void setConvite(int convite) {
        this.user.setConvite(convite);
    }

    public int getIdade() {
        return this.user.getDuplaAtual();
    }

    public void setIdade(int idade) {
        this.user.setIdade(idade);
    }

    public int getDuplaAtual() {
        return this.user.getDuplaAtual();
    }

    public void setDuplaAtual(int dupla) {
        this.user.setDuplaAtual(dupla);
    }

    public boolean isLogado() {
        return this.logado;
    }

    public void setLogado(boolean bool) {
        this.logado = bool;
    }

    public String getAllValues() {
        String s = "";

        s += this.user.getNome() + " | ";
        s += this.user.getData() + " | ";
        s += this.user.getAvatar() + " | ";
        s += String.valueOf(this.user.getId()) + " | ";

        return s;
    }

    public Usuario getUser() {
        return this.user;
    }

}
