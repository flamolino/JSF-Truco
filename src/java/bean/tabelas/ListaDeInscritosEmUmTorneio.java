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

    private String nome;
    private int ordem;

    public ListaDeInscritosEmUmTorneio() {
        this.nome = "";
        this.ordem = -1;
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
}
