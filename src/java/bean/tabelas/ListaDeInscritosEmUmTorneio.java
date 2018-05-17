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
public class ListaDeInscritosEmUmTorneio {

    private String nome, logo;
    private int ordem, idInscrito;

    public ListaDeInscritosEmUmTorneio() {
        this.nome = "";
        this.logo = "";
        this.ordem = -1;
        this.idInscrito = -1;
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
     * @return the ordem
     */
    public int getOrdem() {
        return ordem;
    }

    /**
     * @param ordem the ordem to set
     */
    public void setOrdem(int ordem) {
        this.ordem = ordem;
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
     * @return the idInscrito
     */
    public int getIdInscrito() {
        return idInscrito;
    }

    /**
     * @param idInscrito the idInscrito to set
     */
    public void setIdInscrito(int idInscrito) {
        this.idInscrito = idInscrito;
    }
}
