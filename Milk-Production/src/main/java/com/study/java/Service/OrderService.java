package com.study.java.Service;

import com.study.java.DTO.SubscribeRequest;
import com.study.java.Entity.AppUser;
import com.study.java.Entity.MilkOrder;
import com.study.java.Entity.MilkType;
import com.study.java.Entity.Subscription;
import com.study.java.Repository.AppUserRepository;
import com.study.java.Repository.MilkOrderRepository;
import com.study.java.Repository.MilkTypeRepository;
import com.study.java.Repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class OrderService {

    @Autowired
    private  AppUserRepository appUserRepo;
    @Autowired
    private  MilkTypeRepository milkTypeRepository;
    @Autowired
    private  SubscriptionRepository subscriptionRepository;
    @Autowired
    private  MilkOrderRepository milkOrderRepository;



    /**
     * Creates (or finds) the user, creates a subscription, and places the first order.
     */
    @Transactional
    public MilkOrder subscribeAndCreateOrder(SubscribeRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "request body is required");
        }

        if (req.getPhone() == null || req.getPhone().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "phone is required");
        }

        if (req.getMilkId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "milkId is required");
        }

        if (req.getQuantity() == null || req.getQuantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity must be positive");
        }

        if (req.getFrequency() == null || req.getFrequency().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "frequency is required");
        }

        Subscription.Frequency freq;
        try {
            freq = Subscription.Frequency.valueOf(req.getFrequency().trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid frequency: must be one of " + Arrays.toString(Subscription.Frequency.values())
            );
        }

        AppUser user = appUserRepo.findByPhone(req.getPhone())
                .orElseGet(() -> {
                    AppUser u = new AppUser();
                    u.setName(req.getName());
                    u.setPhone(req.getPhone());
                    u.setAddress(req.getAddress());
                    return appUserRepo.save(u);
                });

        MilkType milk = milkTypeRepository.findById(req.getMilkId())
                .orElseThrow(() -> new IllegalArgumentException("Milk not found: " + req.getMilkId()));

        Subscription sub = new Subscription();
        sub.setUser(user);
        sub.setMilk(milk);
        sub.setQuantity(req.getQuantity());
        sub.setFrequency(Subscription.Frequency.valueOf(req.getFrequency().toUpperCase()));
        sub.setStartDate(LocalDate.now());
        subscriptionRepository.save(sub);

        double amount = req.getQuantity() * milk.getPricePerLiter()* milk.getFatPercentage();

        MilkOrder order = new MilkOrder();
        order.setUser(user);
        order.setMilk(milk);
        order.setQuantity(req.getQuantity());
        order.setAmount(amount);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(String.valueOf(Subscription.Frequency.valueOf(req.getFrequency().toUpperCase())));

        return milkOrderRepository.save(order);


    }
}
