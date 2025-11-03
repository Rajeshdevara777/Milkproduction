package com.study.java.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MilkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "milk_id")
    private MilkType milk;

    private double quantity;
    private double amount;
    private String status;
    private LocalDateTime createdAt;

    public MilkOrder() {}

    // getters/setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Subscription getSubscription() { return subscription; }
    public void setSubscription(Subscription subscription) { this.subscription = subscription; }
    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }
    public MilkType getMilk() { return milk; }
    public void setMilk(MilkType milk) { this.milk = milk; }
    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
