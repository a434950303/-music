/** 
* 
*
*
*/

package com.bjpowernode.music.ss.service;

import java.util.List;

import com.bjpowernode.music.common.IServiceOperations;
import com.bjpowernode.music.ss.domain.MyMusic;

public interface IMyMusicService extends IServiceOperations<MyMusic, MyMusic> {

	public String getUserById(String user_name, String user_password);




	// 从数据库中获取所有当前用户的歌单
	public List<MyMusic> getMySongListNames(int userId);

	// 删除音乐
	public int deleteMyMusic(int song_id, int user_id);

	int addSongList(String songListName,int userId);

	int addSongToSongList(int song_id, int song_list_id);
}
