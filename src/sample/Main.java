package sample;

import components.BtnCustomCell;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import models.Coneccion;
import models.ProductosDAO;

public class Main extends Application implements EventHandler<WindowEvent> {

    private Button btnNuevo;
    private TableView<ProductosDAO> tbvProductos;
    private ProductosDAO productosDAO;
    private Scene escena;
    private VBox vBox;
    private ImageView imageView;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Coneccion.getConection();

        productosDAO = new ProductosDAO();
        CrearUI();
        primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, this);
        primaryStage.setTitle("CRUD Producctos");
        primaryStage.setScene(escena);
        primaryStage.setMaximized(false);
        primaryStage.show();
    }

    private void CrearUI(){
        btnNuevo       = new Button("Nuevo Registro");
        tbvProductos   = new TableView<>();
        tbvProductos.setPrefHeight(650);
        vBox           = new VBox();
        vBox.setPrefSize(350, 500);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10.0));

        btnNuevo.setOnAction(event -> {
            new frmProductos(tbvProductos, null);
        });

        CrearTabla();
        vBox.getChildren().addAll(tbvProductos, btnNuevo);
        escena = new Scene(vBox,1085,700);
    }

    public void CrearTabla(){
        TableColumn<ProductosDAO, Integer>tbcIdProducto = new TableColumn<>("ID");
        tbcIdProducto.setCellValueFactory(new PropertyValueFactory<>("id_producto"));

        TableColumn<ProductosDAO, String>tbcNombre = new TableColumn<>("NOMBRE");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<ProductosDAO, String>tbcDescripcion = new TableColumn<>("Descripcion");
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<ProductosDAO, Integer>tbcExistencia = new TableColumn<>("EXISTENCIA");
        tbcExistencia.setCellValueFactory(new PropertyValueFactory<>("existencia"));

        TableColumn<ProductosDAO, Integer>tbcDisponible = new TableColumn<>("DISPONIBLE");
        tbcDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        TableColumn<ProductosDAO, ImageView> tbcImagen = new TableColumn<>("IMAGEN");
        tbcImagen.setCellValueFactory(new PropertyValueFactory<>("imageView"));


        TableColumn<ProductosDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<ProductosDAO, String>, TableCell<ProductosDAO, String>>() {
                    @Override
                    public TableCell<ProductosDAO, String> call(TableColumn<ProductosDAO, String> param) {
                        return new BtnCustomCell(1);
                    }
                }
        );

        TableColumn<ProductosDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<ProductosDAO, String>, TableCell<ProductosDAO, String>>() {
                    @Override
                    public TableCell<ProductosDAO, String> call(TableColumn<ProductosDAO, String> param) {
                        return new BtnCustomCell(2);
                    }
                }
        );

        tbvProductos.getColumns().addAll(tbcIdProducto, tbcNombre, tbcDescripcion, tbcExistencia, tbcDisponible, tbcImagen, tbcEditar, tbcEliminar);
        tbvProductos.setItems(productosDAO.SELECT());
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(WindowEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setHeaderText("Gracias por usar el programa :)");
        alerta.setContentText("Vuelva pronto");
        alerta.showAndWait();
    }
}
