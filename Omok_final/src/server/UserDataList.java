package server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/*
 * 유저정보와 관련된 기능들을 담당
 * 유저정보 불러오기, 기타기능 처리(ID, 비밀번호 확인 등)
 */
public class UserDataList {
	LinkedList<UserData> userDataList = null;
	
	UserDataList(){
		readData();
	}
	
	//------------------------------
	//파일에서 유저정보 불러오기
	//------------------------------
	public void readData() {
		userDataList = new LinkedList<UserData>();
		
		File file=new File("Members.txt");
		Scanner scan = null;
		try {
				scan=new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
				
		while(scan.hasNextLine()) {
			String temp=scan.nextLine();
			String[] temp_a=temp.split("/");
					
			UserData tempData = new UserData(temp_a[0],temp_a[1],temp_a[2],temp_a[3],temp_a[4],temp_a[5],temp_a[6],temp_a[7],temp_a[8],temp_a[9],temp_a[10],temp_a[11]);
					
			userDataList.add(tempData);
		}
	}
	
	//------------------------------
	//파일로 유저정보 쓰기
	//------------------------------
	public void writeData() {	
		BufferedWriter fw = null;
		try {
			fw = new BufferedWriter(new FileWriter("Members.txt", false));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			for(int i = 0; i < userDataList.size(); i++) {
				fw.write(userDataList.get(i).id + "/" + userDataList.get(i).pw + "/" + userDataList.get(i).name + "/" + userDataList.get(i).nickName + "/" + userDataList.get(i).email + "/" + userDataList.get(i).SNS + "/" + userDataList.get(i).accessNum + "/" + userDataList.get(i).lastAccessIP + "/"+ userDataList.get(i).lastAccessTime + "/"+ userDataList.get(i).win + "/"+ userDataList.get(i).draw + "/"+ userDataList.get(i).defeat);
				fw.newLine();
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	     	     
	}
	
	//------------------------------
	//접속 IP, 접속 시간 업데이트
	//------------------------------
	public void changeLoginInfo(String id, String ip, String time) {
		readData();
		
		for(int i = 0; i < userDataList.size(); i++)
		{
			if(userDataList.get(i).id.equals(id)) {
				userDataList.get(i).lastAccessIP = ip;
				userDataList.get(i).lastAccessTime = time;
				userDataList.get(i).accessNum = String.valueOf(Integer.parseInt(userDataList.get(i).accessNum) + 1);
			}
		}
		
		writeData();
	}
	
	//------------------------------
	//닉네임을 통해 유저정보 검색
	//------------------------------
	public String userInfo(String nickName) {
		String resultStr = null;
		for(int i = 0; i < userDataList.size(); i++)
			if(userDataList.get(i).nickName.equals(nickName))
				resultStr = userDataList.get(i).nickName + "/" + userDataList.get(i).id + "/" + userDataList.get(i).name + "/" +  userDataList.get(i).email + "/" + userDataList.get(i).SNS + "/" + userDataList.get(i).accessNum + "/" + userDataList.get(i).lastAccessIP + "/"+ userDataList.get(i).lastAccessTime + "/"+ userDataList.get(i).win + "/"+ userDataList.get(i).draw + "/"+ userDataList.get(i).defeat;
		
		return resultStr;
	}
	
	public String getUserNickName(String id) {
		for(int i = 0; i < userDataList.size(); i++)
			if(userDataList.get(i).id.equals(id))
				return userDataList.get(i).nickName;
		
		return null;
	}
	
	public String getUserID(String nickName) {
		for(int i = 0; i < userDataList.size(); i++)
			if(userDataList.get(i).nickName.equals(nickName))
				return userDataList.get(i).id;
		
		return null;
	}
}
