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

	// ������ ��������
	private DogDAO dogDAO = new DogDAO();
	private LoginCustomerDAO loginCustomerDAO = new LoginCustomerDAO();
	private LoginCustomerVO selectCustomer = loginCustomerDAO.getLoginCustomer();
	private ArrayList<DogVO> dogList = dogDAO.getLoginCustomerDogList(selectCustomer.getId());
	private ObservableList<DogVO> selectDog;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TableColumn coldogname = new TableColumn("������ �̸�");
		coldogname.setMaxWidth(100);
		coldogname.setStyle("-fx-alignment: CENTER;");
		coldogname.setCellValueFactory(new PropertyValueFactory<>("dogname"));

		TableColumn colbreed = new TableColumn("����");
		colbreed.setMaxWidth(150);
		colbreed.setStyle("-fx-alignment: CENTER;");
		colbreed.setCellValueFactory(new PropertyValueFactory<>("breed"));

		TableColumn colgender = new TableColumn("����");
		colgender.setMaxWidth(60);
		colgender.setStyle("-fx-alignment: CENTER;");
		colgender.setCellValueFactory(new PropertyValueFactory<>("gender"));

		TableColumn colage = new TableColumn("����");
		colage.setMaxWidth(150);
		colage.setStyle("-fx-alignment: CENTER;");
		colage.setCellValueFactory(new PropertyValueFactory<>("age"));

		// ���̺� ����. �÷����� ��ü�� ���̺�信 ����Ʈ�߰� �� �׸� �߰�
		ObservableList<DogVO> data = FXCollections.observableArrayList();
		deleteTb.getColumns().addAll(coldogname, colbreed, colgender, colage);
		if (dogList == null) {
			DBUtil.alertDisplay(1, "���", "��������", "���µ� ��� �����ؿ�");
			return;
		}
		for (DogVO dog : dogList) {
			System.out.println("d =  " + dog);
			data.add(dog);
		}
		deleteTb.setItems(data);
		// ������ư
		btndelete.setOnAction(e1 -> {
			selectDog = deleteTb.getSelectionModel().getSelectedItems();
			String selectDogId = selectDog.get(0).getId();
			String selectDogdogname = selectDog.get(0).getDogname();
			dogDAO.getDVODelete(selectDogId, selectDogdogname);
			((Stage) btnback.getScene().getWindow()).close();
			// myDogfxml����â �ҷ����� �Լ�
			comemydog();

		});
		// �ڷι�ư
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

			addDogStage.setTitle(selectCustomer.getName()+"���� ������");
			addDogStage.setScene(scene);
			addDogStage.setResizable(false);
			addDogStage.show();
		} catch (IOException e11) {
			System.out.println("myDog View call error " + e11);
		}
	}

}
