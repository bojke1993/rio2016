/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Ivan
 */
public class Sport {
    private String naziv;
    private List<String> discipline;
    private String delegat;
    private String vrsta;
    private int brUcesnika;
    private int imaDel=0;

    public int getBrUcesnika() {
        return brUcesnika;
    }

    public void setBrUcesnika(int brUcesnika) {
        this.brUcesnika = brUcesnika;
    }
    
    

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public int getImaDel() {
        return imaDel;
    }

    public void setImaDel(int imaDel) {
        this.imaDel = imaDel;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public List<String> getDiscipline() {
        return discipline;
    }

    public void setDiscipline(List<String> discipline) {
        this.discipline = discipline;
    }

    public String getDelegat() {
        return delegat;
    }

    public void setDelegat(String delegat) {
        this.delegat = delegat;
    }
    
    public void fillFromRs(ResultSet rs) throws SQLException{
        naziv = rs.getString("naziv");
        vrsta = rs.getString("vrsta");
        delegat = rs.getString("delegat");
        imaDel = rs.getInt("ima_del");
        
    }
    
    
}
