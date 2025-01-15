package com.example.final_module4.service;

import com.example.final_module4.modal.Discount;
import com.example.final_module4.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiscountService implements IDiscountService {
    @Autowired
    private DiscountRepository discountRepository;


    @Override
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    @Override
    public void save(Discount discount) {
        discountRepository.save(discount);
    }

    @Override
    public void update(int id, Discount discount) {
        if (discountRepository.existsById(id)) {
            discount.setId(id);
            discountRepository.save(discount);
        }
    }

    @Override
    public void remove(int id) {
        discountRepository.deleteById(id);
    }

    @Override
    public Discount findById(int id) {
        return discountRepository.findById(id).orElse(null);
    }


    @Override
    public Page<Discount> findByTitle(String title, Integer page) {
        return discountRepository.findAllByNameContainingIgnoreCase(title, PageRequest.of(page, 5));
    }

}
