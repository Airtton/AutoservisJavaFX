package sample;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Main extends Application {

    Stage window;
    String täisnimi;
    String valitudAuto;
    Text kas = new Text();
    ArrayList<String> ostuKorv = new ArrayList<>();



    @Override
    public void start(Stage primaryStage) throws Exception {

        // ÜLDSÄTTED ALGUSES
        window = primaryStage;
        GridPane grid = new GridPane();// Loome esimesele stseenile nö lõuendi
        GridPane grid1 = new GridPane();// loome teisele stseenile nö lõuendi
        BorderPane hooldus1 = new BorderPane();
        GridPane ostuMenüü = new GridPane();
        GridPane soovidOsta = new GridPane();

        Scene stseen1 = new Scene(grid, 400, 275); // Loome esimese stseeni
        Scene stseen2 = new Scene(grid1, 400, 275); // loome teise stseeni
        Scene ostustseen1 = new Scene(ostuMenüü, 650, 600) ;
        Scene soovOstastseen = new Scene(soovidOsta,400,275);
        Scene hooldusestseen1 = new Scene(hooldus1, 300, 150);
        // ______________________________________________________________________________________________
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

        TextField eesnimi = new TextField("eesnimi");
        grid.add(eesnimi, 1, 2);

        Label surname = new Label("Perenimi");
        grid.add(surname, 0, 3);

        TextField perenimi = new TextField("Perenimi");
        grid.add(perenimi, 1, 3);

        Button edasi = new Button("Edasi");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(edasi);
        grid.add(hbBtn, 1, 5);

        Button turnoff = new Button("Välju");
        grid.add(turnoff,0,5,1,1);


        turnoff.setOnMouseClicked(event -> System.exit(1)); // Kui klikkida turnoff nupule siis sys.exit
        edasi.setOnMouseClicked(event -> {
            täisnimi = eesnimi.getText() + " " + perenimi.getText();
            System.out.println(täisnimi);
            window.setScene(stseen2);
        });
        // Kui klikkida edasi nupule siis läheb next stseeni
        // ESIMESE STSEENI LÕPP!
        //_______________________________________________________________________________________________________________________________________________


        // TEISE STSEENI ALGUS
        grid1.setAlignment(Pos.CENTER);
        grid1.setHgap(10);
        grid1.setVgap(10);
        grid1.setPadding(new Insets(25, 25, 25, 25));

        Label labelTitle = new Label("Kas ost või hooldus?");
        labelTitle.setFont(Font.font("Tahoma",FontWeight.EXTRA_BOLD,20));
        Image logo = new Image(getClass().getResourceAsStream("logo275.png"));

        Text tühik = new Text("Klient: "+ täisnimi);
        tühik.setFont(Font.font("Tahoma",FontWeight.BOLD,10));
        GridPane.setHalignment(tühik,HPos.CENTER);

        Label tühi = new Label("");
        grid1.add(tühik,0,1,2,1);
        grid1.add(tühi,0,4,2,1);


        // Put on cell (0,0), span 2 column, 1 row.
        grid1.add(labelTitle, 0, 0, 2, 1);


        Button ost = new Button("  Ost  ");

        Button hooldus = new Button("Hooldus");


        Button back = new Button("tagasi");

        GridPane.setHalignment(ost, HPos.CENTER);

        // Put on cell (0,1)
        grid1.add(ost, 0, 2,2,1);


        GridPane.setHalignment(hooldus, HPos.CENTER);
        grid1.add(hooldus, 0, 3,2,1);


        // Horizontal alignment for Login button.
        GridPane.setHalignment(back, HPos.CENTER);
        grid1.add(back, 0, 5,2,2);

        // LOGO PAREMAS VASAKUS NURGAS (VÄIKE)
        grid1.setBackground(new Background(new BackgroundImage(logo,BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

       back.setOnMouseClicked(e -> window.setScene(stseen1)); // KUI KLIKKIDA SIIS LÄHEB TAGASI esimesse stseeni!!!
        ost.setOnMouseClicked(e -> {
            try {
                ArrayList<String> autod = Autod.autoNimed();
                ArrayList<String> slotid = new ArrayList<String>();
                for (int i = 0; i < autod.size(); i++) {
                    slotid.add("image" + i);
                    System.out.println(autod.get(i));
                }

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
        volvo1.setOnMouseClicked( e -> {window.setScene(soovOstastseen);
        valitudAuto = mark.get(0) + " " + hind.get(0);
        kas.setText(mark.get(0) + " " + hind.get(0));
        });


        Image imageV60 = new Image(getClass().getResourceAsStream(png.get(1)));
        Image imageV60flip = new Image(getClass().getResourceAsStream(pngFlip.get(1)));
        Button volvo2 = new Button(mark.get(1), new ImageView(imageV60));
        volvo2.setOnMouseEntered(e -> {volvo2.setGraphic(new ImageView(imageV60flip));volvo2.setText(hind.get(1));});
        volvo2.setOnMouseExited(e -> {volvo2.setGraphic(new ImageView(imageV60 ));volvo2.setText(mark.get(1));});
        volvo1.setOnMouseClicked( e -> {window.setScene(soovOstastseen);});

        Image imageXC90= new Image(getClass().getResourceAsStream(png.get(2)));
        Image imageXC90flip = new Image(getClass().getResourceAsStream(pngFlip.get(2)));
        Button volvo3 = new Button(mark.get(2), new ImageView(imageXC90));
        volvo3.setOnMouseEntered(e -> {volvo3.setGraphic(new ImageView(imageXC90flip));volvo3.setText(hind.get(2));});
        volvo3.setOnMouseExited(e -> {volvo3.setGraphic(new ImageView(imageXC90));volvo3.setText(mark.get(2));});
        volvo3.setOnMouseClicked( e -> {window.setScene(soovOstastseen);});

        ostuMenüü.add(volvo1,0,1,1,1);
        ostuMenüü.add(volvo2,1,1,1,1);
        ostuMenüü.add(volvo3,2,1,1,1);

        Button jah = new Button("Jah");

    // DROPBOX
        // OSTUSTSEENI LÕPP
        //____________________________________________________________________________________________________________________
        // STSEEN, MIS KÜSIB, KAS SOOVID OSTA SEDA AUTOT!
        soovidOsta.setAlignment(Pos.CENTER);
        soovidOsta.setHgap(10);
        soovidOsta.setVgap(10);
        soovidOsta.setPadding(new Insets(25, 25, 25, 25));



        Button b_jah = new Button("jah");
        Button b_ei = new Button ("Ei");

        soovidOsta.add(kas,0,0,3,1);
        soovidOsta.add(b_jah,0,2,1,1);
        soovidOsta.add(b_ei,3,2,1,1);







        window.setTitle("Küsimus");  // lava tiitelribale pannakse tekst
        window.setScene(stseen1);  // lavale lisatakse stseen
        window.show();  // lava tehakse nähtavaks
}
    public static void main(String[] args) {
        launch(args);
    }
}
