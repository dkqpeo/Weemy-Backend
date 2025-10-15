package wemmy.domain.welfare;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wemmy.domain.area.Regions;
import wemmy.domain.user.UserEntityV2;

@Entity (name = "welfare")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Welfare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "welfare_id")
    private Long id;

    @Column(name = "unique_id", unique = true)
    private Long uniqueId;

    private String title;       // 복지(혜택)제목

    @Column(columnDefinition = "TEXT")
    private String field;       // 지원대상

    @Column(columnDefinition = "TEXT")
    private String content;     //내용

    @Column(columnDefinition = "TEXT")
    private String way;         // 신청방법

/*    @Column(columnDefinition = "TEXT")
    private String inquiry;     // 문의처*/

    @Column(columnDefinition = "TEXT")
    private String etc;         // 문의처

    @Column(name = "original_url", length = 2083)
    private String originalUrl; // 원본 url

    @Column(name = "image_url", length = 2083)
    private String imageUrl;    // 이미지 url

    private Long view;          // 조회수

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "user_id")
    UserEntityV2 adminId;         // 관리자 id

    @ManyToOne
    @JoinColumn(name = "w_category_id", referencedColumnName = "w_category_id")
    Wcategory wCategoryId;      // 복지 카테고리

    @ManyToOne
    @JoinColumn(name = "host_id", referencedColumnName = "region_id")
    Regions hostId;             // 주최 카테고리
}
