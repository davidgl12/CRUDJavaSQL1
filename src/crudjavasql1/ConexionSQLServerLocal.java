/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crudjavasql1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class ConexionSQLServerLocal {
    //Explicación rapidísima para David del futuro o cualquiera que vea esto:
    
    /*JDBC es una API que permite conectarse con una base de datos en SQL
    sever, el driver se debe descargar de aquí:
    https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver15
    y una vez hecho esto, se coloca el jar en el proyecto (click derecho en la
    carpetita de libraries y add jar si estás MUY perdido) o se unzipea en program files
    como dice en las notas de instalación, checa, a lo mejor ya lo tienes.
    
    la URL es MUUY importante y es lo más tedioso aquí vienen puntos importantes:
    1. "jdbc:sqlserver:" se pone siempre
    2. "localhost:1433;" es para bases de datos locales.
        Si esto empieza a dar problemas o no se puede conectar has esto:
        1) Abre el SQL Server Configuration Manager
        2) Network configuration - protocols - TCP/IP - properties
        3) En pestaña "Protocol", ponle "Yes" a Enabled
        4) Has lo mismo para todas las ip addresses en la otra pestaña
           y en su dynamic port ponlo en 1433
    3. "databaseName=NombreDeBD" obviamente pon el nombre de tu base de datos
    4. "integratedSecurity=true" es una propiedad para autenticar automáticamente
        la conexión si es que es una BD local, en caso de ser externa esto se
        reemplaza por un usuario y contraseña de SQL server, checar más en link 1.
        Si esto empieza a dar problemas has esto:
        1) Ve a tu driver de JDBC, y busca en auth un .dll que se llama algo así:
           "mssql-jdbc_auth-9.2.1.x64.dll"
        2) Copia eso y ponlo en System 32, no tengas miedo jaja.
    
    Ejemplo de URL para una BD llamada PRODUCTOS:
        "jdbc:sqlserver://localhost:1433;databaseName=PRODUCTOS;integratedSecurity=true;"
    
    Algunos links buenos, si necesitas más ayuda ahí está Google jaja:
    Link 1: 
    https://www.codejava.net/java-se/jdbc/connect-to-microsoft-sql-server-via-jdbc
    Link 2: 
    https://docs.microsoft.com/en-us/previous-versions/sql/legacy/ms378672(v=sql.90)
    documentación de librería java.sql:
    https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html
    Ejemplo sencillo de queries y demás:
    https://docs.microsoft.com/en-us/sql/connect/jdbc/step-3-proof-of-concept-connecting-to-sql-using-java?view=sql-server-ver15
    
    Código original que si funcionó para usar de ejemplo:
        
        String URL = "jdbc:sqlserver://localhost:1433;databaseName=PRODUCTOS;integratedSecurity=true;";
        
        ResultSet resultSet;
        
        try (Connection con = DriverManager.getConnection(URL);
                Statement statement = con.createStatement();){
            
            //Crear y ejecutar query
            String query = "SELECT *FROM CATALOGO";
            resultSet = statement.executeQuery(query);
            
            while(resultSet.next()){
                System.out.println(resultSet.getString(1) + "" + resultSet.getString(2)+ "" + resultSet.getString(3));
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    
    Buena suerte c:
    */
    
    private String NombreDeBD; //Nombre de la Base de datos a la cual se quiere conectar
    private Connection con;
    
    public ConexionSQLServerLocal(String NombreDeBD){
        this.NombreDeBD = NombreDeBD;
    }
    
    /*Método para hacer un select, regresa un arreglo dinámico que contiene
      arreglos de Strings, cada arreglo de strings representa una tupla de la BD*/
    public ArrayList<String[]> select(String SQLQuery){
            ArrayList<String[]> tabla = new ArrayList<>();
            
            String URL = "jdbc:sqlserver://localhost:1433;databaseName="+ NombreDeBD + ";integratedSecurity=true;";
        
            ResultSet resultSet = null;
            
                try (Connection con = DriverManager.getConnection(URL);
                    Statement statement = con.createStatement();){

                    //Crear y ejecutar query
                    resultSet = statement.executeQuery(SQLQuery);
                    
                    int numeroCol = resultSet.getMetaData().getColumnCount();
                    
                    while(resultSet.next()){
                        String[] arreglo = new String[numeroCol];
                        for(int i = 0; i<numeroCol; i++){
                            arreglo[i] = resultSet.getString(i+1);
                        }
                        tabla.add(arreglo);
                    }
                    
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
                
            return tabla;    
    }
    
    //Solo ejecuta la query, no regresa nada
    // (Update, create, delete etc.)
    public void ejecutarSQL(String SQLQuery){
        String URL = "jdbc:sqlserver://localhost:1433;databaseName="+ NombreDeBD + ";integratedSecurity=true;";
        
            ResultSet resultSet = null;
            
                try (Connection con = DriverManager.getConnection(URL);
                    Statement statement = con.createStatement();){

                    //Ejecutar Query
                    statement.execute(SQLQuery);
                    
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
    }
}
