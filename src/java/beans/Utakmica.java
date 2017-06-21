/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Ivan
 */
public class Utakmica {
    private int id;
    private Ekipa ekipa1;
    private Ekipa ekipa2;
    private int kolo;
    private int poeni1;
    private int poeni2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ekipa getEkipa1() {
        return ekipa1;
    }

    public void setEkipa1(Ekipa ekipa1) {
        this.ekipa1 = ekipa1;
    }

    public Ekipa getEkipa2() {
        return ekipa2;
    }

    public void setEkipa2(Ekipa ekipa2) {
        this.ekipa2 = ekipa2;
    }

    public int getKolo() {
        return kolo;
    }

    public void setKolo(int kolo) {
        this.kolo = kolo;
    }

    public int getPoeni1() {
        return poeni1;
    }

    public void setPoeni1(int poeni1) {
        this.poeni1 = poeni1;
    }

    public int getPoeni2() {
        return poeni2;
    }

    public void setPoeni2(int poeni2) {
        this.poeni2 = poeni2;
    }
    
    
}
