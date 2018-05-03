/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.tabelas;

/**
 *
 * @author 04783714118
 */
public class ListaDeTorneios {

    private String nome, descricao;
    private int qtdInscritos, limiteDuplas;

    public ListaDeTorneios() {
        this.nome = "";
        this.descricao = "";
        this.qtdInscritos = -1;
        this.limiteDuplas = -1;
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
     * @return the qtdInscritos
     */
    public int getQtdInscritos() {
        return qtdInscritos;
    }

    /**
     * @param qtdInscritos the qtdInscritos to set
     */
    public void setQtdInscritos(int qtdInscritos) {
        this.qtdInscritos = qtdInscritos;
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

}
