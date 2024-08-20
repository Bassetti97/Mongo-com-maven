package org.fundatec;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

public class MongoDBIntegration {

    public static void main(String[] args) {

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("btsShows");

        MongoCollection<Document> collection = database.getCollection("shows");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("----------------------------------------------------------------------");
            System.out.println("ESCOLHA UMA OPÇÃO:");
            System.out.println("----------------------------------------------------------------------");
            System.out.println("[1] Inserir");
            System.out.println("[2] Atualizar");
            System.out.println("[3] Deletar");
            System.out.println("[4] Listar");
            System.out.println("[5] Sair");
            System.out.println("----------------------------------------------------------------------");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Digite o show_id:");
                    int showIdInsert = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Digite a localização:");
                    String locationInsert = scanner.nextLine();
                    System.out.println("Digite a data (yyyy-mm-dd):");
                    String dateInsert = scanner.nextLine();
                    System.out.println("Digite o número de ingressos disponíveis:");
                    int availableTicketsInsert = scanner.nextInt();
                    scanner.nextLine();

                    Document newShow = new Document("show_id", showIdInsert)
                            .append("location", locationInsert)
                            .append("date", dateInsert)
                            .append("available_tickets", availableTicketsInsert);
                    collection.insertOne(newShow);
                    System.out.println("Documento inserido com sucesso!");
                    break;

                case 2:
                    System.out.println("Digite o show_id do documento a ser atualizado:");
                    int showIdUpdate = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Digite o novo número de ingressos disponíveis:");
                    int availableTicketsUpdate = scanner.nextInt();
                    scanner.nextLine();

                    collection.updateOne(
                            new Document("show_id", showIdUpdate),
                            new Document("$set", new Document("available_tickets", availableTicketsUpdate))
                    );
                    System.out.println("Documento atualizado com sucesso!");
                    break;

                case 3:
                    System.out.println("Digite o show_id do documento a ser deletado:");
                    int showIdDelete = scanner.nextInt();
                    scanner.nextLine();

                    collection.deleteOne(new Document("show_id", showIdDelete));
                    System.out.println("Documento deletado com sucesso!");
                    break;

                case 4:
                    for (Document doc : collection.find()) {
                        System.out.println(doc.toJson());
                    }
                    break;

                case 5:
                    System.out.println("Tchau!");
                    mongoClient.close();
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        }

    }
}
