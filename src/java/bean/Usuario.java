package bean;

public class Usuario {

    private int id, idade, duplaAtual, convite;
    private String nome, login, senha, email, avatar, endereco, data;

    public Usuario(int id, int idade, int duplaAtual, int convite, String nome, String login, String senha,
            String email, String avatar, String endereco, String data) {
        this.id = id;
        this.idade = idade;
        this.duplaAtual = duplaAtual;
        this.convite = convite;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.avatar = avatar;
        this.endereco = endereco;
        this.data = data;
    }

    public Usuario() {
        this.id = -1;
        this.idade = 0;
        this.duplaAtual = -1;
        this.convite = -1;
        this.nome = "";
        this.login = "";
        this.senha = "";
        this.email = "";
        this.avatar = "";
        this.endereco = "";
        this.data = "01-01-0001 01:01:01";
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the idade
     */
    public int getIdade() {
        return idade;
    }

    /**
     * @param idade the idade to set
     */
    public void setIdade(int idade) {
        this.idade = idade;
    }

    /**
     * @return the duplaAtual
     */
    public int getDuplaAtual() {
        return duplaAtual;
    }

    /**
     * @param duplaAtual the duplaAtual to set
     */
    public void setDuplaAtual(int duplaAtual) {
        this.duplaAtual = duplaAtual;
    }

    public int getConvite() {
        return convite;
    }

    public void setConvite(int convite) {
        this.convite = convite;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
