package com.mobileassembly.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ALL("Все"),
    WAITING("В ожидание"),
    ASSEMBLING("Собирается"),
    TESTED("Тестируется"),
    COMPLETE("Готов"),
    ;
    private final String name;
}
