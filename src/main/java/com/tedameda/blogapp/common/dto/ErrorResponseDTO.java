package com.tedameda.blogapp.common.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author TedaMeda
 * @since 12/30/2023
 */
@Data
@Builder
public class ErrorResponseDTO {
    private String message;
}
