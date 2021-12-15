package server;

/*
 * 유저정보
 */
public class UserData {
	public String id;
	public String pw;
	public String name;
	public String nickName;
	public String email;
	public String SNS;
	public String accessNum;
	public String lastAccessIP;
	public String lastAccessTime;
	public String win;
	public String draw;
	public String defeat;
	
	UserData(String id, String pw, String name, String nickName, String email, String SNS, String accessNum, String lastAccessIP, String lastAccessTime, String win, String draw, String defeat){
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.nickName = nickName;
		this.email = email;
		this.SNS = SNS;
		this.accessNum = accessNum;
		this.lastAccessIP = lastAccessIP;
		this.lastAccessTime = lastAccessTime;
		this.win = win;
		this.draw = draw;
		this.defeat = defeat;
	}
}
