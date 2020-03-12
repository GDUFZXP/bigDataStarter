package com.vivo.jedis;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public class ClusterPoolUtil {
	
	private static JedisCluster jedisCluster;
	private static String hostAndPorts = "10.101.21.214:6686,10.101.21.215:6685";
		
	public static JedisCluster getJedisCluster(){
				
		if(jedisCluster==null){
			int timeOut = 10000;
			Set<HostAndPort> nodes = new HashSet<HostAndPort>();
			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setMaxTotal(200);
			poolConfig.setMaxIdle(50);
			poolConfig.setMaxWaitMillis(1000 * 100);
			poolConfig.setTestOnBorrow(false);
			
			String[] hosts = hostAndPorts.split(",");
			for(String hostport:hosts){
				String[] ipport = hostport.split(":");
				String ip = ipport[0];
				int port = Integer.parseInt(ipport[1]);
				nodes.add(new HostAndPort(ip, port));
			}
			jedisCluster = new JedisCluster(nodes,timeOut, poolConfig);
		}
		return jedisCluster;
	}
	
	public static void set(String key,String value){
		JedisCluster jedisCluster = getJedisCluster();
		jedisCluster.set(key, value);
	}
	
	public static String get(String key){
		String value = null;
		JedisCluster jedisCluster = getJedisCluster();
		value = jedisCluster.get(key);
		return value;
	}
	
	public static void main(String[] args) {
		JedisCluster jedisCluster = getJedisCluster();
		String bittest = "hhh";
		jedisCluster.setbit(bittest, 123456L, true);
		jedisCluster.setbit(bittest, 789456L, true);
		jedisCluster.setbit(bittest, 789, true);
		jedisCluster.setbit(bittest, 456, true);
		jedisCluster.setbit(bittest, 123, true);
		jedisCluster.setbit(bittest, 100, true);
		final byte[] bytes = jedisCluster.get(bittest.getBytes());
		System.out.println(bytes);
		BitSet bitSet = fromByteArrayReverse(bytes);
		System.out.println(bitSet.cardinality());
	}

	public static BitSet fromByteArrayReverse(final byte[] bytes) {
		final BitSet bits = new BitSet();
		for (int i = 0; i < bytes.length * 8; i++) {
			if ((bytes[i / 8] & (1 << (7 - (i % 8)))) != 0) {
				bits.set(i);
			}
		}
		return bits;
	}


}