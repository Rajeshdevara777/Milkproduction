package com.study.java.Repository;

import com.study.java.Entity.MilkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilkTypeRepository extends JpaRepository<MilkType,Integer> {
}
