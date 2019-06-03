import Entidades.Athlete;
import Entidades.AthleteOlympicParticipation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public void leerArchivos(){
        String csvFile = "athlete_events.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ","; //Se define separador ","
        String linea[] = new String[15];
        String ultimoID = "0";
        Athlete ultimoAtleta = null;

        try {

            br = new BufferedReader(new FileReader(csvFile));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] datos = line.replaceAll(", ","#").replaceAll(",-","@").replaceAll("\"","").split(csvSplitBy);//separa por lineas y elimina las comillas extra
                datos[13] = datos[13].replaceAll("#",", ").replaceAll("@",",-");//vuelve a agregarle la coma al evento

                if (!ultimoID.equals(datos[0])) {
                    //teniendo en cuenta que los datos se descargar en forma ordenada respecto al ID
                    //podemos asegurarnos de no repetir la creacion de un atleta al crear un atleta cuando el ID es diferente al anterior
                    datos[1] = datos[1].replaceAll("#",", ").replaceAll("@",",-");//vuelve a agregarle la coma al nombre
                    ultimoID = datos[0];
                    ultimoAtleta = crearAtleta(datos);
                }

                for(int i=0;i<15;i++) {
                    System.out.print(datos[i] + " ");
                }
                System.out.println();

                crearParticipacion(datos, ultimoAtleta);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null){
                try {
                    br.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Athlete crearAtleta(String[] dato){//tomando el string de datos retorna un atleta
        Athlete athlete = new Athlete(dato[0],dato[1],dato[2],dato[4],dato[5]);//La edad no se agrega porque varia
        return athlete;
    }
    public AthleteOlympicParticipation crearParticipacion(String[] dato, Athlete athlete){
        AthleteOlympicParticipation participacion = new AthleteOlympicParticipation(dato[14],athlete,dato[3]);
        return participacion;
    }

}