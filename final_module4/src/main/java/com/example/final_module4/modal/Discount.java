package com.example.final_module4.modal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;

    @NotBlank(message = "Tên giảm giá không được để trống")
    @Size(max = 100, message = "Tên giảm giá không được vượt quá 100 ký tự")
    @Column(name = "name_discount", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull(message = "Mức giảm giá không được để trống")
    @Min(value = 10000, message = "Mức giảm giá phải lớn hơn 10.000 VND")
    @Column(name = "discount_price", nullable = false)
    private Double discountPrice;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
