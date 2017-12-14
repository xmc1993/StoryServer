package cn.edu.nju.software.vo;

import lombok.Data;

/**
 * @author zj
 */
@Data
public class BillItemVo {
    int amount;
    GoldBillItemType type;
    int relativeId;
    int userId;


}
