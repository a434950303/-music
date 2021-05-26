package com.bjpowernode.music.ss.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MusicLink implements java.io.Serializable {
	private Integer ml_id;
	private String ml_songName;
	private String ml_singer;
	private String ml_songLink;
	private String ml_lyricLink;
	private String ml_photoLink;
	private String singer_type;
	private String radio_type;
	private String singer_photo;
	private Integer song_list_id;
	private String  song_list_name;

	public Integer getMl_id() {
		return ml_id;
	}

	public void setMl_id(Integer ml_id) {
		this.ml_id = ml_id;
	}

	public String getMl_songName() {
		return ml_songName;
	}

	public void setMl_songName(String ml_songName) {
		this.ml_songName = ml_songName;
	}

	public String getMl_singer() {
		return ml_singer;
	}

	public void setMl_singer(String ml_singer) {
		this.ml_singer = ml_singer;
	}

	public String getMl_songLink() {
		return ml_songLink;
	}

	public void setMl_songLink(String ml_songLink) {
		this.ml_songLink = ml_songLink;
	}

	public String getMl_lyricLink() {
		return ml_lyricLink;
	}

	public void setMl_lyricLink(String ml_lyricLink) {
		this.ml_lyricLink = ml_lyricLink;
	}

	public String getMl_photoLink() {
		return ml_photoLink;
	}

	public void setMl_photoLink(String ml_photoLink) {
		this.ml_photoLink = ml_photoLink;
	}

	public String getSinger_type() {
		return singer_type;
	}

	public void setSinger_type(String singer_type) {
		this.singer_type = singer_type;
	}

	public String getRadio_type() {
		return radio_type;
	}

	public void setRadio_type(String radio_type) {
		this.radio_type = radio_type;
	}

	public String getSinger_photo() {
		return singer_photo;
	}

	public void setSinger_photo(String singer_photo) {
		this.singer_photo = singer_photo;
	}

	public Integer getSong_list_id() {
		return song_list_id;
	}

	public void setSong_list_id(Integer song_list_id) {
		this.song_list_id = song_list_id;
	}

	public String getSong_list_name() {
		return song_list_name;
	}

	public void setSong_list_name(String song_list_name) {
		this.song_list_name = song_list_name;
	}

	@Override
	public String toString() {
		return "MusicLink [ml_id=" + ml_id + ", ml_songName=" + ml_songName + ", ml_singer=" + ml_singer
				+ ", ml_songLink=" + ml_songLink + ", ml_lyricLink=" + ml_lyricLink +", radio_type=" + radio_type +", singer_photo=" + singer_photo + ", singer_type=" + singer_type +", ml_photoLink=" + ml_photoLink
				+ "]";
	}

}
