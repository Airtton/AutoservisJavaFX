package sample;
import java.io.*;
import java.util.*;

public class Autod {
    String mudel;
    Integer hind;
    String png;
    String pngFlip;

    public Autod(String mudel, Integer hind, String png, String pngFlip) {
        this.mudel = mudel;
        this.hind = hind;
        this.png = png;
        this.pngFlip = pngFlip;
    }

    public static ArrayList<String> autoNimed() throws FileNotFoundException {
        File fail = new File("Autod.txt");
        ArrayList<String> Autonimekiri = new ArrayList<String>();
        Scanner sc = new Scanner(fail, "UTF-8");
        while (sc.hasNextLine()) {
            String rida = sc.nextLine();
            Autonimekiri.add(rida);
        }
        return Autonimekiri;

    }

}