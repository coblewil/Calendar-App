/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winsys.project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author acob1
 */
public class WinSysProject extends Application {

    Stage window;
    Image icon;
    final int WIDTH = 890, HEIGHT = 757;
    final String TITLE = "Calendar App";
    final String[] WEEKDAY_STRINGS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"}, MONTH_STRINGS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    final Label[] WEEKDAY_LABELS = new Label[7], MONTH_LABELS = new Label[12];
    Label yearLabel = new Label(), monthLabel = new Label(), dayLabel = new Label(), monthYearLabel = new Label();
    Date todaysDate;
    static String todaysDOW, userDOW;
    public int todaysDOM, todaysMonth, todaysYear, usersDOM, usersMonth, usersYear;
    ComboBox<String> cmbYear;
    Calendar calendarPane;
    Button btnLast, btnNext;
    GridPane calHub, labelHub;
    BorderPane mainPane;
    HashMap<String, HashMap> appointments = new HashMap<String, HashMap>();

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        icon = new Image("res/triforce icon.png");
        window.getIcons().add(icon);
        calendarPane = new Calendar(this);
        calHub = new GridPane();
        labelHub = new GridPane();
        mainPane = new BorderPane();

        window.setMinWidth(908);
        window.setMinHeight(787);

        calendarPane.setMinSize(770, 700);
        calHub.setMinSize(860, 720);

        calendarPane.setPrefSize(770, 700);

        btnLast = new Button("<");
        btnNext = new Button(">");
        cmbYear = new ComboBox<String>();

        btnLast.setMinSize(35, 40);
        btnNext.setMinSize(35, 40);
        cmbYear.setMinSize(90, 40);

        btnLast.setPrefWidth(35);
        btnNext.setPrefWidth(35);
        cmbYear.setPrefWidth(90);

        btnLast.setOnAction(event -> lastMonth());
        btnNext.setOnAction(event -> nextMonth());
        cmbYear.setOnAction(event -> skipYear());

        initDates();

        calHub.add(btnLast, 0, 0);
        calHub.add(btnNext, 2, 0);
        GridPane.setHalignment(btnLast, HPos.RIGHT);
        calHub.add(calendarPane, 1, 0);
        calHub.add(cmbYear, 1, 1);
        GridPane.setHalignment(cmbYear, HPos.CENTER);

        labelHub.add(monthYearLabel, 0, 0);
        GridPane.setHalignment(monthYearLabel, HPos.CENTER);

        labelHub.setAlignment(Pos.CENTER);

        mainPane.setCenter(calHub);
        mainPane.setTop(labelHub);

        Scene scene = new Scene(mainPane, WIDTH, HEIGHT);

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void initDates() {
        todaysDate = new Date();

        DateFormat dateFormatDayFull = new SimpleDateFormat("EEEE");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        todaysDOW = dateFormatDayFull.format(todaysDate);
        LocalDate localDate = todaysDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        todaysDOM = localDate.getDayOfMonth();
        todaysMonth = localDate.getMonthValue() - 1;
        todaysYear = localDate.getYear();
        usersDOM = todaysDOM;
        usersMonth = todaysMonth;
        usersYear = todaysYear;

        for (int i = todaysYear - 100; i < todaysYear + 100; i++) {
            cmbYear.getItems().add(Integer.toString(i));
        }

        for (int i = 0; i < 7; i++) {
            WEEKDAY_LABELS[i] = new Label(WEEKDAY_STRINGS[i]);
        }

        for (int i = 0; i < 12; i++) {
            MONTH_LABELS[i] = new Label(MONTH_STRINGS[i]);
        }

        yearLabel.setText(Integer.toString(todaysYear));
        monthLabel.setText(MONTH_STRINGS[todaysMonth - 1]);
        dayLabel.setText(todaysDOW);

        refreshCalendar(usersMonth, usersYear);
    }

    public void refreshCalendar(int month, int year) {
        int nod, som;

        btnLast.setDisable(false);
        btnNext.setDisable(false);
        if (month == 0 && year <= todaysYear - 10) {
            btnLast.setDisable(true);
        }
        if (month == 11 && year >= todaysYear + 100) {
            btnNext.setDisable(true);
        }

        cmbYear.getSelectionModel().select(Integer.toString(year));

        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);

        yearLabel.setText(Integer.toString(usersYear));
        monthLabel.setText(MONTH_STRINGS[usersMonth]);

        monthYearLabel.setText(monthLabel.getText() + " " + yearLabel.getText());

        calendarPane.refresh();
        for (int i = 1; i <= nod; i++) {
            int row = (i + som - 2) / 7;
            int column = (i + som - 2) % 7;
            //System.out.printf("%s: %s, %s\n", i, row, column);
            calendarPane.setValueAt(i, column, row); //flipped to account for grid coordinates
            checkAppointments(i);
        }
    }

    public void lastMonth() {
        if (usersMonth == 0) {
            usersMonth = 11;
            usersYear -= 1;
        } else {
            usersMonth -= 1;
        }
        //System.out.println("next" + usersYear);
        refreshCalendar(usersMonth, usersYear);
    }

    public void nextMonth() {
        if (usersMonth == 11) {
            usersMonth = 0;
            usersYear += 1;
        } else {
            usersMonth += 1;
        }
        //System.out.println("last"+usersYear);
        refreshCalendar(usersMonth, usersYear);
    }

    public void skipYear() {
        if (cmbYear.getSelectionModel().getSelectedItem() != null) {
            String b = cmbYear.getSelectionModel().getSelectedItem();
            usersYear = Integer.parseInt(b);
            //System.out.println("combo"+usersYear);
            refreshCalendar(usersMonth, usersYear);
        }
    }
    
    public String keyCreator(int x, int y, int z){
        return Integer.toString(x)+" "+Integer.toString(y)+" "+Integer.toString(z);
    }
    
    public void setAppointments(int date, HashMap appointmentsDay) {
        Day x = null;
        int numAppts = appointmentsDay.size();
        if (appointments.get(keyCreator(date, usersMonth, usersYear)) != null) {
            appointments.replace(keyCreator(date, usersMonth, usersYear), appointmentsDay);
        } else {
            appointments.put(keyCreator(date, usersMonth, usersYear), appointmentsDay);
        }
        if (appointmentsDay.size() > 0) {
            x = calendarPane.getDay(date);
        }
        if (x != null) {
            for (int i = 0; i < numAppts; i++) {
                x.addAppt();
            }
        }
        System.out.print("Made it home!");
    }
    
    public void checkAppointments(int date){
        Day x = null;
        HashMap apptLog = appointments.get(keyCreator(date, usersMonth, usersYear));
        if(apptLog == null){
            System.out.printf("hi hi hi");
        }
        if (apptLog != null && apptLog.size() > 0) {
            System.out.println("hi");;
            x = calendarPane.getDay(date);
        }
        if (x != null) {
            for (int i = 0; i < apptLog.size(); i++) {
                x.addAppt();
            }
        }
        //System.out.println("Made it home again!");
    }
}
