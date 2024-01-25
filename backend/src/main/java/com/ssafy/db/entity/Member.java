package com.ssafy.db.entity;

import com.ssafy.db.entity.type.Privilege;
import com.ssafy.db.entity.status.MemberStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member extends BaseEntity implements UserDetails  {

  @Id @Column(name = "member_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long longId;

  @Column(nullable = false)
  private String stringId;

  @Column(nullable = false)
  private String password;  // 암호화 저장

  @Enumerated(EnumType.STRING)
  private Privilege privilege = Privilege.COAME;  // 생성 시 코미

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String nickName;

  @Column(nullable = false)
  private String email;

  @OneToOne
  @JoinColumn(name = "profile_image_id")
  private File profileImage;

  @Column
  private String profileText;

  @Column
  private boolean isElevated;

  @Enumerated(EnumType.STRING)
  private MemberStatus status = MemberStatus.CREATED; // 생성 상태

  @OneToOne
  @JoinColumn(name = "portfolio_id")
  private Portfolio portfolio;

  // 코치가 개설한 강의목록
  @OneToMany(mappedBy = "coach")
  private List<Coaching> coachTeachCourses = new ArrayList<>();

  // 코미가 수강하는 목록
  @OneToMany(mappedBy = "coame")
  private List<Coaching> coameTaughtCourses = new ArrayList<>();

  // 코미가 누른 좋아요
  @OneToMany(mappedBy = "coame")
  private List<Likes> sendLikes = new ArrayList<>();

  // 코치가 코미에게 받은 좋아요
  @OneToMany(mappedBy = "coach")
  private List<Likes> receivedLikes = new ArrayList<>();

  // 코미가 남긴 리뷰
  @OneToMany(mappedBy = "coame")
  private List<Review> sendReviews = new ArrayList<>();

  // 코치가 받은 리뷰
  @OneToMany(mappedBy = "coach")
  private List<Review> receivedReviews = new ArrayList<>();

  // method

  // 연관관계 편의 메서드

  // 회원정보 수정 시 이름과 이메일을 변경하고 상태를 변경으로 바꾼다.
  public void changeMemberStatus(String nickName, String email) {
    this.status = MemberStatus.MODIFIED;
    this.nickName = nickName;
    this.email = email;
  }

  // 스프링 시큐리티 설정파일
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }
  @Override
  public String getUsername() {
    return stringId;
  }
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  @Override
  public boolean isEnabled() {
    return true;
  }
}
