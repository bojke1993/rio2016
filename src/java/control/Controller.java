/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.*;
import beans.*;
import database.DB;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@ManagedBean(name = "controller")
@SessionScoped
public class Controller {

    private String username;
    private String password;
    private UIComponent msg_user, msg_pass, msg_ekipa;
    private Korisnik curUser;
    private List<String> sveZemlje;
    private List<Sport> allSports;
    private List<Korisnik> allDelegates;
    private List<String> dis;
    private List<Sportista> sviSportisti;
    private List<Sportista> nadjeniSportisti;
    private String imePrez;
    private String sport;
    private String disciplina;
    private String pol;
    private Sportista noviSportista = new Sportista();
    private int ukupanBr;
    private int flag = 0;
    private List<Sport> delegatoviSportovi;
    private List<Sport> ekipniSportovi;

    public UIComponent getMsg_ekipa() {
        return msg_ekipa;
    }

    public void setMsg_ekipa(UIComponent msg_ekipa) {
        this.msg_ekipa = msg_ekipa;
    }
    
    
    public List<Sport> getEkipniSportovi() {
        return ekipniSportovi;
    }

    public void setEkipniSportovi(List<Sport> ekipniSportovi) {
        this.ekipniSportovi = ekipniSportovi;
    }
    

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<Sport> getDelagatoviSportovi() {
        return delegatoviSportovi;
    }

    public void setDelagatoviSportovi(List<Sport> delegatoviSportovi) {
        this.delegatoviSportovi = delegatoviSportovi;
    }

    public int getFleg() {
        return flag;
    }

    public void setFleg(int flag) {
        this.flag = flag;
    }

    public int getUkupanBr() {
        return ukupanBr;
    }

    public void setUkupanBr(int ukupanBr) {
        this.ukupanBr = ukupanBr;
    }
    

    public List<Sportista> getSviSportisti() {
        return sviSportisti;
    }

    public void setSviSportisti(List<Sportista> sviSportisti) {
        this.sviSportisti = sviSportisti;
    }

    public List<Sportista> getNadjeniSportisti() {
        return nadjeniSportisti;
    }

    public void setNadjeniSportisti(List<Sportista> nadjeniSportisti) {
        this.nadjeniSportisti = nadjeniSportisti;
    }

    public Sportista getNoviSportista() {
        return noviSportista;
    }

    public void setNoviSportista(Sportista noviSportista) {
        this.noviSportista = noviSportista;
    }

    public List<String> getDis() {
        return dis;
    }

    public void setDis(List<String> dis) {
        this.dis = dis;
    }

    public String getImePrez() {
        return imePrez;
    }

    public void setImePrez(String imePrez) {
        this.imePrez = imePrez;
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

    public List<Korisnik> getAllDelegates() {
        return allDelegates;
    }

    public void setAllDelegates(List<Korisnik> allDelegates) {
        this.allDelegates = allDelegates;
    }

    public List<Sport> getAllSports() {
        return allSports;
    }

    public void setAllSports(List<Sport> allSports) {
        this.allSports = allSports;
    }

    public List<String> getSveZemlje() {
        return sveZemlje;
    }

    public void setSveZemlje(List<String> sveZemlje) {
        this.sveZemlje = sveZemlje;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UIComponent getMsg_user() {
        return msg_user;
    }

    public void setMsg_user(UIComponent msg_user) {
        this.msg_user = msg_user;
    }

    public UIComponent getMsg_pass() {
        return msg_pass;
    }

    public void setMsg_pass(UIComponent msg_pass) {
        this.msg_pass = msg_pass;
    }

    public Korisnik getCurUser() {
        return curUser;
    }

    public void setCurUser(Korisnik curUser) {
        this.curUser = curUser;
    }

    public String login() {
        String ret = "";

        Connection c = DB.getInstance().getConnection();
        ResultSet rs;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);

        try {
            rs = c.createStatement().executeQuery("select * from korisnik where korisnicko_ime= '" + username + "'");
            curUser = new Korisnik();
            if (rs.next()) {
                curUser.fillFromRs(rs);

                if (password.equals(curUser.getLozinka())) {
                    sviDelegati();
                    sviSportovi();
                    session.setAttribute("user", curUser);
                    if (curUser.getTip().equals("vodja")) {
                        
                        ret = "home_vodja";
                    } else if (curUser.getTip().equals("delegat")) {
                        delSportovi();
                        ret = "home_delegat";
                    } else if (curUser.getTip().equals("org")) {
                        ret = "home_organizator";
                    }
                } else {
                    context.addMessage(msg_pass.getClientId(), new FacesMessage("Pogresna lozinka!"));
                }
            } else {
                context.addMessage(msg_user.getClientId(), new FacesMessage("Ne postoji takvo korisnicko ime!"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        return ret;
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        return "index";
    }

    public void napuniListuSaZemljama() {
        sveZemlje = new ArrayList<>();
        sveZemlje.clear();
        Connection c = DB.getInstance().getConnection();
        try {
            ResultSet rs = c.createStatement().executeQuery("select * from zemlja");
            while (rs.next()) {
                String tmp = rs.getString("naziv");
                sveZemlje.add(tmp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
    }

    public void sviSportovi() {
        Connection c = DB.getInstance().getConnection();
        allSports = new ArrayList<>();
        allSports.clear();
        try {
            ResultSet rs = c.createStatement().executeQuery("select * from sportovi ");
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

    public void sviDelegati() {
        Connection c = DB.getInstance().getConnection();
        allDelegates = new ArrayList<>();
        allDelegates.clear();
        try {
            ResultSet rs = c.createStatement().executeQuery("select * from korisnik where tip = 'delegat'");
            while (rs.next()) {
                Korisnik tmp = new Korisnik();
                tmp.fillFromRs(rs);
                allDelegates.add(tmp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
    }

    public String ubaciSportistu() {
        noviSportista.setImePrezime(imePrez);
        noviSportista.setSport(sport);
        noviSportista.setDis(disciplina);
        noviSportista.setZemlja(curUser.getNacija());
        noviSportista.setPol(pol);
        Connection c = DB.getInstance().getConnection();
        String ret = "";
        try {
            if (disciplina == null) {
                c.createStatement().executeUpdate("INSERT INTO sportisti (imePrezime, zemlja, sport, pol) "
                        + "VALUES ('" + imePrez + "','" + curUser.getNacija() + "','" + sport + "','" + pol + "')");
                ret = "porukaVodja";
            } else {
                c.createStatement().executeUpdate("INSERT INTO sportisti (imePrezime, zemlja, sport, disciplina, pol) "
                        + "VALUES ('" + imePrez + "','" + curUser.getNacija() + "','" + sport + "','" + disciplina + "','" + pol + "')");
                ret = "porukaVodja";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        return ret;
    }

    public void trenutneDiscipline() {
        Connection c = DB.getInstance().getConnection();
        dis = new ArrayList<>();
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

    public String napuniListuSaSportistima() {
        Connection c = DB.getInstance().getConnection();

        sviSportisti = new ArrayList<>();
        sviSportisti.clear();
        try {
            ResultSet rs = c.createStatement().executeQuery("select * from sportisti where zemlja = '" + curUser.getNacija() + "' order by sport");

            while (rs.next()) {
                Sportista tmp = new Sportista();
                tmp.fillFromRs(rs);
                sviSportisti.add(tmp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        return "sportisti";
    }

    public void brUcesnika() {
        Connection c = DB.getInstance().getConnection();
        try {
            int cnt1 = 0;
            ResultSet rs1 = c.createStatement().executeQuery("select * from sportisti where zemlja = '"+curUser.getNacija()+"'");
            while(rs1.next()){
                cnt1++;
            }
            ukupanBr = cnt1;
            for (Sport s : allSports) {
                int cnt = 0;
                ResultSet rs = c.createStatement().executeQuery("select * from sportisti where sport ='"+s.getNaziv()+"' and zemlja = '"+curUser.getNacija()+"'");
                while(rs.next()){
                    cnt++;
                }
                s.setBrUcesnika(cnt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
    }
    
    public String filtrirajSport(){
        trenutneDiscipline();
        Connection c = DB.getInstance().getConnection();
        nadjeniSportisti = new ArrayList<>();
        nadjeniSportisti.clear();
        try{
            ResultSet rs = c.createStatement().executeQuery("select * from sportisti where zemlja = '"+curUser.getNacija()+"' and sport = '"+sport+"'");
            while(rs.next()){
                Sportista tmp = new Sportista();
                tmp.fillFromRs(rs);
                nadjeniSportisti.add(tmp);
            }
        }catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        flag = 1;
        return "sviSportovi";
    }
    
    public String filtrirajDisc(){
        Connection c = DB.getInstance().getConnection();
        //nadjeniSportisti = new ArrayList<>();
        nadjeniSportisti.clear();
        try{
            ResultSet rs = c.createStatement().executeQuery("select * from sportisti where zemlja = '"+curUser.getNacija()+"' and disciplina = '"+disciplina+"'");
            while(rs.next()){
                Sportista tmp = new Sportista();
                tmp.fillFromRs(rs);
                nadjeniSportisti.add(tmp);
            }
        }catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        flag = 1;
        return "sveDiscipline";
    }
    public void delSportovi() {
        Connection c = DB.getInstance().getConnection();
        delegatoviSportovi = new ArrayList<>();
        delegatoviSportovi.clear();
        try {
            ResultSet rs = c.createStatement().executeQuery("select * from sportovi where delegat = '"+curUser.getKorisnicko_ime()+"'");
            while (rs.next()) {
                Sport tmp = new Sport();
                tmp.fillFromRs(rs);
                delegatoviSportovi.add(tmp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
    }
    
    public String prijaviEkipniSport(){
        ekipniSportovi = new ArrayList<>();
        ekipniSportovi.clear();
        for(Sport i:allSports){
            if (i.getVrsta().equals("ekipni")){
                ekipniSportovi.add(i);
            }
        }
        return "prijaviEkipe";
    }
    
    public String prijaviEkipu(){
        Connection c = DB.getInstance().getConnection();
        try {
            c.createStatement().executeUpdate("INSERT INTO ekipe (zemlja, sport)"
                    + " VALUES ('"+curUser.getNacija()+"','"+sport+"')");
            //FacesContext.getCurrentInstance().addMessage(msg_ekipa.getClientId(), new FacesMessage("uspesno ste uneli podatak u bazu"));
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        return "ekipaPoruka";
    }
    
}
