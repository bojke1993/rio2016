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
public class Korisnik {
    private String korisnicko_ime;
    private String lozinka;
    private String ime;
    private String prezime;
    private String nacija;
    private String email;
    private String tip;
    
    public void fillFromRs(ResultSet rs) throws SQLException {
        korisnicko_ime = rs.getString("korisnicko_ime");
        lozinka = rs.getString("lozinka");
        ime = rs.getString("ime");
        prezime = rs.getString("prezime");
        nacija = rs.getString("nacija");
        email = rs.getString("email");
        tip = rs.getString("tip");
    }

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getNacija() {
        return nacija;
    }

    public void setNacija(String nacija) {
        this.nacija = nacija;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
    
    
}
