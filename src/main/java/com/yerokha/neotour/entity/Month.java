package com.yerokha.neotour.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "month")
@Data
public class Month {

    @Id
    @Column(name = "month_id")
    private Integer id;

    @Column(name = "month")
    private String month;

    public Month() {
    }

    public Month(String month) {
        this.month = month;
    }

    public static List<Month> valueOf(String month) {
        return List.of(new Month(month));
    }
}
