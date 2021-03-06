package sample;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    private Stage window; // Anname lavale nimetuse
    private Text kliendiNimi = new Text(); // kliendi nimi TEXT formaadis
    private Text kasAuto = new Text("Kas tõesti soovid osta seda autot ?"); // Text auto ostu kinnitamiseks
    private Text kasHooldus = new Text("Kas tõesti soovid kasutada seda teenust ?"); // text hoolduse kinnitamiseks
    private Label valitudAuto = new Label(); // kuvab eelnevalt valitud auto ekraanile
    private Label valitudHooldus = new Label(); // kuvab eelnevalt valitud hoolduse ekraanile
    private String millineAuto; // String lisab valitud auto ostukorvi või teeb selle valitudautoks
    private String millineHooldus;   // String lisab valitud hoolduse ostukorvi või teeb selle valitudhoolduseks  //JAOTADA ARA HOOLDUSE MUUUTUJAD JA AUTODE MUUTUJA ERALDI KASTIKESTESSE !!!
    private String[] tükid; // Üks väga tähtis osa stringide tükeldamiseks
    private ArrayList<Integer> summa = new ArrayList<>(); // Lisab int summad listi ja pärast selle listi najal väljastatkse lõppsumma!
    private int hind; // Hind, mis lisatakse kõikide hindade listi.
    private int intMuutuja; // Muututuja,mis summeerib summat.

    ArrayList<String> ostuKorv = new ArrayList<>(); // Ostukorvi list, seal on sees kõik kasutaja valitud ostud/teenused.
    Random rand = new Random(); // Random, genereerib meile pärast arve numbri :)



    private void misAuto(String auto){ // Teeb stringi Text formaadiks ja võtab kohe ka sealt getText.
        valitudAuto.setText(auto);
        millineAuto = valitudAuto.getText();
    }

    private void misHooldus(String hooldus){ // Sama mis misauto ainuld hoolduse jaoks
        valitudHooldus.setText(hooldus);
        millineHooldus = valitudHooldus.getText();

    }

    private void misNimi(String nimi){ // teeb kliendi täisnime(String) Text-iks ja lisab selle ette "klient:". Seda kasutame menüüdes, et klient näks oma nime :)
        kliendiNimi.setText("Klient: "+ nimi);
    }

    public boolean stringisOnNumber( String s ) { // See meetod kontrollib kas sisestatud stringis on numbrid sees või ei ole. Kui on, siis returnib true, kui ei ole, siis returnib false
        Pattern p = Pattern.compile( "[0-9]" );
        Matcher m = p.matcher( s );

        return m.find();
    }
    public boolean mituTähteJärjest(String string){ // See meetod kontrollib, kas string sisaldab 3 või enam samasugust tähte
        Pattern p = Pattern.compile("((\\w)\\2+\\2)+.*");
        Matcher m = p.matcher(string);
        while (m.find()) {
            return true;
        }
        return false;
    }

    public void gridSet(GridPane grid){ // Grid Pane seade, see on igal gridpanel!
        grid.setAlignment(Pos.CENTER);// Määrame stseenile asukoha (stseen asub ekraani keskel)
        grid.setHgap(10); // Määrame horisontaalsed vahemikud ridade vahel
        grid.setVgap(10);// loome vahed columnite vahel.
        grid.setPadding(new Insets(25, 25, 25, 25));// loome stseenile nö kasti ümber
    }

    private ListView<String> ostuKorviElemendid = new ListView<>(); // See on meie ostunimekirja Nö vaade nimekiri.(Kuvatakse ostumenüüs)

    @Override
    public void start(Stage primaryStage) throws Exception { // yaay! programm saab alguse!

        // ÜLDSÄTTED ALGUSES
        window = primaryStage; //Nagu ülal andsime lavale nimetuse "Window" nüüd anname teada, et seesama window on meie kõige primarystage
        GridPane grid = new GridPane();      // Loome esimesele stseenile nö lõuendi
        GridPane grid1 = new GridPane();    // loome teisele stseenile nö lõuendi
        GridPane hooldus1 = new GridPane(); // loome hooldusele nö lõuendi
        GridPane kerehooldusGrid = new GridPane();  //loome kerehoolduse lõuendi                                       //PEAME REFACTORIMA NIMETUSED ARVUSAADAVATEKS
        GridPane mootorihooldusGrid = new GridPane(); // loome mootorihooldusele lõuendi
        GridPane salongihoolduseGrid = new GridPane(); // loome salongihoolduse lõuendi
        GridPane ostuMenüü = new GridPane(); // loome ostumenüüle lõuendi
        GridPane soovidOsta = new GridPane(); // loome lõuendi steenile, kus küsitakse, kas kasutaja soovib seda osta
        GridPane soovidOstaHooldus = new GridPane(); // Sama, mis ülemine, aga hoolduses
        GridPane ostuKorvGrid = new GridPane(); // loome ostukorvile lõuendi
        BorderPane errorBorder = new BorderPane(); // loome error stseenile lõuendi
        GridPane valiMakseMeetod = new GridPane(); //Maksemeetodi valiku lõuend

        Scene stseen1 = new Scene(grid, 400, 275);        // Loome esimese stseeni
        Scene stseen2 = new Scene(grid1, 400, 275);        // loome teise stseeni
        Scene ostustseen1 = new Scene(ostuMenüü, 700, 600) ; // Loome stseeni autode ostumenüüle
        Scene soovOstastseen = new Scene(soovidOsta,400,275); //Loome stseeni, mis kontrollib üle, kas kasutaja tahab osta seda autot    // PEAME TEGEMA KÕIK AKNAD ENAM VAÄHEM SAMA SUUURSEKS,
        Scene soovOstastseen1 = new Scene(soovidOstaHooldus,400,275); // sama, mis eelmine, kuid see on hoolduses
        Scene hooldusestseen1 = new Scene(hooldus1, 400, 275); // loome hooldusele stseeni
        Scene kerestseen = new Scene(kerehooldusGrid,400,275); // loome kerehoolduse stseeni
        Scene mootorstseen = new Scene(mootorihooldusGrid,400,275); // loome mootorihoolduse stseeni
        Scene salongistseen = new Scene(salongihoolduseGrid,400,275); // loome salongihoolduse stseeni
        Scene errorStseen = new Scene(errorBorder,400,200); // loome errori stseenile stseeni
        Scene ostuKorviStseen = new Scene(ostuKorvGrid,500,500); // loome ostukorvi stseeni
        Scene MakseMeetodStseen = new Scene(valiMakseMeetod,500,300); // loome maksemeetodi stseeni

        // _______________________________________________________________________________________________________________________________________

        // ESIMESE STSEENI ALGUS

        gridSet(grid);


        Text scenetitle = new Text("Teretulemast volvo e-teenindusse!"); // esimese steeni tutvustus
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20)); // tutvustuse teksti vormindus

        Text tutvustus = new Text("Sisestage ees- ja perekonnanimi."); // tekst, mis ütleb, mida tuleb teha

        Label forName = new Label("Eesnimi:"); // tekst, mis teeb arusaadavaks, mida tuleb lahtrisse sisestada

        TextField eesnimi = new TextField("Eesnimi"); // lahter, kuhu tuleb sisestada eesnimi

        Label surname = new Label("Perenimi:"); // tekst, mis teeb arusaadavaks, mida tuleb lahtrisse sisestada

        TextField perenimi = new TextField("Perenimi");// lahter, kuhu tuleb sisestada perekonnanimi


        Button edasi = new Button("Edasi");// Nupp edasi,
        HBox hbBtn = new HBox(10);      // ala nupu jaoks, loon cplumnisse spets ala n.ö vundamendi
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT); // positsioon
        hbBtn.getChildren().add(edasi); // lisan "vundamendile" nupu.

        Button turnoff = new Button("Välju"); // nupp programmist väljumiseks


        turnoff.setOnMouseClicked(event -> System.exit(1));      // Kui klikkida turnoff nupule siis sys.exit

        // Kui klikkida edasi nupule siis läheb next stseeni
        edasi.setOnMousePressed(event -> { // Siin kontrollib, et eesnimi ja perenimi poleks tühi, et siin poleks liiga palju tühikuid ja, et perenimi ja eesnimi poleks lühemad kui 2 tähte. Nüüd kontrollib kas ees-või perenimes on number sees! Upd: lahter eesnimi ei tohi sisaldada sõna Eesnimi ja lahter perenimie ei tohi sisaldada Perenimi!
            if (eesnimi.getText().equals("Eesnimi") || perenimi.getText().equals("Perenimi") || eesnimi.getText().length() < 2 || perenimi.getText().length() < 2 || eesnimi.getText().contains("  ") || perenimi.getText().contains("  ") || stringisOnNumber(eesnimi.getText()) || stringisOnNumber(perenimi.getText()) || mituTähteJärjest(perenimi.getText()) || mituTähteJärjest(eesnimi.getText())|| eesnimi.getText().contains("Eesnimi") || perenimi.getText().contains("Perenimi") || eesnimi.getText().contains("nimi") || perenimi.getText().contains("nimi")){
                window.setScene(errorStseen);
            }
            else{// Kui eelolevad tingimused POLE täidetud, siis ta laseb kasutaja järgmisesse stseeni.
            misNimi(eesnimi.getText() + " " + perenimi.getText());
            window.setScene(stseen2);
        }});

        // Siin asetame eelolevad elemendid lõuendile.
        grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(tutvustus,1,1,1,1);
        grid.add(forName, 0, 2);
        grid.add(eesnimi, 1, 2);
        grid.add(surname, 0, 3);
        grid.add(perenimi, 1, 3);
        grid.add(hbBtn, 1, 5);
        grid.add(turnoff,0,5,1,1);
        // ESIMESE STSEENI LÕPP!


        // _______________________________________________________________________________________________________________________________________________

        //ERROR STSEEN
        // Seda stseeni on vaja juhul, kui kasutaja nimi või perenimi on valesti sisestatud.
        errorBorder.setPadding(new Insets(15, 20, 10, 10)); // loon nö kasti selle stseeni ümber


        Label valeNimi = new Label("Palun sisesta oma ees- ja perekonnanimi korrektselt!"); // Loome märkuse, mida kasutaja peaks tegema
        valeNimi.setPadding(new Insets(10,10,10,35)); // Loome märkusele kasti ümber
        valeNimi.setFont(Font.font("Tahoma",FontWeight.EXTRA_BOLD,12)); // anname märkusele kujunduse
        BorderPane.setAlignment(valeNimi, Pos.TOP_CENTER);


        Button tagasi1 = new Button("Tagasi"); // tagasi nupp, selleks, et minna tagasi esimesse stseeeni
        tagasi1.setPadding(new Insets(10, 10, 10, 10)); ///  kastike ümber tagasi nupu


        tagasi1.setOnMouseClicked(e -> window.setScene(stseen1)); // kui klickida tagasi nupule, siis läheb tagasi esimesse stseeni
        errorBorder.setOnKeyPressed(new EventHandler<KeyEvent>() { //Siin kontrollitakse, kas ESCAPE nupp on vajutatud, kui jah, vahetatakse stseeni
            public void handle(KeyEvent ke) {
                        if(ke.getCode().equals(KeyCode.ESCAPE)){
                            window.setScene(stseen1);
                    }
            }
        });



        // lisame meie lõuendile elemendid
        errorBorder.setCenter(tagasi1);
        errorBorder.setTop(valeNimi);
        // ERROR STSEENI LÕPP



        //_______________________________________________________________________________________________________________________________________________

        // TEISE STSEENI ALGUS

        //Gridpane üldsätted. meetodis on täpsemalt kirjeldatud, mida mingi rida tähendab. (Need on igalpool samad!)
        gridSet(grid1);


        Label ostOrHooldus = new Label("Kas ost või hooldus?");//Tekst, mis ilmub ja küsib kas kasutaja soovib midagi osta või hooldada.
        ostOrHooldus.setFont(Font.font("Tahoma",FontWeight.EXTRA_BOLD,20)); //Teeme meie teksti ilusamaks
        Image logo = new Image(getClass().getResourceAsStream("logo275.png")); // Logo pilt


        kliendiNimi.setFont(Font.font("Tahoma",FontWeight.BOLD,10)); //Kliendinimele anname välimiuse
        GridPane.setHalignment(kliendiNimi,HPos.CENTER); // Lisame kliendinime lõuendile

        Label tühi = new Label(""); //Seda tühja rida on vaja selleks, et alles jätta rida/veergu




        Button ost = new Button("  Ost  "); // NUpp ost
        Button hooldus = new Button("Hooldus"); // nupp hooldus
        Button ostukorv = new Button("Ostukorv"); // nupp ostukorv
        Button back = new Button("tagasi"); // nupp tagasi

        //Anname igale nupule positsiooni
        GridPane.setHalignment(ost, HPos.CENTER);
        GridPane.setHalignment(hooldus, HPos.CENTER);
        GridPane.setHalignment(back, HPos.CENTER);
        GridPane.setHalignment(ostukorv,HPos.CENTER);

        //lisame iga stseenielemendi lõuendile.
        grid1.add(ostOrHooldus, 0, 0, 2, 1);          //Paneme  0 veerule ja 0 reale. Colspan tähendab, et see hõlmab 2 veergu
        grid1.add(kliendiNimi,0,1,2,1);         //Paneme 0 veerule ja 1 reale. rowspan tähendab, et vaja hõlmata ainult 1 rida
        grid1.add(ost, 0, 2,2,1);
        grid1.add(hooldus, 0, 3,2,1);
        grid1.add(ostukorv,0,4,2,1);
        grid1.add(tühi,0,5,2,1);
        grid1.add(back, 0, 6,2,2);

        // LOGO PAREMAS ülemises (VÄIKE)
        grid1.setBackground(new Background(new BackgroundImage(logo,BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, // Logo joonistatakse ainult üks kord. (Kui oleks repeat siis meie BG näeks "mummuline" välja
                BackgroundPosition.DEFAULT, // Positsioon default. Ehk logo joonistamist alustatakse ülevalt vasakust nurgast.
                BackgroundSize.DEFAULT))); // Size default.

        back.setOnMouseClicked(e -> window.setScene(stseen1));    // KUI KLIKKIDA SIIS LÄHEB TAGASI esimesse stseeni!!!

        ostukorv.setOnMouseClicked(e -> { // Kui klikkida ostukorvi nupule siis antakse väärtus listView-le

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // DateTime formaat
            LocalDateTime now = LocalDateTime.now(); //Võtab praeguse aja
            int  n = rand.nextInt(5000) + 1000; // Randomi abil genereerime arve numbri

            ostuKorviElemendid.getItems().add("Arve №" + n); //ListView-le lisatakse esimeseks reaks arve number
            ostuKorviElemendid.getItems().add("Arve koostati: " + dtf.format(now)); // Teisse ritta lisatakse arve koostamise aeg
            ostuKorviElemendid.getItems().add("Klient: " + eesnimi.getText() + " " + perenimi.getText()); // Lisame meie arvele ka kliendi nime
            ostuKorviElemendid.getItems().add(" "); // Teeme tühja rea, et näeks ilusam välja

            for (int i = 0; i < ostuKorv.size() ; i++) { //For tsükkel. Võtab ostukorvist kõik elemendid ja lisab need listView-le
                ostuKorviElemendid.getItems().add(ostuKorv.get(i)); //ostukorvi i element lisame selle listView-le
                tükid = ostuKorv.get(i).split(" "); //Tükeldame meie ostukorv i elemendi
                hind = Integer.parseInt(tükid[4].substring(0,tükid[4].length() - 1)); //Võtame meie viimase tüki ja eemaldame sealt € märgi
                summa.add(hind); //Lisame hinna summade listi
            }

            for (int i = 0; i < summa.size(); i++) { // Selle tsükli abil arvutame kõikide ostude summa.
                        intMuutuja += summa.get(i);
            }
            ostuKorviElemendid.getItems().add(" "); // Lisame tühja rea ilu jaoks
            ostuKorviElemendid.getItems().add("Kokku: " + intMuutuja +"€"); // Lisame summa
            window.setScene(ostuKorviStseen); // Määrame stseeniks ostukorvi stseeni
        });

        ost.setOnMouseClicked(e -> { // Kui vajutame ostu nupu peale, siis kontrollime, kas fail on olemas.
            try {
                ArrayList<String> autod = Autod.autoNimed(); // Niisama lisatud, funktsionaalsust pole. Lihtsalt koha täitmiseks
            } catch (FileNotFoundException e1) {

                System.out.println("Faili pole olemas"); // TEKITA UUS AKEN. - Ausalt öeldes ei viitsinud uut akent lisada. :(
                System.exit(1); // Programm pannakse kinni.

            }
            window.setScene(ostustseen1); // Kõik korras, lähme autode ostustseeni!
        });
        grid1.setOnKeyPressed(new EventHandler<KeyEvent>() { //Siin kontrollitakse, kas ESCAPE nupp on vajutatud, kui jah, vahetatakse stseeni
            public void handle(KeyEvent ke) {
                if(ke.getCode().equals(KeyCode.ESCAPE)){ // Kui key = escape
                    window.setScene(stseen1); // lähme üle menüüsse
                }
            }
        });

        hooldus.setOnMouseClicked(e -> window.setScene(hooldusestseen1)); // Kui vajutada hoolduse peale, siis lähme hoolduse stseeni.

        // TEISE STSEENI LÕPP!
        //_______________________________________________________________________________________________________________________________________________

        // OSTUSTSEENI ALGUS
         // GridPane üldsätted. meetodis on täpne kirjeldus.
        gridSet(ostuMenüü);


        Text ostTutvustus = new Text("Teie ees on müügilolevad autod: ");//Tekst, mis annab kasutajale teada, mis toimub.
        Text tere = new Text(" "); // Tühi rida, lihsalt, et oleks ilusam

        //Teeme palju arrayliste. Jah nagu kilplased, võis kasutada konstrukotrit. Aga see meetod töötab ja me ei hakkanud midagi muutma.
        ArrayList<String> autod = Autod.autoNimed(); // Autode list. Sisaldab tervet rida, mis on failist sisseloetud
        ArrayList<String> mark = new ArrayList<String>(); // Mudeli list 0 positsioon
        ArrayList<String> hind = new ArrayList<String>();  // hinna list 1 positsioon
        ArrayList<String> png = new ArrayList<String>(); // png fail 2 positsioon
        ArrayList<String> pngFlip = new ArrayList<String>(); // png fail peegelpildis 3 positsioon

        for (int i = 0; i < autod.size(); i++) { // Tükeldame ära tsükli abil iga autode elemendi ja lisame eraldi listidesse.
            String[] tükid = autod.get(i).split(" ");
            mark.add(tükid[0]);
            hind.add(tükid[1]);
            png.add(tükid[2]);
            pngFlip.add(tükid[3]);
        }

        //SIIN LOOME VOLVO V40-le NUPU, LISAME TALLE 2 PILTI JA LISAME SÜNDMUSI
        Image imageV40 = new Image(getClass().getResourceAsStream(png.get(0)));
        Image imageV40flip = new Image(getClass().getResourceAsStream(pngFlip.get(0)));
        Button volvo1 = new Button(mark.get(0), new ImageView(imageV40));
        volvo1.setOnMouseEntered(e -> {volvo1.setGraphic(new ImageView(imageV40flip));volvo1.setText(hind.get(0));});
        volvo1.setOnMouseExited(e -> {volvo1.setGraphic(new ImageView(imageV40));volvo1.setText(mark.get(0));});
        volvo1.setOnMousePressed( e -> {misAuto("Mudel: " + mark.get(0) + "  Hind: "+ hind.get(0));window.setScene(soovOstastseen); });

        //SIIN LOOME VOLVO V60-le NUPU, LISAME TALLE 2 PILTI JA LISAME SÜNDMUSI
        Image imageV60 = new Image(getClass().getResourceAsStream(png.get(1)));
        Image imageV60flip = new Image(getClass().getResourceAsStream(pngFlip.get(1)));
        Button volvo2 = new Button(mark.get(1) + "  ", new ImageView(imageV60));
        volvo2.setOnMouseEntered(e -> {volvo2.setGraphic(new ImageView(imageV60flip));volvo2.setText(hind.get(1));});
        volvo2.setOnMouseExited(e -> {volvo2.setGraphic(new ImageView(imageV60 ));volvo2.setText(mark.get(1)+ "  ");});
        volvo2.setOnMousePressed( e -> {misAuto("Mudel: " + mark.get(1) + "  Hind: " + hind.get(1));window.setScene(soovOstastseen);});

        //SIIN LOOME VOLVO XC90-le NUPU, LISAME TALLE 2 PILTI JA LISAME SÜNDMUSI
        Image imageXC90= new Image(getClass().getResourceAsStream(png.get(2)));
        Image imageXC90flip = new Image(getClass().getResourceAsStream(pngFlip.get(2)));
        Button volvo3 = new Button(mark.get(2)+ " ", new ImageView(imageXC90));
        volvo3.setOnMouseEntered(e -> {volvo3.setGraphic(new ImageView(imageXC90flip));volvo3.setText(hind.get(2));});
        volvo3.setOnMouseExited(e -> {volvo3.setGraphic(new ImageView(imageXC90));volvo3.setText(mark.get(2)+ " ");});
        volvo3.setOnMousePressed( e -> {misAuto("Mudel: " + mark.get(2) + "  Hind: " + hind.get(2));window.setScene(soovOstastseen);});

        //SIIN LOOME VOLVO S60-le NUPU, LISAME TALLE 2 PILTI JA LISAME SÜNDMUSI
        Image imageS60= new Image(getClass().getResourceAsStream(png.get(3)));
        Image imageS60flip = new Image(getClass().getResourceAsStream(pngFlip.get(3)));
        Button volvo4 = new Button(mark.get(3) + "  ", new ImageView(imageS60));
        volvo4.setOnMouseEntered(e -> {volvo4.setGraphic(new ImageView(imageS60flip));volvo4.setText(hind.get(3));});
        volvo4.setOnMouseExited(e -> {volvo4.setGraphic(new ImageView(imageS60));volvo4.setText(mark.get(3)+ "  ");});
        volvo4.setOnMousePressed( e -> {misAuto("Mudel: " + mark.get(3) + "  Hind: " + hind.get(3));window.setScene(soovOstastseen);});

        //SIIN LOOME VOLVO S60CC-le NUPU, LISAME TALLE 2 PILTI JA LISAME SÜNDMUSI
        Image imageS60cc= new Image(getClass().getResourceAsStream(png.get(4)));
        Image imageS60ccflip = new Image(getClass().getResourceAsStream(pngFlip.get(4)));
        Button volvo5 = new Button(mark.get(4), new ImageView(imageS60cc));
        volvo5.setOnMouseEntered(e -> {volvo5.setGraphic(new ImageView(imageS60ccflip));volvo5.setText(hind.get(4));});
        volvo5.setOnMouseExited(e -> {volvo5.setGraphic(new ImageView(imageS60cc));volvo5.setText(mark.get(4));});
        volvo5.setOnMousePressed( e -> {misAuto("Mudel: " + mark.get(4) + "  Hind: " + hind.get(4));window.setScene(soovOstastseen);});

        //SIIN LOOME VOLVO V60CC-le NUPU, LISAME TALLE 2 PILTI JA LISAME SÜNDMUSI
        Image imageV60cc = new Image(getClass().getResourceAsStream(png.get(5)));
        Image imageV60ccflip = new Image(getClass().getResourceAsStream(pngFlip.get(5)));
        Button volvo6 = new Button(mark.get(5), new ImageView(imageV60cc));
        volvo6.setOnMouseEntered(e -> {volvo6.setGraphic(new ImageView(imageV60ccflip));volvo6.setText(hind.get(5));});
        volvo6.setOnMouseExited(e -> {volvo6.setGraphic(new ImageView(imageV60cc));volvo6.setText(mark.get(5));});
        volvo6.setOnMousePressed( e -> {misAuto("Mudel: " + mark.get(5) + "  Hind: " + hind.get(5));window.setScene(soovOstastseen);});


        Button tagasi2 = new Button("Tagasi"); // Nupp tagasi minekuks
        GridPane.setHalignment(tagasi2, HPos.CENTER); // Et oleks ilusam anname talle positsiooni "Keskel"
        tagasi2.setOnMouseClicked(e -> window.setScene(stseen2)); // Kui vajutada tagasi nuppu, siis läheb tagasi eelmisesse stseeni

        ostuMenüü.setOnKeyPressed(new EventHandler<KeyEvent>() {//Siin kontrollitakse, kas ESCAPE nupp on vajutatud, kui jah, vahetatakse stseeni
            public void handle(KeyEvent ke) {
                if(ke.getCode().equals(KeyCode.ESCAPE)){
                    window.setScene(stseen2);
                }
            }
        });
        //Siin lisame meie GridPane-le kõik elemendid vastavastsesse ruutudesse!! (row indeks 0 kuni 4)
        ostuMenüü.add(ostTutvustus, 1,0,1, 1);
        ostuMenüü.add(volvo1,0,1,1,1);
        ostuMenüü.add(volvo2,1,1,1,1);
        ostuMenüü.add(volvo3,2,1,1,1);
        ostuMenüü.add(volvo4,0,2,1,1);
        ostuMenüü.add(volvo5,1,2,1,1);
        ostuMenüü.add(volvo6,2,2,1,1);
        ostuMenüü.add(tere,1,3,1,1);
        ostuMenüü.add(tagasi2,1,4,1,1);


        // OSTUSTSEENI LÕPP
        //___________________________________________________________________________________________________________________________________________
        // STSEEN, MIS KÜSIB, KAS SOOVID OSTA SEDA AUTOT!

        // Meie gridpane üldsätted
        soovidOsta.setAlignment(Pos.CENTER);
        soovidOsta.setHgap(10);
        soovidOsta.setVgap(10);
        soovidOsta.setPadding(new Insets(25, 25, 25, 25));



        Button b_jah = new Button("Jah"); // Jah nupp
        Button b_ei = new Button ("Ei"); // ei nupp

        b_jah.setOnMousePressed(e -> {ostuKorv.add(millineAuto); window.setScene(stseen2);} ); // Kui vajutada jah, siis lisatakse valitud auto ostukorvi ning minnakse menüüsse
        b_ei.setOnMouseClicked(e -> window.setScene(ostustseen1)); // kui vajutada ei, siis läheb tagasi autode valiku juurde

        soovidOsta.setOnKeyPressed(new EventHandler<KeyEvent>() {//Siin kontrollitakse, kas ESCAPE nupp on vajutatud, kui jah, vahetatakse stseeni
            public void handle(KeyEvent ke) {
                if(ke.getCode().equals(KeyCode.ESCAPE)){
                    window.setScene(ostustseen1);
                }
            }
        });

        // Lisame GridPanele elemendid
        soovidOsta.add(kasAuto,0,0,3,1);
        soovidOsta.add(valitudAuto,0,1,3,1);
        soovidOsta.add(b_jah,3,2,1,1);
        soovidOsta.add(b_ei,0,2,1,1);



        // _______________________________________________________________________________________________________________________________________________
        // HOOLDUSESTSEENI ALGUS
        //GridPane sätted
        gridSet(hooldus1);

        // Tekst ja tema ilme
        Label labelCare = new Label("Valige palun hooldustüüp");
        labelCare.setFont(Font.font("Tahoma",FontWeight.EXTRA_BOLD,20));
        //Logo pilt
        Image logo1 = new Image(getClass().getResourceAsStream("logo275.png"));

        Label tühi1 = new Label(""); // Tühi rida

        Button kerehooldus = new Button("Kerehooldus"); // Nupp kerehoolduse jaoks
        Button mootorihooldus = new Button("Mootorihooldus"); // nupp mootorihoolduse jaoks
        Button salongihooldus = new Button("Salongihooldus"); // nupp salongihoolduse jaoks
        Button back1 = new Button("tagasi"); // tagasi nupp

        //Anname nuppudele positsioonid
        GridPane.setHalignment(kerehooldus, HPos.CENTER);
        GridPane.setHalignment(mootorihooldus, HPos.CENTER);
        GridPane.setHalignment(salongihooldus, HPos.CENTER);
        GridPane.setHalignment(back1,HPos.CENTER);

        //Lisame gridPanele loodud elemendid
        hooldus1.add(labelCare, 0, 0, 2, 1);
        hooldus1.add(kerehooldus, 0, 2,2,1);
        hooldus1.add(mootorihooldus, 0, 3,2,1);
        hooldus1.add(salongihooldus,0,4,2,1);
        hooldus1.add(tühi1,0,5,2,1);
        hooldus1.add(back1, 0, 6,2,2);

        // LOGO. Kuskil üleval on täpsemini kirjeldatud.
        hooldus1.setBackground(new Background(new BackgroundImage(logo1,BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));



        kerehooldus.setOnMouseClicked(e -> { window.setScene(kerestseen); });// Kui vajutada kerehoolduse peale, siis läheb sinna stseeni
        mootorihooldus.setOnMouseClicked(e-> window.setScene(mootorstseen)); // kui vsjutada mootorihoolduse peale, siis läheb sinna stseeni
        salongihooldus.setOnMouseClicked(e-> window.setScene(salongistseen)); // Muudab stseeni nuppu vajutades!
        back1.setOnMouseClicked(e -> window.setScene(stseen2));  // KUI KLIKKIDA SIIS LÄHEB TAGASI teise stseeni
        hooldus1.setOnKeyPressed(new EventHandler<KeyEvent>() {//Siin kontrollitakse, kas ESCAPE nupp on vajutatud, kui jah, vahetatakse stseeni
            public void handle(KeyEvent ke) {
                if(ke.getCode().equals(KeyCode.ESCAPE)){
                    window.setScene(stseen2);
                }
            }
        });


        // HOOLDUSE ÜLDSTSEENI LÕPP

        //______________________________________________________________________________________________________

        //  HARUDE ALGUS - KEREHOOLDUS
        //Üldsätted gridpane jaoks
        gridSet(kerehooldusGrid);


        //Loome 3 arraylisti. (Pmts sama süsteem nagu autode sisse lugemisel)
        ArrayList<String> kerehooldusInfo = Hooldus.kereTeenus();
        ArrayList<String> teenuseKereNimetus = new ArrayList<String>();
        ArrayList<String> teenusKereHind = new ArrayList<String>();

        for (int i = 0; i < kerehooldusInfo.size(); i++) { // LOeme hooldusinifo read ja splitime need ning lisame vastavatesse listidesse
            String[] tükid = kerehooldusInfo.get(i).split(";");
            teenuseKereNimetus.add(tükid[0]);
            teenusKereHind.add(tükid[1]);

        }


        Label labelkere = new Label("Valige teenus, mida soovite kasutada"); // Tekst
        labelkere.setFont(Font.font("Tahoma",FontWeight.EXTRA_BOLD,12)); // Teksti välimus

        Label tühi2 = new Label(""); // Tühi rida

        //Kriimustuse eemaldamise nupp ja eventid.
        Button kriimustus = new Button(teenuseKereNimetus.get(0) + " " + teenusKereHind.get(0));
        kriimustus.setOnMousePressed( e -> {misHooldus("Teenus:" + teenuseKereNimetus.get(0) + " Hind: "+ teenusKereHind.get(0));window.setScene(soovOstastseen1); });

        //Vahatamise nupp ja sündmused
        Button vahatamine = new Button(teenuseKereNimetus.get(1) + " " + teenusKereHind.get(1));
        vahatamine.setOnMousePressed( e -> {misHooldus("Teenus:" + teenuseKereNimetus.get(1) + " Hind: "+ teenusKereHind.get(1));window.setScene(soovOstastseen1); });

        //rehvivahetus ja sündmused
        Button rehvid = new Button(teenuseKereNimetus.get(2) + " " + teenusKereHind.get(2));
        rehvid.setOnMousePressed( e -> {misHooldus("Teenus: " + teenuseKereNimetus.get(2) + " Hind: "+ teenusKereHind.get(2));window.setScene(soovOstastseen1); });

        //Tagasi nupp ja eelmine stseen
        Button back2 = new Button("tagasi");
        back2.setOnMouseClicked(e -> window.setScene(hooldusestseen1));

        kerehooldusGrid.setOnKeyPressed(new EventHandler<KeyEvent>() {  //Siin kontrollitakse, kas ESCAPE nupp on vajutatud, kui jah, vahetatakse stseeni
            public void handle(KeyEvent ke) {
                if(ke.getCode().equals(KeyCode.ESCAPE)){
                    window.setScene(hooldusestseen1);
                }
            }
        });


        //anname nuppudele asetuse "Keskel"
        GridPane.setHalignment(kriimustus, HPos.CENTER);
        GridPane.setHalignment(vahatamine, HPos.CENTER);
        GridPane.setHalignment(rehvid, HPos.CENTER);
        GridPane.setHalignment(back2,HPos.CENTER);

        // Lisame GridPanele elemendid
        kerehooldusGrid.add(labelkere, 0, 0, 2, 1);
        kerehooldusGrid.add(kriimustus, 0, 2,2,1);
        kerehooldusGrid.add(vahatamine, 0, 3,2,1);
        kerehooldusGrid.add(rehvid,0,4,2,1);
        kerehooldusGrid.add(tühi2,0,5,2,1);
        kerehooldusGrid.add(back2, 0, 6,2,2);


        // KEREHOOLDUSE LÕPP

        //______________________________________________________________________________________________________

        // MOOTORIHOOLDUSE ALGUS

        gridSet(mootorihooldusGrid);

        //Loome 3 arraylisti
        ArrayList<String> mootorihooldusInfo = Hooldus.mootoriTeenus();
        ArrayList<String> teenuseMootoriNimetus = new ArrayList<String>();
        ArrayList<String> teenusMootorHind = new ArrayList<String>();

        for (int i = 0; i < mootorihooldusInfo.size(); i++) {
            String[] tükid = mootorihooldusInfo.get(i).split(";");    //Loeme vastava hoolduse read splittides neid ja lisades vastavatesse listidesse
            teenuseMootoriNimetus.add(tükid[0]);
            teenusMootorHind.add(tükid[1]);

        }


        Label labelmootor = new Label("Valige teenus, mida soovite kasutada");
        labelmootor.setFont(Font.font("Tahoma",FontWeight.EXTRA_BOLD,20));

        Label tühi3 = new Label(""); //tuhi rida/ala


        // Õlivahetuse nupp ja sündmus
        Button õlivahetus = new Button(teenuseMootoriNimetus.get(0) + " " + teenusMootorHind.get(0));
        õlivahetus.setOnMousePressed( e -> {misHooldus("Teenus: " + teenuseMootoriNimetus.get(0) + " Hind: "+ teenusMootorHind.get(0));window.setScene(soovOstastseen1); });

        //Kütusefiltri vahetuse nupp ja sündmus
        Button kütusefilter = new Button(teenuseMootoriNimetus.get(1) + " " + teenusMootorHind.get(1));
        kütusefilter.setOnMousePressed( e -> {misHooldus("Teenus:" + teenuseMootoriNimetus.get(1) + " Hind: "+ teenusMootorHind.get(1));window.setScene(soovOstastseen1); });

        ////Süüteküünalde vahetuse nupp ja sündmus
        Button süüteküünal = new Button(teenuseMootoriNimetus.get(2) + " " + teenusMootorHind.get(2));
        süüteküünal.setOnMousePressed( e -> {misHooldus("Teenus:" + teenuseMootoriNimetus.get(2) + " Hind: "+ teenusMootorHind.get(2));window.setScene(soovOstastseen1); });

        //Diagnostika läbiviimise nupp ja sündmus
        Button diagnostika = new Button(teenuseMootoriNimetus.get(3) + " " + teenusMootorHind.get(3));
        diagnostika.setOnMousePressed( e -> {misHooldus("Teenus: " + teenuseMootoriNimetus.get(3) + " Hind: "+ teenusMootorHind.get(3));window.setScene(soovOstastseen1); });

        //Tagasi nupp ja eelmine stseen
        Button back3 = new Button("tagasi");
        back3.setOnMouseClicked(e -> window.setScene(hooldusestseen1));

        mootorihooldusGrid.setOnKeyPressed(new EventHandler<KeyEvent>() {   //Siin kontrollitakse, kas ESCAPE nupp on vajutatud, kui jah, vahetatakse stseeni
            public void handle(KeyEvent ke) {
                if(ke.getCode().equals(KeyCode.ESCAPE)){
                    window.setScene(hooldusestseen1);
                }
            }
        });


        //Asetame nupud keskele
        GridPane.setHalignment(õlivahetus, HPos.CENTER);
        GridPane.setHalignment(kütusefilter, HPos.CENTER);
        GridPane.setHalignment(süüteküünal, HPos.CENTER);
        GridPane.setHalignment(diagnostika, HPos.CENTER);
        GridPane.setHalignment(back3,HPos.CENTER);

        // Lisame GridPanele elemendid
        mootorihooldusGrid.add(labelmootor, 0, 0, 2, 1);
        mootorihooldusGrid.add(õlivahetus, 0, 2,2,1);
        mootorihooldusGrid.add(kütusefilter, 0, 3,2,1);
        mootorihooldusGrid.add(süüteküünal,0,4,2,1);
        mootorihooldusGrid.add(diagnostika,0,5,2,1);
        mootorihooldusGrid.add(tühi3,0,6,2,1);
        mootorihooldusGrid.add(back3, 0, 7,2,2);





        // MOOTORIHOOLDUSE LÕPP
        //_______________________________________________________________________________________________________________

        // SALONGIHOOLDUSE ALGUS
        gridSet(salongihoolduseGrid);


        //Loome 3 arraylisti
        ArrayList<String> salongiHoolduseInfo = Hooldus.salogiTeenus();
        ArrayList<String> teenuseSalongNimetus = new ArrayList<String>();
        ArrayList<String> teenuseSalongHind = new ArrayList<String>();

        for (int i = 0; i < salongiHoolduseInfo.size(); i++) {
            String[] tükid = salongiHoolduseInfo.get(i).split(";"); //Loeme vastava hoolduse read splittides neid ja lisades vastavatesse listidesse
            teenuseSalongNimetus.add(tükid[0]);
            teenuseSalongHind.add(tükid[1]);

        }


        Label labelSalong = new Label("Valige teenus, mida soovite kasutada");
        labelSalong.setFont(Font.font("Tahoma",FontWeight.EXTRA_BOLD,20));

        Label tühi4 = new Label("");  // tühi rida/ala


        // Kuivpuhastuse nupp ja sündmus
        Button kuivPuhastus = new Button(teenuseSalongNimetus.get(0) + " " + teenuseSalongHind.get(0));
        kuivPuhastus.setOnMousePressed( e -> {misHooldus("Teenus:" + teenuseSalongNimetus.get(0) + " Hind: "+ teenuseSalongHind.get(0));window.setScene(soovOstastseen1); });

        // Sügavpuhastuse nupp ja sündmus
        Button sügavPuhastus = new Button(teenuseSalongNimetus.get(1) + " " + teenuseSalongHind.get(1));
        sügavPuhastus.setOnMousePressed( e -> {misHooldus("Teenus:" + teenuseSalongNimetus.get(1) + " Hind: "+ teenuseSalongHind.get(1));window.setScene(soovOstastseen1); });

        Button taastamine = new Button(teenuseSalongNimetus.get(2) + " " + teenuseSalongHind.get(2));
        taastamine.setOnMousePressed( e -> {misHooldus("Teenus:" + teenuseSalongNimetus.get(2) + " Hind: "+ teenuseSalongHind.get(2));window.setScene(soovOstastseen1); });

        //Tagasi nupp ja eelmine stseen
        Button back4 = new Button("tagasi");
        back4.setOnMouseClicked(e -> window.setScene(hooldusestseen1));

        salongihoolduseGrid.setOnKeyPressed(new EventHandler<KeyEvent>() {//Siin kontrollitakse, kas ESCAPE nupp on vajutatud, kui jah, vahetatakse stseeni
            public void handle(KeyEvent ke) {
                if(ke.getCode().equals(KeyCode.ESCAPE)){
                    window.setScene(hooldusestseen1);
                }
            }
        });

        //Asetame nupud keskele
        GridPane.setHalignment(kuivPuhastus, HPos.CENTER);
        GridPane.setHalignment(sügavPuhastus, HPos.CENTER);
        GridPane.setHalignment(taastamine, HPos.CENTER);
        GridPane.setHalignment(back4,HPos.CENTER);

        // Lisame GridPanele elemendid
        salongihoolduseGrid.add(labelSalong, 0, 0, 2, 1);
        salongihoolduseGrid.add(kuivPuhastus, 0, 2,2,1);
        salongihoolduseGrid.add(sügavPuhastus, 0, 3,2,1);
        salongihoolduseGrid.add(taastamine,0,4,2,1);
        salongihoolduseGrid.add(tühi4,0,6,2,1);
        salongihoolduseGrid.add(back4, 0, 7,2,2);



        //_______________________________________________________________________________________________________________
        // STSEEN, MIS KÜSIB, KAS SOOVID SEDA  TEENUST KASUTADA - PEAB JÄÄMA KOIKIFE HOOLDUS HARUDE LÕPPU !!!


        gridSet(soovidOstaHooldus);


        // Loome jah/ei nupud
        Button hooldusNupp_jah = new Button("Jah");
        Button hooldusNupp_ei = new Button ("Ei");


        hooldusNupp_jah.setOnMousePressed(e -> {ostuKorv.add(millineHooldus); window.setScene(stseen2);} ); // Kui vajutada jah, siis lisatakse valitud auto ostukorvi ning minnakse menüüsse
        hooldusNupp_ei.setOnMouseClicked(e -> window.setScene(hooldusestseen1));       // Kui vajutada nuppu ei, siis läheb tagasi autode valiku juurde
        soovidOstaHooldus.setOnKeyPressed(new EventHandler<KeyEvent>() {    //Siin kontrollitakse, kas ESCAPE nupp on vajutatud, kui jah, vahetatakse stseeni
            public void handle(KeyEvent ke) {
                if(ke.getCode().equals(KeyCode.ESCAPE)){
                    window.setScene(hooldusestseen1);
                }
            }
        });

        // Lisame GridPanele elemendid
        soovidOstaHooldus.add(kasHooldus,0,0,3,1);
        soovidOstaHooldus.add(valitudHooldus,0,1,3,1);
        soovidOstaHooldus.add(hooldusNupp_jah,3,2,1,1);
        soovidOstaHooldus.add(hooldusNupp_ei,0,2,1,1);


        //__________________________________________________________________________________________________________________

        // OSTUKORVI STSEEN ALGAB
        gridSet(ostuKorvGrid);

        // Loome maksa/tagasi nupud
        Button maksa = new Button("Maksa ära");
        Button tagasi = new Button("Tagasi");

        // Loome n.ö sildi
        Text siinolevad = new Text("Teie ostukorvis olevad asjad: ");

        // Maksmise sündmus
        maksa.setOnMouseClicked(e-> {
            if (ostuKorviElemendid.getItems().size() < 7) {
                valeNimi.setText("Makset ei saa sooritada, kuna sinu ostukorv on tühi!"); // Kontrollitakse kas ostukorvis on midagi ning kui pole väljastatakse vastav teade
                tagasi1.setOnMouseClicked(event -> window.setScene(ostuKorviStseen));
                window.setScene(errorStseen);
            } else {
                window.setScene(MakseMeetodStseen); // Kui ostukorv pole tühi, suunatakse maksemeetodite stseeni
            }
        });

        // Vajutades nuppu tagasi suunatakse kasutaja peamenüüsse
        tagasi.setOnMouseClicked(e -> {window.setScene(stseen2);ostuKorviElemendid.getItems().clear(); intMuutuja = 0; summa.clear();});

        ostuKorvGrid.setOnKeyPressed(new EventHandler<KeyEvent>() {     //Siin kontrollitakse, kas ESCAPE nupp on vajutatud, kui jah, vahetatakse stseeni
            public void handle(KeyEvent ke) {
                if(ke.getCode().equals(KeyCode.ESCAPE)){
                    window.setScene(stseen2);
                    ostuKorviElemendid.getItems().clear();
                    intMuutuja = 0; summa.clear();
                }
            }
        });

        // Luuaks ostukorvi box/tabel
        HBox hbox = new HBox(ostuKorviElemendid);
        // Lisame GridPanele elemendid
        ostuKorvGrid.add(siinolevad,0,0,1,3);
        ostuKorvGrid.add(tühi,0,1,1,1);
        ostuKorvGrid.add(maksa,4,2,1,1);
        ostuKorvGrid.add(hbox,0,2,2,2);
        ostuKorvGrid.add(tagasi,4,4,1,1);


        // OSTUKORVI STSEEEN LÕPPEB
// _______________________________________________________________________________________________________________________________________________

        // MAKSA ÄRA STSEEN!!!
        gridSet(valiMakseMeetod);


        Text valiMm = new Text("Vali maksemeetod");
        GridPane.setHalignment(valiMm,HPos.CENTER);


        // Defeneerime Swedbangi pildi ja loome vastava nupu
        Image swed = new Image(getClass().getResourceAsStream("swed.png"));
        Button swedBank = new Button("", new ImageView(swed));

        // Defeneerime SEB pildi ja loome vastava nupu
        Image seb = new Image(getClass().getResourceAsStream("seb.png"));
        Button sebBank = new Button("", new ImageView(seb));

        // Defeneerime Bitcoini pildi ja loome vastava nupu
        Image bit = new Image(getClass().getResourceAsStream("bit.png"));
        Button bitCoin = new Button("", new ImageView(bit));

        // Loome hoatuse teksti
        Text hoiatus = new Text("Kuna me ise ei müü mitte ühtegi asja ning raha selle eest nõuda ei saa, \n siis nupule vajutades Te väljute programmist. \n Sellisel viisil kujutame ette, et ostud on makstud ja soovime Teile ilusat päevajätku! ");
        GridPane.setHalignment(hoiatus,HPos.CENTER);
        hoiatus.setTextAlignment(TextAlignment.CENTER);


        Text kolmtühja = new Text("   \n  \n   ");


        // Nupule vajutades lõpetab programm töö
        swedBank.setOnMouseClicked(e -> System.exit(1));
        // Hiirega (kursoriga) antud nupule peale minnes ilmub hoiatus, mille eelnevalt lõime
        swedBank.setOnMouseEntered(e-> {valiMakseMeetod.getChildren().remove(kolmtühja); valiMakseMeetod.add(hoiatus,0,2,3,3);});
        // Hiirt (kursorit) nupu pealt ära korjate kaob hoiatus ja see asendatatakse tühjade ridadega
        swedBank.setOnMouseExited(e-> { valiMakseMeetod.add(kolmtühja,0,2,3,3);valiMakseMeetod.getChildren().remove(hoiatus);});


        // Nupule vajutades lõpetab programm töö
        sebBank.setOnMouseClicked(e -> System.exit(1));
        // Hiirega (kursoriga) antud nupule peale minnes ilmub hoiatus, mille eelnevalt lõime
        sebBank.setOnMouseEntered(e-> {valiMakseMeetod.getChildren().remove(kolmtühja);valiMakseMeetod.add(hoiatus,0,2,3,3);});
        // Hiirt (kursorit) nupu pealt ära korjate kaob hoiatus ja see asendatatakse tühjade ridadega
        sebBank.setOnMouseExited(e-> { valiMakseMeetod.add(kolmtühja,0,2,3,3);valiMakseMeetod.getChildren().remove(hoiatus);});

        // Nupule vajutades lõpetab programm töö
        bitCoin.setOnMouseClicked(e -> System.exit(1));
        // Hiirega (kursoriga) antud nupule peale minnes ilmub hoiatus, mille eelnevalt lõime
        bitCoin.setOnMouseEntered(e->{valiMakseMeetod.getChildren().remove(kolmtühja);valiMakseMeetod.add(hoiatus,0,2,3,3);});
        // Hiirt (kursorit) nupu pealt ära korjate kaob hoiatus ja see asendatatakse tühjade ridadega
        bitCoin.setOnMouseExited(e-> { valiMakseMeetod.add(kolmtühja,0,2,3,3);valiMakseMeetod.getChildren().remove(hoiatus);});

        // Lisame GridPanele elemendid
        valiMakseMeetod.add(valiMm,1,0,1,1);
        valiMakseMeetod.add(swedBank,0,1,1,1);
        valiMakseMeetod.add(sebBank,1,1,1,1);
        valiMakseMeetod.add(bitCoin,2,1,1,1);
        valiMakseMeetod.add(kolmtühja,0,2,3,3);

        //maksa ära STSEEN LÕPPEB
        //______________________________________________________________________________________________________________________

        /*public static void salvestaInfo(List<Sissekanne> sissekanded) throws IOException {

        try (DataOutputStream väljundVoog = new DataOutputStream(new FileOutputStream("päevik.dat"))) {

            väljundVoog.writeInt(sissekanded.size());

            for (Sissekanne sissekanne : sissekanded) {

                /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
                String kuupaev = sissekanne.getKuupäev().format(formatter);                     //See viis peaks ka toimima :)
                väljundVoog.writeUTF(kuupaev);*/

                /*väljundVoog.writeUTF(sissekanne.getKuupäev().toString());
                väljundVoog.writeInt(sissekanne.getKirjeldus().length());   // Pole vist hetkel oige, aga peab naitama sissekannete arvu
                väljundVoog.writeUTF(sissekanne.getKirjeldus());


                System.out.println("Seansside info on salvestatud!");         //Ei joudnud
            }
            }
        }*/

        //______________________________________________________________________________________________________________________

        window.setTitle("Volvo");  // lava tiitelribale pannakse tekst
        window.setScene(stseen1);  // lavale lisatakse stseen
        window.show();  // lava tehakse nähtavaks
}
    public static void main(String[] args) {
        launch(args);
    }
}