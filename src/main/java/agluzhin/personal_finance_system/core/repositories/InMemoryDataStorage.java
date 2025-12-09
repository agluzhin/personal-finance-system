package agluzhin.personal_finance_system.core.repositories;

import agluzhin.personal_finance_system.core.entities.wallet.Wallet;
import agluzhin.personal_finance_system.core.entities.user.User;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.PreDestroy;
import org.slf4j.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Данный класс содержит всю информацию о коллекциях сущностей типа "User" и "Wallet".
 * Используется в качестве упрощенной альтернативы БД.
 */
@Component
public class InMemoryDataStorage {
    // Логгер, используемый для вывода в консоль информации о работе класса "InMemoryDataStorage".
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryDataStorage.class);

    // Постоянный файловый путь хранения информации - директория.
    private static final String DATA_DIRECTORY_PATH = "src/main/java/agluzhin/personal_finance_system/core/repositories";

    // Постоянный файловый путь хранения информации - json-файл с данными.
    private static final Path DATA_FILE_PATH = Paths.get(DATA_DIRECTORY_PATH).resolve("data.json");

    // Коллекция для хранения информации о "пользователях" со структурой: "id : User".
    public static HashMap<String, User> users = new HashMap<>();

    // Коллекция для хранения информации о "кошельках" со структурой: "id : Wallet".
    public static HashMap<String, Wallet> wallets = new HashMap<>();

    /**
     * Конструктор хранилища информации "InMemoryDataStorage".
     * Проверяет наличие JSON файла с данными, в случае успеха - загружает информацию из него.
     * @throws IOException исключение, связанное с любой проблемой записи/чтения данных в/из файл-а.
     */
    public InMemoryDataStorage() throws IOException {
        if (Files.exists(DATA_FILE_PATH)) {
            LOG.info(" ===== LOADING DATA FROM JSON FILE ===== ");
            loadDataFromFile();
            LOG.info("SUCCESS: all data was loaded from JSON data file");
        } else {
            LOG.info(" ===== CREATING JSON FILE ===== ");
            createDataFile();
            LOG.info("SUCCESS: JSON data file created");
        }
    }

    /**
     * Метод создания JSON файла для дальнейшей загрузки/выгрузки данных по "пользователям" и "кошелькам".
     * @throws IOException исключение, связанное с любой проблемой записи/чтения данных в/из файл-а.
     */
    private void createDataFile() throws IOException {
        try {
            Files.createFile(DATA_FILE_PATH);
        } catch (IOException ex) {
            throw new IOException("failed to create JSON data file");
        }
    }

    /**
     * Метод загрузки данных по "пользователям" и "кошелькам" из JSON файла.
     * @throws IOException исключение, связанное с любой проблемой записи/чтения данных в/из файл-а.
     */
    private void loadDataFromFile() throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(DATA_FILE_PATH.toString())) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            Type userMapType = new TypeToken<HashMap<String, User>>() {
            }.getType();
            Type walletMapType = new TypeToken<HashMap<String, Wallet>>() {
            }.getType();
            users = gson.fromJson(jsonObject.get("users"), userMapType);
            wallets = gson.fromJson(jsonObject.get("wallets"), walletMapType);
        } catch (IOException e) {
            throw new IOException("failed to load data from JSON file");
        }
    }


    /**
     * Метод загрузки данных по "пользователям" и "кошелькам" в JSON файл.
     * @throws IOException исключение, связанное с любой проблемой записи/чтения данных в/из файл-а.
     */
    private void loadDataToFile() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> allData = new HashMap<>();
        allData.put("users", users);
        allData.put("wallets", wallets);
        try (FileWriter writer = new FileWriter(DATA_FILE_PATH.toFile())) {
            gson.toJson(allData, writer);
        } catch (IOException e) {
            throw new IOException("failed to load data into JSON file");
        }
    }

    /**
     * Метод установки значения "false" по полю "isAuthorize" для всех "пользователей".
     */
    private void unauthorizeUsers() {
        users.forEach((id, user) -> user.setIsAuthorize(false));
    }

    /**
     * Метод вызова "unauthorizeUsers" и "loadDataToFile" при завершении приложения.
     * @throws IOException исключение, связанное с любой проблемой записи/чтения данных в/из файл-а.
     */
    @PreDestroy
    private void sessionPreDestroy() throws IOException {
        LOG.info(" ===== UNAUTHORIZING USERS ===== ");
        unauthorizeUsers();
        LOG.info("SUCCESS: all users were unauthorized");
        LOG.info(" ===== LOADING DATA TO JSON FILE ===== ");
        loadDataToFile();
        LOG.info("SUCCESS: all data was loaded into JSON data file");
    }
}

