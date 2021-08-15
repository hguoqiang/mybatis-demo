
package org.hgq;


import redis.clients.jedis.Jedis;

public class JedisUtils {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("120.26.250.18");
		// 如果 Redis 服务设置了密码，需要下面这行，没有就不需要
		// jedis.auth("123456");
		System.out.println("连接成功");
		//查看服务是否运行
		System.out.println("服务正在运行: "+jedis.ping());
	}
}
