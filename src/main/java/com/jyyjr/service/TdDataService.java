package com.jyyjr.service;

import java.util.List;

import com.jyyjr.common.Message;
import com.jyyjr.pojo.TdData;

public interface TdDataService {

	Message<List<TdData>> getTdData(String vid);
}
