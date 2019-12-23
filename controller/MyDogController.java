package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DogVO;
import model.LoginCustomerVO;

public class MyDogController implements Initializable {
	@FXML
	private Label lbtext;
	@FXML
	private Button btnadd;
	@FXML
	private Button btnreference;
	@FXML
	private Button btndelete;
	@FXML
	private Button btnexit;
	@FXML
	private Button btndog1;
	@FXML
	private Button btndog2;
	@FXML
	private Button btndog3;
	@FXML
	private Button btndog4;
	@FXML
	private Button btndog5;
	@FXML
	private Button btndog6;
	@FXML
	private ImageView imagedog1;
	@FXML
	private ImageView imagedog2;
	@FXML
	private ImageView imagedog3;
	@FXML
	private ImageView imagedog4;
	@FXML
	private ImageView imagedog5;
	@FXML
	private ImageView imagedog6;
	//
	
	//
	
	
	//로그인한 고객정보 , 강아지리스트 가져오기 위함
	private DogDAO dogDAO = new DogDAO();
	private LoginCustomerDAO loginCustomerDAO = new LoginCustomerDAO();
	private LoginCustomerVO selectCustomer = loginCustomerDAO.getLoginCustomer();
	private ArrayList<DogVO> dogList = dogDAO.getLoginCustomerDogList(selectCustomer.getId());
	//
	//로그인한 개 정보
	private loginDogDAO loginDogDAO = new loginDogDAO();
	// 로그인에 쓸 옵저버불리스트
	private ArrayList<DogVO> data = new ArrayList<DogVO>();
	private ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();
	//
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 로그인 했을 때 버튼에 이미지 셋팅
		imagebtListSetting();
		btnadd.setOnAction(event -> {
			hanblerBtnOpneWindowAction(event,"AddDog");
		});
		btnexit.setOnAction(event -> {
			hadlerBtnCancleAction(event);
		});
		btndelete.setOnAction(event -> {
			hanblerBtnOpneWindowAction(event,"deleteDog");
		});
		btndog1.setOnAction(event -> {

			loginDogDAO.getSelectDogregiste(dogList.get(0));
			hanblerBtnOpneWindowAction(event,"hotelReservation");
			
		});
		btndog2.setOnAction(event -> {
			loginDogDAO.getSelectDogregiste(dogList.get(1));
			hanblerBtnOpneWindowAction(event,"hotelReservation");
		});
		btndog3.setOnAction(event -> {
			loginDogDAO.getSelectDogregiste(dogList.get(2));
			hanblerBtnOpneWindowAction(event,"hotelReservation");
		});
		btndog4.setOnAction(event -> {
			loginDogDAO.getSelectDogregiste(dogList.get(3));
			hanblerBtnOpneWindowAction(event,"hotelReservation");
		});
		btndog5.setOnAction(event -> {
			loginDogDAO.getSelectDogregiste(dogList.get(4));
			hanblerBtnOpneWindowAction(event,"hotelReservation");
		});
		btndog6.setOnAction(event -> {
			loginDogDAO.getSelectDogregiste(dogList.get(5));
			hanblerBtnOpneWindowAction(event,"hotelReservation");
		});
	}
		
	private void imagebtListSetting() {
		lbtext.setText(selectCustomer.getName()+"님의 강아지");
		imageViewList.add(imagedog1);
		imageViewList.add(imagedog2);
		imageViewList.add(imagedog3);
		imageViewList.add(imagedog4);
		imageViewList.add(imagedog5);
		imageViewList.add(imagedog6);

		for (int i = 0; i < dogList.size(); i++) {
			String string = dogList.get(i).getLocalurl();
			System.out.println("dogList.get(" + i + ")=          " + dogList.get(i));
			if (dogList.get(i).getLocalurl().equals("")) {
				System.out.println("dogList.get(i).getLocalurl()=    " + string);
				Image dogimage = new Image("file:/C:/Users/alfo5-28/Desktop/noimg.jpg", false);
				imageViewList.get(i).setImage(dogimage);
			} else {
				try {
					Image dogimage = new Image(string, false);
					imageViewList.get(i).setImage(dogimage);
				} catch (Exception e) {
					System.out.println("사진넣는 에러 eee=                    " + e);
				}
			}
		}

	}

	public void hanblerBtnOpneWindowAction(ActionEvent event, String fxml) {
		Parent addDogController = null;
		Stage addDogStage = null;
		try {
			addDogController = FXMLLoader.load(getClass().getResource("/view/"+fxml+".fxml"));
			Scene scene = new Scene(addDogController);
			addDogStage = new Stage();

			addDogStage.setTitle(selectCustomer.getName()+"님의 강아지");
			addDogStage.setScene(scene);
			addDogStage.setResizable(false);
			addDogStage.show();
		} catch (IOException e) {
			System.out.println(fxml+" View call error " + e);
		}
		((Stage) btnexit.getScene().getWindow()).close();
	}

	private void hadlerBtnCancleAction(ActionEvent event) {
		((Stage) btnexit.getScene().getWindow()).close();
	}
}
