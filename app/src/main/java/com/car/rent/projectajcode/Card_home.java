package com.car.rent.projectajcode;

public class Card_home {
    private String Name;
    private String Phone;
    private String Seat;
    private String Date_Day;
    private String Start_way;
    private String Start_end;
    private String time_start;
    private String time_end;

    public Card_home(){

    }

    public Card_home(String name, String phone, String seat, String date_day, String start_way, String start_end, String time_start, String time_end) {
        Name = name;
        Phone = phone;
        Seat = seat;
        Date_Day = date_day;
        Start_way = start_way;
        Start_end = start_end;
        this.time_start = time_start;
        this.time_end = time_end;
    }

    public String getDate_Day() {
        return Date_Day;
    }

    public void setDate_Day(String date_Day) {
        Date_Day = date_Day;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getSeat() {
        return Seat;
    }

    public void setSeat(String seat) {
        Seat = seat;
    }


    public String getStart_way() {
        return Start_way;
    }

    public void setStart_way(String start_way) {
        Start_way = start_way;
    }

    public String getStart_end() {
        return Start_end;
    }

    public void setStart_end(String start_end) {
        Start_end = start_end;
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
}
