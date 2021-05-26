/** 
* 
*
*
*/

package com.bjpowernode.music.ss.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.bjpowernode.music.common.AbstractService;
import com.bjpowernode.music.common.IOperations;
import com.bjpowernode.music.ss.domain.MyMusic;
import com.bjpowernode.music.ss.mapper.IMyMusicMapper;
import com.bjpowernode.music.ss.service.IMyMusicService;
import org.springframework.stereotype.Service;

@Service("myMusicService")
public class MyMusicService extends AbstractService<MyMusic, MyMusic> implements IMyMusicService {

	public MyMusicService() {
		this.setTableName("myMusic");
	}

	@Resource
	private IMyMusicMapper myMusicMapper;

	@Override          
	protected IOperations<MyMusic, MyMusic> getMapper() {
		return myMusicMapper;
	}

	@Override
	public void setTableName(String tableName) {
		this.tableName = tableName;
		;
	}

	@Override
	public String getUserById(String user_name, String user_password) {
		return myMusicMapper.getUserById(user_name, user_password);
	}


	@Override
	public List<MyMusic> getMySongListNames(int userId) {
		return myMusicMapper.getMySongListNames(userId);
	}

	// 删除音乐
	@Override
	public int deleteMyMusic(int song_id, int user_id) {
		return myMusicMapper.deleteMyMusic(song_id, user_id);
	}



	// 删除音乐
	@Override
	public int addSongList(String songListName,int userId) {
		return myMusicMapper.addSongList(songListName,userId);
	}

	@Override
	public int addSongToSongList(int song_id, int song_list_id) {
		return myMusicMapper.addSongToSongList(song_id,song_list_id);
	}
}
