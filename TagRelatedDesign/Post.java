import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/*
================================================================================
 Part 2: Post 엔티티 (Post.java - 태그 관계 부분만 발췌)
--------------------------------------------------------------------------------
 - 기존 Post 엔티티에 Tag와의 관계를 설정하는 코드입니다.
 - 이 클래스가 관계의 주인(Owner) 역할을 합니다.
*/

@Entity
@Table(name = "posts")
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ... (storeName, body 등 다른 필드들) ...

    // Post와 Tag의 다대다(N:M) 관계를 정의합니다.
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "post_tags", // 자동으로 생성될 중간 테이블의 이름
        joinColumns = @JoinColumn(name = "post_id"), // 중간 테이블에서 Post를 참조하는 외래키
        inverseJoinColumns = @JoinColumn(name = "tag_id") // 중간 테이블에서 Tag를 참조하는 외래키
    )
    private Set<Tag> tags = new HashSet<>();

    // ... (다른 필드 및 메소드) ...

    //== 연관관계 편의 메소드 ==//
    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getPosts().add(this);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getPosts().remove(this);
    }
}

