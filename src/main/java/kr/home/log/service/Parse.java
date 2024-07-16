package kr.home.log.service;

import java.util.List;
import java.util.Map;

public interface Parse<K, V> {
	Map<K, List<V>> parse(String param);
}
