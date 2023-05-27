package com.mobileassembly.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceType {
    ALL("Все"),
    SENSORY("Сенсорные"),
    PUSH_BUTTON("Кнопочные"),
    FOLDING("Раскладные"),
    CURVED("Изогнутые"),
    ;
    private final String name;
}
