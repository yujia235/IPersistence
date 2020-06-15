package com.yujia.service;

public interface ITransferService {
    void transfer(String fromCardNo,String toCardNo,int money) throws Exception;
}
