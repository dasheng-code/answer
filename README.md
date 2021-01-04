1.实现用户名生成算法
-------------------------
      public  int pseudoEncrypt(String mobileNo){
        int h = 0;
        char[] value = mobileNo.toCharArray();
        for (char val : value) {
            h += val;
        }
        int l1 = (h >> 16) & 0xffff;
        int r1 = h & 0xffff;
        int l2, r2;
        int i;
        for (i = 0; i < 3; i++) {
            l2 = r1;
            r2 = l1 ^ (int)(Math.round(((((1366*r1 + 150889) % 714025) / 714025.0) * 32767))) ;
            l1 = l2;
            r1 = r2;
        }
        return ((r1 << 16) +l1);
    }

2.用restful实现登录注册功能
----------------------------
      见userController

5.圈地策略
------------------
      战马的足迹+起点和终点间的直线围成一个正方形，封地上可以建立城池，开4个城门
