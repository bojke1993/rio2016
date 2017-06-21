/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import beans.*;
import database.DB;
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
@ManagedBean(name = "reg")
@SessionScoped
public class Registration {

    private String username;
    private String password1;
    private String password2;
    private UIComponent msg_pass;
    private UIComponent msg_tip;
    private UIComponent msg;
    private String ime;
    private String prezime;
    private String email;
    private String zemlja;
    private String tip;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public UIComponent getMsg_pass() {
        return msg_pass;
    }

    public void setMsg_pass(UIComponent msg_pass) {
        this.msg_pass = msg_pass;
    }

    public UIComponent getMsg_tip() {
        return msg_tip;
    }

    public void setMsg_tip(UIComponent msg_tip) {
        this.msg_tip = msg_tip;
    }

    public UIComponent getMsg() {
        return msg;
    }

    public void setMsg(UIComponent msg) {
        this.msg = msg;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZemlja() {
        return zemlja;
    }

    public void setZemlja(String zemlja) {
        this.zemlja = zemlja;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String registruj() {
        String ret = "";
        FacesContext context = FacesContext.getCurrentInstance();
        Connection c = DB.getInstance().getConnection();
        try {
            int check = proveraLozinke(password1);
            if (password1.equals(password2) && check != 0) {
                 
                if (tip.equals("org")) {
                    ResultSet rs = c.createStatement().executeQuery("select * from korisnik where tip = 'org'");
                    if (rs.next()) {
                        context.addMessage(msg_tip.getClientId(), new FacesMessage("Organizator vec postoji!"));
                        ret = "registracija";
                    } else {
                        c.createStatement().executeUpdate("insert into korisnik (korisnicko_ime, lozinka, ime, prezime, nacija, email, tip) "
                                + "values ('" + username + "','" + password1 + "','" + ime + "','" + prezime + "','" + zemlja + "','" + email + "','" + tip + "')");
                        ret = "KorisnikPoruka";
                    }
                } else if (tip.equals("vodja")) {
                    ResultSet rs = c.createStatement().executeQuery("select * from korisnik where nacija = '" + zemlja + "' and  tip != 'org'");
                    if (rs.next()) {
                        context.addMessage(msg_tip.getClientId(), new FacesMessage("Postoji vodja delegacije za izabranu zemlju!"));
                        ret = "registracija";
                    } else {
                        c.createStatement().executeUpdate("insert into korisnik (korisnicko_ime, lozinka, ime, prezime, nacija, email, tip) "
                                + "values ('" + username + "','" + password1 + "','" + ime + "','" + prezime + "','" + zemlja + "','" + email + "','" + tip + "')");
                        ret = "KorisnikPoruka";
                    }
                } else if (tip.equals("delegat")) {
                    c.createStatement().executeUpdate("insert into korisnik (korisnicko_ime, lozinka, ime, prezime, nacija, email, tip) "
                            + "values ('" + username + "','" + password1 + "','" + ime + "','" + prezime + "','" + zemlja + "','" + email + "','" + tip + "')");
                    ret = "KorisnikPoruka";
                }

            } else {
                if(check == 0){
                    context.addMessage(msg_pass.getClientId(), new FacesMessage("Lozinka mora da ima 8 - 12 karaktera. min [A-Z]=1, min[a-z]=3, min[0-9]=2 i min[#$*]=2\n"
                            + " Početni karakter mora biti slovo. Maksimalan broj uzastopnih karaktera je tri."));
                }else{
                    context.addMessage(msg_pass.getClientId(), new FacesMessage("Lozinke se ne poklapaju!"));
                }
                ret = "registracija";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        return ret;
    }

    public int proveraLozinke(String loz) {
        int ret = 1;
        if (loz.length() < 6 || loz.length() > 12) {
            //FacesContext.getCurrentInstance().addMessage(msg_pass.getClientId(), new FacesMessage("Lozinka mora imati najmanje 6 a najvise 12 karaktera!"));
            ret = 0;
        }
        if (loz.matches("^([^0-9]*|[^A-Z]*|[^a-z]*)$") || !(loz.matches("[a-z].+") || (loz.matches("[A-Z].+")))){ //{6,12}
            //FacesContext.getCurrentInstance().addMessage(msg_pass.getClientId(), new FacesMessage("\"Lozinka mora sadrzati barem jedno veliko i malo slovo, barem jednu cifru, krv srebrnog jednoroga uzetu u noci punog crvenog meseca pod krosnjom vrbe koja place i mora pocinjati slovom!\""));
            ret = 0;
        }
        if (loz.matches("(.)\\1{3,}")) {
            //FacesContext.getCurrentInstance().addMessage(msg_pass.getClientId(), new FacesMessage("\"Lozinka ne sme imati vise od 3 uzastopna karaktera"));
            ret = 0;
        }
        return ret;
    }
}
