/*
 * MIT License
 *
 * Copyright (c) 2017-2018 nuls.io
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.boc.v2.util;
import org.apache.commons.lang.StringUtils;

import io.boc.v2.base.Address;
import io.boc.v2.constant.BaseConstant;
import io.boc.v2.crypto.ECKey;
import io.boc.v2.crypto.HexUtil;
import io.boc.v2.error.AccountErrorCode;
import io.boc.v2.exception.NulsException;
import io.boc.v2.model.Account;
import io.boc.v2.parse.SerializeUtils;

import java.math.BigInteger;

/**
 * @author: qinyifeng
 */
public class AccountTool {
	public static Account createAccount(int chainId, String prikey) throws NulsException {
        ECKey key = null;
        if (StringUtils.isBlank(prikey)) {
            key = new ECKey();
        } else {
            try {
                key = ECKey.fromPrivate(new BigInteger(1, HexUtil.decode(prikey)));
            } catch (Exception e) {
                throw new RuntimeException("AccountErrorCode.PRIVATE_KEY_WRONG");
            }
        }
        Address address = new Address(chainId, BaseConstant.DEFAULT_ADDRESS_TYPE, SerializeUtils.sha256hash160(key.getPubKey()));
        Account account = new Account();
        account.setChainId(chainId);
        account.setAddress(address);
        account.setPubKey(key.getPubKey());
        account.setPriKey(key.getPrivKeyBytes());
        account.setEncryptedPriKey(new byte[0]);
        account.setCreateTime(System.currentTimeMillis());
        account.setEcKey(key);
        return account;
    }
	
	public static Account createAccount(int chainId, String prikey, String prefix) throws NulsException {
        ECKey key = null;
        if (StringUtils.isBlank(prikey)) {
            key = new ECKey();
        } else {
            try {
                key = ECKey.fromPrivate(new BigInteger(1, HexUtil.decode(prikey)));
            } catch (Exception e) {
                throw new NulsException(AccountErrorCode.PRIVATE_KEY_WRONG, e);
            }
        }
        Address address = new Address(chainId, prefix, BaseConstant.DEFAULT_ADDRESS_TYPE, SerializeUtils.sha256hash160(key.getPubKey()));
        Account account = new Account();
        account.setChainId(chainId);
        account.setAddress(address);
        account.setPubKey(key.getPubKey());
        account.setPriKey(key.getPrivKeyBytes());
        account.setEncryptedPriKey(new byte[0]);
        account.setCreateTime(System.currentTimeMillis());
        account.setEcKey(key);
        return account;
    }
	
	
	public static Account createAccount(int chainId) throws NulsException {
        return createAccount(chainId, null);
    }
	
	public static String getPrefix(String address) {
        for (int i = 1; i < address.length(); i++) {
            char c = address.charAt(i);
            if (ValidateUtil.regexMatch(c + "", "^[a-z]{1}$")) {
                return address.substring(0, i);
            }
        }
        return null;
    }
}
