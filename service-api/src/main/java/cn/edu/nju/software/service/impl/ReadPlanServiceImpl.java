package cn.edu.nju.software.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.edu.nju.software.entity.Baby;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.nju.software.dao.ReadingPlanMapper;
import cn.edu.nju.software.entity.ReadingPlan;
import cn.edu.nju.software.entity.ReadingPlanExample;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.ReadPlanService;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月21日 
*/
@Service
public class ReadPlanServiceImpl implements ReadPlanService {

	@Autowired
	ReadingPlanMapper readingPlanMapper;
	
	//返回自增主键
	@Override
	public int saveReadPlan(ReadingPlan readPlan) {
		return readingPlanMapper.insert(readPlan);
	}

	@Override
	public int deleteReadPlan(Integer id) {
		return readingPlanMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateReadPlan(ReadingPlan readPlan) {
		return readingPlanMapper.updateByPrimaryKeyWithBLOBs(readPlan);
	}

	@Override
	public ReadingPlan selectReadPlanById(Integer id) {
		return readingPlanMapper.selectByPrimaryKey(id);
	}

	@Override
	public ResponseData<List<ReadingPlan>> getAllReadPlan(Integer page,Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		ReadingPlanExample readingPlanExample=new ReadingPlanExample();
        //通过criteria构造查询条件
		ReadingPlanExample.Criteria criteria = readingPlanExample.createCriteria();
        criteria.andValidEqualTo(1);
        //数据库中有大文本文件 查询使用这个方法，selectByExampleWithBLOBs
        List<ReadingPlan> list = readingPlanMapper.selectByExampleWithBLOBs(readingPlanExample);
        PageInfo<ReadingPlan> pageInfo=new PageInfo<>(list);
        int count=(int)pageInfo.getTotal();
        ResponseData<List<ReadingPlan>> responseData=new ResponseData<>();
        responseData.setCount(count);
        responseData.jsonFill(1,null, list);
		return responseData;
	}

	/**
	 * 因为判断是否触发徽章模块的service层需要调用获取阅读计划的方法，所以将相关代码提取到service层。
	 * 3个月：91天
	 * 3-6个月：182天
	 * 6-12个月：365天
	 * 12-18个月：547天
	 * 18-24个月：730天
	 * 2-3岁：1095天
	 * 3-4岁：1460天
	 * 4-5岁：1825天
	 * 5-6岁：2190天
	 * 当宝宝年龄大于6岁时，小于7岁时，阅读计划给他返回 5-6岁的阅读计划
	 * 还未出生的宝宝给他返回0-2岁的阅读计划
	 * @return 由于getReadingPlanByTime方法调用了select*ByExample方法，所以虽然是只返回一个对象也用list装
	 */
	@Override
	public List<ReadingPlan> getReadingPlanByTime(Baby baby) {

		Date currentTime = new Date();

		//获取生日
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		String babyBirthday = formatter.format(baby.getBirthday());

		// 获取当前年份和月份
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM");
		String timePoint = formatter2.format(currentTime);

		// 计算天数,这里的days是baby属于哪个年龄段的最大天数
		long days = 0;
		days = getDays(babyBirthday, dateString);

		//分情况提取对应的阅读计划
		if (days <= 730) {
			List<ReadingPlan> list = getReadingPlanByTime("0-2岁", timePoint);
			return list;
		} else if (days <= 1095 && days > 730) {
			List<ReadingPlan> list = getReadingPlanByTime("2-3岁", timePoint);
			return list;
		} else if (days <= 1460 && days > 1095) {
			List<ReadingPlan> list = getReadingPlanByTime("3-4岁", timePoint);
			return list;
		} else if (days <= 1825 && days > 1460) {
			List<ReadingPlan> list = getReadingPlanByTime("4-5岁", timePoint);
			return list;
		} else if (days <= 2190 && days > 1825) {
			List<ReadingPlan> list = getReadingPlanByTime("5-6岁", timePoint);
			return list;
		} else if (days <= 2555 && days > 2190) {
			List<ReadingPlan> list = getReadingPlanByTime("5-6岁", timePoint);
			return list;
		}
		return null;

	}

	// 方法抽取获取 获取俩段时间的天数 date1为开始时间
	private long getDays(String date1, String date2) {
		if (date1 == null || date1.equals("")) {
			return 0;
		}
		if (date2 == null || date2.equals("")) {
			return 0;
		}
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return Math.abs(day);
	}

	//方法提取，当查询条件都具备了，在这里跟数据库进行交互
	private List<ReadingPlan> getReadingPlanByTime(String days,String timePoint){
		ReadingPlanExample readingPlanExample=new ReadingPlanExample();
		//通过criteria构造查询条件
		ReadingPlanExample.Criteria criteria = readingPlanExample.createCriteria();
		criteria.andAgegroupEqualTo(days);
		criteria.andTimepointEqualTo(timePoint);
		List<ReadingPlan> list=readingPlanMapper.selectByExampleWithBLOBs(readingPlanExample);
		return list;
	}

}
