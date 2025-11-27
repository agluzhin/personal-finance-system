package agluzhin.personal_finance_system.core.repositories;

import agluzhin.personal_finance_system.core.entities.wallet.Wallet;
import agluzhin.personal_finance_system.core.entities.user.User;

import jakarta.annotation.PreDestroy;
import org.slf4j.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Данный класс содержит всю информацию о пользователях, кошельках и категориях (доходов/расходов).
 * <p>Используется в качестве упрощенной альтернативы БД.</p>
 */
@Component
public class InMemoryDataStorage {
    // Логгер, используемый для вывода в консоль информации о состоянии приложения.
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryDataStorage.class);
    // Постоянные файловые пути хранения информации (директория/data-файл).
    private static final String DATA_DIRECTORY_PATH = "src/main/java/agluzhin/personal_finance_system/core/repositories";
    private static final Path DATA_FILE_PATH = Paths.get(DATA_DIRECTORY_PATH).resolve("data.json");

    // Коллекции для хранения информации о пользователях, кошельках и категориях (доходов/расходов).
    // Структуры коллекций: "id : Object".
    public static HashMap<String, User> users = new HashMap<>();
    public static HashMap<String, Wallet> wallets = new HashMap<>();

    /**
     * Конструктор хранилища информации (тип: InMemory).
     * <p>Проверяет наличие data-файла и в случае успешной проверки - загружает информацию из файла.</p>
     */
//    public InMemoryDataStorage() {
//        if (Files.exists(DATA_FILE_PATH)) {
//            loadFromDataFile();
//        } else {
//            createDataFile();
//        }
//    }

    /**
     * Записывает информацию из data-файла в соответствующие коллекции хранилища.
     */
//    private void loadFromDataFile() {
//        TODO: реализовать загрузку данных из JSON файла.
//        try (JsonReader reader = new JsonReader(new FileReader(DATA_FILE_PATH.toString()))) {
//            Gson gson = new Gson();
//            JsonObject root = gson.fromJson(reader, JsonObject.class);
//            String[] jsonObjectBlocks = {"users", "wallets", "categories"};
//            for (String block : jsonObjectBlocks) {
//                JsonArray blockData = root.getAsJsonArray(block);
//                if (block.equals("users")) {
//
//                }
//                if (block.equals("wallets")) {
//
//                }
//            }
//        } catch (IOException ex) {
//            LOG.error("Failed to load info from data-file: {}", ex.getMessage());
//        }
//    }

    private void createDataFile() {
        try {
            Files.createFile(DATA_FILE_PATH);
            LOG.info("Data file created at {}", DATA_FILE_PATH);
        } catch (IOException ex) {
            LOG.error("Failed to create data-file: {}", ex.getMessage());
        }
    }

//    private void loadUsers(JsonArray data) {
//        TODO: реализовать выгрузку из JSON файла данных по пользователям.
//        for (var user : data) {
//            JsonObject userData = user.getAsJsonObject();
//            users.put(
//                    userData.get("userId").getAsString(),
//                    new User(
//                            userData.get("userId").getAsString(),
//                            userData.get("login").getAsString(),
//                            userData.get("password").getAsString(),
//                            userData.get("isActive").getAsBoolean(),
//                            userData.get("isAuthorized").getAsBoolean(),
//                            userData.get("walletId").getAsString()
//                    ));
//        }
//    }

//    private void loadWallets(JsonArray data) {
//        TODO: реализовать выгрузку из JSON файла данных по кошелькам.
//        for (var wallet : data) {
//            JsonObject walletData = wallet.getAsJsonObject();
//            Map<String, Map<String, BigDecimal>> categories = new HashMap<>();
//            Map<String, BigDecimal> budget = new HashMap<>();
//
//            wallets.put(
//                    walletData.get("walletId").getAsString(),
//                    new Wallet(
//                            walletData.get("walletId").getAsString(),
//                            categories,
//                            budget
//                    ));
//        }
//    }

    private void unauthorizeUsers() {
        users.forEach((id, user) -> user.setIsAuthorized(false));
        LOG.info("ALL USERS WAS UNAUTHORIZED");
    }

    private void loadDataToFile() {
        // TODO: реализовать загрузку users и wallets в JSON файл (с помощью GSON lib).
    }

    @PreDestroy
    private void sessionPreDestroy() {
        unauthorizeUsers();
    }
}
