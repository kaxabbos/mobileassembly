package com.mobileassembly.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public enum Role implements GrantedAuthority {
    ADMIN("Управляющий"),
    TECHNICIAN("Техник"),
    TESTER("Тестировщик"),
    SALES_MANAGER("Менеджер"),
    USER("Диспетчер"),
    ;

    private final String name;

    @Override
    public String getAuthority() {
        return name();
    }
}
