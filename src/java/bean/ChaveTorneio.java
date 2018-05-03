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

    private int id, idDupla1, idDupla2, winner, loser, fase;

    public ChaveTorneio() {
        this.id = -1;
        this.idDupla1 = -1;
        this.idDupla2 = -1;
        this.winner = -1;
        this.loser = -1;
        this.fase = -1;
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
     * @return the winner
     */
    public int getWinner() {
        return winner;
    }

    /**
     * @param winner the winner to set
     */
    public void setWinner(int winner) {
        this.winner = winner;
    }

    /**
     * @return the loser
     */
    public int getLoser() {
        return loser;
    }

    /**
     * @param loser the loser to set
     */
    public void setLoser(int loser) {
        this.loser = loser;
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

}
