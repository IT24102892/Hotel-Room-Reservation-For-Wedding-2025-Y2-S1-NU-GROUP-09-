package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "calendarview")
public class Calendarview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String wedingtype; // "Hindu", "Muslim", or "Christian"

    @Column(nullable = false)
    private String Discription;

    @Column(nullable = false)
    private String location; // "Jaffna", "Vaniya", "Mannar", "Kilinochi", "Mullaithevu"

    @Column
    private String image; // URL for image

    @Column(nullable = false)
    private String time; // Start and end time (e.g., "10:00 AM - 2:00 PM")

    // Default constructor
    public Calendarview() {}

    // Parameterized constructor
    public Calendarview(String date, String wedingtype, String discription, String location, String image, String time) {
        this.date = date;
        this.wedingtype = wedingtype;
        this.Discription = discription;
        this.location = location;
        this.image = image;
        this.time = time;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getWedingtype() { return wedingtype; }
    public void setWedingtype(String wedingtype) { this.wedingtype = wedingtype; }

    public String getDiscription() { return Discription; }
    public void setDiscription(String discription) { this.Discription = discription; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    @Override
    public String toString() {
        return "Calendarview{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", wedingtype='" + wedingtype + '\'' +
                ", Discription='" + Discription + '\'' +
                ", location='" + location + '\'' +
                ", image='" + image + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}