/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winsys.project;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author acob1
 */
public class Calendar extends GridPane {

    final String[] WEEKDAY_STRINGS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    final Label[] WEEKDAY_LABELS = new Label[7];
    static WinSysProject home;
    //GridPane weekdayPane = new GridPane(), mainPane = new GridPane();
    Day[][] page = new Day[7][6];//flipped to account from grid coordinates, +1 added to row for labels

    public Calendar(WinSysProject home) {
        this.home = home;
        refresh();
        for (int i = 0; i < 7; i++) {
            WEEKDAY_LABELS[i] = new Label(WEEKDAY_STRINGS[i]);
            GridPane.setHalignment(WEEKDAY_LABELS[i], HPos.CENTER);
            this.add(WEEKDAY_LABELS[i], i, 0);
        }
    }

    public void setValueAt(int i, int col, int row) {
        Day x = page[col][row];
        if(col%7 == 0 || col%7 == 6){
            x.setWeekend(true);
        }
        x.setDate(i);
        //System.out.println(i + " " + x.getDate());
    }
    
    public void refresh(){
        this.getChildren().clear();
        for (int i = 0; i < 7; i++) {
            WEEKDAY_LABELS[i] = new Label(WEEKDAY_STRINGS[i]);
            GridPane.setHalignment(WEEKDAY_LABELS[i], HPos.CENTER);
            this.add(WEEKDAY_LABELS[i], i, 0);
        }
        //grid.add(grid, WIDTH, WIDTH);
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 6; row++) {
                Day x = new Day(-1, home);
                if(col%7 == 0 || col%7==6){
                    x.setWeekend(true);
                }
                page[col][row] = x;
                this.add(page[col][row], col, row+1);
            }
        }
    }
    
    public Day getDay(int date){
        Day x = null;
        for(Day[] y: page){
            for(Day z: y){
                if(z.getDate()==date){
                    x = z;
                    break;
                }
            }
        }
        return x; 
    }
}
