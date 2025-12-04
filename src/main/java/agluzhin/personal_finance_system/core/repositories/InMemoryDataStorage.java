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
import java.net.UnknownServiceException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Данный класс содержит всю информацию о пользователях и кошельках.<br>
 * Используется в качестве упрощенной альтернативы БД.
 */
@Component
public class InMemoryDataStorage {
    // Логгер, используемый для вывода в консоль информации о состоянии приложения.
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryDataStorage.class);
    // Постоянные файловые пути хранения информации (директория/json-файл с данными).
    private static final String DATA_DIRECTORY_PATH = "src/main/java/agluzhin/personal_finance_system/core/repositories";
    private static final Path DATA_FILE_PATH = Paths.get(DATA_DIRECTORY_PATH).resolve("data.json");

    // Коллекции для хранения информации о пользователях и кошельках со структурой: "id : Object".
    public static HashMap<String, User> users = new HashMap<>();
    public static HashMap<String, Wallet> wallets = new HashMap<>();

    /**
     * Конструктор хранилища информации (тип: InMemory).<br>
     * Проверяет наличие data-файла и в случае успеха - загружает информацию из JSON файла.
     * @throws IOException исключение в случае любых проблем с файлом (чтение/запись).
     */
    public InMemoryDataStorage() throws IOException {
        if (Files.exists(DATA_FILE_PATH)) {
            loadDataFromFile();
            LOG.info("SUCCESS: all data was loaded from JSON data file");
        } else {
            createDataFile();
            LOG.info("SUCCESS: JSON data file created");
        }
    }

    /**
     * Метод создания JSON файла для дальнейшей загрузки/выгрузки данных по пользователям и кошелькам.
     * @throws IOException исключение в случае любых проблем с файлом (чтение/запись).
     */
    private void createDataFile() throws IOException {
        try {
            Files.createFile(DATA_FILE_PATH);
        } catch (IOException ex) {
            throw new IOException("failed to create JSON data file");
        }
    }

    /**
     * Метод загрузки данных по пользователям и кошелькам из JSON файла.
     * @throws IOException исключение в случае любых проблем с файлом (чтение/запись).
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
     * Метод загрузки данных по пользователям и кошелькам в JSON файл.
     * @throws IOException исключение в случае любых проблем с файлом (чтение/запись).
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
     * Метод установления значения "false" для статуса авторизации всем пользователям.
     */
    private void unauthorizeUsers() {
        users.forEach((id, user) -> user.setIsAuthorized(false));
    }

    @PreDestroy
    private void sessionPreDestroy() throws IOException {
        unauthorizeUsers();
        LOG.info("SUCCESS: all users were unauthorized");
        loadDataToFile();
        LOG.info("SUCCESS: all data was loaded into JSON data file");
    }
}

