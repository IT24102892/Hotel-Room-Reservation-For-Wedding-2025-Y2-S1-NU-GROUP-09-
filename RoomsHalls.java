package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roomsandhalls")
public class RoomsHalls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String types; // "rooms" or "halls"

    @Column(nullable = false)
    private int number; // capacity: rooms (1 or 2), halls (100 to 1000)

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    private boolean aircondition; // AC or non-AC

    @Column
    private String image; // URL for image

    @Column(nullable = false)
    private int price; // Price for room or hall

    // Default constructor
    public RoomsHalls() {}

    // Parameterized constructor
    public RoomsHalls(String types, int number, boolean available, boolean aircondition, String image, int price) {
        this.types = types;
        this.number = number;
        this.available = available;
        this.aircondition = aircondition;
        this.image = image;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTypes() { return types; }
    public void setTypes(String types) { this.types = types; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public boolean isAircondition() { return aircondition; }
    public void setAircondition(boolean aircondition) { this.aircondition = aircondition; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    @Override
    public String toString() {
        return "RoomsHalls{" +
                "id=" + id +
                ", types='" + types + '\'' +
                ", number=" + number +
                ", available=" + available +
                ", aircondition=" + aircondition +
                ", image='" + image + '\'' +
                ", price=" + price +
                '}';
    }
}