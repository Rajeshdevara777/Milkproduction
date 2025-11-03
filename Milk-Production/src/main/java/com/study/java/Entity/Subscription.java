package com.study.java.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Subscription {

    public enum Frequency { daily, weekly, monthly }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "milk_id")
    private MilkType milk;

    private double quantity;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    private LocalDate startDate;

    public Subscription() {}

    // getters/setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }
    public MilkType getMilk() { return milk; }
    public void setMilk(MilkType milk) { this.milk = milk; }
    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public Frequency getFrequency() { return frequency; }
    public void setFrequency(Frequency frequency) { this.frequency = frequency; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
}
