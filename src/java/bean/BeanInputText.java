package bean;

import data.qry.Select;
import data.qry.Update;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "inputText")
@SessionScoped

public class BeanInputText {

    private String inputText;
    private String inputTextTitle;
    private String retorno;
    private Usuario user = null;
    private Dupla dupla = null;
    private String mensagem;
    private int opcao;

    /*  IF
        opcao == 1 { Adicionar a logo do time }
        opcao == 2 { Convidar usuario pra ser parceiro }
     */
    public BeanInputText() {
        this.inputText = "";
        this.inputTextTitle = "";
        this.retorno = "";
        this.mensagem = "";
    }

    public boolean setDefaultInputText() {
        this.inputText = "";
        this.inputTextTitle = "";
        this.retorno = "";
        this.mensagem = "";
        return true;
    }

    public boolean verificaSeTemMensagem() {
        return !this.mensagem.equals("");
    }

    public void limpaMensagem() throws SQLException {

        this.mensagem = "";
    }

    public String callInputText(Usuario objeto, String telaRetorno, int opcao, String titulo) {

        this.inputTextTitle = titulo;
        this.inputText = "";
        this.user = objeto;
        this.retorno = telaRetorno;
        this.opcao = opcao;
        this.mensagem = "";
        return callInputText();

    }

    public String callInputText(Dupla objeto, String telaRetorno, int opcao, String titulo) {

        this.inputTextTitle = titulo;
        this.inputText = "";
        this.dupla = objeto;
        this.retorno = telaRetorno;
        this.opcao = opcao;
        this.mensagem = "";
        return callInputText();

    }

    public String callInputText(Usuario uObjeto, Dupla dObjeto, String telaDeRetorno, int opcao, String titulo) {

        this.inputTextTitle = titulo;
        this.inputText = "";
        this.user = uObjeto;
        this.dupla = dObjeto;
        this.retorno = telaDeRetorno;
        this.opcao = opcao;
        this.mensagem = "";
        return callInputText();

    }

    public String btnCancelar() {
        this.setNull();
        return this.retorno;
    }

    public String btnConfirmar() throws SQLException {

        String ret;

        switch (this.opcao) {
            case 1:
                ret = addLogoDoTime();
                break;

            case 2:
                ret = convidarParceiro();
                break;
                
            case 3:
                ret = updAvatar();
                break;

            default:
                ret = btnCancelar();
                break;
        }
        this.setNull();
        return ret;

    }

    private String addLogoDoTime() throws SQLException {

        Update upd = new Update();
        upd.atualizarLogo(this.dupla.getId(), this.inputText);
        this.setNull();
        return this.retorno;

    }

    public String convidarParceiro() throws SQLException {

        Select sel = new Select();

        if (sel.verificaUsuarioExistente(this.inputText)) {
            if (sel.verificaUsuarioTemConvite(this.inputText) != -1) {
                this.mensagem = "Este usuário já têm um convite pendente";
            } else {
                Update upd = new Update();
                upd.convidaParceiro(this.dupla.getJogadorLider(), sel.getIDPorLogin(this.inputText));
                this.mensagem = "Um convite foi enviado para o usuário " + this.inputText;
            }
        } else {
            this.mensagem = "Não existe um usuário com esse login";
        }

        return "";

    }
    
    private String updAvatar() throws SQLException {

        Update upd = new Update();
        upd.atualizarAvatar(this.user.getId(), this.inputText);
        this.setNull();
        return this.retorno;

    }

    public String callInputText() {
        return "go-to-inputText";
    }

    private void setNull() {
        this.user = null;
        this.dupla = null;
    }

    /**
     * @return the inputText
     */
    public String getInputText() {
        return inputText;
    }

    /**
     * @param inputText the inputText to set
     */
    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    /**
     * @return the inputTextTitle
     */
    public String getInputTextTitle() {
        return inputTextTitle;
    }

    /**
     * @param inputTextTitle the inputTextTitle to set
     */
    public void setInputTextTitle(String inputTextTitle) {
        this.inputTextTitle = inputTextTitle;
    }

    /**
     * @return the user
     */
    public Usuario getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Usuario user) {
        this.user = user;
    }

    /**
     * @return the dupla
     */
    public Dupla getDupla() {
        return dupla;
    }

    /**
     * @param dupla the dupla to set
     */
    public void setDupla(Dupla dupla) {
        this.dupla = dupla;
    }

    public String getMensagem() {
        return this.mensagem;
    }



}
