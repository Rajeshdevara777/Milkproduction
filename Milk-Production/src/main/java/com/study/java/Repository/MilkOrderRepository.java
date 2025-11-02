package com.study.java.Repository;

import com.study.java.Entity.MilkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilkOrderRepository extends JpaRepository<MilkOrder,Integer> {
}
