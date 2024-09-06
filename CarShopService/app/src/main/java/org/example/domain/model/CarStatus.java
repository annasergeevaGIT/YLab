package org.example.domain.model;

import lombok.Getter;
import lombok.ToString;

/**
 * Enumeration representing different statuses for a car.
 */
@Getter
@ToString
public enum CarStatus {
    AVAILABLE, RESERVED, SOLD
}
