package com.mobileassembly.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Select {
    OFFICE_EQUIPMENT("Оргтехника"),
    WORKERS("Работники");
    private final String name;
}
