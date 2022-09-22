package me.approximations.spawners.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Amigo {
    private String nome;
    private boolean canBreak;
    private boolean canPlace;
    private boolean canKill;
}
