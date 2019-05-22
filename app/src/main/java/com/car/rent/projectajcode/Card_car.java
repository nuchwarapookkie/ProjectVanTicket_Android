package com.car.rent.projectajcode;

public class Card_car {
    private String time_start;
    private String time_end;
    private String finish_way;
    private String date;
    private String seat_check;
    public Card_car(){

    }
    public Card_car(String time_start, String time_end, String finish_way, String date,String seat_check) {
        this.time_start = time_start;
        this.time_end = time_end;
        this.finish_way = finish_way;
        this.date = date;
        this.seat_check = seat_check;
    }

    public void setSeat_check(String seat_check) {
        this.seat_check = seat_check;
    }

    public String getSeat_check() {
        return seat_check;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getFinish_way() {
        return finish_way;
    }

    public void setFinish_way(String finish_way) {
        this.finish_way = finish_way;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
