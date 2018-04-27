package bean;

import data.qry.Insert;
import data.qry.Select;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
    public void logar() throws SQLException {

        Select sel = new Select();

        this.user = sel.AutenticarLogin(this.user.getLogin(), this.user.getSenha());

        if (this.user.getId() != -1) {
            setLogado(true);
            setMensagem("Logou!");
        } else {
            setLogado(false);
            setMensagem("Não Logou!");
        }

    }

    public void cadastrarUsuario() {

        try {
            Select sel = new Select();
            Insert ins = new Insert();

            if (sel.verificaUsuarioExistente(this.user.getLogin())) {
                this.mensagem = "Já existe um usuário com esse login!";
            } else {
                if (ins.novoUsuario(user)) {
                    this.mensagem = "Cadastro realizado com sucesso!";
                }
            }
        } catch (SQLException ex) {
            this.mensagem = "Erro ao realizar cadastro!";
            Logger.getLogger(BeanUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String desloga() {
        this.logado = false;
        return callIndex();
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

    public String callIndex() {
        this.mensagem = "";
        this.user = new Usuario();
        return "index";
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

    public int getId() {
        return this.user.getId();
    }

    public void setId(int id) {
        this.user.setId(id);
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

}
