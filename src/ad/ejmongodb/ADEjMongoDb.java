/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ad.ejmongodb;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

/**
 *
 * @author a20armandocb
 */
public class ADEjMongoDb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        MongoClient mongoC = new MongoClient();
//        MongoClient mongoC = new MongoClient("localhost",27017);
//        MongoDatabase mongoDB = mongoC.getDatabase("mibasededatos");
//        DBCollection collection = (DBCollection) mongoDB.getCollection("trabajadores");

        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(), CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient mongoC = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        MongoDatabase db = mongoC.getDatabase("centroeducativo");

//        MongoCollection<Document> coleccion = db.getCollection("alumnos"); //obtenemos la coleccion
//
//        List<Document> consulta = coleccion.find().into(new ArrayList<Document>());
//        System.out.println("total: " + consulta.size());
//        for (int i = 0; i < consulta.size(); i++) {//mostramos los documentos
//            System.out.println(" ---- " + consulta.get(i).toString());
//        }
        MongoCursor<String> cursor = db.listCollectionNames().iterator();
        while (cursor.hasNext()) {
            String doc = cursor.next();
            System.out.println(doc);
        }

        MongoCollection<FutbolistaNew> collectionCodecRegistry = db.getCollection("futbolistas", FutbolistaNew.class);
        collectionCodecRegistry.drop();
        FutbolistaNew f01 = new FutbolistaNew("armando", "madarona", 25, new ArrayList<String>(Arrays.asList("uno","dos")), false, 160);
        FutbolistaNew f02 = new FutbolistaNew("Paco", "Castro", 85, new ArrayList<String>(Arrays.asList("uno","dos")), true, 160);
        
        collectionCodecRegistry.insertOne(f01);
        collectionCodecRegistry.insertOne(f02);
        
        
        FutbolistaNew f03 = new FutbolistaNew("Pedro", "Perez", 45, new ArrayList<String>(Arrays.asList("uno","dos")), true, 160);
        FutbolistaNew f04 = new FutbolistaNew("Roberto", "Merino", 65, new ArrayList<String>(Arrays.asList("uno","dos")),true, 160);
        FutbolistaNew f05 = new FutbolistaNew("Pica", "Piedra", 85, new ArrayList<String>(Arrays.asList("uno","dos")),false, 160);
        
        ArrayList<FutbolistaNew> listadoFutbolistas = new  ArrayList<FutbolistaNew>();
        
        listadoFutbolistas.add(f03);
        listadoFutbolistas.add(f04);        
        listadoFutbolistas.add(f05);        
        collectionCodecRegistry.insertMany(listadoFutbolistas);
        
        

        FutbolistaNew f12 = collectionCodecRegistry.find().first();
        System.out.println("primero: " + f12.toString());
        System.out.println("Borrando");
        collectionCodecRegistry.deleteOne(new Document("_id",f12.getId()));        
        f12 = collectionCodecRegistry.find().first();
        System.out.println("primero: " + f12.toString());
        
        System.out.println("modificando");
        f12.setApellidos("XXXXXXXXXXXXXX");
        
        Document findDocument1 = new Document("_id",f12.getId());     
        Document updateDocument1 = new Document("$set", new Document("apellidos", f12.getApellidos()));
        collectionCodecRegistry.updateOne(findDocument1,updateDocument1);    
        
        
            
//        ArrayList<FutbolistaNew> gente = collectionCodecRegistry.find().into(new ArrayList<FutbolistaNew>());
//        System.out.println("Cantidad: " + gente.size());
//        for(FutbolistaNew f: gente){
//            System.out.println(f.toString());
//        }

        MongoCursor<FutbolistaNew> cursor1 = collectionCodecRegistry.find(Filters.gt("edad", 80)).iterator();
        while(cursor1.hasNext()) {
            FutbolistaNew f = cursor1.next();
            System.out.println(f.toString());
        }

//        Document consulta1 = new Document("_id","").append("$count", "_id");
//        List <Document> lista2 = Arrays.asList(consulta1);
//        MongoCursor<FutbolistaNew> cursor2 = collectionCodecRegistry.aggregate(lista2).iterator();

          
          
          
          
        System.out.println("fin");
    }

}
