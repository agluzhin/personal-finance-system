package agluzhin.personal_finance_system.core.data;

import agluzhin.personal_finance_system.core.models.category.Category;
import agluzhin.personal_finance_system.core.models.user.User;
import agluzhin.personal_finance_system.core.models.wallet.Wallet;

import org.slf4j.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

@Component
public class InMemoryDataStorage {
    private static final Logger log = LoggerFactory.getLogger(InMemoryDataStorage.class);
    private static final String DATA_DIRECTORY_PATH = "src/main/java/agluzhin/personal_finance_system/data";

    public static HashMap<String, User> users;
    public static HashMap<String, Wallet> wallets;
    public static HashMap<String, Category> categories;

    public InMemoryDataStorage() {
        if (Files.exists(Path.of(DATA_DIRECTORY_PATH, "data.json"))) {
            loadDataFromFile();
        } else {
            initializeDataStorage();
        }
    }

    private void loadDataFromFile() {
        // TODO: реализовать загрузку данных из JSON файла.
    }

    private void initializeDataStorage() {
        // FIXME: реализовать создание файла в случае его отсутствия.
        users = new HashMap<>();
        try {
            Files.createFile(Path.of(DATA_DIRECTORY_PATH, "data.json"));
        } catch (UnsupportedOperationException ex) {
            log.warn("Incorrect file path given.");
        } catch (FileAlreadyExistsException ex) {
            log.warn("File is already exists.");
        } catch (IOException ex) {
            log.warn("");
        }
    }
}
