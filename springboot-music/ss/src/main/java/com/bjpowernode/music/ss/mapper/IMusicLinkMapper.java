package com.bjpowernode.music.ss.mapper;

import java.util.List;

import com.bjpowernode.music.ss.domain.MyMusic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bjpowernode.music.common.IOperations;
import com.bjpowernode.music.ss.domain.MusicLink;

public interface IMusicLinkMapper extends IOperations<MusicLink, MusicLink> {

	// #是将传入的值当做字符串的形式,$是将传入的数据直接显示生成sql语句,所以必须用$
	// 从数据库中搜索歌曲
	@Select("select a.*,b.singer_photo,b.singer_type from musiclink a left join singer b on a.ml_singer=b.singer_name where a.ml_songName like '%${songName}%'")
	public List<MusicLink> songRearch(@Param("songName") String songName);


	@Select("select a.*,b.singer_photo,b.singer_type from musiclink a left join singer b on a.ml_singer=b.singer_name where a.ml_singer like '%${singerName}%'")
	public List<MusicLink> songRearchBySinger(@Param("singerName") String singerName);


	@Select("select * from user u LEFT JOIN song_list_user slu on u.user_id = slu.user_id \n" +
			"LEFT JOIN song_list_song sls on slu.song_list_id =sls.song_list_id left join musiclink ml on\n" +
			"sls.song_id=ml.ml_id where  u.user_id = ${user_id}")
	public List<MusicLink> getMyMusicList(@Param("user_id") int user_id);

	// 获取用户id
	@Select("select user_id from user where user_name=#{user_name} and user_password=#{user_password}")
	public String getUserId(@Param("user_name") String user_name, @Param("user_password") String user_password);

	public void insertSongRearch(int song_id, int userId);

	// 判断歌曲重复
	@Select("select my_id from mymusic where my_songName=#{songName} and user_id =#{userId}")
	public String judgeSong(@Param("songName") String songName, @Param("userId") int userId);


	// 删除音乐
	@Delete("delete from song_list_song where song_list_id=${song_list_id} and song_id=${song_id}")
	int deleteMyMusic(@Param("song_list_id")Integer song_list_id,@Param("song_id") Integer song_id);
}
