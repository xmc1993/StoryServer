package cn.edu.nju.software.service.user;

import cn.edu.nju.software.entity.UserGoldBill;
import cn.edu.nju.software.vo.BillItemVo;

import java.util.List;

/**
 * @author zj
 */

public interface UserGoldBillService {

    int addGold(BillItemVo vo);

    int deleteGold(BillItemVo vo);

    List<UserGoldBill> getBillByPage();

}
