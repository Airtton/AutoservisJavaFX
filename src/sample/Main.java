package sample;
import javafx.application.Application;
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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    private Stage window;
    private Text kliendiNimi = new Text();
    private Text kasAuto = new Text("Kas tõesti soovid osta seda autot ?");
    private Text kasHooldus = new Text("Kas tõesti soovid kasutada seda teenust ?");


    private Label valitudAuto = new Label();
    private Label valitudHooldus = new Label();
    private String millineAuto;
    private String millineHooldus;                                  //JAOTADA ARA HOOLDUSE MUUUTUJAD JA AUTODE MUUTUJA ERALDI KASTIKESTESSE !!!
    private String nimekiri = "";
    private String[] tükid;
    private ArrayList<Integer> summa = new ArrayList<>();
    private int hind;
    private int intMuutuja;


    ArrayList<String> ostuKorv = new ArrayList<>();
    Random rand = new Random();

    private void misAuto(String auto){
        valitudAuto.setText(auto);
        millineAuto = valitudAuto.getText();
    }


    private void misHooldus(String hooldus){
        valitudHooldus.setText(hooldus);
        millineHooldus = valitudHooldus.getText();

    }



    private void misNimi(String nimi){
        kliendiNimi.setText("Klient: "+ nimi);
    }

    private String nimeKiri(ArrayList<String> aaa){

        for (int i = 0; i < aaa.size(); i++) {

            }
        return nimekiri;
    }

    ListView<String> testime = new ListView<>();

    @Override
    public void start(Stage primaryStage) throws Exception {

        // ÜLDSÄTTED ALGUSES
        window = primaryStage;
        GridPane grid = new GridPane();      // Loome esimesele stseenile nö lõuendi
        GridPane grid1 = new GridPane();    // loome teisele stseenile nö lõuendi
        GridPane hooldus1 = new GridPane();
        GridPane kerehooldusGrid = new GridPane();                                         //PEAME REFACTORIMA NIMETUSED ARVUSAADAVATEKS
        GridPane ostuMenüü = new GridPane();
        GridPane soovidOsta = new GridPane();

        GridPane soovidOsta1 = new GridPane();

        GridPane ostuKorvgrid = new GridPane();
        BorderPane errorBorder = new BorderPane();

        Scene stseen1 = new Scene(grid, 400, 275);        // Loome esimese stseeni
        Scene stseen2 = new Scene(grid1, 400, 275);        // loome teise stseeni
        Scene ostustseen1 = new Scene(ostuMenüü, 650, 600) ;
        Scene soovOstastseen = new Scene(soovidOsta,400,275);                       // PEAME TEGEMA KÕIK AKNAD ENAM VAÄHEM SAMA SUUURSEKS

        Scene soovOstastseen1 = new Scene(soovidOsta1,400,275);

        Scene hooldusestseen1 = new Scene(hooldus1, 400, 275);
        Scene kerestseen = new Scene(kerehooldusGrid,400,275);
        Scene errorStseen = new Scene(errorBorder,350,200);
        Scene ostuKorviStseen = new Scene(ostuKorvgrid,500,500);


        // _______________________________________________________________________________________________________________________________________

        // ESIMESE STSEENI ALGUS

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Teretulemast volvo e-teenindusse!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Text tutvustus = new Text("Sisestage ees- ja perekonnanimi.");
        grid.add(tutvustus,1,1,1,1);

        Label forName = new Label("Eesnimi:");
        grid.add(forName, 0, 2);

        TextField eesnimi = new TextField("Eesnimi");
        grid.add(eesnimi, 1, 2);

        Label surname = new Label("Perenimi:");
        grid.add(surname, 0, 3);

        TextField perenimi = new TextField("Perenimi");
        grid.add(perenimi, 1, 3);

        Button edasi = new Button("Edasi");
        HBox hbBtn = new HBox(10);                                      // ala nupu jaoks, loon calumnisse spets ala n.ö vundamendi
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(edasi);
        grid.add(hbBtn, 1, 5);

        Button turnoff = new Button("Välju");
        grid.add(turnoff,0,5,1,1);


        turnoff.setOnMouseClicked(event -> System.exit(1));            // Kui klikkida turnoff nupule siis sys.exit
        edasi.setOnMousePressed(event -> {
            if (eesnimi.getText().equals("Eesnimi") || perenimi.getText().equals("Perenimi") || eesnimi.getText().length() < 2 || perenimi.getText().length() < 2 ){
                window.setScene(errorStseen);
            }
            else{
            misNimi(eesnimi.getText() + " " + perenimi.getText());
            window.setScene(stseen2);
        }});

        // Kui klikkida edasi nupule siis läheb next stseeni
        // ESIMESE STSEENI LÕPP!
// _______________________________________________________________________________________________________________________________________________

        //ERROR STSEEN

        errorBorder.setPadding(new Insets(15, 20, 10, 10));


        Label valeNimi = new Label("Palun sisesta oma ees- ja perekonnanimi korrektselt!");
        valeNimi.setPadding(new Insets(10,10,10,35));
        valeNimi.setFont(Font.font("Tahoma",FontWeight.EXTRA_BOLD,10));

        Button tagasi1 = new Button("Tagasi");
        tagasi1.setPadding(new Insets(10, 10, 10, 10));

        tagasi1.setOnMouseClicked(e -> window.setScene(stseen1));

        errorBorder.setCenter(tagasi1);
        errorBorder.setTop(valeNimi);


        // ERROR STSEENI LÕPP

//_______________________________________________________________________________________________________________________________________________

        // TEISE STSEENI ALGUS

        grid1.setAlignment(Pos.CENTER);
        grid1.setHgap(10);
        grid1.setVgap(10);
        grid1.setPadding(new Insets(25, 25, 25, 25));

        Label labelTitle = new Label("Kas ost või hooldus?");
        labelTitle.setFont(Font.font("Tahoma",FontWeight.EXTRA_BOLD,20));
        Image logo = new Image(getClass().getResourceAsStream("logo275.png"));


        kliendiNimi.setFont(Font.font("Tahoma",FontWeight.BOLD,10));
        GridPane.setHalignment(kliendiNimi,HPos.CENTER);

        Label tühi = new Label("");




        Button ost = new Button("  Ost  ");
        Button hooldus = new Button("Hooldus");
        Button ostukorv = new Button("Ostukorv");
        Button back = new Button("tagasi");

        GridPane.setHalignment(ost, HPos.CENTER);
        GridPane.setHalignment(hooldus, HPos.CENTER);
        GridPane.setHalignment(back, HPos.CENTER);
        GridPane.setHalignment(ostukorv,HPos.CENTER);

        grid1.add(labelTitle, 0, 0, 2, 1);          // Put on cell (0,0), span 2 column, 1 row.
        grid1.add(kliendiNimi,0,1,2,1);
        grid1.add(ost, 0, 2,2,1);               // Put on cell (0,1)
        grid1.add(hooldus, 0, 3,2,1);
        grid1.add(ostukorv,0,4,2,1);
        grid1.add(tühi,0,5,2,1);



        grid1.add(back, 0, 6,2,2);

        // LOGO PAREMAS ülemises (VÄIKE)
        grid1.setBackground(new Background(new BackgroundImage(logo,BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        back.setOnMouseClicked(e -> window.setScene(stseen1));                     // KUI KLIKKIDA SIIS LÄHEB TAGASI esimesse stseeni!!!

        ostukorv.setOnMouseClicked(e -> {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    int  n = rand.nextInt(5000) + 1000;
                    testime.getItems().add("Arve №" + n);
                    testime.getItems().add("Arve koostati: " + dtf.format(now));
                    testime.getItems().add("Klient: " + eesnimi.getText() + " " + perenimi.getText());          // SEE ASI ILUSAKS
                    testime.getItems().add(" ");

            for (int i = 0; i < ostuKorv.size() ; i++) {
            testime.getItems().add(ostuKorv.get(i));
            tükid = ostuKorv.get(i).split(" ");
            hind = Integer.parseInt(tükid[4].substring(0,tükid[4].length() - 1));
            summa.add(hind);}
                    for (int i = 0; i < summa.size(); i++) {
                        intMuutuja += summa.get(i);
                    }
            testime.getItems().add(" ");
            testime.getItems().add("Kokku: " + intMuutuja +"€");
             window.setScene(ostuKorviStseen);}
             );

        ost.setOnMouseClicked(e -> {
            try {
                ArrayList<String> autod = Autod.autoNimed();


            } catch (FileNotFoundException e1) {

                System.out.println("Faili pole olemas"); // TEKITA UUS AKEN.
                System.exit(1);

            }
            window.setScene(ostustseen1);
        });
        hooldus.setOnMouseClicked(e -> window.setScene(hooldusestseen1));

        // TEISE STSEENI LÕPP!
        //_______________________________________________________________________________________________________________________________________________

        // OSTUSTSEENI ALGUS

        ostuMenüü.setAlignment(Pos.CENTER);
        ostuMenüü.setHgap(10);
        ostuMenüü.setVgap(10);
        ostuMenüü.setPadding(new Insets(25, 25, 25, 25));

        Text ostTutvustus = new Text("Teie ees on müügilolevad autod: ");

        ArrayList<String> autod = Autod.autoNimed();
        ArrayList<String> mark = new ArrayList<String>();
        ArrayList<String> hind = new ArrayList<String>();
        ArrayList<String> png = new ArrayList<String>();
        ArrayList<String> pngFlip = new ArrayList<String>();

        for (int i = 0; i < autod.size(); i++) {
            String[]tükid = autod.get(i).split(" ");
            mark.add(tükid[0]);
            hind.add(tükid[1]);
            png.add(tükid[2]);
            pngFlip.add(tükid[3]);
        }

        Image imageV40 = new Image(getClass().getResourceAsStream(png.get(0)));
        Image imageV40flip = new Image(getClass().getResourceAsStream(pngFlip.get(0)));
        Button volvo1 = new Button(mark.get(0), new ImageView(imageV40));
        volvo1.setOnMouseEntered(e -> {volvo1.setGraphic(new ImageView(imageV40flip));volvo1.setText(hind.get(0));});
        volvo1.setOnMouseExited(e -> {volvo1.setGraphic(new ImageView(imageV40));volvo1.setText(mark.get(0));});
        volvo1.setOnMousePressed( e -> {misAuto("Mudel: " + mark.get(0) + "  Hind: "+ hind.get(0));window.setScene(soovOstastseen); });


        Image imageV60 = new Image(getClass().getResourceAsStream(png.get(1)));
        Image imageV60flip = new Image(getClass().getResourceAsStream(pngFlip.get(1)));
        Button volvo2 = new Button(mark.get(1) + "  ", new ImageView(imageV60));
        volvo2.setOnMouseEntered(e -> {volvo2.setGraphic(new ImageView(imageV60flip));volvo2.setText(hind.get(1));});
        volvo2.setOnMouseExited(e -> {volvo2.setGraphic(new ImageView(imageV60 ));volvo2.setText(mark.get(1)+ "  ");});
        volvo2.setOnMousePressed( e -> {misAuto("Mudel: " + mark.get(1) + "  Hind: " + hind.get(1));window.setScene(soovOstastseen);});

        Image imageXC90= new Image(getClass().getResourceAsStream(png.get(2)));
        Image imageXC90flip = new Image(getClass().getResourceAsStream(pngFlip.get(2)));
        Button volvo3 = new Button(mark.get(2)+ " ", new ImageView(imageXC90));
        volvo3.setOnMouseEntered(e -> {volvo3.setGraphic(new ImageView(imageXC90flip));volvo3.setText(hind.get(2));});
        volvo3.setOnMouseExited(e -> {volvo3.setGraphic(new ImageView(imageXC90));volvo3.setText(mark.get(2)+ " ");});
        volvo3.setOnMousePressed( e -> {misAuto("Mudel: " + mark.get(2) + "  Hind: " + hind.get(2));window.setScene(soovOstastseen);});


        Image imageS60= new Image(getClass().getResourceAsStream(png.get(3)));
        Image imageS60flip = new Image(getClass().getResourceAsStream(pngFlip.get(3)));
        Button volvo4 = new Button(mark.get(3) + "  ", new ImageView(imageS60));
        volvo4.setOnMouseEntered(e -> {volvo4.setGraphic(new ImageView(imageS60flip));volvo4.setText(hind.get(3));});
        volvo4.setOnMouseExited(e -> {volvo4.setGraphic(new ImageView(imageS60));volvo4.setText(mark.get(3)+ "  ");});
        volvo4.setOnMousePressed( e -> {misAuto("Mudel: " + mark.get(3) + "  Hind: " + hind.get(3));window.setScene(soovOstastseen);});


        Image imageS60cc= new Image(getClass().getResourceAsStream(png.get(4)));
        Image imageS60ccflip = new Image(getClass().getResourceAsStream(pngFlip.get(4)));
        Button volvo5 = new Button(mark.get(4), new ImageView(imageS60cc));
        volvo5.setOnMouseEntered(e -> {volvo5.setGraphic(new ImageView(imageS60ccflip));volvo5.setText(hind.get(4));});
        volvo5.setOnMouseExited(e -> {volvo5.setGraphic(new ImageView(imageS60cc));volvo5.setText(mark.get(4));});
        volvo5.setOnMousePressed( e -> {misAuto("Mudel: " + mark.get(4) + "  Hind: " + hind.get(4));window.setScene(soovOstastseen);});


        Image imageV60cc = new Image(getClass().getResourceAsStream(png.get(5)));
        Image imageV60ccflip = new Image(getClass().getResourceAsStream(pngFlip.get(5)));
        Button volvo6 = new Button(mark.get(5), new ImageView(imageV60cc));
        volvo6.setOnMouseEntered(e -> {volvo6.setGraphic(new ImageView(imageV60ccflip));volvo6.setText(hind.get(5));});
        volvo6.setOnMouseExited(e -> {volvo6.setGraphic(new ImageView(imageV60cc));volvo6.setText(mark.get(5));});
        volvo6.setOnMousePressed( e -> {misAuto("Mudel: " + mark.get(5) + "  Hind: " + hind.get(5));window.setScene(soovOstastseen);});

        Text tere = new Text(" ");


        Button tagasi2 = new Button("Tagasi");
        GridPane.setHalignment(tagasi2, HPos.CENTER);
        tagasi2.setOnMouseClicked(e -> window.setScene(stseen2));




        ostuMenüü.add(ostTutvustus, 1,0,1, 1);
        ostuMenüü.add(volvo1,0,1,1,1);
        ostuMenüü.add(volvo2,1,1,1,1);
        ostuMenüü.add(volvo3,2,1,1,1);
        ostuMenüü.add(volvo4,0,2,1,1);
        ostuMenüü.add(volvo5,1,2,1,1);
        ostuMenüü.add(volvo6,2,2,1,1);
        ostuMenüü.add(tere,1,3,1,1);
        ostuMenüü.add(tagasi2,1,4,1,1);

        Button jah = new Button("Jah"); // EI TEA MIS SEE ON HILJEM VAATAB ÜLE

    // DROPBOX

        // OSTUSTSEENI LÕPP
        //___________________________________________________________________________________________________________________________________________
        // STSEEN, MIS KÜSIB, KAS SOOVID OSTA SEDA AUTOT!

        soovidOsta.setAlignment(Pos.CENTER);
        soovidOsta.setHgap(10);
        soovidOsta.setVgap(10);
        soovidOsta.setPadding(new Insets(25, 25, 25, 25));



        Button b_jah = new Button("Jah");
        Button b_ei = new Button ("Ei");

        b_jah.setOnMousePressed(e -> {ostuKorv.add(millineAuto); window.setScene(stseen2);} );
        b_ei.setOnMouseClicked(e -> window.setScene(ostustseen1));


        soovidOsta.add(kasAuto,0,0,3,1);
        soovidOsta.add(valitudAuto,0,1,3,1);
        soovidOsta.add(b_jah,3,2,1,1);
        soovidOsta.add(b_ei,0,2,1,1);

 // _______________________________________________________________________________________________________________________________________________

        // OSTUKORVI STSEEN ALGAB

        ostuKorvgrid.setAlignment(Pos.CENTER);
        ostuKorvgrid.setHgap(10);
        ostuKorvgrid.setVgap(10);
        ostuKorvgrid.setPadding(new Insets(25, 25, 25, 25));
        Button maksa = new Button("Maksa ära");
        Button tagasi = new Button("Tagasi");
        tagasi.setOnMouseClicked(e -> {window.setScene(stseen2);testime.getItems().clear();});
        Text siinolevad = new Text("Teie ostukorvis olevad asjad: ");


        Text ostukorviElemendid = new Text(nimeKiri(ostuKorv));
        HBox hbox = new HBox(testime);
        ostuKorvgrid.add(siinolevad,0,0,1,3);
        ostuKorvgrid.add(tühi,0,1,1,1);
        ostuKorvgrid.add(maksa,4,2,1,1);
        ostuKorvgrid.add(hbox,0,2,2,2);
        ostuKorvgrid.add(tagasi,4,4,1,1);


        // OSTUKORVI STSEEEN LÕPPEB
// _______________________________________________________________________________________________________________________________________________

        // MAKSA ÄRA STSEEN!!!

        // SIIN TA VÕIKS TÄNADA OSTU EEST!

        //maksa ära STSEEN LÕPPEB

// _______________________________________________________________________________________________________________________________________________
        // HOOLDUSESTSEENI ALGUS

        hooldus1.setAlignment(Pos.CENTER);
        hooldus1.setHgap(10);
        hooldus1.setVgap(10);
        hooldus1.setPadding(new Insets(25, 25, 25, 25));

        Label labelCare = new Label("Valige palun hooldustüüp");
        labelCare.setFont(Font.font("Tahoma",FontWeight.EXTRA_BOLD,20));
        Image logo1 = new Image(getClass().getResourceAsStream("logo275.png"));



        Label tühi1 = new Label("");




        Button kerehooldus = new Button("Kerehooldus");
        Button mootorihooldus = new Button("Mootorihooldus");
        Button salongihooldus = new Button("Salongihooldus");
        Button back1 = new Button("tagasi");

        GridPane.setHalignment(kerehooldus, HPos.CENTER);
        GridPane.setHalignment(mootorihooldus, HPos.CENTER);
        GridPane.setHalignment(salongihooldus, HPos.CENTER);
        GridPane.setHalignment(back1,HPos.CENTER);

        hooldus1.add(labelCare, 0, 0, 2, 1);

        hooldus1.add(kerehooldus, 0, 2,2,1);
        hooldus1.add(mootorihooldus, 0, 3,2,1);
        hooldus1.add(salongihooldus,0,4,2,1);
        hooldus1.add(tühi1,0,5,2,1);



        hooldus1.add(back1, 0, 6,2,2);

        // LOGO
        hooldus1.setBackground(new Background(new BackgroundImage(logo1,BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));



        kerehooldus.setOnMouseClicked(e -> {
            try {
                ArrayList<String> kere = Hooldus.kereTeenus();


            } catch (FileNotFoundException e1) {

                System.out.println("Faili pole olemas"); // TEKITA UUS AKEN.
                System.exit(1);

            }
            window.setScene(kerestseen);
        });

        back1.setOnMouseClicked(e -> window.setScene(stseen2));                   // KUI KLIKKIDA SIIS LÄHEB TAGASI teise stseeni


        // HOOLDUSE ÜLDSTSEENI LÕPP

        //______________________________________________________________________________________________________

        //  HARUDE ALGUS - KEREHOOLDUS

        kerehooldusGrid.setAlignment(Pos.CENTER);
        kerehooldusGrid.setHgap(10);
        kerehooldusGrid.setVgap(10);
        kerehooldusGrid.setPadding(new Insets(25, 25, 25, 25));

        ArrayList<String> kerehooldusInfo = Hooldus.kereTeenus();
        ArrayList<String> teenuseKereNimetus = new ArrayList<String>();
        ArrayList<String> teenusKereHind = new ArrayList<String>();

        for (int i = 0; i < kerehooldusInfo.size(); i++) {
            String[]tükid = kerehooldusInfo.get(i).split(";");
            teenuseKereNimetus.add(tükid[0]);
            teenusKereHind.add(tükid[1]);

        }


        Label labelkere = new Label("Valige teenus, mida soovite kasutada");
        labelkere.setFont(Font.font("Tahoma",FontWeight.EXTRA_BOLD,20));

        Label tühi2 = new Label("");


        Button kriimustus = new Button(teenuseKereNimetus.get(0) + " " + teenusKereHind.get(0));
        kriimustus.setOnMousePressed( e -> {misHooldus("Teenus:" + teenuseKereNimetus.get(0) + " Hind: "+ teenusKereHind.get(0));window.setScene(soovOstastseen1); });

        Button vahatamine = new Button(teenuseKereNimetus.get(1) + " " + teenusKereHind.get(1));
        vahatamine.setOnMousePressed( e -> {misHooldus("Teenus:" + teenuseKereNimetus.get(1) + " Hind: "+ teenusKereHind.get(1));window.setScene(soovOstastseen1); });

        Button rehvid = new Button(teenuseKereNimetus.get(2) + " " + teenusKereHind.get(2));
        rehvid.setOnMousePressed( e -> {misHooldus("Teenus: " + teenuseKereNimetus.get(2) + " Hind: "+ teenusKereHind.get(2));window.setScene(soovOstastseen1); });

        Button back2 = new Button("tagasi");



        GridPane.setHalignment(kriimustus, HPos.CENTER);
        GridPane.setHalignment(vahatamine, HPos.CENTER);
        GridPane.setHalignment(rehvid, HPos.CENTER);
        GridPane.setHalignment(back2,HPos.CENTER);


        kerehooldusGrid.add(labelkere, 0, 0, 2, 1);
        kerehooldusGrid.add(kriimustus, 0, 2,2,1);
        kerehooldusGrid.add(vahatamine, 0, 3,2,1);
        kerehooldusGrid.add(rehvid,0,4,2,1);
        kerehooldusGrid.add(tühi2,0,5,2,1);

        kerehooldusGrid.add(back2, 0, 6,2,2);

        //kriimustus.setOnMouseClicked(e -> window.setScene(kerestseen));
        //vahatamine.setOnMouseClicked(e -> window.setScene(kerestseen));
        //rehvid.setOnMouseClicked(e -> window.setScene(kerestseen));


        back2.setOnMouseClicked(e -> window.setScene(hooldusestseen1));

        // Valik peab minema ostukorvi !












        // KEREHOOLDUSE LÕPP

        //______________________________________________________________________________________________________

        // MOOTORIHOOLDUSE ALGUS










        // MOOTORIHOOLDUSE LÕPP

        //_______________________________________________________________________________________________________
        // STSEEN, MIS KÜSIB, KAS SOOVID SEDA  TEENUST KASUTADA - PEAB JÄÄMA KOIKIFE HOOLDUS HARUDE LÕPPU !!!

        soovidOsta1.setAlignment(Pos.CENTER);
        soovidOsta1.setHgap(10);
        soovidOsta1.setVgap(10);
        soovidOsta1.setPadding(new Insets(25, 25, 25, 25));



        Button hooldusNupp_jah = new Button("Jah");
        Button hooldusNupp_ei = new Button ("Ei");

        hooldusNupp_jah.setOnMousePressed(e -> {ostuKorv.add(millineHooldus); window.setScene(stseen2);} );
        hooldusNupp_ei.setOnMouseClicked(e -> window.setScene(hooldusestseen1));


        soovidOsta1.add(kasHooldus,0,0,3,1);
        soovidOsta1.add(valitudHooldus,0,1,3,1);
        soovidOsta1.add(hooldusNupp_jah,3,2,1,1);
        soovidOsta1.add(hooldusNupp_ei,0,2,1,1);


        //__________________________________________________________________________________________________________________


        window.setTitle("Volvo");  // lava tiitelribale pannakse tekst
        window.setScene(stseen1);  // lavale lisatakse stseen
        window.show();  // lava tehakse nähtavaks
}
    public static void main(String[] args) {
        launch(args);
    }
}