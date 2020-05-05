package io.boc.v2.util;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import io.boc.v2.model.Result;
import io.boc.v2.model.annotation.ApiOperation;
import io.boc.v2.model.dto.*;
import io.boc.v2.service.*;
public class BocSDKTool {
    private static AccountService accountService = AccountService.getInstance();
   
    private static TransactionService transactionService = TransactionService.getInstance();
    
    private static ContractService contractService = ContractService.getInstance();
	
	public static Result sign(List<SignDto> signDtoList, String txHex) {
        return accountService.sign(signDtoList, txHex);
    }
	
	public static Result sign(String txHex, String address, String privateKey) {
        return transactionService.signTx(txHex, address, privateKey);
    }
	
	public static Result createTransferTxOffline(TransferDto transferDto) {
        return transactionService.createTransferTx(transferDto);
    }
	
	public static Result<List<AccountDto>> createOffLineAccount(int count, String password) {
        return accountService.createOffLineAccount(count, null, password);
    }
	
	public static BigInteger calcTransferTxFee(TransferTxFeeDto dto) {
        return transactionService.calcTransferTxFee(dto);
    }
	
	@ApiOperation(description = "离线 - 批量创建地址带固定前缀的账户", order = 151, detailDesc = "创建的账户不会保存到钱包中,接口直接返回账户的keystore信息")
    public static Result<List<AccountDto>> createOffLineAccount(int count, String prefix, String password) {
        return accountService.createOffLineAccount(count, prefix, password);
    }
	
	@ApiOperation(description = "离线获取账户明文私钥", order = 153)
	public static Result getPriKeyOffline(String address, String encryptedPriKey, String password) {
        return accountService.getPriKeyOffline(address, encryptedPriKey, password);
    }
	
	@ApiOperation(description = "离线组装 - token转账交易", order = 454)
	public static Result<Map> tokenTransferTxOffline(String fromAddress, BigInteger senderBalance, String nonce, String toAddress, String contractAddress, long gasLimit, BigInteger amount, String remark) {
        return contractService.tokenTransferTxOffline(fromAddress, senderBalance, nonce, toAddress, contractAddress, gasLimit, amount, remark);
    }
	
}