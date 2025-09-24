package com.unnkk.elsechat.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRequestDTO(@NotBlank @Size(max = 500) String content) {}
