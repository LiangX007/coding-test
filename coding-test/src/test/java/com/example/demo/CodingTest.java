package com.example.demo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.service.impl.LoadingFunctionImpl;

@SpringBootTest
public class CodingTest {

	@Autowired
	private LoadingFunctionImpl loadingFunction;
	private static final Logger log = LoggerFactory.getLogger(CodingTest.class);

	/**
	 * test case one: put key and value into cache and get value by a exist key
	 */
	@Test
	public void putAndgetTest() {
		String key = "test:key";
		String value = "test:value";
		// put k v into cache
		loadingFunction.put(key, value);
		// key value by key from cache
		String getKey = loadingFunction.get(key);

		log.info("in cache : {} = {}", key, getKey);
	}

	/**
	 * test case two : get a not exist key from cache and then need to apply that key from (DB) 
	 * expectation result : 100 times(thread) request a not exist key
	 * and try to get value from cache ; just one time(thread) call apply method
	 * 
	 */
	@Test
	public void getNotExistKey() {
		String key = "notExist";
		String getKey = "";
		for (int i = 0; i < 100; i++) {
			getKey = loadingFunction.get(key);
		}
		log.info("-----------------------");
		log.info("in cache : {} = {}", key, getKey);
	}

}
