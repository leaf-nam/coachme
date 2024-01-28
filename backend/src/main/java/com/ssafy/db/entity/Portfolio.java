package com.ssafy.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Portfolio extends BaseEntity {
  @Id @GeneratedValue
  @Column(name = "portfolio_id")
  private Long id;

  @Column(nullable = false, length = 5000)
  private String htmlDocs;
}