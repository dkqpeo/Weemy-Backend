package wemmy.domain.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = { AuditingEntityListener.class })
@MappedSuperclass
public abstract class BaseTimeEntity {

    @CreatedDate // Entity가 만들어질 때 자동으로 기록
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    @Column(updatable = false)
    private LocalDateTime createTime;

    //@LastModifiedDate // Entity가 업데이트 될 때 자동으로 기록
    //private LocalDateTime updateTime;
}
