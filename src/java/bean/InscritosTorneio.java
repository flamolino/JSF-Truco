/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author 04783714118
 */
public class InscritosTorneio {

    private int id, idDupla, idTorneio, ordem;

    public InscritosTorneio() {
        this.id = -1;
        this.idDupla = -1;
        this.idTorneio = -1;
        this.ordem = 0;
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
     * @return the idDupla
     */
    public int getIdDupla() {
        return idDupla;
    }

    /**
     * @param idDupla the idDupla to set
     */
    public void setIdDupla(int idDupla) {
        this.idDupla = idDupla;
    }

    /**
     * @return the idTorneio
     */
    public int getIdTorneio() {
        return idTorneio;
    }

    /**
     * @param idTorneio the idTorneio to set
     */
    public void setIdTorneio(int idTorneio) {
        this.idTorneio = idTorneio;
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
