package com.qwqnoi.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUNT("该问题似乎消失了呢......"),
    SERVICE_ERROR("哎呀，服务器出错了......"),
    OTHER_ERROR("你似乎来到了没有知识的荒原......");

    private String message;
    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
