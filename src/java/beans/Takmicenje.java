/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.ResultSet;
import java.sql.SQLException;


import java.sql.Date;


/**
 *
 * @author Ivan
 */
public class Takmicenje {
    private String sport;
    private String disciplina;
    private Date datumOd;
    private Date datumDo;
    private String konkurencija;
    private String vrsta;
    private String lokacija;

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public Date getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(Date datumOd) {
        this.datumOd = datumOd;
    }

    public Date getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(Date datumDo) {
        this.datumDo = datumDo;
    }

    public String getKonkurencija() {
        return konkurencija;
    }

    public void setKonkurencija(String konkurencija) {
        this.konkurencija = konkurencija;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }


    public void fillFromRs(ResultSet rs) throws SQLException{
        sport = rs.getString("sport");
        disciplina = rs.getString("disciplina");
        datumOd = rs.getDate("datumOd");
        datumDo = rs.getDate("datumDo");
        konkurencija = rs.getString("konkurencija");
        vrsta = rs.getString("vrsta");
        lokacija = rs.getString("lokacija");
    }
}
