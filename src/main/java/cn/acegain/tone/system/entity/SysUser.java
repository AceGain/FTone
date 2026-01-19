package cn.acegain.tone.system.entity;

import cn.acegain.tone.common.entity.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Table(value = "sys_user", comment = "用户表")
public class SysUser extends BaseEntity {

    @Id(comment = "主键")
    private Integer id;

    @Column(value = "avatar", comment = "头像")
    private String avatar;

    @Column(value = "name", comment = "名称")
    private String name;

    @Column(value = "status", comment = "状态")
    private String status;

    @Column(value = "account", comment = "账号")
    private String account;

    @Column(value = "password", comment = "密码")
    private String password;

    @Column(value = "phone", comment = "手机")
    private String phone;

    @Column(value = "email", comment = "邮箱")
    private String email;

    @Column(value = "remark", comment = "描述")
    private String remark;

    @Column(value = "is_default", comment = "系统默认")
    private Boolean isDefault;

    @Column(value = "is_delete", comment = "逻辑删除", isLogicDelete = true)
    private Boolean isDelete;

}
