package sample;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Main extends Application {

    Stage window;
    private String täisnimi;


    @Override
    public void start(Stage primaryStage) throws Exception{

        // ÜLDSÄTTED ALGUSES
        window = primaryStage;
        BorderPane piir = new BorderPane(); // Loome esimesele stseenile nö lõuendi
        BorderPane piir1 = new BorderPane(); // loome teisele stseenile nö lõuendi
        BorderPane ost1 = new BorderPane();
        BorderPane hooldus1 = new BorderPane();

        Scene stseen1 = new Scene(piir,300,150); // Loome esimese stseeni
        Scene stseen2 = new Scene(piir1, 300,150); // loome teise stseeni
        Scene ostustseen1 = new Scene (ost1,300,150);
        Scene hooldusestseen1 = new Scene(hooldus1,300,150);
        // ______________________________________________________________________________________________
        // ESIMESE STSEENI ALGUS
        Text nimi = new Text("Tere! \n Olete sisenenud volvo e-teenindusse! \n Palun sisestage allolevasse lahtrisse oma nimi."); // Tekst esimeses stseenis
        TextField field = new TextField(); // Nime sisestamiseks aken
        field.setMinWidth(30); // Anname suuruse aknale
        field.setPrefWidth(80);
        field.setMaxWidth(200);

        Button edasi1 = new Button("Edasi"); // loome "edasi" nupu
        Button turnoff= new Button("välju"); // loome programmist väljumise nupu
        piir.setTop(nimi); // Paneme TOPi teksti
        piir.setCenter(field); // Paneme keskele akna nime sisestamiseks
        piir.setLeft(turnoff); // Vasakule paneme turnoffi
        piir.setRight(edasi1); // paneme paremale järgmisesse stseeni minemise nupu
        BorderPane.setAlignment(edasi1,Pos.BOTTOM_RIGHT); // Edasi1 nupp läheb alla paremale (täpsustus)
        BorderPane.setAlignment(turnoff, Pos.BOTTOM_LEFT); // turnoff läheb alla vasakule
        BorderPane.setAlignment(nimi, Pos.TOP_CENTER); // tekst läheb üles keskele
        BorderPane.setAlignment(field, Pos.CENTER); // aken nime sisestamiseks läheb keskele
        BorderPane.setMargin(turnoff, new Insets(12,0,12,25)); // väike kohendus turnoffi jaoks PÄRAST PEAB ÄRA MUUTMA
        BorderPane.setMargin(edasi1, new Insets(12,25,12,0)); // sama ka edasi1 nupuga  PÄRAST PEAB ÄRA MUUTMA


        turnoff.setOnMouseClicked(event -> System.exit(1)); // Kui klikkida turnoff nupule siis sys.exit
        edasi1.setOnMouseClicked(event -> {
            täisnimi = field.getText();
            System.out.println(field.getText());window.setScene(stseen2);}); // Kui klikkida edasi nupule siis läheb next stseeni
        // ESIMESE STSEENI LÕPP!
        //_______________________________________________________________________________________________________________________________________________


        // TEISE STSEENI ALGUS

        Text esimene = new Text("Kas ost või hooldus?"); // LOome sõnumi selle stseeni jaoks
        Button button2 = new Button("tagasi");// Loome nupu tagasi minekuks
        Button ost = new Button ("Ost"); // loome nupu ostudesse minekuks
        Button hooldus = new Button ("hooldus"); // loome nupu hooldusesse minekuks
        piir1.setCenter(button2);// Paneme selle nupu keskele
        piir1.setTop(esimene);
        piir1.setLeft(ost);
        piir1.setRight(hooldus);
        BorderPane.setAlignment(esimene,Pos.CENTER);
        BorderPane.setAlignment(button2,Pos.CENTER); // paneme selle nupu keskele
        BorderPane.setAlignment(ost,Pos.CENTER_LEFT);
        BorderPane.setAlignment(hooldus,Pos.CENTER_RIGHT);
        button2.setOnMouseClicked(e -> window.setScene(stseen1)); // KUI KLIKKIDA SIIS LÄHEB TAGASI esimesse stseeni!!!
        ost.setOnMouseClicked(e -> {
            Autod a = new Autod();
            try {
                ArrayList<String> autod = a.autoNimed();
            } catch (FileNotFoundException e1) {

                System.out.println("Faili pole olemas"); // TEKITA UUS AKEN.

            }
            window.setScene(ostustseen1);});
        hooldus.setOnMouseClicked(e -> window.setScene(hooldusestseen1));



        // TEISE STSEENI LÕPP!
        //_______________________________________________________________________________________________________________________________________________

        // OSTUSTSEENI ALGUS
        Text ostTutvustus = new Text("Teie ees on müügilolevad autod: ");



        // DROPBOX



        window.setTitle("Küsimus");  // lava tiitelribale pannakse tekst
        window.setScene(stseen1);  // lavale lisatakse stseen
        window.show();  // lava tehakse nähtavaks
    }
    public static void main(String[] args) {
        launch(args);
    }
}
