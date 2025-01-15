package com.example.final_module4.controller;

import com.example.final_module4.modal.Discount;
import com.example.final_module4.service.IDiscountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/discounts")
public class DiscountController {
    @Autowired
    private IDiscountService discountService;
    @GetMapping("")
    public ModelAndView searchDiscounts(
            Model model,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Double discountPrice,
            @RequestParam(defaultValue = "0") int page) {

        LocalDate parsedStartDate = null;
        LocalDate parsedEndDate = null;

        // Kiểm tra và parse startDate
        if (startDate != null && !startDate.isBlank()) {
            try {
                parsedStartDate = LocalDate.parse(startDate);
            } catch (DateTimeParseException e) {
                model.addAttribute("error", "Ngày bắt đầu không hợp lệ");
                return new ModelAndView("list", "discounts", List.of());
            }
        }

        // Kiểm tra và parse endDate
        if (endDate != null && !endDate.isBlank()) {
            try {
                parsedEndDate = LocalDate.parse(endDate);
            } catch (DateTimeParseException e) {
                model.addAttribute("error", "Ngày kết thúc không hợp lệ");
                return new ModelAndView("list", "discounts", List.of());
            }
        }

        List<Discount> discounts = discountService.search(parsedStartDate, parsedEndDate, discountPrice, page);
        return new ModelAndView("list", "discounts", discounts);
    }

    @GetMapping("/create")
    public String viewAddBlog(Model model) {
        model.addAttribute("discount", new Discount());
        return "create";
    }
    @PostMapping("/create")
    public String createBlog(
            @Valid @ModelAttribute("discount") Discount discount,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("discount", discount);
            return "create";
        }
        discountService.save(discount);
        redirectAttributes.addFlashAttribute("message", "Khuyến mãi được tạo thành công!");
        return "redirect:/discounts";
    }


    @GetMapping("/{id}/edit")
    public String viewUpdate(@PathVariable int id, Model model) {
        Discount discount = discountService.findById(id);
        if (discount == null) {
            throw new RuntimeException("Không tìm thấy giảm giá với ID: " + id);
        }
        model.addAttribute("startDateFormatted", discount.getStartDate().toString());
        model.addAttribute("endDateFormatted", discount.getEndDate().toString());
        model.addAttribute("discount", discount);
        return "edit";
    }


    @PostMapping("/edit")
    public String updateBlog(
            @Valid @ModelAttribute("discount") Discount discount,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("discount", discount);
            return "edit";
        }

        if (discountService.findById(discount.getId()) == null) {
            redirectAttributes.addFlashAttribute("error", "Khuyến mãi không tồn tại!");
            return "redirect:/discounts";
        }

        discountService.update(discount.getId(), discount);
        redirectAttributes.addFlashAttribute("message", "Khuyến mãi đã được cập nhật!");
        return "redirect:/discounts";
    }

    @PostMapping("/{id}/delete")
    public String deleteDiscount(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Discount discount = discountService.findById(id);
        if (discount == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy khuyến mãi để xóa!");
            return "redirect:/discounts";
        }

        discountService.remove(id);
        redirectAttributes.addFlashAttribute("message", "Đã xóa thành công khuyến mãi: " + discount.getName());
        return "redirect:/discounts";
    }
}

