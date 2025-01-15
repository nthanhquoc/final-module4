package com.example.final_module4.repository;

import com.example.final_module4.modal.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    Page<Discount> findAllByNameContainingIgnoreCase(String title, Pageable page);
}
