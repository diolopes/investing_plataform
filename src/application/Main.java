package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Scene mainScene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			ScrollPane scrollPane = loader.load();
			
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			
			mainScene = new Scene(scrollPane);
			mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Sample JavaFX application");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Scene getMainScene() {
		return mainScene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}




/* Instaciámnos um novo objeto loader do tipo FXMlLoader. Passamos o path da View.
Ao instaciarmos o FXMLOADER (line 16) vai ser importante para manipular a tela antes de carregar.
Depois chamo o loader.load() (line 17), para carregar a view.
Line 19 e 20 é para o tamanho do menubar estar adequado à janela.
Vai ser criado um objeto do tipo Scene (lene 2) que vai ser a minha cena principal. 
Vou instanciar a minha cena passando como argumento o objeto principal da view (parent line 17).
No primaryStage que vai ser o palco da minha cena que chegou instanciado para mim (line 14).
Eu vou setar (set) a cena dele como cena principal (line23) e vou definir um titulo para o meu palco (line24) e depois vou mostrar o palco (line25) */