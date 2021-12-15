package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.swing.*;

/*
 * 유저정보 Frame class
 * 대결을 신청할경우 1대1 채팅방으로 이동
 */

public class UserInfoFrame extends JFrame {
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 500;
	public String opponentNickName;//상대편 정보를 불러오기 위한 닉네임
	private UserInfo userInfo = new UserInfo();
	String userInfoStr = null;
	JFrame thisFrame = null;
	
	Thread t = null;
	
	public UserInfoFrame(String opponentNickName) {
		super("User Info");
		this.opponentNickName = opponentNickName;
		
		//Frame 기본 설정
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);//화면 가운데 정렬
		
		//Frame 내부 배치 설정
		containerSetup();
		
		//창 보이기
		setVisible(true);
	}
	
	//------------------------------
	//Frame 내부 배치 설정
	//------------------------------
	private void containerSetup() {
		Container mainCon = this.getContentPane();
		thisFrame = this;
		mainCon.setLayout(null);
		
		//------------------------------
		//유저정보
		//------------------------------
		JLabel userInfoF = new JLabel();
		userInfoF.setBounds(10, 10, 360, 360);
		//배경색상설정
		userInfoF.setOpaque(true);
		userInfoF.setBackground(Color.white);
		//글자정렬, 글자크기 변경
		userInfoF.setVerticalAlignment(JLabel.TOP);
		userInfoF.setFont(userInfoF.getFont().deriveFont(12.0f));	
		//유저정보
		String[] userInfoStrList = userInfo.loadUserInfo(opponentNickName);
		userInfoStr = "<html>";
		for(int i = 0; i < userInfoStrList.length; i++)
			userInfoStr += userInfoStrList[i] + "<br/>";
		userInfoStr += "</html>";
		userInfoF.setText(userInfoStr);
		final String userInfoStrTemp = userInfoStr;
		
		//------------------------------
		//대결신청 버튼
		//------------------------------
		JButton matchBtn = new JButton("대결신청");
		matchBtn.setBounds(10, 400, 360, 40);
		
		//대결신청 버튼 클릭시
		ActionListener matchListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(opponentNickName.equals(Login.myID))
					JOptionPane.showMessageDialog(null, "오류: 자신과의 승부는 불가능합니다.");
				else {
					try {
						DataOutputStream outToServer =
								new DataOutputStream(ClientMain.clientSocket.getOutputStream());//서버로 출력
						outToServer.writeBytes("202" + Login.myID + "/" + opponentNickName + "\n");										
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}								
					
				}
			}
		};
		matchBtn.addActionListener(matchListener);
		
		this.add(userInfoF);
		this.add(matchBtn);
	}

		
}
