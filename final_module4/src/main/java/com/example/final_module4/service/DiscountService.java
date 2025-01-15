package com.example.final_module4.service;

import com.example.final_module4.modal.Discount;
import com.example.final_module4.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<Discount> search(LocalDate startDate, LocalDate endDate, Double discountPrice, int page) {

        if (startDate == null && endDate == null && discountPrice == null) {
            return discountRepository.findAll(PageRequest.of(page, 10)).getContent();
        }

        // Nếu chỉ có discountPrice
        if (discountPrice != null && startDate == null && endDate == null) {
            return discountRepository.findByDiscountPrice(discountPrice, PageRequest.of(page, 10)).getContent();
        }

        // Nếu chỉ có startDate và endDate
        if (discountPrice == null && startDate != null && endDate != null) {
            return discountRepository.findByStartDateAndEndDate(startDate, endDate, PageRequest.of(page, 10)).getContent();
        }

        // Nếu có cả discountPrice, startDate, và endDate
        if (discountPrice != null && startDate != null && endDate != null) {
            return discountRepository.findByStartDateAndEndDateAndDiscountPrice(
                    startDate, endDate, discountPrice, PageRequest.of(page, 10)).getContent();
        }

        return new ArrayList<>();
    }
}
