package cn.edu.nju.software.service.impl.user;

import cn.edu.nju.software.dao.user.UserGoldBillMapper;
import cn.edu.nju.software.entity.UserGoldBill;
import cn.edu.nju.software.service.user.UserGoldBillService;
import cn.edu.nju.software.vo.BillItemVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zj
 */
@Service
public class UserGoldBillServiceImpl implements UserGoldBillService {
    @Autowired
    private UserGoldBillMapper userGoldBillMapper;

    @Override
    public int addGold(BillItemVo vo) {
        UserGoldBill userGoldBill = new UserGoldBill();
        BeanUtils.copyProperties(vo, userGoldBill);
        return userGoldBillMapper.insert(userGoldBill);
    }

    @Override
    public int deleteGold(BillItemVo vo) {
        //userGoldBillMapper.deleteByPrimaryKey(vo.get)
        return 0;
    }

    @Override
    public List<UserGoldBill> getBillByPage() {
        return null;
    }
}
