package com.study.java.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SubscribeRequest {
    public record SubscribeRequest(
            @NotBlank
            String name,
            @NotBlank
            String phone,
            @NotBlank
            String address,
            @NotNull
            Integer milkId,
            @Positive
            double quantity,
            // must be: "daily" | "weekly" | "monthly"
            @NotBlank
            String frequency
    ) {}
}
