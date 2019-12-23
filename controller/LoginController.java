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
		//1. �α��ι�ư �̺�Ʈ ó��
		btnLogin.setOnAction( e-> {	handlerBtnLoginAction(e); });
		//2. ��ư��� �̺�Ʈó��
		btnCancel.setOnAction( e-> {	handlerBtnCancelAction(e); });
		//3. ȸ������ ��ư �̺�Ʈó��
		btnSignup.setOnAction( e-> {	handlerBtnSignupAction(e); });
		//�׽�Ʈ������ set
		txtId.setText("hadong");
		txtPassword.setText("1234");

	}

	//3. ȸ������ ��ư �̺�Ʈó��
	private void handlerBtnSignupAction(ActionEvent e) {
		Parent signupRoot = null;
		try {
			signupRoot = FXMLLoader.load(getClass().getResource("/view/signup.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnSignup.getScene().getWindow());
			stageDialog.setTitle("ȸ������");
			// ===============================
			// TextField ��ü �����ͼ� �ʵ� ����
			// ���⵵ ���� ���� �ؾ� ��
			TextField editId = (TextField) signupRoot.lookup("#txtId");
			TextField editpassword = (TextField) signupRoot.lookup("#txtPassword");
			TextField editpasswordcheck = (TextField) signupRoot.lookup("#txtPasswordcheck");
			TextField editName = (TextField) signupRoot.lookup("#txtName");
			TextField editPhone = (TextField) signupRoot.lookup("#txtPhone");
			TextField editEmail = (TextField) signupRoot.lookup("#txtEmail");

			// Button ��ü �����ͼ� �̺�Ʈ ó��
			Button btnOK = (Button) signupRoot.lookup("#btnOK");
			Button btnCancel = (Button) signupRoot.lookup("#btnCancel");
			
			// ��� ��ư�� ��������
			btnCancel.setOnAction((e3) -> {
				stageDialog.close();
			});
			// ��� (����)��ư�� ��������
			 btnOK.setOnAction((e4) -> {
				try {
					
					 if(!editpassword.getText().equals(editpasswordcheck.getText())) {
						 throw new Exception("��й�ȣȮ�� ����ġ"); 
						 }
					 
					CustomerVO cvo = new CustomerVO(editId.getText(), editpassword.getText(),
							editName.getText(), editPhone.getText(), editEmail.getText());
					// ���̺�信 ������ ����..
						// DB�� �θ��� ���
					customerDAO = new CustomerDAO();
						// �����ͺ��̽� ���̺� �Է°��� �Է��ϴ� �Լ�
						int count = customerDAO.getCustomerregiste(cvo);
						if (count == 0) {
							throw new Exception("���̵� �ߺ�");
						}else {
							stageDialog.close();
						}
					
					// �˸�â ����
					alertDisplay(1, "ȸ�����Լ���", "�������ּż� �����մϴ�.", "��������");
					
				} catch (Exception e7) {
					// �˸�â ����
					alertDisplay(1, "ȸ�����Խ���", "ȸ����Ͻ���",e7.getMessage());
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


	//1. �α��ι�ư �̺�Ʈ ó��
	public void handlerBtnLoginAction(ActionEvent event) {
		
		try {
			CustomerDAO customerDAO = new CustomerDAO();
			LoginCustomerDAO loginCustomerDAO  = new LoginCustomerDAO();
			selectCustomer = customerDAO.getCustomerCheck(txtId.getText(),txtPassword.getText());
			System.out.println("�α��� ��Ʈ�ѷ� selectCustomer=" + selectCustomer);
			loginCustomerDAO.getLoginCustomerregiste(selectCustomer);
			if(txtId.getText().equals("manager") && txtPassword.getText().equals("1234") ) {
				//������ ���̵�� �α����� �� ��� ������ ����â���� �̵��Ѵ�. 
				Parent mainView=null;
				Stage mainStage=null;
//					mainView=FXMLLoader.load(getClass().getResource("/view/main.fxml"));
					mainView=FXMLLoader.load(getClass().getResource("/view/managerMain.fxml"));
					Scene scene = new Scene(mainView);
					mainStage = new Stage();
					mainStage.setTitle("manager Main");
					mainStage.setScene(scene);
					mainStage.setResizable(true);
					//������ ���������� �ݰ� ���ο�â�� ����. 
					((Stage) btnLogin.getScene().getWindow()).close();
					mainStage.show();
			}else if (selectCustomer == null) {
				throw new Exception("���̵�� ��й�ȣ�� Ȯ�����ּ���");
			}
			else {
				//Ŭ���̾�Ʈ ���̵�� �α����� �Ǿ��� selectDogâ���� �̵��Ѵ�. 
				Parent View=null;
				Stage Stage=null;
//					mainView=FXMLLoader.load(getClass().getResource("/view/main.fxml"));
					View=FXMLLoader.load(getClass().getResource("/view/myDog.fxml"));
					Scene scene = new Scene(View);
					Stage = new Stage();
					Stage.setTitle(txtId.getText()+"���� ������");
					Stage.setScene(scene);
					Stage.setResizable(true);
					//������ ���������� �ݰ� ���ο�â�� ����. 
					((Stage) btnLogin.getScene().getWindow()).close();
					Stage.show();
			}
			
		} catch (Exception e1) {
			DBUtil.alertDisplay(5, "�α���", "��������", e1.toString());
		}
	}
	//2. ��ư��� �̺�Ʈó��
	public void handlerBtnCancelAction(ActionEvent e) {
		((Stage) btnLogin.getScene().getWindow()).close();
	}
	// ���â ó���ϴ� �Լ�
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
