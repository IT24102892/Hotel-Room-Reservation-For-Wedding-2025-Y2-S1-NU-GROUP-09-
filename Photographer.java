package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "photographer") // matches your table name (lowercase)
public class Photographer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private Integer experience; // years

    @Column(columnDefinition = "TEXT")
    private String image; // image URL

    @Column(nullable = false, length = 100)
    private String location;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 30)
    private String phone;

    // Use BigDecimal for currency; change to Integer if you prefer whole numbers
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    // ---- constructors ----
    public Photographer() {}

    public Photographer(String email,
                        Integer experience,
                        String image,
                        String location,
                        String name,
                        String phone,
                        BigDecimal price) {
        this.email = email;
        this.experience = experience;
        this.image = image;
        this.location = location;
        this.name = name;
        this.phone = phone;
        this.price = price;
    }

    // ---- getters/setters ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getExperience() { return experience; }
    public void setExperience(Integer experience) { this.experience = experience; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
