package com.bjpowernode.music.ss.controller.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bjpowernode.music.common.WebResponse;
import com.bjpowernode.music.ss.domain.MusicLink;
import com.bjpowernode.music.ss.domain.MyMusic;
import com.bjpowernode.music.ss.service.IMusicLinkService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjpowernode.music.ss.service.IMyMusicService;

/**
 * @date 2018年12月23日 00:03:48
 */

@Controller
@RequestMapping("/myMusic")
public class MyMusicController {

    @Autowired
    protected WebResponse webResponse;

    @Resource
    protected IMyMusicService myMusicService;

    @Resource
    protected IMusicLinkService musicLinkService;
//	@Autowired
//	MyMusicService myMusicService2;

    // 歌曲收藏
    @RequestMapping(value = "/addMusicCollect", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public WebResponse addMusicCollect(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                       @RequestParam(required = false) String user_name) {
        WebResponse webResponse = new WebResponse();
        MyMusic myMusic = new MyMusic();
        System.out.println("接收到的用户名:" + user_name);

        Integer statusCode = 200;

        // 数据库插入语句，对应xml文件的insert
        this.myMusicService.insert(myMusic);

        webResponse.setStatusCode(statusCode);
        return webResponse;

    }

    // 从数据库中获取歌曲数据，在我的音乐中显示
    @RequestMapping(value = "/getMyMusicList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public WebResponse getMyMusicList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                      @RequestParam(required = false) int user_id
    ) {

        Object data = null;
        String statusMsg = "";
        int statusCode = 200;

        try {
            List<MusicLink> list = musicLinkService.getMyMusicList(user_id);

            Map<Object, Object> map = new HashMap<Object, Object>();
            // map.put("total", count);
            int size = list.size();
            if (size > 0) {
                List<MusicLink> listFont = new ArrayList<MusicLink>();
                MusicLink vo;
                MusicLink voFont = new MusicLink();
                for (int i = 0; i < size; i++) {
                    vo = list.get(i);
                    // 通过java反射将类中当前属性字段对应的内容复制到另外一个类中
                    BeanUtils.copyProperties(vo, voFont);
                    listFont.add(voFont);
                    voFont = new MusicLink();
                }
                map.put("list", listFont);
                data = map;
                statusMsg = "根据条件获取分页数据成功！！！";
            } else {
                map.put("list", list);
                data = map;
                statusCode = 202;
                statusMsg = "no record!!!";
                return webResponse.getWebResponse(statusCode, statusMsg, data);
            }

        } catch (Exception e) {

        }


        return webResponse.getWebResponse(statusCode, statusMsg, data);
    }

    // 删除音乐
    @RequestMapping(value = "/deleteMyMusic", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public WebResponse deleteMyMusic(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                     @RequestParam(required = false) Integer song_list_id, @RequestParam(required = false) Integer ml_id) {
        WebResponse webResponse = new WebResponse();

        Object data = null;
        String statusMsg = "";
        int statusCode = 201;
        int del = 0;
        del = musicLinkService.deleteMyMusic(song_list_id, ml_id);


        return webResponse.getWebResponse(statusCode, statusMsg, data);
    }


    // 新增歌单
    @RequestMapping(value = "/addSongList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public WebResponse addSongList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                   @RequestParam(required = false) String songListName, @RequestParam(required = false) int userId) {
        WebResponse webResponse = new WebResponse();
        MyMusic myMusic = new MyMusic();

        Object data = null;
        String statusMsg = "";
        int statusCode = 200;
        int del = 0;

        del = this.myMusicService.addSongList(songListName, userId);

        return webResponse.getWebResponse(statusCode, statusMsg, data);
    }


    // 从数据库中获取歌曲数据，在我的音乐中显示
    @RequestMapping(value = "/getMySongListNames", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public WebResponse getMySongListNames(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                          @RequestParam(required = false) int user_id
    ) {
        String str = "";
        Object data = null;
        String statusMsg = "";
        int statusCode = 200;

        try {
            List<MyMusic> list = this.myMusicService.getMySongListNames(user_id);
            List<String> stringList = list.stream().map(e -> "\"" + e.getSong_list_id() + "\": \"" + e.getSong_list_name() + "\"").collect(Collectors.toList());
            str = String.join(",", stringList);
            str = "{" + str + "}";

        } catch (Exception e) {

        }


        return webResponse.getWebResponse(statusCode, statusMsg, str);
    }

    // 增加收藏
    @RequestMapping(value = "/addSongToSongList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public WebResponse addSongToSongList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                         @RequestParam(required = false) int song_id, @RequestParam(required = false) int song_list_id
    ) {
        String statusMsg = "";
        int statusCode = 200;
        int res = this.myMusicService.addSongToSongList(song_id, song_list_id);
        return webResponse.getWebResponse(statusCode, statusMsg, "收藏成功！");
    }

}
