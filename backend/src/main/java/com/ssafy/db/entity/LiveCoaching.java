package com.ssafy.db.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class LiveCoaching extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "live_coaching_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "coaching_id")
  private Coaching coaching;

  @ManyToOne
  @JoinColumn(name = "coame_coaching_id")
  private CoameCoaching coameCoaching;

  @Column(name = "coaching_date", nullable = false) //? null 가능한지
  private LocalDateTime coachingDate; //?BaseEntity 추가
}