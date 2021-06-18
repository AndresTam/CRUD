package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductosDAO {
    private int id_producto;
    private String nombre;
    private String descripcion;
    private Double existencia;
    private String disponible;
    private byte[] imagen;
    private File file;
    private ImageView imageView;

    public int getId_producto() {
        return id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getExistencia() {
        return existencia;
    }

    public void setExistencia(Double existencia) {
        this.existencia = existencia;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public void setFile(File file){ this.file = file; }

    public ImageView getImageView() { return imageView; }

    public void INSERT(){
        try{
            FileInputStream fis = new FileInputStream(file);
            PreparedStatement ps = Coneccion.conn.prepareStatement("INSERT INTO tbl_productos (nombre, descripcion, existencia, disponible, imagen)" +
                    "VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setDouble(3, existencia);
            ps.setString(4, disponible);
            ps.setBinaryStream(5, fis, (int)file.length());
            ps.executeUpdate();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void UPDATE(){
        try{
            FileInputStream fis = new FileInputStream(file);
            PreparedStatement ps = Coneccion.conn.prepareStatement("UPDATE tbl_productos SET nombre=?, descripcion=?, existencia=?,disponible=?, imagen=?" +
                    "WHERE id_producto ="+id_producto);
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setDouble(3, existencia);
            ps.setString(4, disponible);
            ps.setBinaryStream(5, fis, (int)file.length());
            ps.executeUpdate();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void DELETE(){
        try{
            String query = "DELETE FROM tbl_productos WHERE id_producto="+ id_producto;
            Statement stmt = Coneccion.conn.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public ObservableList<ProductosDAO> SELECT(){
        ObservableList<ProductosDAO> listaP = FXCollections.observableArrayList();
        try{
            ProductosDAO objP;
            String query = "SELECT * FROM tbl_productos";
            Statement stmt = Coneccion.conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                objP = new ProductosDAO();
                objP.id_producto = res.getInt("id_producto");
                objP.nombre      = res.getString("nombre");
                objP.descripcion = res.getString("descripcion");
                objP.existencia  = res.getDouble("existencia");
                objP.disponible  = res.getString("disponible");

                InputStream is   = res.getBinaryStream("imagen");
                OutputStream os = new FileOutputStream( new File("photo.jpg"));
                byte[] content = new byte[1024];
                int size = 0;
                while((size = is.read(content)) != -1){
                    os.write(content, 0 ,size);
                }
                os.close();
                is.close();
                Image img = new Image("file:photo.jpg", 150,150,true,true);

                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(150);
                imgView.setFitWidth(150);
                imgView.setPreserveRatio(true);
                objP.imageView = imgView;

                listaP.add(objP);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return listaP;
    }
}
