package com.example.rockpaperscissorsultimate.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue
    private UUID id;
    
    private String title;
    private int roleImportance; //1 - 10 , 1 - самая низкая важность / 10 - президент башкирии
}
