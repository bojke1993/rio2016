/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Ivan
 */
public class Sportista {
    private int id;
    private String imePrezime;
    private String zemlja;
    private String sport;
    private String dis;
    private String pol;
    private int zlato;
    private int srebro;
    private int bronza;
    private int izabran;
    private double rez;
    private int imaRez;

    public int getImaRez() {
        return imaRez;
    }

    public void setImaRez(int imaRez) {
        this.imaRez = imaRez;
    }

    public double getRez() {
        return rez;
    }

    public void setRez(double rez) {
        this.rez = rez;
    }

    public int getIzabran() {
        return izabran;
    }

    public void setIzabran(int izabran) {
        this.izabran = izabran;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public void setImePrezime(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    public String getZemlja() {
        return zemlja;
    }

    public void setZemlja(String zemlja) {
        this.zemlja = zemlja;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public int getZlato() {
        return zlato;
    }

    public void setZlato(int zlato) {
        this.zlato = zlato;
    }

    public int getSrebro() {
        return srebro;
    }

    public void setSrebro(int srebro) {
        this.srebro = srebro;
    }

    public int getBronza() {
        return bronza;
    }

    public void setBronza(int bronza) {
        this.bronza = bronza;
    }
    
    public void fillFromRs(ResultSet rs) throws SQLException {
        
        imePrezime = rs.getString("imePrezime");
        zemlja = rs.getString("zemlja");
        sport = rs.getString("sport");
        dis = rs.getString("disciplina");
        pol = rs.getString("pol");
        izabran = rs.getInt("izabran");
        rez = rs.getDouble("rezultat");
        imaRez = rs.getInt("imaRez");
    }
}
