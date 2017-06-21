/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import beans.*;
import database.DB;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ivan
 */
@ManagedBean(name = "org")
@SessionScoped
public class Org {

    private UIComponent msgOrg;
    private String noviSport;
    private String vrsta;
    private String novaDisc;
    private List<Sport> allSports;
    private String izabraniDelegat;
    private Takmicenje novoTakmicenje = new Takmicenje();
    private List<Takmicenje> svaTakm;
    private List<Ekipa> ekipe;
    private List<String> dis;
    private List<Sportista> izabraniSportisti;
    private Date datumOd;
    private Date datumDo;
    private String lokacija;
    private String konk;
    private String selectSport;
    private String discSport;
    private boolean ekipni = false;

    public List<Ekipa> getEkipe() {
        return ekipe;
    }

    public void setEkipe(List<Ekipa> ekipe) {
        this.ekipe = ekipe;
    }

    public boolean isEkipni() {
        return ekipni;
    }

    public void setEkipni(boolean ekipni) {
        this.ekipni = ekipni;
    }

    public List<Sportista> getIzabraniSportisti() {
        return izabraniSportisti;
    }

    public void setIzabraniSportisti(List<Sportista> izabraniSportisti) {
        this.izabraniSportisti = izabraniSportisti;
    }

    public List<Takmicenje> getSvaTakm() {
        return svaTakm;
    }

    public void setSvaTakm(List<Takmicenje> svaTakm) {
        this.svaTakm = svaTakm;
    }

    public String getDiscSport() {
        return discSport;
    }

    public void setDiscSport(String discSport) {
        this.discSport = discSport;
    }

    public String getKonk() {
        return konk;
    }

    public void setKonk(String konk) {
        this.konk = konk;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
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

    public List<String> getDis() {
        return dis;
    }

    public void setDis(List<String> dis) {
        this.dis = dis;
    }

    public Takmicenje getNovoTakmicenje() {
        return novoTakmicenje;
    }

    public void setNovoTakmicenje(Takmicenje novoTakmicenje) {
        this.novoTakmicenje = novoTakmicenje;
    }

    public String getIzabraniDelegat() {
        return izabraniDelegat;
    }

    public void setIzabraniDelegat(String izabraniDelegat) {
        this.izabraniDelegat = izabraniDelegat;
    }

    public UIComponent getMsgOrg() {
        return msgOrg;
    }

    public void setMsgOrg(UIComponent msgOrg) {
        this.msgOrg = msgOrg;
    }

    public List<Sport> getAllSports() {
        return allSports;
    }

    public void setAllSports(List<Sport> allSports) {
        this.allSports = allSports;
    }

    public String getNoviSport() {
        return noviSport;
    }

    public void setNoviSport(String noviSport) {
        this.noviSport = noviSport;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getNovaDisc() {
        return novaDisc;
    }

    public void setNovaDisc(String novaDisc) {
        this.novaDisc = novaDisc;
    }

    public String getSelectSport() {
        return selectSport;
    }

    public void setSelectSport(String selectSport) {
        this.selectSport = selectSport;
    }

    public String unesiNoviSport() {
        Connection c = DB.getInstance().getConnection();
        String ret = "";
        int cnt = 0;
        try {
            for (Sport s : allSports) {
                if (noviSport.equals(s.getNaziv())) {
                    cnt++;
                }
            }
            if (cnt > 0) {
                ret = "greska";
            } else {
                c.createStatement().executeUpdate("INSERT INTO sportovi (naziv, vrsta, ima_del) VALUES ('" + noviSport + "','" + vrsta + "','0')");
                ret = "Poruka";
            }
            noviSport = "";

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        sviSportovi();
        return ret;
    }

    public String unesiNovuDisciplinu() {
        Connection c = DB.getInstance().getConnection();
        String ret = "";
        try {
            c.createStatement().executeUpdate("INSERT INTO discipline (naziv, sport) VALUES ('" + novaDisc + "','" + discSport + "')");
            ret = "Poruka";
            novaDisc = "";
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        return ret;
    }

    public void sviSportovi() {
        Connection c = DB.getInstance().getConnection();
        allSports = new ArrayList<>();
        allSports.clear();
        try {
            ResultSet rs = c.createStatement().executeQuery("select * from sportovi");
            while (rs.next()) {
                Sport tmp = new Sport();
                tmp.fillFromRs(rs);
                allSports.add(tmp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
    }

    public void trenutneDiscipline() {
        Connection c = DB.getInstance().getConnection();
        dis = new ArrayList<>();
        dis.clear();
        //dis.add("400m");
        try {
            ResultSet rs = c.createStatement().executeQuery("select * from discipline where sport = '" + noviSport + "'");
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

    public String izaberiDelegata(Sport s) {
        Connection c = DB.getInstance().getConnection();
        try {
            c.createStatement().executeUpdate("UPDATE sportovi SET delegat = '" + izabraniDelegat + "', ima_del = '1' WHERE naziv = '" + s.getNaziv() + "'");
            //sviSportovi();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        sviSportovi();
        return "";
    }

    public String ubaciTakmicenje() throws ParseException {
        novoTakmicenje.setSport(noviSport);
        novoTakmicenje.setDisciplina(novaDisc);
        novoTakmicenje.setKonkurencija(konk);
        novoTakmicenje.setLokacija(lokacija);
        novoTakmicenje.setVrsta(vrsta);
        //novoTakmicenje.setDatumDo(datumDo);
        //novoTakmicenje.setDatumOd(datumOd);
        String ret = "";
        Connection c = DB.getInstance().getConnection();

        java.sql.Date Od = new java.sql.Date(datumOd.getTime());
        java.sql.Date Do = new java.sql.Date(datumDo.getTime());
        try {
            if (novoTakmicenje.getDisciplina() != null) {
                c.createStatement().executeUpdate("INSERT INTO takmicenje (sport, konkurencija, disciplina, datumOd, datumDo, vrsta, lokacija) "
                        + "VALUES ('" + novoTakmicenje.getSport() + "','" + novoTakmicenje.getKonkurencija() + "','" + novoTakmicenje.getDisciplina() + "','" + Od + "','" + Do + "',"
                        + "'" + novoTakmicenje.getVrsta() + "','" + novoTakmicenje.getLokacija() + "')");
            } else {
                c.createStatement().executeUpdate("INSERT INTO takmicenje (sport, konkurencija, datumOd, datumDo, vrsta, lokacija) "
                        + "VALUES ('" + novoTakmicenje.getSport() + "','" + novoTakmicenje.getKonkurencija() + "','" + Od + "','" + Do + "',"
                        + "'" + novoTakmicenje.getVrsta() + "','" + novoTakmicenje.getLokacija() + "')");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        ret = prikaziSvaTakmicenja();
        return ret;
    }

    public String prikaziSvaTakmicenja() {
        Connection c = DB.getInstance().getConnection();
        svaTakm = new ArrayList<>();
        svaTakm.clear();
        try {
            ResultSet rs = c.createStatement().executeQuery("select * from takmicenje");
            while (rs.next()) {
                Takmicenje tmp = new Takmicenje();
                tmp.fillFromRs(rs);
                svaTakm.add(tmp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        return "takm";
    }

    public String izaberiSport(String sp, String dis, String pol) {
        Connection c = DB.getInstance().getConnection();
        izabraniSportisti = new ArrayList<>();
        ekipe = new ArrayList<>();
        ekipe.clear();
        izabraniSportisti.clear();
        try {
            if (sp.equals("kosarka") || sp.equals("vaterpolo") || sp.equals("odbojka")) {
                ResultSet rs = c.createStatement().executeQuery("select * from ekipe where sport = '" + sp + "'");
                while (rs.next()) {
                    Ekipa tmp1 = new Ekipa();
                    tmp1.fillFromRs(rs);
                    if (tmp1.getPotvrda() == 0) {
                        ekipe.add(tmp1);
                    }
                }
                ekipni = true;
            } else {
                ResultSet rs = c.createStatement().executeQuery("select * from sportisti where disciplina = '" + dis + "' and pol = '" + pol + "'");
                while (rs.next()) {
                    Sportista tmp = new Sportista();
                    tmp.fillFromRs(rs);
                    if (tmp.getIzabran() == 0) {
                        izabraniSportisti.add(tmp);
                    }
                    ekipni = false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        return "izabranoTakmicenje";
    }

    public String potvrdiSportistu(Sportista sp) {
        Connection c = DB.getInstance().getConnection();
        try {
            c.createStatement().executeUpdate("UPDATE sportisti SET izabran = '1' WHERE imePrezime = '" + sp.getImePrezime() + "'");

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        izabraniSportisti.remove(sp);
        return "izabranoTakmicenje";
    }
    
    public String potvrdiEkipu(Ekipa e) {
        Connection c = DB.getInstance().getConnection();
        try {
            c.createStatement().executeUpdate("UPDATE ekipe SET potvrda = '1' WHERE zemlja = '" + e.getZemlja() + "'");

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        ekipe.remove(e);
        return "izabranoTakmicenje";
    }
}
