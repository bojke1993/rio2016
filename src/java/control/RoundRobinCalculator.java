/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import beans.*;

/**
 * Klasa RoundRobinCalculator je singleton klasa i odredjuje nasumican raspored
 * igranja utakmica izmedju timova. Prvo nasumicno rasporedjuje timove u grupe,
 * a zatim odredjuje nasumican raspored po rundama. Timovi se u ovoj klasi
 * tretiraju kao String tj. na osnovu njihovog naziva.
 *
 * @author rados
 */
public class RoundRobinCalculator {

    // instanca Singleton-a
    private static RoundRobinCalculator instance;
    // lista timova
    private List<Ekipa> teams;
    // broj grupa na koliko treba podeliti timove
    private int numberOfGroups;
    // broj timova po grupi
    private int groupSize;
    // matrica grupa
    public Ekipa[][] groups;
    // broj kola u grupi, broj utakmica u jednom kolu grupe
    private int roundsPerGroup, gamesPerRound;
    // kolekcija svih grupa koja skladisti matrice za svako kolo i svaku utakmicu
    public List<Ekipa[][][]> fullSchedule;
    private boolean notSet = true;

    private RoundRobinCalculator() {
        fullSchedule = new ArrayList<>();
    }

    /**
     * Geter ka instanci Signleton-a
     *
     * @return RoundRobinCalculator instancu
     */
    public static RoundRobinCalculator getInstance() {
        if (instance == null) {
            instance = new RoundRobinCalculator();
        }
        return instance;
    }

    /**
     * Metoda koja postavlja parametre na osnovu kojih se vrsi podela u grupe.
     * Broj timova u listi mora da bude deljiv sa brojem grupa (u protivnom je
     * potrebno da se lista dopuni sa praznim stringovima ili null objektima)
     *
     * @param teams - lista timova koji se takmice
     * @param numberOfGroups - broj grupa u okviru kojih treba rasporediti
     * timove.
     */
    public void addParams(List<Ekipa> teams, int numberOfGroups) {
        if (teams.size() % numberOfGroups != 0) {
            System.out.println("Neodgovarajuci broj timova i grupa!");
            return;
        }
        this.teams = teams;
        this.numberOfGroups = numberOfGroups;
        this.groupSize = teams.size() / numberOfGroups;
        divideIntoGroups();
        this.roundsPerGroup = groupSize - 1;
        this.gamesPerRound = groupSize / 2;
        notSet = false;
    }

    /**
     * Metoda koja vrsi podelu timova po grupama.
     */
    private void divideIntoGroups() {
        Collections.shuffle(teams);
        boolean oddGroupMembers = groupSize % 2 != 0;
        this.groups = oddGroupMembers ? new Ekipa[numberOfGroups][++groupSize] : new Ekipa[numberOfGroups][groupSize];
        int counter = 0;
        for (int i = 0; i < groups.length; i++) {
            for (int j = 0; j < groups[i].length; j++) {
                if (oddGroupMembers && j == groups[i].length - 1) {
                    groups[i][j] = null;
                } else {
                    groups[i][j] = teams.get(counter++);
                }
            }
        }
    }

    /**
     * Generise nasumican raspored utakmica po u okviru jedne grupe.
     *
     * @param group - niz sa timovima u grupi
     * @return 3D matricu stringova za prikaz rundi, utakmica i timova
     */
    public Ekipa[][][] generateSignleGroupSchedule(Ekipa[] group) {
        Ekipa[][] helpMatrix = new Ekipa[2][group.length / 2];
        Ekipa[][][] result = new Ekipa[roundsPerGroup][gamesPerRound][2];
        // inicijalizacija pomocne matrice
        for (int i = 0; i < helpMatrix[0].length; i++) {
            helpMatrix[0][i] = group[i];
            helpMatrix[1][i] = group[i + helpMatrix[0].length];
        }
        //printMatrix(helpMatrix);
        // algoritam
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j][0] = helpMatrix[0][j];
                result[i][j][1] = helpMatrix[1][j];
            }
            matrixTransform(helpMatrix);
        }
        return result;
    }

    /**
     * Generise nasumican raspored utakmica po svim grupama.
     */
    public void generateSchedule() {
        if (notSet) {
            System.out.println("Niste postavili parametre.");
            return;
        }
        for (int i = 0; i < groups.length; i++) {
            fullSchedule.add(generateSignleGroupSchedule(groups[i]));
        }
    }

    // UTIL PART
    /**
     * Stampa timove po grupama.
     */
    public void printGroups() {
        if (notSet) {
            System.out.println("Niste postavili parametre.");
            return;
        }
        for (int i = 0; i < groups.length; i++) {
            System.out.println("GRUPA " + (i + 1) + ": ");
            for (int j = 0; j < groups[i].length; j++) {
                System.out.println("\t" + groups[i][j]);
            }
        }
    }

    // Stampanje elemenata matrice
    private <T> void printMatrix(T[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("");
        }
    }

    // Pregledno stampa raspored na osnovu 3D niza.
    private <T> void formatGroupSchedule(T[][][] schedule) {
        for (int i = 0; i < schedule.length; i++) {
            System.out.println((i + 1) + ". kolo:");
            for (int j = 0; j < schedule[i].length; j++) {
                System.out.print("\t Utakmica " + (j + 1) + ": ");
                System.out.print(schedule[i][j][0] + " - " + schedule[i][j][1]);
                System.out.println("");
            }
        }
    }

    // Stampa raspored utakmica u svim grupama
    public void formatFullSchedule() {
        if(notSet){
            System.out.println("Niste postavili parametre.");
            return;
        }
        int i = 0;
        for (Ekipa[][][] group : fullSchedule) {
            System.out.println("GRUPA " + (++i) + ": ");
            formatGroupSchedule(group);
        }
    }

    // Metoda koja transformise matricu za potrebe algoritma
    private <T> void matrixTransform(T[][] matrix) {
        T help1 = matrix[1][0];
        T help2 = matrix[0][matrix[0].length - 1];
        for (int i = matrix[0].length - 1; i > 1; i--) {
            matrix[0][i] = matrix[0][i - 1];
        }
        matrix[0][1] = help1;
        for (int i = 0; i < matrix[0].length - 1; i++) {
            matrix[1][i] = matrix[1][i + 1];
        }
        matrix[1][matrix[0].length - 1] = help2;
    }
}
