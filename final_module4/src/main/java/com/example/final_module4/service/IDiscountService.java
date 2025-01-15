package com.example.final_module4.service;

import com.example.final_module4.modal.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

public interface IDiscountService {
    List<Discount> findAll();
    void save(Discount discount);
    void update(int id,  Discount discount);
    void remove(int id);
    Discount findById(int id);
    Page<Discount> findByTitle(String title, Integer page);
}
