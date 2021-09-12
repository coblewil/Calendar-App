/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winsys.project;

import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author acob1
 */
public final class Day extends GridPane {

    private boolean weekend = false, active = false;
    private int date;
    final short COLUMNS = 2;
    final int WIDTH = 110, HEIGHT = 110;
    private Label dateLabel;
    final VBox datePane;
    final GridPane apptPane;
    ArrayList<Label> VBoxLabels = new ArrayList<Label>();
    final Color ACT_COLOR = Color.web("0xFFFFFF"), WKND_COLOR = Color.web("0xAEFBBE"), HIGH_COLOR = Color.web("0xB3E8FF"), SCND_COLOR = Color.web("0xDDDDDD");

    static WinSysProject home;

    public Day(int date, WinSysProject home) {
        this.home = home;
        datePane = new VBox();
        apptPane = new GridPane();

        this.date = date;
        if (date < 0) {
            dateLabel = new Label("");
        } else {
            dateLabel = new Label(Integer.toString(date));
        }
        dateLabel.setPrefSize(WIDTH, HEIGHT);
        dateLabel.setMinSize(WIDTH, HEIGHT);
        dateLabel.setAlignment(Pos.TOP_LEFT);
        GridPane.setHalignment(dateLabel, HPos.LEFT);
        GridPane.setValignment(dateLabel, VPos.TOP);
        VBoxLabels.add(dateLabel);
        datePane.getChildren().add(dateLabel);
        datePane.setBackground(new Background(new BackgroundFill(ACT_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        this.setBorder(new Border(new BorderStroke(SCND_COLOR, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setActable(date);

        datePane.setOnMouseClicked(event -> {
            handleClick();
        });

        this.add(datePane, 0, 0);
        this.add(apptPane, 0, 1);
    }
    
    public void redrawVBox(Node... x){
        this.home = home;
        this.date = date;
        if (date < 0) {
            dateLabel = new Label("");
        } else {
            dateLabel = new Label(Integer.toString(date));
        }
        dateLabel.setPrefSize(WIDTH, HEIGHT);
        dateLabel.setMinSize(WIDTH, HEIGHT);
        dateLabel.setAlignment(Pos.TOP_LEFT);
        GridPane.setHalignment(dateLabel, HPos.LEFT);
        GridPane.setValignment(dateLabel, VPos.TOP);
        datePane.getChildren().add(dateLabel);
        for(Node y : x){
            datePane.getChildren().add(y);
        }
        datePane.setBackground(new Background(new BackgroundFill(ACT_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        this.setBorder(new Border(new BorderStroke(SCND_COLOR, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setActable(date);

        datePane.setOnMouseClicked(event -> {
            handleClick();
        });

        this.add(datePane, 0, 0);
        this.add(apptPane, 0, 1);
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
        setLabel(this.date);
        setActable(this.date);
    }

    private void setLabel(int date) {
        dateLabel.setText(Integer.toString(date));
    }

    public boolean isWeekend() {
        return weekend;
    }

    public void setWeekend(boolean x) {
        weekend = x;
        if (weekend) {
            datePane.setBackground(new Background(new BackgroundFill(WKND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            datePane.setBackground(new Background(new BackgroundFill(ACT_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    public void setActable(int date) {
        if (date > 0) {
            active = true;
        } else {
            active = false;
        }
        if (date > 0 && !weekend) {
            datePane.setOnMouseEntered((MouseEvent t) -> {
                datePane.setBackground(new Background(new BackgroundFill(HIGH_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            });
            datePane.setOnMouseExited((MouseEvent t) -> {
                datePane.setBackground(new Background(new BackgroundFill(ACT_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            });
        } else if (date > 0 && weekend) {
            datePane.setOnMouseEntered((MouseEvent t) -> {
                datePane.setBackground(new Background(new BackgroundFill(HIGH_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            });
            datePane.setOnMouseExited((MouseEvent t) -> {
                datePane.setBackground(new Background(new BackgroundFill(WKND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            });
        }
    }

    public void handleClick() {
        if (!active) {
            return;
        } else {
            new Agenda().display(this);
        }
    }

    public void addAppt() {
        datePane.getChildren().clear();
        VBoxLabels.clear();
        if (date < 0) {
            dateLabel = new Label("");
        } else {
            dateLabel = new Label(Integer.toString(date));
        }
        dateLabel.setPrefSize(WIDTH, HEIGHT);
        dateLabel.setMinSize(WIDTH, HEIGHT);
        dateLabel.setAlignment(Pos.TOP_LEFT);
        GridPane.setHalignment(dateLabel, HPos.LEFT);
        GridPane.setValignment(dateLabel, VPos.TOP);
        VBoxLabels.add(dateLabel);
        Label x = new Label("!");
        VBoxLabels.add(x);
        //clear();
        for(Label y : VBoxLabels){
            y.setPrefSize(WIDTH, HEIGHT/(VBoxLabels.size()));
            y.setMinSize(WIDTH, HEIGHT/(VBoxLabels.size()));
            y.setMaxSize(WIDTH, HEIGHT/(VBoxLabels.size()));
        }
        x.setAlignment(Pos.CENTER);
        datePane.getChildren().add(dateLabel);
        datePane.getChildren().add(x);
    }

    public void addAppts(int number) {
        ArrayList<Node> list = new ArrayList<Node>();
        for (int i = 0; i < number; i++) {
            Label x = new Label("!");
            Label blank = new Label(" ");
            list.add(x);
        }
    }

}
