package com.tensquare.friend.controller;

import com.tensquare.friend.pojo.NotFriend;
import com.tensquare.friend.service.FriendService;
import com.tensquare.friend.service.NotFriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private NotFriendService notFriendService;
    @Autowired
    private HttpServletRequest request;


    /**
     * @author: zhangyu
     * @date: 2019-12-25
     * @param: [friendId, type]
     * @return: entity.Result
     * 功能描述: 添加好友的方法
     */
    @PutMapping("like/{friendId}/{type}")
    public Result addFriend(@PathVariable String friendId,@PathVariable String type){
        try {
            //从请求作用域中获取用户信息
            Claims claims = (Claims) request.getAttribute("claims");
            //判断登陆的口令token是否存在，并且是否能获得用户id
            if (claims != null && claims.getId() != null){
                //得到当前用户的用户名
                String userId = claims.getId();
                //判断type决定是添加到好友还是添加到非好友
                if ("1".equals(type)){
                    //调用service完成添加好友功能
                    friendService.addFriend(userId,friendId);
                }else {
                    //调用service完成添加非好友功能
                    notFriendService.addNotFriend(userId,friendId);
                }
                return new Result(true,StatusCode.OK,"添加成功");
            }
            return new Result(false,StatusCode.ERROR,"请先登陆在添加");
        }catch (RuntimeException e){
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,e.getMessage());
        }
    }
}
