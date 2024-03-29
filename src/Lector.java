import Entidades.Athlete;
import Entidades.AthleteOlympicParticipation;
import Entidades.NationalOlympicCommittee;
import Entidades.OlympicGame;
import TADS.Hash.HashImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Lector {

    public ArrayList<Athlete> Atletas = new ArrayList(14000);
    public ArrayList<AthleteOlympicParticipation> Participaciones = new ArrayList(22000);
    public HashImpl<String,NationalOlympicCommittee> nationalOlympicCommittees = new HashImpl<>(250);
    public ArrayList<String> arrayComittees = new ArrayList<>(500);
    public ArrayList<OlympicGame>olympicGames=new ArrayList<>(14000);

    public Lector(){
    }

    public void leerArchivos(){
        String csvFile = "athlete_events.csv";
        BufferedReader br = null;
        BufferedReader br2 = null;
        String line = "";
        String line2 = "";
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
                datos[6]=datos[6].replaceAll("-\\d","");

                if (!ultimoID.equals(datos[0])) {
                    //teniendo en cuenta que los datos se descargar en forma ordenada respecto al ID
                    //podemos asegurarnos de no repetir la creacion de un atleta al crear un atleta cuando el ID es diferente al anterior
                    datos[1] = datos[1].replaceAll("#",", ").replaceAll("@",",-");//vuelve a agregarle la coma al nombre
                    ultimoID = datos[0];
                    ultimoAtleta = crearAtleta(datos);
                    Atletas.add(ultimoAtleta); //agrega el atleta al arraylist

                }
                AthleteOlympicParticipation participation = crearParticipacion(datos,ultimoAtleta);
                participation.setAthlete(ultimoAtleta);
                Participaciones.add(participation);
                ultimoAtleta.addParticipacion(participation);

            }

            br2 = new BufferedReader(new FileReader("noc_regions.csv"));
            br2.readLine();//se saltea la primer linea
            while ((line2 = br2.readLine()) != null) {
                String[] datos2 = line2.replaceAll(", ","#").split(csvSplitBy);//separa por lineas y elimina las comillas extra

                nationalOlympicCommittees.put(datos2[0],crearComittee(datos2));
                arrayComittees.add(datos2[0]);

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
        Athlete athlete = new Athlete(dato[0],dato[1],dato[2],dato[4],dato[5],dato[6],dato[7]);//La edad no se agrega porque varia
        return athlete;

    }

    public AthleteOlympicParticipation crearParticipacion(String[] dato, Athlete athlete){
        AthleteOlympicParticipation participacion = new AthleteOlympicParticipation(dato[14],athlete,dato[3],dato[9],dato[12],dato[8],dato[13],dato[10],dato[11]);
        return participacion;
    }

    public NationalOlympicCommittee crearComittee(String dato[]){
        if(dato.length == 3){
            NationalOlympicCommittee temp = new NationalOlympicCommittee(dato[0], dato[1], dato[2]);
            return temp;
        }
        else{
            NationalOlympicCommittee temp = new NationalOlympicCommittee(dato[0], dato[1], "");
            return temp;
        }

    }

}
