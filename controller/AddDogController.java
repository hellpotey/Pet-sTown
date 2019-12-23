package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.DogVO;
import model.LoginCustomerVO;
import javafx.stage.Stage;

public class AddDogController implements Initializable {
	@FXML
	private ImageView imageselect;
	@FXML
	private TextField txtdogname;
	@FXML
	private TextField txtbreed;
	@FXML
	private TextField txtage;
	@FXML
	private TextArea txtspecial;
	@FXML
	private RadioButton RBmale;
	@FXML
	private RadioButton RBfemale;
	@FXML
	private RadioButton RBYes;
	@FXML
	private RadioButton RBNo;
	@FXML
	private ToggleGroup dogGender;
	@FXML
	private ToggleGroup dogNeuter;
	@FXML
	private Button btnimages;
	@FXML
	private Button btnsave;
	@FXML
	private Button btncancle;
	private String selectFileName = ""; // �̹��� ���ϸ�
	private String localUrl = "";
	// �̹��� ���� ���
	private Image localImage;
	private File selectedFile = null;
	// �̹��� ó��
	// �̹��� ������ ������ �Ű������� ���� ��ü ����
	private File dirSave = new File("C:/images");
	// �̹��� �ҷ��� ������ ������ ���� ��ü ����
	LoginController loginController = new LoginController();
	private LoginCustomerVO selectCustomer;
	// �α��ο� �� �������Ҹ���Ʈ
	ObservableList<DogVO> data;
	private String fileName = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// �̹��� ���� ��ư
		btnimages.setOnAction(event -> {
			handlerBtnImagesAction(event);
		});
		// �����ư
		btnsave.setOnAction(event -> {
			handlerBtnSaveAction(event);
		});
		// ��� ��ư
		btncancle.setOnAction(event -> {
			handlerBtnCancle(event);
		});
	}

	// ���
	private void handlerBtnCancle(ActionEvent event) {
		((Stage) btncancle.getScene().getWindow()).close();
		comemydog();
	}

	// imageSave() �̹��� ���� �޼ҵ� ������ �����ͼ� -> ���ϸ��� ���� �ۼ��ϰ�
	// ->inputstream�� outputstream�� ���ؼ� ������ �����Ѵ�. * @param (�о�� * ���� ��ü)

	//���� ��ư �׼�
	private void handlerBtnSaveAction(ActionEvent event) {

		String gender = null;
		String neuter = null;
		LoginCustomerDAO loginCustomerDAO = new LoginCustomerDAO();


		if (RBmale.isSelected()) {
			gender = "����";

		} else {
			gender = "����";
		}
		if (RBYes.isSelected()) {
			neuter = "��";
		} else {
			neuter = "��";
		}
		System.out.println("selectCustomer=   " + loginCustomerDAO.getLoginCustomer());
		selectCustomer = loginCustomerDAO.getLoginCustomer();
		DogVO newdogVO = new DogVO(selectCustomer.getId(), txtdogname.getText(), txtbreed.getText(), gender,
				txtage.getText(), neuter, txtspecial.getText(), localUrl);

		System.out.println("localUrl =     " + localUrl);
		System.out.println("newdogVO= " + newdogVO);
		// ���̺�信 ������ ����..
		// DB�� �θ��� ���
		DogDAO dogDAO = new DogDAO();
		// �����ͺ��̽� ���̺� �Է°��� �Է��ϴ� �Լ�
		int count;
		try {
			count = dogDAO.getDogregiste(newdogVO);
			if (count == 0) {
				throw new Exception("�� ���� ���� ");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((Stage) btncancle.getScene().getWindow()).close();
		comemydog();
	}



	// �̹��� ���� ��ư�׼�
	private void handlerBtnImagesAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(btnsave.getScene().getWindow());
			System.out.println("selectedFile=       " + selectedFile);

			if (selectedFile != null) { // �̹��� ���� ���
				localUrl = selectedFile.toURI().toURL().toString();
			}
			System.out.println("localUrl=       " + localUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		localImage = new Image(localUrl, false);
		imageselect.setImage(localImage);
		imageselect.setFitHeight(200);
		imageselect.setFitWidth(150);
		btnsave.setDisable(false);

		if (selectedFile != null) {
			selectFileName = selectedFile.getName();
		}
	}

	public void alertDisplay(int type, String title, String headerText, String contentText) {

		Alert alert = null;
		switch (type) {
		case 1:
			alert = new Alert(AlertType.WARNING);
			break;
		case 2:
			alert = new Alert(AlertType.CONFIRMATION);
			break;
		case 3:
			alert = new Alert(AlertType.ERROR);
			break;
		case 4:
			alert = new Alert(AlertType.NONE);
			break;
		case 5:
			alert = new Alert(AlertType.INFORMATION);
			break;
		}
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(headerText + "\n" + contentText);
		alert.setResizable(false);
		alert.showAndWait();
	}

	public void comemydog() {
		Parent addDogController = null;
		Stage addDogStage = null;
		try {
			addDogController = FXMLLoader.load(getClass().getResource("/view/myDog.fxml"));
			Scene scene = new Scene(addDogController);
			addDogStage = new Stage();

			addDogStage.setTitle("myDog Window");
			addDogStage.setScene(scene);
			addDogStage.setResizable(false);
			addDogStage.show();
		} catch (IOException e11) {
			System.out.println("myDog View call error " + e11);
		}
	}
}
