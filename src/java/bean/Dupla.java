
package bean;

public class Dupla {

    private int id, jogadorLider, jogador;
    private String nome, logo, data;

    public Dupla(int id, int jogadorLider, int jogador, String nome, String logo, String data) {

        this.id = id;
        this.jogadorLider = jogadorLider;
        this.jogador = jogador;
        this.nome = nome;
        this.logo = logo;
        this.data = data;

    }

    public Dupla() {
        this.id = -1;
        this.jogador = -1;
        this.jogadorLider = -1;
        this.nome = "";
        this.logo = "";
        this.data = "https://milvus.com.br/wordpress/wp-content/uploads/2017/05/avatar-default.jpg";
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
     * @return the jogadorLider
     */
    public int getJogadorLider() {
        return jogadorLider;
    }

    /**
     * @param jogadorLider the jogadorLider to set
     */
    public void setJogadorLider(int jogadorLider) {
        this.jogadorLider = jogadorLider;
    }

    /**
     * @return the jogador
     */
    public int getJogador() {
        return jogador;
    }

    /**
     * @param jogador the jogador to set
     */
    public void setJogador(int jogador) {
        this.jogador = jogador;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo the logo to set
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

}
