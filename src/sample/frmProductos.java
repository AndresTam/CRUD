package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ProductosDAO;

import java.io.*;

public class frmProductos extends Stage {
    private Label lblNombre, lblDescripcion, lblExistencia, lblDisponible, lblImagen;
    private TextField txtNombre, txtExistencia, txtDisponible;
    private TableView<ProductosDAO> tbvProductos;
    private TextArea txtDescripcion, txtImagen;
    private FileInputStream fileInputStream;
    private Button btnGuardar, btnImagen;
    private FileChooser fileChooser;
    private ImageView imageView;
    private ProductosDAO objDAO;
    private Scene escena;
    private Image image;
    private File file;
    private VBox vBox;

    public frmProductos(TableView<ProductosDAO> tbvProductos, ProductosDAO objDAO){
        this.tbvProductos = tbvProductos;
        if(objDAO != null) {
            this.objDAO = objDAO;
        } else{
            this.objDAO = new ProductosDAO();
        }
        CrearUI();
        this.setTitle("Registro de Producto");
        this.setScene(escena);
        this.show();
    }

    public void CrearUI(){
        txtNombre      = new TextField();
        txtNombre.setText(objDAO.getNombre());
        txtDescripcion = new TextArea();
        txtDescripcion.setText(objDAO.getDescripcion());
        txtDescripcion.setPrefHeight(200.0);
        txtExistencia  = new TextField();
        txtExistencia.setText(String.valueOf(objDAO.getExistencia()));
        txtDisponible  = new TextField();
        txtDisponible.setText(objDAO.getDisponible());
        txtImagen      = new TextArea();
        txtImagen.setPromptText("Ruta de la imagen");
        txtImagen.setPrefHeight(200.0);
        txtImagen.setEditable(false);
        lblNombre      = new Label("Nombre");
        lblDescripcion = new Label("Descripción");
        lblExistencia  = new Label("Existencia");
        lblDisponible  = new Label("Disponibilidad");
        lblImagen      = new Label("Imagen");
        btnGuardar     = new Button("Guardar");
        btnImagen      = new Button("Buscar Imagen");
        fileChooser    = new FileChooser();
        imageView      = new ImageView();
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);
        vBox           = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        btnImagen.setOnAction(event -> {
            file = fileChooser.showOpenDialog(this);
            if(file != null){
                txtImagen.setText(file.getAbsolutePath());
                image = new Image(file.toURI().toString(), 150, 150, true, true);
                imageView.setImage(image);
                imageView.setFitHeight(150);
                imageView.setFitWidth(150);
                imageView.setPreserveRatio(true);
            }
        });

        btnGuardar.setOnAction(event -> {

            objDAO.setNombre(txtNombre.getText());
            objDAO.setDescripcion(txtDescripcion.getText());
            objDAO.setExistencia(Double.parseDouble(txtExistencia.getText()));
            objDAO.setDisponible(txtDisponible.getText());
            objDAO.setFile(file);
            if(objDAO.getId_producto() > 0){
                objDAO.UPDATE();
            } else{
                objDAO.INSERT();
            }

            tbvProductos.setItems(objDAO.SELECT());
            tbvProductos.refresh();
            this.close();
        });

        vBox.getChildren().addAll(lblNombre, txtNombre, lblDescripcion, txtDescripcion, lblExistencia, txtExistencia, lblDisponible, txtDisponible, lblImagen, btnImagen, txtImagen, imageView, btnGuardar);
        escena = new Scene(vBox,350, 900);
    }
}
