/** 
* 
*
*
*/

package com.bjpowernode.music.ss.service;

import com.bjpowernode.music.common.IServiceOperations;
import com.bjpowernode.music.ss.domain.MusicLink;
import com.bjpowernode.music.ss.domain.MyMusic;

import java.util.List;

public interface IMusicLinkService extends IServiceOperations<MusicLink, MusicLink> {

	// 将榜单音乐收藏插入到我的音乐表中
	public void insertSongRearch(int song_id, int userId);

	public String judgeSong(String songName, int userId);
	// 从数据库中获取音乐到我的音乐列表中
	public List<MusicLink> getMyMusicList(int userId);

	int deleteMyMusic(Integer song_list_id, Integer ml_id);
}
