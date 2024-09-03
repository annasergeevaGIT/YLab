package org.example.domain.model;

import lombok.Getter;
import lombok.ToString;

/**
 * Enumeration representing different roles for users.
 */
@Getter
@ToString
public enum UserRole {
    ADMIN, MANAGER, CUSTOMER
}
