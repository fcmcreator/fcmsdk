package io.boc.v2.util;
import java.math.BigInteger;
import java.util.List;

import io.boc.v2.SDKContext;
import io.boc.v2.crypto.P2PHKSignature;
import io.boc.v2.error.AccountErrorCode;
import io.boc.v2.exception.NulsException;
import io.boc.v2.model.CoinFrom;
import io.boc.v2.model.CoinTo;

public class TxUtils {

    public static boolean isMainAsset(int chainId, int assetId) {
        return chainId == SDKContext.main_chain_id && assetId == SDKContext.main_asset_id;
    }

    public static boolean isNulsAsset(int chainId, int assetId) {
        return chainId == SDKContext.nuls_chain_id && assetId == SDKContext.nuls_asset_id;
    }

    public static void calcTxFee(List<CoinFrom> coinFroms, List<CoinTo> coinTos, int txSize) throws NulsException {
        BigInteger totalFrom = BigInteger.ZERO;
        for (CoinFrom coinFrom : coinFroms) {
            txSize += coinFrom.size();
            if (TxUtils.isMainAsset(coinFrom.getAssetsChainId(), coinFrom.getAssetsId())) {
                totalFrom = totalFrom.add(coinFrom.getAmount());
            }
        }
        BigInteger totalTo = BigInteger.ZERO;
        for (CoinTo coinTo : coinTos) {
            txSize += coinTo.size();
            if (TxUtils.isMainAsset(coinTo.getAssetsChainId(), coinTo.getAssetsId())) {
                totalTo = totalTo.add(coinTo.getAmount());
            }
        }
        //本交易预计收取的手续费
        BigInteger targetFee = TransactionFeeCalculator.getNormalTxFee(txSize);
        //实际收取的手续费, 可能自己已经组装完成
        BigInteger actualFee = totalFrom.subtract(totalTo);
        if (BigIntegerUtils.isLessThan(actualFee, BigInteger.ZERO)) {
            throw new NulsException(AccountErrorCode.INSUFFICIENT_FEE);
        } else if (BigIntegerUtils.isLessThan(actualFee, targetFee)) {
            throw new NulsException(AccountErrorCode.INSUFFICIENT_FEE);
        }
    }


    public static BigInteger calcTransferTxFee(int addressCount, int fromLength, int toLength, String remark, BigInteger price) {
        int size = 10;
        size += addressCount * P2PHKSignature.SERIALIZE_LENGTH;
        size += 70 * fromLength;
        size += 68 * toLength;
        if (StringUtils.isNotBlank(remark)) {
            size += StringUtils.bytes(remark).length;
        }
        size = size / 1024 + 1;
        return price.multiply(new BigInteger(size + ""));
    }

    public static BigInteger calcStopConsensusTxFee(int fromLength, int toLength, BigInteger price) {
        int size = 152;
        size += 70 * fromLength;
        size += 68 * toLength;
        size = size / 1024 + 1;
        return price.multiply(new BigInteger(size + ""));
    }

    /**
     * 根据交易HASH获取NONCE（交易HASH后8位）
     * Obtain NONCE according to HASH (the last 8 digits of HASH)
     */
    public static byte[] getNonce(byte[] txHash) {
        byte[] targetArr = new byte[8];
        System.arraycopy(txHash, txHash.length - 8, targetArr, 0, 8);
        return targetArr;
    }
}
