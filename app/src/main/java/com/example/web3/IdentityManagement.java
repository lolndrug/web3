package com.example.web3;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class IdentityManagement {
    private static final String CONTRACT_ADDRESS = "0xc2B11E5CBf5D733E34D790CF5149e469f5a5eF54";



    public IdentityManagement(String contractAddress, Web3j web3j, RawTransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
    }

    public static IdentityManagement load(String yourContractAddress, Web3j web3j, Credentials credentials, DefaultGasProvider defaultGasProvider) {
        return new IdentityManagement(
                CONTRACT_ADDRESS,
                web3j,
                new RawTransactionManager(web3j, credentials),
                DefaultGasProvider.GAS_PRICE,
                DefaultGasProvider.GAS_LIMIT
        );
    }

    public TransactionReceipt uploadIdentity(String userAddress,String name, String surname, String motherName, String fatherName, String id, String dof, String gender, String password) throws Exception {
        Function function = new Function(
                "uploadIdentity",
                Arrays.asList(
                        new org.web3j.abi.datatypes.Address(userAddress),
                        new Utf8String(name),
                        new Utf8String(surname),
                        new Utf8String(motherName),
                        new Utf8String(fatherName),
                        new Utf8String(id),
                        new Utf8String(dof),
                        new Utf8String(gender),
                        new Utf8String(password)
                ),
                Collections.emptyList()
        );

        return executeTransaction(function);
    }

    public TransactionReceipt shareIdentity(BigInteger id, String recipient) throws Exception {
        Function function = new Function(
                "shareIdentity",
                Arrays.asList(
                        new Uint256(id),
                        new org.web3j.abi.datatypes.Address(recipient)
                ),
                Collections.emptyList()
        );

        return executeTransaction(function);
    }


    private TransactionReceipt executeTransaction(Function function) throws Exception {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.TransactionReceipt transactionReceipt =
                executeRemoteCallTransaction(encodedFunction);
        return new TransactionReceipt();
    }

    private org.web3j.protocol.core.methods.response.TransactionReceipt executeRemoteCallTransaction(String data) throws Exception {
        RemoteCall<org.web3j.protocol.core.methods.response.TransactionReceipt> remoteCall =
                new RemoteCall<>(() -> executeRemoteCallTransaction(data));
        return remoteCall.send();
    }

    public boolean login(String id, String password) throws Exception {
        Function function = new Function(
                "login",
                Arrays.asList(
                        new Utf8String(id),
                        new Utf8String(password)
                ),
                Collections.emptyList()
        );

        return true; // I will fix this if I have time
    }


}
