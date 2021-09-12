/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winsys.project;

import java.util.Date;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author acob1
 */
public class Appointment {

    Agenda agenda;
    boolean active = false;
    Date date;
    int dom, month, year;
    String name;
    String desc;
    String dur, time;
    AppointmentWrap apptWrap;
    boolean allDay;

    public Appointment(Agenda agenda) {
        this.agenda = agenda;
    }

    public Appointment(Agenda agenda, int month, int dom, int year, String name, String desc, String dur, String time, boolean allDay) {
        this.agenda = agenda;
        active = true;
        this.month = month;
        this.dom = dom;
        this.year = year;
        this.name = name;
        this.desc = desc;
        this.dur = dur;
        this.time = time;
        this.allDay = allDay;
        if (allDay) {
            dur = "24:00";
        }
    }

    public void setDuration(String duration) {
        this.dur = duration;
    }

    public void setTime(String time) {
        this.dur = time;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.desc = description;
    }

    public AppointmentWrap getApptWrap() {
        return new AppointmentWrap();
    }

    public boolean isActive() {
        return active;
    }

    public class AppointmentWrap extends Pane {

        final int WIDTH = Agenda.WIDTH, RULES = Agenda.RULES;
        final Color ACT_COLOR = Agenda.ACT_COLOR, WKND_COLOR = Agenda.WKND_COLOR, HIGH_COLOR = Agenda.HIGH_COLOR, SCND_COLOR = Agenda.SCND_COLOR;
        Label lblName = new Label();
        Label lblTime = new Label();
        Label lblBlank = new Label("   ");
        GridPane smallgrid = new GridPane();

        AppointmentWrap() {
            if (active) {
                lblName.setText(name);
                lblTime.setText(time);
                lblName.setAlignment(Pos.CENTER_LEFT);
                lblTime.setAlignment(Pos.CENTER_LEFT);
                smallgrid.add(lblTime, 0, 0);
                smallgrid.add(lblBlank, 1, 0);
                smallgrid.add(lblName, 2, 0);
                smallgrid.setMinSize(WIDTH, RULES);
                this.getChildren().add(smallgrid);
            }
            this.setMinSize(WIDTH, RULES);
            this.setBackground(new Background(new BackgroundFill(ACT_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            this.setBorder(new Border(new BorderStroke(SCND_COLOR, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            this.setOnMouseEntered((MouseEvent t) -> {
                this.setBackground(new Background(new BackgroundFill(HIGH_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            });
            this.setOnMouseExited((MouseEvent t) -> {
                this.setBackground(new Background(new BackgroundFill(ACT_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            });
            this.setOnMouseClicked(doubleEvent -> {
                if (doubleEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (doubleEvent.getClickCount() == 2) {
                        System.out.println("Hey");
                        getAppointmentDetails();
                    }
                }

            });
        }

        public boolean isActive() {
            return active;
        }

        public void getAppointmentDetails() {
            System.out.println("Ya");
            agenda.curPane = this;
            if (!active) {
                agenda.addAppointmentDetails();
            }
        }

    }

}
