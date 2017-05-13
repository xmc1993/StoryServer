//import cn.edu.nju.software.util.JedisUtil;
//import org.junit.Test;
//import redis.clients.jedis.Jedis;
//
///**
// * Created by xmc1993 on 16/11/15.
// */
//public class RedisTest {
//
//
//	@Test
//	public void testApi(){
//		Jedis jedis = JedisUtil.getJedis();
//		jedis.set("feng", "key");
//		String key = jedis.get("feng");
//		Long dads = jedis.del("feng");
//		System.out.println(key);
//	}
//
//	@Test
//	public void testRedisCache(){
//		//        ArrayList<CarouselDetailVo> carouselDetailVos = new ArrayList<>();
//		//        CarouselDetailVo carouselDetailVo = new CarouselDetailVo();
//		//        carouselDetailVo.setId(1);
//		//        carouselDetailVo.setLength(1);
//		//        carouselDetailVo.setDescription("desc");
//		//        carouselDetailVo.setHighQualityUrl("http://");
//		//        carouselDetailVo.setAdvOptions(0);
//		//        carouselDetailVo.setIntroduction("intro");
//		//        carouselDetailVo.setTitle("title");
//		//        carouselDetailVo.setType("article");
//		//        carouselDetailVos.add(carouselDetailVo);
//		//        CarouselCache cache = new CarouselCacheImpl();
//		//        cache.put(110, null);
//		//        cache.put(110, carouselDetailVos);
//		//        List<CarouselDetailVo> carouselDetailVos1 = cache.get(110);
//		//        System.out.println("");
//	}
//}
