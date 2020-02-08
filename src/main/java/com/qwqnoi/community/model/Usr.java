package com.qwqnoi.community.model;

public class Usr {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usr.id
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usr.account_id
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    private String accountId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usr.name
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usr.token
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    private String token;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usr.gmt_create
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usr.gmt_modified
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    private Long gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usr.bio
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    private String bio;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usr.avatar_url
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    private String avatarUrl;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usr.id
     *
     * @return the value of usr.id
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usr.id
     *
     * @param id the value for usr.id
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usr.account_id
     *
     * @return the value of usr.account_id
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usr.account_id
     *
     * @param accountId the value for usr.account_id
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usr.name
     *
     * @return the value of usr.name
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usr.name
     *
     * @param name the value for usr.name
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usr.token
     *
     * @return the value of usr.token
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usr.token
     *
     * @param token the value for usr.token
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usr.gmt_create
     *
     * @return the value of usr.gmt_create
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usr.gmt_create
     *
     * @param gmtCreate the value for usr.gmt_create
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usr.gmt_modified
     *
     * @return the value of usr.gmt_modified
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usr.gmt_modified
     *
     * @param gmtModified the value for usr.gmt_modified
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usr.bio
     *
     * @return the value of usr.bio
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public String getBio() {
        return bio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usr.bio
     *
     * @param bio the value for usr.bio
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public void setBio(String bio) {
        this.bio = bio == null ? null : bio.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usr.avatar_url
     *
     * @return the value of usr.avatar_url
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usr.avatar_url
     *
     * @param avatarUrl the value for usr.avatar_url
     *
     * @mbg.generated Sat Feb 08 15:26:08 CST 2020
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }
}