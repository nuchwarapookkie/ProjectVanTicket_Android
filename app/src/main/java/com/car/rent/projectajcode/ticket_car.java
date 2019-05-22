package com.car.rent.projectajcode;

public class ticket_car {
    private String Date_Day;
    private String Name;
    private String Phone;
    private String Seat;
    private String Start_end;
    private String Start_way;
    private String time_end;
    private String time_start;

    public ticket_car(){}

    public ticket_car(String date_Day, String name, String phone, String seat, String start_end, String start_way, String time_end, String time_start) {
        Date_Day = date_Day;
        Name = name;
        Phone = phone;
        Seat = seat;
        Start_end = start_end;
        Start_way = start_way;
        this.time_end = time_end;
        this.time_start = time_start;
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

    public String getStart_end() {
        return Start_end;
    }

    public void setStart_end(String start_end) {
        Start_end = start_end;
    }

    public String getStart_way() {
        return Start_way;
    }

    public void setStart_way(String start_way) {
        Start_way = start_way;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }
}
