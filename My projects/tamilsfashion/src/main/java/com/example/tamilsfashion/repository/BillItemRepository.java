package com.example.tamilsfashion.repository;

import com.example.tamilsfashion.entity.Bill;
import com.example.tamilsfashion.entity.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillItemRepository extends JpaRepository<BillItem, Long> {

    List<BillItem> findByBill(Bill bill);

}