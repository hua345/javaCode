package com.github.chenjianhua.springbootelasticsearch.model;

import lombok.Data;

/**
 * @author chenjianhua
 * @date 2020/10/9
 */
@Data
public class CompanyInfo {
    private String companyName;
    /**
     * 公司电话
     */
    private String companyPhone;
    /**
     * 公司邮箱
     */
    private String companyEmail;
    /**
     * 官网
     */
    private String officialWebsite;
    /**
     * 公司地址
     */
    private String companyAddress;
    /**
     * 公司简介
     */
    private String companyProfile;
    /**
     * 工商注册
     */
    private BusinessRegistration businessRegistration;
}
