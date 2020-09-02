package com.sjl.rpc.context.zk.loadbalance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jianlei
 * @date: 2020/9/2
 * @description: 负载均衡
 */
public class LoadBlance {

  private static int i = 0;

  public static <T> String selectService(T datas) {
    String result = "";
    if (datas instanceof List) {
      List services = (List) datas;
      if (i >=services.size()) {
        i = 0;
      }
      result = (String) services.get(i++);

    } else if (datas instanceof String) {
      result = (String) datas;
    }

    return result;
  }

}
