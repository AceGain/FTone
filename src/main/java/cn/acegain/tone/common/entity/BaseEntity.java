package cn.acegain.tone.common.entity;

import com.mybatisflex.annotation.Column;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(value = "create_by", comment = "创建人")
    private String createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(value = "create_time", comment = "创建时间")
    private LocalDateTime createTime;

    @Column(value = "update_by", comment = "修改人")
    private String updateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(value = "update_time", comment = "修改时间")
    private LocalDateTime updateTime;

}
