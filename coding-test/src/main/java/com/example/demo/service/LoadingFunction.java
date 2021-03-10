package com.example.demo.service;

import java.util.Map;

public interface LoadingFunction<K, V> {
	
	/**
	 * remove key from cache
	 * @param key
	 * @return
	 */
	void remove(K key);
	
	/**
	 * apply value from DB and put it in cache
	 * @param key
	 * @return
	 */
	V apply(String key);
	
	/**
	 * add key and value in cache
	 * @param key
	 * @param value
	 */
	Map<K,V> put(K key ,V value);
	
	/**
	 * get value by key in cache
	 * @param key
	 * @return
	 */
	V get(String key);
}
