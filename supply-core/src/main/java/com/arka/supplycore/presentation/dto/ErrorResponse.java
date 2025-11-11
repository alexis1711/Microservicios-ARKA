package com.arka.supplycore.presentation.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
  private String error;
  private String message;
  private LocalDateTime timestamp;
  private String path;
}
