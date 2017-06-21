/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import database.DB;
import beans.*;
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
@ManagedBean(name="del")
@SessionScoped
public class delegat {
    private int pos1 = 0;
    private String izabraniSport;
    private String izabranaDisc;
    private String konk;
    private List<Ekipa> sviTimovi;
    private List<Ekipa> grupaA;
    private List<Ekipa> grupaB;
    private List<Takmicenje> svaTakmicenja;
    private List<Sportista> takmicari;
    private List<Sportista> tabela;
    private Ekipa[][] grupe;
    private List<Ekipa[][][]> listaUtakmica;
    private Ekipa[][][] matrix;
    private boolean generisao = false;
    private Double res;
    private boolean showTable = false;

    public boolean isShowTable() {
        return showTable;
    }

    public void setShowTable(boolean showTable) {
        this.showTable = showTable;
    }

    public List<Sportista> getTabela() {
        return tabela;
    }

    public Double getRes() {
        return res;
    }

    public void setTabela(List<Sportista> tabela) {
        this.tabela = tabela;
    }

    public void setRes(Double res) {
        this.res = res;
    }
    
    public int rbr(){
        return ++pos1;
    }
    
    public String getIzabranaDisc() {
        return izabranaDisc;
    }

    public void setIzabranaDisc(String izabranaDisc) {
        this.izabranaDisc = izabranaDisc;
    }

    public String getKonk() {
        return konk;
    }

    public void setKonk(String konk) {
        this.konk = konk;
    }

    public List<Sportista> getTakmicari() {
        return takmicari;
    }

    public void setTakmicari(List<Sportista> takmicari) {
        this.takmicari = takmicari;
    }

    public List<Takmicenje> getSvaTakmicenja() {
        return svaTakmicenja;
    }

    public void setSvaTakmicenja(List<Takmicenje> svaTakmicenja) {
        this.svaTakmicenja = svaTakmicenja;
    }

    public Ekipa[][][] getMatrix() {
        return matrix;
    }

    public void setMatrix(Ekipa[][][] matrix) {
        this.matrix = matrix;
    }
    

    public List<Ekipa> getGrupaA() {
        return grupaA;
    }

    public void setGrupaA(List<Ekipa> grupaA) {
        this.grupaA = grupaA;
    }

    public List<Ekipa> getGrupaB() {
        return grupaB;
    }

    public void setGrupaB(List<Ekipa> grupaB) {
        this.grupaB = grupaB;
    }

    public boolean isGenerisao() {
        return generisao;
    }

    public void setGenerisao(boolean generisao) {
        this.generisao = generisao;
    }

    public String getIzabraniSport() {
        return izabraniSport;
    }

    public void setIzabraniSport(String izabraniSport) {
        this.izabraniSport = izabraniSport;
    }

    public List<Ekipa> getSviTimovi() {
        return sviTimovi;
    }

    public void setSviTimovi(List<Ekipa> sviTimovi) {
        this.sviTimovi = sviTimovi;
    }

    public Ekipa[][] getGrupe() {
        return grupe;
    }

    public void setGrupe(Ekipa[][] grupe) {
        this.grupe = grupe;
    }

    public List<Ekipa[][][]> getListaUtakmica() {
        return listaUtakmica;
    }

    public void setListaUtakmica(List<Ekipa[][][]> listaUtakmica) {
        this.listaUtakmica = listaUtakmica;
    }
    
    public String delegatovaTakmicenja(){
        Connection c = DB.getInstance().getConnection();
        svaTakmicenja = new ArrayList<>();
        svaTakmicenja.clear();
        try{
            ResultSet rs = c.createStatement().executeQuery("select * from takmicenje where sport = '"+izabraniSport+"'");
            while(rs.next()){
                Takmicenje tmp = new Takmicenje();
                tmp.fillFromRs(rs);
                svaTakmicenja.add(tmp);
            }
        }catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        return "";
    }
    
    public String generisiGrupe(){
        grupaA = new ArrayList<>();
        grupaA.clear();
        grupaB = new ArrayList<>();
        grupaB.clear();
        RoundRobinCalculator.getInstance().addParams(sviTimovi, 2);
        RoundRobinCalculator.getInstance().generateSchedule();
        grupe = RoundRobinCalculator.getInstance().groups;
        listaUtakmica = RoundRobinCalculator.getInstance().fullSchedule;
        for(int i = 0; i < 6; i++){
            grupaA.add(grupe[0][i]);
            grupaB.add(grupe[1][i]);
        }
        return "del_ekipni";
    }
    
    public String organizujTakmicenje(String sp, String dis, String pol){
        izabranaDisc = dis;
        konk = pol;
        Connection c = DB.getInstance().getConnection();
        sviTimovi = new ArrayList<>();
        sviTimovi.clear();
        takmicari = new ArrayList<>();
        takmicari.clear();
        String ret="";
        try{
            String query = "";
            if(izabraniSport.equals("kosarka") || izabraniSport.equals("odbojka") || izabraniSport.equals("vaterpolo")){
                query = "select * from ekipe where sport = '"+izabraniSport+"' and potvrda = '1'";
                ResultSet rs = c.createStatement().executeQuery(query);
                while(rs.next()){
                    Ekipa tmp = new Ekipa();
                    tmp.fillFromRs(rs);
                    sviTimovi.add(tmp);
                }
                ret = "del_ekipni";
            }else{
                query = "select * from sportisti where disciplina = '"+dis+"' and izabran = '1' and pol = '"+pol+"'";
                ResultSet rs = c.createStatement().executeQuery(query);
                while(rs.next()){
                    Sportista tmp1 = new Sportista();
                    tmp1.fillFromRs(rs);
                    takmicari.add(tmp1);
                }
                ret = "del_indiv";
                if(izabraniSport.equals("tenis") || izabraniSport.equals("stoni tenis")){
                    ret = "playoff_tree";
                }
            }
        }catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        return ret;
    }
    
    public String unesiRezultate(Sportista sp){
        Connection c = DB.getInstance().getConnection();
        try{
            c.createStatement().executeUpdate("UPDATE sportisti SET rezultat = '"+res+"', imaRez = '1' WHERE imePrezime = '"+sp.getImePrezime()+"' ");
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DB.getInstance().putConnection(c);
        }
        trenutniTakmicari();
        return "";
    }
    
    public void trenutniTakmicari(){
        Connection c = DB.getInstance().getConnection();
        takmicari.clear();
        try{
            ResultSet rs = c.createStatement().executeQuery("SELECT * FROM sportisti WHERE disciplina = '"+izabranaDisc+"'");
            while(rs.next()){
                Sportista tmp = new Sportista();
                tmp.fillFromRs(rs);
                takmicari.add(tmp);
            }
        }catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
    }
    
    public String showTableRace(){
        tabela = new ArrayList<>();
        tabela.clear();
        showTable = true;
        Connection c = DB.getInstance().getConnection();
        try{
            ResultSet rs = c.createStatement().executeQuery("SELECT * FROM sportisti WHERE disciplina = '"+izabranaDisc+"'"
                    + " AND pol = '"+konk+"' ORDER BY rezultat ASC");
            while(rs.next()){
                Sportista tmp = new Sportista();
                tmp.fillFromRs(rs);
                tabela.add(tmp);
            }
        }catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.getInstance().putConnection(c);
        }
        
        return "del_indiv";
    }
    
}
