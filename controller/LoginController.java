package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.CustomerVO;

public class LoginController implements Initializable {
	@FXML	private TextField txtId;
	@FXML	private PasswordField txtPassword;
	@FXML	private Button btnLogin;
	@FXML	private Button btnCancel;
	@FXML	private Button btnSignup;
	private CustomerDAO customerDAO;
	public CustomerVO selectCustomer =null;
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//1. 로그인버튼 이벤트 처리
		btnLogin.setOnAction( e-> {	handlerBtnLoginAction(e); });
		//2. 버튼취소 이벤트처리
		btnCancel.setOnAction( e-> {	handlerBtnCancelAction(e); });
		//3. 회원가입 버튼 이벤트처리
		btnSignup.setOnAction( e-> {	handlerBtnSignupAction(e); });
		//테스트용으로 set
		txtId.setText("hadong");
		txtPassword.setText("1234");

	}

	//3. 회원가입 버튼 이벤트처리
	private void handlerBtnSignupAction(ActionEvent e) {
		Parent signupRoot = null;
		try {
			signupRoot = FXMLLoader.load(getClass().getResource("/view/signup.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnSignup.getScene().getWindow());
			stageDialog.setTitle("회원가입");
			// ===============================
			// TextField 객체 가져와서 필드 세팅
			// 여기도 포맷 제한 해야 함
			TextField editId = (TextField) signupRoot.lookup("#txtId");
			TextField editpassword = (TextField) signupRoot.lookup("#txtPassword");
			TextField editpasswordcheck = (TextField) signupRoot.lookup("#txtPasswordcheck");
			TextField editName = (TextField) signupRoot.lookup("#txtName");
			TextField editPhone = (TextField) signupRoot.lookup("#txtPhone");
			TextField editEmail = (TextField) signupRoot.lookup("#txtEmail");

			// Button 객체 가져와서 이벤트 처리
			Button btnOK = (Button) signupRoot.lookup("#btnOK");
			Button btnCancel = (Button) signupRoot.lookup("#btnCancel");
			
			// 취소 버튼을 눌렀을때
			btnCancel.setOnAction((e3) -> {
				stageDialog.close();
			});
			// 등록 (저장)버튼을 눌렀을때
			 btnOK.setOnAction((e4) -> {
				try {
					
					 if(!editpassword.getText().equals(editpasswordcheck.getText())) {
						 throw new Exception("비밀번호확인 불일치"); 
						 }
					 
					CustomerVO cvo = new CustomerVO(editId.getText(), editpassword.getText(),
							editName.getText(), editPhone.getText(), editEmail.getText());
					// 테이블뷰에 들어가버린 순간..
						// DB를 부르는 명령
					customerDAO = new CustomerDAO();
						// 데이터베이스 테이블에 입력값을 입력하는 함수
						int count = customerDAO.getCustomerregiste(cvo);
						if (count == 0) {
							throw new Exception("아이디 중복");
						}else {
							stageDialog.close();
						}
					
					// 알림창 띄우기
					alertDisplay(1, "회원가입성공", "가입해주셔서 감사합니다.", "축하축하");
					
				} catch (Exception e7) {
					// 알림창 띄우기
					alertDisplay(1, "회원가입실패", "회원등록실패",e7.getMessage());
				}
				}
			);

			// ===============================

			Scene scene = new Scene(signupRoot);
			stageDialog.setScene(scene);
			stageDialog.setResizable(false);
			stageDialog.show();

		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
	}


	//1. 로그인버튼 이벤트 처리
	public void handlerBtnLoginAction(ActionEvent event) {
		
		try {
			CustomerDAO customerDAO = new CustomerDAO();
			LoginCustomerDAO loginCustomerDAO  = new LoginCustomerDAO();
			selectCustomer = customerDAO.getCustomerCheck(txtId.getText(),txtPassword.getText());
			System.out.println("로그인 컨트롤러 selectCustomer=" + selectCustomer);
			loginCustomerDAO.getLoginCustomerregiste(selectCustomer);
			if(txtId.getText().equals("manager") && txtPassword.getText().equals("1234") ) {
				//관리자 아이디로 로그인이 될 경우 관리자 메인창으로 이동한다. 
				Parent mainView=null;
				Stage mainStage=null;
//					mainView=FXMLLoader.load(getClass().getResource("/view/main.fxml"));
					mainView=FXMLLoader.load(getClass().getResource("/view/managerMain.fxml"));
					Scene scene = new Scene(mainView);
					mainStage = new Stage();
					mainStage.setTitle("manager Main");
					mainStage.setScene(scene);
					mainStage.setResizable(true);
					//현재의 스테이지를 닫고 새로운창을 연다. 
					((Stage) btnLogin.getScene().getWindow()).close();
					mainStage.show();
			}else if (selectCustomer == null) {
				throw new Exception("아이디와 비밀번호를 확인해주세요");
			}
			else {
				//클라이언트 아이디로 로그인이 되었을 selectDog창으로 이동한다. 
				Parent View=null;
				Stage Stage=null;
//					mainView=FXMLLoader.load(getClass().getResource("/view/main.fxml"));
					View=FXMLLoader.load(getClass().getResource("/view/myDog.fxml"));
					Scene scene = new Scene(View);
					Stage = new Stage();
					Stage.setTitle(txtId.getText()+"님의 강아지");
					Stage.setScene(scene);
					Stage.setResizable(true);
					//현재의 스테이지를 닫고 새로운창을 연다. 
					((Stage) btnLogin.getScene().getWindow()).close();
					Stage.show();
			}
			
		} catch (Exception e1) {
			DBUtil.alertDisplay(5, "로그인", "실패했음", e1.toString());
		}
	}
	//2. 버튼취소 이벤트처리
	public void handlerBtnCancelAction(ActionEvent e) {
		((Stage) btnLogin.getScene().getWindow()).close();
	}
	// 경고창 처리하는 함수
	private void alertDisplay(int type, String title, String headerText, String contentText) {
		Alert alert = null;
		switch(type){
		case  1:   alert = new Alert(AlertType.WARNING); break;
		case  2:   alert = new Alert(AlertType.CONFIRMATION);break;
		case  3:   alert = new Alert(AlertType.ERROR);break;
		case  4:   alert = new Alert(AlertType.NONE);break;
		case  5:   alert = new Alert(AlertType.INFORMATION);break;
		}
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(headerText+"\n"+contentText);
		alert.setResizable(false);
		alert.show();
	}
}
