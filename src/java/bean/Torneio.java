package bean;

public class Torneio {

    private int id, limiteDuplas, finalizado, criador;
    private String nome, descricao;
    private String data = null;

    public Torneio() {
        this.id = -1;
        this.limiteDuplas = 0;
        this.finalizado = 0;
        this.criador = -1;
        this.nome = "";
        this.descricao = "";
        this.data = "";
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
     * @return the limiteDuplas
     */
    public int getLimiteDuplas() {
        return limiteDuplas;
    }

    /**
     * @param limiteDuplas the limiteDuplas to set
     */
    public void setLimiteDuplas(int limiteDuplas) {
        this.limiteDuplas = limiteDuplas;
    }

    /**
     * @return the finalizado
     */
    public int getFinalizado() {
        return finalizado;
    }

    /**
     * @param finalizado the finalizado to set
     */
    public void setFinalizado(int finalizado) {
        this.finalizado = finalizado;
    }

    /**
     * @return the criador
     */
    public int getCriador() {
        return criador;
    }

    /**
     * @param criador the criador to set
     */
    public void setCriador(int criador) {
        this.criador = criador;
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
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
