package com.example.demo.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.service.LoadingFunction;

@Service
public class LoadingFunctionImpl implements LoadingFunction<String, String> {

	private static final Logger log = LoggerFactory.getLogger(LoadingFunctionImpl.class);

	static Map<String, String> map = new ConcurrentHashMap<String, String>();

	@Override
	public String apply(String key) {
		log.info("-------  call apply method -----------");
		String result = "I am a " + key + " from apply";
		return result;
	}

	@Override
	public Map<String, String> put(String key, String value) {
		map.put(key, value);
		return map;
	}

	@Override
	public String get(String key) {
		Boolean exist = map.get(key) != null;

		if (exist) {
			log.info("get {} from cache", key);
			return map.get(key);
			
		} else {
			String lock = "lock:"+key;
			Boolean getlock = getLock(lock);
			if (getlock) {
				String string = apply(key);
				log.info("{} not exist in cache ,need to apply method ", key, key);
				put(key, string);
				// release lock
				remove(lock);
			}

		}
		log.info("get {} from cache", key);
		return map.get(key);
	}

	@Override
	public void remove(String key) {
		map.remove(key);

	}

	/**
	 * Mini distributed lock
	 * 
	 * @return
	 */
	synchronized Boolean getLock(String lock) {
		Boolean exist = !map.containsKey(lock);
		if (exist) {
			map.put(lock, lock);
			return true;
		}
		return false;
	}

}
