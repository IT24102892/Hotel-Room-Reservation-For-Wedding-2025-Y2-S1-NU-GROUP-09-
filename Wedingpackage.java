package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wedingpackage")
public class Wedingpackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String packageName;

    @Column(nullable = false)
    private String hallprice; // Stored as "$" followed by digits

    @Column(nullable = false)
    private String foodtype; // "veg" or "nonveg"

    @Column(nullable = false)
    private boolean addon; // drinks/snacks

    @Column(nullable = false)
    private int persons;

    @Column
    private String image; // URL for image

    // Default constructor
    public Wedingpackage() {}

    // Parameterized constructor
    public Wedingpackage(String packageName, String hallprice, String foodtype, boolean addon, int persons, String image) {
        this.packageName = packageName;
        this.hallprice = formatHallPrice(hallprice); // Format price on construction
        this.foodtype = foodtype;
        this.addon = addon;
        this.persons = persons;
        this.image = image;
    }

    // Helper method to format hallprice
    private String formatHallPrice(String price) {
        if (price == null || price.isEmpty()) return null;
        if (!price.matches("\\d+")) throw new IllegalArgumentException("Hall price must be a number");
        return "$" + price;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }

    public String getHallprice() { return hallprice; }
    public void setHallprice(String hallprice) { this.hallprice = formatHallPrice(hallprice); }

    public String getFoodtype() { return foodtype; }
    public void setFoodtype(String foodtype) { this.foodtype = foodtype; }

    public boolean isAddon() { return addon; }
    public void setAddon(boolean addon) { this.addon = addon; }

    public int getPersons() { return persons; }
    public void setPersons(int persons) { this.persons = persons; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    @Override
    public String toString() {
        return "Wedingpackage{" +
                "id=" + id +
                ", packageName='" + packageName + '\'' +
                ", hallprice='" + hallprice + '\'' +
                ", foodtype='" + foodtype + '\'' +
                ", addon=" + addon +
                ", persons=" + persons +
                ", image='" + image + '\'' +
                '}';
    }
}