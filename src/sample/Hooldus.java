package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Hooldus {

    String teenus;
    int hind;


    public Hooldus(String teenus, int hind) {
        this.teenus = teenus;
        this.hind = hind;
    }

    public static ArrayList<String> kereTeenus() throws FileNotFoundException {

        File fail = new File("KereHooldus.txt");
        ArrayList<String> kerehooldus = new ArrayList<String>();
        Scanner sc = new Scanner(fail, "UTF-8");

        while (sc.hasNextLine()) {
            String rida = sc.nextLine();
            kerehooldus.add(rida);
        }
        return kerehooldus;

    }


    public static ArrayList<String> mootoriTeenus() throws FileNotFoundException {

        File fail = new File("MootoriHooldus.txt");
        ArrayList<String> mootorihooldus = new ArrayList<String>();
        Scanner sc = new Scanner(fail, "UTF-8");

        while (sc.hasNextLine()) {
            String rida = sc.nextLine();
            mootorihooldus.add(rida);
        }
        return mootorihooldus;
    }


    public static ArrayList<String> salogiTeenus() throws FileNotFoundException {

        File fail = new File("SalongiHooldus.txt");
        ArrayList<String> salongihooldus = new ArrayList<String>();
        Scanner sc = new Scanner(fail, "UTF-8");

        while (sc.hasNextLine()) {
            String rida = sc.nextLine();
            salongihooldus.add(rida);
        }
        return salongihooldus;


    }
}
