package com.android_gazete;

public class Gazete {
	int _id;
	String name;
	String url;
	byte[] image;
	int rank;
	public Gazete(int id,String name, String url, byte[] image, int rank) {
		// TODO Auto-generated constructor stub
		this._id=id;
		this.name=name;
		this.url=url;
		this.image=image;
		this.rank=rank;
	}
	public Gazete(String name,String url,byte[] image,int rank){
		this.name=name;
		this.url=url;
		this.image=image;
		this.rank=rank;
	}
	public Gazete()
	{
	
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
		

}
