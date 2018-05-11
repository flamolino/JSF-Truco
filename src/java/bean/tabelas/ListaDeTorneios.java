/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.tabelas;

import java.util.Date;
import outro.Utilities;

/**
 *
 * @author 04783714118
 */
public class ListaDeTorneios {

    private String nome, descricao, dataEncerraInsc;
    private int qtdInscritos, limiteDuplas;
    private boolean cheio;

    public ListaDeTorneios() {
        this.nome = "";
        this.descricao = "";
        this.qtdInscritos = -1;
        this.limiteDuplas = -1;
        this.dataEncerraInsc = "01-01-0001 01:01:01";
        this.cheio = false;

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

    /**
     * @return the dataEncerraInsc
     */
    public String getDataEncerraInsc() {
        return dataEncerraInsc;
    }

    /**
     * @param dataEncerraInsc the dataEncerraInsc to set
     */
    public void setDataEncerraInsc(String dataEncerraInsc) {
        this.dataEncerraInsc = dataEncerraInsc;
    }

    /**
     * @return the cheio
     */
    public boolean isCheio() {

        return this.verificaSeDataJaUltrapassouAtual() || this.cheio;
    }

    public boolean isJustCheio() {
        return this.cheio;
    }

    /**
     * @param cheio the cheio to set
     */
    public void setCheio(boolean cheio) {
        this.cheio = cheio;
    }

    private boolean verificaSeDataJaUltrapassouAtual() {

        Date dtEnc = Utilities.StringToDate(this.dataEncerraInsc);
        Date dtAtual = Utilities.StringToDate(Utilities.getDataAtualSemHoraString());

        return dtEnc.compareTo(dtAtual) <= 0;
    }

    public ListaDeTorneios getObjLstTorneio() {
        return this;
    }

}
