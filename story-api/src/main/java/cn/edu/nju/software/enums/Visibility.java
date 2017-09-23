package cn.edu.nju.software.enums;

/**
 * Created by xmc1993 on 2017/9/22.
 */
public class Visibility {
    //互关
    public final static int  MUTUAL = 1;//0000 0000 0000 0001
    //被关注
    public final static int FOLLOWEE = 2;//0000 0000 0000 0010
    //关注
    public final static int FOLLOWER = 4;//0000 0000 0000 0100
    //自己
    public final static int SELF = 8;//0000 0000 0000 1000

    public final static int STRANGER = 16;//0000 0001 0000

    //所有人可见 预留了3位bit给其他权限
    public final static int ALL_VISUAL = 255;//0000 0000 1111 1111
    //仅自己可见
    public final static int SELF_VISUAL = 8;//0000 0000 0000 1000
    //仅关注者可见
    public final static int FOLLOWER_VISUAL = 12;//0000 0000 0000 1100
    //仅我关注的人可见
    public final static int FOLLOWEE_VISUAL = 10;//0000 0000 0000 1010
    //仅相互关注的人可见
    public final static int MUTUAL_VISUAL = 9;//0000 0000 0000 1001

}
