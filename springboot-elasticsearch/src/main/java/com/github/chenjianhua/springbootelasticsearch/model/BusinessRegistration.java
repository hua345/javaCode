package com.github.chenjianhua.springbootelasticsearch.model;

import lombok.Data;

/**
 * @author chenjianhua
 * @date 2020/10/9
 */
@Data
public class BusinessRegistration {
    /**
     * 法定代表人
     */
    private String legalRepresentative;
    /**
     * 经营状态
     */
    private String operatingStatus;
    /**
     * 注册资本
     */
    private String registeredCapital;
    /**
     * 实缴资本
     */
    private String paidInCapital;
    /**
     * 所属行业
     */
    private String industry;
    /**
     * 统一社会信用代码
     */
    private String socialCreditCode;
    /**
     * 纳税人识别号
     */
    private String taxpayerIdentificationNumber;
    /**
     * 工商注册号
     */
    private String businessRegistrationNumber;
    /**
     * 组织机构代码
     */
    private String organizationCode;
    /**
     * 登记机关
     */
    private String registrationAuthority;
    /**
     * 成立日期
     */
    private String establishmentDate;
    /**
     * 企业类型
     */
    private String enterpriseType;
    /**
     * 营业期限
     */
    private String operatingPeriod;
    /**
     * 行政区划
     */
    private String administrativeDivisions;
    /**
     * 审核/年检日期
     */
    private String annualInspectionDate;
    /**
     * 注册地址
     */
    private String registeredAddress;
    /**
     * 经营范围
     */
    private String businessScope;
}
