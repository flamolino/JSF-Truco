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
public class ChaveTorneio {

    private int id, idDupla1, idDupla2, fase, idTorneio, scoreDp1, scoreDp2;

    public ChaveTorneio() {
        this.id = -1;
        this.idDupla1 = -1;
        this.idDupla2 = -1;
        this.fase = 1;
        this.idTorneio = -1;
        this.scoreDp1 = 0;
        this.scoreDp2 = 0;
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
     * @return the idDupla1
     */
    public int getIdDupla1() {
        return idDupla1;
    }

    /**
     * @param idDupla1 the idDupla1 to set
     */
    public void setIdDupla1(int idDupla1) {
        this.idDupla1 = idDupla1;
    }

    /**
     * @return the idDupla2
     */
    public int getIdDupla2() {
        return idDupla2;
    }

    /**
     * @param idDupla2 the idDupla2 to set
     */
    public void setIdDupla2(int idDupla2) {
        this.idDupla2 = idDupla2;
    }

    /**
     * @return the fase
     */
    public int getFase() {
        return fase;
    }

    /**
     * @param fase the fase to set
     */
    public void setFase(int fase) {
        this.fase = fase;
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
     * @return the scoreDp1
     */
    public int getScoreDp1() {
        return scoreDp1;
    }

    /**
     * @param scoreDp1 the scoreDp1 to set
     */
    public void setScoreDp1(int scoreDp1) {
        this.scoreDp1 = scoreDp1;
    }

    /**
     * @return the scoreDp2
     */
    public int getScoreDp2() {
        return scoreDp2;
    }

    /**
     * @param scoreDp2 the scoreDp2 to set
     */
    public void setScoreDp2(int scoreDp2) {
        this.scoreDp2 = scoreDp2;
    }

}
