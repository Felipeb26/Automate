package com.bats.init.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UiEntity {

    private int id;

    private String background;
    private String textColor;
    private String borderColor;
}
