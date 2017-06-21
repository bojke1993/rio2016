/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import beans.Sportista;
import database.DB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Ivan
 */
@ManagedBean(name = "pretraga")
@SessionScoped
public class Pretraga {

    private String imePrezime;
    private String zemlja;
    private String sport;
    private String disciplina;
    private String pol;
    private List<String> dis;
    private List<String> allSports;
    private List<Sportista> pronadjeniSportisti = null;
    private boolean imaRes = false;

    public boolean isImaRes() {
        return imaRes;
    }

    public void setImaRes(boolean imaRes) {
        this.imaRes = imaRes;
    }

    public List<Sportista> getPronadjeniSportisti() {
        return pronadjeniSportisti;
    }

    public void setPronadjeniSportisti(List<Sportista> pronadjeniSportisti) {
        this.pronadjeniSportisti = pronadjeniSportisti;
    }

    public List<String> getAllSports() {
        return allSports;
    }

    public void setAllSports(List<String> allSports) {
        this.allSports = allSports;
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

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public List<String> getDis() {
        return dis;
    }

    public void setDis(List<String> dis) {
        this.dis = dis;
    }

    public String idiNapretragu() {
        Connection c = DB.getInstance().getConnection();
        allSports = new ArrayList<>();
        dis = new ArrayList<>();
        dis.clear();
        allSports.clear();
        try {
            ResultSet rs = c.createStatement().executeQuery("select * from sportovi");
            while (rs.next()) {
                String tmp = rs.getString("naziv");
                allSports.add(tmp);
            }
            ResultSet rs1 = c.createStatement().executeQuery("select * from discipline");
            while (rs1.next()) {
                String tmp = rs1.getString("naziv");
                dis.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        return "pretraga";
    }

    public void trenutneDiscipline() {
        Connection c = DB.getInstance().getConnection();
        dis.clear();
        try {
            ResultSet rs = c.createStatement().executeQuery("select * from discipline where sport = '" + sport + "'");
            while (rs.next()) {
                String tmp = rs.getString("naziv");
                dis.add(tmp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
    }

    public void pretrazi() {
        Connection c = DB.getInstance().getConnection();
        pronadjeniSportisti = new ArrayList<>();
        pronadjeniSportisti.clear();
        try {
            ResultSet rs;
            String query = "";
            if(!imePrezime.equals("")){
                query = "select * from sportisti where imePrezime like '%"+imePrezime+"%'";
            }else{
                if(pol != null && zemlja != null && sport != null && disciplina != null){
                    query = "select * from sportisti where pol = '"+pol+"' and zemlja ='"+zemlja+"' and sport = '"+sport+"' and disciplina ='"+disciplina+"'";
                }else{
                    if(disciplina != null && sport != null){
                        query = "select * from sportisti where sport = '"+sport+"' and disciplina ='"+disciplina+"' order by zemlja";
                    }else{
                        if(pol != null && zemlja != null){
                            query = "select * from sportisti where pol = '"+pol+"' and zemlja ='"+zemlja+"'";
                        }else{
                            if(pol != null){
                                query = "select * from sportisti where pol = '"+pol+"' order by zemlja";
                            }else{
                                if(zemlja != null){
                                    query = "select * from sportisti where zemlja ='"+zemlja+"' order by zemlja";
                                }else{
                                    query = "select * from sportisti order by zemlja";
                                }
                            }
                        }
                    }
                       
                    
                }
            }
                

            rs = c.createStatement().executeQuery(query);
            while (rs.next()) {
                Sportista tmp = new Sportista();
                tmp.fillFromRs(rs);
                pronadjeniSportisti.add(tmp);
            }
            if (!pronadjeniSportisti.isEmpty()) {
                imaRes = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pretraga.class.getName()).log(Level.SEVERE, pol, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
    }

}
