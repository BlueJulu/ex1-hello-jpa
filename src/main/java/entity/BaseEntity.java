package entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // 매핑 정보만 갖는 부모 클래스
public abstract class BaseEntity {

    // @Column(name = "INSERT_MEMBER") // 테이블 명을 달리 할 수 있다
    private String createdBy;
    private LocalDateTime createdAt;
    @Column(name = "UPDATE_MEMBER") // 테이블 명을 달리 할 수 있다
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
