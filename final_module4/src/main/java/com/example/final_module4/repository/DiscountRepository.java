package com.example.final_module4.repository;

import com.example.final_module4.modal.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    Page<Discount> findAllByNameContainingIgnoreCase(String title, Pageable page);
    Page<Discount> findByDiscountPrice(Double discountPrice, Pageable pageable);
    Page<Discount> findByStartDateAndEndDate(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<Discount> findByStartDateAndEndDateAndDiscountPrice(
            LocalDate startDate, LocalDate endDate, Double discountPrice, Pageable pageable);
}

