package cn.acegain.tone.common;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Status status;

    private Object code;

    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dataTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public Result(Status status, Object code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.dataTime = DateTime.now();
        this.data = data;
    }

    public Result(Status status, Object code, String message) {
        this(status, code, message, null);
    }

    public static <T> Result<T> success() {
        return new Result<>(Status.Success, 200, "请求成功");
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(Status.Success, 200, "请求成功", data);
    }

    public static <T> Result<T> success(Object code, String message) {
        return new Result<>(Status.Success, code, message);
    }

    public static <T> Result<T> success(Object code, String message, T data) {
        return new Result<>(Status.Success, code, message, data);
    }

    public static <T> Result<T> failure() {
        return new Result<>(Status.Failure, 400, "请求失败");
    }

    public static <T> Result<T> failure(Object code, String message) {
        return new Result<>(Status.Failure, code, message);
    }

    public static <T> Result<T> error() {
        return new Result<>(Status.Error, 500, "请求异常");
    }

    public static <T> Result<T> error(Object code, String message) {
        return new Result<>(Status.Error, code, message);
    }

    public enum Status {

        Success,

        Failure,

        Error

    }

}
