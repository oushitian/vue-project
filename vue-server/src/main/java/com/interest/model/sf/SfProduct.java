package com.interest.model.sf;

import java.util.Date;

public class SfProduct {
    private Integer productId;

    private String number = "";

    private String name = "";

    private String englishName = "";    //默认为""

    private String purchaseName = "";

    private String systemName = "";

    private String barCode;

    private Integer categoryOne;

    private String categoryOneName;

    private String categoryOneCode;

    private Integer categoryTwo;

    private String categoryTwoName;

    private String categoryTwoCode;

    private Integer categoryThree;

    private String categoryThreeName;

    private String categoryThreeCode;

    private Integer parentId;

    private Integer parentNumber;

    private Integer brandId;

    private String brandName = "";

    private String brandEnglishName = "";

    private Integer price;

    private Integer shelfLife;

    private Date shelveDate;

    private Integer productArea;

    private Integer country;

    private String saleUnit = "";

    private String specification = "";

    private String weight = "";

    private String model = "";

    private Integer status;

    private Integer isShow;

    private String boxSpecification = "";

    private Date auditTime;

    private Integer priceType;

    private Integer productType;

    private Integer businessModel;

    private String isSell;

    private Integer merchantNumber;

    private Integer costPrice;

    private String sfshipping = "";

    private String sfairline = "";

    private Integer tax = 0;

    private Integer inTax = 0;

    private Integer outTax = 0;

    private String filename;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName == null ? null : purchaseName.trim();
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName == null ? null : systemName.trim();
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public Integer getCategoryOne() {
        return categoryOne;
    }

    public void setCategoryOne(Integer categoryOne) {
        this.categoryOne = categoryOne;
    }

    public String getCategoryOneName() {
        return categoryOneName;
    }

    public void setCategoryOneName(String categoryOneName) {
        this.categoryOneName = categoryOneName == null ? null : categoryOneName.trim();
    }

    public String getCategoryOneCode() {
        return categoryOneCode;
    }

    public void setCategoryOneCode(String categoryOneCode) {
        this.categoryOneCode = categoryOneCode == null ? null : categoryOneCode.trim();
    }

    public Integer getCategoryTwo() {
        return categoryTwo;
    }

    public void setCategoryTwo(Integer categoryTwo) {
        this.categoryTwo = categoryTwo;
    }

    public String getCategoryTwoName() {
        return categoryTwoName;
    }

    public void setCategoryTwoName(String categoryTwoName) {
        this.categoryTwoName = categoryTwoName == null ? null : categoryTwoName.trim();
    }

    public String getCategoryTwoCode() {
        return categoryTwoCode;
    }

    public void setCategoryTwoCode(String categoryTwoCode) {
        this.categoryTwoCode = categoryTwoCode == null ? null : categoryTwoCode.trim();
    }

    public Integer getCategoryThree() {
        return categoryThree;
    }

    public void setCategoryThree(Integer categoryThree) {
        this.categoryThree = categoryThree;
    }

    public String getCategoryThreeName() {
        return categoryThreeName;
    }

    public void setCategoryThreeName(String categoryThreeName) {
        this.categoryThreeName = categoryThreeName == null ? null : categoryThreeName.trim();
    }

    public String getCategoryThreeCode() {
        return categoryThreeCode;
    }

    public void setCategoryThreeCode(String categoryThreeCode) {
        this.categoryThreeCode = categoryThreeCode == null ? null : categoryThreeCode.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getParentNumber() {
        return parentNumber;
    }

    public void setParentNumber(Integer parentNumber) {
        this.parentNumber = parentNumber;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getBrandEnglishName() {
        return brandEnglishName;
    }

    public void setBrandEnglishName(String brandEnglishName) {
        this.brandEnglishName = brandEnglishName == null ? null : brandEnglishName.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(Integer shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Date getShelveDate() {
        return shelveDate;
    }

    public void setShelveDate(Date shelveDate) {
        this.shelveDate = shelveDate;
    }

    public Integer getProductArea() {
        return productArea;
    }

    public void setProductArea(Integer productArea) {
        this.productArea = productArea;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public String getSaleUnit() {
        return saleUnit;
    }

    public void setSaleUnit(String saleUnit) {
        this.saleUnit = saleUnit == null ? null : saleUnit.trim();
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getBoxSpecification() {
        return boxSpecification;
    }

    public void setBoxSpecification(String boxSpecification) {
        this.boxSpecification = boxSpecification == null ? null : boxSpecification.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getBusinessModel() {
        return businessModel;
    }

    public void setBusinessModel(Integer businessModel) {
        this.businessModel = businessModel;
    }

    public String getIsSell() {
        return isSell;
    }

    public void setIsSell(String isSell) {
        this.isSell = isSell == null ? null : isSell.trim();
    }

    public Integer getMerchantNumber() {
        return merchantNumber;
    }

    public void setMerchantNumber(Integer merchantNumber) {
        this.merchantNumber = merchantNumber;
    }

    public Integer getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
    }

    public String getSfshipping() {
        return sfshipping;
    }

    public void setSfshipping(String sfshipping) {
        this.sfshipping = sfshipping == null ? null : sfshipping.trim();
    }

    public String getSfairline() {
        return sfairline;
    }

    public void setSfairline(String sfairline) {
        this.sfairline = sfairline == null ? null : sfairline.trim();
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Integer getInTax() {
        return inTax;
    }

    public void setInTax(Integer inTax) {
        this.inTax = inTax;
    }

    public Integer getOutTax() {
        return outTax;
    }

    public void setOutTax(Integer outTax) {
        this.outTax = outTax;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }
}