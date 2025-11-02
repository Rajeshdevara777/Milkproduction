package com.study.java.Service;

import com.study.java.Entity.AppUser;
import com.study.java.Entity.MilkOrder;
import com.study.java.Entity.MilkType;
import com.study.java.Entity.Subscription;
import com.study.java.Repository.AppUserRepository;
import com.study.java.Repository.MilkOrderRepository;
import com.study.java.Repository.MilkTypeRepository;
import com.study.java.Repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class OrderService {

    private final AppUserRepository appUserRepo;
    private final MilkTypeRepository milkTypeRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final MilkOrderRepository milkOrderRepository;

    public OrderService(AppUserRepository appUserRepo,
                        MilkTypeRepository milkTypeRepository,
                        SubscriptionRepository subscriptionRepository,
                        MilkOrderRepository milkOrderRepository) {
        this.appUserRepo = appUserRepo;
        this.milkTypeRepository = milkTypeRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.milkOrderRepository = milkOrderRepository;
    }

    /**
     * Creates (or finds) the user, creates a subscription, and places the first order.
     */
    @Transactional
    public <SubscribeRequest> MilkOrder subscribeAndCreateOrder(SubscribeRequest req) {
        // 1) Get or create user by phone
        AppUser user = appUserRepo.findByPhone(req.phone())
                .orElseGet(() -> {
                    AppUser u = new AppUser();
                    u.setName(req.name());
                    u.setPhone(req.phone());
                    u.setAddress(req.address());
                    return appUserRepo.save(u);
                });

        // 2) Load milk type
        MilkType milk = milkTypeRepository.findById(req.milkId())
                .orElseThrow(() -> new IllegalArgumentException("Milk not found: " + req.milkId()));

        // 3) Create subscription
        Subscription sub = new Subscription();
        sub.setUser(user);
        sub.setMilk(milk);
        sub.setQuantity(req.quantity());
        // frequency must be one of: daily, weekly, monthly (lowercase per your enum)
        Subscription.Frequency freq = Subscription.Frequency.valueOf(req.frequency().toLowerCase());
        sub.setFrequency(freq);
        sub.setStartDate(LocalDate.now());
        sub = subscriptionRepository.save(sub);

        // 4) Create first order (Pending)
        double amount = req.quantity() * milk.getPricePerLiter();

        MilkOrder order = new MilkOrder();
        order.setSubscription(sub);
        order.setUser(user);
        order.setMilk(milk);
        order.setQuantity(req.quantity());
        order.setAmount(amount);
        order.setStatus("Pending");
        order.setCreatedAt(LocalDateTime.now());
        return milkOrderRepository.save(order);
    }
}
