/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winsys.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import winsys.project.Appointment.AppointmentWrap;

/**
 *
 * @author acob1
 */
public class Agenda {

    static WinSysProject home;
    static final int WIDTH = 300, HEIGHT = 610, RULES = 40;
    /*static ScrollPane scrollPane = new ScrollPane();
    static VBox vBox = new VBox();
    static ArrayList<Pane> paneList = new ArrayList<Pane>();*/
    static final Color ACT_COLOR = Color.web("0xFFFFFF"), WKND_COLOR = Color.web("0xAEFBBE"), HIGH_COLOR = Color.web("0xB3E8FF"), SCND_COLOR = Color.web("0xB3E8FF");
    final String ICON_LOC = "res/triforce icon.png";
    ArrayList<AppointmentWrap> paneList = new ArrayList<AppointmentWrap>();
    VBox vb = new VBox();
    VBox bigV = new VBox();
    GridPane biggestG = new GridPane();
    Button btnAdd = new Button("+");
    Button btnDel = new Button("-");
    HBox buttonPane = new HBox();
    ScrollPane scrollPane = new ScrollPane();
    Stage window = new Stage();
    GridPane apptGrid = new GridPane();
    ComboBox<String> cmbTime = new ComboBox<String>();
    ComboBox<String> cmbDur = new ComboBox<String>();
    CheckBox chkDay = new CheckBox("All day event");
    Button btnSubmit = new Button("Submit");
    Label lblName = new Label("Appt Name:");
    Label lblDesc = new Label("Description:");
    Label lblTime = new Label("Time:");
    Label lblDur = new Label("Duration (hrs):");
    Label lblBlank = new Label(""), lblBlank2 = new Label(""), lblBlank3 = new Label(""), lblBlank4 = new Label("");
    TextField tfName = new TextField();
    TextArea taDesc = new TextArea();
    int dom, month, year;
    boolean blankAppt = false;
    HashMap<AppointmentWrap, Appointment> appointments = new HashMap<AppointmentWrap, Appointment>();
    AppointmentWrap curPane;

    public void display(Day day) {
        home = Day.home;
        month = home.usersMonth + 1;
        year = home.usersYear;
        dom = day.getDate();

        /*ImageView icon = new ImageView(new Image(ICON_LOC));
        icon.setFitWidth(20);
        icon.setFitHeight(20);*/
        btnAdd.setMinSize(40, 40);
        btnDel.setMinSize(40, 40);
        btnDel.setOnAction(event -> {
            if(curPane == null){
                AlertBox.display("Deletion error", "No appointment is currently selected.");
            }else{
                Appointment check = appointments.remove(curPane);
                if(check == null){
                    blankAppt = false;
                }
                paneList.remove(curPane);
                vb.getChildren().remove(curPane);
                curPane = null;
            }
        }
        );
        
        btnAdd.setOnAction(event -> {
            if (blankAppt) {
                AlertBox.display("Empty Appointment Error", "An empty appointment is already on the schedule. Please fill it out before creating another.");
            } else {
                blankAppt = true;
                AppointmentWrap x = addBlankAppointment();
                x.setOnMouseClicked(doubleEvent -> {
                    if (doubleEvent.getButton().equals(MouseButton.PRIMARY)) {
                        if (doubleEvent.getClickCount() == 2) {
                            addAppointmentDetails();
                        }
                    }

                });
                paneList.add(x);
                vb.getChildren().add(x);
            }
        }
        );
        

        btnSubmit.setOnAction(event -> {
            AppointmentWrap x = addAppointment();
            if (x != null) {
                tfName.setText(null);
                taDesc.setText(null);
                cmbTime.getSelectionModel().select(null);
                cmbDur.getSelectionModel().select(null);
                chkDay.setSelected(false);
                paneList.add(x);
                for(AppointmentWrap y : paneList){
                    if(!y.isActive()){
                        paneList.remove(y);
                        vb.getChildren().remove(y);
                        //System.out.println(y.isActive());
                    }
                }
                vb.getChildren().add(x);
                blankAppt = false;
            }
        });
        //VBoxvb = new VBox();
        //ArrayList<Pane> paneList = new ArrayList<Pane>();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(
                "Agenda " + month + "/" + dom + "/" + year);
        window.setMinWidth(WIDTH);
        window.setMinHeight(HEIGHT);
        window.setMaxWidth(WIDTH);
        window.setMaxHeight(HEIGHT);
        window.setResizable(
                false);
        /* for(int i = 0; i< HEIGHT/RULES-4; i++){
            Pane x = new Pane();
            x.setMinSize(WIDTH, RULES);
            x.setBackground(new Background(new BackgroundFill(ACT_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            x.setBorder(new Border(new BorderStroke(SCND_COLOR, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            x.setOnMouseEntered((MouseEvent t) -> {
                x.setBackground(new Background(new BackgroundFill(HIGH_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            });
            x.setOnMouseExited((MouseEvent t) -> {
                x.setBackground(new Background(new BackgroundFill(ACT_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            });
            paneList.add(x);
        }
        
        for(Pane x: paneList){
            vb.getChildren().add(x);
        }*/
        buttonPane.getChildren().add(btnAdd);
        buttonPane.getChildren().add(btnDel);
        buttonPane.setMinHeight(40);
        buttonPane.setMaxHeight(40);
        buttonPane.setAlignment(Pos.BASELINE_CENTER);
        vb.setMinHeight(500);
        vb.setMinWidth(294);
        scrollPane.setContent(vb);
        scrollPane.setMinHeight(534);
        scrollPane.setMinWidth(294);
        //layout.getChildren().add(closeBtn);
        //layout.setAlignment(Pos.CENTER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        bigV.getChildren().addAll(scrollPane, buttonPane);
        biggestG.add(bigV, 0, 0);
        Scene scene = new Scene(biggestG);

        window.setScene(scene);

        window.setOnCloseRequest(event -> {
            System.out.println("trying to get home!");
            home.setAppointments(day.getDate(), appointments);
        });
        
        window.showAndWait();
        
    }

    private AppointmentWrap addBlankAppointment() {
        AppointmentWrap x = new Appointment(this).getApptWrap();
        //paneList.add(x);
        //vb.getChildren().add(x);
        //vb.getChildren().add(test);
        return x;
    }

    private AppointmentWrap addAppointment() {
        if (tfName.getText() == null || cmbTime.getSelectionModel().getSelectedItem() == null || cmbDur.getSelectionModel().getSelectedItem() == null) {
            AlertBox.display("Form Error", "Either appointment name, time or duration has not been selected");
            return null;
        }
        String name, desc;
        int day1, month1, year1;
        String time;
        String dur;
        boolean isAllDay;
        name = tfName.getText();
        System.out.println(name);
        desc = taDesc.getText();
        day1 = dom;
        month1 = month;
        year1 = year;
        time = cmbTime.getSelectionModel().getSelectedItem();
        dur = cmbDur.getSelectionModel().getSelectedItem();
        isAllDay = chkDay.isSelected();
        Appointment a = new Appointment(this, month1, day1, year1, name, desc, dur, time, isAllDay);
        AppointmentWrap x = a.getApptWrap();
        appointments.put(x, a);
        return x;
    }

    public void addAppointmentDetails() {
        window.setMaxWidth(800);
        window.setWidth(800);
        biggestG.getChildren().clear();
        apptGrid.getChildren().clear();
        biggestG.add(bigV, 0, 0);
        apptGrid.setMinWidth(500);
        for (int i = 1; i <= 24; i++) {
            cmbTime.getItems().add(Integer.toString(i - 1) + ":30");
            cmbTime.getItems().add(Integer.toString(i) + ":00");
            cmbDur.getItems().add(Integer.toString(i - 1) + ":30");
            cmbDur.getItems().add(Integer.toString(i) + ":00");
        }
        apptGrid.add(lblName, 0, 0);
        apptGrid.add(lblDesc, 0, 1);
        apptGrid.add(lblTime, 0, 2);
        apptGrid.add(lblDur, 0, 3);
        apptGrid.add(tfName, 1, 0);
        apptGrid.add(taDesc, 1, 1);
        apptGrid.add(cmbTime, 1, 2);
        apptGrid.add(cmbDur, 1, 3);
        apptGrid.add(lblBlank, 1, 4);
        apptGrid.add(chkDay, 1, 5);
        apptGrid.add(lblBlank2, 1, 6);
        apptGrid.add(lblBlank3, 1, 7);
        apptGrid.add(btnSubmit, 1, 8);
        lblName.setMinWidth(100);
        lblDesc.setMinWidth(100);
        lblTime.setMinWidth(100);
        lblDur.setMinWidth(100);
        lblName.setAlignment(Pos.CENTER_RIGHT);
        lblDesc.setAlignment(Pos.CENTER_RIGHT);
        lblTime.setAlignment(Pos.CENTER_RIGHT);
        lblDur.setAlignment(Pos.CENTER_RIGHT);
        biggestG.add(apptGrid, 1, 0);
        scrollPane.setMaxWidth(291);
        vb.setMaxWidth(291);
        buttonPane.setMaxWidth(291);
    }

}
