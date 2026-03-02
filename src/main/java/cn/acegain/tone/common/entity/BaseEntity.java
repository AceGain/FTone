package cn.acegain.tone.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(value = "create_by", comment = "创建人")
    private String createBy;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(value = "create_time", comment = "创建时间")
    private LocalDateTime createTime;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(value = "update_by", comment = "修改人")
    private String updateBy;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(value = "update_time", comment = "修改时间")
    private LocalDateTime updateTime;

}
