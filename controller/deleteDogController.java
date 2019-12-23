package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DogVO;
import model.LoginCustomerVO;

public class deleteDogController implements Initializable {
	@FXML
	private TableView deleteTb;
	@FXML
	private Button btndelete;
	@FXML
	private Button btnback;

	// 고객정보 가져오기
	private DogDAO dogDAO = new DogDAO();
	private LoginCustomerDAO loginCustomerDAO = new LoginCustomerDAO();
	private LoginCustomerVO selectCustomer = loginCustomerDAO.getLoginCustomer();
	private ArrayList<DogVO> dogList = dogDAO.getLoginCustomerDogList(selectCustomer.getId());
	private ObservableList<DogVO> selectDog;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TableColumn coldogname = new TableColumn("강아지 이름");
		coldogname.setMaxWidth(100);
		coldogname.setStyle("-fx-alignment: CENTER;");
		coldogname.setCellValueFactory(new PropertyValueFactory<>("dogname"));

		TableColumn colbreed = new TableColumn("견종");
		colbreed.setMaxWidth(150);
		colbreed.setStyle("-fx-alignment: CENTER;");
		colbreed.setCellValueFactory(new PropertyValueFactory<>("breed"));

		TableColumn colgender = new TableColumn("성별");
		colgender.setMaxWidth(60);
		colgender.setStyle("-fx-alignment: CENTER;");
		colgender.setCellValueFactory(new PropertyValueFactory<>("gender"));

		TableColumn colage = new TableColumn("나이");
		colage.setMaxWidth(150);
		colage.setStyle("-fx-alignment: CENTER;");
		colage.setCellValueFactory(new PropertyValueFactory<>("age"));

		// 테이블 설정. 컬럼들의 객체를 테이블뷰에 리스트추가 및 항목 추가
		ObservableList<DogVO> data = FXCollections.observableArrayList();
		deleteTb.getColumns().addAll(coldogname, colbreed, colgender, colage);
		if (dogList == null) {
			DBUtil.alertDisplay(1, "경고", "개가없음", "없는데 어떻게 삭제해요");
			return;
		}
		for (DogVO dog : dogList) {
			System.out.println("d =  " + dog);
			data.add(dog);
		}
		deleteTb.setItems(data);
		// 삭제버튼
		btndelete.setOnAction(e1 -> {
			selectDog = deleteTb.getSelectionModel().getSelectedItems();
			String selectDogId = selectDog.get(0).getId();
			String selectDogdogname = selectDog.get(0).getDogname();
			dogDAO.getDVODelete(selectDogId, selectDogdogname);
			((Stage) btnback.getScene().getWindow()).close();
			// myDogfxml메인창 불러오는 함수
			comemydog();

		});
		// 뒤로버튼
		btnback.setOnAction(e -> {
			((Stage) btnback.getScene().getWindow()).close();
			comemydog();
		});
	}

	public void comemydog() {
		Parent addDogController = null;
		Stage addDogStage = null;
		try {
			addDogController = FXMLLoader.load(getClass().getResource("/view/myDog.fxml"));
			Scene scene = new Scene(addDogController);
			addDogStage = new Stage();

			addDogStage.setTitle(selectCustomer.getName()+"님의 강아지");
			addDogStage.setScene(scene);
			addDogStage.setResizable(false);
			addDogStage.show();
		} catch (IOException e11) {
			System.out.println("myDog View call error " + e11);
		}
	}

}
