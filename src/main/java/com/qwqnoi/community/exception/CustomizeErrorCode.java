package com.qwqnoi.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUNT(2001,"该问题似乎消失了呢QwQ"),
    TARGET_PARAM_NOT_FOUNT(2002,"你好像还没有选中任何问题或评论呢"),
    NO_LOG_IN(2003, "你还没有登录哦"),
    SERVICE_ERROR(2004,"哎呀，服务器出错了orz"),
    OTHER_ERROR(2005,"你似乎来到了没有知识的荒原......"),
    TYPE_PARAM_WRONG(2006,"评论类型错误"),
    COMMENT_NOT_FOUNT(2001,"该评论似乎消失了呢QwQ");

    private Integer code;
    private String message;

    @Override
    public Integer getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
