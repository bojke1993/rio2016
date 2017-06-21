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
public class Ekipa {
    private String zemlja;
    private String sport;
    private String konkurencija;
    private int potvrda;

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

    public String getKonkurencija() {
        return konkurencija;
    }

    public void setKonkurencija(String konkurencija) {
        this.konkurencija = konkurencija;
    }

    public int getPotvrda() {
        return potvrda;
    }

    public void setPotvrda(int potvrda) {
        this.potvrda = potvrda;
    }
    
    public void fillFromRs(ResultSet rs) throws SQLException{
        zemlja = rs.getString("zemlja");
        sport = rs.getString("sport");
        potvrda = rs.getInt("potvrda");
    }
}
