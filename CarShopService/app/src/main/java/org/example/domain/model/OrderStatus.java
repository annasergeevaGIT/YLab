package org.example.domain.model;

import lombok.Getter;
import lombok.ToString;

/**
 * Enum representing the status of an order.
 */
@Getter
@ToString
public enum OrderStatus {
    PENDING, APPROVED, CANCELLED, COMPLETED
}
