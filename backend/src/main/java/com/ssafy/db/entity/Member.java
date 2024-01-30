package com.ssafy.db.entity;

import com.ssafy.db.entity.status.ElevateStatus;
import com.ssafy.db.entity.type.Privilege;
import com.ssafy.db.entity.status.MemberStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "member_id")
  private Long longId;

  @Column(nullable = false, unique = true, length = 20)
  private String stringId;

  @Column(nullable = false, unique = true, length = 100)
  private String password;  // 암호화 저장

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 10)
  private String nickName;

  @Column(nullable = false, length = 50)
  private String email;

  @Column(length = 100)
  private String profileText;

  @OneToOne
  @JoinColumn(name = "profile_image_id")
  private File profileImage;

  @Enumerated(EnumType.STRING)
  private ElevateStatus elevateStatus;

  @Enumerated(EnumType.STRING)
  @ColumnDefault(value = "'CREATED'")
  @Column(nullable = false)
  private MemberStatus status = MemberStatus.CREATED; // 생성 상태

  @Enumerated(EnumType.STRING)
  @ColumnDefault(value = "'COAME'")
  @Column(nullable = false)
  private Privilege privilege = Privilege.COAME;  // 생성 시 코미


  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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
  // 회원정보 생성 시 권한을 설정하고 상태를 생성으로 바꾼다.
  public void initMemberPrivilegeAndStatus() {
      this.status = MemberStatus.CREATED;
      this.privilege = Privilege.COAME;
  }

  // 회원정보 수정 시 이름과 이메일을 변경하고 상태를 변경으로 바꾼다.
  public void changeMemberStatus(String nickName, String email) {
    this.status = MemberStatus.MODIFIED;
    this.nickName = nickName;
    this.email = email;
  }

  // 코치 등록 시 권한상승 리스트에 추가한다.
  public void elevateRequest(String newHtmlDocs) {
    this.elevateStatus = ElevateStatus.REQUEST;
    this.portfolio = new Portfolio(newHtmlDocs);
  }

  // 권한 상승 요청시, 권한을 1에서 2로 수정한다.
  public void elevatePermissionRequest() {
    this.privilege = Privilege.COACH;
    this.elevateStatus = ElevateStatus.SUCCEED;
    this.status = MemberStatus.MODIFIED;
  }

  // 연관관계 편의 메서드

}