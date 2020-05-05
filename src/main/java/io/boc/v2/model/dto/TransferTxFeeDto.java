package io.boc.v2.model.dto;

import java.math.BigInteger;

import io.boc.v2.model.*;

@ApiModel
public class TransferTxFeeDto {
    @ApiModelProperty(description = "转账地址数量")
    private int addressCount;
    @ApiModelProperty(description = "转账输入长度")
    private int fromLength;
    @ApiModelProperty(description = "转账输出长度")
    private int toLength;
    @ApiModelProperty(description = "交易备注")
    private String remark;
    @ApiModelProperty(description = "手续费单价" ,required = false)
    private BigInteger price;

    public int getAddressCount() {
        return addressCount;
    }

    public void setAddressCount(int addressCount) {
        this.addressCount = addressCount;
    }

    public int getFromLength() {
        return fromLength;
    }

    public void setFromLength(int fromLength) {
        this.fromLength = fromLength;
    }

    public int getToLength() {
        return toLength;
    }

    public void setToLength(int toLength) {
        this.toLength = toLength;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }
}
